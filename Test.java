public class Test {
    public static void main(String[] args) {
        Vector v1 = new Vector(3);
        Vector v2 = new Vector(3);
        Vector v3 = new Vector(3);
        Vector v4 = new Vector(3);
        
        v1.setValue(1, 1);
        v1.setValue(0, 2);
        // v2.setValue(2, 10);
        v3.setValue(1, 1);
        v4.setValue(1, 3);

        Matrix m = new Matrix(v1, v2, v3, v4);
        m.rowInterchange(0, 2);

        
        m.printMatrix();
    

        // Matrix m = new Matrix(3, 4);
        // m.setValue(0, 0, 0);
        // m.setValue(0, 1, 5);
        // m.setValue(0, 2, -4);
        // m.setValue(0, 3, -3);
        // m.setValue(1, 0, 9);
        // m.setValue(1, 1, 9);
        // m.setValue(1, 2, 7);
        // m.setValue(1, 3, -6);
        // m.setValue(2, 0, -8);
        // m.setValue(2, 1, 7);
        // m.setValue(2, 2, -10);
        // m.setValue(2, 3, 8);

        m.rref();

        m.printMatrix();

        



    }


    
}
