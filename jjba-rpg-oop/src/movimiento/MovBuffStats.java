package movimiento;

import estado.TipoStat;
import personaje.Personaje;

/**
 * Movimiento de estado que sube stats del usuario. The World (Jotaro):
 * velocidad +2, ataqueEspecial +1. Velocidad Furtiva (Kars): velocidad +2,
 * ataque +1.
 */
public class MovBuffStats extends Movimiento {

	private final TipoStat stat1;
	private final int niveles1;
	private final TipoStat stat2;
	private final int niveles2;

	public MovBuffStats(String nombre, int pp, int costeEnergia, TipoStat stat1, int niveles1, TipoStat stat2,
			int niveles2, String efecto) {
		super(nombre, BlancoMov.USUARIO, TipoMov.ESTADO, 0, Movimiento.PRECISION_INFALIBLE, pp, costeEnergia, null, 0,
				efecto);
		this.stat1 = stat1;
		this.niveles1 = niveles1;
		this.stat2 = stat2;
		this.niveles2 = niveles2;
	}

	@Override
	public void ejecutarEfecto(Personaje objetivo) {
		objetivo.modificarNivelStat(stat1, niveles1);
		if (stat2 != null) {
			objetivo.modificarNivelStat(stat2, niveles2);
		}
	}
}
