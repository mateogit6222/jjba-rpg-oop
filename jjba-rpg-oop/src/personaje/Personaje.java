package personaje;

import java.util.ArrayList;
import java.util.List;

import estado.Estado;
import item.Item;
import movimiento.BlancoMov;
import movimiento.Movimiento;
import movimiento.TipoMov;

//Atributos Personaje

public abstract class Personaje {
	protected String nombre;
	protected TipoPj tipopj;
	protected int vidaMax;
	protected int vidaActual;
	protected int energiaMax;
	protected int energiaActual;
	protected int ataque;
	protected int defensa;
	protected int ataqueEspecial;
	protected int defensaEspecial;
	protected int velocidad;
	protected boolean estaProtegido;
	// protected boolean estaVivo;
	protected List<Estado> estadosActivos;
	protected Item item;
	protected List<Movimiento> movimientos;

	// Constructor Personaje

	public Personaje(String nombre, TipoPj tipopj, int vidaMax, int vidaActual, int energiaMax, int energiaActual,
			int ataque, int defensa, int ataqueEspecial, int defensaEspecial, int velocidad, boolean estaProtegido,
			Item item) {
		this.nombre = nombre;
		this.tipopj = tipopj;
		this.vidaMax = vidaMax;
		this.vidaActual = vidaActual;
		this.energiaMax = energiaMax;
		this.energiaActual = energiaActual;
		this.ataque = ataque;
		this.defensa = defensa;
		this.ataqueEspecial = ataqueEspecial;
		this.defensaEspecial = defensaEspecial;
		this.velocidad = velocidad;
		this.estaProtegido = estaProtegido;
		this.item = item;

		this.estadosActivos = new ArrayList();
		this.movimientos = new ArrayList();
	}

	// Getters Personaje
	
	public int getEnergiaActual() {
		return energiaActual;
	}

	// Funciones Personaje

	public boolean estaVivo() {
		/*
		 * if (vidaActual > 0) { return true; } else { return false; }
		 */

		return vidaActual > 0 ? true : false; // Andrés no preguntes que me lo dijiste tú

	}

	public void infoPersonaje() {
		System.out.println("\t Características /n" + "nombre: " + this.nombre + "/n" + "tipoPj: " + this.tipopj + "/n"
				+ "vidaMax: " + this.vidaMax + "/n" + "vidaActual: " + this.vidaActual + "/n" + "energiaMax: "
				+ this.energiaMax + "/n" + "energiaActual: " + this.energiaActual + "/n" + "ataque: " + this.ataque
				+ "/n" + "defensa: " + this.defensa + "/n" + "ataqueEspecial: " + this.ataqueEspecial + "/n"
				+ "defensaEspecial: " + this.defensaEspecial + "/n" + "velocidad: " + this.velocidad + "/n" + "item: "
				+ this.item);

	}

	public void gastarEnergia() {

	}

	public void calcularDanio() {

	}

	public void recibirDanio() {

	}

	public void curar() {

	}

	public void aplicarEstado() {

	}

	public void procesarEstados() {

	}

}
