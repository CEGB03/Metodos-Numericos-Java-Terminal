package metodos.Regresion;

import metodos.Pibot;
import metodos.SistEcLin.EliminacionGaussiana;

import java.util.Scanner;


/**
 * r > 0.8 sino malo
 */
public class RegresionLineal {
    Double[][] matrizA;
    Double[] x;
    int filas = 0, columnas = 0;

    public RegresionLineal(Double[][] A, int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas + 1;
        recortarMatriz(A);
        imprimir();
    }

    private void recortarMatriz(Double[][] A){
        matrizA = new Double[filas][columnas];
        for (int i = 0; i < filas; i++) {
            System.arraycopy(A[i], 0, matrizA[i], 0, columnas);
        }
    }

    private void imprimir() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++)
                System.out.print(matrizA[i][j] + " | ");
            System.out.println();
        }
        System.out.println();

    }

    public void regresion(){
        Double[][] a = new Double[columnas][columnas];
        Double[] b = new Double[columnas];
        for (int i = 0; i < columnas; i++) {
            for (int j = 0; j < columnas; j++) {
                a[i][j] = 0.0;
            }
            b[i] = 0.0;
        }
        double yProm = 0, r = 0, Sr = 0, St = 0, sum = 0;
        a[1][1] = (double) filas;
        for(int i = 0 ; i < columnas ; i++){

            a[0][0] += Math.pow(matrizA[i][0], 2); // Suma de los cuadrados de las x.
            a[0][1] += matrizA[i][0];              // Suma de las x.
            a[1][0] += matrizA[i][0];              // Suma de las x.
            b[0] += matrizA[i][0] * matrizA[i][1]; // Suma de x[i] * y[i].
            b[1] += matrizA[i][1];                 // Suma de las y.

        }

        System.out.println("Matriz:");
        for(int i = 0 ; i < 2 ; i++){
            for(int j = 0 ; j < 2 ; j++){
                System.out.print("a["+i+"]["+j+"] = " + a[i][j] + " ");
            }
            System.out.print("| b["+i+"] = " + "| " + b[i] + "\n");
        }

        //System.out.println();
        x = new Double[2];
        EliminacionGaussiana gaussiana = new EliminacionGaussiana(a, b, x, a.length);
        gaussiana.eliminar();


        System.out.println("Conjunto solución:");
        for (int i = 0; i < 2; ++i) {
            System.out.print("X" + i + " = "+x[i]+"\n");
        }

        for(int i = 0; i < filas ; i++){
            yProm+= matrizA[i][1];

        }
        yProm = yProm/filas; //check
        for(int i = 0; i < filas ; i++){
            St+= Math.pow((matrizA[i][1] - yProm) , 2);

        }

        for(int i = 0; i<filas ; i++){
            sum+= Math.pow((matrizA[i][1] - (x[0]*matrizA[i][0]+x[1])) , 2);
        }

        Sr = Math.sqrt(sum/filas);//check
        System.out.println("\nSr = " + Sr);

        r = (St - sum)/St;//check

        System.out.println("\nr = " + r);


        error(x, a, 1, a.length);

    }
    private void error (Double[] x, Double[][] m, int gradoP, int filas){
        double yb = 0;
        double e = 0;
        double ecm;
        double st = 0;
        double r;
        for (int i = 0; i < filas; i++) {
            double sum = 0;
            for (int j = 0; j <= gradoP; j++) {
                double aux2 = Math.pow(m[i][0], j);
                double aux = x[j]* aux2;
                sum = sum + aux;
            }
            e = e + Math.pow(m[i][1]-sum, 2);
            yb = yb + m[i][1];
        }
        yb = yb/(filas+1);
        for (int i = 0; i < filas; i++) {
            st = st + Math.pow(m[i][1]-yb, 2);
        }
        r = Math.sqrt(Math.abs(e-st)/st);
        ecm = Math.sqrt(e/filas);
        System.out.println("\nEl error es de(e): "+e);
        System.out.println("El error cuadratico medio es de(ecm): "+ecm);
        System.out.println("st: %"+ st);
        System.out.println("El coeficiente de correlacion es(r): "+r);
    }
}
