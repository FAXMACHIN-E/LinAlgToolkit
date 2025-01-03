package src.test.java;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import src.main.java.Matrix;

public class ConstructorTesting {

    @Test
    public void testConstructorRowsCols(){
        Matrix m = new Matrix(3, 3);
        assertNotNull(m);
    }


    @Test
    public void rrefTestArrayZeros(){
        double[][] matrix = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0},
        };
  
        Matrix m = new Matrix(matrix);

        m.rref();

        assertNotNull(m);
    }
}
