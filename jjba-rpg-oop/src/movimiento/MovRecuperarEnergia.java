package movimiento;

import personaje.Personaje;

/**
 * Movimiento que recupera un porcentaje de energiaMax del usuario. Falla si la
 * energía ya está al máximo. Usado por Inhalación (100%), Respiración Hamon
 * (100%), Regeneración (50%).
 */
public class MovRecuperarEnergia extends Movimiento {

	private final double porcentaje; // 0.0 - 1.0

	public MovRecuperarEnergia(String nombre, int pp, double porcentaje, String efecto) {
		super(nombre, BlancoMov.USUARIO, TipoMov.ESTADO, 0, Movimiento.PRECISION_INFALIBLE, pp, 0, null, 0, efecto);
		this.porcentaje = porcentaje;
	}

	@Override
	public void ejecutarEfecto(Personaje objetivo) {
		if (objetivo.getEnergiaActual() >= objetivo.getEnergiaMax()) {
			System.out.println(objetivo.getNombre() + " ya tiene la energía al máximo. ¡El movimiento falló!");
			// Devolvemos el coste (0) — no hay gasto, pero sí se consumió el pp en
			// usarMov()
			return;
		}
		int recuperado = (int) (objetivo.getEnergiaMax() * porcentaje);
		objetivo.recuperarEnergia(recuperado);
		System.out.println(objetivo.getNombre() + " recupera " + recuperado + " de energía.");
	}
}
