package personaje;

import item.PiedraRojaDeAja;
import movimiento.BlancoMov;
import movimiento.TipoMov;
import movimiento.*;

/**
 * Lisa Lisa — Usuario de Hamon. Stats base: vida 85, ataque 110, defensa 80,
 * atkEsp 40, defEsp 80, vel 105 Con La Piedra Roja de Aja: ataque +25,
 * ataqueEspecial +25
 */
public class LisaLisa extends UsuarioDeHamon {

	public LisaLisa() {
		super("Lisa Lisa", 85, 100, 110, 80, 40, 80, 105, new PiedraRojaDeAja());

		// Hamon Finger — físico, potencia 120, PP 8, coste 50
		aprenderMovimiento(new MovAtaqueSimple("Hamon Finger", BlancoMov.ELEGIDO, TipoMov.FISICO, 120, 1.0, 8, 50, 0,
				"Dedo cargado de Hamon."));

		// Hamon Touch — físico, potencia 20, PP 32, coste 20, sube velocidad usuario
		aprenderMovimiento(new MovAtaqueSubeVelocidad("Hamon Touch", BlancoMov.ELEGIDO, TipoMov.FISICO, 20, 1.0, 32, 20,
				0, "Toque de Hamon. Sube la velocidad del usuario en un nivel."));

		// Snake Muffler — protección con prioridad +4
		aprenderMovimiento(new MovProteccion("Snake Muffler"));

		// Respiración Hamon — recupera 100% energía
		aprenderMovimiento(new MovRecuperarEnergia("Respiración Hamon", 16, 1.0,
				"Recupera el 100% de la energía. Falla si la energía está al máximo."));
	}
}
