// Renato Martínez - 21.776.430-0 - ITI
// Alonso Arriagada - 21.803.339-3 - ITI

package taller03;

public final class Vampiro extends Monstruo implements Mordedor, Volador {
    private final int velocidadDeVuelo;
    private final int resistenciaAlSol;

    /** Inicializa los atributos de la transformación. */
    public Vampiro(int vida, int velocidad, int fuerza, int resistencia,
            int velocidadDeVuelo, int resistenciaAlSol) {
        super("Vampiro", vida, velocidad, fuerza, resistencia);
        this.velocidadDeVuelo = velocidadDeVuelo;
        this.resistenciaAlSol = resistenciaAlSol;
    }

    /** Devuelve la velocidad de vuelo del vampiro. */
    public int getVelocidadDeVuelo() {
        return velocidadDeVuelo;
    }

    /** Devuelve la resistencia al sol del vampiro. */
    public int getResistenciaAlSol() {
        return resistenciaAlSol;
    }

    @Override
    public String describir() {
        return super.describir() + " | Velocidad de vuelo " + velocidadDeVuelo
                + " | Resistencia al sol " + resistenciaAlSol;
    }

    @Override
    public double aceptar(VisitorMinijuego visitor) {
        return visitor.visitar(this);
    }

    @Override
    public String morder() {
        return "El vampiro muestra los colmillos y muerde.";
    }

    @Override
    public String volar() {
        return "El vampiro se eleva batiendo su capa.";
    }
}
