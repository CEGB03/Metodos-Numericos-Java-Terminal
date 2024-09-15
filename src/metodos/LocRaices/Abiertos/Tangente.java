package metodos.LocRaices.Abiertos;

import java.util.Scanner;

import static java.lang.Math.abs;

/**
 * Clase que implementa el método de la tangente (o método de Newton-Raphson modificado)
 * para encontrar raíces de una función.
 */
public class Tangente {
    private Scanner sc = new Scanner(System.in); // Scanner para entrada de datos
    private Double xViejo = 0.0; // Punto anterior
    private Double xActual = 0.0; // Punto inicial
    private Double xNuevo = 0.0; // Valor de la nueva aproximación
    private Double error = 0.0; // Diferencia entre xNuevo y xActual (error)
    private Double tolerancia = 0.0; // Tolerancia definida por el usuario para detener el ciclo
    private int contador = 0; // Contador de iteraciones

    /**
     * Define la función cuya raíz se quiere encontrar.
     *
     * @param x Valor en el cual evaluar la función.
     * @return El resultado de la función para el valor dado.
     */
    double f(double x) {
        return x * x - 4;  // Ejemplo de función: f(x) = x^2 - 4
    }

    /**
     * Define la derivada de la función.
     *
     * @param x Valor en el cual evaluar la derivada.
     * @return El resultado de la derivada para el valor dado.
     */
    double fPrima(double x) {
        return 2 * x;  // Derivada de la función: f'(x) = 2*x
    }

    /**
     * Método que implementa el algoritmo de tangente (o método de Newton-Raphson modificado).
     * Solicita los puntos y la tolerancia al usuario, y realiza iteraciones para encontrar la raíz.
     */
    public void tangente() {
        // Solicitar al usuario la tolerancia permitida
        System.out.println("Ingrese la tolerancia\n");
        tolerancia = Double.parseDouble(sc.nextLine());  // Leer la tolerancia

        // Solicitar al usuario el punto anterior para el método de tangente
        System.out.println("Ingrese el punto anterior\n");
        xViejo = Double.parseDouble(sc.nextLine());  // Leer el punto anterior

        // Solicitar al usuario el punto inicial para comenzar el método
        System.out.println("Ingrese el punto inicial\n");
        xActual = Double.parseDouble(sc.nextLine());  // Leer el punto inicial

        // Ciclo que continúa hasta que el error sea menor que la tolerancia o se exceda el número máximo de iteraciones
        do {
            contador++;  // Incrementar el contador de iteraciones

            // Si la derivada de la función es demasiado pequeña, se detiene el método para evitar divisiones por cero
            if (Math.abs(fPrima(xViejo)) < 1e-5) {
                System.out.println("\n********\nDERIVADA PEQUEÑA********\n");
                break;  // Salir del ciclo si la derivada es muy pequeña
            } else {
                // Calcular la aproximación usando el método de la tangente (o secante)
                double aprox = (f(xViejo) - f(xActual)) / (xViejo - xActual);
                xNuevo = xActual - (f(xActual) / aprox);  // Nueva aproximación de la raíz

                // Calcular el error como la diferencia absoluta entre xNuevo y xActual
                error = abs(xNuevo - xActual);

                // Actualizar los valores para la siguiente iteración
                xViejo = xActual;  // Actualizar el valor viejo de x
                xActual = xNuevo;  // Actualizar el valor actual con la nueva aproximación
            }

            // Continuar el ciclo mientras el error sea mayor que la tolerancia o el número de iteraciones sea menor que 10000
        } while (error > tolerancia && contador < 10000);

        // Mostrar los resultados finales al usuario
        System.out.println("\n\n\nLa raíz de f es: " + xNuevo);  // Mostrar la raíz calculada
        System.out.println("\nEl valor del error en la raíz es de: " + error);  // Mostrar el error
        System.out.println("\nLa resolución del problema tomó " + contador + " iteraciones");  // Mostrar el número de iteraciones realizadas
    }
}
