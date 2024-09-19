package metodos.Interpolacion;

import metodos.LecturaMatriz;
import metodos.ListarArchivosDirectorio;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Clase que gestiona los métodos de interpolación y permite seleccionar el tipo de interpolación a realizar.
 */
public class Interpolaciones {
    // Scanner para interactuar con el usuario
    static Scanner sc = new Scanner(System.in);

    // Matriz de coeficientes y vectores de resultados y soluciones
    Double[][] A;
    Double[] b;
    Double[] x;

    // Variables que almacenan el tamaño de la matriz
    int filas = 0, columnas = 0;

    // Ruta del archivo que contiene la matriz
    String dir;

    /**
     * Constructor de la clase Interpolaciones.
     * Muestra un menú para seleccionar el método de interpolación y procede a leer la matriz desde un archivo
     * para realizar la interpolación según la opción seleccionada.
     *
     * @throws FileNotFoundException Si el archivo especificado no se encuentra.
     */
    public Interpolaciones() throws FileNotFoundException {
        // Mostrar opciones de interpolación
        System.out.println("Seleccione:  \n 1 --> Lagrange" +
                                        "\n 2 --> Polinomial" +
                                        "\n 3 --> Otro método (aún no implementado)\n");
        int interpolaciones = Integer.parseInt(sc.nextLine());

        // Listar los archivos disponibles en el directorio
        ListarArchivosDirectorio archivosDirectorio = new ListarArchivosDirectorio();

        // Según la opción seleccionada, se realiza la lectura de la matriz y se invoca el método de interpolación
        switch (interpolaciones) {
            case 1:
                // Interpolación de Lagrange
                System.out.println("Ingresar el nombre del archivo donde está la matriz");
                dir = sc.nextLine();
                lectura(dir);  // Leer la matriz desde el archivo
                Lagrange l = new Lagrange(A, filas, columnas);
                l.interpolar(sc);  // Realizar interpolación
                break;

            case 2:
                // Interpolación Polinomial
                System.out.println("Ingresar el nombre del archivo donde está la matriz");
                dir = sc.nextLine();
                lectura(dir);  // Leer la matriz desde el archivo
                Polinomial p = new Polinomial(A, filas);
                p.interpolar();  // Realizar interpolación
                break;

            case 3:
                // Interpolación Spline Cubica
                //System.out.println("Ingresar el nombre del archivo donde está la matriz");
                //dir = sc.nextLine();
                //lectura(dir);  // Leer la matriz desde el archivo
                //SplineCubica sp = new SplineCubica(A, filas);
                //sp.interpolar();
                break;

            default:
                // Opción inválida
                System.out.println("\n Se seleccionó mal la opción");
        }
    }

    /**
     * Método para leer la matriz desde un archivo utilizando la clase LecturaMatriz.
     * Inicializa la matriz A y los vectores b y x.
     *
     * @param s Ruta del archivo que contiene la matriz.
     * @throws FileNotFoundException Si el archivo no se encuentra.
     */
    public void lectura(String s) throws FileNotFoundException {
        // Utilizar la clase LecturaMatriz para cargar la matriz desde el archivo
        LecturaMatriz lecturaMatriz = new LecturaMatriz(s);

        // Obtener el tamaño de la matriz
        filas = lecturaMatriz.getFilas();
        columnas = lecturaMatriz.getColumnas();

        // Inicializar las matrices y vectores
        A = new Double[filas][columnas];
        b = new Double[filas];
        x = new Double[filas];

        // Cargar la matriz con los datos leídos
        A = lecturaMatriz.getmCarga();
    }
}
