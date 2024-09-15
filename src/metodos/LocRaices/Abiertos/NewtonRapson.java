package metodos.LocRaices.Abiertos;

import java.util.Scanner;

import static java.lang.Math.*;  // Importar funciones matemáticas estáticas

/**
 * Clase que implementa el método de Newton-Raphson para encontrar raíces de una función.
 */
public class NewtonRapson {
    private Scanner sc = new Scanner(System.in);  // Escáner para entrada de datos del usuario
    private Double xViejo = 0.0;  // Variable para almacenar el valor anterior de x
    private Double error = 0.0;  // Variable para almacenar el error en cada iteración
    private Double tolerancia = 0.0;  // Tolerancia permitida para la aproximación de la raíz
    private Double xNuevo = 0.0;  // Variable para almacenar el valor nuevo de x en cada iteración
    private int contador = 0;  // Contador de iteraciones

    /**
     * Define la función objetivo f(x) para la cual se busca la raíz.
     *
     * @param x Valor en el cual evaluar la función.
     * @return El resultado de f(x) para el valor dado.
     */
    double f(double x){
        return x * x - 4;  // Función f(x) = x^2 - 4
    }

    /**
     * Define la derivada de la función objetivo f(x).
     *
     * @param x Valor en el cual evaluar la derivada.
     * @return El resultado de f'(x) para el valor dado.
     */
    double fPrima(double x){
        return 2 * x;  // Derivada de f(x), f'(x) = 2x
    }

    /**
     * Método que implementa el algoritmo de Newton-Raphson para encontrar la raíz de f(x).
     * Solicita los parámetros necesarios al usuario y realiza iteraciones hasta encontrar la raíz.
     */
    public void newtonRaphson(){
        // Solicita la tolerancia del usuario
        System.out.println("Ingrese la tolerancia\n");
        tolerancia = Double.parseDouble(sc.nextLine());  // Lee la tolerancia permitida

        // Solicita el valor inicial del punto
        System.out.println("Ingrese el punto inicial\n");
        xViejo = Double.parseDouble(sc.nextLine());  // Lee el valor inicial de x

        // Realiza iteraciones hasta que el error sea menor que la tolerancia o se alcancen 10000 iteraciones
        do {
            contador++;  // Incrementa el contador de iteraciones

            // Si la derivada es demasiado pequeña, muestra una advertencia y rompe el ciclo
            if (Math.abs(fPrima(xViejo)) < 1e-5) {
                System.out.println("\n********\nDERIVADA PEQUEÑA********\n");
                break;
            } else {
                // Calcula el nuevo valor de x usando la fórmula de Newton-Raphson
                xNuevo = xViejo - (f(xViejo) / fPrima(xViejo));
                error = abs(xNuevo - xViejo);  // Calcula el error como la diferencia entre xNuevo y xViejo
                xViejo = xNuevo;  // Actualiza xViejo con el nuevo valor calculado
            }

        } while (error > tolerancia && contador < 10000);  // Repite hasta que el error sea menor que la tolerancia

        // Muestra los resultados finales
        System.out.println("\n\n\nLa raiz de f es: " + xNuevo);
        System.out.println("\nEl valor del error en la raiz es de: " + error);
        System.out.println("\nLa resolucion del problema tomó " + contador + " iteraciones");
    }
}
