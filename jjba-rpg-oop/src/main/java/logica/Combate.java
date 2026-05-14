package logica;

import personaje.*;
import movimiento.*;
import item.LuckAndPluck;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import persistencia.DificultadDAO;
import persistencia.JugadorDAO;
import persistencia.PartidaDAO;
import persistencia.PersonajeDAO;

/**
 * Clase principal que gestiona el bucle de juego y la lógica de los combates.
 * Actúa como controlador, coordinando los turnos, la selección de equipos, la
 * toma de decisiones (humana o IA) y la resolución de efectos y daños.
 */
public class Combate {

	private static final Scanner scanner = new Scanner(System.in);
	private static final Random rng = new Random();

	// ─────────────────────────────────────────────
	// PUNTO DE ENTRADA
	// ─────────────────────────────────────────────

	/**
	 * Método principal que lanza la aplicación. Muestra el menú inicial y permite
	 * al jugador elegir entre el modo asistido, el modo automático o salir del
	 * juego.
	 *
	 * @param args Argumentos de línea de comandos (no utilizados).
	 */
	public static void main(String[] args) {
		mostrarTitulo();

		boolean salir = false;
		while (!salir) {
			System.out.println("╔══════════════════════════════╗");
			System.out.println("║     JJBA RPG — MENÚ          ║");
			System.out.println("╠══════════════════════════════╣");
			System.out.println("║  [1] Modo asistido           ║");
			System.out.println("║  [2] Modo automático         ║");
			System.out.println("║  [3] Cargar partida          ║");
			System.out.println("║  [4] Salir                   ║");
			System.out.println("╚══════════════════════════════╝");
			System.out.print("Elige: ");
			String opcion = scanner.nextLine().trim();

			switch (opcion) {
			case "1":
				iniciarCombate(false, null);
				break;
			case "2":
				iniciarCombate(true, null);
				break;
			case "3":
				menuCargarPartida();
				break;
			case "4":
				salir = true;
				System.out.println("¡Hasta la próxima, hijo de una gran persona!");
				break;
			default:
				System.out.println("Opción no válida.");
			}
		}
	}

	// ─────────────────────────────────────────────
	// FLUJO PRINCIPAL DEL COMBATE
	// ─────────────────────────────────────────────

