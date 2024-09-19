package metodos.Regresion;

public class RegresionExponencial {
    public static void main(String[] args) {
        // Datos de la tabla
        double[] x = {0.5, 0.8, 1.3, 2.0};
        double[] y = {-0.716, -0.103, 3.419, 52.598};

        // Transformación de x a z = exp(x^2)
        double[] z = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            z[i] = Math.exp(x[i] * x[i]);
        }

        // Preparación de la matriz de diseño X y el vector de respuestas Y
        double[][] X = new double[x.length][2];
        double[] Y = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            X[i][0] = z[i]; // Columna para 'a'
            X[i][1] = 1.0;  // Columna para 'b'
            Y[i] = y[i];
        }

        // Calcular la transpuesta de X
        double[][] Xt = transpose(X);

        // Calcular Xt * X
        double[][] XtX = multiply(Xt, X);

        // Calcular Xt * Y
        double[] XtY = multiply(Xt, Y);

        // Resolver XtX * coef = XtY para obtener coef (a, b)
        double[] coef = solveLinearSystem(XtX, XtY);

        // Imprimir resultados
        System.out.println("Coeficiente a: " + coef[0]);
        System.out.println("Coeficiente b: " + coef[1]);
    }

    // Transponer una matriz
    private static double[][] transpose(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[][] transposed = new double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }

    // Multiplicar dos matrices
    private static double[][] multiply(double[][] a, double[][] b) {
        int rowsA = a.length;
        int colsA = a[0].length;
        int colsB = b[0].length;
        double[][] result = new double[rowsA][colsB];
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    // Multiplicar una matriz y un vector
    private static double[] multiply(double[][] matrix, double[] vector) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[] result = new double[rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }

    // Resolver un sistema de ecuaciones lineales usando eliminación de Gauss
    private static double[] solveLinearSystem(double[][] A, double[] b) {
        int n = b.length;
        double[][] augmented = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            System.arraycopy(A[i], 0, augmented[i], 0, n);
            augmented[i][n] = b[i];
        }

        // Eliminación de Gauss
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double factor = augmented[j][i] / augmented[i][i];
                for (int k = i; k <= n; k++) {
                    augmented[j][k] -= factor * augmented[i][k];
                }
            }
        }

        // Sustitución hacia atrás
        double[] result = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            result[i] = augmented[i][n];
            for (int j = i + 1; j < n; j++) {
                result[i] -= augmented[i][j] * result[j];
            }
            result[i] /= augmented[i][i];
        }

        return result;
    }
}