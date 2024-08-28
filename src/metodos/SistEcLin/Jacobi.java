package metodos.SistEcLin;

import java.util.Scanner;

class Jacobi {
    private Double[][] a;
    private final Double[] b;
    private Double xViejo[];
    private Double xNuevo[];
    int filas = 0;
    private Double tolerancia = 0.0;
    private Double error = 0.0;
    //private Double xNuevo = 0.0;

    public Jacobi(Double[][] A, Double[] b, int filas) {
        this.a = A;
        this.b = b;
        this.filas = filas;
    }
    public void eliminar(Scanner sc){

        System.out.println("\n\n***Ha seleccionado método de jacobi***\n\n");

        boolean check = diagonalmenteDominante(a, filas);
        if (!check)
            System.exit(0);

        Double suma = 0.0;
        xNuevo = new Double[filas];
        xViejo = new Double[filas];

        for (int i = 0; i < filas; i++) {
            xViejo[i] = 0.0;
            xNuevo[i] = xViejo[i];
        }

        int iteraciones = 0;
        System.out.println("\nIngrese la tolerancia\n");
        tolerancia = Double.valueOf(sc.nextLine());

        do {
            iteraciones++;
            for (int i = 0; i < filas; i++) {
                suma = 0.0;
                for (int j = 0; j < filas; j++) {
                    if (j != i)
                        suma += a[i][j] * xViejo[j];
                }
                xNuevo[i] = (b[i] - suma) / a[i][i];
            }

            // Manejo del error
            suma = 0.0;
            for (int i = 0; i < filas; i++) {
                suma += (xNuevo[i] - xViejo[i]) * (xNuevo[i] - xViejo[i]);
            }
            error = Math.sqrt(suma);

            // Reasignación del vector viejo para la próxima pasada.
            if (filas >= 0) System.arraycopy(xNuevo, 0, xViejo, 0, filas);
        } while (error > tolerancia && iteraciones < 10000);

        System.out.println("\n El resultado es: \nxnuevo = [\t");
        for (int i = 0; i < filas; i++) {
            System.out.println(xNuevo[i]+"\t");
        }

        System.out.println("]\n La cantidad de iteraciones fueron:"+iteraciones+"\n El error es de "+error+".");
    }
    boolean diagonalmenteDominante(Double[][] a, int filas){
        double suma = 0;
        int counter = 0;
        for(int i = 0; i < filas ; i++){
            suma = 0;
            counter++;
            for(int j = 0 ; j < filas ; j++){
                if(j!=i){
                    suma+= Math.abs(a[i][j]);
                }
            }

            if(Math.abs(a[i][i]) < suma)
                System.out.println("\nLa matriz no es diagonalmente dominante. Fila: "+counter+".");

            if(a[i][i] == 0){
                System.out.println("\nCeros en la diagonal");
                return false;
            }
        }
        return true;
    }
}