package utils;

import persistencia.GestorConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    private static int contarInterrogaciones(String sql) {
        int contador = 0;
        for (int i = 0; i < sql.length(); i++) {
            if (sql.charAt(i) == '?') {
                contador++;
            }
        }
        return contador;
    }

    public static List<Integer> insertDataAndGetId(String sql, List<Object> data) {
        return doAction(sql, data, true);
    }

    public static void insertData(String sql, List<Object> data) {
        doAction(sql, data, false);
    }

    private static List<Integer> doAction(String sql, List<Object> data, boolean devolverId) {
        List<Integer> ids = new ArrayList<>();
        
        // Obtenemos la conexión única del Singleton
        Connection con = GestorConexionBD.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql,
                devolverId ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS)) {
            
            int numInte = contarInterrogaciones(sql);
            boolean esLote = data.size() % numInte == 0;

            if (!esLote) {
                throw new Exception("Los datos no coinciden con las ? de la consulta SQL");
            }
            
            for (int i = 0, posicion = 1; i < data.size(); i++, posicion++) {
                ps.setObject(posicion, data.get(i));
                if ((i + 1) % numInte == 0) {
                    posicion = 0;
                    ps.addBatch();
                }
            }
            
            ps.executeBatch();

            // Si no necesitamos ID o si es un UPDATE/DELETE, terminamos aquí
            if (!devolverId || sql.trim().toLowerCase().startsWith("delete") || sql.trim().toLowerCase().startsWith("update")) {
                return null;
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                while (rs.next()) {
                    ids.add(rs.getInt(1));
                }
            }

        } catch (Exception e) {
            System.err.println("[UtilsBD] Error ejecutando la consulta: " + sql);
            e.printStackTrace();
        }
        return ids;
    }

    /**
     * LECTURA: Retorna una lista de mapas. Cada mapa es una fila de la base de datos (Columna -> Valor).
     */
    public static List<Map<String, Object>> selectData(String sql, List<Object> parametros) {
        List<Map<String, Object>> resultados = new ArrayList<>();
        Connection con = GestorConexionBD.getInstancia().getConexion();
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            
            // Inyectamos los parámetros en las '?' si existen
            if (parametros != null && !parametros.isEmpty()) {
                for (int i = 0; i < parametros.size(); i++) {
                    ps.setObject(i + 1, parametros.get(i));
                }
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int numColumnas = metaData.getColumnCount();
                
                while (rs.next()) {
                    Map<String, Object> fila = new HashMap<>();
                    for (int i = 1; i <= numColumnas; i++) {
                        String nombreColumna = metaData.getColumnLabel(i);
                        Object valor = rs.getObject(i);
                        fila.put(nombreColumna, valor);
                    }
                    resultados.add(fila);
                }
            }
        } catch (Exception e) {
            System.err.println("[UtilsBD] Error en SELECT: " + sql);
            e.printStackTrace();
        }
        return resultados;
    }
}