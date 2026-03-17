package item;

/**
 * Star Platinum - Stand CA de Jotaro Kujo. Bonus: ataque +10, ataqueEspecial
 * +45, velocidad +45
 */
public class StarPlatinum extends Item {

	public StarPlatinum() {
		super("Star Platinum", TipoItem.STAND_CA, "Bonificación fija: ataque +10, ataqueEspecial +45, velocidad +45");
	}

	@Override
	public void aplicarEfecto() {
		if (portador == null)
			return;
		portador.setAtaque(portador.getAtaque() + 10);
		portador.setAtaqueEspecial(portador.getAtaqueEspecial() + 45);
		portador.setVelocidad(portador.getVelocidad() + 45);
	}
}
