package metodos.LocRaices.Cerrados;

import java.util.Scanner;

public class RegulaFalsi {
    private Scanner sc = new Scanner(System.in);
    private Double a = 0.0;
    private Double b = 0.0;
    private Double tolerancia = 0.0;
    private Double c = 0.0;
    private Double error = 0.0;
    private int contador = 0;

    public void regulaFalsi() {

        System.out.println("Ingrese el límite inferior del intervalo: ");
        a = Double.parseDouble(sc.nextLine());
        System.out.println("Ingrese el límite superior del intervalo: ");
        b = Double.parseDouble(sc.nextLine());
        System.out.println("Ingrese la tolerancia: ");
        tolerancia = Double.parseDouble(sc.nextLine());

        if (funcion(a) * funcion(b) < 0.0) {
            do {
                contador++;

                c = (a*funcion(b) - b*funcion(a)) / (funcion(b) - funcion(a));


                if (funcion(a) * funcion(c) > 0) {
                    a = c;
                } else if (funcion(a) * funcion(c) < 0) {
                    b = c;
                } else {
                    break;
                }

                error = (b - a) / 2;

            } while (error > tolerancia && contador < 150000);

            System.out.println("\nResultado de la raíz: "+ c);
            System.out.println("\nError estimado: "+ error);
            System.out.println("\nCantidad de iteraciones: " + contador + "\n");

        } else {
            System.out.println("\nNo hay raíz o hay un número par de ellas\n");
        }
    }

    double funcion(double x) {
        //return log(x) + exp(sin(x)) - x;
        return -2+(7*x)-(5*x*x)+(6*x*x*x);
    }
}
