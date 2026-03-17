package item;

/**
 * Stone Free - Stand CA de Jolyne Cujoh. Bonus: ataque +10, defensa +20,
 * ataqueEspecial +25, defensaEspecial +10, velocidad +35
 */
public class StoneFree extends Item {

	public StoneFree() {
		super("Stone Free", TipoItem.STAND_CA,
				"Bonificación fija: ataque +10, defensa +20, ataqueEspecial +25, defensaEspecial +10, velocidad +35");
	}

	@Override
	public void aplicarEfecto() {
		if (portador == null)
			return;
		portador.setAtaque(portador.getAtaque() + 10);
		portador.setDefensa(portador.getDefensa() + 20);
		portador.setAtaqueEspecial(portador.getAtaqueEspecial() + 25);
		portador.setDefensaEspecial(portador.getDefensaEspecial() + 10);
		portador.setVelocidad(portador.getVelocidad() + 35);
	}
}