	/**
	 * Inicia y gestiona el bucle principal de un combate completo. Controla la
	 * inicialización de equipos, el orden de los turnos por ronda, la ejecución de
	 * las acciones y la comprobación de las condiciones de victoria o derrota.
	 *
	 * @param modoAuto Si es true, la IA controlará también al equipo del jugador.
	 */
	private static void iniciarCombate(boolean modoAuto, Partida partidaCargada) {
		System.out.println("\n═══════════════════════════════════════");
		System.out.println(modoAuto ? "  MODO AUTOMÁTICO" : "  MODO ASISTIDO");
		System.out.println("═══════════════════════════════════════\n");

		JugadorDAO jugadorDAO = new JugadorDAO();
		DificultadDAO dificultadDAO = new DificultadDAO();
		Jugador jugadorActual = null;
		Dificultad dificultadActual = null;

		List<Personaje> equipoJugador;
		List<Personaje> equipoEnemigo;
		int ronda = 1;

		if (partidaCargada != null) {
			// REANUDAR ESTADO PERSISTENTE
			PersonajeDAO pDAO = new PersonajeDAO();
			equipoJugador = pDAO.cargarPersonajes(partidaCargada.getIdPartida(), "JUGADOR");
			equipoEnemigo = pDAO.cargarPersonajes(partidaCargada.getIdPartida(), "ENEMIGO");
			ronda = partidaCargada.getRondaActual();

			jugadorActual = jugadorDAO.cargarJugador(partidaCargada.getIdJugador());
			dificultadActual = dificultadDAO.cargarDificultad(partidaCargada.getIdDificultad());

			System.out.println("[SISTEMA] Reanudando combate desde la Ronda " + ronda);
		} else {
			// PARTIDA NUEVA
			jugadorActual = jugadorDAO.cargarJugador(1);
			dificultadActual = dificultadDAO.cargarDificultad(2);

			equipoJugador = seleccionarEquipoJugador(modoAuto);
			equipoEnemigo = seleccionarEquipoEnemigo();

			aplicarModificadoresDificultad(equipoEnemigo, dificultadActual);
		}

		aplicarEfectosIniciales(equipoJugador, equipoEnemigo);

		System.out.println("\n¡Que empiece el combate!");
		pausa(modoAuto);

		while (equipoVivo(equipoJugador) && equipoVivo(equipoEnemigo)) {

			if (!modoAuto) {
				System.out.print("\n¿Deseas guardar la partida antes de la Ronda " + ronda + "? (s/n): ");
				String respuesta = scanner.nextLine().trim().toLowerCase();
				// Cambia la llamada original por esta:
				if (respuesta.equals("s")) {
					guardarProgreso(ronda, modoAuto, equipoJugador, equipoEnemigo, jugadorActual, dificultadActual);
				}
			}

			System.out.println("\n╔══════════════════════════════════════╗");
			System.out.println("║            RONDA " + ronda + "                   ║");
			System.out.println("╚══════════════════════════════════════╝");

			mostrarEstadoCombate(equipoJugador, equipoEnemigo);

			List<Personaje> turnoOrden = ordenarPorVelocidad(equipoJugador, equipoEnemigo);

			for (Personaje atacante : turnoOrden) {
				if (!atacante.estaVivo())
					continue;
				if (!equipoVivo(equipoJugador) || !equipoVivo(equipoEnemigo))
					break;

				atacante.setEstaProtegido(false);

				boolean esJugador = equipoJugador.contains(atacante);
				List<Personaje> rivales = esJugador ? equipoEnemigo : equipoJugador;
				List<Personaje> aliados = esJugador ? equipoJugador : equipoEnemigo;

				System.out.println("\n──────────────────────────────────────");
				System.out.println("  Turno de: " + atacante.toString());

				ejecutarTurno(atacante, rivales, aliados, esJugador && !modoAuto);

				resetearProteccionSiNoUsada(atacante);
			}

			System.out.println("\n══ Fase de estados ══");
			procesarEstadosTodos(equipoJugador, equipoEnemigo);

			if (!equipoVivo(equipoEnemigo) || !equipoVivo(equipoJugador))
				break;

			ronda++;
			pausa(modoAuto);
		}

		mostrarResumenFinal(equipoJugador, equipoEnemigo, ronda);

		if (equipoVivo(equipoJugador) && jugadorActual != null && dificultadActual != null) {
			int experienciaBaseTotal = 300; // Experiencia base por derrotar a los 3 Hombres del Pilar
			int experienciaFinal = (int) (experienciaBaseTotal * dificultadActual.getMultiplicadorExp());

			System.out.println("\n[RECOMPENSA] ¡Tu equipo ha ganado " + experienciaFinal + " EXP!");
			jugadorActual.ganarExperiencia(experienciaFinal);

			// Guardamos el nuevo nivel y experiencia en la base de datos
			jugadorDAO.actualizarProgreso(jugadorActual);
		}

	}

	// ─────────────────────────────────────────────
	// SELECCIÓN DE EQUIPOS
	// ─────────────────────────────────────────────

	/**
	 * Permite al jugador seleccionar su equipo de 3 personajes o los genera
	 * aleatoriamente si el modo automático está activado.
	 *
	 * @param modoAuto Determina si la selección será manual (false) o aleatoria
	 *                 (true).
	 * @return Una lista con los 3 personajes que conforman el equipo aliado.
	 */
	private static List<Personaje> seleccionarEquipoJugador(boolean modoAuto) {
		System.out.println("\n╔══════════════════════════════════════╗");
		System.out.println("║    ELIGE TU EQUIPO (3 personajes)    ║");
		System.out.println("╚══════════════════════════════════════╝");

		List<Personaje> catalogo = catalogoJugadores();
		mostrarCatalogo(catalogo);

		List<Personaje> equipo = new ArrayList<>();

		if (modoAuto) {
			for (int i = 0; i < 3; i++) {
				equipo.add(crearPersonajePorIndice(rng.nextInt(catalogo.size())));
			}
			System.out.println("Equipo automático: " + nombresEquipo(equipo));
		} else {
			for (int i = 1; i <= 3; i++) {
				System.out.print("Elige personaje " + i + " (1-" + catalogo.size() + "): ");
				int idx = leerEntero(1, catalogo.size()) - 1;
				equipo.add(crearPersonajePorIndice(idx));
				System.out.println("  -> " + equipo.get(equipo.size() - 1).getNombre() + " añadido.");
			}
		}

		return equipo;
	}

