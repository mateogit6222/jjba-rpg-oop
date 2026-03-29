package personaje;

import movimiento.Movimiento;
import item.Item;

import java.util.List;
import java.util.Scanner;

/**
 * Clase abstracta que agrupa a los personajes jugables que poseen un Stand.
 * Delega la toma de decisiones en combate directamente al jugador a través de
 * la consola.
 */
public abstract class UsuarioDeStand extends Personaje {

	private static final Scanner scanner = new Scanner(System.in);

	/**
	 * Constructor base para un usuario de Stand. Inicializa al personaje con la
	 * clasificación TipoPj.U_STAND. * (Mismos parámetros descriptivos que en la
	 * clase Personaje).
	 */
	public UsuarioDeStand(String nombre, int vidaMax, int energiaMax, int ataque, int defensa, int ataqueEspecial,
			int defensaEspecial, int velocidad, Item item) {
		super(nombre, TipoPj.U_STAND, vidaMax, energiaMax, ataque, defensa, ataqueEspecial, defensaEspecial, velocidad,
				false, item);
	}

	@Override
	public void elegirAccion() {
		// Este método se llama para mostrar el menú; la lógica real de turno
		// está en Combate.java, que llama a elegirMovimiento() pasando los objetivos.
		System.out.println("[" + nombre + "] elige una acción.");
	}

	/**
	 * Muestra la interfaz de consola con los movimientos disponibles del Stand.
	 * Detalla estadísticas clave de cada habilidad (PP, Coste, Prioridad) y asegura
	 * mediante bucle de validación que se elija una acción ejecutable.
	 *
	 * @return El movimiento elegido y validado por el jugador.
	 */
	public Movimiento elegirMovimiento() {
		List<Movimiento> movs = getMovimientos();
		System.out.println("\n══════════════════════════════");
		System.out.println("  " + nombre + " — ¿Qué movimiento usar?");
		System.out.println("══════════════════════════════");

		for (int i = 0; i < movs.size(); i++) {
			Movimiento m = movs.get(i);
			boolean usable = m.puedeUsarseMov();
			System.out.println("  [" + (i + 1) + "] " + m.getNombre() + "  PP: " + m.getPp() + "  Energía: "
					+ m.getCosteEnergia() + "  Prioridad: " + (m.getPrioridad() >= 0 ? "+" : "") + m.getPrioridad()
					+ (usable ? "" : "  (no usable)"));
		}
		System.out.println("══════════════════════════════");

		while (true) {
			System.out.print("Elige (1-" + movs.size() + "): ");
			try {
				String linea = scanner.nextLine().trim();
				int idx = Integer.parseInt(linea) - 1;
				if (idx >= 0 && idx < movs.size()) {
					Movimiento elegido = movs.get(idx);
					if (!elegido.puedeUsarseMov()) {
						System.out.println("  ¡Ese movimiento no se puede usar ahora!");
					} else {
						return elegido;
					}
				} else {
					System.out.println("  Número fuera de rango.");
				}
			} catch (NumberFormatException e) {
				System.out.println("  Entrada inválida. Escribe un número.");
			}
		}
	}

	/**
	 * Muestra por consola una lista interactiva de objetivos posibles y gestiona la
	 * selección manual del jugador, validando rangos e inputs incorrectos.
	 *
	 * @param opciones La lista de personajes que pueden ser seleccionados.
	 * @param etiqueta Contexto del objetivo (ej. "enemigo", "curación") para la
	 *                 interfaz.
	 * @return El personaje sobre el que recaerá el efecto del movimiento.
	 */
	public Personaje elegirObjetivo(List<Personaje> opciones, String etiqueta) {
		System.out.println("\n  Elige objetivo (" + etiqueta + "):");
		for (int i = 0; i < opciones.size(); i++) {
			System.out.println("  [" + (i + 1) + "] " + opciones.get(i).toString());
		}
		while (true) {
			System.out.print("Elige (1-" + opciones.size() + "): ");
			try {
				int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
				if (idx >= 0 && idx < opciones.size()) {
					return opciones.get(idx);
				}
				System.out.println("  Número fuera de rango.");
			} catch (NumberFormatException e) {
				System.out.println("  Entrada inválida.");
			}
		}
	}
}