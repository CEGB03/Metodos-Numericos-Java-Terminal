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
        imprimirPolinomio();
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
    private void imprimirPolinomio(){
        //sustitucion regresiva
        Double suma= (double) 0;
        int fila = filas;
        Double[] a =new Double[fila]; //vector de soluciones

        //valor de la ultima variable
        a[fila-1] = b[fila-1] / A[fila-1][fila-1];
        System.out.println( "----- Soluciones -----");
        System.out.println("a["+ (fila-1) +"]= " + a[fila-1]);

        for (int i=fila-2; i>=0; i--)
        {
            suma = b[i];
            for(int j=i+1; j<=fila-1; j++)
            {
                suma-= A[i][j]*a[j];
            }
            a[i]=(suma)/A[i][i];
            System.out.println("a["+ i + "]= " + a[i]);
        }
        System.out.println("\n");

        //Imprimo el polinomio final:
        System.out.println("P = " );
        for(int i=0,j=0;i<filas;i++,j++)
        {
            if(j==0)
                System.out.print( a[i] +" + ");
            else
                System.out.println(a[i] + " " + "x^" + j + "+");

        }
    }
}
