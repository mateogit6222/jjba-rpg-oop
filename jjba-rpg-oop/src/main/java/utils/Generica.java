package utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;

public class Generica {

    /**
     * Mapea una fila de la base de datos (Map) a una instancia de una clase Java dinámicamente.
     */
    public static <T> T mapearAObjeto(Map<String, Object> filaBD, Class<T> clase) throws Exception {
        // Instanciamos el objeto usando el constructor por defecto
        Constructor<T> constructor = clase.getDeclaredConstructor();
        constructor.setAccessible(true);
        T instancia = constructor.newInstance();

        // Obtenemos todos los atributos de la clase Java
        Field[] listaCampos = clase.getDeclaredFields();

        for (Field campo : listaCampos) {
            campo.setAccessible(true);
            
            // Nombre de la variable en Java (ej: "vidaMax") todo a minúsculas
            String nombreJava = campo.getName().toLowerCase();

            // Buscamos en el diccionario de la BD si hay una columna equivalente
            for (Map.Entry<String, Object> columna : filaBD.entrySet()) {
                // Nombre de la BD (ej: "vida_max") sin guiones y en minúsculas ("vidamax")
                String nombreBD = columna.getKey().replace("_", "").toLowerCase();
                
                if (nombreJava.equals(nombreBD)) {
                    Object valorCrudo = columna.getValue();
                    Class<?> tipoCampo = campo.getType();
                    
                    Object valorConvertido = convertir(valorCrudo, tipoCampo);
                    campo.set(instancia, valorConvertido);
                    break; // Al encontrar coincidencia, pasamos al siguiente atributo
                }
            }
        }

        return instancia;
    }

    /**
     * Asegura que los tipos crudos de la base de datos encajen con los primitivos de Java.
     */
    private static Object convertir(Object valor, Class<?> tipoDestino) {
        if (valor == null) return null;

        String strValor = valor.toString();

        if (tipoDestino == int.class || tipoDestino == Integer.class) {
            // Limpia decimales residuales por si MySQL devuelve un double como "10.0"
            return Integer.valueOf((strValor.contains(".")) ? strValor.substring(0, strValor.indexOf('.')) : strValor);
        }
        if (tipoDestino == double.class || tipoDestino == Double.class) {
            return Double.valueOf(strValor);
        }
        if (tipoDestino == String.class) {
            return strValor;
        }
        if (tipoDestino == boolean.class || tipoDestino == Boolean.class) {
            if (strValor.equals("1")) return true;
            if (strValor.equals("0")) return false;
            return Boolean.valueOf(strValor);
        }

        return valor; // Retorna el objeto tal cual si no es ninguno de los anteriores
    }
}