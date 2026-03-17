package item;

/**
 * Anillo de Bodas de la Muerte - Ítem característico de Wamuu y Esidisi.
 */
public class AnilloBodas extends Item {

	public AnilloBodas() {
		super("Anillo de bodas de la muerte", TipoItem.ITEM_CARACT, "Ítem característico de los Hombres del Pilar.");
	}

	@Override
	public void aplicarEfecto() {
		// Sin efecto pasivo de stats.
	}
}
