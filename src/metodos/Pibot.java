package metodos;

/**
 * La clase Pibot realiza operaciones de pivoteo y triangulación en matrices.
 * Es utilizada para resolver sistemas de ecuaciones lineales, aplicando métodos de eliminación
 * mediante el pivoteo parcial y la triangulación.
 */
public class Pibot {
    // Variables para almacenar la matriz A, el vector b, y el número de filas del sistema.
    private int filas = 0;
    private Double[][] A;
    private Double[] b;

    // Variables auxiliares para el pivoteo.
    double factor = 0;
    int p = 0;  // Índice de pivote

    // Variable de intercambio utilizada para pivoteo.
    Double swap = 0.0;

    /**
     * Constructor que recibe la matriz de coeficientes A, el vector de términos independientes b
     * y el número de filas del sistema.
     * @param matrizA Matriz de coeficientes del sistema.
     * @param matrizB Vector de términos independientes.
     * @param cantFilas Número de filas (o ecuaciones) del sistema.
     */
    public Pibot(Double[][] matrizA, Double[] matrizB, int cantFilas) {
        this.A = matrizA;
        this.b = matrizB;
        this.filas = cantFilas;
    }

    /**
     * Realiza el pivoteo parcial en la matriz A y el vector b.
     * El pivoteo parcial busca el elemento máximo en la columna actual y lo coloca en la diagonal principal.
     * Si hay ceros en la diagonal, intercambia filas para evitar errores en los cálculos.
     */
    public void pibotear() {
        double errorMinimo = 1e-5; // Tolerancia para considerar ceros en la diagonal
        // Pivoteo parcial
        for (int jj = 0; jj < filas; jj++) {
            p = jj;  // Suponemos inicialmente que el pivote es el elemento en la diagonal principal
            if (Math.abs(A[jj][jj]) < errorMinimo) { // Si el pivote es pequeño, buscamos otro
                for (int ll = jj + 1; ll < filas; ll++) {
                    if (Math.abs(A[ll][jj]) > Math.abs(A[jj][jj])) {
                        p = ll;  // Actualizamos el pivote si encontramos uno mejor
                    }
                }
                // Intercambiamos las filas si es necesario
                if (p != jj) {
                    for (int mm = 0; mm < A[0].length; mm++) {
                        swap = A[p][mm];
                        A[p][mm] = A[jj][mm];
                        A[jj][mm] = swap;
                    }
                    // Intercambiamos también los términos en el vector b
                    swap = b[p];
                    b[p] = b[jj];
                    b[jj] = swap;
                }
            }
        }
    }

    /**
     * Realiza la triangulación de la matriz A aplicando pivoteo parcial.
     * Convierte la matriz en una forma triangular superior para resolver el sistema.
     */
    public void triangulacionConPivot() {
        double f;
        Double aux;
        double errorMinimo = 1e-5; // Tolerancia para considerar ceros en la diagonal
        int n = filas;
        for (int i = 0; i < n - 1; i++) {
            int cambio = 0;
            // Pivoteo si es necesario
            if (Math.abs(A[i][i]) < errorMinimo) {
                for (int j = i + 1; j <= n - 1; j++) {
                    if (Math.abs(A[j][i]) > errorMinimo) {
                        // Intercambio de filas
                        for (int k = i; k <= n - 1; k++) {
                            aux = A[i][k];
                            A[i][k] = A[j][k];
                            A[j][k] = aux;
                        }
                        aux = b[i];
                        b[i] = b[j];
                        b[j] = aux;
                        cambio = 1;
                        break;
                    }
                }
                if (cambio == 0) {
                    System.err.println("El sistema es singular, no se puede resolver.");
                } else {
                    System.out.println("Se realizó pivoteo.");
                }
            }
            // Eliminación Gaussiana
            for (int j = i + 1; j <= n - 1; j++) {
                f = (-A[j][i]) / A[i][i];
                for (int k = i; k <= n - 1; k++) {
                    A[j][k] = A[j][k] + f * A[i][k];
                }
                b[j] = b[j] + f * b[i];
            }
        }
    }

    /**
     * Realiza el pivoteo para la matriz utilizando el mayor valor de cada columna como pivote.
     */
    public void pivoteo_GPT() {
        for (int i = 0; i < filas - 1; i++) {
            int p = i;
            // Encuentra el valor máximo en la columna
            for (int j = i + 1; j < filas; j++) {
                if (Math.abs(A[j][i]) > Math.abs(A[p][i])) {
                    p = j;
                }
            }
            // Intercambiar filas
            Double[] tempRow = A[i];
            A[i] = A[p];
            A[p] = tempRow;

            double tempB = b[i];
            b[i] = b[p];
            b[p] = tempB;
        }
    }

    /**
     * Realiza la triangulación de la matriz A después del pivoteo.
     * Convierte la matriz en forma triangular superior para resolver el sistema de ecuaciones.
     * @param i Índice de la fila actual para aplicar la triangulación.
     */
    public void triangulacion_GPT(int i) {
        for (int j = i + 1; j < filas; j++) {
            double factor = A[j][i] / A[i][i];
            for (int k = i; k < filas; k++) {
                A[j][k] -= factor * A[i][k];
            }
            b[j] -= factor * b[i];
        }
    }

    // Métodos para imprimir matrices y vectores.

    /**
     * Imprime la matriz A.
     * @param matriz La matriz a imprimir.
     */
    public static void imprimirMatriz(Double[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Imprime el vector b.
     * @param vector El vector a imprimir.
     */
    public static void imprimirVector(Double[] vector) {
        for (int i = 0; i < vector.length; i++) {
            System.out.println("b[" + i + "] = " + vector[i]);
        }
    }

    // Métodos Getters para obtener el valor de las variables principales.
    public Double getSwap() {
        return swap;
    }

    public int getP() {
        return p;
    }

    public double getFactor() {
        return factor;
    }

    public Double[][] getA() {
        return A;
    }

    public Double[] getB() {
        return b;
    }
}
