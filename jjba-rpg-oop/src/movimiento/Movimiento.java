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

	public boolean puedeUsarseMov(Personaje caster) {

		if (this.pp > 0 && caster.getEnergiaActual() > 0) {
			return true;
		} else if (this.pp < 0) {
			return false;
		} else if (caster.getEnergiaActual() < 0) {
			return false;
		} else {
			return false;
		}
		//Verifica que el personaje tiene pp y energiaActual suficiente.
	}

	public void usarMov(Personaje caster, Personaje objetivo) {
		
	}

}
