package metodos.LocRaices.Cerrados;

import java.util.Scanner;

/**
 * Clase que implementa el método de la falsa posición (Regula Falsi) para encontrar raíces de una función.
 */
public class RegulaFalsi {
    private Scanner sc = new Scanner(System.in); // Scanner para entrada de datos
    private Double a = 0.0; // Límite inferior del intervalo
    private Double b = 0.0; // Límite superior del intervalo
    private Double tolerancia = 0.0; // Tolerancia para detener el proceso
    private Double c = 0.0; // Valor calculado para la raíz usando el método de la falsa posición
    private Double error = 0.0; // Error estimado entre los límites del intervalo
    private int contador = 0; // Contador de iteraciones

    /**
     * Método que ejecuta el algoritmo de la falsa posición.
     * Solicita los límites del intervalo y la tolerancia al usuario,
     * luego realiza iteraciones para encontrar la raíz de la función.
     */
    public void regulaFalsi() {
        // Solicitar al usuario los límites del intervalo y la tolerancia
        System.out.println("Ingrese el límite inferior del intervalo: ");
        a = Double.parseDouble(sc.nextLine()); // Leer el valor de a
        System.out.println("Ingrese el límite superior del intervalo: ");
        b = Double.parseDouble(sc.nextLine()); // Leer el valor de b
        System.out.println("Ingrese la tolerancia: ");
        tolerancia = Double.parseDouble(sc.nextLine()); // Leer el valor de la tolerancia

        // Verificar si el intervalo es válido (f(a) y f(b) deben tener signos opuestos)
        if (funcion(a) * funcion(b) < 0.0) {
            do {
                contador++; // Incrementar el contador de iteraciones

                // Calcular el punto c usando la fórmula del método de la falsa posición
                c = (a * funcion(b) - b * funcion(a)) / (funcion(b) - funcion(a));

                // Verificar en qué parte del intervalo está la raíz
                if (funcion(a) * funcion(c) > 0) {
                    a = c; // Si f(a) y f(c) tienen el mismo signo, mover a al valor de c
                } else if (funcion(a) * funcion(c) < 0) {
                    b = c; // Si f(a) y f(c) tienen signos opuestos, mover b al valor de c
                } else {
                    break; // Si f(c) es 0, hemos encontrado la raíz exacta
                }

                // Calcular el error como la mitad del tamaño del intervalo actual
                error = (b - a) / 2;

                // Repetir el proceso hasta que el error sea menor que la tolerancia o se alcance el máximo de iteraciones
            } while (error > tolerancia && contador < 150000);

            // Imprimir los resultados
            System.out.println("\nResultado de la raíz: " + c);
            System.out.println("Error estimado: " + error);
            System.out.println("Cantidad de iteraciones: " + contador + "\n");

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
