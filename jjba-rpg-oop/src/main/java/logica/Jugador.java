package logica;

/**
 * Entidad que representa el perfil persistente del usuario en el juego.
 * Almacena su progresión global (experiencia y nivel) para el Ranking.
 */
public class Jugador {

	private int idJugador;
	private String nombre;
	private int experiencia;
	private int nivel;

	// Constructor vacío obligatorio para que funcione la instanciación por
	// Reflection (Generica)
	public Jugador() {
	}

	public Jugador(String nombre, int experiencia, int nivel) {
		this.nombre = nombre;
		this.experiencia = experiencia;
		this.nivel = nivel;
	}

	// Getters
	public int getIdJugador() {
		return idJugador;
	}

	public String getNombre() {
		return nombre;
	}

	public int getExperiencia() {
		return experiencia;
	}

	public int getNivel() {
		return nivel;
	}

	// Setters para actualizar la progresión
	public void setIdJugador(int idJugador) {
		this.idJugador = idJugador;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setExperiencia(int experiencia) {
		this.experiencia = experiencia;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	/**
	 * Añade experiencia al jugador y calcula si sube de nivel automáticamente.
	 */
	public void ganarExperiencia(int expGanada) {
		if (expGanada > 0) {
			this.experiencia += expGanada;
			// Fórmula de escalado simple: cada nivel requiere (nivel * 100) de exp
			while (this.experiencia >= (this.nivel * 100)) {
				this.experiencia -= (this.nivel * 100);
				this.nivel++;
				System.out.println("\n¡Felicidades! " + this.nombre + " ha subido al Nivel " + this.nivel + "!");
			}
		}
	}

	@Override
	public String toString() {
		return "Jugador: " + nombre + " | Nivel: " + nivel + " | Exp: " + experiencia;
	}
}