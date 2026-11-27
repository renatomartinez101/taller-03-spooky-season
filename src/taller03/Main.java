// Renato Martínez - 21.776.430-0 - ITI
// Alonso Arriagada - 21.803.339-3 - ITI

package taller03;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.Scanner;

/** Punto de entrada y consola del taller. */
public final class Main {
    private static final String ARCHIVO_MONSTRUOS = "Monstruos.txt";
    private static final String ARCHIVO_REGISTROS = "Registros.txt";
    private static final String COMENTARIO_RENATO = "# Renato Martínez - 21.776.430-0";
    private static final String COMENTARIO_ALONSO = "# Alonso Arriagada - 21.803.339-3";
    private static final int FIN_DE_ENTRADA = -1;

    private final Sistema sistema;
    private final Scanner teclado;
    private boolean registrosLegibles = true;

    /** Conecta la consola con el sistema de juego. */
    private Main(Sistema sistema, Scanner teclado) {
        this.sistema = sistema;
        this.teclado = teclado;
    }

    /** Carga los datos y abre la consola de Spooky Season. */
    public static void main(String[] args) {
        try (Scanner teclado = new Scanner(System.in)) {
            new Main(new Sistema(), teclado).ejecutar();
        }
    }

    /** Lee los archivos una vez y permite iniciar varias sesiones. */
    private void ejecutar() {
        System.out.println("Cargando archivos...");
        cargarArchivo(ARCHIVO_MONSTRUOS, true);
        cargarArchivo(ARCHIVO_REGISTROS, false);
        int monstruos = sistema.getMonstruos().length;
        System.out.println("> " + monstruos + " monstruos cargados desde " + ARCHIVO_MONSTRUOS);
        System.out.println("> " + sistema.getJugadores().length + " jugadores cargados desde " + ARCHIVO_REGISTROS);
        if (monstruos == 0) {
            advertir("No hay monstruos válidos. El programa terminará.");
            return;
        }

        System.out.println("\n=========================================");
        System.out.println("              SPOOKY SEASON");
        System.out.println("=========================================");
        while (iniciarSesion()) {
            boolean salir = mostrarMenuPrincipal();
            cerrarSesion();
            if (salir) {
                return;
            }
        }
    }

    /** Separa cada línea y muestra el motivo si el sistema descarta sus datos. */
    private void cargarArchivo(String nombre, boolean esCatalogo) {
        File archivo = new File(nombre);
        try (Scanner lector = new Scanner(archivo, StandardCharsets.UTF_8)) {
            int numeroLinea = 0;
            while (lector.hasNextLine()) {
                String linea = lector.nextLine();
                numeroLinea++;
                if (numeroLinea == 1 && linea.startsWith("\uFEFF")) {
                    linea = linea.substring(1);
                }
                if (numeroLinea <= 2 && (linea.equals(COMENTARIO_RENATO) || linea.equals(COMENTARIO_ALONSO))) {
                    continue;
                }
                String[] campos = linea.split(";", -1);
                try {
                    if (esCatalogo) {
                        sistema.cargarMonstruo(campos);
                    } else {
                        sistema.cargarJugador(campos);
                    }
                } catch (IllegalArgumentException error) {
                    advertir(nombre + ", línea " + numeroLinea + " descartada. " + error.getMessage());
                }
            }
            if (lector.ioException() != null) {
                throw lector.ioException();
            }
        } catch (FileNotFoundException error) {
            if (!archivo.exists() && !esCatalogo) {
                advertir(nombre + " no existe. Se creará al guardar la sesión.");
            } else {
                informarFalloLectura(nombre, esCatalogo);
            }
        } catch (IOException error) {
            informarFalloLectura(nombre, esCatalogo);
        }
    }

    /** Evita sobrescribir registros que no pudieron leerse del todo. */
    private void informarFalloLectura(String archivo, boolean esCatalogo) {
        advertir("No se pudo leer " + archivo + ".");
        if (!esCatalogo) {
            registrosLegibles = false;
        }
    }

    /** Pide un nombre válido y muestra la bienvenida correspondiente. */
    private boolean iniciarSesion() {
        while (true) {
            System.out.print("\nIngrese su nombre de usuario: ");
            if (!teclado.hasNextLine()) {
                return false;
            }
            try {
                Jugador anterior = sistema.iniciarSesion(teclado.nextLine());
                if (anterior == null) {
                    System.out.printf("%n¡Bienvenido, %s! Es tu primera vez jugando.%n", sistema.getUsuario());
                } else {
                    System.out.printf("%n¡Bienvenido de vuelta, %s! Tu récord actual es de %d puntos.%n",
                            anterior.nombre(), anterior.puntajeMaximo());
                }
                return true;
            } catch (IllegalArgumentException error) {
                advertir(error.getMessage());
            }
        }
    }

