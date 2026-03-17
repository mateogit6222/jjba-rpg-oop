package personaje;

import movimiento.Movimiento;
import item.Item;

import java.util.List;
import java.util.Scanner;

/**
 * Subclase abstracta para usuarios de Stand. elegirAccion() pide entrada por
 * teclado al jugador.
 */
public abstract class UsuarioDeStand extends Personaje {

	private static final Scanner scanner = new Scanner(System.in);

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
	 * Muestra el menú de movimientos y devuelve el movimiento elegido. Devuelve
	 * null si ningún movimiento es usable.
	 */
	public Movimiento elegirMovimiento() {
		List<Movimiento> movs = getMovimientos();
		System.out.println("\n══════════════════════════════");
		System.out.println("  " + nombre + " — ¿Qué movimiento usar?");
		System.out.println("══════════════════════════════");

		for (int i = 0; i < movs.size(); i++) {
			Movimiento m = movs.get(i);
			boolean usable = m.puedeUsarseMov();
			System.out.printf("  [d] -22s PP: 2d  Energía: 2d  Prioridad: +d  sn", i + 1, m.getNombre(), m.getPp(),
					m.getCosteEnergia(), m.getPrioridad(), usable ? "" : "(no usable)");
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
	 * Muestra los objetivos disponibles y devuelve el elegido.
	 */
	public Personaje elegirObjetivo(List<Personaje> opciones, String etiqueta) {
		System.out.println("\n  Elige objetivo (" + etiqueta + "):");
		for (int i = 0; i < opciones.size(); i++) {
			System.out.printf("  [d] sn", i + 1, opciones.get(i).toString());
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
