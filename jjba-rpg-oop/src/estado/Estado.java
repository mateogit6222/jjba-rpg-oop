package estado;

//Atributos Estado

public abstract class Estado {
	protected String nombre;
	protected int turnosRestantes;
	protected int potenciaPorTurno;
	protected boolean apilable;
	//protected origen

	//Constructor Estado

	public Estado(String nombre, int turnosRestantes, int potenciaPorTurno, boolean apilable) {
		super();
		this.nombre = nombre;
		this.turnosRestantes = turnosRestantes;
		this.potenciaPorTurno = potenciaPorTurno;
		this.apilable = apilable;
	}

}
