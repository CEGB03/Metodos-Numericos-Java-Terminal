package metodos.LocRaices.Abiertos;

import java.util.Scanner;

import static java.lang.Math.*;  // Importar todas las funciones matemáticas estáticas

/**
 * Clase que implementa el método de punto fijo para encontrar raíces de una función.
 */
public class PuntoFijo {
    private Scanner sc = new Scanner(System.in);  // Escáner para entrada de datos del usuario
    private Double xViejo = 0.0;  // Almacena el valor anterior de x (punto inicial)
    private Double xNuevo = 0.0;  // Almacena el valor nuevo de x después de cada iteración
    private int contador = 0;  // Contador para el número de iteraciones
    private Double error = 0.0;  // Variable para almacenar el error en cada iteración
    private Double tolerancia = 0.0;  // Tolerancia permitida para la aproximación de la raíz

    /**
     * Define la función g(x) utilizada en el método de punto fijo.
     *
     * @param x Valor en el cual evaluar la función.
     * @return El resultado de g(x) para el valor dado.
     */
    double g(double x) {
        //return exp(-x);  // Función g(x) = e^(-x)
        //Funcion  ln(x^2+1)-sen(x)
        return Math.log(x*x+1)-Math.sin(x);// Funcion 1 b1 Primer parcial
    }

    /**
     * Método que implementa el algoritmo de punto fijo para encontrar una raíz.
     * Solicita los parámetros necesarios al usuario y realiza iteraciones hasta encontrar la raíz.
     */
    public void puntoFijo() {
        // Solicita la tolerancia que el usuario desea para el cálculo de la raíz
        System.out.println("Ingrese la tolerancia\n");
        tolerancia = Double.parseDouble(sc.nextLine());  // Lee la tolerancia

        // Solicita el punto inicial desde donde empezar el método iterativo
        System.out.println("Ingrese el punto inicial\n");
        xViejo = Double.parseDouble(sc.nextLine());  // Lee el punto inicial

        // Ciclo que itera hasta que el error sea menor que la tolerancia especificada
        do {
            contador++;  // Incrementa el contador de iteraciones

            // Verifica si la derivada numérica de g(x) es mayor o igual a 1
            // Si la derivada es >= 1, el método no converge
            if (abs((g(xViejo + 0.01) - g(xViejo)) / 0.01) >= 1) {
                System.out.println("\n\nNo cumple con el criterio de convergencia. El metodo diverge (|g'(x)|>1)");
                break;  // Si no cumple con el criterio de convergencia, termina el ciclo
            } else {
                // Calcula el nuevo valor de x usando la función g(x)
                xNuevo = g(xViejo);
                error = abs(xNuevo - xViejo);  // Calcula el error entre el valor viejo y el nuevo
                xViejo = xNuevo;  // Actualiza xViejo con el nuevo valor
            }

        } while (error > tolerancia);  // Repite el ciclo hasta que el error sea menor que la tolerancia

        // Imprime los resultados finales
        System.out.println("\n\n\nEl punto fijo de g(x), es decir la raiz de f es: " + xNuevo);
        System.out.println("\nEl valor del error en la raiz es de: " + error);
        System.out.println("\nLa resolucion del problema tomó " + contador + " iteraciones");
    }
}
