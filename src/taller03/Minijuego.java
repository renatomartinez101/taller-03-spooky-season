// Renato Martínez - 21.776.430-0 - ITI
// Alonso Arriagada - 21.803.339-3 - ITI

package taller03;

public interface Minijuego extends VisitorMinijuego {
    /** Devuelve el nombre que aparece en el menú. */
    String getNombre();
    /** Explica el objetivo del minijuego. */
    String getDescripcion();
    /** Devuelve la habilidad que mejora el puntaje del minijuego. */
    Habilidad getHabilidadPremiada();

    /** Solicita al monstruo el potencial mediante doble despacho. */
    default double calcularPotencial(Monstruo monstruo) {
        return monstruo.aceptar(this);
    }
}
