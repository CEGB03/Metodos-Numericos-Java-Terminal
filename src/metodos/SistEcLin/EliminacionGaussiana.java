package metodos.SistEcLin;

import java.io.FileNotFoundException;
import metodos.Pibot;

public class EliminacionGaussiana {
    Double[][] A;
    Double[] b;
    Double[] x;
    int filas = 0, columnas = 0;
    double factor = 0;
    int p = 0;
    Double swap = (double) 0;

    public EliminacionGaussiana(String s) throws FileNotFoundException {
        System.out.println("s = " + s);
        LecturaMatriz lecturaMatriz = new LecturaMatriz(s);
        filas = lecturaMatriz.getFilas();
        columnas = lecturaMatriz.getColumnas();
        A = new Double[filas][columnas];
        b = new Double[filas];
        x = new Double[filas];
        A = lecturaMatriz.getmFinal();
        b = lecturaMatriz.getB();
    }

    public void eliminar() {
        System.out.println("Vuelta al main.\n");

        if (filas != columnas) {
            System.out.println(filas + "x" + columnas + "\nLa matriz no es cuadrada, no se puede resolver usando eliminacion gaussiana.");
            System.exit(20);
        }

//      Descomentar para ver la matriz
        System.out.println("Imprimir antes de pibot");
        imprimir();
        //Piboteo inicio
        Pibot pibot = new Pibot(A, b, filas);
        //pibot.pivoteo_GPT();
        pibot.pibotear();
        A = pibot.getA();
        b = pibot.getB();
        factor = pibot.getFactor();
        swap = pibot.getSwap();
        p = pibot.getP();
        //Piboteo fin

//      Descomentar para ver la matriz
        System.out.println("Imprimir despues de pibot y triangulacion");
        pibot.triangulacionConPivot();
        imprimir();

        // Verificar si la matriz es singular
        int det = 1;
        for (int ii = 0; ii < filas; ii++)
            det *= A[ii][ii];
        if (det == 0) {
            System.err.println("Matriz singular, no se puede resolver.\n");
            System.exit(30);
        }
        System.out.println("det(A)= " + det);
        imprimirSolucion();
        // Retro-sustitución  EMA
        for (int i = filas - 1; i >= 0; i--) {
            double suma = b[i];
            imprimirSolucion();
            for (int j = i + 1; j < filas; j++) {
                suma -= A[i][j] * x[j];
            }
            //imprimirSolucion();
            x[i] = suma / A[i][i];
        }

        System.out.println("Matriz Solucion:");
        imprimirSolucion();
    }

    public void imprimir() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++)
                System.out.print(A[i][j] + " ");
            System.out.println("| " + b[i]);
        }
        System.out.println();
    }

    public void imprimirSolucion() {
        // Imprimir la solución x
        System.out.println("Solucion x:");
        for (int ii = 0; ii < filas; ii++) {
            System.out.println("x[" + (ii + 1) + "] = " + x[ii]);
        }
        System.out.println();
    }
}
