package estado;

public enum TipoStat {
	ATAQUE,
	DEFENSA,
	ATAQUE_ESPECIAL,
	DEFENSA_ESPECIAL,
	VELOCIDAD;
	
	private static final double[] TABLA = { 0.25, 0.29, 0.33, 0.40, 0.50, 0.67, 1.00, 1.50, 2.00, 2.50,
			3.00, 3.50, 4.00 };
	
	public static double getMultiplicador(int nivel) {
		if (nivel < -6 || nivel > 6)
	        throw new IllegalArgumentException("Nivel stat fuera de rango: " + nivel);
		return TABLA[nivel + 6]; // nivel -6 → índice 0, nivel 0 → índice 6
	}

}
