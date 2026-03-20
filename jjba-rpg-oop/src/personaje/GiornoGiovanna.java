package personaje;

import item.GoldExperience;
import movimiento.BlancoMov;
import movimiento.TipoMov;
import movimiento.*;

/**
 * Giorno Giovanna — Usuario de Stand. Stats base: vida 80, ataque 80, defensa
 * 80, atkEsp 90, defEsp 90, vel 80 Con Gold Experience: ataque +10, defensa
 * +10, ataqueEspecial +40, velocidad +40
 */
public class GiornoGiovanna extends UsuarioDeStand {

	public GiornoGiovanna() {
		super("Giorno Giovanna", 80, 100, 80, 80, 90, 90, 80, new GoldExperience());

		// Muda Muda Muda — especial, potencia 120, PP 8, coste 50
		aprenderMovimiento(new MovAtaqueSimple("Muda Muda Muda", BlancoMov.ELEGIDO, TipoMov.ESPECIAL, 120, 1.0, 8, 50,
				0, "Lluvia de golpes de Gold Experience."));

		// Life Shot — físico, potencia 55, PP 24, coste 20, reduce velocidad objetivo
		aprenderMovimiento(new MovAtaqueReduceVelocidad("Life Shot", BlancoMov.ELEGIDO, TipoMov.FISICO, 55, 1.0, 24, 20,
				0, "Disparo de vida. Reduce la velocidad del objetivo en un nivel."));

		// Abioscudo — protección con prioridad +4
		aprenderMovimiento(new MovProteccion("Abioscudo"));

		// Abiogénesis — recupera 25% vida y 25% energía del usuario
		aprenderMovimiento(new MovRecuperarVidaEnergia("Abiogénesis", BlancoMov.USUARIO, 8, 20, 0.25, 0.25,
				"Regeneración biológica. Recupera 25% vida y energía propias. Falla si ambas están al máximo."));
	}
}