	/**
	 * Genera automáticamente el equipo rival compuesto por 3 personajes manejados
	 * por la IA.
	 *
	 * @return Una lista con los 3 personajes que conforman el equipo enemigo.
	 */
	private static List<Personaje> seleccionarEquipoEnemigo() {
		System.out.println("\n╔══════════════════════════════════════╗");
		System.out.println("║    EQUIPO ENEMIGO (IA automática)    ║");
		System.out.println("╚══════════════════════════════════════╝");

		List<Personaje> catalogo = catalogoEnemigos();
		List<Personaje> equipo = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			equipo.add(crearEnemigoPorIndice(rng.nextInt(catalogo.size())));
		}
		System.out.println("Equipo enemigo: " + nombresEquipo(equipo));
		return equipo;
	}

	// ─────────────────────────────────────────────
	// CATÁLOGOS
	// ─────────────────────────────────────────────

	private static List<Personaje> catalogoJugadores() {
		List<Personaje> lista = new ArrayList<>();
		lista.add(new JonathanJoestar());
		lista.add(new JosephJoestar());
		lista.add(new LisaLisa());
		lista.add(new WilliamZeppeli());
		lista.add(new JotaroKujo());
		lista.add(new JosukeHigashikata());
		lista.add(new GiornoGiovanna());
		lista.add(new JolyneCujoh());
		return lista;
	}

	private static Personaje crearPersonajePorIndice(int idx) {
		switch (idx) {
		case 0:
			return new JonathanJoestar();
		case 1:
			return new JosephJoestar();
		case 2:
			return new LisaLisa();
		case 3:
			return new WilliamZeppeli();
		case 4:
			return new JotaroKujo();
		case 5:
			return new JosukeHigashikata();
		case 6:
			return new GiornoGiovanna();
		case 7:
			return new JolyneCujoh();
		default:
			return new JonathanJoestar();
		}
	}

	private static List<Personaje> catalogoEnemigos() {
		List<Personaje> lista = new ArrayList<>();
		lista.add(new Santana());
		lista.add(new Wamuu());
		lista.add(new Esidisi());
		lista.add(new Kars());
		return lista;
	}

	private static Personaje crearEnemigoPorIndice(int idx) {
		switch (idx) {
		case 0:
			return new Santana();
		case 1:
			return new Wamuu();
		case 2:
			return new Esidisi();
		case 3:
			return new Kars();
		default:
			return new Santana();
		}
	}

	private static void mostrarCatalogo(List<Personaje> catalogo) {
		System.out.println();
		for (int i = 0; i < catalogo.size(); i++) {
			Personaje p = catalogo.get(i);
			System.out.println("  [" + (i + 1) + "] " + p.getNombre() + " (" + p.getTipopj() + ")" + "  HP:"
					+ p.getVidaMax() + "  EN:" + p.getEnergiaMax() + "  ATQ:" + p.getAtaque() + "  DEF:"
					+ p.getDefensa() + "  VEL:" + p.getVelocidad());
		}
		System.out.println();
	}

	// ─────────────────────────────────────────────
	// EFECTOS INICIALES
	// ─────────────────────────────────────────────

	/**
	 * Aplica habilidades pasivas o efectos de ítems que se activan justo al empezar
	 * el combate. Por ejemplo, el ítem Luck & Pluck o la habilidad de revivir de
	 * William Zeppeli.
	 *
	 * @param jugador  El equipo aliado.
	 * @param enemigos El equipo rival.
	 */
	private static void aplicarEfectosIniciales(List<Personaje> jugador, List<Personaje> enemigos) {
		for (Personaje p : jugador) {
			if (p.getItem() instanceof LuckAndPluck) {
				for (Personaje e : enemigos) {
					if (e.estaVivo()) {
						System.out.println(
								"¡Luck & Pluck de " + p.getNombre() + " aplica Anticura a " + e.getNombre() + "!");
						((LuckAndPluck) p.getItem()).aplicarAnticuraA(e);
						break;
					}
				}
			}
			if (p instanceof WilliamZeppeli) {
				((WilliamZeppeli) p).getDeepPassOverdrive().setEquipoAliado(jugador);
			}
		}
	}

	// ─────────────────────────────────────────────
	// TURNO
	// ─────────────────────────────────────────────

	/**
	 * Orquesta el turno individual de un personaje en la ronda actual. Deriva la
	 * ejecución a métodos más específicos dependiendo de si el atacante es un
	 * enemigo (IA), un jugador humano, o un aliado en modo automático.
	 *
	 * @param atacante        El personaje que está realizando su turno.
	 * @param rivales         La lista de personajes del equipo contrario.
	 * @param aliados         La lista de personajes del mismo equipo que el
	 *                        atacante.
	 * @param esJugadorHumano true si el turno requiere input por consola del
	 *                        usuario.
	 */
	private static void ejecutarTurno(Personaje atacante, List<Personaje> rivales, List<Personaje> aliados,
			boolean esJugadorHumano) {
		if (atacante instanceof HombreDelPilar) {
			ejecutarTurnoIA((HombreDelPilar) atacante, rivales);
		} else if (esJugadorHumano) {
			ejecutarTurnoHumano(atacante, rivales, aliados);
		} else {
			ejecutarTurnoAutoJugador(atacante, rivales, aliados);
		}
	}

	/**
	 * Ejecuta la lógica del turno para un personaje controlado por un jugador
	 * humano. Pide por consola la selección de movimiento y el objetivo
	 * correspondiente.
	 *
	 * @param atacante El personaje controlado por el jugador.
	 * @param rivales  El equipo enemigo.
	 * @param aliados  El equipo aliado (necesario para movimientos de soporte o
	 *                 resurrección).
	 */
	private static void ejecutarTurnoHumano(Personaje atacante, List<Personaje> rivales, List<Personaje> aliados) {
		Movimiento mov = null;
		if (atacante instanceof UsuarioDeHamon) {
			mov = ((UsuarioDeHamon) atacante).elegirMovimiento();
		} else if (atacante instanceof UsuarioDeStand) {
			mov = ((UsuarioDeStand) atacante).elegirMovimiento();
		}

		if (mov == null) {
			System.out.println(atacante.getNombre() + " no puede usar ningún movimiento. ¡Pasa turno!");
			return;
		}

		if (mov instanceof MovDeepPassOverdrive) {
			MovDeepPassOverdrive dpo = (MovDeepPassOverdrive) mov;
			if (!dpo.hayAliadoDebilitado()) {
				System.out.println("¡No hay aliados debilitados para revivir!");
				return;
			}
			Personaje aliadoRevivir = elegirAliadoDebilitadoConsola(aliados, atacante);
			dpo.setObjetivoRevivir(aliadoRevivir);
			mov.usarMov(aliadoRevivir);
			return;
		}

		Personaje objetivo = seleccionarObjetivoHumano(atacante, mov, rivales, aliados);
		if (objetivo != null) {
			mov.usarMov(objetivo);
		}
	}

	/**
	 * Ejecuta la lógica del turno para un aliado cuando el modo automático está
	 * activado. La IA aliada prioriza el movimiento de mayor potencia y elige al
	 * enemigo con menos vida.
	 *
	 * @param atacante El aliado controlado por la IA básica.
	 * @param rivales  El equipo enemigo.
	 * @param aliados  El equipo aliado.
	 */
	private static void ejecutarTurnoAutoJugador(Personaje atacante, List<Personaje> rivales, List<Personaje> aliados) {
		Movimiento mejorMov = null;
		int mayorPotencia = -1;
		for (Movimiento m : atacante.getMovimientos()) {
			if (m.puedeUsarseMov() && m.getPotencia() > mayorPotencia
					&& m.getBlancoMov() != movimiento.BlancoMov.USUARIO) {
				mayorPotencia = m.getPotencia();
				mejorMov = m;
			}
		}
		if (mejorMov == null) {
			for (Movimiento m : atacante.getMovimientos()) {
				if (m.puedeUsarseMov()) {
					mejorMov = m;
					break;
				}
			}
		}
		if (mejorMov == null) {
			System.out.println(atacante.getNombre() + " no puede usar ningún movimiento. ¡Pasa turno!");
			return;
		}

		if (mejorMov instanceof MovDeepPassOverdrive) {
			MovDeepPassOverdrive dpo = (MovDeepPassOverdrive) mejorMov;
			if (!dpo.hayAliadoDebilitado())
				return;
			Personaje aliadoRevivir = primerAliadoDebilitado(aliados, atacante);
			if (aliadoRevivir == null)
				return;
			dpo.setObjetivoRevivir(aliadoRevivir);
			mejorMov.usarMov(aliadoRevivir);
			return;
		}

		if (mejorMov.getBlancoMov() == movimiento.BlancoMov.USUARIO) {
			mejorMov.usarMov(atacante);
			return;
		}

		if (mejorMov.getBlancoMov() == movimiento.BlancoMov.OP_ADY) {
			List<Personaje> vivosRivales = vivosDeEquipo(rivales);
			System.out.println("  " + atacante.getNombre() + " usa " + mejorMov.getNombre() + " en area.");
			for (Personaje r : vivosRivales) {
				mejorMov.usarMov(r);
			}
			return;
		}

		List<Personaje> vivosRivales = vivosDeEquipo(rivales);
		if (vivosRivales.isEmpty())
			return;
		Personaje objetivo = vivosRivales.get(0);
		for (Personaje p : vivosRivales) {
			if (p.getVidaActual() < objetivo.getVidaActual())
				objetivo = p;
		}
		mejorMov.usarMov(objetivo);
	}

	/**
	 * Ejecuta el turno de los enemigos (Hombres del Pilar) basándose en su propia
	 * IA interna.
	 *
	 * @param atacante El enemigo que está realizando su turno.
	 * @param rivales  El equipo del jugador (objetivos potenciales).
	 */
	private static void ejecutarTurnoIA(HombreDelPilar atacante, List<Personaje> rivales) {
		atacante.setRivalesVivos(vivosDeEquipo(rivales));
		Movimiento mov = atacante.elegirMovimientoIA();
		if (mov == null) {
			System.out.println(atacante.getNombre() + " no puede hacer nada. ¡Pasa turno!");
			return;
		}

		if (mov.getBlancoMov() == movimiento.BlancoMov.OP_ADY) {
			List<Personaje> vivosRivales = vivosDeEquipo(rivales);
			System.out.println("  " + atacante.getNombre() + " usa " + mov.getNombre() + " en area.");
			for (Personaje r : vivosRivales)
				mov.usarMov(r);
			return;
		}

		if (mov.getBlancoMov() == movimiento.BlancoMov.USUARIO) {
			mov.usarMov(atacante);
			return;
		}

		Personaje objetivo = atacante.seleccionarObjetivoIA();
		if (objetivo == null)
			objetivo = primerVivo(rivales);
		if (objetivo == null)
			return;
		mov.usarMov(objetivo);
	}

	// ─────────────────────────────────────────────
	// SELECCIÓN DE OBJETIVO (HUMANO)
	// ─────────────────────────────────────────────

	/**
	 * Gestiona la selección manual de un objetivo para un movimiento concreto.
	 * Determina automáticamente los objetivos si el movimiento es de área o afecta
	 * al propio usuario.
	 *
	 * @param atacante El personaje que usa el movimiento.
	 * @param mov      El movimiento que se va a ejecutar.
	 * @param rivales  Lista de enemigos disponibles para atacar.
	 * @param aliados  Lista de aliados disponibles para movimientos de soporte.
	 * @return El personaje objetivo seleccionado, o null si es un ataque de área.
	 */
	private static Personaje seleccionarObjetivoHumano(Personaje atacante, Movimiento mov, List<Personaje> rivales,
			List<Personaje> aliados) {

		movimiento.BlancoMov blanco = mov.getBlancoMov();

		if (blanco == movimiento.BlancoMov.USUARIO) {
			return atacante;
		}

		if (blanco == movimiento.BlancoMov.OP_ADY) {
			List<Personaje> vivosRivales = vivosDeEquipo(rivales);
			if (vivosRivales.isEmpty()) {
				System.out.println("No hay rivales vivos.");
				return null;
			}
			System.out.println("  -> " + mov.getNombre() + " afecta a todos los rivales.");
			for (Personaje r : vivosRivales)
				mov.usarMov(r);
			return null;
		}

		// Movimientos de estado sin potencia que afectan a aliados
		if (mov.getTipoMov() == movimiento.TipoMov.ESTADO && mov.getPotencia() == 0) {
			if (mov instanceof MovCintaDeMoebius) {
				return atacante;
			}
			// Otros de estado → elegir aliado vivo
			List<Personaje> vivosAliados = vivosDeEquipo(aliados);
			if (!vivosAliados.contains(atacante))
				vivosAliados.add(0, atacante);
			if (vivosAliados.isEmpty())
				return atacante;
			if (atacante instanceof UsuarioDeHamon)
				return ((UsuarioDeHamon) atacante).elegirObjetivo(vivosAliados, "aliado");
			if (atacante instanceof UsuarioDeStand)
				return ((UsuarioDeStand) atacante).elegirObjetivo(vivosAliados, "aliado");
			return atacante;
		}

		// Movimiento de daño: elegir rival vivo
		List<Personaje> vivosRivales = vivosDeEquipo(rivales);
		if (vivosRivales.isEmpty()) {
			System.out.println("No hay rivales vivos.");
			return null;
		}
		if (atacante instanceof UsuarioDeHamon)
			return ((UsuarioDeHamon) atacante).elegirObjetivo(vivosRivales, "enemigo");
		if (atacante instanceof UsuarioDeStand)
			return ((UsuarioDeStand) atacante).elegirObjetivo(vivosRivales, "enemigo");
		return vivosRivales.get(0);
	}

	// ─────────────────────────────────────────────
	// ESTADOS
	// ─────────────────────────────────────────────

	/**
	 * Procesa los efectos recurrentes por turno (como veneno o quemaduras) para
	 * todos los personajes vivos en el campo de batalla al final de la ronda.
	 *
	 * @param equipoA El equipo del jugador.
	 * @param equipoB El equipo enemigo.
	 */
	private static void procesarEstadosTodos(List<Personaje> equipoA, List<Personaje> equipoB) {
		for (Personaje p : equipoA) {
			if (p.estaVivo() && !p.getEstadosActivos().isEmpty()) {
				System.out.println("  [Estados de " + p.getNombre() + "]");
				p.procesarEstados();
			}
		}
		for (Personaje p : equipoB) {
			if (p.estaVivo() && !p.getEstadosActivos().isEmpty()) {
				System.out.println("  [Estados de " + p.getNombre() + "]");
				p.procesarEstados();
			}
		}
	}

	// ─────────────────────────────────────────────
	// PROTECCIÓN
	// ─────────────────────────────────────────────

	private static void resetearProteccionSiNoUsada(Personaje p) {
		for (Movimiento m : p.getMovimientos()) {
			if (m instanceof MovProteccion && !p.isEstaProtegido()) {
				((MovProteccion) m).resetearConsecutivos();
			}
		}
	}

	// ─────────────────────────────────────────────
	// ORDEN DE TURNO
	// ─────────────────────────────────────────────

	/**
	 * Une ambos equipos y los ordena de forma descendente según su estadística de
	 * velocidad actual (incluyendo modificadores). En caso de empate, el orden se
	 * decide de forma aleatoria.
	 *
	 * @param a Primer equipo a ordenar.
	 * @param b Segundo equipo a ordenar.
	 * @return Una lista unificada y ordenada de todos los personajes listos para
	 *         actuar.
	 */
	private static List<Personaje> ordenarPorVelocidad(List<Personaje> a, List<Personaje> b) {
		List<Personaje> todos = new ArrayList<>();
		todos.addAll(a);
		todos.addAll(b);

		// 1. Mezclamos la lista primero para que los empates se resuelvan al azar de
		// forma segura
		java.util.Collections.shuffle(todos, rng);

		// 2. Ordenamos por velocidad
		todos.sort((p1, p2) -> {
			int v1 = (int) (p1.getVelocidad() * p1.getMultiplicadorStat(estado.TipoStat.VELOCIDAD));
			int v2 = (int) (p2.getVelocidad() * p2.getMultiplicadorStat(estado.TipoStat.VELOCIDAD));

			// Comparamos de mayor a menor. Si v1 y v2 son idénticos, Integer.compare
			// devuelve 0.
			return Integer.compare(v2, v1);
		});
		return todos;
	}

	// ─────────────────────────────────────────────
	// VISUALIZACIÓN
	// ─────────────────────────────────────────────

	private static void mostrarTitulo() {
		System.out.println("╔══════════════════════════════════════════╗");
		System.out.println("║   JOJO'S BIZARRE ADVENTURE  RPG          ║");
		System.out.println("║        Combate Triple por Turnos          ║");
		System.out.println("╚══════════════════════════════════════════╝");
		System.out.println();
	}

	private static void mostrarEstadoCombate(List<Personaje> jugador, List<Personaje> enemigo) {
		System.out.println("\n  ── TU EQUIPO ──────────────────────────");
		for (Personaje p : jugador) {
			mostrarBarraPersonaje(p);
		}
		System.out.println("  ── ENEMIGOS ────────────────────────────");
		for (Personaje p : enemigo) {
			mostrarBarraPersonaje(p);
		}
	}

	private static void mostrarBarraPersonaje(Personaje p) {
		String debilitado = p.estaVivo() ? "" : " [DEBILITADO]";
		String estadosStr = p.getEstadosActivos().isEmpty() ? "" : " | Estados: " + p.getEstadosActivos();
		System.out.println("  " + p.getNombre() + "  HP: " + p.getVidaActual() + "/" + p.getVidaMax() + "  EN: "
				+ p.getEnergiaActual() + "/" + p.getEnergiaMax() + estadosStr + debilitado);
	}

	private static void mostrarResumenFinal(List<Personaje> jugador, List<Personaje> enemigo, int rondas) {
		System.out.println("\n╔══════════════════════════════════════════╗");
		System.out.println("║            FIN DEL COMBATE               ║");
		System.out.println("╚══════════════════════════════════════════╝");
		System.out.println("  Rondas jugadas: " + rondas);

		if (equipoVivo(jugador)) {
			System.out.println("\n  ¡¡VICTORIA!! ¡Tu equipo ha ganado!");
		} else {
			System.out.println("\n  DERROTA. Los Hombres del Pilar han triunfado...");
		}

		System.out.println("\n  ── Estado final ────────────────────────");
		System.out.println("  TU EQUIPO:");
		for (Personaje p : jugador) {
			mostrarBarraPersonaje(p);
		}
		System.out.println("  ENEMIGOS:");
		for (Personaje p : enemigo) {
			mostrarBarraPersonaje(p);
		}
		System.out.println();
	}

	// ─────────────────────────────────────────────
	// UTILIDADES
	// ─────────────────────────────────────────────

	private static boolean equipoVivo(List<Personaje> equipo) {
		for (Personaje p : equipo) {
			if (p.estaVivo())
				return true;
		}
		return false;
	}

	private static List<Personaje> vivosDeEquipo(List<Personaje> equipo) {
		List<Personaje> vivos = new ArrayList<>();
		for (Personaje p : equipo) {
			if (p.estaVivo())
				vivos.add(p);
		}
		return vivos;
	}

	private static Personaje primerVivo(List<Personaje> equipo) {
		for (Personaje p : equipo) {
			if (p.estaVivo())
				return p;
		}
		return null;
	}

	private static Personaje primerAliadoDebilitado(List<Personaje> aliados, Personaje excluir) {
		for (Personaje p : aliados) {
			if (!p.estaVivo() && p != excluir)
				return p;
		}
		return null;
	}

	private static Personaje elegirAliadoDebilitadoConsola(List<Personaje> aliados, Personaje excluir) {
		List<Personaje> debilitados = new ArrayList<>();
		for (Personaje p : aliados) {
			if (!p.estaVivo() && p != excluir)
				debilitados.add(p);
		}
		System.out.println("  Elige el aliado a revivir:");
		for (int i = 0; i < debilitados.size(); i++) {
			System.out.println("  [" + (i + 1) + "] " + debilitados.get(i).getNombre());
		}
		System.out.print("Elige (1-" + debilitados.size() + "): ");
		int idx = leerEntero(1, debilitados.size()) - 1;
		return debilitados.get(idx);
	}

	private static String nombresEquipo(List<Personaje> equipo) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < equipo.size(); i++) {
			if (i > 0)
				sb.append(", ");
			sb.append(equipo.get(i).getNombre());
		}
		return sb.toString();
	}

	private static int leerEntero(int min, int max) {
		while (true) {
			try {
				int val = Integer.parseInt(scanner.nextLine().trim());
				if (val >= min && val <= max)
					return val;
				System.out.print("  Elige entre " + min + " y " + max + ": ");
			} catch (NumberFormatException e) {
				System.out.print("  Entrada invalida. Elige entre " + min + " y " + max + ": ");
			}
		}
	}

	private static void pausa(boolean modoAuto) {
		if (!modoAuto) {
			System.out.print("\n  [Pulsa Enter para continuar...]");
			scanner.nextLine();
		}
	}

	/**
	 * Guarda el estado actual del combate en la base de datos asociando las
	 * entidades activas.
	 */
	private static void guardarProgreso(int ronda, boolean modoAuto, List<Personaje> jugador, List<Personaje> enemigo,
			Jugador jugadorActual, Dificultad dificultadActual) {
		System.out.println("\n[SISTEMA] Guardando partida...");

		PartidaDAO partidaDAO = new PartidaDAO();
		PersonajeDAO personajeDAO = new PersonajeDAO();

		// Extraemos los IDs reales de los objetos cargados en memoria
		int idJugador = (jugadorActual != null) ? jugadorActual.getIdJugador() : 1;
		int idDificultad = (dificultadActual != null) ? dificultadActual.getIdDificultad() : 2;

		// Guardamos la cabecera de la Partida con los datos correctos
		int idPartida = partidaDAO.guardarNuevaPartida(idJugador, idDificultad, ronda, modoAuto);

		if (idPartida != -1) {
			personajeDAO.guardarPersonajes(idPartida, jugador, "JUGADOR");
			personajeDAO.guardarPersonajes(idPartida, enemigo, "ENEMIGO");

			System.out.println("¡Progreso guardado correctamente en la base de datos!");
		} else {
			System.err.println("Error: No se pudo conectar con la base de datos para guardar.");
		}
	}

	private static void menuCargarPartida() {
		System.out.print("\nIntroduce el ID de la partida a cargar: ");
		int id = leerEntero(1, 9999);

		PartidaDAO pDAO = new PartidaDAO();
		Partida partidaGuardada = pDAO.cargarPartida(id);

		if (partidaGuardada != null) {
			iniciarCombate(partidaGuardada.isModoAuto(), partidaGuardada);
		}
	}

	/**
	 * Aplica de forma limpia y desacoplada los modificadores de dificultad sobre
	 * los enemigos.
	 */
	private static void aplicarModificadoresDificultad(List<Personaje> enemigos, Dificultad dificultadActual) {
		if (dificultadActual == null)
			return;

		for (Personaje enemigo : enemigos) {
			int nuevaVidaMax = (int) (enemigo.getVidaMax() * dificultadActual.getMultiplicadorVida());
			enemigo.setVidaMax(nuevaVidaMax);
			enemigo.setVidaActual(nuevaVidaMax);

			enemigo.setAtaque((int) (enemigo.getAtaque() * dificultadActual.getMultiplicadorDanio()));
			enemigo.setAtaqueEspecial((int) (enemigo.getAtaqueEspecial() * dificultadActual.getMultiplicadorDanio()));
		}
		System.out.println("[SISTEMA] Modificadores de dificultad aplicados al equipo rival.");
	}

}