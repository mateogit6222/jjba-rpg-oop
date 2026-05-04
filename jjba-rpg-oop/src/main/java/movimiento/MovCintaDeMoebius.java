package movimiento;

import estado.Curacion;
import personaje.Personaje;

/**
 * Cinta de Moebius (Jolyne): recupera el 50% de energiaMax y aplica estado
 * curación.
 */
public class MovCintaDeMoebius extends Movimiento {

	public MovCintaDeMoebius() {
		super("Cinta de Moebius", BlancoMov.USUARIO, TipoMov.ESTADO, 0, Movimiento.PRECISION_INFALIBLE, 8, 20, null, 0,
				"Recupera el 50% de energiaMax y aplica estado curación (1/16 vidaMax por turno).");
	}

	@Override
	public void ejecutarEfecto(Personaje objetivo) {
		int energia = objetivo.getEnergiaMax() / 2;
		objetivo.recuperarEnergia(energia);
		System.out.println(objetivo.getNombre() + " recupera " + energia + " de energía.");
		objetivo.aplicarEstado(new Curacion());
	}
}
