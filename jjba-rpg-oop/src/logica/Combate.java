package logica;

import personaje.*;
import movimiento.*;
import item.LuckAndPluck;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Combate {

	private static final Scanner scanner = new Scanner(System.in);
	private static final Random rng = new Random();

	// ─────────────────────────────────────────────
	// PUNTO DE ENTRADA
	// ─────────────────────────────────────────────

	public static void main(String[] args) {
		mostrarTitulo();

		boolean salir = false;
		while (!salir) {
			System.out.println("╔══════════════════════════════╗");
			System.out.println("║     JJBA RPG — MENÚ          ║");
			System.out.println("╠══════════════════════════════╣");
			System.out.println("║  [1] Modo asistido           ║");
			System.out.println("║  [2] Modo automático         ║");
			System.out.println("║  [3] Salir                   ║");
			System.out.println("╚══════════════════════════════╝");
			System.out.print("Elige: ");
			String opcion = scanner.nextLine().trim();

			switch (opcion) {
			case "1":
				iniciarCombate(false);
				break;
			case "2":
				iniciarCombate(true);
				break;
			case "3":
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

	private static void iniciarCombate(boolean modoAuto) {
		System.out.println("\n═══════════════════════════════════════");
		System.out.println(modoAuto ? "  MODO AUTOMÁTICO" : "  MODO ASISTIDO");
		System.out.println("═══════════════════════════════════════\n");

		List<Personaje> equipoJugador = seleccionarEquipoJugador(modoAuto);
		List<Personaje> equipoEnemigo = seleccionarEquipoEnemigo();

		aplicarEfectosIniciales(equipoJugador, equipoEnemigo);

		System.out.println("\n¡Que empiece el combate!");
		pausa(modoAuto);

		int ronda = 1;
		while (equipoVivo(equipoJugador) && equipoVivo(equipoEnemigo)) {
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

				boolean esJugador = equipoJugador.contains(atacante);
				List<Personaje> rivales = esJugador ? equipoEnemigo : equipoJugador;
				List<Personaje> aliados = esJugador ? equipoJugador : equipoEnemigo;

				System.out.println("\n──────────────────────────────────────");
				System.out.println("  Turno de: " + atacante.toString());

				ejecutarTurno(atacante, rivales, aliados, esJugador && !modoAuto);

				atacante.setEstaProtegido(false);
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
	}

	// ─────────────────────────────────────────────
	// SELECCIÓN DE EQUIPOS
	// ─────────────────────────────────────────────

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
			if (mov instanceof MovRecuperarVidaEnergia || mov instanceof MovCintaDeMoebius) {
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

	private static List<Personaje> ordenarPorVelocidad(List<Personaje> a, List<Personaje> b) {
		List<Personaje> todos = new ArrayList<>();
		todos.addAll(a);
		todos.addAll(b);
		todos.sort((p1, p2) -> {
			int v1 = (int) (p1.getVelocidad() * p1.getMultiplicadorStat(estado.TipoStat.VELOCIDAD));
			int v2 = (int) (p2.getVelocidad() * p2.getMultiplicadorStat(estado.TipoStat.VELOCIDAD));
			if (v2 != v1)
				return Integer.compare(v2, v1);
			return rng.nextBoolean() ? 1 : -1;
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
}