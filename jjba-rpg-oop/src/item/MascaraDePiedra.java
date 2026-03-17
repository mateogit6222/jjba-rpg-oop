package item;

/**
 * Máscara de Piedra - Ítem potenciador de Kars. Bonus: vidaMax +60
 */
public class MascaraDePiedra extends Item {

	public MascaraDePiedra() {
		super("Máscara de piedra", TipoItem.ITEM_POT, "Bonificación fija: vidaMax +60");
	}

	@Override
	public void aplicarEfecto() {
		if (portador == null)
			return;
		portador.setVidaMax(portador.getVidaMax() + 60);
		// También aumenta la vida actual al equiparse (empieza con vida completa)
		portador.curar(60);
	}
}
