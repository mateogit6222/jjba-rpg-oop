package item;

//Atributos Item

public abstract class Item {
	protected String nombre;
	protected TipoItem tipoItem;
	protected int potenciador; // Suma estadísticas: STAND_CA, STAND_LA, ITEM_POT
	protected String efecto;

	// Constructor Item

	public Item(String nombre, TipoItem tipoItem, int potenciador, String efecto) {
		this.nombre = nombre;
		this.tipoItem = tipoItem;
		this.potenciador = potenciador;
		this.efecto = efecto;
	}

	// Getters Item

	public TipoItem getTipoItem() {
		return tipoItem;
	}

	public int getPotenciador() {
		return potenciador;
	}

	// Funciones Item

	public void infoItem() {
		System.out.println("\t Características /n" + "nombre: " + this.nombre + "/n" + "tipoItem: " + this.tipoItem
				+ "/n" + "efecto: " + this.efecto);
	}
}
