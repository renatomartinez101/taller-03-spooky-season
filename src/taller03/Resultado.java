// Renato Martínez - 21.776.430-0 - ITI
// Alonso Arriagada - 21.803.339-3 - ITI

package taller03;

/** Registra una partida y el mensaje devuelto por la habilidad elegida. */
public record Resultado(Minijuego minijuego, Monstruo monstruo, Habilidad habilidad,
        String mensajeHabilidad, boolean habilidadEfectiva, int puntaje) {

    /** Devuelve el nombre de la habilidad que queda en el historial. */
    public String nombreHabilidad() {
        return habilidad == null ? "Ninguna" : habilidad.getNombre();
    }
}
