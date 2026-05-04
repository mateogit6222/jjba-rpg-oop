package movimiento;

import personaje.Personaje;
import personaje.TipoPj;

/**
 * Movimiento que hace el doble de daño contra H_PILAR y NO_MUERTO. Usado por
 * Sunlight Yellow Overdrive, Clacker Volley.
 */
public class MovAtaqueAntiPilar extends Movimiento {

	public MovAtaqueAntiPilar(String nombre, BlancoMov blanco, TipoMov tipo, int potencia, double precision, int pp,
			int costeEnergia, String efectoSecundario, int prioridad, String efecto) {
		super(nombre, blanco, tipo, potencia, precision, pp, costeEnergia, efectoSecundario, prioridad, efecto);
	}

	@Override
	public void ejecutarEfecto(Personaje objetivo) {
		int danio = caster.calcularDanio(this, objetivo);

		TipoPj tipo = objetivo.getTipopj();
		if (tipo == TipoPj.H_PILAR || tipo == TipoPj.NO_MUERTO) {
			danio *= 2;
			System.out.println("¡Es muy eficaz contra los Hombres del Pilar y No Muertos!");
		}

		System.out.println(objetivo.getNombre() + " recibe " + danio + " de daño.");
		objetivo.recibirDanio(danio);
	}
}
