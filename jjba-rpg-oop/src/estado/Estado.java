package estado;

//Atributos Estado

public abstract class Estado {
	protected String nombre;
	protected TipoEstado tipoEstado;
	protected int turnosRestantes;
	protected int potenciaPorTurno;
	protected boolean apilable;
	// protected origen

	// Constructor Estado

	public Estado(String nombre, TipoEstado tipoEstado, int turnosRestantes, int potenciaPorTurno, boolean apilable) {
		this.nombre = nombre;
		this.tipoEstado = tipoEstado;
		this.turnosRestantes = turnosRestantes;
		this.potenciaPorTurno = potenciaPorTurno;
		this.apilable = apilable;
	}

	// Getters Estado

	public TipoEstado getTipoEstado() {
		return tipoEstado;
	}

	public int getTurnosRestantes() {
		return turnosRestantes;
	}

	public int getPotenciaPorTurno() {
		return potenciaPorTurno;
	}

	public boolean isApilable() {
		return apilable;
	}

}
