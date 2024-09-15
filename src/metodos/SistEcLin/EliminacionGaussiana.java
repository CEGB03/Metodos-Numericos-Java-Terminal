package metodos.SistEcLin;

import java.io.FileNotFoundException;
import metodos.LecturaMatriz;
import metodos.Pibot;

/**
 * Clase que implementa el método de eliminación gaussiana para resolver sistemas de ecuaciones lineales.
 * Se puede utilizar tanto con datos leídos desde un archivo como con matrices ya cargadas en memoria.
 * El proceso incluye pivoteo parcial, triangulación y retro-sustitución.
 */
public class EliminacionGaussiana {
    Double[][] A; // Matriz de coeficientes
    Double[] b;   // Vector de términos independientes
    Double[] x;   // Solución del sistema
    int filas = 0, columnas = 0; // Dimensiones de la matriz
    double factor = 0; // Factor utilizado en el proceso de pivoteo
    int p = 0; // Variable para almacenar el índice del pivote
    Double swap = (double) 0; // Variable temporal para intercambio en el pivoteo

    /**
     * Constructor que inicializa la matriz A y el vector b desde un archivo.
     *
     * @param s Ruta del archivo que contiene la matriz A y el vector b.
     * @throws FileNotFoundException Si el archivo no se encuentra.
     */
    public EliminacionGaussiana(String s) throws FileNotFoundException {
        // Se utiliza la clase LecturaMatriz para obtener los datos desde el archivo
        LecturaMatriz lecturaMatriz = new LecturaMatriz(s);
        filas = lecturaMatriz.getFilas(); // Asignar número de filas
        columnas = lecturaMatriz.getColumnas(); // Asignar número de columnas
        A = new Double[filas][columnas]; // Inicializar matriz A
        b = new Double[filas]; // Inicializar vector b
        x = new Double[filas]; // Inicializar vector de solución x
        A = lecturaMatriz.getmFinal(); // Obtener la matriz A desde el archivo
        b = lecturaMatriz.getB(); // Obtener el vector b desde el archivo
    }

    /**
     * Constructor que inicializa con datos ya cargados de la matriz A, el vector b y el vector x.
     * Este constructor se utiliza, por ejemplo, para casos como la regresión polinómica.
     *
     * @param A Matriz de coeficientes.
     * @param b Vector de términos independientes.
     * @param x Vector de solución.
     * @param filas Número de filas (dimensión) de la matriz A.
     */
    public EliminacionGaussiana(Double[][] A, Double[] b, Double[] x, int filas) {
        this.A = A; // Asignar matriz A
        this.b = b; // Asignar vector b
        this.x = x; // Asignar vector de solución x
        this.filas = filas; // Asignar número de filas
        this.columnas = filas; // La matriz debe ser cuadrada, por lo que filas = columnas
        eliminar(); // Ejecutar el proceso de eliminación gaussiana
    }

    /**
     * Método principal que ejecuta el algoritmo de eliminación gaussiana.
     * Comprueba si la matriz es cuadrada, realiza el pivoteo, verifica si es singular,
     * y finalmente realiza la retro-sustitución para encontrar la solución.
     */
    public void eliminar() {
        // Verificar si la matriz es cuadrada
        if (filas != columnas) {
            System.out.println(filas + "x" + columnas + "\nLa matriz no es cuadrada, no se puede resolver usando eliminacion gaussiana.");
            System.exit(20); // Termina el programa si la matriz no es cuadrada
        }

        // Realizar pivoteo parcial
        pivoteoParcial();

        // Verificar si la matriz es singular
        if (esMatrizSingular()) {
            System.err.println("Matriz singular, no se puede resolver.\n");
            System.exit(30); // Termina el programa si la matriz es singular
        }

        // Realizar la retro-sustitución para resolver el sistema
        retroSustitucion();

        // Imprimir el resultado final de la solución
        System.out.println("Matriz Solucion:");
        imprimirSolucion();
    }

    /**
     * Realiza el pivoteo parcial y la triangulación de la matriz A y el vector b.
     * Utiliza la clase Pibot para gestionar el proceso de pivoteo y triangulación.
     */
    private void pivoteoParcial() {
        // Inicialización del pivoteo usando la clase Pibot
        Pibot pibot = new Pibot(A, b, filas);
        pibot.pibotear(); // Realiza el pivoteo parcial

        // Actualizar los valores de A, b, factor y otras variables tras el pivoteo
        A = pibot.getA();
        b = pibot.getB();
        factor = pibot.getFactor();
        swap = pibot.getSwap();
        p = pibot.getP();

        // Realizar la triangulación superior
        pibot.triangulacionConPivot();
    }

    /**
     * Verifica si la matriz A es singular (si su determinante es 0).
     *
     * @return true si la matriz es singular, false en caso contrario.
     */
    private boolean esMatrizSingular() {
        int det = 1; // Variable para calcular el determinante
        for (int ii = 0; ii < filas; ii++) {
            det *= A[ii][ii]; // Multiplicar los elementos de la diagonal
        }
        System.out.println("det(A)= " + det);
        return det == 0; // Retornar si el determinante es 0 (matriz singular)
    }

    /**
     * Realiza la retro-sustitución para encontrar el vector solución x.
     * Este método utiliza la matriz triangular superior resultante de la eliminación gaussiana.
     */
    private void retroSustitucion() {
        // Recorre las filas de abajo hacia arriba para realizar la retro-sustitución
        for (int i = filas - 1; i >= 0; i--) {
            double suma = b[i]; // Iniciar la suma con el valor correspondiente de b
            for (int j = i + 1; j < filas; j++) {
                suma -= A[i][j] * x[j]; // Restar los productos ya conocidos
            }
            x[i] = suma / A[i][i]; // Resolver para la incógnita x[i]
        }
    }

    /**
     * Imprime la matriz A junto con el vector b.
     * Esto se utiliza principalmente para depuración y verificación visual del sistema.
     */
    public void imprimir() {
        int filas = A.length; // Número de filas de la matriz
        int columnas = A[0].length; // Número de columnas de la primera fila

        // Imprimir cada elemento de la matriz A
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(A[i][j] + " ");
            }
            System.out.println(); // Nueva línea al final de cada fila
        }
        System.out.println();
    }

    /**
     * Imprime el vector solución x, que contiene las soluciones del sistema de ecuaciones.
     */
    public void imprimirSolucion() {
        System.out.println("Solucion x:");
        for (int ii = 0; ii < filas; ii++) {
            System.out.println("x[" + (ii + 1) + "] = " + x[ii]); // Imprimir cada solución
        }
        System.out.println();
    }

    /**
     * Retorna el vector de soluciones x.
     *
     * @return Vector x.
     */
    public Double[] getX() {
        return x; // Retornar el vector de soluciones x
    }

    /**
     * Retorna el vector de términos independientes b.
     *
     * @return Vector b.
     */
    public Double[] getB() {
        return b; // Retornar el vector b
    }

    /**
     * Retorna la matriz de coeficientes A.
     *
     * @return Matriz A.
     */
    public Double[][] getA() {
        return A; // Retornar la matriz de coeficientes A
    }

    /**
     * Retorna el número de filas de la matriz A.
     *
     * @return Número de filas de la matriz.
     */
    public int getFilas() {
        return filas; // Retornar el número de filas de la matriz
    }
}
