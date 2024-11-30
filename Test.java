public class Test {
    public static void main(String[] args) {
        double[][] matrix = {
            {2.0, 4.0, -1.0, 3.0, 4.0},
            {-5.0, 2.0, 0.0, -6.0, -3.0},
            {3.0, -1.0, 4.0, 5.0, 8.0},
            {1.0, -11.0, 2.0, 5.0, 5},

        };

        Matrix m = new Matrix(matrix);

        m.rref();

        m.printMatrix();



    }

    
}
