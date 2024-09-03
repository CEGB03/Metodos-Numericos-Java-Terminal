
import metodos.Interpolacion.Interpolaciones;
import metodos.LocRaices.Abiertos.*;
import metodos.LocRaices.Cerrados.*;
import metodos.SistEcLin.*;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Menu de seleccion" +
                "\nSeleccione el metodo a usar:" +
                "\n1) Biseccion." +
                "\n2) Regula Falsi." +
                "\n3) Punto Fijo." +
                "\n4) Newton-Rapson."+
                "\n5) Tangente."+
                "\n6) Sistema de Eliminacion Gaussiana."+
                "\n7) Sistema por Eliminacion Iterativa."+
                "\n8) Interpolacion.");
        int op = sc.nextInt();

        switch (op){
            case 1 :
                Biseccion b = new Biseccion();
                b.biseccion();
                break;
            case 2 :
                RegulaFalsi r = new RegulaFalsi();
                r.regulaFalsi();
                break;
            case 3 :
                PuntoFijo p = new PuntoFijo();
                p.puntoFijo();
                break;
            case 4 :
                NewtonRapson n = new NewtonRapson();
                n.newtonRaphson();
                break;
            case 5 :
                Tangente t = new Tangente();
                t.tangente();
                break;
            case 6 :
                System.out.println("Ingresar el nombre del archivo donde esta la matriz");
                System.out.println("sc = " + sc);
                sc.nextLine();
                String dir = sc.nextLine();
                EliminacionGaussiana eg = new EliminacionGaussiana(dir);
                eg.eliminar();
                break;
            case 7:
                MetodosIterativos mi = new MetodosIterativos();
                break;
            case 8:
                Interpolaciones i = new Interpolaciones();
                break;
            default:
                System.out.println("Metodo no reconocido");
                break;
        }
        System.out.println("Fin del Main");
    }
}