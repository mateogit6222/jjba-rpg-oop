package persistencia;

import logica.Dificultad;
import utils.Generica;
import utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DificultadDAO {

	/**
	 * Recupera los modificadores de una dificultad específica por su ID.
	 */
	public Dificultad cargarDificultad(int idDificultad) {
		String sql = "SELECT * FROM DIFICULTAD WHERE id_dificultad = ?";
		List<Map<String, Object>> res = Utils.selectData(sql, Arrays.asList(idDificultad));

		if (res != null && !res.isEmpty()) {
			try {
				return Generica.mapearAObjeto(res.get(0), Dificultad.class);
			} catch (Exception e) {
				System.err.println("[DificultadDAO] Error al cargar dificultad: " + e.getMessage());
			}
		}
		return null;
	}

	/**
	 * Recupera todas las dificultades disponibles para mostrarlas en el menú de
	 * selección.
	 */
	public List<Dificultad> obtenerTodas() {
		List<Dificultad> lista = new ArrayList<>();
		String sql = "SELECT * FROM DIFICULTAD ORDER BY id_dificultad ASC";
		List<Map<String, Object>> res = Utils.selectData(sql, null);

		for (Map<String, Object> fila : res) {
			try {
				lista.add(Generica.mapearAObjeto(fila, Dificultad.class));
			} catch (Exception e) {
				// Ignorar error individual
			}
		}
		return lista;
	}
}