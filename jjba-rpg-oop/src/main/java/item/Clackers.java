package item;

/**
 * Clackers - Ítem característico de Joseph Joestar. Sin bonus de stats; su
 * efecto narrativo está en el movimiento Clacker Volley.
 */
public class Clackers extends Item {

	public Clackers() {
		super("Clackers", TipoItem.ITEM_CARACT,
				"Ítem característico de Joseph. Su poder se canaliza en Clacker Volley.");
	}

	@Override
	public void aplicarEfecto() {
		// Sin efecto pasivo de stats.
	}
}
