// Renato Martínez - 21.776.430-0 - ITI
// Alonso Arriagada - 21.803.339-3 - ITI

package taller03;

import java.util.ArrayList;

/** Reúne los nombres del menú y conecta cada elección con su interfaz. */
public enum Habilidad {
    MORDER("Morder"),
    VOLAR("Volar"),
    REGENERARSE("Regenerarse"),
    ENFURECERSE("Enfurecerse");

    private final String nombre;

    Habilidad(String nombre) {
        this.nombre = nombre;
    }

    /** Nombre que se muestra en el menú y en el historial. */
    public String getNombre() {
        return nombre;
    }

    /** Detecta las interfaces que implementa el monstruo elegido. */
    public static Habilidad[] disponibles(Monstruo monstruo) {
        ArrayList<Habilidad> habilidades = new ArrayList<>();
        if (monstruo instanceof Mordedor) {
            habilidades.add(MORDER);
        }
        if (monstruo instanceof Volador) {
            habilidades.add(VOLAR);
        }
        if (monstruo instanceof Regenerable) {
            habilidades.add(REGENERARSE);
        }
        if (monstruo instanceof Furioso) {
            habilidades.add(ENFURECERSE);
        }
        return habilidades.toArray(new Habilidad[0]);
    }

    /** Invoca la interfaz correspondiente a una habilidad disponible. */
    public String ejecutar(Monstruo monstruo) {
        return switch (this) {
            case MORDER -> ((Mordedor) monstruo).morder();
            case VOLAR -> ((Volador) monstruo).volar();
            case REGENERARSE -> ((Regenerable) monstruo).regenerarse();
            case ENFURECERSE -> ((Furioso) monstruo).enfurecerse();
        };
    }
}
