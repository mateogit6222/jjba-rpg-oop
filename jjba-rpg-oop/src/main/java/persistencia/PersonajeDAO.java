package persistencia;

import personaje.Personaje;
import utils.Generica;
import utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PersonajeDAO {

	/**
	 * ESCRITURA (BATCH INSERT): Guarda una lista de personajes asociados a una
	 * partida en una sola transacción. * @param idPartida El ID de la partida a la
	 * que pertenecen.
	 * 
	 * @param personajes La lista de combatientes a guardar.
	 * @param bando      Cadena de texto ("JUGADOR" o "ENEMIGO") para diferenciarlos
	 *                   en la BD.
	 */
	public void guardarPersonajes(int idPartida, List<Personaje> personajes, String bando) {
		// La consulta SQL con 16 interrogaciones respetando la tabla PERSONAJE
		String sql = "INSERT INTO PERSONAJE (id_partida, tipo_clase, bando, tipo_pj, nombre, vida_max, vida_actual, "
				+ "energia_max, energia_actual, ataque, defensa, ataque_esp, defensa_esp, velocidad, esta_protegido, id_item) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		// Lista plana donde acumularemos los datos de TODOS los personajes para el
		// Batch
		List<Object> loteDatos = new ArrayList<>();

		for (Personaje p : personajes) {
			loteDatos.add(idPartida);
			loteDatos.add(p.getClass().getSimpleName()); // Guarda el nombre real de la clase (ej. "GiornoGiovanna")
			loteDatos.add(bando);
			loteDatos.add(p.getTipopj().name()); // Enum a String (ej. "U_STAND")
			loteDatos.add(p.getNombre());
			loteDatos.add(p.getVidaMax());
			loteDatos.add(p.getVidaActual());
			loteDatos.add(p.getEnergiaMax());
			loteDatos.add(p.getEnergiaActual());
			loteDatos.add(p.getAtaque());
			loteDatos.add(p.getDefensa());
			loteDatos.add(p.getAtaqueEspecial());
			loteDatos.add(p.getDefensaEspecial());
			loteDatos.add(p.getVelocidad());
			loteDatos.add(p.isEstaProtegido() ? 1 : 0); // Booleano a TINYINT numérico

			// Para el id_item, como los instanciamos de forma fija por clase, podemos
			// dejarlo nulo en este punto
			// o mapearlo si en el futuro creamos un catálogo de ítems persistente.
			loteDatos.add(null);
		}

		// Ejecutamos la inserción masiva a través de tu clase Utils
		Utils.insertData(sql, loteDatos);
		System.out.println("[PersonajeDAO] Se han guardado " + personajes.size() + " combatientes del bando " + bando
				+ " en la BD.");
	}

	/**
	 * LECTURA: Recupera los personajes de una partida y los instancia según su
	 * clase original.
	 */
	public List<Personaje> cargarPersonajes(int idPartida, String bando) {
		List<Personaje> personajes = new ArrayList<>();
		String sql = "SELECT * FROM PERSONAJE WHERE id_partida = ? AND bando = ?";

		// 1. Obtenemos los datos crudos
		List<Map<String, Object>> resultados = Utils.selectData(sql, Arrays.asList(idPartida, bando));

		for (Map<String, Object> fila : resultados) {
			try {
				// 2. Recuperamos el nombre de la clase (ej: "JotaroKujo")
				String nombreClase = (String) fila.get("tipo_clase");

				// 3. Obtenemos la clase real usando Reflection
				Class<?> claseReal = Class.forName("personaje." + nombreClase);

				// 4. Usamos tu clase Generica para mapear los stats al objeto recién creado
				Personaje p = (Personaje) Generica.mapearAObjeto(fila, claseReal);
				personajes.add(p);

			} catch (Exception e) {
				System.err.println("[PersonajeDAO] Error al reconstruir personaje: " + e.getMessage());
			}
		}
		return personajes;
	}

}