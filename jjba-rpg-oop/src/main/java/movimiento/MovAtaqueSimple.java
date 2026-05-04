package movimiento;

import personaje.Personaje;

/**
 * Movimiento de daño simple: calcula y aplica daño al objetivo. Sirve como base
 * para movimientos de ataque sin efectos secundarios complejos.
 */
public class MovAtaqueSimple extends Movimiento {

	public MovAtaqueSimple(String nombre, BlancoMov blanco, TipoMov tipo, int potencia, double precision, int pp,
			int costeEnergia, int prioridad, String efecto) {
		super(nombre, blanco, tipo, potencia, precision, pp, costeEnergia, null, prioridad, efecto);
	}

	@Override
	public void ejecutarEfecto(Personaje objetivo) {
		int danio = caster.calcularDanio(this, objetivo);
		System.out.println(objetivo.getNombre() + " recibe " + danio + " de daño.");
		objetivo.recibirDanio(danio);
	}
}
