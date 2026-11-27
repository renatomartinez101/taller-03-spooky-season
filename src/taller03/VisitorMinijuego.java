// Renato Martínez - 21.776.430-0 - ITI
// Alonso Arriagada - 21.803.339-3 - ITI

package taller03;

public interface VisitorMinijuego {
    /** Calcula el potencial de Zombi en este minijuego. */
    double visitar(Zombi zombi);
    /** Calcula el potencial de Vampiro en este minijuego. */
    double visitar(Vampiro vampiro);
    /** Calcula el potencial de Momia en este minijuego. */
    double visitar(Momia momia);
    /** Calcula el potencial de HombreLobo en este minijuego. */
    double visitar(HombreLobo hombreLobo);
}
