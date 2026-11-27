// Renato Martínez - 21.776.430-0 - ITI
// Alonso Arriagada - 21.803.339-3 - ITI

package taller03;

public final class Momia extends Monstruo implements Regenerable, Furioso {
    private final int longitudVendajes;

    /** Inicializa los atributos de la transformación. */
    public Momia(int vida, int velocidad, int fuerza, int resistencia,
            int longitudVendajes) {
        super("Momia", vida, velocidad, fuerza, resistencia);
        this.longitudVendajes = longitudVendajes;
    }

    /** Devuelve la longitud de los vendajes en metros. */
    public int getLongitudVendajes() {
        return longitudVendajes;
    }

    @Override
    public String describir() {
        return super.describir() + " | Longitud de vendajes " + longitudVendajes + " m";
    }

    @Override
    public double aceptar(VisitorMinijuego visitor) {
        return visitor.visitar(this);
    }

    @Override
    public String regenerarse() {
        return "La momia se envuelve con una nueva capa de vendas.";
    }

    @Override
    public String enfurecerse() {
        return "La momia despierta la furia de su sarcófago.";
    }
}
