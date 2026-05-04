package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GestorConexionBD {

	private static GestorConexionBD instancia;
	private Connection conexion;

	// Ajusta la URL, el usuario y la contraseña según tu configuración local
	private final String URL = "jdbc:mysql://localhost:3306/intermodular_bd_pg";
	private final String USUARIO = "root";
	private final String PASSWORD = "";

	private GestorConexionBD() {
		try {
			conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
			System.out.println("[BD] Conexión establecida correctamente.");
		} catch (SQLException e) {
			System.err.println("[BD] Error al conectar: " + e.getMessage());
		}
	}

	public static GestorConexionBD getInstancia() {
		if (instancia == null) {
			instancia = new GestorConexionBD();
		}
		return instancia;
	}

	public Connection getConexion() {
		return conexion;
	}

	public void cerrarConexion() {
		if (conexion != null) {
			try {
				conexion.close();
				System.out.println("[BD] Conexión cerrada.");
			} catch (SQLException e) {
				System.err.println("[BD] Error al cerrar la conexión.");
			}
		}
	}
}