package personaje;

import item.CopaDeVino;
import movimiento.BlancoMov;
import movimiento.TipoMov;
import movimiento.*;

/**
 * William Anthonio Zeppeli — Usuario de Hamon. Stats base: vida 95, ataque 105,
 * defensa 80, atkEsp 40, defEsp 80, vel 100 Ítem: Copa de Vino (sin bonus de
 * stats)
 */
public class WilliamZeppeli extends UsuarioDeHamon {

	private MovDeepPassOverdrive deepPassOverdrive;

	public WilliamZeppeli() {
		super("William Anthonio Zeppeli", 95, 100, 105, 80, 40, 80, 100, new CopaDeVino());

		// Tornado Overdrive — físico, potencia 120, PP 8, coste 50
		aprenderMovimiento(new MovAtaqueSimple("Tornado Overdrive", BlancoMov.ELEGIDO, TipoMov.FISICO, 120, 1.0, 8, 50,
				0, "Torbellino de Hamon."));

		// Hamon Cutter — físico, potencia 90, PP 16, coste 30
		aprenderMovimiento(new MovAtaqueSimple("Hamon Cutter", BlancoMov.ELEGIDO, TipoMov.FISICO, 90, 1.0, 16, 30, 0,
				"Corte de energía Hamon."));

		// Deep Pass Overdrive — sacrificio para revivir aliado
		deepPassOverdrive = new MovDeepPassOverdrive();
		aprenderMovimiento(deepPassOverdrive);

		// Respiración Hamon — recupera 100% energía
		aprenderMovimiento(new MovRecuperarEnergia("Respiración Hamon", 16, 1.0,
				"Recupera el 100% de la energía. Falla si la energía está al máximo."));
	}

	public MovDeepPassOverdrive getDeepPassOverdrive() {
		return deepPassOverdrive;
	}
}
