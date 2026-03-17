package estado;

import personaje.Personaje;

/**
 * Estado Curación: HOT que recupera 1/16 de vidaMax al final de cada turno.
 * Duración permanente.
 */
public class Curacion extends Estado {

	public Curacion() {
		super("curación", TipoEstado.HOT, null, Estado.DURACION_PERMANENTE, false);
	}

	@Override
	public void alAplicar(Personaje objetivo) {
		System.out.println("¡" + objetivo.getNombre() + " está en estado de curación!");
	}

	@Override
	public void porTurno(Personaje objetivo) {
		int cura = Math.max(1, objetivo.getVidaMax() / 16);
		System.out.println(objetivo.getNombre() + " recupera " + cura + " HP por curación.");
		objetivo.curar(cura);
	}

	@Override
	public void alEliminar(Personaje objetivo) {
		System.out.println(objetivo.getNombre() + " ya no está en curación.");
	}

	@Override
	public String toString() {
		return "Curación";
	}
}
