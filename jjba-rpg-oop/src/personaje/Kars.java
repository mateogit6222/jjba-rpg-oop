package personaje;

import estado.TipoStat;
import item.MascaraDePiedra;
import movimiento.BlancoMov;
import movimiento.TipoMov;
import movimiento.*;

/**
 * Kars — Hombre del Pilar, jefe final (enemigo IA). Stats base: vida
 * 120+60=180, ataque 120, defensa 80, atkEsp 20, defEsp 80, vel 80 Con Máscara
 * de Piedra: vidaMax +60
 */
public class Kars extends HombreDelPilar {

	public Kars() {
		super("Kars", 120, 100, 120, 80, 20, 80, 80, new MascaraDePiedra());

		// Sables de Luz — físico, potencia 120, PP 8, coste 50
		aprenderMovimiento(new MovAtaqueSimple("Sables de Luz", BlancoMov.ELEGIDO, TipoMov.FISICO, 120, 1.0, 8, 50, 0,
				"Cuchillas de hueso endurecido como diamante."));

		// Estocada de Luz — físico, potencia 40, PP 48, coste 10, prioridad +1
		aprenderMovimiento(new MovAtaqueSimple("Estocada de Luz", BlancoMov.ELEGIDO, TipoMov.FISICO, 40, 1.0, 48, 10, 1,
				"Ataque rápido con cuchilla de luz."));

		// Velocidad Furtiva — sube velocidad +2 y ataque +1
		aprenderMovimiento(new MovBuffStats("Velocidad Furtiva", 8, 50, TipoStat.VELOCIDAD, 2, TipoStat.ATAQUE, 1,
				"Desaparece a velocidad sobrehumana: velocidad x2, ataque x1.5."));

		// Regeneración — recupera 50% energía
		aprenderMovimiento(new MovRecuperarEnergia("Regeneración", 16, 0.5,
				"Regeneración celular: recupera el 50% de la energía. Falla si está al máximo."));
	}
}
