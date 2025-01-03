package src.test.java;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import src.main.java.Matrix;
//TODO: Finish these constructors. Make them all check if not null and check if they have the correct value.
public class ConstructorTesting {

    @Test
    public void testConstructorRowsCols(){
        Matrix m = new Matrix(3, 3);
        assertNotNull(m);
    }

    @Test
    public void testConstructorVectors(){
        Matrix m = new Matrix(3, 3);
        assertNotNull(m);
    }

    @Test
    public void testConstructor2DArray(){
        Matrix m = new Matrix(3, 3);
        assertNotNull(m);
    }

    @Test
    public void testConstructor1DArray(){
        Matrix m = new Matrix(3, 3);
        assertNotNull(m);
    }
}
