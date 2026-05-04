package personaje;

import estado.Envenenado;
import item.AnilloBodas;
import movimiento.BlancoMov;
import movimiento.TipoMov;
import movimiento.*;

/**
 * Wamuu — Hombre del Pilar (enemigo IA). Stats base: vida 120, ataque 110,
 * defensa 80, atkEsp 20, defEsp 80, vel 70 Ítem: Anillo de bodas de la muerte
 * (característico, sin bonus stats)
 */
public class Wamuu extends HombreDelPilar {

	public Wamuu() {
		super("Wamuu", 120, 100, 110, 80, 20, 80, 70, new AnilloBodas());

		// Kamizuna Arashi — físico, potencia 90, PP 16, coste 30, 30% envenena
		aprenderMovimiento(new MovAtaqueConEfecto("Kamizuna Arashi", BlancoMov.ELEGIDO, TipoMov.FISICO, 90, 1.0, 16, 30,
				0, "Tormenta de arena divina. 30% de envenenar al objetivo.", 0.30, new Envenenado()));

		// Fūjin Hō — físico, potencia 90, PP 16, coste 30
		aprenderMovimiento(new MovAtaqueSimple("Fūjin Hō", BlancoMov.ELEGIDO, TipoMov.FISICO, 90, 1.0, 16, 30, 0,
				"Técnica del Dios del Viento."));

		// Lente Atmosférica — protección con prioridad +4
		aprenderMovimiento(new MovProteccion("Lente Atmosférica"));

		// Regeneración — recupera 50% energía
		aprenderMovimiento(new MovRecuperarEnergia("Regeneración", 16, 0.5,
				"Regeneración celular: recupera el 50% de la energía. Falla si está al máximo."));
	}
}
