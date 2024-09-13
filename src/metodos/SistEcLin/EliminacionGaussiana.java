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
    int filas = 0, columnas = 0;
    double factor = 0;
    int p = 0;
    Double swap = (double) 0;

    /**
     * Constructor que inicializa la matriz A y el vector b desde un archivo.
     *
     * @param s Ruta del archivo que contiene la matriz A y el vector b.
     * @throws FileNotFoundException Si el archivo no se encuentra.
     */
    public EliminacionGaussiana(String s) throws FileNotFoundException {
        //System.out.println("s = " + s);
        LecturaMatriz lecturaMatriz = new LecturaMatriz(s);
        filas = lecturaMatriz.getFilas();
        columnas = lecturaMatriz.getColumnas();
        A = new Double[filas][columnas];
        b = new Double[filas];
        x = new Double[filas];
        A = lecturaMatriz.getmFinal();
        b = lecturaMatriz.getB();
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
        this.A = A;
        this.b = b;
        this.x = x;
        this.filas = filas;
        this.columnas = filas;
        eliminar();
    }

    /**
     * Método principal que ejecuta el algoritmo de eliminación gaussiana.
     * Comprueba si la matriz es cuadrada, realiza el pivoteo, verifica si es singular,
     * y finalmente realiza la retro-sustitución para encontrar la solución.
     */
    public void eliminar() {
        //System.out.println("Vuelta al main.\n");

        // Verificar si la matriz es cuadrada
        if (filas != columnas) {
            System.out.println(filas + "x" + columnas + "\nLa matriz no es cuadrada, no se puede resolver usando eliminacion gaussiana.");
            System.exit(20);
        }

        /*System.out.println("Imprimir antes de pibot");
        imprimir();*/

        // Realizar pivoteo parcial
        pivoteoParcial();

        // Verificar si la matriz es singular
        if (esMatrizSingular()) {
            System.err.println("Matriz singular, no se puede resolver.\n");
            System.exit(30);
        }

        // Realizar la retro-sustitución
        retroSustitucion();

        System.out.println("Matriz Solucion:");
        imprimirSolucion();
    }

    /**
     * Realiza el pivoteo parcial y la triangulación de la matriz A y el vector b.
     * Utiliza la clase Pibot para gestionar el proceso de pivoteo y triangulación.
     */
    private void pivoteoParcial() {
        // Inicialización del pivoteo
        Pibot pibot = new Pibot(A, b, filas);
        pibot.pibotear(); // Realiza el pivoteo parcial

        A = pibot.getA();
        b = pibot.getB();
        factor = pibot.getFactor();
        swap = pibot.getSwap();
        p = pibot.getP();

        //System.out.println("Imprimir despues de pibot y triangulacion");
        pibot.triangulacionConPivot(); // Triangulación superior
        //imprimir();
    }

    /**
     * Verifica si la matriz A es singular (si su determinante es 0).
     *
     * @return true si la matriz es singular, false en caso contrario.
     */
    private boolean esMatrizSingular() {
        int det = 1;
        for (int ii = 0; ii < filas; ii++) {
            det *= A[ii][ii];
        }
        System.out.println("det(A)= " + det);
        return det == 0;
    }

    /**
     * Realiza la retro-sustitución para encontrar el vector solución x.
     * Este método utiliza la matriz triangular superior resultante de la eliminación gaussiana.
     */
    private void retroSustitucion() {
        for (int i = filas - 1; i >= 0; i--) {
            double suma = b[i];
            for (int j = i + 1; j < filas; j++) {
                suma -= A[i][j] * x[j];
            }
            x[i] = suma / A[i][i];
        }
    }

    /**
     * Imprime la matriz A junto con el vector b.
     * Esto se utiliza principalmente para depuración y verificación visual del sistema.
     */
    public void imprimir() {
        int filas = A.length;
        int columnas = A[0].length; // Obtener el número de columnas de la primera fila

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(A[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Imprime el vector solución x, que contiene las soluciones del sistema de ecuaciones.
     */
    public void imprimirSolucion() {
        System.out.println("Solucion x:");
        for (int ii = 0; ii < filas; ii++) {
            System.out.println("x[" + (ii + 1) + "] = " + x[ii]);
        }
        System.out.println();
    }

    /**
     * Retorna el vector de soluciones x.
     *
     * @return Vector x.
     */
    public Double[] getX() {
        return x;
    }

    /**
     * Retorna el vector de términos independientes b.
     *
     * @return Vector b.
     */
    public Double[] getB() {
        return b;
    }

    /**
     * Retorna la matriz de coeficientes A.
     *
     * @return Matriz A.
     */
    public Double[][] getA() {
        return A;
    }

    /**
     * Retorna el número de filas de la matriz A.
     *
     * @return Número de filas de la matriz.
     */
    public int getFilas() {
        return filas;
    }
}
