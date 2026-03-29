package estado;

import personaje.Personaje;

//Atributos Estado

/**
 * Clase abstracta que representa una alteración de estado (buff, debuff, o
 * condición de estado). Aplica efectos al personaje a lo largo de los turnos de
 * combate.
 */
public abstract class Estado {
	protected String nombre;
	protected TipoEstado tipoEstado;
	protected TipoStat tipoStat;
	protected int turnosRestantes;
	protected boolean apilable;

	/** Constante para indicar que el estado no desaparece por paso del tiempo. */
	public static final int DURACION_PERMANENTE = -1;

	// Constructor Estado

	/**
	 * Constructor base para crear una alteración de estado.
	 *
	 * @param nombre          Nombre de la alteración (ej. "Envenenado",
	 *                        "Sangrado").
	 * @param tipoEstado      Clasificación de la alteración.
	 * @param tipoStat        La estadística a la que afecta principalmente, si
	 *                        aplica.
	 * @param turnosRestantes Número de turnos antes de que el estado expire
	 *                        automáticamente.
	 * @param apilable        Indica si el mismo estado puede aplicarse múltiples
	 *                        veces al mismo objetivo.
	 */
	public Estado(String nombre, TipoEstado tipoEstado, TipoStat tipoStat, int turnosRestantes, boolean apilable) {
		this.nombre = nombre;
		this.tipoEstado = tipoEstado;
		this.tipoStat = tipoStat;
		this.turnosRestantes = turnosRestantes;
		this.apilable = apilable;
	}

	// Getters Estado

	public String getNombre() {
		return nombre;
	}

	public TipoEstado getTipoEstado() {
		return tipoEstado;
	}

	public TipoStat getTipoStat() {
		return tipoStat;
	}

	public int getTurnosRestantes() {
		return turnosRestantes;
	}

	public boolean isApilable() {
		return apilable;
	}

	// Setters Estado

	public void setTurnosRestantes(int turnosRestantes) {
		this.turnosRestantes = turnosRestantes;
	}

	// Funciones Estado

	/**
	 * Efecto disparado en el momento exacto en el que el estado es asignado a un
	 * personaje.
	 *
	 * @param objetivo El personaje que recibe el estado.
	 */
	public abstract void alAplicar(Personaje objetivo);

	/**
	 * Efecto disparado en cada ronda de combate mientras el estado siga activo.
	 *
	 * @param objetivo El personaje que sufre el efecto.
	 */
	public abstract void porTurno(Personaje objetivo);

	/**
	 * Efecto disparado en el momento en que el estado se disipa o elimina del
	 * personaje. Útil para revertir modificaciones de estadísticas.
	 *
	 * @param objetivo El personaje del cual se elimina el estado.
	 */
	public abstract void alEliminar(Personaje objetivo);

}
