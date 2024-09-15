package metodos.SistEcLin;

import java.util.Scanner;

/**
 * La clase Jacobi implementa el método de Jacobi para resolver sistemas de ecuaciones lineales.
 * Este método iterativo se basa en la descomposición de la matriz del sistema en una parte diagonal y una
 * parte no diagonal, y utiliza las soluciones de la iteración anterior para calcular las nuevas soluciones.
 * La clase incluye métodos para:
 * - Inicializar los vectores y la matriz del sistema.
 * - Ejecutar el algoritmo iterativo hasta que el error sea menor que una tolerancia especificada o se alcance
 *   un número máximo de iteraciones.
 * - Verificar si la matriz es diagonalmente dominante, lo cual es necesario para garantizar la convergencia del
 *   método.
 * La clase también proporciona una verificación de la matriz para asegurarse de que es diagonalmente dominante,
 * y maneja el cálculo del error para determinar la convergencia del método.
 */
class Jacobi {
    private Double[][] a;  // Matriz de coeficientes
    private final Double[] b;  // Vector de términos independientes
    private Double[] xViejo;  // Vector de soluciones de la iteración anterior
    private Double[] xNuevo;  // Vector de soluciones de la iteración actual
    int filas = 0;  // Número de ecuaciones (filas)
    private Double tolerancia = 0.0;  // Tolerancia para el criterio de parada
    private Double error = 0.0;  // Error calculado en cada iteración

    // Constructor que inicializa la matriz 'a', el vector 'b' y el número de filas
    public Jacobi(Double[][] A, Double[] b, int filas) {
        this.a = A;
        this.b = b;
        this.filas = filas;
    }

    // Método principal que ejecuta el algoritmo de Jacobi
    public void eliminar(Scanner sc) {
        System.out.println("\n\n***Ha seleccionado método de jacobi***\n\n");

        // Verificación de que la matriz sea diagonalmente dominante
        boolean check = diagonalmenteDominante(a, filas);
        if (!check)
            System.exit(0);  // Si no es diagonalmente dominante, se detiene la ejecución

        Double suma = 0.0;
        xNuevo = new Double[filas];  // Vector para almacenar la solución actual
        xViejo = new Double[filas];  // Vector para almacenar la solución anterior

        // Inicializa las soluciones con valores cero
        for (int i = 0; i < filas; i++) {
            xViejo[i] = 0.0;
            xNuevo[i] = xViejo[i];
        }

        int iteraciones = 0;  // Contador de iteraciones

        // Solicita al usuario que ingrese la tolerancia
        System.out.println("\nIngrese la tolerancia\n");
        tolerancia = Double.valueOf(sc.nextLine());

        // Bucle iterativo hasta que el error sea menor que la tolerancia o se alcance el límite de iteraciones
        do {
            iteraciones++;
            // Para cada fila del sistema de ecuaciones
            for (int i = 0; i < filas; i++) {
                suma = 0.0;
                // Realiza la suma de los términos excepto el de la diagonal
                for (int j = 0; j < filas; j++) {
                    if (j != i)
                        suma += a[i][j] * xViejo[j];  // Usa las soluciones de la iteración anterior
                }
                // Calcula la nueva solución para la variable i
                xNuevo[i] = (b[i] - suma) / a[i][i];
            }

            // Cálculo del error para verificar la convergencia
            suma = 0.0;
            for (int i = 0; i < filas; i++) {
                suma += (xNuevo[i] - xViejo[i]) * (xNuevo[i] - xViejo[i]);  // Diferencia cuadrada entre soluciones
            }
            error = Math.sqrt(suma);  // Raíz cuadrada de la suma de diferencias (norma Euclidiana)

            // Copia las soluciones nuevas a las viejas para la próxima iteración
            if (filas >= 0) System.arraycopy(xNuevo, 0, xViejo, 0, filas);
        } while (error > tolerancia && iteraciones < 10000);  // Criterio de parada

        // Muestra los resultados obtenidos
        System.out.println("\n El resultado es: \nxnuevo = [\t");
        for (int i = 0; i < filas; i++) {
            System.out.println(xNuevo[i] + "\t");
        }
        System.out.println("]\n La cantidad de iteraciones fueron:" + iteraciones + "\n El error es de " + error + ".");
    }

    // Verifica si la matriz es diagonalmente dominante
    boolean diagonalmenteDominante(Double[][] a, int filas) {
        double suma = 0;
        int counter = 0;
        for (int i = 0; i < filas; i++) {
            suma = 0;
            counter++;
            // Calcula la suma de los términos fuera de la diagonal
            for (int j = 0; j < filas; j++) {
                if (j != i) {
                    suma += Math.abs(a[i][j]);
                }
            }

            // Verifica si el valor absoluto del término en la diagonal es mayor que la suma de los términos fuera de la diagonal
            if (Math.abs(a[i][i]) < suma)
                System.out.println("\nLa matriz no es diagonalmente dominante. Fila: " + counter + ".");

            // Verifica si hay ceros en la diagonal
            if (a[i][i] == 0) {
                System.out.println("\nCeros en la diagonal");
                return false;  // No es posible continuar si hay un cero en la diagonal
            }
        }
        return true;
    }
}