    /** Navega las opciones principales y avisa si se debe cerrar el programa. */
    private boolean mostrarMenuPrincipal() {
        while (true) {
            System.out.printf("%n------ MENÚ PRINCIPAL (%s) ------%n", sistema.getUsuario());
            System.out.println("1) Jugar un minijuego");
            System.out.println("2) Ver catálogo de monstruos");
            System.out.println("3) Ver historial de la sesión");
            System.out.println("4) Ver ranking de jugadores");
            System.out.println("5) Terminar sesión");
            System.out.println("6) Salir");

            switch (leerOpcion(1, 6)) {
                case 1 -> mostrarMinijuegos();
                case 2 -> mostrarCatalogo();
                case 3 -> mostrarHistorial();
                case 4 -> mostrarRanking();
                case 5 -> { return false; }
                case 6, FIN_DE_ENTRADA -> { return true; }
                default -> throw new IllegalStateException("Opción de menú inesperada.");
            }
        }
    }

    /** Muestra el estado de cada minijuego y acepta solo los pendientes. */
    private void mostrarMinijuegos() {
        Minijuego[] minijuegos = sistema.getMinijuegos();
        System.out.println("\n------ MINIJUEGOS ------");
        for (int i = 0; i < minijuegos.length; i++) {
            Resultado resultado = sistema.buscarResultado(minijuegos[i]);
            String estado = resultado == null ? "PENDIENTE" : "JUGADO - " + resultado.puntaje() + " pts";
            System.out.printf("%d) %-26s [%s]%n", i + 1, minijuegos[i].getNombre(), estado);
        }
        System.out.println("0) Volver");

        while (true) {
            int opcion = leerOpcion(0, minijuegos.length);
            if (opcion <= 0) {
                return;
            }
            Minijuego minijuego = minijuegos[opcion - 1];
            if (sistema.buscarResultado(minijuego) != null) {
                advertir("Ya jugaste " + minijuego.getNombre() + " en esta sesión.");
            } else if (jugarMinijuego(minijuego)) {
                return;
            }
        }
    }

    /** Deja elegir una transformación y permite retroceder antes de jugar. */
    private boolean jugarMinijuego(Minijuego minijuego) {
        Monstruo[] disponibles = sistema.getMonstruosDisponibles();
        if (disponibles.length == 0) {
            advertir("No quedan monstruos disponibles para esta sesión.");
            return true;
        }
        while (true) {
            System.out.printf("%n%s: %s%n", minijuego.getNombre(), minijuego.getDescripcion());
            System.out.println("\nElige el monstruo en el que te transformarás:");
            for (int i = 0; i < disponibles.length; i++) {
                System.out.printf("%d) %s%n", i + 1, disponibles[i].describir());
            }
            System.out.println("0) Volver");

            int opcion = leerOpcion(0, disponibles.length);
            if (opcion <= 0) {
                return opcion == FIN_DE_ENTRADA;
            }
            if (elegirHabilidad(minijuego, disponibles[opcion - 1])) {
                return true;
            }
        }
    }

    /** Muestra el potencial y registra la partida solo al confirmar una habilidad o ninguna. */
    private boolean elegirHabilidad(Minijuego minijuego, Monstruo monstruo) {
        System.out.println("\nTe comes un dulce de dudosa procedencia...");
        System.out.println("¡Te transformas en " + monstruo.getTipo() + "!");
        System.out.printf(Locale.ROOT, "Potencial para %s: %.2f%n",
                minijuego.getNombre(), sistema.calcularPotencial(minijuego, monstruo));
        System.out.println("\n¿Quieres usar una habilidad?");

        Habilidad[] habilidades = Habilidad.disponibles(monstruo);
        for (int i = 0; i < habilidades.length; i++) {
            System.out.printf("%d) %s%n", i + 1, habilidades[i].getNombre());
        }
        int sinHabilidad = habilidades.length + 1;
        System.out.println(sinHabilidad + ") No usar habilidad");
        System.out.println("0) Volver");

        int opcion = leerOpcion(0, sinHabilidad);
        if (opcion <= 0) {
            return opcion == FIN_DE_ENTRADA;
        }
        Habilidad habilidad = opcion == sinHabilidad ? null : habilidades[opcion - 1];
        Resultado resultado = sistema.jugar(minijuego, monstruo, habilidad);
        if (habilidad != null) {
            System.out.println(resultado.mensajeHabilidad());
            if (resultado.habilidadEfectiva()) {
                System.out.println("¡La habilidad fue efectiva! Puntaje x1.5");
            } else {
                System.out.println("La habilidad no tuvo efecto en este minijuego.");
            }
        }
        System.out.printf("%n>> Puntaje obtenido: %d puntos%n", resultado.puntaje());
        return true;
    }

