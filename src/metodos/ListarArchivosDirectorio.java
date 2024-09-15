package metodos;

import java.io.File;
import java.util.Arrays;

/**
 * La clase ListarArchivosDirectorio lista todos los archivos con extensiones .dat y .txt
 * en el directorio actual donde se ejecuta el programa.
 */
public class ListarArchivosDirectorio {

    /**
     * Constructor que lista los archivos del directorio actual.
     * Muestra el directorio actual y los archivos con las extensiones especificadas.
     */
    public ListarArchivosDirectorio() {
        // Obtener el directorio actual donde se ejecuta el programa
        File directorioActual = new File(System.getProperty("user.dir"));
        System.out.println("Directorio actual: " + directorioActual.getAbsolutePath());

        // Listar todos los archivos en el directorio
        File[] archivos = directorioActual.listFiles();
        if (archivos != null) {
            // Ordenar los archivos alfabéticamente
            Arrays.sort(archivos, (File f1, File f2) -> f1.getName().compareToIgnoreCase(f2.getName()));

            System.out.println("Archivos disponibles en el directorio:");
            for (File archivo : archivos) {
                if (archivo.isFile()) {
                    if (archivo.getName().endsWith(".dat") || archivo.getName().endsWith(".txt")) {
                        System.out.println("Archivo: " + archivo.getName());
                    }
                }
            }
        } else {
            System.out.println("El directorio está vacío o no se pudo acceder.");
        }
    }
}
