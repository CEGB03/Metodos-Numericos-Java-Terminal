package metodos.SistEcLin;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LecturaMatriz {
        private int filas=0;
        private int columnas=0;
        private int correccionM=0;
        Double[][] mCarga; // Matriz para almacenar los valores leídos
        Double[][] mFinal;
        Double[]b;
        public LecturaMatriz(String archivo) throws FileNotFoundException {
            archivo+=".dat";
            try {
                FileReader fr = new FileReader(archivo);
                BufferedReader br = new BufferedReader(fr);
                int c=0;

                while ((c = br.read()) != -1) {
                    char caracter = (char) c;
                    if (caracter == '\n') {
                        filas++; // Incrementa el contador cuando se encuentra un salto de línea
                    }
                }
                br.close();
                fr = new FileReader(archivo);
                br = new BufferedReader(fr);

                System.out.println("numero de filas="+filas);
                String linea="";
                int i = 0;
                int j = 0;

                mCarga=new Double[filas][(filas+1)];
                for (int ii=0; ii<filas; ii++){
                    for (int jj=0; jj<(filas+1); jj++)
                        mCarga[ii][jj]=0.;
                }
                c=0;
                while ((linea = br.readLine()) != null) {
                    /*for (i = 0; i < filas; i++) {*/
                    j = 0;
                    String[] elementos = linea.split(" ");
                    for (String elemento : elementos) {
                        mCarga[i][j] = Double.parseDouble(elemento); // Convierte y almacena el valor en el arreglo
                        j++;
                    }
                    i++;
                }

                br.close();
                columnas = j;
                if ((columnas - filas) != 0) {
                    correccionM = 2;
                    System.out.println("numero de columnas de matriz irregular="+columnas+"\nDice irregular ya que la matriz al inicio contiene el vector b");
                    //imprimo la matriz para verificar que lo leyo correctamente
                    columnas--;
                    mFinal=new Double[filas][columnas];
                    b=new Double[filas];
                    for (i = 0; i < filas; i++) {
                        for (j = 0; j < columnas; j++)
                            mFinal[i][j] = mCarga[i][j];
                        b[i] = mCarga[i][columnas];
                    }

                    //Impresion de la matriz completa luego de la copia.
                    for (i = 0; i < filas; i++) {
                        for (j = 0; j < columnas; j++)
                            System.out.print(mFinal[i][j]+" ");
                        System.out.println("| "+b[i]);
                    }
                } else {
                    correccionM = 1;
                    System.out.println("No hay matriz b\n");
                    System.out.println("numero de columnas de matriz regular= "+ columnas);

                    for (i = 0; i < filas; i++) {
                        for (j = 0; j < columnas; j++)
                            mFinal[i][j] = mCarga[i][j];
                    }
                    for (i = 0; i < filas; i++) {
                        for (j = 0; j < columnas; j++)
                            System.out.print(mFinal[i][j]+" ");
                        System.out.println();
                    }
                }
            } catch (IOException e){
                //throw new FileNotFoundException seria la excepcion para cortar el programa si hace falta, la parte de
                throw new FileNotFoundException("No se puede abrir el archivo .dat.");
            }
        }

        public Double[] getB() {return b;}

        public Double[][] getmFinal() {return mFinal;}

        public int getColumnas() {return columnas;}

        public int getFilas() {return filas;}
}
