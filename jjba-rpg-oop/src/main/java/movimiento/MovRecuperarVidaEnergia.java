package movimiento;

import personaje.Personaje;

/**
 * Movimiento que recupera un % de vidaMax Y un % de energiaMax del objetivo.
 * Falla si AMBAS están al máximo. Usado por Rematerialización (Josuke, 25%/25%)
 * y Abiogénesis (Giorno, 25%/25%).
 */
public class MovRecuperarVidaEnergia extends Movimiento {

	private final double porcentajeVida;
	private final double porcentajeEnergia;
	private final BlancoMov blancoReal;

	public MovRecuperarVidaEnergia(String nombre, BlancoMov blanco, int pp, int costeEnergia, double porcentajeVida,
			double porcentajeEnergia, String efecto) {
		super(nombre, blanco, TipoMov.ESTADO, 0, Movimiento.PRECISION_INFALIBLE, pp, costeEnergia, null, 0, efecto);
		this.porcentajeVida = porcentajeVida;
		this.porcentajeEnergia = porcentajeEnergia;
		this.blancoReal = blanco;
	}

	@Override
	public void ejecutarEfecto(Personaje objetivo) {
		boolean vidaLlena = objetivo.getVidaActual() >= objetivo.getVidaMax();
		boolean energiaLlena = objetivo.getEnergiaActual() >= objetivo.getEnergiaMax();

		if (vidaLlena && energiaLlena) {
			System.out.println(objetivo.getNombre() + " ya tiene vida y energía al máximo. ¡El movimiento falló!");
			return;
		}

		if (!vidaLlena) {
			int cura = (int) (objetivo.getVidaMax() * porcentajeVida);
			objetivo.curar(cura);
			System.out.println(objetivo.getNombre() + " recupera " + cura + " HP.");
		}
		if (!energiaLlena) {
			int energia = (int) (objetivo.getEnergiaMax() * porcentajeEnergia);
			objetivo.recuperarEnergia(energia);
			System.out.println(objetivo.getNombre() + " recupera " + energia + " de energía.");
		}
	}
}
