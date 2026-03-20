package personaje;

import item.CrazyDiamond;
import movimiento.BlancoMov;
import movimiento.TipoMov;
import movimiento.*;

/**
 * Josuke Higashikata — Usuario de Stand. Stats base: vida 80, ataque 80,
 * defensa 80, atkEsp 90, defEsp 90, vel 80 Con Crazy Diamond: ataque +10,
 * defensa +40, ataqueEspecial +15, defensaEspecial +30, velocidad +5
 */
public class JosukeHigashikata extends UsuarioDeStand {

	public JosukeHigashikata() {
		super("Josuke Higashikata", 80, 100, 80, 80, 90, 90, 80, new CrazyDiamond());

		// Dora Ra Ra — especial, potencia 100, PP 8, coste 40
		aprenderMovimiento(new MovAtaqueSimple("Dora Ra Ra", BlancoMov.ELEGIDO, TipoMov.ESPECIAL, 100, 1.0, 8, 40, 0,
				"Lluvia de golpes de Crazy Diamond."));

		// Return Cluster — físico, potencia 60, PP 32, coste 20
		aprenderMovimiento(new MovAtaqueSimple("Return Cluster", BlancoMov.ELEGIDO, TipoMov.FISICO, 60, 1.0, 32, 20, 0,
				"Ataque físico de Crazy Diamond."));

		// Mixowall — protección con prioridad +4
		aprenderMovimiento(new MovProteccion("Mixowall"));

		// Rematerialización — recupera 25% vida y 25% energía del objetivo
		aprenderMovimiento(new MovRecuperarVidaEnergia("Rematerialización", BlancoMov.ELEGIDO, 16, 20, 0.25, 0.25,
				"Restaura el 25% de vida y energía del objetivo. Falla si ambas están al máximo."));
	}
}
