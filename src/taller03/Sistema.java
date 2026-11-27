// Renato Martínez - 21.776.430-0 - ITI
// Alonso Arriagada - 21.803.339-3 - ITI

package taller03;

import java.util.ArrayList;
import java.util.LinkedList;

/** Administra los datos cargados y las reglas de una sesión de Spooky Season. */
public final class Sistema {
    private final ArrayList<Monstruo> monstruos = new ArrayList<>();
    private final ArrayList<Jugador> jugadores = new ArrayList<>();
    private final LinkedList<Resultado> historial = new LinkedList<>();
    private final Minijuego[] minijuegos = {
        new CarreraCementerio(), new CazaCalabazas(),
        new LanzamientoLapidas(), new SobrevivirAmanecer()
    };
    private String usuario;
    private int puntajeAcumulado;

    /** Valida los campos de una fila y agrega una instancia al catálogo. */
    public void cargarMonstruo(String[] campos) {
        String tipo = sinMarcaUtf8(campos[0]).strip();
        int cantidad = switch (tipo) {
            case "Zombi", "Momia" -> 6;
            case "Vampiro", "HombreLobo" -> 7;
            default -> throw new IllegalArgumentException("Tipo de monstruo desconocido.");
        };
        if (campos.length != cantidad) {
            throw new IllegalArgumentException("Se esperaban " + cantidad + " campos.");
        }

        int vida = entero(campos[1], "Vida", 1, 200);
        int velocidad = entero(campos[2], "Velocidad", 1, 100);
        int fuerza = entero(campos[3], "Fuerza", 1, 100);
        int resistencia = entero(campos[4], "Resistencia", 1, 100);

        Monstruo monstruo = switch (tipo) {
            case "Zombi" -> new Zombi(vida, velocidad, fuerza, resistencia,
                    entero(campos[5], "FuerzaDeMordida", 1, 100));
            case "Vampiro" -> new Vampiro(vida, velocidad, fuerza, resistencia,
                    entero(campos[5], "VelocidadDeVuelo", 1, 100),
                    entero(campos[6], "ResistenciaAlSol", 1, 100));
            case "Momia" -> new Momia(vida, velocidad, fuerza, resistencia,
                    entero(campos[5], "LongitudVendajes", 1, 300));
            case "HombreLobo" -> new HombreLobo(vida, velocidad, fuerza, resistencia,
                    entero(campos[5], "Ferocidad", 1, 100), entero(campos[6], "FaseLunar", 1, 4));
            default -> throw new IllegalArgumentException("Tipo de monstruo desconocido.");
        };
        monstruos.add(monstruo);
    }

    /** Carga el récord de una fila y conserva el mayor si se repite el nombre. */
    public void cargarJugador(String[] campos) {
        if (campos.length != 2) {
            throw new IllegalArgumentException("Se esperaban 2 campos.");
        }
        String nombre = validarNombre(sinMarcaUtf8(campos[0]));
        int puntaje = entero(campos[1], "PuntajeMaximo", 0, Integer.MAX_VALUE);
        guardarMejorPuntaje(nombre, puntaje);
    }

    /** Quita la marca de codificación cuando un editor la añade al archivo. */
    private String sinMarcaUtf8(String campo) {
        return campo.startsWith("\uFEFF") ? campo.substring(1) : campo;
    }

    /** Convierte un campo numérico y comprueba sus límites inclusivos. */
    private int entero(String campo, String atributo, int minimo, int maximo) {
        int valor;
        try {
            valor = Integer.parseInt(campo.strip());
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException(atributo + " debe ser un entero.");
        }
        if (valor < minimo || valor > maximo) {
            throw new IllegalArgumentException(atributo + " debe estar entre " + minimo + " y " + maximo + ".");
        }
        return valor;
    }

