package personaje;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Random;

import estado.Estado;
import estado.TipoStat;
import item.Item;
import movimiento.BlancoMov;
import movimiento.Movimiento;
import movimiento.TipoMov;

//TODO: Implementar más adelante debilidades y resistencias en calcularDanio y recibirDanio.

//Atributos Personaje

/**
 * Clase abstracta que representa la entidad base para todos los combatientes
 * del juego. Gestiona los atributos principales, estadísticas, el inventario
 * básico (ítem) y los estados alterados.
 */
public abstract class Personaje {
	protected String nombre;
	protected TipoPj tipopj;

	protected int vidaMax;
	protected int vidaActual;
	protected int energiaMax;
	protected int energiaActual;

	protected int ataque;
	protected int defensa;
	protected int ataqueEspecial;
	protected int defensaEspecial;
	protected int velocidad;

	protected boolean estaProtegido;

	// protected boolean estaVivo;

	protected List<Estado> estadosActivos;
	protected Item item;
	protected List<Movimiento> movimientos;

	protected Map<TipoStat, Integer> nivelesStat;

	private static final Random random = new Random();

	// Constructor Personaje

	/**
	 * Constructor principal para inicializar un Personaje.
	 *
	 * @param nombre          El nombre del personaje.
	 * @param tipopj          El tipo o clasificación del personaje.
	 * @param vidaMax         Puntos de vida máximos.
	 * @param energiaMax      Puntos de energía máximos (equivalente a PP/Mana).
	 * @param ataque          Estadística de ataque físico base.
	 * @param defensa         Estadística de defensa física base.
	 * @param ataqueEspecial  Estadística de ataque especial base.
	 * @param defensaEspecial Estadística de defensa especial base.
	 * @param velocidad       Estadística de velocidad para determinar el orden de
	 *                        turno.
	 * @param estaProtegido   Estado inicial de protección del personaje.
	 * @param item            Ítem equipado inicialmente (puede ser null).
	 */
	public Personaje(String nombre, TipoPj tipopj, int vidaMax, int energiaMax, int ataque, int defensa,
			int ataqueEspecial, int defensaEspecial, int velocidad, boolean estaProtegido, Item item) {
		this.nombre = nombre;
		this.tipopj = tipopj;
		this.vidaMax = vidaMax;
		this.vidaActual = vidaMax; // Empieza con vida al máximo
		this.energiaMax = energiaMax;
		this.energiaActual = energiaMax; // Empieza con energía al máximo
		this.ataque = ataque;
		this.defensa = defensa;
		this.ataqueEspecial = ataqueEspecial;
		this.defensaEspecial = defensaEspecial;
		this.velocidad = velocidad;
		this.estaProtegido = estaProtegido;
		this.item = item;

		this.estadosActivos = new ArrayList<>();
		this.movimientos = new ArrayList<>();

		this.nivelesStat = new HashMap<>();
		for (TipoStat stat : TipoStat.values()) {
			nivelesStat.put(stat, 0); // Todos los niveles empiezan en 0
		}

		// Aplicar bonificaciones del ítem al equiparlo
		if (item != null) {
			item.aplicarEfecto();
		}
	}

	// Getters Personaje

	public String getNombre() {
		return nombre;
	}

	public TipoPj getTipopj() {
		return tipopj;
	}

	public int getVidaMax() {
		return vidaMax;
	}

	public int getVidaActual() {
		return vidaActual;
	}

	public int getEnergiaMax() {
		return energiaMax;
	}

	public int getEnergiaActual() {
		return this.energiaActual;
	}

	public int getAtaque() {
		return ataque;
	}

	public int getDefensa() {
		return defensa;
	}

	public int getAtaqueEspecial() {
		return ataqueEspecial;
	}

	public int getDefensaEspecial() {
		return defensaEspecial;
	}

	public int getVelocidad() {
		return velocidad;
	}

	public boolean isEstaProtegido() {
		return estaProtegido;
	}

	public List<Estado> getEstadosActivos() {
		return estadosActivos;
	}

	public Item getItem() {
		return item;
	}

	public List<Movimiento> getMovimientos() {
		return movimientos;
	}

	public int getNivelStat(TipoStat stat) {
		return nivelesStat.get(stat);
	}

	public Map<TipoStat, Integer> getNivelesStat() {
		return Collections.unmodifiableMap(nivelesStat);
	}

	public double getMultiplicadorStat(TipoStat stat) {
		return TipoStat.getMultiplicador(nivelesStat.get(stat));
	}

	// Setters Personaje

	public void setVidaMax(int vidaMax) {
		this.vidaMax = vidaMax;
	}

