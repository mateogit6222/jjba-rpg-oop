package logica;

public class Partida {

	// Atributos que coinciden con las columnas de la tabla PARTIDA
	private int idPartida;
	private int idJugador;
	private int idDificultad;
	private int rondaActual;
	private boolean modoAuto;

	// Constructor vacío OBLIGATORIO para que Generica.newInstance() funcione
	public Partida() {
	}

	// Getters para poder leer los datos cuando se cargue la partida
	public int getIdPartida() {
		return idPartida;
	}

	public int getIdJugador() {
		return idJugador;
	}

	public int getIdDificultad() {
		return idDificultad;
	}

	public int getRondaActual() {
		return rondaActual;
	}

	public boolean isModoAuto() {
		return modoAuto;
	}

	// Un método toString() para que puedas imprimirla fácilmente por consola y
	// comprobar que funciona
	@Override
	public String toString() {
		return "Partida [idPartida=" + idPartida + ", idJugador=" + idJugador + ", idDificultad=" + idDificultad
				+ ", rondaActual=" + rondaActual + ", modoAuto=" + modoAuto + "]";
	}
}