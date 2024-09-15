package metodos.LocRaices.Cerrados;

import java.util.Scanner;

/**
 * Clase que implementa el método de bisección para encontrar raíces de una función.
 */
public class Biseccion {
    private Scanner sc = new Scanner(System.in); // Scanner para entrada de datos
    private Double a = 0.0; // Límite inferior del intervalo
    private Double b = 0.0; // Límite superior del intervalo
    private Double tolerancia = 0.0; // Tolerancia para detener el proceso
    private Double c = 0.0; // Valor del punto medio entre a y b
    private Double error = 0.0; // Error estimado entre los límites del intervalo
    private int iterator = 0; // Contador de iteraciones

    /**
     * Método que ejecuta el algoritmo de bisección.
     * Solicita los límites del intervalo y la tolerancia al usuario,
     * luego realiza iteraciones para encontrar la raíz de la función.
     */
    public void biseccion() {
        // Solicitar al usuario los límites del intervalo
        System.out.println("Ingrese el límite inferior del intervalo: ");
        a = Double.parseDouble(sc.nextLine()); // Leer el valor de a
        System.out.println("Ingrese el límite superior del intervalo: ");
        b = Double.parseDouble(sc.nextLine()); // Leer el valor de b
        System.out.println("Ingrese la tolerancia: ");
        tolerancia = Double.parseDouble(sc.nextLine()); // Leer el valor de la tolerancia

        // Verificar si el intervalo es válido (f(a) y f(b) deben tener signos opuestos)
        if (funcion(a) * funcion(b) < 0.0) {
            // Ejecutar el método de bisección hasta que el error sea menor que la tolerancia
            do {
                // Calcular el punto medio entre a y b
                c = (a + b) / 2;
                // Calcular el error como la mitad del tamaño del intervalo actual
                error = (b - a) / 2;

                // Verificar en qué parte del intervalo está la raíz
                if (funcion(a) * funcion(c) > 0) {
                    a = c; // Si f(a) y f(c) tienen el mismo signo, mover a al valor de c
                } else if (funcion(a) * funcion(c) < 0) {
                    b = c; // Si f(a) y f(c) tienen signos opuestos, mover b al valor de c
                } else {
                    break; // Si f(c) es 0, hemos encontrado la raíz exacta
                }

                iterator++; // Incrementar el contador de iteraciones

                // Repetir el proceso hasta que el error sea menor que la tolerancia
            } while (error > tolerancia);

            // Imprimir los resultados
            System.out.println("\nResultado de la raíz: " + c);
            System.out.println("Error estimado: " + error);
            System.out.println("Cantidad de iteraciones: " + iterator + "\n");

        } else {
            // Si f(a) y f(b) no tienen signos opuestos, el intervalo es inválido
            System.out.println("\nNo hay raíz o hay un número par de ellas\n");
        }
    }

    /**
     * Define la función cuya raíz se quiere encontrar.
     *
     * @param x Valor en el cual evaluar la función.
     * @return El resultado de la función para el valor dado.
     */
    double funcion(double x) {
        // Aquí se define la función f(x)
        // Ejemplo: f(x) = -2 + 7*x - 5*x^2 + 6*x^3
        return -2 + (7 * x) - (5 * x * x) + (6 * x * x * x);
    }
}
