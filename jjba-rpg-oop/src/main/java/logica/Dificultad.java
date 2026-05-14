package logica;

public class Dificultad {

	private int idDificultad;
	private String nombre;
	private double multiplicadorVida; // Escala para vidaMax
	private double multiplicadorDanio; // Escala para ataque y ataqueEspecial
	private double multiplicadorExp; // Escala para la recompensa final de EXP

	public Dificultad() {
	}

	public int getIdDificultad() {
		return idDificultad;
	}

	public String getNombre() {
		return nombre;
	}

	public double getMultiplicadorVida() {
		return multiplicadorVida;
	}

	public double getMultiplicadorDanio() {
		return multiplicadorDanio;
	}

	public double getMultiplicadorExp() {
		return multiplicadorExp;
	}

	@Override
	public String toString() {
		return nombre + " (Vida x" + multiplicadorVida + ", Daño x" + multiplicadorDanio + ", Exp x" + multiplicadorExp
				+ ")";
	}
}