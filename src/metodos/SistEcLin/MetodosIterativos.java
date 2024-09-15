package metodos.SistEcLin;

import metodos.LecturaMatriz;
import metodos.ListarArchivosDirectorio;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * La clase MetodosIterativos ofrece una interfaz para seleccionar y ejecutar métodos iterativos
 * para la resolución de sistemas de ecuaciones lineales. Los métodos disponibles incluyen Jacobi,
 * Gauss-Seidel y Gauss-Seidel con relajación. La clase permite al usuario ingresar el nombre de un
 * archivo que contiene la matriz de coeficientes y el vector de términos independientes, luego lee
 * estos datos del archivo y ejecuta el método iterativo seleccionado. Además, incluye una verificación
 * para asegurar que la matriz sea diagonalmente dominante, una condición necesaria para la convergencia
 * de algunos métodos iterativos.
 */
public class MetodosIterativos {
    static Scanner sc = new Scanner(System.in);  // Scanner para la entrada de datos
    Double[][] A;  // Matriz de coeficientes
    Double[] b;    // Vector de términos independientes
    Double[] x;    // Vector de soluciones
    int filas = 0, columnas = 0;  // Variables para el número de filas y columnas de la matriz
    String dir;  // Variable para almacenar la dirección del archivo de entrada

    // Constructor que permite al usuario seleccionar y ejecutar el método deseado
    public MetodosIterativos() throws FileNotFoundException {
        // Muestra el menú de opciones
        System.out.println("Seleccione:  \n 1 --> Jacobi" +
                "\n 2 --> Gauss Seidel" +
                "\n 3 --> Gauss Seidel con Relajacion\n");
        int iterativo = Integer.parseInt(sc.nextLine());  // Lee la opción seleccionada por el usuario

        // Crea un objeto para listar los archivos en el directorio
        ListarArchivosDirectorio archivosDirectorio = new ListarArchivosDirectorio();

        // Dependiendo de la selección del usuario, ejecuta el método correspondiente
        switch (iterativo) {
            case 1:
                System.out.println("Ingresar el nombre del archivo donde está la matriz");
                dir = sc.nextLine();  // Lee la dirección del archivo
                lectura(dir);  // Llama al método lectura() para leer la matriz del archivo
                Jacobi j = new Jacobi(A, b, filas);  // Crea una instancia del método Jacobi
                j.eliminar(sc);  // Ejecuta el método Jacobi
                break;

            case 2:
                System.out.println("Ingresar el nombre del archivo donde está la matriz");
                dir = sc.nextLine();  // Lee la dirección del archivo
                lectura(dir);  // Llama al método lectura() para leer la matriz del archivo
                GaussSeidel gs = new GaussSeidel(A, b, filas);  // Crea una instancia del método Gauss-Seidel
                gs.eliminar(sc);  // Ejecuta el método Gauss-Seidel
                break;

            case 3:
                System.out.println("Ingresar el nombre del archivo donde está la matriz");
                dir = sc.nextLine();  // Lee la dirección del archivo
                lectura(dir);  // Llama al método lectura() para leer la matriz del archivo
                GaussSeidelRelajacion gsr = new GaussSeidelRelajacion(A, b, filas);  // Crea una instancia de Gauss-Seidel con relajación
                gsr.eliminar(sc);  // Ejecuta el método Gauss-Seidel con relajación
                break;

            default:
                System.out.println("\nSe seleccionó mal la opción");  // Manejo de una opción inválida
        }
    }

    // Método para leer la matriz y el vector b desde un archivo
    public void lectura(String s) throws FileNotFoundException {
        LecturaMatriz lecturaMatriz = new LecturaMatriz(s);  // Crea una instancia de la clase LecturaMatriz
        filas = lecturaMatriz.getFilas();  // Obtiene el número de filas del archivo
        columnas = lecturaMatriz.getColumnas();  // Obtiene el número de columnas del archivo
        A = new Double[filas][columnas];  // Inicializa la matriz A
        b = new Double[filas];  // Inicializa el vector b
        x = new Double[filas];  // Inicializa el vector x (soluciones)
        A = lecturaMatriz.getmFinal();  // Obtiene la matriz de coeficientes desde el archivo
        b = lecturaMatriz.getB();  // Obtiene el vector b desde el archivo
    }

    // Método auxiliar que verifica si la matriz es diagonalmente dominante
    boolean diagonalmenteDominante(Double[][] a, int filas) {
        double suma = 0;
        int counter = 0;
        for (int i = 0; i < filas; i++) {
            suma = 0;
            counter++;
            // Suma los valores absolutos de los elementos que no están en la diagonal
            for (int j = 0; j < filas; j++) {
                if (j != i) {
                    suma += Math.abs(a[i][j]);
                }
            }

            // Verifica si el elemento diagonal es mayor que la suma de los elementos fuera de la diagonal
            if (Math.abs(a[i][i]) < suma)
                System.out.println("\nLa matriz no es diagonalmente dominante. Fila: " + counter + ".");

            // Verifica si hay ceros en la diagonal, lo que invalida el método iterativo
            if (a[i][i] == 0) {
                System.out.println("\nCeros en la diagonal");
                return false;  // Si hay un cero en la diagonal, se detiene el proceso
            }
        }
        return true;  // La matriz es diagonalmente dominante si pasa todas las pruebas
    }
}
