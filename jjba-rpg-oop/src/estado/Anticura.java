package estado;

import personaje.Personaje;

/**
 * Estado Anticura: MODIFICADOR que bloquea la curación (HOT no surte efecto).
 * Aplicado por el ítem Luck & Pluck al portador enemigo. Duración permanente.
 */
public class Anticura extends Estado {

	public Anticura() {
		super("anticura", TipoEstado.MODIFICADOR, null, 5, false);
	}

	@Override
	public void alAplicar(Personaje objetivo) {
		System.out.println("¡" + objetivo.getNombre() + " no podrá recuperar vida!");
	}

	@Override
	public void porTurno(Personaje objetivo) {
		// El efecto es pasivo; se comprueba en curar() de Personaje.
	}

	@Override
	public void alEliminar(Personaje objetivo) {
		System.out.println("¡El efecto de anticura sobre " + objetivo.getNombre() + " ha terminado!");
	}

	@Override
	public String toString() {
		return "Anticura";
	}
}