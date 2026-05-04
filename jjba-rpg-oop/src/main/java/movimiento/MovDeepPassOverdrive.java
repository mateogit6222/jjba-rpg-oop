package movimiento;

import estado.TipoStat;
import personaje.Personaje;

import java.util.List;

/**
 * Deep Pass Overdrive (William Zeppeli): El usuario se debilita (vida a 0) y
 * revive a un aliado debilitado con el 50% de vida, aumentando además su
 * ataque, ataqueEspecial y velocidad en un nivel (+1). No se puede usar si no
 * hay ningún aliado debilitado.
 *
 * NOTA: este movimiento necesita acceso al equipo aliado; se inyecta antes de
 * usarlo mediante setEquipoAliado().
 */
public class MovDeepPassOverdrive extends Movimiento {

	private List<Personaje> equipoAliado;
	private Personaje objetivoRevivir; // se setea antes de ejecutar desde Combate

	public MovDeepPassOverdrive() {
		super("Deep Pass Overdrive", BlancoMov.ELEGIDO, TipoMov.ESTADO, 0, Movimiento.PRECISION_INFALIBLE, 1, 100, null,
				0,
				"El usuario se debilita y revive a un aliado con el 50% de vida. Le sube ataque, ataqueEspecial y velocidad en 1 nivel.");
	}

	public void setEquipoAliado(List<Personaje> equipoAliado) {
		this.equipoAliado = equipoAliado;
	}

	public void setObjetivoRevivir(Personaje objetivo) {
		this.objetivoRevivir = objetivo;
	}

	/** Comprueba si hay al menos un aliado debilitado en el equipo. */
	public boolean hayAliadoDebilitado() {
		if (equipoAliado == null)
			return false;
		for (Personaje p : equipoAliado) {
			if (!p.estaVivo() && p != caster)
				return true;
		}
		return false;
	}

	@Override
	public void ejecutarEfecto(Personaje objetivo) {
		if (objetivoRevivir == null || objetivoRevivir.estaVivo()) {
			System.out.println("¡Deep Pass Overdrive no tiene un objetivo válido!");
			return;
		}

		// El usuario se sacrifica
		System.out.println(caster.getNombre() + " entrega toda su energía vital...");
		caster.recibirDanio(caster.getVidaMax()); // fuerza vida a 0

		// Revivir al aliado con 50% de vida
		int vidaRecuperada = objetivoRevivir.getVidaMax() / 2;
		objetivoRevivir.curar(vidaRecuperada);
		System.out.println(objetivoRevivir.getNombre() + " vuelve a la batalla con " + vidaRecuperada + " HP.");

		// Buffs al aliado revivido
		objetivoRevivir.modificarNivelStat(TipoStat.ATAQUE, 1);
		objetivoRevivir.modificarNivelStat(TipoStat.ATAQUE_ESPECIAL, 1);
		objetivoRevivir.modificarNivelStat(TipoStat.VELOCIDAD, 1);
	}
}
