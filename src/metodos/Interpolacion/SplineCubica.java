package metodos.Interpolacion;

import java.lang.Math;
import java.util.Scanner;

public class SplineCubica {
    Double[][] matrizM;
    int filas;
    double[][] matrizA;
    double[] matrizB;
    double[] matrizX;

    public SplineCubica(Double[][] m, int filas) {
        this.matrizM = m;
        this.filas = filas;
        int n = 4 * (filas - 1);  // Número de ecuaciones para el sistema de spline cúbica
        matrizA = new double[n][n];
        matrizB = new double[n];
        matrizX = new double[n];
        imprimirTabla();
    }
/*
    public static void main(String[] args) {
        int fils = 4;
        double[][] matrizzz = new double[fils][2];
        matrizzz[0][0] = 3;matrizzz[0][1] = 2.5;
        matrizzz[1][0] = 4.5;matrizzz[1][1] = 1;
        matrizzz[2][0] = 7;matrizzz[2][1] = 2.5;
        matrizzz[3][0] = 9;matrizzz[3][1] = 0.5;
        SplineCubica splineCubica = new SplineCubica(matrizzz, matrizzz.length);
        splineCubica.interpolar();
    }*/
    public void interpolar() {
        buildMatrix(matrizM,matrizA,matrizB,filas);
        triangulacion(matrizA, matrizB, matrizX, 4*(filas-1));
        interpolacion(matrizM, matrizX, filas);
    }

    private void imprimirTabla() {
        System.out.println("(X\t\t;\tY)");
        for (int i = 0; i < filas; i++) //FILAS aca ponelos a mano, segun la cantidad de puntos
            System.out.println("(" + matrizM[i][0] + "\t;\t" + matrizM[i][1] + ")");
    }

    private void buildMatrix(Double[][] m, double[][] a, double[] b, int filas) {
        //construccion de las primeras 2n filas.
        int n = filas - 1; //esto es intervalos

        //construccion de las primeras 2n filas
        for (int k = 0; k < n; k++) {

            for (int j = 0; j < 4; j++) {
                a[2 * k][4 * k + j] = Math.pow(m[k][0], 3 - j);
                a[2 * k + 1][4 * k + j] = Math.pow(m[k + 1][0], 3 - j);
            }

            b[2 * k] = m[k][1];
            b[2 * k + 1] = m[k + 1][1];
        }

        //construccion de las derivadas primeras
        int k = 0; //hasta n-2
        for (int i = (2 * n); i <= ((3 * n) - 2); i++) { //filas

            for (int j = 0; j <= 2; ++j) {
                a[i][4 * k + j] = (3 - j) * Math.pow(m[k + 1][0], 2 - j);
                a[i][4 * (k + 1) + j] = -(3 - j) * Math.pow(m[k + 1][0], (2 - j));

            }
            k++;
            if (k > (n - 2)) {
                break;
            }
            b[i] = 0;
        }

        //construccion de las derivadas segundas
        k = 0; //hasta n-2
        for (int i = 3 * n - 1; i <= 4 * n - 3; i++) {

            a[i][4 * k] = 6 * m[k + 1][0];
            a[i][4 * k + 1] = 2;
            a[i][4 * (k + 1)] = -6 * m[k + 1][0];
            a[i][4 * (k + 1) + 1] = -2;

            k++;
            if (k > (n - 2)) {
                break;
            }
            b[i] = 0;
        }

        a[4 * n - 2][0] = 3 * m[0][0];
        a[4 * n - 2][1] = 1;
        a[4 * n - 1][4 * n - 4] = 3 * m[n][0];
        a[4 * n - 1][4 * n - 3] = 1;
        b[4 * n - 2] = 0;
        b[4 * n - 1] = 0;

        System.out.println();
        for (int i = 0; i < 4 * n; i++) {
            for (int j = 0; j < 4 * n; j++) {
                System.out.print(a[i][j]+" | ");
            }
            System.out.println(" || "+b[i]);
        }

    }

    private void triangulacion(double[][] a, double[] b, double[] x, int filas) {
        for (int i = 0; i < (filas - 1); i++) {
            pivot(a, b, filas, i);
            for (int j = i + 1; j < filas; j++) {
                double factor = -a[j][i] / a[i][i];
                for (int k = 0; k < filas; ++k) {
                    a[j][k] = a[i][k] * factor + a[j][k];
                }
                b[j] = b[i] * factor + b[j];
            }
        }

        double norma = determinante(a, b, x, filas);
        if (norma == 0.0) {
            System.out.println("matriz singular");
        } else {
            retrostutitucion(a, b, x, filas);
        }
    }

    private void retrostutitucion(double[][] a, double[] b, double[] x, int filas) {
        double value = 0;
        value = b[filas - 1] / a[filas - 1][filas - 1];
        x[filas - 1] = value;
        for (int i = filas - 2; i >= 0; --i) {
            double sum = 0;
            for (int j = i + 1; j < filas; ++j) {
                sum = sum + a[i][j] * x[j];
            }
            value = (b[i] - sum) / a[i][i];
            x[i] = value;
        }
        System.out.println("Conjunto solucion:");
        for (int i = 0; i < (filas / 4); ++i) {
            System.out.println("a"+i+" = +"+x[4 * i]);
            System.out.println("b"+i+" = "+x[4 * i + 1]);
            System.out.println("c"+i+" = "+x[4 * i + 2]);
            System.out.println("d"+i+" = "+x[4 * i + 3]);
        }
    }

    private void pivot(double[][] a, double[] b, int filas, int i) {
        if (Math.abs(a[i][i]) < 0.0001) {
            for (int j = i + 1; j < filas; j++) {
                if (Math.abs(a[j][i]) > Math.abs(a[i][i])) {
                    for (int k = i; k < filas; ++k) {
                        double swap = a[i][k];
                        a[i][k] = a[j][k];
                        a[j][k] = swap;
                    }
                    double swap = b[i];
                    b[i] = b[j];
                    b[j] = swap;
                }
            }
        }
    }

    private double determinante(double[][] a, double[] b, double[] x, int filas) {
        double norma = 1;
        for (int i = 0; i < filas; i++) {
            norma = norma * a[i][i];
        }
        return norma;
    }

    private void interpolacion(Double[][] m, double[] z, int fila) {
        Scanner sc = new Scanner(System.in);
        double x;//Coeficiente a interpolar
        System.out.print("Ingrese el valor a interpolar: ");
        x=sc.nextDouble();
        int intervalo=0;
        double resultado = 0;
        if (x >= m[0][0] && x <= m[fila - 1][0]) {

            for (int i = 0; i < fila; i++) {

                if (x >= m[i][0] && x < m[i + 1][0]) {
                    resultado = z[4 * i] * Math.pow(x, 3) + z[4 * i + 1] * Math.pow(x, 2) + z[4 * i + 2] * x + z[4 * i + 3];
                    intervalo = i;
                    break;
                }
            }
            System.out.println("\nEl valor a interpolar se encuentra en el intervalo "+ intervalo);
            System.out.println("El valor interpolado para "+x+" es: "+resultado+".");

        } else {
            System.out.println("\nEl valor a interpolar no se encuentra en el rango de datos\n");
        }
    }
}