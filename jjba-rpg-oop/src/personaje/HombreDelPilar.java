package personaje;

import movimiento.Movimiento;
import movimiento.TipoMov;
import movimiento.MovProteccion;
import item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Subclase abstracta para Hombres del Pilar. elegirAccion() es la IA: elige el
 * movimiento más dañino disponible, o usa protección si la vida está alta y
 * lleva varios turnos atacando.
 */
public abstract class HombreDelPilar extends Personaje {

	private static final Random rng = new Random();
	private int turnosSinProteger = 0;

	// Se inyectan desde Combate antes de cada turno de IA
	private List<Personaje> rivalesVivos = new ArrayList<>();
	private Personaje objetivoActual = null;

	public HombreDelPilar(String nombre, int vidaMax, int energiaMax, int ataque, int defensa, int ataqueEspecial,
			int defensaEspecial, int velocidad, Item item) {
		super(nombre, TipoPj.H_PILAR, vidaMax, energiaMax, ataque, defensa, ataqueEspecial, defensaEspecial, velocidad,
				false, item);
	}

	public void setRivalesVivos(List<Personaje> rivales) {
		this.rivalesVivos = rivales;
	}

	public Personaje getObjetivoActual() {
		return objetivoActual;
	}

	@Override
	public void elegirAccion() {
		// La acción real la decide elegirMovimientoIA() + seleccionarObjetivoIA()
		// que son llamados desde Combate. Este método es solo el hook abstracto.
	}

	/**
	 * La IA elige un movimiento según esta lógica: 1. Si la vida es >= 50% y lleva
	 * 3+ turnos sin protegerse → usar protección. 2. Si hay movimientos de daño
	 * usables → usar el de mayor potencia. 3. Si no hay daño disponible → usar el
	 * primer movimiento de estado usable. 4. Si nada es usable → null (pasa turno).
	 */
	public Movimiento elegirMovimientoIA() {
		List<Movimiento> movs = getMovimientos();
		List<Movimiento> ataques = new ArrayList<>();
		List<Movimiento> estados = new ArrayList<>();
		MovProteccion proteccion = null;

		for (Movimiento m : movs) {
			if (!m.puedeUsarseMov())
				continue;
			if (m instanceof MovProteccion) {
				proteccion = (MovProteccion) m;
			} else if (m.getTipoMov() != TipoMov.ESTADO) {
				ataques.add(m);
			} else {
				estados.add(m);
			}
		}

		// Usar protección si lleva varios turnos sin usarla y tiene vida suficiente
		double porcentajeVida = (double) getVidaActual() / getVidaMax();
		if (proteccion != null && turnosSinProteger >= 3 && porcentajeVida >= 0.5) {
			turnosSinProteger = 0;
			System.out.println("  → " + nombre + " decide protegerse.");
			return proteccion;
		}

		// Elegir el ataque de mayor potencia disponible
		if (!ataques.isEmpty()) {
			turnosSinProteger++;
			Movimiento mejor = ataques.get(0);
			for (Movimiento m : ataques) {
				if (m.getPotencia() > mejor.getPotencia())
					mejor = m;
			}
			return mejor;
		}

		// Usar estado si no hay ataques
		if (!estados.isEmpty()) {
			return estados.get(rng.nextInt(estados.size()));
		}

		return null; // Sin movimientos disponibles
	}

	/**
	 * La IA selecciona objetivo: el rival vivo con menos vida (más cerca de caer).
	 */
	public Personaje seleccionarObjetivoIA() {
		if (rivalesVivos == null || rivalesVivos.isEmpty())
			return null;
		Personaje objetivo = null;
		for (Personaje p : rivalesVivos) {
			if (p.estaVivo()) {
				if (objetivo == null || p.getVidaActual() < objetivo.getVidaActual()) {
					objetivo = p;
				}
			}
		}
		objetivoActual = objetivo;
		return objetivo;
	}
}
