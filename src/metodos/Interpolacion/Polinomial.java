package metodos.Interpolacion;

import metodos.Pibot;

import java.util.Scanner;

public class Polinomial {
    private Double[][] m;
    private int filas;

    public Polinomial(Double[][] A, int filas) {
        this.filas = filas;
        this.m = A;
    }
    public void interpolar(Scanner sc) {
        System.out.println("\n\n***Ha seleccionado método de Polinomial***\n\n");
        Double[][] matrizA = new Double[filas][filas];
        Double[] matrizB = new Double[filas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < filas +1 ; j++) {
                matrizA[i][j] = Math.pow(m[i][0], j);
            }
            matrizB[i] = m[i][1];
        }
        System.out.println("\nMatriz a\n");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < filas; j++) {
                System.out.print(matrizA[i][j]);
                System.out.println("\t");
            }
            System.out.println("\n");
        }
        System.out.println("\nMatriz b\n");
        for (int i = 0; i < filas; i++) {
            System.out.print(matrizB[i]);
            System.out.println("\t");
        }
        Pibot pibot = new Pibot(matrizA, matrizB, filas);
        pibot.triangulacionConPivot();
        Pibot.imprimirMatriz(matrizA);
        Pibot.imprimirVector(matrizB);
    }
}
