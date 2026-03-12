package estado;

import personaje.Personaje;

//Atributos Estado

public abstract class Estado {
	protected String nombre;
	protected TipoEstado tipoEstado;
	protected TipoStat tipoStat;
	protected int turnosRestantes;
	protected boolean apilable;

	public static final int DURACION_PERMANENTE = -1;

	// Constructor Estado

	public Estado(String nombre, TipoEstado tipoEstado, TipoStat tipoStat, int turnosRestantes, boolean apilable) {
		this.nombre = nombre;
		this.tipoEstado = tipoEstado;
		this.tipoStat = tipoStat;
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

	public TipoStat getTipoStat() {
		return tipoStat;
	}

	public int getTurnosRestantes() {
		return turnosRestantes;
	}

	public boolean isApilable() {
		return apilable;
	}

	// Setters Estado

	public void setTurnosRestantes(int turnosRestantes) {
		this.turnosRestantes = turnosRestantes;
	}

	// Funciones Estado

	public abstract void alAplicar(Personaje objetivo);

	public abstract void porTurno(Personaje objetivo);

	public abstract void alEliminar(Personaje objetivo);

}