    /** Lista cada transformación con sus atributos, habilidades y estado. */
    private void mostrarCatalogo() {
        System.out.println("\n------ CATÁLOGO DE MONSTRUOS ------");
        int numero = 1;
        for (Monstruo monstruo : sistema.getMonstruos()) {
            Habilidad[] habilidades = Habilidad.disponibles(monstruo);
            String estado = sistema.monstruoUsado(monstruo) ? "USADO" : "DISPONIBLE";
            System.out.printf("%d) %s | Habilidades: %s, %s [%s]%n", numero, monstruo.describir(),
                    habilidades[0].getNombre(), habilidades[1].getNombre(), estado);
            numero++;
        }
    }

    /** Imprime las partidas de la sesión y su puntaje acumulado. */
    private void mostrarHistorial() {
        System.out.println("\n------ HISTORIAL DE LA SESIÓN ------");
        Resultado[] historial = sistema.getHistorial();
        if (historial.length == 0) {
            System.out.println("Aún no jugaste ningún minijuego.");
        }
        for (int i = 0; i < historial.length; i++) {
            Resultado resultado = historial[i];
            System.out.printf("%d. %s | %s | Habilidad: %s | %d pts%n", i + 1,
                    resultado.minijuego().getNombre(), resultado.monstruo().describir(),
                    resultado.nombreHabilidad(), resultado.puntaje());
        }
        System.out.printf("Puntaje acumulado: %d pts%n", sistema.getPuntajeAcumulado());
    }

    /** Muestra los récords en el orden proporcionado por el sistema. */
    private void mostrarRanking() {
        System.out.println("\n------ RANKING ------");
        Jugador[] ranking = sistema.getRanking();
        if (ranking.length == 0) {
            System.out.println("Todavía no hay jugadores registrados.");
        }
        for (int i = 0; i < ranking.length; i++) {
            System.out.printf("%d. %-15s %d%n", i + 1, ranking[i].nombre(), ranking[i].puntajeMaximo());
        }
    }

    /** Informa el resultado de la sesión y guarda los registros. */
    private void cerrarSesion() {
        int total = sistema.getPuntajeAcumulado();
        boolean jugo = sistema.getHistorial().length > 0;
        Jugador anterior = sistema.terminarSesion();
        System.out.printf("%nSesión terminada. Puntaje total: %d puntos.%n", total);
        if (!jugo) {
            System.out.println("No jugaste ningún minijuego. Los récords se mantienen.");
        } else if (anterior == null) {
            System.out.println("¡Lograste tu primer récord!");
        } else if (total > anterior.puntajeMaximo()) {
            System.out.printf("¡Nuevo récord! Superaste tu marca anterior de %d puntos.%n",
                    anterior.puntajeMaximo());
        } else {
            System.out.printf("No superaste tu récord (%d). ¡Suerte para la próxima!%n",
                    anterior.puntajeMaximo());
        }
        guardarRegistros();
    }

    /** Escribe el archivo completo antes de reemplazar la versión anterior. */
    private void guardarRegistros() {
        if (!registrosLegibles) {
            advertir("No se sobrescribirá " + ARCHIVO_REGISTROS + " porque hubo un error de lectura.");
            return;
        }
        Path destino = Path.of(ARCHIVO_REGISTROS).toAbsolutePath();
        try {
            Path temporal = Files.createTempFile(destino.getParent(), "registros-", ".tmp");
            try {
                try (BufferedWriter escritor =
                        new BufferedWriter(new FileWriter(temporal.toFile(), StandardCharsets.UTF_8))) {
                    escritor.write(COMENTARIO_RENATO);
                    escritor.newLine();
                    escritor.write(COMENTARIO_ALONSO);
                    escritor.newLine();
                    for (Jugador jugador : sistema.getJugadores()) {
                        escritor.write(jugador.nombre() + ";" + jugador.puntajeMaximo());
                        escritor.newLine();
                    }
                }
                try {
                    Files.move(temporal, destino, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
                } catch (AtomicMoveNotSupportedException error) {
                    Files.move(temporal, destino, StandardCopyOption.REPLACE_EXISTING);
                }
            } finally {
                Files.deleteIfExists(temporal);
            }
        } catch (IOException error) {
            advertir("No se pudo guardar " + ARCHIVO_REGISTROS + ". " + error.getMessage());
            return;
        }
        System.out.println("Registros guardados en " + ARCHIVO_REGISTROS + ".");
    }

    /** Lee una opción entera del rango indicado o detecta el fin de la entrada. */
    private int leerOpcion(int minimo, int maximo) {
        while (true) {
            System.out.print("> ");
            if (!teclado.hasNextLine()) {
                return FIN_DE_ENTRADA;
            }
            try {
                int opcion = Integer.parseInt(teclado.nextLine().strip());
                if (opcion >= minimo && opcion <= maximo) {
                    return opcion;
                }
                advertir("Opción fuera de rango. Intente nuevamente.");
            } catch (NumberFormatException error) {
                advertir("Debe ingresar un número. Intente nuevamente.");
            }
        }
    }

    /** Imprime un aviso recuperable de la consola. */
    private void advertir(String mensaje) {
        System.out.println("[!] " + mensaje);
    }
}
