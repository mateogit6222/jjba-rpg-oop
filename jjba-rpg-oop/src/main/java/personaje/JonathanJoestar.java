package personaje;

import item.LuckAndPluck;
import movimiento.BlancoMov;
import movimiento.TipoMov;
import movimiento.*;
import estado.Quemado;

/**
 * Jonathan Joestar — Usuario de Hamon. Stats base: vida 100, ataque 90, defensa
 * 90, atkEsp 40, defEsp 90, vel 90 Ítem: Luck & Pluck (aplica anticura al
 * enemigo — gestionado desde Combate)
 */
public class JonathanJoestar extends UsuarioDeHamon {

	public JonathanJoestar() {
		super("Jonathan Joestar", 100, 100, 90, 90, 40, 90, 90, new LuckAndPluck());

		// Sunlight Yellow Overdrive — físico, daño x2 vs Pilar/No Muerto, PP 8, coste
		// 50
		aprenderMovimiento(new MovAtaqueAntiPilar("Sunlight Yellow Overdrive", BlancoMov.ELEGIDO, TipoMov.FISICO, 120,
				1.0, 8, 50, "Doble de daño a Hombres del Pilar y No Muertos.", 0, "El latido del sol."));

		// Zoom Punch — físico, potencia 40, PP 48, coste 10, prioridad +1
		aprenderMovimiento(new MovAtaqueSimple("Zoom Punch", BlancoMov.ELEGIDO, TipoMov.FISICO, 40, 1.0, 48, 10, 1,
				"Puñetazo extendido con Hamon."));

		// Scarlet Overdrive — estado puro, aplica quemado, precisión 85%
		// Usamos MovAtaqueConEfecto con potencia 0: calcularDanio devuelve 0 para
		// ESTADO,
		// pero la precisión (0.85) se gestiona en Movimiento.usarMov() antes de llamar
		// a ejecutarEfecto.
		aprenderMovimiento(new MovAtaqueConEfecto("Scarlet Overdrive", BlancoMov.ELEGIDO, TipoMov.ESTADO, 0, 0.85, 8,
				20, 0, "Aplica el estado quemado si acierta.", 1.0, new Quemado()));

		// Respiración Hamon — recupera 100% energía
		aprenderMovimiento(new MovRecuperarEnergia("Respiración Hamon", 16, 1.0,
				"Recupera el 100% de la energía. Falla si la energía está al máximo."));
	}

	/** Devuelve el ítem como LuckAndPluck para poder usar aplicarAnticuraA(). */
	public LuckAndPluck getLuckAndPluck() {
		return (LuckAndPluck) getItem();
	}
}
