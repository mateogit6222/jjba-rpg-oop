package item;

/**
 * Crazy Diamond - Stand CA de Josuke Higashikata. Bonus: ataque +10, defensa
 * +40, ataqueEspecial +15, defensaEspecial +30, velocidad +5
 */
public class CrazyDiamond extends Item {

	public CrazyDiamond() {
		super("Crazy Diamond", TipoItem.STAND_CA,
				"Bonificación fija: ataque +10, defensa +40, ataqueEspecial +15, defensaEspecial +30, velocidad +5");
	}

	@Override
	public void aplicarEfecto() {
		if (portador == null)
			return;
		portador.setAtaque(portador.getAtaque() + 10);
		portador.setDefensa(portador.getDefensa() + 40);
		portador.setAtaqueEspecial(portador.getAtaqueEspecial() + 15);
		portador.setDefensaEspecial(portador.getDefensaEspecial() + 30);
		portador.setVelocidad(portador.getVelocidad() + 5);
	}
}
