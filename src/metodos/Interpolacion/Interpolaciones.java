package metodos.Interpolacion;

import metodos.SistEcLin.LecturaMatriz;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Interpolaciones {
    static Scanner sc = new Scanner(System.in);
    Double[][] A;
    Double[] b;
    Double[] x;
    int filas = 0, columnas = 0;
    String dir;

    public Interpolaciones() throws FileNotFoundException {
        System.out.println("Seleccione:  \n 1 --> Lagrange" +
                                        "\n 2 --> Polinomial" +
                                        "\n 3 --> \n");
        int intertpolaciones = Integer.parseInt(sc.nextLine());
        sc.nextLine();

        switch (intertpolaciones) {
            case 1:
                System.out.println("Ingresar el nombre del archivo donde esta la matriz");
                dir = sc.nextLine();
                lectura(dir);
                Lagrange l = new Lagrange(A, filas, columnas);
                l.interpolar(sc);
                break;

            case 2:
                System.out.println("Ingresar el nombre del archivo donde esta la matriz");
                dir = sc.nextLine();
                lectura(dir);
                Polinomial p = new Polinomial(A, filas);
                p.interpolar(sc);
                break;
            case 3:
                System.out.println("Ingresar el nombre del archivo donde esta la matriz");
                dir = sc.nextLine();
                lectura(dir);
                /*GaussSeidelRelajacion gsr = new GaussSeidelRelajacion(A, b, filas);
                gsr.eliminar(sc);*/
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
        A = lecturaMatriz.getmCarga();
    }
}
