package metodos.SistEcLin;

import java.util.Scanner;

/**
 * La clase GaussSeidelRelajacion implementa el método de Gauss-Seidel con relajación
 * para resolver sistemas de ecuaciones lineales. Este método iterativo utiliza una técnica
 * de relajación para mejorar la convergencia de la solución. La clase incluye métodos para:
 * - Inicializar la matriz del sistema y el vector de términos independientes.
 * - Ejecutar el algoritmo iterativo de Gauss-Seidel con relajación.
 * - Verificar si la matriz es diagonalmente dominante, lo cual es necesario para garantizar
 *   la convergencia del método.
 * - Calcular el error para determinar si el proceso ha convergido dentro de una tolerancia especificada.
 */
class GaussSeidelRelajacion {
    // Variables para almacenar la matriz del sistema 'a', el vector de términos independientes 'b'
    // y otros parámetros del método como el número de filas, las soluciones anteriores y actuales.
    private final Double[][] a; // Matriz de coeficientes
    private final Double[] b;   // Vector de términos independientes
    int filas = 0;              // Número de filas (ecuaciones)
    private Double[] xViejo;     // Vector de soluciones de la iteración anterior
    private Double[] xNuevo;     // Vector de soluciones de la iteración actual
    private Double tolerancia = 0.0;  // Tolerancia para el criterio de parada
    private Double error = 0.0;       // Error calculado en cada iteración
    private Double factor_relajacion = 0.0;  // Factor de relajación (w)

    // Constructor que inicializa la matriz, el vector y el número de ecuaciones
    public GaussSeidelRelajacion(Double[][] A, Double[] b, int filas) {
        this.a = A;
        this.b = b;
        this.filas = filas;
    }

    // Método principal que ejecuta el algoritmo de Gauss-Seidel con relajación
    public void eliminar(Scanner sc) {
        System.out.println("\n\n***Ha seleccionado método de Gauss Seidel con Coeficiente de Relajacion***\n\n");

        // Verifica si la matriz es diagonalmente dominante antes de continuar
        if (!(diagonalmenteDominante(a, filas)))
            System.exit(0);

        Double suma = 0.0;
        xNuevo = new Double[filas]; // Vector de soluciones nuevas
        xViejo = new Double[filas]; // Vector de soluciones viejas

        // Inicializa las soluciones con valores cero
        for (int i = 0; i < filas; i++) {
            xViejo[i] = 0.0;
            xNuevo[i] = xViejo[i];
        }

        int iteraciones = 0; // Contador de iteraciones

        // Solicita la tolerancia y el factor de relajación al usuario
        System.out.println("\nIngrese la tolerancia\n");
        tolerancia = Double.valueOf(sc.nextLine());
        System.out.println("\nIngrese el factor relajacion\n");
        factor_relajacion = Double.valueOf(sc.nextLine());

        // Bucle iterativo hasta que el error sea menor que la tolerancia o se alcance el límite de iteraciones
        do {
            iteraciones++;
            for (int i = 0; i < filas; i++) {
                suma = 0.0;

                // Si es la primera ecuación, evita sumar con los valores de la diagonal
                if (i == 0) {
                    for (int j = 1; j < filas; j++) {
                        suma += a[i][j] * xNuevo[j]; // Suma de los términos de la ecuación
                    }
                    xNuevo[i] = (b[i] - suma) / a[i][i]; // Actualización de la solución para la ecuación i
                    xNuevo[i] = factor_relajacion * xNuevo[i] + (1 - factor_relajacion) * xViejo[i]; // Aplicación del factor de relajación
                } else {
                    // Para las demás ecuaciones
                    for (int j = 0; j < i; j++) {
                        suma += a[i][j] * xNuevo[j]; // Suma de los términos anteriores
                    }
                    for (int j = i + 1; j < filas; j++) {
                        suma += a[i][j] * xViejo[j]; // Suma de los términos posteriores
                    }

                    xNuevo[i] = (b[i] - suma) / a[i][i]; // Actualización de la solución para la ecuación i
                    xNuevo[i] = factor_relajacion * xNuevo[i] + (1 - factor_relajacion) * xViejo[i]; // Aplicación del factor de relajación
                }
            }

            // Cálculo del error para verificar la convergencia
            suma = 0.0;
            for (int i = 0; i < filas; i++) {
                suma += (xNuevo[i] - xViejo[i]) * (xNuevo[i] - xViejo[i]); // Diferencia cuadrada entre la solución nueva y la vieja
            }
            error = Math.sqrt(suma); // Raíz cuadrada de la suma de las diferencias cuadradas (norma Euclidiana)

            // Reasignación del vector de soluciones antiguas para la próxima iteración
            for (int i = 0; i < filas; i++) {
                xViejo[i] = xNuevo[i];
            }
        } while (error > tolerancia && iteraciones < 10000); // Criterio de parada

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
            // Suma de los valores fuera de la diagonal
            for (int j = 0; j < filas; j++) {
                if (j != i) {
                    suma += Math.abs(a[i][j]);
                }
            }

            // Verifica si el valor absoluto del término en la diagonal es mayor a la suma de los términos fuera de la diagonal
            if (Math.abs(a[i][i]) < suma)
                System.out.println("\nLa matriz no es diagonalmente dominante. Fila: " + counter + ".");

            // Verifica si hay ceros en la diagonal
            if (a[i][i] == 0) {
                System.out.println("\nCeros en la diagonal");
                return false;
            }
        }
        return true;
    }
}
