package personaje;

import item.Clackers;
import movimiento.BlancoMov;
import movimiento.TipoMov;
import movimiento.*;

/**
 * Joseph Joestar — Usuario de Hamon. Stats base: vida 100, ataque 95, defensa
 * 85, atkEsp 40, defEsp 85, vel 95 Ítem: Clackers (sin bonus de stats)
 */
public class JosephJoestar extends UsuarioDeHamon {

	public JosephJoestar() {
		super("Joseph Joestar", 100, 100, 95, 85, 40, 85, 95, new Clackers());

		// Hamon Overdrive — físico, potencia 120, PP 8, coste 50
		aprenderMovimiento(new MovAtaqueSimple("Hamon Overdrive", BlancoMov.ELEGIDO, TipoMov.FISICO, 120, 1.0, 8, 50, 0,
				"Puñetazo cargado con Hamon."));

		// Clacker Volley — físico, daño x2 vs Pilar/No Muerto, PP 8, coste 50
		aprenderMovimiento(new MovAtaqueAntiPilar("Clacker Volley", BlancoMov.ELEGIDO, TipoMov.FISICO, 70, 1.0, 8, 50,
				"Doble de daño a Hombres del Pilar y No Muertos.", 0, "Cadenas explosivas de Hamon."));

		// Hamon Hair Barrier — protección con prioridad +4
		aprenderMovimiento(new MovProteccion("Hamon Hair Barrier"));

		// Respiración Hamon — recupera 100% energía
		aprenderMovimiento(new MovRecuperarEnergia("Respiración Hamon", 16, 1.0,
				"Recupera el 100% de la energía. Falla si la energía está al máximo."));
	}
}
