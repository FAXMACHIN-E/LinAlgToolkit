public class Test {
    public static void main(String[] args) {
        Matrix m = new Matrix(4, 3);
        Vector v = new Vector(3);

        m.populate();
        v.populate();

        m.printMatrix();
        v.printMatrix();

        m.getColumn(2).printMatrix();



    }
}