    /** Normaliza el nombre antes de usarlo como identificador del jugador. */
    private String validarNombre(String nombre) {
        nombre = nombre.strip();
        if (nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (nombre.contains(";")) {
            throw new IllegalArgumentException("El nombre no puede contener el carácter ';'.");
        }
        return nombre;
    }

    /** Abre una sesión limpia y devuelve el registro anterior, si existe. */
    public Jugador iniciarSesion(String nombre) {
        usuario = validarNombre(nombre);
        historial.clear();
        puntajeAcumulado = 0;
        int indice = buscarJugador(usuario);
        return indice < 0 ? null : jugadores.get(indice);
    }

    /** Busca la posición de un usuario por su nombre exacto. */
    private int buscarJugador(String nombre) {
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).nombre().equals(nombre)) {
                return i;
            }
        }
        return -1;
    }

    /** Agrega un récord o sustituye el existente si el puntaje es mayor. */
    private void guardarMejorPuntaje(String nombre, int puntaje) {
        int indice = buscarJugador(nombre);
        if (indice < 0) {
            jugadores.add(new Jugador(nombre, puntaje));
        } else if (puntaje > jugadores.get(indice).puntajeMaximo()) {
            jugadores.set(indice, new Jugador(nombre, puntaje));
        }
    }

    /** Devuelve el nombre de la sesión activa. */
    public String getUsuario() {
        return usuario;
    }

    /** Devuelve el puntaje acumulado de las partidas terminadas. */
    public int getPuntajeAcumulado() {
        return puntajeAcumulado;
    }

    /** Entrega una copia del catálogo en el orden del archivo. */
    public Monstruo[] getMonstruos() {
        return monstruos.toArray(new Monstruo[0]);
    }

    /** Entrega una copia de los minijuegos en el orden del menú. */
    public Minijuego[] getMinijuegos() {
        return minijuegos.clone();
    }

    /** Recorre el historial para recuperar el resultado de un minijuego. */
    public Resultado buscarResultado(Minijuego minijuego) {
        for (Resultado resultado : historial) {
            if (resultado.minijuego() == minijuego) {
                return resultado;
            }
        }
        return null;
    }

    /** Identifica si se usó esta instancia concreta del catálogo. */
    public boolean monstruoUsado(Monstruo monstruo) {
        for (Resultado resultado : historial) {
            if (resultado.monstruo() == monstruo) {
                return true;
            }
        }
        return false;
    }

    /** Devuelve solo las transformaciones disponibles en la sesión actual. */
    public Monstruo[] getMonstruosDisponibles() {
        ArrayList<Monstruo> disponibles = new ArrayList<>();
        for (Monstruo monstruo : monstruos) {
            if (!monstruoUsado(monstruo)) {
                disponibles.add(monstruo);
            }
        }
        return disponibles.toArray(new Monstruo[0]);
    }

    /** Consulta el potencial de la transformación en el minijuego. */
    public double calcularPotencial(Minijuego minijuego, Monstruo monstruo) {
        return minijuego.calcularPotencial(monstruo);
    }

    /** Valida la partida, aplica la habilidad elegida y actualiza el historial. */
    public Resultado jugar(Minijuego minijuego, Monstruo monstruo, Habilidad habilidad) {
        if (usuario == null || buscarResultado(minijuego) != null
                || !monstruos.contains(monstruo) || monstruoUsado(monstruo)) {
            throw new IllegalArgumentException("El minijuego o el monstruo no están disponibles.");
        }

        String mensaje = "";
        if (habilidad != null) {
            boolean disponible = false;
            for (Habilidad opcion : Habilidad.disponibles(monstruo)) {
                disponible |= opcion == habilidad;
            }
            if (!disponible) {
                throw new IllegalArgumentException("El monstruo no tiene esa habilidad.");
            }
            mensaje = habilidad.ejecutar(monstruo);
        }
        boolean efectiva = habilidad == minijuego.getHabilidadPremiada();
        double multiplicador = efectiva ? 1.5 : 1.0;
        int puntaje = (int) Math.round(calcularPotencial(minijuego, monstruo) * 10.0 * multiplicador);
        Resultado resultado = new Resultado(minijuego, monstruo, habilidad, mensaje, efectiva, puntaje);
        historial.add(resultado);
        puntajeAcumulado += puntaje;
        return resultado;
    }

    /** Devuelve las partidas en el orden en que fueron jugadas. */
    public Resultado[] getHistorial() {
        return historial.toArray(new Resultado[0]);
    }

    /** Devuelve los récords en el orden en que se cargaron o crearon. */
    public Jugador[] getJugadores() {
        return jugadores.toArray(new Jugador[0]);
    }

    /** Ordena una copia del ranking mediante inserción. */
    public Jugador[] getRanking() {
        Jugador[] ranking = getJugadores();
        for (int i = 1; i < ranking.length; i++) {
            Jugador jugador = ranking[i];
            int posicion = i;
            while (posicion > 0 && vaAntes(jugador, ranking[posicion - 1])) {
                ranking[posicion] = ranking[posicion - 1];
                posicion--;
            }
            ranking[posicion] = jugador;
        }
        return ranking;
    }

    /** Compara primero el récord y luego el nombre para resolver empates. */
    private boolean vaAntes(Jugador primero, Jugador segundo) {
        if (primero.puntajeMaximo() != segundo.puntajeMaximo()) {
            return primero.puntajeMaximo() > segundo.puntajeMaximo();
        }
        int comparacion = primero.nombre().compareToIgnoreCase(segundo.nombre());
        return comparacion < 0 || comparacion == 0 && primero.nombre().compareTo(segundo.nombre()) < 0;
    }

    /** Finaliza la sesión y devuelve el registro que existía antes del cierre. */
    public Jugador terminarSesion() {
        int indice = buscarJugador(usuario);
        Jugador anterior = indice < 0 ? null : jugadores.get(indice);
        if (!historial.isEmpty()) {
            guardarMejorPuntaje(usuario, puntajeAcumulado);
        }
        usuario = null;
        historial.clear();
        puntajeAcumulado = 0;
        return anterior;
    }
}
