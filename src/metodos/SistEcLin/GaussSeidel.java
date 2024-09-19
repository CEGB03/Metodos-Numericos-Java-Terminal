package metodos.SistEcLin;

import metodos.Pibot;

import java.util.Scanner;

/**
 * Clase que implementa el método iterativo de Gauss-Seidel para resolver sistemas de ecuaciones lineales.
 * Utiliza una matriz de coeficientes y un vector de términos independientes para realizar las iteraciones.
 * Además, verifica si la matriz es diagonalmente dominante antes de iniciar el proceso.
 */
class GaussSeidel {
    private final Double[][] a;  // Matriz de coeficientes
    private final Double[] b;    // Vector de términos independientes
    private Double[] xViejo;     // Soluciones de la iteración anterior
    private Double[] xNuevo;     // Soluciones de la iteración actual
    int filas = 0;               // Número de filas de la matriz (dimensión)
    private Double tolerancia = 0.0; // Valor de tolerancia para el criterio de convergencia
    private Double error = 0.0;      // Error calculado en cada iteración

    /**
     * Constructor que inicializa la matriz de coeficientes y el vector de términos independientes.
     *
     * @param A Matriz de coeficientes.
     * @param b Vector de términos independientes.
     * @param filas Número de filas (dimensión) de la matriz.
     */
    public GaussSeidel(Double[][] A, Double[] b, int filas) {
        this.a = A;           // Asignar la matriz de coeficientes
        this.b = b;           // Asignar el vector de términos independientes
        this.filas = filas;   // Asignar el número de filas (dimensión)
    }

    /**
     * Método que ejecuta el algoritmo de Gauss-Seidel. Solicita al usuario que ingrese una tolerancia
     * para el criterio de convergencia y realiza iteraciones hasta que el error sea menor que esta tolerancia
     * o se alcance el límite de 10,000 iteraciones.
     *
     * @param sc Objeto Scanner para la entrada de datos desde la consola.
     */
    public void eliminar(Scanner sc) {

        System.out.println("\n\n***Ha seleccionado método de Gauss Seidel***\n\n");
        Pibot pibot = new Pibot(a, b, filas);
        pibot.pivoteo_GPT();
        imprimirMatriz();
        // Verificar si la matriz es diagonalmente dominante antes de proceder
        boolean check = diagonalmenteDominante(a, filas);
        if (!check) System.exit(0);

        Double suma;
        xNuevo = new Double[filas];  // Vector para las soluciones nuevas
        xViejo = new Double[filas];  // Vector para las soluciones anteriores

        // Inicializar los vectores de solución en 0
        for (int i = 0; i < filas; i++) {
            xViejo[i] = 0.0;
            xNuevo[i] = xViejo[i];
        }

        int iteraciones = 0;
        System.out.println("\nIngrese la tolerancia\n");
        tolerancia = Double.valueOf(sc.nextLine());  // Leer la tolerancia desde el usuario

        // Comenzar las iteraciones del método de Gauss-Seidel
        do {
            iteraciones++;
            for (int i = 0; i < filas; i++) {
                suma = 0.0;

                // Cálculo del nuevo valor de x[i] utilizando las soluciones actualizadas
                if (i == 0) {
                    for (int j = 1; j < filas; j++) {
                        suma += a[i][j] * xNuevo[j];
                    }
                    xNuevo[i] = (b[i] - suma) / a[i][i];
                } else {
                    for (int j = 0; j < i; j++) {
                        suma += a[i][j] * xNuevo[j];
                    }

                    for (int j = i + 1; j < filas; j++) {
                        suma += a[i][j] * xViejo[j];
                    }

                    xNuevo[i] = (b[i] - suma) / a[i][i];
                }
            }

            // Calcular el error como la diferencia entre las soluciones nuevas y viejas
            suma = 0.0;
            for (int i = 0; i < filas; i++) {
                suma += (xNuevo[i] - xViejo[i]) * (xNuevo[i] - xViejo[i]);
            }
            error = Math.sqrt(suma);  // Calcular el error como la raíz cuadrada de la suma de los cuadrados de las diferencias

            // Actualizar el vector de soluciones viejas para la próxima iteración
            for (int i = 0; i < filas; i++) {
                xViejo[i] = xNuevo[i];
            }

        } while (error > tolerancia && iteraciones < 10000);  // Continuar iterando hasta que el error sea menor a la tolerancia

        // Imprimir el resultado final
        System.out.println("\n El resultado es: \nxnuevo = [\t");
        for (int i = 0; i < filas; i++) {
            System.out.println(xNuevo[i] + "\t");
        }

        System.out.println("]\n La cantidad de iteraciones fueron:" + iteraciones + "\n El error es de " + error + ".");
    }

    /**
     * Verifica si la matriz es diagonalmente dominante.
     * Una matriz es diagonalmente dominante si el valor absoluto de cada elemento en la diagonal es mayor que
     * la suma de los valores absolutos de los otros elementos en su fila.
     *
     * @param a Matriz de coeficientes.
     * @param filas Número de filas (dimensión) de la matriz.
     * @return true si la matriz es diagonalmente dominante, false en caso contrario.
     */
    boolean diagonalmenteDominante(Double[][] a, int filas) {
        double suma;
        int counter = 0;

        for (int i = 0; i < filas; i++) {
            suma = 0;
            counter++;
            for (int j = 0; j < filas; j++) {
                if (j != i) {
                    suma += Math.abs(a[i][j]);  // Sumar los valores absolutos de los elementos fuera de la diagonal
                }
            }

            // Verificar si la matriz no es diagonalmente dominante
            if (Math.abs(a[i][i]) < suma) {
                System.out.println("\nLa matriz no es diagonalmente dominante. Fila: " + counter + ".");
            }

            // Verificar si hay ceros en la diagonal
            if (a[i][i] == 0) {
                System.out.println("\nCeros en la diagonal");
                return false;
            }
        }
        return true;  // Retornar true si la matriz es diagonalmente dominante
    }
    public void imprimirMatriz() {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }
}
