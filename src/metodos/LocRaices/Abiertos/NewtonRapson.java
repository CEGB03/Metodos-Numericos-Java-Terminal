package metodos.LocRaices.Abiertos;

import java.util.Scanner;

import static java.lang.Math.*;

public class NewtonRapson {
    private Scanner sc = new Scanner(System.in);
    private Double xViejo = 0.0;
    private Double error = 0.0;
    private Double tolerancia = 0.0;
    private Double xNuevo = 0.0;
    private int contador = 0;

    double f(double x){
        return x*x -4;////
    }
    double fPrima(double x){
        return 2*x;////
    }
    public void newtonRaphson(){
        System.out.println("Ingrese la tolerancia\n");
        tolerancia = Double.parseDouble(sc.nextLine());
        System.out.println("Ingrese el punto inicial\n");
        xViejo = Double.parseDouble(sc.nextLine());
        do{
            contador ++ ;

            if(Math.abs(fPrima(xViejo)) < 1e-5){
                System.out.println("\n********\nDERIVADA PEQUEÑA********\n");
                break;
            } else{
                xNuevo = xViejo - (f(xViejo)/fPrima(xViejo));
                error = abs(xNuevo - xViejo);
                xViejo = xNuevo;
            }

        }while(error > tolerancia || contador < 10000 );

        System.out.println("\n\n\nLa raiz de f es: "+ xNuevo);
        System.out.println("\nEl valor del error en la raiz es de: "+ error);
        System.out.println("\nLa resolucion del proble tomó "+contador+" iteraciones");
    }
}
