package metodos.Regresion;

import metodos.LecturaMatriz;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Regresiones {
    static Scanner sc = new Scanner(System.in);
    Double[][] A;
    Double[] b;
    Double[] x;
    int filas = 0, columnas = 0;
    String dir;

    public Regresiones() throws FileNotFoundException {
        System.out.println("Seleccione:  \n 1 --> Lineal" +
                "\n 2 --> Polinomial(Cuadrados Minimos)" +
                "\n 3 --> Proximamente...");
        int intertpolaciones = Integer.parseInt(sc.nextLine());
        //sc.nextLine();

        switch (intertpolaciones) {
            case 1:
                System.out.println("Ingresar el nombre del archivo donde esta la matriz");
                dir = sc.nextLine();
                lectura(dir);
                RegresionLineal lineal = new RegresionLineal(A, filas, columnas);
                lineal.regresion();
                break;

            case 2:
                System.out.println("Ingresar el nombre del archivo donde esta la matriz");
                dir = sc.nextLine();
                lectura(dir);
                RegresionPolinomial polinomial = new RegresionPolinomial(A, filas, columnas);
                polinomial.regresion();
                break;
            case 3:
                System.out.println("Ingresar el nombre del archivo donde esta la matriz");
                dir = sc.nextLine();
                lectura(dir);

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
