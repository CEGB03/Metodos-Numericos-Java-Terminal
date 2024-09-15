import metodos.Interpolacion.Interpolaciones;
import metodos.LocRaices.Abiertos.*;
import metodos.LocRaices.Cerrados.*;
import metodos.Regresion.Regresiones;
import metodos.SistEcLin.*;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * La clase Main proporciona un menú de selección para ejecutar diferentes métodos numéricos.
 * Permite al usuario elegir entre métodos de búsqueda de raíces, regresiones, interpolaciones y sistemas de ecuaciones.
 */
public class Main {

    // Scanner para la entrada del usuario.
    static Scanner sc = new Scanner(System.in);

    /**
     * Método principal que ejecuta el menú de selección y llama a los métodos correspondientes
     * basados en la opción ingresada por el usuario.
     * @param args Argumentos de línea de comandos (no utilizados).
     * @throws FileNotFoundException Si el archivo para la matriz no es encontrado.
     */
    public static void main(String[] args) throws FileNotFoundException {
        // Bucle infinito para mostrar el menú y ejecutar opciones.
        while (true) {
            // Mostrar menú de opciones al usuario.
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

            // Leer la opción seleccionada por el usuario.
            int op = sc.nextInt();

            // Ejecutar el método correspondiente según la opción seleccionada.
            switch (op) {
                case 1:
                    // Método de Bisección
                    Biseccion biseccion = new Biseccion();
                    biseccion.biseccion();
                    break;
                case 2:
                    // Método de Regula Falsi
                    RegulaFalsi regulaFalsi = new RegulaFalsi();
                    regulaFalsi.regulaFalsi();
                    break;
                case 3:
                    // Método de Punto Fijo
                    PuntoFijo puntoFijo = new PuntoFijo();
                    puntoFijo.puntoFijo();
                    break;
                case 4:
                    // Método de Newton-Rapson
                    NewtonRapson newtonRapson = new NewtonRapson();
                    newtonRapson.newtonRaphson();
                    break;
                case 5:
                    // Método de Tangente
                    Tangente tangente = new Tangente();
                    tangente.tangente();
                    break;
                case 6:
                    // Sistema de Eliminación Gaussiana
                    System.out.println("Ingresar el nombre del archivo donde está la matriz:");
                    sc.nextLine(); // Limpiar el buffer del scanner
                    String dir = sc.nextLine(); // Leer el nombre del archivo
                    EliminacionGaussiana eliminacionGaussiana = new EliminacionGaussiana(dir);
                    eliminacionGaussiana.eliminar();
                    break;
                case 7:
                    // Sistema por Eliminación Iterativa
                    MetodosIterativos metodosIterativos = new MetodosIterativos();
                    break;
                case 8:
                    // Método de Interpolación
                    Interpolaciones interpolaciones = new Interpolaciones();
                    break;
                case 9:
                    // Método de Regresiones
                    Regresiones regresiones = new Regresiones();
                    break;
                default:
                    // Mensaje de error para opciones no válidas
                    System.out.println("Método no reconocido");
                    break;
            }
            System.out.println("Fin del Main");
        }
    }
}
