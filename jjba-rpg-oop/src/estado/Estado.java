package estado;

//Atributos Estado

public abstract class Estado {
	protected String nombre;
	protected TipoEstado tipoEstado;
	protected int turnosRestantes;
	protected boolean apilable;
	// protected origen

	// Constructor Estado

	public Estado(String nombre, TipoEstado tipoEstado, int turnosRestantes, boolean apilable) {
		this.nombre = nombre;
		this.tipoEstado = tipoEstado;
		this.turnosRestantes = turnosRestantes;
		this.apilable = apilable;
	}

	// Getters Estado

	public String getNombre() {
		return nombre;
	}

	public TipoEstado getTipoEstado() {
		return tipoEstado;
	}

	public int getTurnosRestantes() {
		return turnosRestantes;
	}

	public boolean isApilable() {
		return apilable;
	}

	// Funciones Estado

}
