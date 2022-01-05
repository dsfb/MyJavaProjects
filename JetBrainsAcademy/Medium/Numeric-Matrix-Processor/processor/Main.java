package processor;

import java.util.Scanner;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);
    private final static Scanner intScanner = new Scanner(System.in);

    public static double[][] getMatrix(String order) {
        System.out.printf("Enter size of %smatrix: ", order);
        int aX = intScanner.nextInt();
        int aY = intScanner.nextInt();

        System.out.printf("Enter %smatrix:\n", order);
        double[][] aMatrix = new double[aX][aY];
        for (int i = 0; i < aX; i++) {
            for (int j = 0; j < aY; j++) {
                aMatrix[i][j] = scanner.nextDouble();
            }
        }

        return aMatrix;
    }

    public static void showMatrix(double[][] matrix) {
        System.out.println("The result is:");
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.printf("%f ", matrix[i][j]);
            }
            System.out.println();
        }
    }

    public static void addMatrices() {
        double[][] aMatrix = getMatrix("first ");
        double[][] bMatrix = getMatrix("second ");

        if (aMatrix[0].length != bMatrix[0].length || aMatrix.length != bMatrix.length) {
            System.out.println("ERROR");
            System.exit(0);
        }

        for (int i = 0; i < aMatrix[0].length; i++) {
            for (int j = 0; j < aMatrix.length; j++) {
                aMatrix[i][j] += bMatrix[i][j];
            }
        }

        showMatrix(aMatrix);
    }

    public static void multiplyMatrixByScalar() {
        double[][] aMatrix = getMatrix("");

        System.out.print("Enter constant: ");
        double scalar = scanner.nextDouble();

        for (int i = 0; i < aMatrix[0].length; i++) {
            for (int j = 0; j < aMatrix.length; j++) {
                aMatrix[i][j] *= scalar;
            }
        }

        showMatrix(aMatrix);
    }

    public static void multiplyMatrices() {
        System.out.print("Enter size of first matrix: ");
        int aX = intScanner.nextInt();
        int aY = intScanner.nextInt();

        System.out.println("Enter first matrix:");
        double[][] aMatrix = new double[aX][aY];
        for (int i = 0; i < aX; i++) {
            for (int j = 0; j < aY; j++) {
                aMatrix[i][j] = scanner.nextDouble();
            }
        }

        System.out.print("Enter size of second matrix: ");
        int bX = intScanner.nextInt();
        int bY = intScanner.nextInt();

        double[][] bMatrix = new double[bX][bY];
        System.out.println("Enter second matrix:");
        for (int i = 0; i < bX; i++) {
            for (int j = 0; j < bY; j++) {
                bMatrix[i][j] += scanner.nextDouble();
            }
        }

        double[][] rMatrix = new double[aX][bY];
        for (int i = 0; i < aX; i++) {
            for (int j = 0; j < bY; j++) {
                for (int k = 0; k < aY; k++) {
                    rMatrix[i][j] += aMatrix[i][k] * bMatrix[k][j];
                }
            }
        }

        showMatrix(rMatrix);
    }

    public static int getTransposeOption() {
        System.out.println("1. Main diagonal");
        System.out.println("2. Side diagonal");
        System.out.println("3. Vertical line");
        System.out.println("4. Horizontal line");
        System.out.print("Your choice: ");
        return intScanner.nextInt();
    }

    public static double[][] getTransposedMatrix(double[][] aMatrix) {
        double swap = 0;
        for (int i = 0; i < aMatrix[0].length; i++) {
            for (int j = i; j < aMatrix.length; j++) {
                if (i != j) {
                    swap = aMatrix[i][j];
                    aMatrix[i][j] = aMatrix[j][i];
                    aMatrix[j][i] = swap;
                }
            }
        }

        return aMatrix;
    }

    public static void transposeMatrix() {
        int transposeOption = getTransposeOption();
        double[][] aMatrix = getMatrix("");
        int aX = aMatrix[0].length;
        int aY = aMatrix.length;

        double swap = 0;
        if (transposeOption == 1) {
            aMatrix = getTransposedMatrix(aMatrix);
        } else if (transposeOption == 2) {
            for (int i = 0; i < aX; i++) {
                for (int j = 0; j < aY / 2; j++) {
                    swap = aMatrix[i][j];
                    aMatrix[i][j] = aMatrix[i][aY - 1 - j];
                    aMatrix[i][aY - 1 - j] = swap;
                }
            }
            for (int i = 0; i < aX; i++) {
                for (int j = i; j < aY; j++) {
                    if (i != j) {
                        swap = aMatrix[i][j];
                        aMatrix[i][j] = aMatrix[j][i];
                        aMatrix[j][i] = swap;
                    }
                }
            }
            for (int i = 0; i < aX; i++) {
                for (int j = 0; j < aY / 2; j++) {
                    swap = aMatrix[i][j];
                    aMatrix[i][j] = aMatrix[i][aY - 1 - j];
                    aMatrix[i][aY - 1 - j] = swap;
                }
            }
        } else if (transposeOption == 3) {
            for (int i = 0; i < aX; i++) {
                for (int j = 0; j < aY / 2; j++) {
                    swap = aMatrix[i][j];
                    aMatrix[i][j] = aMatrix[i][aY - 1 - j];
                    aMatrix[i][aY - 1 - j] = swap;
                }
            }
        } else if (transposeOption == 4) {
            for (int i = 0; i < aX / 2; i++) {
                for (int j = 0; j < aY; j++) {
                    swap = aMatrix[i][j];
                    aMatrix[i][j] = aMatrix[aX - 1 - i][j];
                    aMatrix[aX - 1 - i][j] = swap;
                }
            }
        }

        showMatrix(aMatrix);
    }

    public static int getOption() {
        System.out.println("1. Add matrices");
        System.out.println("2. Multiply matrix by a constant");
        System.out.println("3. Multiply matrices");
        System.out.println("4. Transpose matrix");
        System.out.println("5. Calculate a determinant");
        System.out.println("6. Inverse matrix");
        System.out.println("0. Exit");
        System.out.print("Your choice: ");
        return intScanner.nextInt();
    }

    public static double[][] getMinor(double[][] matrix, int x, int y) {
        double[][] minor = new double[matrix[0].length - 1][matrix.length - 1];
        int x_cter = 0;
        int y_cter = 0;
        for (int i = 0; i < matrix[0].length; i++) {
            if (i == x) {
                continue;
            }
            for (int j = 0; j < matrix.length; j++) {
                if (j == y) {
                    continue;
                }
                minor[x_cter][y_cter] = matrix[i][j];
                y_cter++;
                if (y_cter == minor.length) {
                    y_cter = 0;
                }
            }
            x_cter++;
        }
        return minor;
    }

    public static double calculateMinorDeterminant(double[][] matrix, int xC, int yC) {
        double[][] minor = getMinor(matrix, xC, yC);
        return calculateDeterminant(minor);
    }

    public static double calculateCofactor(double[][] aMatrix, int x, int y) {
        int signal = (int) Math.pow(-1, x + y);

        if (aMatrix.length == 2) {
            int xC = 1;
            if (xC == x) {
                xC = 0;
            }

            int yC = 1;
            if (yC == y) {
                yC = 0;
            }

            return signal * aMatrix[xC][yC];
        } else {
            double minor = calculateMinorDeterminant(aMatrix, x, y);
            return signal * minor;
        }
    }

    public static double[][] getCofactorMatrix(double[][] matrix) {
        double[][] cofactors = new double[matrix.length][matrix.length];

        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                cofactors[i][j] = calculateCofactor(matrix, i, j);
            }
        }

        return cofactors;
    }

    public static double calculateDeterminant(double[][] aMatrix) {
        double determinant = 0;

        if (aMatrix.length == 1) {
            determinant = aMatrix[0][0];
        } else if (aMatrix.length == 2) {
            determinant = aMatrix[0][0] * aMatrix[1][1];
            determinant -= aMatrix[0][1] * aMatrix[1][0];
        } else {
            double cofactor = -1;
            double minor = 0;
            for (int j = 0; j < aMatrix[0].length; j++) {
                cofactor *= -1;
                minor = calculateMinorDeterminant(aMatrix, 0, j);
                determinant += minor * cofactor * aMatrix[0][j];
            }
        }

        return determinant;
    }

    public static double[][] getInverseMatrix(double[][] matrix, double determinant) {
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] /= determinant;
            }
        }

        return matrix;
    }

    public static void main(String[] args) {
        int option = getOption();
        double[][] matrix = new double[1][1];
        double determinant = 0;
        while (option != 0) {
            switch (option) {
                case 1:
                    addMatrices();
                    System.out.println();
                    break;
                case 2:
                    multiplyMatrixByScalar();
                    System.out.println();
                    break;
                case 3:
                    multiplyMatrices();
                    System.out.println();
                    break;
                case 4:
                    transposeMatrix();
                    break;
                case 5:
                    matrix = getMatrix("");
                    determinant = calculateDeterminant(matrix);
                    System.out.println("The result is:");
                    System.out.println(determinant);
                    break;
                case 6:
                    matrix = getMatrix("");
                    determinant = calculateDeterminant(matrix);
                    if (determinant == 0) {
                        System.out.println("This matrix doesn't have an inverse.");
                    } else {
                        double[][] cofactors = getCofactorMatrix(matrix);
                        cofactors = getTransposedMatrix(cofactors);
                        cofactors = getInverseMatrix(cofactors, determinant);
                        showMatrix(cofactors);
                    }
                    break;
                default:
                    break;
            }
            option = getOption();
        }
    }
}

