package item;

/**
 * La Piedra Roja de Aja - Ítem potenciador de Lisa Lisa. Bonus: ataque +25,
 * ataqueEspecial +25
 */
public class PiedraRojaDeAja extends Item {

	public PiedraRojaDeAja() {
		super("La Piedra Roja de Aja", TipoItem.ITEM_POT, "Bonificación fija: ataque +25, ataqueEspecial +25");
	}

	@Override
	public void aplicarEfecto() {
		if (portador == null)
			return;
		portador.setAtaque(portador.getAtaque() + 25);
		portador.setAtaqueEspecial(portador.getAtaqueEspecial() + 25);
	}
}
