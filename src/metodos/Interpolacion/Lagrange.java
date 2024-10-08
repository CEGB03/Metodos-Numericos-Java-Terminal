package metodos.Interpolacion;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Clase que realiza la interpolación polinómica de Lagrange utilizando una matriz de datos.
 */
public class Lagrange {
    // Matriz de datos (pares de coordenadas x e y)
    private final Double[][] m;
    // Vectores que almacenan los valores de x e y
    private Double[] x;
    private Double[] y;
    // Número de filas y columnas de la matriz
    private final int filas;
    private final int columnas;

    /**
     * Función de referencia para calcular el error entre el valor real y el interpolado.
     * En este caso, f(x) = x + 2/x.
     *
     * @param x El valor en el que se evalúa la función.
     * @return El valor de la función f(x).
     */
    private double func(double x) {
        //return x + 2 / x;
        //return 0.9999961926217174*Math.pow(x,2)+-1.999974103763422;//Modelo de parcial
        //Funcion  ln(x^2+1)-sen(x)
        return Math.log(Math.pow(x,2)+1)-Math.sin(x);// Funcion Eje 1 Primer parcial
    }
    private double funPol(double x) {
        return -0.148 * (x - 1.2) / (1.0 - 1.2) * (x - 1.5) / (1.0 - 1.5) * (x - 1.75) / (1.0 - 1.75) * (x - 2.0) / (1.0 - 2.0) *
                -0.04 * (x - 1.0) / (1.2 - 1.0) * (x - 1.5) / (1.2 - 1.5) * (x - 1.75) / (1.2 - 1.75) * (x - 2.0) / (1.2 - 2.0) *
                0.0181 * (x - 1.0) / (1.5 - 1.0) * (x - 1.2) / (1.5 - 1.2) * (x - 1.75) / (1.5 - 1.75) * (x - 2.0) / (1.5 - 2.0) *
                0.419 * (x - 1.0) / (1.75 - 1.0) * (x - 1.2) / (1.75 - 1.2) * (x - 1.5) / (1.75 - 1.5) * (x - 2.0) / (1.75 - 2.0) *
                0.7 * (x - 1.0) *(x-1.2)/(2.0-1.2)*(x-1.5)/(2.0-1.5)*(x-1.75)/(2.0-1.75);
    }

    /**
     * Constructor de la clase Lagrange.
     * Recibe una matriz con los puntos de interpolación y separa los valores de x e y.
     *
     * @param A Matriz de entrada con los puntos de interpolación.
     * @param filas Número de filas (puntos de interpolación).
     * @param columnas Número de columnas (se asume que es 2 para pares x, y).
     */
    public Lagrange(Double[][] A, int filas, int columnas) {
        this.m = A;
        this.filas = filas;
        this.columnas = columnas + 1;  // Se agrega una columna al valor recibido
        separarXY();  // Separa los valores de x e y desde la matriz
    }

    /**
     * Método para realizar la interpolación de Lagrange.
     * Calcula el valor interpolado para un coeficiente ingresado por el usuario y el error asociado.
     *
     * @param sc Scanner para leer la entrada del usuario.
     */
    public void interpolar(Scanner sc) {
        System.out.println("\n***Ha seleccionado método de Lagrange***\n");

        // Definir el coeficiente a interpolar y calcular el error
        double coeficienteInterpolador;
        double e;

        // Solicitar al usuario el valor a interpolar
        System.out.println("Recuerde haber definido la función previamente");
        System.out.println("Ingrese el valor a interpolar");
        coeficienteInterpolador = Double.parseDouble(sc.nextLine());

        // Realizar la interpolación de Lagrange
        Double sum = 0.0;
        for (int i = 0; i < filas; i++) {
            Double producto = 1.0;
            for (int j = 0; j < filas; j++) {
                if (j != i) {
                    producto *= ((coeficienteInterpolador - x[j]) / (x[j] - x[i]));
                }
            }
            sum += y[i] * producto;
        }

        // Calcular el error absoluto con respecto a la función definida
        e = Math.abs(func(coeficienteInterpolador) - sum);

       /* Double evaluarPolinomio = funPol(1.6);
        Double funEva = func(evaluarPolinomio);

*/
        // Mostrar el resultado de la interpolación y el error
        System.out.println("El valor interpolado para " + coeficienteInterpolador + " es: " + sum + ", con un error de " + e + ".");

        // Imprimir el polinomio interpolador
        imprimirPolinomioCnk();

        /*//Cosas Parcial 1
        System.out.println("El valor 1.6 evaluado en el polinomio es de "+ evaluarPolinomio);
        System.out.println("El valor 1.6 evaluado en el polinomio es de "+ funEva);
        errorComunExacto(e, evaluarPolinomio);*/
    }

    /**
     * Método que separa los valores de x e y desde la matriz.
     * Los valores de x se almacenan en el vector x y los de y en el vector y.
     */
    private void separarXY() {
        // Inicializar los vectores de x e y
        x = new Double[filas];
        y = new Double[filas];

        // Llenar los vectores con los valores correspondientes de la matriz
        for (int i = 0; i < filas; i++) {
            x[i] = m[i][0];
            y[i] = m[i][1];
        }

        // Imprimir los valores de x e y
        for (int i = 0; i < filas; i++) {
            System.out.println(x[i] + ";" + y[i]);
        }
    }

    /**
     * Método que construye y muestra el polinomio interpolador de Lagrange.
     * El polinomio se genera en forma simbólica, utilizando los coeficientes de x y y.
     */
    private void imprimirPolinomioCnk() {
        StringBuilder polinomio = new StringBuilder();

        // Construir el polinomio de Lagrange
        for (int i = 0; i < filas; i++) {
            StringBuilder termino = new StringBuilder();
            termino.append("\n").append(y[i]);

            for (int j = 0; j < filas; j++) {
                if (j != i) {
                    termino.append(" * (x - ").append(x[j]).append(") / (").append(x[i]).append(" - ").append(x[j]).append(")");
                }
            }

            /*// Añadir el signo más entre términos (excepto antes del primer término)
            if (i > 0 && y[i] >= 0) {
                polinomio.append(" + ");
            } else if (y[i] < 0) {
                polinomio.append(" - ");
                termino.deleteCharAt(0);  // Eliminar el signo negativo de y[i] si es negativo
            }*/

            polinomio.append(termino);
        }

        // Mostrar el polinomio resultante
        System.out.println("El polinomio interpolador de Lagrange es: ");
        System.out.println(polinomio.toString());
    }
    private void errorComunExacto(Double c, Double raizReal) {
        DecimalFormat df = new DecimalFormat("0.00001");
        System.out.println("~~ Error común ~~");

        // Calcular el error absoluto exacto
        double errorAbsolutoExacto = Math.abs(c - raizReal);

        // Formatear el error absoluto exacto a un formato adecuado
        String formattedErrorAbsolutoExacto = df.format(errorAbsolutoExacto);

        // Imprimir el valor aproximado de la raíz junto con el error absoluto
        System.out.println("La raíz es aproximadamente: " + c + " ± " + formattedErrorAbsolutoExacto + "\n");
    }
}
