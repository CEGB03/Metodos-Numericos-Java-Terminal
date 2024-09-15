package metodos.Regresion;

import metodos.LecturaMatriz;
import metodos.ListarArchivosDirectorio;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Clase para manejar diferentes tipos de regresiones.
 * Permite seleccionar entre regresión lineal y polinomial, y ejecutar el ajuste correspondiente.
 */
public class Regresiones {
    static Scanner sc = new Scanner(System.in); // Scanner para entrada de datos
    Double[][] A; // Matriz de coeficientes para las regresiones
    Double[] b; // Vector de constantes para las regresiones
    Double[] x; // Vector para almacenar los resultados de la regresión
    int filas = 0, columnas = 0; // Número de filas y columnas de la matriz
    String dir; // Directorio del archivo a leer

    /**
     * Constructor de la clase Regresiones.
     * Muestra un menú para seleccionar el tipo de regresión y procesa la opción seleccionada.
     *
     * @throws FileNotFoundException Si el archivo especificado no se encuentra.
     */
    public Regresiones() throws FileNotFoundException {
        // Mostrar el menú para seleccionar el tipo de regresión
        System.out.println("Seleccione:  \n 1 --> Lineal" +
                "\n 2 --> Polinomial(Cuadrados Minimos)" +
                "\n 3 --> Proximamente...");
        int regresiones = Integer.parseInt(sc.nextLine()); // Leer la opción seleccionada

        // Crear instancia para listar archivos en el directorio
        ListarArchivosDirectorio archivosDirectorio = new ListarArchivosDirectorio();
        switch (regresiones) {
            case 1:
                // Caso para regresión lineal
                System.out.println("Ingresar el nombre del archivo donde está la matriz");
                dir = sc.nextLine(); // Leer el nombre del archivo
                lectura(dir); // Leer el archivo para obtener la matriz
                RegresionLineal lineal = new RegresionLineal(A, filas, columnas); // Crear instancia de regresión lineal
                lineal.regresion(); // Ejecutar la regresión lineal
                break;

            case 2:
                // Caso para regresión polinomial
                System.out.println("Ingresar el nombre del archivo donde está la matriz");
                dir = sc.nextLine(); // Leer el nombre del archivo
                lectura(dir); // Leer el archivo para obtener la matriz
                RegresionPolinomial polinomial = new RegresionPolinomial(A, filas, columnas); // Crear instancia de regresión polinomial
                polinomial.regresion(); // Ejecutar la regresión polinomial
                break;

            case 3:
                // Caso futuro para otros tipos de regresión
                System.out.println("Opción para futuras implementaciones.");
                break;

            default:
                // Mensaje para opción no válida
                System.out.println("\n Se seleccionó mal la opción");
        }
    }

    /**
     * Lee una matriz desde un archivo especificado.
     * Inicializa las matrices y vectores necesarios para la regresión.
     *
     * @param s Nombre del archivo que contiene la matriz.
     * @throws FileNotFoundException Si el archivo especificado no se encuentra.
     */
    public void lectura(String s) throws FileNotFoundException {
        // Crear instancia de LecturaMatriz para leer el archivo
        LecturaMatriz lecturaMatriz = new LecturaMatriz(s);
        filas = lecturaMatriz.getFilas(); // Obtener el número de filas de la matriz
        columnas = lecturaMatriz.getColumnas(); // Obtener el número de columnas de la matriz
        A = new Double[filas][columnas]; // Inicializar la matriz de coeficientes
        b = new Double[filas]; // Inicializar el vector de constantes
        x = new Double[filas]; // Inicializar el vector de resultados
        A = lecturaMatriz.getmCarga(); // Cargar los datos de la matriz desde el archivo
    }
}
