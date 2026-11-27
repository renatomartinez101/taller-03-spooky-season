<!-- Renato Martínez - 21.776.430-0 -->
<!-- Alonso Arriagada - 21.803.339-3 -->
# Taller 03 · Spooky Season

Proyecto de consola de Programación Orientada a Objetos, segundo semestre de 2026. Cada jugador se transforma en un monstruo distinto para competir en cuatro minijuegos. El ranking conserva el mejor puntaje de sesión de cada participante.

## Integrantes

| Nombre | RUT | Carrera | GitHub |
| --- | --- | --- | --- |
| Renato Martínez | 21.776.430-0 | ITI | [renatomartinez101](https://github.com/renatomartinez101) |
| Alonso Arriagada | 21.803.339-3 | ITI | [shrd-cl](https://github.com/shrd-cl) |

## Ejecutar en Eclipse

1. Instala o selecciona un JDK 21 en **Window > Preferences > Java > Installed JREs**.
2. Clona el repositorio.

   ```sh
   git clone https://github.com/renatomartinez101/taller-03-spooky-season.git
   ```

3. En Eclipse, elige **File > Import > General > Existing Projects into Workspace**. Selecciona la carpeta clonada y marca **SpookySeason**.
4. Abre `src/taller03/Main.java` y elige **Run As > Java Application**.
5. Escribe las opciones en la vista **Console**. El directorio de trabajo debe ser la raíz del proyecto, donde están los archivos de datos.

## Ejecutar desde la terminal

Desde la raíz del proyecto, con el JDK 21 disponible en la terminal, compila y ejecuta el programa.

```sh
javac -encoding UTF-8 -d bin src/taller03/*.java
java -cp bin taller03.Main
```

## Estructura del proyecto

| Ruta o clase | Responsabilidad |
| --- | --- |
| `src/taller03/Main.java` | Lee el teclado y los TXT con `Scanner`, muestra los menús y escribe los registros con `FileWriter`. |
| `src/taller03/Sistema.java` | Valida y construye el modelo. Gestiona las colecciones, la sesión, el puntaje y el ranking. |
| `Monstruo` y sus cuatro subclases | Representan los atributos y las habilidades de cada transformación. |
| `Minijuego`, `VisitorMinijuego` y sus cuatro juegos | Resuelven las 16 fórmulas de potencial mediante doble despacho. |
| `Habilidad`, `Mordedor`, `Volador`, `Regenerable` y `Furioso` | Definen y ejecutan las opciones de habilidad. |
| `Jugador` y `Resultado` | Registran el récord histórico y cada partida del historial. |
| `Monstruos.txt` | Catálogo de entrada con una transformación por línea. |
| `Registros.txt` | Récords históricos en el formato `Jugador;PuntajeMaximo`. |
| `renatoMartinez_alonsoArriagada_Taller3POO.txt` | Datos de los integrantes y enlace de entrega. |

## Probar el taller

- Inicia como **Clapt**. La bienvenida debe mostrar un récord de **7600**.
- Juega **Carrera del Cementerio** con el **Vampiro** y elige **Volar**. El potencial debe ser **110.00** y el puntaje **1650**.
- En la misma sesión, intenta jugar nuevamente la carrera. Después elige **Lanzamiento de Lápidas** con el **Zombi** y selecciona **Morder**. El potencial debe ser **42.00** y el puntaje **420**. El historial debe acumular **2070**.
- Comprueba que el catálogo marca como usados al Vampiro y al Zombi. El ranking debe seguir encabezado por **Koshao**, con **7823**.
- Usa **0) Volver** desde la elección de habilidad para regresar a los monstruos. **3) No usar habilidad** completa el juego con el puntaje base.
- Termina la sesión e inicia con un usuario nuevo. Juega y sal con **6**. Al volver a ejecutar el programa, el usuario debe conservar su récord.
- En una copia de los datos, prueba entradas vacías, opciones fuera de rango y líneas inválidas. Si quitas `Registros.txt`, se vuelve a crear al cerrar una sesión. Si quitas `Monstruos.txt`, se informa el error y termina el programa.

## Decisiones de diseño

Cada fila válida del catálogo crea una instancia independiente, incluso si coincide con otra del mismo tipo. El historial se almacena en una `LinkedList`, y el ranking usa ordenamiento por inserción sobre una copia de los registros. Los empates se resuelven por nombre. Si un archivo contiene varios récords para el mismo nombre, se conserva el mayor.

Los TXT de datos incluyen los comentarios de los integrantes al inicio. Sus filas se leen en UTF-8 y se separan por punto y coma. Al cerrar una sesión, el programa vuelve a escribir los registros con los encabezados y los récords históricos.
