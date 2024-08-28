package metodos.SistEcLin;

import java.util.Scanner;

class GaussSeidelRelajacion {
    private final Double[][] a;
    private final Double[] b;
    int filas = 0;
    private Double[] xViejo;
    private Double[] xNuevo;
    private Double tolerancia = 0.0;
    private Double error = 0.0;
    private Double factor_relajacion = 0.0;

    public GaussSeidelRelajacion(Double[][] A, Double[] b, int filas) {
        this.a = A;
        this.b = b;
        this.filas = filas;
    }
    public void eliminar(Scanner sc){
        System.out.println("\n\n***Ha seleccionado método de Gauss Seidel con Coeficiente de Relajacion***\n\n");
        if(!(diagonalmenteDominante(a,filas)))
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
        System.out.println("\nIngrese el factor relajacion\n");
        factor_relajacion = Double.valueOf(sc.nextLine());

        do {
            iteraciones++;
            for (int i = 0; i < filas; i++) {
                suma = 0.0;
                if(i == 0){
                    for(int j = 1 ; j < filas ; j++){
                        suma+= a[i][j] * xNuevo[j];
                    }
                    xNuevo[i] = (b[i] - suma)/a[i][i];
                    xNuevo[i] = factor_relajacion * xNuevo[i] + (1-factor_relajacion) * xViejo[i];
                }else{
                    for(int j = 0 ; j < i  ; j++){
                        suma += a[i][j] * xNuevo[j];
                    }

                    for(int j = i+1 ; j < filas ; j++){
                        suma+= a[i][j] * xViejo[j];
                    }

                    xNuevo[i] = (b[i] - suma)/a[i][i];
                    xNuevo[i] = factor_relajacion * xNuevo[i] + (1-factor_relajacion) * xViejo[i];
                }
            }

            // Manejo del error
            suma = 0.0;
            for (int i = 0; i < filas; i++) {
                suma += (xNuevo[i] - xViejo[i]) * (xNuevo[i] - xViejo[i]);
            }
            error = Math.sqrt(suma);

            // Reasignación del vector viejo para la próxima pasada.
            for (int i = 0; i < filas; i++) {
                xViejo[i] = xNuevo[i];
            }
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
