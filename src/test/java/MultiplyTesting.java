package src.test.java;


import static org.junit.Assert.assertTrue;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import src.main.java.Matrix;
import src.main.java.Vector;

@RunWith(Theories.class)
public class MultiplyTesting {

    @DataPoints
   public static int[] POSSIBLE_ROWS_COLS = {1, 2, 3, 4, 5, 6};

    @Theory
    public void testZeroMatrixMultiplication(int rows, int columns){
        Matrix matrix = new Matrix(rows, columns);
        Vector vector = new Vector(columns);

        int count = 0;
        for(int i=0; i<matrix.getRowAmount(); i++){
            for(int j=0; j<matrix.getColumnAmount(); j++){
                matrix.setValue(i, j, count);
                count++;
            }
        }
        System.out.println(rows);
        System.out.println(columns);

        matrix.multiply(vector);

        Vector zeroVector = new Vector(rows);

        assertTrue(matrix.equals(zeroVector));

    }

    @Theory
    public void testZeroVectorMultiplication(int rows, int columns){

    }

    

}
