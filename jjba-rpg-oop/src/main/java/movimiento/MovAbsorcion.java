package movimiento;

import personaje.Personaje;

/**
 * Movimiento de daño que recupera al usuario el 50% del daño infligido. Usado
 * por Absorción Celular (Santana).
 */
public class MovAbsorcion extends Movimiento {

	public MovAbsorcion(String nombre, BlancoMov blanco, TipoMov tipo, int potencia, double precision, int pp,
			int costeEnergia, int prioridad, String efecto) {
		super(nombre, blanco, tipo, potencia, precision, pp, costeEnergia, "Recupera el 50% del daño infligido.",
				prioridad, efecto);
	}

	@Override
	public void ejecutarEfecto(Personaje objetivo) {
		int danio = caster.calcularDanio(this, objetivo);
		System.out.println(objetivo.getNombre() + " recibe " + danio + " de daño.");
		objetivo.recibirDanio(danio);

		int curacion = danio / 2;
		if (curacion > 0) {
			System.out.println(caster.getNombre() + " absorbe " + curacion + " HP.");
			caster.curar(curacion);
		}
	}
}
