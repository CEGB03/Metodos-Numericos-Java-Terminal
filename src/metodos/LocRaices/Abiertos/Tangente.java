package metodos.LocRaices.Abiertos;

import java.util.Scanner;

import static java.lang.Math.abs;

public class Tangente {
    private Scanner sc = new Scanner(System.in);
    private Double xViejo = 0.0;
    private Double xActual = 0.0;
    private Double xNuevo = 0.0;
    private Double error = 0.0;
    private Double tolerancia = 0.0;
    private int contador = 0;

    double f(double x){
        return x*x -4;////
    }
    double fPrima(double x){
        return 2*x;////
    }
    public void tangente(){
        System.out.println("Ingrese la tolerancia\n");
        tolerancia = Double.parseDouble(sc.nextLine());
        System.out.println("Ingrese el punto anterior\n");
        xViejo = Double.parseDouble(sc.nextLine());
        System.out.println("Ingrese el punto inicial\n");
        xActual = Double.parseDouble(sc.nextLine());
        do{
            contador ++ ;

            if(Math.abs(fPrima(xViejo)) < 1e-5){
                System.out.println("\n********\nDERIVADA PEQUEÑA********\n");
                break;
            } else{
                double aprox = (f(xViejo) - f(xActual)) / (xViejo - xActual);
                xNuevo = xActual - (f(xActual) / aprox);
                error = abs(xNuevo - xActual); // Calcular el error
                xViejo = xActual; // Actualizar xActual con xViejo
                xActual = xNuevo; // Actualizar xViejo con xNuevo
                contador++; // Incrementar el contador de iteraciones
            }

        }while(error > tolerancia || contador < 10000 );

        System.out.println("\n\n\nLa raiz de f es: "+ xNuevo);
        System.out.println("\nEl valor del error en la raiz es de: "+ error);
        System.out.println("\nLa resolucion del proble tomó "+contador+" iteraciones");
    }
}
