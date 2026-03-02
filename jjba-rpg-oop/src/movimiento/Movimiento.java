package movimiento;

import personaje.Personaje;

//Atributos Movimiento

public abstract class Movimiento {
	protected String nombre;
	protected BlancoMov blancoMov;
	protected TipoMov tipoMov;
	protected int potencia;
	protected double precision;
	protected int pp;
	protected int costeEnergia;
	protected String efectoSecundario;
	protected int prioridad;
	protected String efecto;

	// Constructor Movimiento

	public Movimiento(String nombre, BlancoMov blancoMov, TipoMov tipoMov, int potencia, double precision, int pp,
			int costeEnergia, String efectoSecundario, int prioridad, String efecto) {
		super();
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

	// Funciones Movimiento

	public boolean puedeUsarse(Personaje personaje) {

		if (pp > 0 && personaje.getEnergiaActual() > 0) {
			return true;
		} else if (pp < 0) {
			return false;
		} else if (personaje.getEnergiaActual() < 0) {
			return false;
		} else {
			return false;
		}
	}

}
