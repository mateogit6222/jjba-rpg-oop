package item;

/**
 * Gold Experience - Stand CA de Giorno Giovanna. Bonus: ataque +10, defensa
 * +10, ataqueEspecial +40, velocidad +40
 */
public class GoldExperience extends Item {

	public GoldExperience() {
		super("Gold Experience", TipoItem.STAND_CA,
				"Bonificación fija: ataque +10, defensa +10, ataqueEspecial +40, velocidad +40");
	}

	@Override
	public void aplicarEfecto() {
		if (portador == null)
			return;
		portador.setAtaque(portador.getAtaque() + 10);
		portador.setDefensa(portador.getDefensa() + 10);
		portador.setAtaqueEspecial(portador.getAtaqueEspecial() + 40);
		portador.setVelocidad(portador.getVelocidad() + 40);
	}
}
