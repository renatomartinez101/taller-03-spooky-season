// Renato Martínez - 21.776.430-0 - ITI
// Alonso Arriagada - 21.803.339-3 - ITI

package taller03;

public final class LanzamientoLapidas implements Minijuego {
    @Override
    public String getNombre() {
        return "Lanzamiento de Lápidas";
    }

    @Override
    public String getDescripcion() {
        return "Lanza una lápida lo más lejos posible.";
    }

    @Override
    public Habilidad getHabilidadPremiada() {
        return Habilidad.ENFURECERSE;
    }

    /** Calcula el potencial de Zombi en este minijuego. */
    @Override
    public double visitar(Zombi zombi) {
        return (double) zombi.getFuerza() * zombi.getResistencia() / 100.0;
    }

    /** Calcula el potencial de Vampiro en este minijuego. */
    @Override
    public double visitar(Vampiro vampiro) {
        return vampiro.getFuerza() + vampiro.getVelocidadDeVuelo() / 4.0;
    }

    /** Calcula el potencial de Momia en este minijuego. */
    @Override
    public double visitar(Momia momia) {
        return momia.getFuerza() + momia.getLongitudVendajes() / 10.0;
    }

    /** Calcula el potencial de HombreLobo en este minijuego. */
    @Override
    public double visitar(HombreLobo hombreLobo) {
        return hombreLobo.getFuerza() + (double) hombreLobo.getFerocidad() * hombreLobo.getFaseLunar() / 8.0;
    }
}
