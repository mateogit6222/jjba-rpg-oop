package item;

import personaje.Personaje;

//Atributos Item

public abstract class Item {
	protected String nombre;
	protected TipoItem tipoItem;
	protected String efecto;
	protected Personaje portador;

	// Constructor Item

	public Item(String nombre, TipoItem tipoItem, String efecto) {
		this.nombre = nombre;
		this.tipoItem = tipoItem;
		this.efecto = efecto;
	}

	// Getters Item

	public String getNombre() {
		return nombre;
	}

	public TipoItem getTipoItem() {
		return tipoItem;
	}

	public String getEfecto() {
		return efecto;
	}

	// Setters Item

	public void setPersonaje(Personaje personaje) {
		this.portador = personaje;
	}

	// Funciones Item

	public void infoItem() {
		System.out.println("\tCaracterísticas\n" + "nombre: " + nombre + "\n" + "tipoItem: " + tipoItem + "\n"
				+ "efecto: " + efecto);
	}

	@Override
	public String toString() {
		return nombre + " [" + tipoItem + "]";
	}

	public abstract void aplicarEfecto();

}
