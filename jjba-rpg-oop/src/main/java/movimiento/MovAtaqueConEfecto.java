package movimiento;

import personaje.Personaje;
import estado.Estado;

import java.util.Random;

/**
 * Movimiento de daño con efecto secundario probabilístico (veneno, quemado,
 * etc.).
 */
public class MovAtaqueConEfecto extends Movimiento {

	private final double probabilidadEfecto;
	private final Estado estadoAplicar;
	private final Random rng = new Random();

	public MovAtaqueConEfecto(String nombre, BlancoMov blanco, TipoMov tipo, int potencia, double precision, int pp,
			int costeEnergia, int prioridad, String efecto, double probabilidadEfecto, Estado estadoAplicar) {
		super(nombre, blanco, tipo, potencia, precision, pp, costeEnergia,
				estadoAplicar != null ? estadoAplicar.getNombre() : null, prioridad, efecto);
		this.probabilidadEfecto = probabilidadEfecto;
		this.estadoAplicar = estadoAplicar;
	}

	@Override
	public void ejecutarEfecto(Personaje objetivo) {
		int danio = caster.calcularDanio(this, objetivo);
		System.out.println(objetivo.getNombre() + " recibe " + danio + " de daño.");
		objetivo.recibirDanio(danio);

		if (estadoAplicar != null && objetivo.estaVivo() && rng.nextDouble() < probabilidadEfecto) {
			objetivo.aplicarEstado(estadoAplicar);
		}
	}
}
