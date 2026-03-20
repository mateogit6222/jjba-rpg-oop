package personaje;

import estado.TipoStat;
import item.StarPlatinum;
import movimiento.BlancoMov;
import movimiento.TipoMov;
import movimiento.*;

/**
 * Jotaro Kujo — Usuario de Stand. Stats base (sin ítem): vida 80, ataque 80,
 * defensa 80, atkEsp 90, defEsp 90, vel 80 Con Star Platinum: ataque +10,
 * ataqueEspecial +45, velocidad +45
 */
public class JotaroKujo extends UsuarioDeStand {

	public JotaroKujo() {
		super("Jotaro Kujo", 80, 100, 80, 80, 90, 90, 80, new StarPlatinum());

		// Ora Ora Ora — especial, potencia 120, PP 8, coste 50
		aprenderMovimiento(new MovAtaqueSimple("Ora Ora Ora", BlancoMov.ELEGIDO, TipoMov.ESPECIAL, 120, 1.0, 8, 50, 0,
				"Lluvia de golpes de Star Platinum."));

		// Star Finger — físico, potencia 40, PP 48, coste 10, prioridad +1
		aprenderMovimiento(new MovAtaqueSimple("Star Finger", BlancoMov.ELEGIDO, TipoMov.FISICO, 40, 1.0, 48, 10, 1,
				"Ataque rápido con el dedo de Star Platinum."));

		// The World — estado, sube velocidad +2 y ataqueEspecial +1
		aprenderMovimiento(new MovBuffStats("The World", 8, 50, TipoStat.VELOCIDAD, 2, TipoStat.ATAQUE_ESPECIAL, 1,
				"Detiene el tiempo: velocidad x2, ataqueEspecial x1.5."));

		// Inhalación — estado, recupera 100% energía
		aprenderMovimiento(new MovRecuperarEnergia("Inhalación", 16, 1.0,
				"Recupera el 100% de la energía. Falla si la energía está al máximo."));
	}
}
