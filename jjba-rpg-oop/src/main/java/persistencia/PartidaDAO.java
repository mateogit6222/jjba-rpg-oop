package persistencia;

import utils.Utils;
import utils.Generica;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import logica.Partida; 

public class PartidaDAO {

	/**
	 * ESCRITURA (INSERT): Guarda una nueva partida en la base de datos. Retorna el
	 * id_partida autogenerado si tiene éxito, o -1 si falla.
	 */
	public int guardarNuevaPartida(int idJugador, int idDificultad, int rondaActual, boolean modoAuto) {
		String sql = "INSERT INTO PARTIDA (id_jugador, id_dificultad, ronda_actual, modo_auto) VALUES (?, ?, ?, ?)";

		// Empaquetamos los datos respetando el orden exacto de las interrogaciones (?)
		List<Object> datos = Arrays.asList(idJugador, idDificultad, rondaActual, modoAuto);

		// Ejecutamos la inserción usando tu clase Utils
		List<Integer> idsGenerados = Utils.insertDataAndGetId(sql, datos);

		if (idsGenerados != null && !idsGenerados.isEmpty()) {
			System.out.println("[PartidaDAO] Partida guardada con éxito. ID asignado: " + idsGenerados.get(0));
			return idsGenerados.get(0);
		}

		System.err.println("[PartidaDAO] Error crítico al intentar guardar la partida.");
		return -1;
	}

	/**
	 * LECTURA (SELECT): Recupera una partida desde la base de datos y la convierte
	 * en Objeto Java. Retorna la instancia de Partida, o null si no se encuentra el
	 * ID.
	 */
	public Partida cargarPartida(int idPartidaBuscada) {
		String sql = "SELECT * FROM PARTIDA WHERE id_partida = ?";

		// 1. Obtenemos el diccionario con los datos crudos desde MySQL usando Utils
		List<Map<String, Object>> resultados = Utils.selectData(sql, Arrays.asList(idPartidaBuscada));

		// 2. Comprobamos si MySQL encontró la partida solicitada
		if (resultados != null && !resultados.isEmpty()) {
			try {
				// 3. ¡Magia del ORM! Mapeamos el primer diccionario directamente a la clase
				// Partida
				// Tu clase Generica se encargará de ignorar los guiones bajos ("ronda_actual"
				// -> "rondaActual")
				Partida partidaCargada = Generica.mapearAObjeto(resultados.get(0), Partida.class);

				System.out.println("[PartidaDAO] Partida cargada en memoria exitosamente.");
				return partidaCargada;

			} catch (Exception e) {
				System.err.println("[PartidaDAO] Error interno al intentar mapear los datos al objeto Partida.");
				e.printStackTrace();
			}
		} else {
			System.out.println("[PartidaDAO] No existe ninguna partida guardada con el ID: " + idPartidaBuscada);
		}

		return null;
	}
}