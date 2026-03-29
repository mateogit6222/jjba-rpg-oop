package personaje;

import movimiento.Movimiento;
import movimiento.TipoMov;
import movimiento.MovProteccion;
import item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase abstracta que representa a los enemigos principales controlados por la
 * IA. Define el comportamiento automático en combate, evaluando el estado de la
 * partida para priorizar la supervivencia o maximizar el daño.
 */
public abstract class HombreDelPilar extends Personaje {

	private static final Random rng = new Random();
	private int turnosSinProteger = 0;

	// Se inyectan desde Combate antes de cada turno de IA
	private List<Personaje> rivalesVivos = new ArrayList<>();
	private Personaje objetivoActual = null;

	/**
	 * Constructor base para crear un Hombre del Pilar. Inicializa al personaje con
	 * la clasificación TipoPj.H_PILAR.
	 *
	 * @param nombre          Nombre del enemigo.
	 * @param vidaMax         Puntos de vida máximos.
	 * @param energiaMax      Puntos de energía máximos.
	 * @param ataque          Ataque físico base.
	 * @param defensa         Defensa física base.
	 * @param ataqueEspecial  Ataque especial base.
	 * @param defensaEspecial Defensa especial base.
	 * @param velocidad       Estadística que determina el orden en la ronda.
	 * @param item            Objeto que el enemigo lleva equipado (puede ser null).
	 */
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
	 * Evalúa la situación del combate y selecciona el movimiento óptimo para la IA.
	 * Sigue un árbol de decisión predefinido: 1. Uso táctico de protección si la
	 * salud es >= 50% y lleva más de 3 turnos sin protegerse. 2. Prioridad absoluta
	 * al ataque que genere el mayor daño base. 3. Uso de movimientos de estado como
	 * recurso secundario si no hay ataques viables.
	 *
	 * @return El movimiento seleccionado para el turno, o null si no tiene opciones
	 *         disponibles.
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
	 * Selecciona estratégicamente al objetivo del ataque de la IA. Focaliza el daño
	 * en el oponente más vulnerable (el que tenga menos vida actual) para intentar
	 * reducir la superioridad numérica del jugador rápidamente.
	 *
	 * @return El personaje rival seleccionado como objetivo, o null si no hay
	 *         objetivos viables.
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
