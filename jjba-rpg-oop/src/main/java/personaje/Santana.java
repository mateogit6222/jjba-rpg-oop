package personaje;

import movimiento.BlancoMov;
import movimiento.TipoMov;
import movimiento.*;

/**
 * Santana — Hombre del Pilar (enemigo IA). Stats base: vida 120, ataque 105,
 * defensa 80, atkEsp 20, defEsp 80, vel 65 Sin ítem.
 */
public class Santana extends HombreDelPilar {

	public Santana() {
		super("Santana", 120, 100, 105, 80, 20, 80, 65, null);

		// Absorción Celular — físico, potencia 75, PP 16, coste 40, drena 50% daño
		aprenderMovimiento(new MovAbsorcion("Absorción Celular", BlancoMov.ELEGIDO, TipoMov.FISICO, 75, 1.0, 16, 40, 0,
				"Absorbe al objetivo. Recupera el 50% del daño infligido."));

		// Balas de Carne — físico, potencia 60, PP 32, coste 10
		aprenderMovimiento(new MovAtaqueSimple("Balas de Carne", BlancoMov.ELEGIDO, TipoMov.FISICO, 60, 1.0, 32, 10, 0,
				"Proyectiles de tejido óseo."));

		// Costillas Abiertas — protección con prioridad +4
		aprenderMovimiento(new MovProteccion("Costillas Abiertas"));

		// Regeneración — recupera 50% energía
		aprenderMovimiento(new MovRecuperarEnergia("Regeneración", 16, 0.5,
				"Regeneración celular: recupera el 50% de la energía. Falla si está al máximo."));
	}
}
