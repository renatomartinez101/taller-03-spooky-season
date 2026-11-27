// Renato Martínez - 21.776.430-0 - ITI
// Alonso Arriagada - 21.803.339-3 - ITI

package taller03;

/** Una transformación del catálogo. Cada instancia representa una fila distinta. */
public abstract class Monstruo {
    private final String tipo;
    private final int vida;
    private final int velocidad;
    private final int fuerza;
    private final int resistencia;

    /** Inicializa los atributos de la transformación. */
    protected Monstruo(String tipo, int vida, int velocidad, int fuerza, int resistencia) {
        this.tipo = tipo;
        this.vida = vida;
        this.velocidad = velocidad;
        this.fuerza = fuerza;
        this.resistencia = resistencia;
    }

    /** Devuelve el nombre del tipo de monstruo. */
    public String getTipo() {
        return tipo;
    }

    /** Devuelve los puntos de vida. */
    public int getVida() {
        return vida;
    }

    /** Devuelve la velocidad base. */
    public int getVelocidad() {
        return velocidad;
    }

    /** Devuelve la fuerza base. */
    public int getFuerza() {
        return fuerza;
    }

    /** Devuelve la resistencia base. */
    public int getResistencia() {
        return resistencia;
    }

    /** Presenta los atributos de la transformación en el catálogo. */
    public String describir() {
        return String.format("%-10s | Vida %d | Velocidad %d | Fuerza %d | Resistencia %d",
                tipo, vida, velocidad, fuerza, resistencia);
    }

    /** Invoca la fórmula del visitante para esta transformación. */
    public abstract double aceptar(VisitorMinijuego visitor);
}
