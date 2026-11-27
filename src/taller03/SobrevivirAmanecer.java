// Renato Martínez - 21.776.430-0 - ITI
// Alonso Arriagada - 21.803.339-3 - ITI

package taller03;

public final class SobrevivirAmanecer implements Minijuego {
    @Override
    public String getNombre() {
        return "Sobrevivir al Amanecer";
    }

    @Override
    public String getDescripcion() {
        return "Aguanta en pie cuando salgan los primeros rayos de sol.";
    }

    @Override
    public Habilidad getHabilidadPremiada() {
        return Habilidad.REGENERARSE;
    }

    /** Calcula el potencial de Zombi en este minijuego. */
    @Override
    public double visitar(Zombi zombi) {
        return (double) zombi.getResistencia() * zombi.getVida() / 100.0;
    }

    /** Calcula el potencial de Vampiro en este minijuego. */
    @Override
    public double visitar(Vampiro vampiro) {
        return (double) vampiro.getResistencia() * vampiro.getResistenciaAlSol() / 100.0;
    }

    /** Calcula el potencial de Momia en este minijuego. */
    @Override
    public double visitar(Momia momia) {
        return ((double) momia.getResistencia() + momia.getVida()) / 2.0
                * (1.0 + momia.getLongitudVendajes() / 300.0);
    }

    /** Calcula el potencial de HombreLobo en este minijuego. */
    @Override
    public double visitar(HombreLobo hombreLobo) {
        return ((double) hombreLobo.getResistencia() + hombreLobo.getVida()) / 2.0
                * (5.0 - hombreLobo.getFaseLunar()) / 4.0;
    }
}
