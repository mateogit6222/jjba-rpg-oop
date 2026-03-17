package item;

import personaje.Personaje;
import estado.Anticura;

/**
 * Luck & Pluck - Ítem potenciador de Jonathan Joestar. Aplica el estado
 * Anticura al portador enemigo (al inicio del combate se aplica al rival). Aquí
 * aplicarEfecto() aplica anticura al propio portador como estado pasivo de
 * campo. La lógica real de aplicarlo al enemigo se gestiona en Combate.
 */
public class LuckAndPluck extends Item {

	public LuckAndPluck() {
		super("Luck & Pluck", TipoItem.ITEM_POT, "Aplica el estado anticura al portador enemigo.");
	}

	@Override
	public void aplicarEfecto() {
		// La aplicación al enemigo se gestiona desde Combate al inicio del combate.
		// No hay bonus de stats propios.
	}

	/**
	 * Método auxiliar para que Combate pueda aplicar anticura al objetivo correcto.
	 */
	public void aplicarAnticuraA(Personaje objetivo) {
		objetivo.aplicarEstado(new Anticura());
	}
}
