package utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Generica {

	/**
	 * Mapea una fila de la base de datos (Map) a una instancia de una clase Java,
	 * explorando automáticamente los atributos de la clase y de todas sus
	 * superclases.
	 */
	public static <T> T mapearAObjeto(Map<String, Object> filaBD, Class<T> clase) throws Exception {
		// 1. Instanciamos el objeto usando su constructor por defecto
		Constructor<T> constructor = clase.getDeclaredConstructor();
		constructor.setAccessible(true);
		T instancia = constructor.newInstance();

		// 2. RECOPILACIÓN JERÁRQUICA: Extraemos los campos de la clase hija Y de sus
		// clases padre
		List<Field> todosLosCampos = new ArrayList<>();
		Class<?> claseActual = clase;
		while (claseActual != null && claseActual != Object.class) {
			for (Field campo : claseActual.getDeclaredFields()) {
				todosLosCampos.add(campo);
			}
			claseActual = claseActual.getSuperclass(); // Subimos un nivel en la herencia
		}

		// 3. Mapeo dinámico ignorando guiones bajos
		for (Field campo : todosLosCampos) {
			campo.setAccessible(true);
			String nombreJava = campo.getName().toLowerCase();

			for (Map.Entry<String, Object> columna : filaBD.entrySet()) {
				String nombreBD = columna.getKey().replace("_", "").toLowerCase();

				if (nombreJava.equals(nombreBD)) {
					Object valorCrudo = columna.getValue();
					Class<?> tipoCampo = campo.getType();

					try {
						Object valorConvertido = convertir(valorCrudo, tipoCampo);
						campo.set(instancia, valorConvertido);
					} catch (Exception e) {
						// Si un atributo específico falla (ej. colecciones complejas), no detenemos la
						// carga
					}
					break;
				}
			}
		}

		return instancia;
	}

	/**
	 * Convierte los tipos crudos de SQL a primitivos de Java y Enums de forma
	 * segura.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object convertir(Object valor, Class<?> tipoDestino) {
		if (valor == null)
			return null;

		String strValor = valor.toString();

		if (tipoDestino == int.class || tipoDestino == Integer.class) {
			return Integer.valueOf((strValor.contains(".")) ? strValor.substring(0, strValor.indexOf('.')) : strValor);
		}
		if (tipoDestino == double.class || tipoDestino == Double.class) {
			return Double.valueOf(strValor);
		}
		if (tipoDestino == String.class) {
			return strValor;
		}
		if (tipoDestino == boolean.class || tipoDestino == Boolean.class) {
			if (strValor.equals("1"))
				return true;
			if (strValor.equals("0"))
				return false;
			return Boolean.valueOf(strValor);
		}
		// SOPORTE AVANZADO: Conversión automática de Strings de MySQL a Enums de Java
		// (ej. TipoPj)
		if (tipoDestino.isEnum()) {
			return Enum.valueOf((Class<Enum>) tipoDestino, strValor);
		}

		return valor;
	}
}