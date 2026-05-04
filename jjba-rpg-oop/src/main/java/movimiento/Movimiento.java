package movimiento;

import java.util.Random;

import personaje.Personaje;

//Atributos Movimiento

/**
 * Clase abstracta base para todos los ataques y habilidades del juego. Define
 * la potencia, precisión, coste y reglas de objetivo de cada acción.
 */
public abstract class Movimiento {
	protected String nombre;
	protected BlancoMov blancoMov;
	protected TipoMov tipoMov;
	protected int potencia;
	protected double precision; // 0.0 – 1.0 (ej: 1.0 = 100%, 0.85 = 85%)
	protected int pp; // Usos restantes del movimiento
	protected int costeEnergia;
	protected String efectoSecundario;
	protected int prioridad;
	protected String efecto;
	protected Personaje caster;

	/** Constante que indica que un movimiento nunca falla. */
	public static final double PRECISION_INFALIBLE = -1.0;

	// Constructor Movimiento

	/**
	 * Crea un nuevo movimiento con sus atributos definidos.
	 *
	 * @param nombre           El nombre de la técnica o ataque.
	 * @param blancoMov        A quién afecta el movimiento (usuario, un rival,
	 *                         todos los adyacentes, etc.).
	 * @param tipoMov          Clasificación del movimiento (Físico, Especial o
	 *                         Estado).
	 * @param potencia         Poder base utilizado para el cálculo de daño (0 para
	 *                         movimientos de estado).
	 * @param precision        Probabilidad de acierto (0.0 a 1.0, o
	 *                         PRECISION_INFALIBLE).
	 * @param pp               Puntos de Poder (usos máximos).
	 * @param costeEnergia     Cantidad de energía que el usuario debe gastar para
	 *                         ejecutarlo.
	 * @param efectoSecundario Descripción del efecto secundario, si lo tiene.
	 * @param prioridad        Nivel de prioridad de ejecución (mayor prioridad se
	 *                         ejecuta antes en la ronda).
	 * @param efecto           Descripción general de lo que hace el movimiento.
	 */
	public Movimiento(String nombre, BlancoMov blancoMov, TipoMov tipoMov, int potencia, double precision, int pp,
			int costeEnergia, String efectoSecundario, int prioridad, String efecto) {
		this.nombre = nombre;
		this.blancoMov = blancoMov;
		this.tipoMov = tipoMov;
		this.potencia = potencia;
		this.precision = precision;
		this.pp = pp;
		this.costeEnergia = costeEnergia;
		this.efectoSecundario = efectoSecundario;
		this.prioridad = prioridad;
		this.efecto = efecto;
	}

	// Getters Movimiento

	public String getNombre() {
		return nombre;
	}

	public TipoMov getTipoMov() {
		return tipoMov;
	}

	public BlancoMov getBlancoMov() {
		return blancoMov;
	}

	public int getPotencia() {
		return potencia;
	}

	public double getPrecision() {
		return precision;
	}

	public int getPp() {
		return pp;
	}

	public int getCosteEnergia() {
		return costeEnergia;
	}

	public int getPrioridad() {
		return prioridad;
	}

	public String getEfecto() {
		return efecto;
	}

	public String getEfectoSecundario() {
		return efectoSecundario;
	}

	// Setters Movimiento

	public void setPersonaje(Personaje personaje) {
		this.caster = personaje;
	}

	// Funciones Movimiento

	/**
	 * Comprueba si el movimiento puede ser ejecutado por el usuario actual.
	 *
	 * @return true si quedan usos (PP) y el usuario tiene suficiente energía, false
	 *         en caso contrario.
	 */
	public boolean puedeUsarseMov() {

		if (this.pp > 0 && caster.getEnergiaActual() >= this.costeEnergia) {
			return true;
		} else {
			return false;
		}
		// Verifica que el personaje tiene pp y energiaActual suficiente.
	}

	/**
	 * Intenta ejecutar el movimiento sobre el objetivo indicado, comprobando costes
	 * y precisión. Si el movimiento acierta, invoca el método abstracto
	 * ejecutarEfecto().
	 *
	 * @param objetivo El personaje que recibirá el efecto o daño del movimiento.
	 */
	public void usarMov(Personaje objetivo) {
		if (!puedeUsarseMov()) {
			if (this.pp <= 0)
				System.out.println(nombre + " no tiene pp restantes.");
			else
				System.out.println(caster.getNombre() + " no tiene energía suficiente (" + caster.getEnergiaActual()
						+ "/" + this.costeEnergia + ").");
			return;
		}

		this.pp--;
		caster.gastarEnergia(this.costeEnergia);

		if (this.precision != PRECISION_INFALIBLE && new Random().nextDouble() > this.precision) {
			System.out.println(caster.getNombre() + " usó " + nombre + ", ¡pero falló!");
			return;
		}

		System.out.println(caster.getNombre() + " usó " + nombre + ".");
		ejecutarEfecto(objetivo);
	}

	/**
	 * Lógica específica del efecto del movimiento. A implementar por las subclases
	 * (ej. aplicar daño, curar, alterar estados).
	 *
	 * @param objetivo El personaje sobre el que recae el efecto.
	 */
	public abstract void ejecutarEfecto(Personaje objetivo);

}
