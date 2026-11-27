// Renato Martínez - 21.776.430-0 - ITI
// Alonso Arriagada - 21.803.339-3 - ITI

package taller03;

public final class Zombi extends Monstruo implements Mordedor, Regenerable {
    private final int fuerzaDeMordida;

    /** Inicializa los atributos de la transformación. */
    public Zombi(int vida, int velocidad, int fuerza, int resistencia,
            int fuerzaDeMordida) {
        super("Zombi", vida, velocidad, fuerza, resistencia);
        this.fuerzaDeMordida = fuerzaDeMordida;
    }

    /** Devuelve la fuerza de mordida del zombi. */
    public int getFuerzaDeMordida() {
        return fuerzaDeMordida;
    }

    @Override
    public String describir() {
        return super.describir() + " | Fuerza de mordida " + fuerzaDeMordida;
    }

    @Override
    public double aceptar(VisitorMinijuego visitor) {
        return visitor.visitar(this);
    }

    @Override
    public String morder() {
        return "El zombi da un mordisco con sus últimos dientes.";
    }

    @Override
    public String regenerarse() {
        return "El zombi vuelve a unir sus huesos.";
    }
}
