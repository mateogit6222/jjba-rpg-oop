package movimiento;

import estado.TipoStat;
import personaje.Personaje;

/**
 * Movimiento de daño que además sube la velocidad del usuario en un nivel.
 * Usado por Hamon Touch (Lisa Lisa).
 */
public class MovAtaqueSubeVelocidad extends Movimiento {

	public MovAtaqueSubeVelocidad(String nombre, BlancoMov blanco, TipoMov tipo, int potencia, double precision, int pp,
			int costeEnergia, int prioridad, String efecto) {
		super(nombre, blanco, tipo, potencia, precision, pp, costeEnergia, "Sube la velocidad del usuario en un nivel.",
				prioridad, efecto);
	}

	@Override
	public void ejecutarEfecto(Personaje objetivo) {
		int danio = caster.calcularDanio(this, objetivo);
		System.out.println(objetivo.getNombre() + " recibe " + danio + " de daño.");
		objetivo.recibirDanio(danio);

		caster.modificarNivelStat(TipoStat.VELOCIDAD, 1);
	}
}
