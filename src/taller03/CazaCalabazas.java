// Renato Martínez - 21.776.430-0 - ITI
// Alonso Arriagada - 21.803.339-3 - ITI

package taller03;

public final class CazaCalabazas implements Minijuego {
    @Override
    public String getNombre() {
        return "Caza de Calabazas";
    }

    @Override
    public String getDescripcion() {
        return "Muerde la mayor cantidad de calabazas del huerto en 60 segundos.";
    }

    @Override
    public Habilidad getHabilidadPremiada() {
        return Habilidad.MORDER;
    }

    /** Calcula el potencial de Zombi en este minijuego. */
    @Override
    public double visitar(Zombi zombi) {
        return (double) zombi.getFuerzaDeMordida() * zombi.getVida() / 100.0 + zombi.getResistencia() / 2.0;
    }

    /** Calcula el potencial de Vampiro en este minijuego. */
    @Override
    public double visitar(Vampiro vampiro) {
        return ((double) vampiro.getFuerza() + vampiro.getVelocidad()) / 3.0;
    }

    /** Calcula el potencial de Momia en este minijuego. */
    @Override
    public double visitar(Momia momia) {
        return (double) momia.getLongitudVendajes() * momia.getFuerza() / 200.0;
    }

    /** Calcula el potencial de HombreLobo en este minijuego. */
    @Override
    public double visitar(HombreLobo hombreLobo) {
        return (double) hombreLobo.getFerocidad() * hombreLobo.getFaseLunar() / 4.0
                + hombreLobo.getVelocidad() / 4.0;
    }
}
