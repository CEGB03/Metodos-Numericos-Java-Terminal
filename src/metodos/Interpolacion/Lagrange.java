package metodos.Interpolacion;

import java.util.Scanner;

public class Lagrange {
    private final Double[][] m;
    private Double[] x;
    private Double[] y;
    private final int filas;
    private final int columnas;
    private double func(double x) {
        return x + 2/x;
    }

    public Lagrange(Double[][] A, int filas, int columnas) {
        this.m = A;
        this.filas = filas;
        this.columnas = columnas + 1;
        separarXY();
    }
    public void interpolar(Scanner sc) {
        System.out.println("\n***Ha seleccionado método de Lagrange***\n");
        double coeficienteInterpolador;//Coeficiente a interpolar
        double e;
        System.out.println("Recuerde haber definido la funcion previamente");
        System.out.println("Ingrese el valor a interpolar");
        coeficienteInterpolador= Double.parseDouble(sc.nextLine());

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
        e = Math.abs(func(coeficienteInterpolador) - sum);

        System.out.println("El valor interpolado para "+coeficienteInterpolador+" es: "+sum+", con un error de "+e+".");
        imprimirPolinomioCnk();
    }
        private void separarXY(){
            x = new Double[filas];
            y = new Double[filas];
            for (int i = 0; i < filas; i++) {
                x[i] = m[i][0];
                y[i] = m[i][1];
            }
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
