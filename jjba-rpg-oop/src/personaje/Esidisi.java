package personaje;

import estado.Quemado;
import item.AnilloBodas;
import movimiento.BlancoMov;
import movimiento.TipoMov;
import movimiento.*;

/**
 * Esidisi — Hombre del Pilar (enemigo IA). Stats base: vida 120, ataque 115,
 * defensa 80, atkEsp 20, defEsp 80, vel 75 Ítem: Anillo de bodas de la muerte
 * (característico)
 */
public class Esidisi extends HombreDelPilar {

	public Esidisi() {
		super("Esidisi", 120, 100, 115, 80, 20, 80, 75, new AnilloBodas());

		// Llamas Erráticas — físico, potencia 100, PP 8, coste 40
		aprenderMovimiento(new MovAtaqueSimple("Llamas Erráticas", BlancoMov.ELEGIDO, TipoMov.FISICO, 100, 1.0, 8, 40,
				0, "Llamaradas de sangre hirviente."));

		// Sangre Hirviente — físico, potencia 85, PP 16, coste 40, 50% quema
		aprenderMovimiento(new MovAtaqueConEfecto("Sangre Hirviente", BlancoMov.ELEGIDO, TipoMov.FISICO, 85, 1.0, 16,
				40, 0, "Sangre a 500°C. 50% de quemar al objetivo.", 0.50, new Quemado()));

		// Llama Protectora — protección con prioridad +4
		aprenderMovimiento(new MovProteccion("Llama Protectora"));

		// Regeneración — recupera 50% energía
		aprenderMovimiento(new MovRecuperarEnergia("Regeneración", 16, 0.5,
				"Regeneración celular: recupera el 50% de la energía. Falla si está al máximo."));
	}
}
