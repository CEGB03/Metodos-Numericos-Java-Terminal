package metodos.Interpolacion;

import metodos.Pibot;

import java.util.Scanner;

public class Polinomial {
    private Double[][] m;
    private Double[] x;
    private Double[] y;
    private int filas;

    public Polinomial(Double[][] A, int filas) {
        this.filas = filas;
        this.m = A;
        //separarXY();
    }
    public void interpolar(Scanner sc) {
        System.out.println("\n\n***Ha seleccionado método de Polinomial***\n\n");
        Double[][] matrizA = new Double[filas][filas];
        Double[] matrizB = new Double[filas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < filas ; j++) {
                matrizA[i][j] = Math.pow(m[i][0], j);
            }
            matrizB[i] = m[i][1];
        }
        /*System.out.println("\nMatriz a\n");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < filas; j++) {
                System.out.print(matrizA[i][j]);
                System.out.print(" | ");
            }
            System.out.println();
        }
        System.out.println("Fin Mariz a");
        System.out.println("\nMatriz b\n");
        for (int i = 0; i < filas; i++) {
            System.out.print(matrizB[i]);
            System.out.print("\n");
        }
        System.out.println("Fin Mariz b");*/
        //imprimirTabla();
        Pibot pibot = new Pibot(matrizA, matrizB, filas);
        Pibot.imprimirMatriz(matrizA);
        Pibot.imprimirVector(matrizB);
        pibot.triangulacionConPivot();
        Pibot.imprimirMatriz(matrizA);
        Pibot.imprimirVector(matrizB);
        //imprimirTabla();
        //imprimirPolinomioCnk();
    }

    private void separarXY(){
        x = new Double[filas];
        y = new Double[filas];
        for (int i = 0; i < filas; i++) {
            x[i] = m[i][0];
            y[i] = m[i][1];
        }
        imprimirTabla();
    }
    private void imprimirTabla(){
        for (int i = 0; i < filas; i++) {
            System.out.println(x[i]+";"+y[i]);
        }
    }
    private void imprimirPolinomioCnk() {
        StringBuilder polinomio = new StringBuilder();

        for (int i = 0; i < filas; i++) {
            StringBuilder termino = new StringBuilder();
            termino.append("\n").append(y[i]);

            for (int j = 0; j < filas; j++) {
                if (j != i) {
                    termino.append(" * (x - ").append(x[j]).append(") / (").append(x[i]).append(" - ").append(x[j]).append(")");
                }
            }

            // Añadir el signo más entre términos (excepto antes del primer término)
            if (i > 0 && y[i] >= 0) {
                polinomio.append(" + ");
            } else if (y[i] < 0) {
                polinomio.append(" - ");
                termino.deleteCharAt(0);  // Eliminar el signo negativo de y[i] si es negativo
            }

            polinomio.append(termino);
        }

        System.out.println("El polinomio interpolador de Lagrange es: ");
        System.out.println(polinomio.toString());
    }
}
