package metodos.LocRaices.Abiertos;

import java.util.Scanner;

import static java.lang.Math.*;

public class PuntoFijo {
    private Scanner sc = new Scanner(System.in);
    private Double xViejo = 0.0; // es el punto de partida digamos
    private Double xNuevo = 0.0;
    private int contador = 0;
    private Double error = 0.0;
    private Double tolerancia = 0.0;

    double g(double x)
    { // funcion g
        return exp(-x);
    }

    public void puntoFijo()
    {
        // acordate que tenes que inicializarlo donde quieras, va a funcionar igual. Te puede llevar +- iteraciones pero es lo mismo

        System.out.println("Ingrese la tolerancia\n");
        tolerancia  = Double.parseDouble(sc.nextLine());
        System.out.println("Ingrese el punto inicial\n");
        xViejo = Double.parseDouble(sc.nextLine());
        do
        {

            contador++;

            if (abs((g(xViejo + 0.01) - g(xViejo)) / 0.01) >= 1) // Derivada >= 1
            {

                System.out.println("\n\nNo cumple con el criterio de convergencia. El metodo diverge (|g'(x)|>1)");
                break;
            }
            else
            {

                xNuevo = g(xViejo);
                error = abs(xNuevo - xViejo);
                xViejo = xNuevo;
            }

        } while (error > tolerancia);

        System.out.println("\n\n\nEl punto fijo de g(x), es decir la raiz de f es: "+ xNuevo);
        System.out.println("\nEl valor del error en la raiz es de: "+ error);
        System.out.println("\nLa resolucion del proble tomó "+contador+" iteraciones");
    }
}
