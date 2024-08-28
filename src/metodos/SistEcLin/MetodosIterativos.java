package metodos.SistEcLin;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class MetodosIterativos {
    static Scanner sc = new Scanner(System.in);
    Double[][] A;
    Double[] b;
    Double[] x;
    int filas = 0, columnas = 0;
    String dir;

    public MetodosIterativos() throws FileNotFoundException {
        System.out.println("Seleccione:  \n 1 --> Jacobi" +
                                        "\n 2 --> Gauss Seidel" +
                                        "\n 3 --> Gauss Seidel con Relajacion\n");
        int iterativo = Integer.parseInt(sc.nextLine());
        sc.nextLine();
        switch (iterativo) {
            case 1:
                System.out.println("Ingresar el nombre del archivo donde esta la matriz");
                dir = sc.nextLine();
                lectura(dir);
                Jacobi j = new Jacobi(A, b, filas);
                j.eliminar(sc);
                break;

            case 2:
                System.out.println("Ingresar el nombre del archivo donde esta la matriz");
                dir = sc.nextLine();
                lectura(dir);
                GaussSeidel gs = new GaussSeidel(A, b, filas);
                gs.eliminar(sc);
                break;
            case 3:
                System.out.println("Ingresar el nombre del archivo donde esta la matriz");
                dir = sc.nextLine();
                lectura(dir);
                GaussSeidelRelajacion gsr = new GaussSeidelRelajacion(A, b, filas);
                gsr.eliminar(sc);
                break;

            default:
                System.out.println("\n Se seleccionó mal la opcion");
        }
    }
    public void lectura(String s) throws FileNotFoundException {
        LecturaMatriz lecturaMatriz = new LecturaMatriz(s);
        filas = lecturaMatriz.getFilas();
        columnas = lecturaMatriz.getColumnas();
        A = new Double[filas][columnas];
        b = new Double[filas];
        x = new Double[filas];
        A = lecturaMatriz.getmFinal();
        b = lecturaMatriz.getB();
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
