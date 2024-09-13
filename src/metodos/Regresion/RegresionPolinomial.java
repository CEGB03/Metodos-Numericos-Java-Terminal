package metodos.Regresion;

import metodos.SistEcLin.EliminacionGaussiana;

import java.util.Scanner;

public class RegresionPolinomial {
    static Scanner sc = new Scanner(System.in);
    Double[][] matrizA;
    Double[] matrizB;
    Double[] x;
    Double[][] tabla;
    int filas = 0, columnas = 0, grado;
    boolean flag;

    public RegresionPolinomial(Double[][] A, int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas + 1;
        recortarTabla(A);
        imprimirTabla();
    }

    private void recortarTabla(Double[][] A){
        tabla = new Double[filas][columnas];
        for (int i = 0; i < filas; i++) {
            System.arraycopy(A[i], 0, tabla[i], 0, columnas);
        }
    }

    private void imprimirMatriz(Double[][] matrizImprimir) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j <= columnas; j++)
                System.out.print(matrizImprimir[i][j] + " | ");
            System.out.println();
        }
        System.out.println();

    }

    private void imprimirTabla() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++)
                System.out.print(tabla[i][j] + " | ");
            System.out.println();
        }
        System.out.println();

    }

    public void regresion() {
        do {
            System.out.print("Ingrese el grado del polinomio: ");
            grado = Integer.parseInt(sc.nextLine());

            if (filas < grado + 1) {
                System.out.print("La regresión no es posible para el polinomio deseado");
                flag = true;
            } else {
                flag = false;
            }
        } while (flag);

        // Cambia el tamaño de la matrizA y matrizB
        matrizA = new Double[grado + 1][grado + 1]; // +1 para acomodar todos los términos
        matrizB = new Double[grado + 1]; // También debe tener grado + 1
        x=new Double[grado + 1];

        ensamblarMatriz();

        // Verificación para evitar nulls
        for (int ii = 0 ; ii < grado ; ii++)
            if (matrizB[ii] == null)
                matrizB[ii] = 0.0;
        for (int ii = 0 ; ii < grado ; ii++)
            for (int jj = 0 ; jj < grado ; jj++)
                if (matrizA[ii][jj] == null)
                    matrizA[ii][jj] = 0.0;

        eliminacionGaussiana();
        imprimirPolinomio(x, grado);
        calcularDetalles(/*tabla, x, filas, grado*/);
    }

    /**
     * Ensambla la matriz de Vandermonde para realizar la interpolación polinómica.
     *
     *
     */
    private void ensamblarMatriz() {
        double suma;
        // Itera sobre los índices de la matriz para calcular los valores de la matriz de Vandermonde
        for (int indexA = 0; indexA <= grado; indexA++) {
            for (int indexB = 0; indexB <= grado; indexB++) {
                suma = 0;
                // Suma los valores de los tabla elevados a las potencias correspondientes
                for (int indexC = 0; indexC < filas; indexC++) {
                    suma += Math.pow(tabla[indexC][0], indexB + indexA);
                }
                matrizA[indexA][indexB] = suma;
            }

            suma = 0;
            // Calcula el valor del vector B
            for (int indexB = 0; indexB < filas; indexB++) {
                suma += (Math.pow(tabla[indexB][0], indexA) * tabla[indexB][1]);
            }
            matrizB[indexA] = suma;
        }

        // Imprime la matriz de Vandermonde
        System.out.println("\nMatriz de Vandermonde:");
        for (int indexA = 0; indexA <= grado; indexA++) {
            for (int indexB = 0; indexB <= grado; indexB++) {
                System.out.print(matrizA[indexA][indexB] + " ");
            }
            System.out.println(" ---> " + matrizB[indexA]);
        }
    }

    /**
     * Imprime el polinomio resultante.
     *
     * @param soluciones Vector que contiene los coeficientes del polinomio.
     * @param grado Grado del polinomio.
     */
    private void imprimirPolinomio(Double[] soluciones, int grado) {
        System.out.println("Polinomio:");
        for (int exponente = 0; exponente <= grado; exponente++) {
            // Verificar si el valor no es null
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
     *
     *
     */
    private void eliminacionGaussiana() {
        EliminacionGaussiana eliminacionGaussiana = new EliminacionGaussiana(matrizA, matrizB, x, grado+1);
        matrizA = eliminacionGaussiana.getA();
        matrizB = eliminacionGaussiana.getB();
        x = eliminacionGaussiana.getX();
        /*System.out.println("filas = " + filas);
        filas = eliminacionGaussiana.getFilas();
        System.out.println("filas = " + filas);*/
    }
    /**
     * Calcula y muestra detalles estadísticos como el error y los coeficientes de correlación.
     *
     *
     */

/*
    private void calcularDetalles() {
        System.out.println("\nDetalles:");
        double syx;    // Desviación estándar (error estándar estimado)
        double sr;     // Suma de los residuos
        double st;     // Suma total de cuadrados
        double r2;     // Coeficiente de determinación
        double r;      // Coeficiente de correlación
        double yMedia; // Media de los valores de y
        double suma;
        double ecm;    // Error cuadrático medio


        // Cálculo de la media de y
        yMedia = 0;
        for (int i = 0; i < filas; i++) {
            yMedia += tabla[i][1];
        }
        yMedia /= filas;

        // Cálculo del error total (suma de cuadrados)
        st = 0;
        for (int i = 0; i < filas; i++) {
            st += Math.pow(tabla[i][1] - yMedia, 2);
        }

        // Cálculo de los residuos
        sr = 0;
        for (int i = 0; i < filas; i++) {
            suma = 0;
            for (int j = 0; j <= grado; j++) {
                suma += x[j] * Math.pow(tabla[i][0], j);
            }
            sr += Math.pow(tabla[i][1] - suma, 2);
        }

        ecm = Math.pow(sr / filas, (double) 1 /2); // Divide por el total de filas (número de datos)
        syx = Math.sqrt(sr / (filas - (grado + 1)));

        // Cálculo de r^2 y r
        r2 = Math.abs(st - sr) / st;
        r = Math.sqrt(r2);
        // Impresión de los resultados
        System.out.println("Error (suma de cuadrados de los residuos): " + sr);
        System.out.println("Error cuadrático medio: " + ecm);
        System.out.println("Desviación estándar (error estándar estimado): " + syx);
        System.out.println("Error total (suma de cuadrados): " + st);
        System.out.println("Coeficiente de determinación (r^2): " + r2);
        System.out.println("Coeficiente de correlación (r): " + r);
    }
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
            yMedia = yMedia + tabla[index][1];
        }
        yMedia = yMedia / filas;

        // calculo suma total de los cuadrados
        st = 0;
        for (int index = 0; index < filas; index++)
            st = st + Math.pow(tabla[index][1] - yMedia, 2);

        // Cálculo del error-residuo
        sr = 0;
        for (int indexA = 0; indexA < filas; indexA++) {
            suma = 0;
            for (int indexB = 0; indexB <= grado; indexB++) {
                suma = suma + (x[indexB] * Math.pow(tabla[indexA][0], indexB));
            }
            sr = sr + Math.pow((tabla[indexA][1] - suma), 2);
        }

        ecm = Math.sqrt(sr/filas);

        // Cálculo del error estandar estimado (desviación estándar)
        syx = Math.sqrt(sr / ((double) filas - (grado + 1)));

        // Cálculo del coeficiente de determinación
        r2 = Math.abs(st - sr) / st;

        // Cálculo del coeficiente de correlación
        r = Math.sqrt(r2);

  /*
    private void calcularDetalles(Double[][] tabla, Double[] soluciones, int filas, int grado) {
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

        // Cálculo de la media de los valores de y (yMedia)
        yMedia = 0;
        for (int i = 0; i < filas; i++) {
            yMedia += tabla[i][1];
        }
        yMedia /= filas;  // Promedio de los valores de y

        // Cálculo de la suma total de cuadrados (st)
        st = 0;
        for (int i = 0; i < filas; i++) {
            st += Math.pow(tabla[i][1] - yMedia, 2);  // (yi - yMedia)^2
        }

        // Cálculo de los residuos (sr) y suma para el ECM
        sr = 0;
        for (int i = 0; i < filas; i++) {
            // f(x) = B + A * x = soluciones[0] + soluciones[1] * x
            suma = soluciones[0] + soluciones[1] * tabla[i][0];

            // Imprimir el valor estimado f(x) y el valor real y
            System.out.println("Valor estimado f(x) = " + suma + ", Valor real y = " + tabla[i][1]);

            // Suma de los errores al cuadrado
            sr += Math.pow(tabla[i][1] - suma, 2);  // (yi - f(xi))^2

            // Imprimir el valor intermedio de sr
            System.out.println("Residuos acumulados (sr): " + sr);
        }

        // Cálculo del Error Cuadrático Medio (ECM)
        ecm = Math.sqrt(sr / filas);  // ECM = sqrt(1/n * sum((yi - f(xi))^2))

        // Cálculo de la desviación estándar (syx)
        syx = Math.sqrt(sr / (filas - (grado + 1)));  // syx = sqrt(sr / (n - (grado + 1)))

        // Cálculo del coeficiente de determinación (r^2)
        r2 = Math.abs(st - sr) / st;  // r^2 = (st - sr) / st

        // Cálculo del coeficiente de correlación (r)
        r = Math.sqrt(r2);  // r = sqrt(r^2)
*/
        // Impresión de los resultados
        System.out.println("Error (suma de cuadrados de los residuos): " + sr);
        System.out.println("Error cuadrático medio (ECM): " + ecm);
        System.out.println("Desviación estándar (syx): " + syx);
        System.out.println("Error total (suma de cuadrados, st): " + st);
        System.out.println("Coeficiente de determinación (r^2): " + r2);
        System.out.println("Coeficiente de correlación (r): " + r);
    }
}
