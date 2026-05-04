package movimiento;

import estado.TipoStat;
import personaje.Personaje;

/**
 * Movimiento de daño con efecto secundario garantizado: reduce la velocidad del
 * objetivo en un nivel. Usado por Life Shot (Giorno) y Blue Web (Jolyne).
 */
public class MovAtaqueReduceVelocidad extends Movimiento {

	public MovAtaqueReduceVelocidad(String nombre, BlancoMov blanco, TipoMov tipo, int potencia, double precision,
			int pp, int costeEnergia, int prioridad, String efecto) {
		super(nombre, blanco, tipo, potencia, precision, pp, costeEnergia,
				"Reduce la velocidad del objetivo en un nivel.", prioridad, efecto);
	}

	@Override
	public void ejecutarEfecto(Personaje objetivo) {
		int danio = caster.calcularDanio(this, objetivo);
		System.out.println(objetivo.getNombre() + " recibe " + danio + " de daño.");
		objetivo.recibirDanio(danio);

		if (objetivo.estaVivo()) {
			objetivo.modificarNivelStat(TipoStat.VELOCIDAD, -1);
		}
	}
}
