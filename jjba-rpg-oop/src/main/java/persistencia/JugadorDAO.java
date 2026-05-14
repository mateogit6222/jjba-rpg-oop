package persistencia;

import logica.Jugador;
import utils.Generica;
import utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JugadorDAO {

	/**
	 * Registra un nuevo jugador en la base de datos y devuelve su ID autogenerado.
	 */
	public int registrarJugador(String nombre) {
		String sql = "INSERT INTO JUGADOR (nombre, experiencia, nivel) VALUES (?, 0, 1)";
		List<Integer> ids = Utils.insertDataAndGetId(sql, Arrays.asList(nombre));

		if (ids != null && !ids.isEmpty()) {
			return ids.get(0);
		}
		return -1;
	}
	
	/**
	 * Recupera un jugador de la base de datos mediante su ID numérico.
	 */
	public Jugador cargarJugador(int idJugador) {
		String sql = "SELECT * FROM JUGADOR WHERE id_jugador = ?";
		List<Map<String, Object>> res = Utils.selectData(sql, Arrays.asList(idJugador));

		if (res != null && !res.isEmpty()) {
			try {
				return Generica.mapearAObjeto(res.get(0), Jugador.class);
			} catch (Exception e) {
				System.err.println("[JugadorDAO] Error al mapear jugador: " + e.getMessage());
			}
		}
		return null;
	}

	/**
	 * Recupera un jugador por su nombre de usuario.
	 */
	public Jugador cargarJugadorPorNombre(String nombre) {
		String sql = "SELECT * FROM JUGADOR WHERE nombre = ?";
		List<Map<String, Object>> res = Utils.selectData(sql, Arrays.asList(nombre));

		if (res != null && !res.isEmpty()) {
			try {
				return Generica.mapearAObjeto(res.get(0), Jugador.class);
			} catch (Exception e) {
				System.err.println("[JugadorDAO] Error al mapear jugador: " + e.getMessage());
			}
		}
		return null;
	}

	/**
	 * Actualiza la experiencia y nivel de un jugador en la base de datos.
	 */
	public void actualizarProgreso(Jugador jugador) {
		String sql = "UPDATE JUGADOR SET experiencia = ?, nivel = ? WHERE id_jugador = ?";
		Utils.insertData(sql, Arrays.asList(jugador.getExperiencia(), jugador.getNivel(), jugador.getIdJugador()));
	}

	/**
	 * RANKING GLOBAL: Devuelve la lista de los mejores jugadores ordenados por
	 * nivel y experiencia.
	 */
	public List<Jugador> obtenerTopRanking(int limite) {
		List<Jugador> top = new ArrayList<>();
		// Ordenamos descendentemente por nivel y luego por experiencia
		String sql = "SELECT * FROM JUGADOR ORDER BY nivel DESC, experiencia DESC LIMIT ?";
		List<Map<String, Object>> res = Utils.selectData(sql, Arrays.asList(limite));

		for (Map<String, Object> fila : res) {
			try {
				top.add(Generica.mapearAObjeto(fila, Jugador.class));
			} catch (Exception e) {
				// Ignorar mapeos fallidos individuales
			}
		}
		return top;
	}
}