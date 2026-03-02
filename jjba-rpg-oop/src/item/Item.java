package item;

//Atributos Item

public abstract class Item {
	protected String nombre;
	protected TipoItem tipoItem;
	protected int modificador;
	protected String efecto;

	// Constructor Item

	public Item(String nombre, TipoItem tipoItem, int modificador, String efecto) {
		super();
		this.nombre = nombre;
		this.tipoItem = tipoItem;
		this.modificador = modificador;
		this.efecto = efecto;
	}

	// Funciones Item

	public void infoItem() {
		System.out.println("\t Características /n" + "nombre: " + this.nombre + "/n" + "tipoItem: " + this.tipoItem + "/n"
				+ "efecto: " + this.efecto);
	}
	
	//Aplicar Item

}
