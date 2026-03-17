package item;

/**
 * Copa de Vino - Ítem característico de William Zeppeli.
 */
public class CopaDeVino extends Item {

	public CopaDeVino() {
		super("Copa de Vino", TipoItem.ITEM_CARACT, "Ítem característico de Zeppeli.");
	}

	@Override
	public void aplicarEfecto() {
		// Sin efecto pasivo de stats.
	}
}
