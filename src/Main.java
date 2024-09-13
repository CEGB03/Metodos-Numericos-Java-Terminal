
import metodos.Interpolacion.Interpolaciones;
import metodos.LocRaices.Abiertos.*;
import metodos.LocRaices.Cerrados.*;
import metodos.Regresion.Regresiones;
import metodos.SistEcLin.*;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException {
        while (true){
            System.out.println("Menu de seleccion" +
                    "\nSeleccione el metodo a usar:" +
                    "\n1) Biseccion." +
                    "\n2) Regula Falsi." +
                    "\n3) Punto Fijo." +
                    "\n4) Newton-Rapson." +
                    "\n5) Tangente." +
                    "\n6) Sistema de Eliminacion Gaussiana." +
                    "\n7) Sistema por Eliminacion Iterativa." +
                    "\n8) Interpolacion." +
                    "\n9) Regresiones.");
            int op = sc.nextInt();

            switch (op) {
                case 1:
                    Biseccion biseccion = new Biseccion();
                    biseccion.biseccion();
                    break;
                case 2:
                    RegulaFalsi regulaFalsi = new RegulaFalsi();
                    regulaFalsi.regulaFalsi();
                    break;
                case 3:
                    PuntoFijo puntoFijo = new PuntoFijo();
                    puntoFijo.puntoFijo();
                    break;
                case 4:
                    NewtonRapson newtonRapson = new NewtonRapson();
                    newtonRapson.newtonRaphson();
                    break;
                case 5:
                    Tangente tangente = new Tangente();
                    tangente.tangente();
                    break;
                case 6:
                    System.out.println("Ingresar el nombre del archivo donde esta la matriz");
                    System.out.println("sc = " + sc);
                    sc.nextLine();
                    String dir = sc.nextLine();
                    EliminacionGaussiana eliminacionGaussiana = new EliminacionGaussiana(dir);
                    eliminacionGaussiana.eliminar();
                    break;
                case 7:
                    MetodosIterativos metodosIterativos = new MetodosIterativos();
                    break;
                case 8:
                    Interpolaciones interpolaciones = new Interpolaciones();
                    break;
                case 9:
                    Regresiones regresiones = new Regresiones();
                    break;
                default:
                    System.out.println("Metodo no reconocido");
                    break;
            }
            System.out.println("Fin del Main");
        }
    }
}