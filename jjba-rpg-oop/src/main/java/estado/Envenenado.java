package estado;

import personaje.Personaje;

/**
 * Estado Envenenado: DOT que inflige 1/16 de la vidaMax al final de cada turno.
 * Duración permanente.
 */
public class Envenenado extends Estado {

	public Envenenado() {
		super("envenenado", TipoEstado.DOT, null, Estado.DURACION_PERMANENTE, false);
	}

	@Override
	public void alAplicar(Personaje objetivo) {
		System.out.println("¡" + objetivo.getNombre() + " ha sido envenenado!");
	}

	@Override
	public void porTurno(Personaje objetivo) {
		int danio = Math.max(1, objetivo.getVidaMax() / 16);
		System.out.println(objetivo.getNombre() + " sufre " + danio + " de daño por veneno.");
		objetivo.recibirDanio(danio);
	}

	@Override
	public void alEliminar(Personaje objetivo) {
		System.out.println(objetivo.getNombre() + " ya no está envenenado.");
	}

	@Override
	public String toString() {
		return "Envenenado";
	}
}
