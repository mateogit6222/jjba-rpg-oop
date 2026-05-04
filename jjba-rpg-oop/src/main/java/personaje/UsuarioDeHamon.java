package personaje;

import movimiento.Movimiento;
import item.Item;

import java.util.List;
import java.util.Scanner;

/**
 * Clase abstracta que agrupa a los personajes jugables que utilizan la técnica
 * del Hamon. Delega la toma de decisiones en combate directamente al jugador a
 * través de la consola.
 */
public abstract class UsuarioDeHamon extends Personaje {

	private static final Scanner scanner = new Scanner(System.in);

	/**
	 * Constructor base para un usuario de Hamon. Inicializa al personaje con la
	 * clasificación TipoPj.U_HAMON. * (Mismos parámetros descriptivos que en la
	 * clase Personaje).
	 */
	public UsuarioDeHamon(String nombre, int vidaMax, int energiaMax, int ataque, int defensa, int ataqueEspecial,
			int defensaEspecial, int velocidad, Item item) {
		super(nombre, TipoPj.U_HAMON, vidaMax, energiaMax, ataque, defensa, ataqueEspecial, defensaEspecial, velocidad,
				false, item);
	}

	@Override
	public void elegirAccion() {
		System.out.println("[" + nombre + "] elige una acción.");
	}

	/**
	 * Muestra la interfaz de consola con los movimientos disponibles del personaje.
	 * Detalla estadísticas clave de cada habilidad (PP, Coste, Prioridad) y valida
	 * que la selección del jugador sea un movimiento ejecutable en este turno.
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
				int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
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
				System.out.println("  Entrada inválida.");
			}
		}
	}

	/**
	 * Muestra por consola una lista de objetivos posibles y gestiona la selección
	 * del jugador.
	 *
	 * @param opciones La lista de personajes elegibles (pueden ser aliados o
	 *                 enemigos).
	 * @param etiqueta Una palabra descriptiva (ej. "enemigo", "aliado") para
	 *                 clarificar el menú.
	 * @return El personaje elegido como objetivo de la acción.
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