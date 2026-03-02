package item;

//Atributos Item

public abstract class Item {
	protected String nombre;
	protected TipoItem tipoItem;
	protected int danioBase;
	protected int modificador;

	//Constructor Item

	public Item(String nombre, TipoItem tipoItem, int danioBase, int modificador) {
		super();
		this.nombre = nombre;
		this.tipoItem = tipoItem;
		this.danioBase = danioBase;
		this.modificador = modificador;
	}

}
