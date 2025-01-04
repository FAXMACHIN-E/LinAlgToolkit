package src.test.java;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import src.main.java.Matrix;
import src.main.java.Vector;
//TODO: This deep of testing is not necessary
public class ConstructorTesting {

    @Test
    public void testMatrixConstructorRowsCols(){
        Matrix matrix = new Matrix(3, 2);

        assertNotNull(matrix);
        assertEquals(3, matrix.getRowAmount());
        assertEquals(2, matrix.getColumnAmount());
    }

    @Test
    public void testMatrixConstructorRowsColsInvalid(){
        assertThrows(IllegalArgumentException.class, () -> new Matrix(0, 0));
    }

    //TODO:Finish this
    @Test
    public void testMatrixConstructorVectors(){
        Vector v1 = new Vector(new double[]{1, 2, 3});
        Vector v2 = new Vector(new double[]{4, 5, 6});



    }

    @Test
    public void testMatrixConstructorVectorsInvalid(){



    }

    @Test
    public void testMatrixConstructor2DArray(){
        double[][] m = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8}
        };
        Matrix matrix = new Matrix(m);

        assertNotNull(matrix);

        int counter = 0;
        for(int row=0; row<matrix.getRowAmount(); row++){
            for(int col=0; col<matrix.getColumnAmount(); col++){
                assertEquals(counter, matrix.getValue(row, col), 0.001);
                counter++;
            }
        }
    }

    @Test
    public void testMatrixConstructor2DArrayInvalid(){
        double[][] m = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7}
        };

        assertThrows(IllegalArgumentException.class, () -> new Matrix(m));
    }

    @Test
    public void testMatrixConstructor1DArray(){
        double[] m = {0, 1, 2, 3, 4};
        Matrix matrix = new Matrix(m);

        assertNotNull(matrix);
        for(int i=0; i<matrix.getColumnAmount(); i++){
            assertEquals(i, matrix.getValue(0, i), 0.001);
        }
    }

    @Test
    public void testMatrixConstructor1DArrayInvalid(){
        double[] m = {};
        assertThrows(IllegalArgumentException.class, () -> new Matrix(m));
    }
}
