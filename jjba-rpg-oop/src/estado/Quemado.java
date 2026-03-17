package estado;

import personaje.Personaje;

/**
 * Estado Quemado: DOT que inflige 1/16 de la vidaMax al final de cada turno.
 * Duración permanente (hasta que el personaje se debilite).
 */
public class Quemado extends Estado {

	public Quemado() {
		super("quemado", TipoEstado.DOT, null, Estado.DURACION_PERMANENTE, false);
	}

	@Override
	public void alAplicar(Personaje objetivo) {
		System.out.println("¡" + objetivo.getNombre() + " ha sido quemado!");
	}

	@Override
	public void porTurno(Personaje objetivo) {
		int danio = Math.max(1, objetivo.getVidaMax() / 16);
		System.out.println(objetivo.getNombre() + " sufre " + danio + " de daño por quemadura.");
		objetivo.recibirDanio(danio);
	}

	@Override
	public void alEliminar(Personaje objetivo) {
		System.out.println(objetivo.getNombre() + " ya no está quemado.");
	}

	@Override
	public String toString() {
		return "Quemado";
	}
}