	public void setEnergiaMax(int energiaMax) {
		this.energiaMax = energiaMax;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}

	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}

	public void setAtaqueEspecial(int ataqueEspecial) {
		this.ataqueEspecial = ataqueEspecial;
	}

	public void setDefensaEspecial(int defensaEspecial) {
		this.defensaEspecial = defensaEspecial;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	public void setEstaProtegido(boolean estaProtegido) {
		this.estaProtegido = estaProtegido;
	}

	// Funciones Personaje

	/**
	 * Comprueba si el personaje tiene puntos de vida superiores a cero.
	 *
	 * @return true si el personaje puede seguir combatiendo, false si está
	 *         debilitado.
	 */
	public boolean estaVivo() {
		/*
		 * if (this.vidaActual > 0) { return true; } else { return false; }
		 */

		return this.vidaActual > 0 ? true : false;
		// Devuelve si el personaje puede actuar.
	}

	/**
	 * Método abstracto que define la lógica para la selección de acciones durante
	 * un turno. Debe ser implementado por las subclases dependiendo de si es
	 * controlado por IA o jugador.
	 */
	public abstract void elegirAccion();

	/**
	 * Muestra por consola la información detallada del personaje.
	 */
	public void infoPersonaje() {
		System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" + "Nombre:         " + nombre + "\n" + "Tipo:           "
				+ tipopj + "\n" + "Vida:           " + vidaActual + "/" + vidaMax + "\n" + "Energía:        "
				+ energiaActual + "/" + energiaMax + "\n" + "Ataque:         " + ataque + "\n" + "Defensa:        "
				+ defensa + "\n" + "Atq. Especial:  " + ataqueEspecial + "\n" + "Def. Especial:  " + defensaEspecial
				+ "\n" + "Velocidad:      " + velocidad + "\n" + "Ítem:           "
				+ (item != null ? item.toString() : "ninguno") + "\n" + "Estados:        "
				+ (estadosActivos.isEmpty() ? "ninguno" : estadosActivos.toString())
				+ "\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
		// Devuelve información del personaje.
	}

	@Override
	public String toString() {
		return nombre + " [" + tipopj + ", " + vidaActual + "/" + vidaMax + " HP, " + energiaActual + "/" + energiaMax
				+ " EN]";
	}

	/**
	 * Modifica los niveles de estadística en combate (buffs/debuffs) con un límite
	 * de -6 a +6.
	 *
	 * @param stat    La estadística que se va a alterar.
	 * @param niveles La cantidad de niveles a subir (positivo) o bajar (negativo).
	 */
	public void modificarNivelStat(TipoStat stat, int niveles) {
		int nivelActual = nivelesStat.get(stat);
		int nivelNuevo = Math.max(-6, Math.min(6, nivelActual + niveles));
		nivelesStat.put(stat, nivelNuevo);

		if (nivelNuevo == nivelActual) {
			if (niveles > 0) {
				System.out.println("¡El " + stat + " de " + nombre + " no puede subir más!");
			} else {
				System.out.println("¡El " + stat + " de " + nombre + " no puede bajar más!");
			}
		} else {
			int diferencia = nivelNuevo - nivelActual;
			String mensaje;

			if (diferencia == 1)
				mensaje = "subió";
			else if (diferencia == 2)
				mensaje = "subió mucho";
			else if (diferencia >= 3)
				mensaje = "subió muchísimo";
			else if (diferencia == -1)
				mensaje = "bajó";
			else if (diferencia == -2)
				mensaje = "bajó mucho";
			else
				mensaje = "bajó muchísimo";

			System.out.println("¡El " + stat + " de " + nombre + " " + mensaje + "!");
		}
	}

	public void equiparItem(Item nuevoItem) {
		this.item = nuevoItem;
		if (nuevoItem != null) {
			nuevoItem.setPersonaje(this);
			nuevoItem.aplicarEfecto();
			// this.item = null;
		}
	}

	public void aprenderMovimiento(Movimiento mov) {
		this.movimientos.add(mov);
		mov.setPersonaje(this);
	}

	public boolean gastarEnergia(int coste) {
		if (this.energiaActual >= coste) {
			this.energiaActual -= coste;
			return true;
		}
		return false;
		// Comprueba y descuenta energía si es posible.
	}

	public void recuperarEnergia(int cantidad) {
		if (cantidad > 0) {
			this.energiaActual = Math.min(this.energiaActual + cantidad, this.energiaMax);
		}
	}

	/**
	 * Calcula el daño infligido a un objetivo utilizando las fórmulas estándar de
	 * combate. Incluye probabilidad de golpe crítico y modificadores de área.
	 *
	 * @param movimiento El movimiento que se está ejecutando.
	 * @param defensor   El personaje objetivo que recibirá el ataque.
	 * @return La cantidad de daño calculada (mínimo 1).
	 */
	public int calcularDanio(Movimiento movimiento, Personaje defensor) {

		// Los movimientos de estado no hacen daño directo
		if (movimiento.getTipoMov() == TipoMov.ESTADO) {
			return 0;
		}

		// Crítico: probabilidad ~4.167% (1/24), multiplicador x1.5
		boolean esCritico = random.nextDouble() < 0.04167;
		double multiplicadorCritico = esCritico ? 1.5 : 1.0;
		if (esCritico) {
			System.out.println("¡Golpe crítico!");
		}

		// Roll de daño: entre 85% y 100% del daño base
		double rollDanio = (random.nextInt(16) + 85) / 100.0;

		int atkStat;
		int defStat;

		if (movimiento.getTipoMov() == TipoMov.FISICO) {
			atkStat = (int) (this.ataque * getMultiplicadorStat(TipoStat.ATAQUE));
			defStat = (int) (defensor.getDefensa() * defensor.getMultiplicadorStat(TipoStat.DEFENSA));
		} else { // ESPECIAL
			atkStat = (int) (this.ataqueEspecial * getMultiplicadorStat(TipoStat.ATAQUE_ESPECIAL));
			defStat = (int) (defensor.getDefensaEspecial() * defensor.getMultiplicadorStat(TipoStat.DEFENSA_ESPECIAL));
		}

		// Fórmula base (nivel fijo = 50 como en el documento)
		int nivel = 50;
		double danioBase = (((2.0 * nivel / 5 + 2) * movimiento.getPotencia() * atkStat / defStat) / 50.0 + 2)
				* multiplicadorCritico;

		double danioFinal;

		// Reducción de daño si el movimiento afecta a oponentes adyacentes (área)
		if (movimiento.getBlancoMov() == BlancoMov.OP_ADY) {
			danioFinal = (danioBase * 3072.0 / 4096.0) * rollDanio;
		} else {
			danioFinal = danioBase * rollDanio;
		}

		return (int) Math.max(1, danioFinal); // Mínimo 1 de daño
	}

	/**
	 * Resta la cantidad de daño especificada de la vida actual del personaje.
	 * Gestiona el estado de protección y la notificación de debilitamiento.
	 *
	 * @param cantidad Puntos de vida a restar.
	 */
	public void recibirDanio(int cantidad) {
		if (estaProtegido) {
			System.out.println(nombre + " se ha protegido."); //
			return;
		}

		if (cantidad > 0) {
			this.vidaActual -= cantidad;

			if (this.vidaActual < 0) {
				this.vidaActual = 0;
			}

			if (this.vidaActual == 0) {
				System.out.println(nombre + " se ha debilitado."); //
			}
		}
		// Actualiza vida y gestiona muerte.
	}

	public void curar(int cantidad) {
		if (cantidad > 0) {
			for (Estado e : estadosActivos) {
				if (e.getNombre().equals("anticura")) {
					System.out.println(nombre + " tiene anticura activa. ¡No puede recuperar vida!");
					return;
				}
			}
			this.vidaActual = Math.min(this.vidaActual + cantidad, this.vidaMax);
		}
	}

	public void aplicarEstado(Estado estado) {
		if (!estaVivo())
			return;

		for (Estado e : estadosActivos) {
			if (e.getNombre().equals(estado.getNombre())) {
				if (!estado.isApilable()) {
					System.out.println(nombre + " ya tiene el estado " + estado.getNombre() + ".");
					return;
				}
			}
		}

		estadosActivos.add(estado);
		estado.alAplicar(this);
		// Añade un estado a la colección aplicando reglas de acumulación.
	}

	//

	/**
	 * Procesa todos los estados alterados activos en el personaje al final de cada
	 * turno. Resta la duración de los estados temporales y elimina los expirados.
	 */
	public void procesarEstados() {
		Iterator<Estado> it = estadosActivos.iterator();

		while (it.hasNext()) {
			Estado estado = it.next();
			estado.porTurno(this);

			if (!estaVivo()) {
				estadosActivos.clear();
				return;
			}

			if (estado.getTurnosRestantes() != Estado.DURACION_PERMANENTE) {
				estado.setTurnosRestantes(estado.getTurnosRestantes() - 1);
				if (estado.getTurnosRestantes() == 0) {
					estado.alEliminar(this);
					it.remove();
				}
			}
		}
		// Recorre estados activos, aplica su efecto por turno (y elimina expirados).
	}

}
