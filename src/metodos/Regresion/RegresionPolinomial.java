package metodos.Regresion;

import metodos.SistEcLin.EliminacionGaussiana;

import java.util.Scanner;

/**
 * Clase para realizar la regresión polinómica utilizando una matriz de datos.
 * La clase permite ajustar un polinomio a los datos proporcionados y calcular detalles estadísticos sobre el ajuste.
 */
public class RegresionPolinomial {
    static Scanner sc = new Scanner(System.in); // Escáner para entrada de datos
    Double[][] matrizA; // Matriz de coeficientes para el sistema lineal
    Double[] matrizB;  // Vector de términos independientes
    Double[] x;        // Vector de coeficientes del polinomio
    Double[][] tabla;  // Tabla de datos (x, y)
    int filas = 0, columnas = 0, grado; // Número de filas, columnas y grado del polinomio
    boolean flag;     // Bandera para controlar la validez del grado del polinomio

    /**
     * Constructor que inicializa la clase con la tabla de datos.
     *
     * @param A Matriz de datos.
     * @param filas Número de filas en la matriz.
     * @param columnas Número de columnas en la matriz.
     */
    public RegresionPolinomial(Double[][] A, int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas + 1; // Aumentar una columna para el vector de términos independientes
        recortarTabla(A); // Ajustar la tabla de datos
        imprimirTabla(); // Imprimir la tabla de datos
    }

    /**
     * Recorta la matriz A para ajustarse a las dimensiones de la tabla de datos.
     *
     * @param A Matriz de datos.
     */
    private void recortarTabla(Double[][] A){
        tabla = new Double[filas][columnas];
        for (int i = 0; i < filas; i++) {
            System.arraycopy(A[i], 0, tabla[i], 0, columnas);
        }
    }

