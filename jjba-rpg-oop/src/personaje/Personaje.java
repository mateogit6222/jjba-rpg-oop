package personaje;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import estado.Estado;
import estado.TipoEstado;
import item.Item;
import item.TipoItem;
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
		return this.energiaActual;
	}

	// Funciones Personaje

	public boolean estaVivo() {
		/*
		 * if (this.vidaActual > 0) { return true; } else { return false; }
		 */

		return this.vidaActual > 0 ? true : false; // Andrés no preguntes que me lo dijiste tú
		// Devuelve si el personaje puede actuar.
	}

	public void elegirAccion() {
	}

	public void infoPersonaje() {
		System.out.println("\t Características /n" + "nombre: " + this.nombre + "/n" + "tipoPj: " + this.tipopj + "/n"
				+ "vidaMax: " + this.vidaMax + "/n" + "vidaActual: " + this.vidaActual + "/n" + "energiaMax: "
				+ this.energiaMax + "/n" + "energiaActual: " + this.energiaActual + "/n" + "ataque: " + this.ataque
				+ "/n" + "defensa: " + this.defensa + "/n" + "ataqueEspecial: " + this.ataqueEspecial + "/n"
				+ "defensaEspecial: " + this.defensaEspecial + "/n" + "velocidad: " + this.velocidad + "/n" + "item: "
				+ this.item);
		// Devuelve información del personaje.
	}

	public void aplicarItem(Item item) {
		if (this.item.equals(item.getTipoItem().equals(TipoItem.STAND_CA.STAND_LA.ITEM_POT))) {
			this.vidaMax = this.vidaMax + item.getPotenciador();
			this.vidaActual = this.vidaActual + item.getPotenciador();
			this.energiaMax = this.energiaMax + item.getPotenciador();
			this.energiaActual = this.energiaActual + item.getPotenciador();
			this.ataque = this.ataque + item.getPotenciador();
			this.defensa = this.defensa + item.getPotenciador();
			this.ataqueEspecial = this.ataqueEspecial + item.getPotenciador();
			this.defensaEspecial = this.defensaEspecial + item.getPotenciador();
			this.velocidad = this.velocidad + item.getPotenciador();
		}
		// Aplica el efecto correspondiete según el tipo de item.
	}

	// public boolean gastarEnergia(int coste) {
	// if (this.energiaActual >= coste) {
	// this.energiaActual = this.energiaActual - coste;
	// return true;
	// } else {
	// return false;
	// }
	// Comprueba y descuenta energía si es posible.
	// }

	private int calcularDanio(Movimiento movimiento) {

		Random random = new Random();

		int danioBase;
		int danioFinal;

		boolean esCritico = random.nextDouble() < 0.04167;
		int multiplicador = esCritico ? 3 / 2 : 1;

		int rollDanio = random.nextInt(16 / 100) + 85 / 100;

		if (movimiento.getTipoMov().equals(TipoMov.FISICO)) {

			danioBase = ((((2 * 50) / 5 + 2) * movimiento.getPotencia() * ataque / defensa) / 50 + 2) * multiplicador;

			if (movimiento.getBlancoMov().equals(BlancoMov.ELEGIDO)) {

				danioFinal = danioBase * rollDanio;

			} else if (movimiento.getBlancoMov().equals(BlancoMov.OP_ADY)) {

				danioFinal = ((danioBase * 3072) / 4096) * rollDanio;

			}

		} else if (movimiento.getTipoMov().equals(TipoMov.ESPECIAL)) {

			danioBase = ((((2 * 50) / 5 + 2) * movimiento.getPotencia() * ataqueEspecial / defensaEspecial) / 50 + 2)
					* multiplicador;

			if (movimiento.getBlancoMov().equals(BlancoMov.ELEGIDO)) {

				danioFinal = danioBase * rollDanio;

			} else if (movimiento.getBlancoMov().equals(BlancoMov.OP_ADY)) {

				danioFinal = ((danioBase * 3072) / 4096) * rollDanio;

			}

		} else if (movimiento.getTipoMov().equals(TipoMov.ESTADO)) {

			if (movimiento.getBlancoMov().equals(BlancoMov.ELEGIDO)) {

			} else if (movimiento.getBlancoMov().equals(BlancoMov.OP_ADY)) {

			} else if (movimiento.getBlancoMov().equals(BlancoMov.USUARIO)) {

			}

		}
		return calcularDanio(movimiento);

	}

	public void recibirDanio(int cantidad) {
		if (this.vidaActual > cantidad) {
			this.vidaActual = this.vidaActual - cantidad;
		} else {
			this.vidaActual = 0;
		}
		// Actualiza vida y gestiona muerte.
	}

	public void curar(int cantidad) {
		if (this.vidaActual + cantidad <= this.vidaMax) {
			this.vidaActual = this.vidaActual + cantidad;
		}
		// Suma vida sin superar vidaMax.
	}

	public void aplicarEstado(Estado estado) {
		if (this.estadosActivos.size() < 1) {
			this.estadosActivos.add(estado);
		} else if (this.estadosActivos.size() <= 1 && estado.isApilable()) {
			this.estadosActivos.add(estado);
		}
		// Añade un estado a la colección aplicando reglas de acumulación.
	}

	public void procesarEstados() {
		for (Estado e : estadosActivos) {
			if (e.getTipoEstado().equals(TipoEstado.DOT)) {
				this.recibirDanio(e.getPotenciaPorTurno());
			}
			if (e.getTipoEstado().equals(TipoEstado.HOT)) {
				this.curar(e.getPotenciaPorTurno());
			}
			if (e.getTipoEstado().equals(TipoEstado.MODIFICADOR)) {

			}
		}
		// Recorre estados activos, aplica su efecto por turno (y elimina expirados).
	}

}
