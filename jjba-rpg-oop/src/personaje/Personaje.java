package personaje;

import java.util.ArrayList;

import estado.Estado;
import item.Item;
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
	protected ArrayList<Estado> estadosActivos;
	protected Item item;
	protected ArrayList<Movimiento> movimientos;

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

	// Funciones Personaje

	public boolean estaVivo() {
		/*
		 * if (vidaActual > 0) { return true; } else { return false; }
		 */

		return vidaActual > 0 ? true : false; // Andrés no preguntes que me lo dijiste tú

	}

	public void recibirMovimiento(Movimiento movimiento) {
		if (movimiento.getTipoMov().equals(TipoMov.FISICO)) {
		}
	}

}
