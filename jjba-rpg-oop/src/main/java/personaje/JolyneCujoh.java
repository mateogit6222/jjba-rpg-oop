package personaje;

import item.StoneFree;
import movimiento.BlancoMov;
import movimiento.TipoMov;
import movimiento.*;

/**
 * Jolyne Cujoh — Usuario de Stand. Stats base: vida 80, ataque 80, defensa 80,
 * atkEsp 90, defEsp 90, vel 80 Con Stone Free: ataque +10, defensa +20,
 * ataqueEspecial +25, defensaEspecial +10, velocidad +35
 */
public class JolyneCujoh extends UsuarioDeStand {

	public JolyneCujoh() {
		super("Jolyne Cujoh", 80, 100, 80, 80, 90, 90, 80, new StoneFree());

		// Ora Ora Ora (Jolyne) — especial, potencia 100, PP 8, coste 40
		aprenderMovimiento(new MovAtaqueSimple("Ora Ora Ora", BlancoMov.ELEGIDO, TipoMov.ESPECIAL, 100, 1.0, 8, 40, 0,
				"Lluvia de golpes de Stone Free."));

		// Unravel — físico, potencia 60, PP 24, coste 20, prioridad +1
		aprenderMovimiento(new MovAtaqueSimple("Unravel", BlancoMov.ELEGIDO, TipoMov.FISICO, 60, 1.0, 24, 20, 1,
				"Ataque con hilo de Stone Free."));

		// Blue Web — especial, área (oponentes adyacentes), potencia 50, reduce
		// velocidad
		aprenderMovimiento(new MovAtaqueReduceVelocidad("Blue Web", BlancoMov.OP_ADY, TipoMov.ESPECIAL, 50, 1.0, 24, 20,
				0, "Red de hilo que golpea a oponentes adyacentes y reduce su velocidad."));

		// Cinta de Moebius — recupera 50% energía y aplica curación
		aprenderMovimiento(new MovCintaDeMoebius());
	}
}
