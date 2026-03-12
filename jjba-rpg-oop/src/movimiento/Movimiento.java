package movimiento;

import java.util.Random;

import personaje.Personaje;

//Atributos Movimiento

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

	public static final double PRECISION_INFALIBLE = -1.0;

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

	public boolean puedeUsarseMov() {

		if (this.pp > 0 && caster.getEnergiaActual() >= this.costeEnergia) {
			return true;
		} else {
			return false;
		}
		// Verifica que el personaje tiene pp y energiaActual suficiente.
	}

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

	public abstract void ejecutarEfecto(Personaje objetivo);

}
