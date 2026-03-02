package movimiento;

//Atributos Movimiento

public abstract class Movimiento {
	protected String nombre;
	protected BlancoMov blancoMov;
	protected TipoMov tipoMov;
	protected int potencia;
	protected double precision;
	protected int pp;
	protected int costeEnergia;
	protected String efectoSecundario;
	protected int prioridad;
	protected String efecto;

	//Constructor Movimiento

	public Movimiento(String nombre, BlancoMov blancoMov, TipoMov tipoMov, int potencia, double precision, int pp,
			int costeEnergia, String efectoSecundario, int prioridad, String efecto) {
		super();
		this.nombre = nombre;
		this.blancoMov = blancoMov;
		this.tipoMov = tipoMov;
		this.potencia = potencia;
		this.precision = precision;
		this.pp = pp;
		this.costeEnergia = costeEnergia;
		this.efectoSecundario = efectoSecundario;
		this.prioridad = prioridad;
		this.efecto = efecto;
	}

	//Getters Movimiento
	
	public TipoMov getTipoMov() {
		return tipoMov;
	}

	public BlancoMov getBlancoMov() {
		return blancoMov;
	}
	
}
