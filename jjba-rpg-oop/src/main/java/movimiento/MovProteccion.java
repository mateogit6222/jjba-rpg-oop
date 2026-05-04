package movimiento;

import personaje.Personaje;

import java.util.Random;

/**
 * Movimiento de protección: pone estaProtegido = true en el usuario. Si se usa
 * consecutivamente, la probabilidad de éxito se reduce en 1/3 cada vez. Usado
 * por Mixowall, Abioscudo, Costillas Abiertas, Lente Atmosférica, etc.
 */
public class MovProteccion extends Movimiento {

	private int usosConsecutivos = 0;
	private final Random rng = new Random();

	public MovProteccion(String nombre) {
		super(nombre, BlancoMov.USUARIO, TipoMov.ESTADO, 0, -1.0, // precision infalible base; la manejamos internamente
				16, 0, null, 4, // prioridad +4
				"Protege al usuario de ataques este turno.");
	}

	@Override
	public void ejecutarEfecto(Personaje objetivo) {
		// Calcular probabilidad de éxito según usos consecutivos
		double probabilidadExito;
		if (usosConsecutivos == 0) {
			probabilidadExito = 1.0;
		} else {
			// Cada uso consecutivo reduce la precisión a 1/3 del valor anterior
			probabilidadExito = Math.pow(1.0 / 3.0, usosConsecutivos);
		}

		if (rng.nextDouble() < probabilidadExito) {
			objetivo.setEstaProtegido(true);
			System.out.println(objetivo.getNombre() + " se ha protegido.");
			usosConsecutivos++;
		} else {
			System.out.println("¡" + nombre + " falló! La precisión se redujo demasiado.");
			usosConsecutivos = 0;
		}
	}

	/**
	 * Debe llamarse al final del turno si el personaje NO usó este movimiento, para
	 * resetear el contador de usos consecutivos.
	 */
	public void resetearConsecutivos() {
		usosConsecutivos = 0;
	}

	public int getUsosConsecutivos() {
		return usosConsecutivos;
	}
}
