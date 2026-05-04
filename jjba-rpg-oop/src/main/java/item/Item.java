package item;

import personaje.Personaje;

//Atributos Item

/**
 * Clase abstracta base para los objetos equipables del juego. Los ítems
 * proporcionan ventajas pasivas o efectos únicos en combate al portador.
 */
public abstract class Item {
	protected String nombre;
	protected TipoItem tipoItem;
	protected String efecto;
	protected Personaje portador;

	// Constructor Item

	/**
	 * Constructor base para inicializar un objeto equipable.
	 *
	 * @param nombre   El nombre del ítem.
	 * @param tipoItem La categoría a la que pertenece el ítem.
	 * @param efecto   Descripción textual de la ventaja proporcionada.
	 */
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

	/**
	 * Lógica específica que se dispara cuando el ítem entra en funcionamiento.
	 * Generalmente se llama cuando el ítem se equipa por primera vez.
	 */
	public abstract void aplicarEfecto();

}
