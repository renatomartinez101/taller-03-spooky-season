// Renato Martínez - 21.776.430-0 - ITI
// Alonso Arriagada - 21.803.339-3 - ITI

package taller03;

public final class CarreraCementerio implements Minijuego {
    @Override
    public String getNombre() {
        return "Carrera del Cementerio";
    }

    @Override
    public String getDescripcion() {
        return "Recorre el cementerio de punta a punta antes de que el sepulturero te vea.";
    }

    @Override
    public Habilidad getHabilidadPremiada() {
        return Habilidad.VOLAR;
    }

    /** Calcula el potencial de Zombi en este minijuego. */
    @Override
    public double visitar(Zombi zombi) {
        return (double) zombi.getVelocidad() * zombi.getResistencia() / 100.0;
    }

    /** Calcula el potencial de Vampiro en este minijuego. */
    @Override
    public double visitar(Vampiro vampiro) {
        return (vampiro.getVelocidad() + 2.0 * vampiro.getVelocidadDeVuelo()) / 2.0;
    }

    /** Calcula el potencial de Momia en este minijuego. */
    @Override
    public double visitar(Momia momia) {
        return momia.getVelocidad() * 100.0 / (100.0 + momia.getLongitudVendajes());
    }

    /** Calcula el potencial de HombreLobo en este minijuego. */
    @Override
    public double visitar(HombreLobo hombreLobo) {
        return (hombreLobo.getVelocidad() + hombreLobo.getFerocidad() / 5.0) * hombreLobo.getFaseLunar() / 4.0;
    }
}
