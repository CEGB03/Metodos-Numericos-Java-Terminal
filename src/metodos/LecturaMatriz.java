package metodos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * La clase LecturaMatriz lee una matriz desde un archivo y la almacena en matrices internas.
 * También maneja la separación del vector b si el archivo contiene una matriz aumentada.
 */
public class LecturaMatriz {
    private int filas = 0;
    private int columnas = 0;
    private int correccionM = 0;
    Double[][] mAux; // Matriz para almacenar los valores leídos
    Double[][] mCarga; // Matriz auxiliar que almacena los valores leídos
    Double[][] mFinal; // Matriz final sin la columna adicional
    Double[] b; // Vector b si se encuentra en la matriz aumentada

    /**
     * Constructor que lee una matriz desde un archivo.
     * @param archivo Nombre del archivo que contiene la matriz.
     * @throws FileNotFoundException Si el archivo no se encuentra.
     */
    public LecturaMatriz(String archivo) throws FileNotFoundException {
        //archivo+=".dat";
        try {
            // Contar el número de filas en el archivo
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            int c;
            while ((c = br.read()) != -1) {
                char caracter = (char) c;
                if (caracter == '\n') {
                    filas++;
                }
            }
            br.close();
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            System.out.println("numero de filas=" + filas);
            String linea;
            int i = 0;
            int j = 0;

            // Inicializar la matriz auxiliar
            mAux = new Double[filas][(filas + 1)];
            for (int ii = 0; ii < filas; ii++) {
                for (int jj = 0; jj < (filas + 1); jj++)
                    mAux[ii][jj] = 0.;
            }
            // Leer la matriz desde el archivo
            while ((linea = br.readLine()) != null) {
                j = 0;
                System.out.println(linea);
                String[] elementos = linea.split(" ");
                for (String elemento : elementos) {
                    mAux[i][j] = Double.parseDouble(elemento); // Convierte y almacena el valor
                    j++;
                }
                i++;
            }

            br.close();
            columnas = j;
            mCarga = new Double[filas][columnas];
            mCarga = mAux.clone();
            // Verificar si hay una columna adicional (vector b)
            if ((columnas - filas) != 0) {
                correccionM = 2;
                columnas--;
                mFinal = new Double[filas][columnas];
                b = new Double[filas];
                for (i = 0; i < filas; i++) {
                    for (j = 0; j < columnas; j++)
                        mFinal[i][j] = mCarga[i][j];
                    b[i] = mCarga[i][columnas];
                }
            } else {
                correccionM = 1;
                System.out.println("No hay matriz b\n");
                System.out.println("numero de columnas de matriz regular= " + columnas);

                for (i = 0; i < filas; i++) {
                    for (j = 0; j < columnas; j++)
                        mFinal[i][j] = mCarga[i][j];
                }
            }
            System.out.println("Fin de la recumeracion.");
        } catch (IOException e) {
            throw new FileNotFoundException("No se puede abrir el archivo .dat.");
        }
    }

    public Double[] getB() {
        return b;
    }

    public Double[][] getmFinal() {
        return mFinal;
    }

    public int getColumnas() {
        return columnas;
    }

    public int getFilas() {
        return filas;
    }

    public Double[][] getmCarga() {
        return mCarga;
    }
}
