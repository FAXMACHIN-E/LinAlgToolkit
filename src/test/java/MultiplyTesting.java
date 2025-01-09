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
    public void testZeroVectorMultiplication(int rows, int columns){
        Matrix matrix = new Matrix(rows, columns);
        Vector vector = new Vector(columns);

        int count = 0;
        for(int i=0; i<matrix.getRowAmount(); i++){
            for(int j=0; j<matrix.getColumnAmount(); j++){
                matrix.setValue(i, j, count);
                count++;
            }
        }

        matrix.multiply(vector);

        Vector zeroVector = new Vector(rows);

        assertTrue(matrix.equals(zeroVector));

    }

    @Theory
    public void testZeroMatrixMultiplication(int rows, int columns){
        Matrix matrix = new Matrix(rows, columns);
        Matrix empty = new Matrix(columns, rows);

        int count = 0;
        for(int i=0; i<matrix.getRowAmount(); i++){
            for(int j=0; j<matrix.getColumnAmount(); j++){
                matrix.setValue(i, j, count);
                count++;
            }
        }

        matrix.multiply(empty);

        Matrix zeroMatrix = new Matrix(rows, rows);

        assertTrue(matrix.equals(zeroMatrix));
    }

    

}
