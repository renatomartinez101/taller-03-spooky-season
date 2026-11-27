// Renato Martínez - 21.776.430-0 - ITI
// Alonso Arriagada - 21.803.339-3 - ITI

package taller03;

public final class HombreLobo extends Monstruo implements Mordedor, Furioso {
    private final int ferocidad;
    private final int faseLunar;

    /** Inicializa los atributos de la transformación. */
    public HombreLobo(int vida, int velocidad, int fuerza, int resistencia,
            int ferocidad, int faseLunar) {
        super("HombreLobo", vida, velocidad, fuerza, resistencia);
        this.ferocidad = ferocidad;
        this.faseLunar = faseLunar;
    }

    /** Devuelve la ferocidad del hombre lobo. */
    public int getFerocidad() {
        return ferocidad;
    }

    /** Devuelve la fase lunar entre 1 y 4. */
    public int getFaseLunar() {
        return faseLunar;
    }

    @Override
    public String describir() {
        return super.describir() + " | Ferocidad " + ferocidad + " | Fase lunar " + faseLunar;
    }

    @Override
    public double aceptar(VisitorMinijuego visitor) {
        return visitor.visitar(this);
    }

    @Override
    public String morder() {
        return "El hombre lobo lanza una dentellada.";
    }

    @Override
    public String enfurecerse() {
        return "El hombre lobo aúlla y desata su furia.";
    }
}