    /**
     * Imprime una matriz en la consola.
     *
     * @param matrizImprimir Matriz a imprimir.
     */
    private void imprimirMatriz(Double[][] matrizImprimir) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j <= columnas; j++)
                System.out.print(matrizImprimir[i][j] + " | ");
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Imprime la tabla de datos en la consola.
     */
    private void imprimirTabla() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++)
                System.out.print(tabla[i][j] + " | ");
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Realiza la regresión polinómica.
     * Solicita al usuario el grado del polinomio y calcula el ajuste.
     */
    public void regresion() {
        do {
            System.out.print("Ingrese el grado del polinomio: ");
            grado = Integer.parseInt(sc.nextLine());

            // Verificar si el número de datos es suficiente para el grado del polinomio
            if (filas < grado + 1) {
                System.out.print("La regresión no es posible para el polinomio deseado");
                flag = true;
            } else {
                flag = false;
            }
        } while (flag);

        // Inicializar matrices y vectores para la regresión
        matrizA = new Double[grado + 1][grado + 1]; // Matriz de coeficientes
        matrizB = new Double[grado + 1]; // Vector de términos independientes
        x = new Double[grado + 1]; // Vector de coeficientes del polinomio

        ensamblarMatriz(); // Crear la matriz de Vandermonde y el vector B

        // Verificación para evitar valores null
        for (int ii = 0; ii < grado; ii++)
            if (matrizB[ii] == null)
                matrizB[ii] = 0.0;
        for (int ii = 0; ii < grado; ii++)
            for (int jj = 0; jj < grado; jj++)
                if (matrizA[ii][jj] == null)
                    matrizA[ii][jj] = 0.0;

        eliminacionGaussiana(); // Resolver el sistema lineal
        imprimirPolinomio(x, grado); // Imprimir el polinomio resultante
        calcularDetalles(); // Calcular y mostrar detalles estadísticos
    }

    /**
     * Ensambla la matriz de Vandermonde y el vector B para la regresión polinómica.
     */
    private void ensamblarMatriz() {
        double suma;
        for (int indexA = 0; indexA <= grado; indexA++) {
            for (int indexB = 0; indexB <= grado; indexB++) {
                suma = 0;
                for (int indexC = 0; indexC < filas; indexC++) {
                    suma += Math.pow(tabla[indexC][0], indexB + indexA);
                }
                matrizA[indexA][indexB] = suma;
            }

            suma = 0;
            for (int indexB = 0; indexB < filas; indexB++) {
                suma += (Math.pow(tabla[indexB][0], indexA) * tabla[indexB][1]);
            }
            matrizB[indexA] = suma;
        }

        // Imprimir la matriz de Vandermonde y el vector B
        System.out.println("\nMatriz de Vandermonde:");
        for (int indexA = 0; indexA <= grado; indexA++) {
            for (int indexB = 0; indexB <= grado; indexB++) {
                System.out.print(matrizA[indexA][indexB] + " ");
            }
            System.out.println(" ---> " + matrizB[indexA]);
        }
    }

    /**
     * Imprime el polinomio resultante en la consola.
     *
     * @param soluciones Vector que contiene los coeficientes del polinomio.
     * @param grado Grado del polinomio.
     */
    private void imprimirPolinomio(Double[] soluciones, int grado) {
        System.out.println("grado = " + grado);
        System.out.println("Polinomio:");
        for (int exponente = 0; exponente <= grado; exponente++) {
            if (soluciones[exponente] != null) {
                if (exponente == 0) {
                    System.out.print(soluciones[exponente]);
                } else {
                    if (soluciones[exponente] >= 0) {
                        System.out.print(" + " + soluciones[exponente] + "x^" + exponente);
                    } else {
                        System.out.print(" " + soluciones[exponente] + "x^" + exponente);
                    }
                }
            } else {
                System.out.print(" (coeficiente no definido para x^" + exponente + ")");
            }
        }
        System.out.println();
    }

    /**
     * Realiza la eliminación gaussiana con pivoteo parcial.
     */
    private void eliminacionGaussiana() {
        EliminacionGaussiana eliminacionGaussiana = new EliminacionGaussiana(matrizA, matrizB, x, grado + 1);
        eliminacionGaussiana.eliminar();
        matrizA = eliminacionGaussiana.getA();
        matrizB = eliminacionGaussiana.getB();
        x = eliminacionGaussiana.getX();
    }

    /**
     * Calcula y muestra detalles estadísticos sobre el ajuste polinómico.
     */
    private void calcularDetalles() {
        System.out.println("\nDetalles:");

        // Inicialización de variables
        double syx;    // Desviación estándar (error estándar estimado)
        double sr;     // Suma de los residuos (Suma de los errores al cuadrado)
        double st;     // Suma total de cuadrados
        double r2;     // Coeficiente de determinación (r^2)
        double r;      // Coeficiente de correlación (r)
        double yMedia; // Media de los valores de y
        double suma;   // Para sumar los valores estimados
        double ecm;    // Error cuadrático medio

        yMedia = 0;
        for (int index = 0; index < filas; index++){
            yMedia += tabla[index][1];
        }
        yMedia /= filas;

        // Calcular suma total de los cuadrados
        st = 0;
        for (int index = 0; index < filas; index++)
            st += Math.pow(tabla[index][1] - yMedia, 2);

        // Cálculo del error-residuo
        sr = 0;
        for (int indexA = 0; indexA < filas; indexA++) {
            suma = 0;
            for (int indexB = 0; indexB <= grado; indexB++) {
                suma += (x[indexB] * Math.pow(tabla[indexA][0], indexB));
            }
            sr += Math.pow((tabla[indexA][1] - suma), 2);
        }

        ecm = Math.sqrt(sr / filas); // Error cuadrático medio

        // Cálculo del error estándar estimado (desviación estándar)
        syx = Math.sqrt(sr / ((double) filas - (grado + 1)));

        // Cálculo del coeficiente de determinación
        r2 = Math.abs(st - sr) / st;

        // Cálculo del coeficiente de correlación
        r = Math.sqrt(r2);

        // Impresión de los resultados
        System.out.println("Error (suma de cuadrados de los residuos): " + sr);
        System.out.println("Error cuadrático medio (ECM): " + ecm);
        System.out.println("Desviación estándar (syx): " + syx);
        System.out.println("Error total (suma de cuadrados, st): " + st);
        System.out.println("Coeficiente de determinación (r^2): " + r2);
        System.out.println("Coeficiente de correlación (r): " + r);
    }
}
