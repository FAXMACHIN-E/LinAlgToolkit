package src.test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import src.main.java.Matrix;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;

@RunWith(Theories.class)
public class RrefTesting {

   @DataPoints
   public static int[] POSSIBLE_SIZES = {1, 2, 3, 4, 5, 6};

    @Theory
    public void testEmptyMatrices(int rows, int columns){
        Matrix matrix = new Matrix(rows, columns);
        matrix.rref();
        
        for(int row=0; row<matrix.getRowAmount(); row++){
            for(int col=0; col<matrix.getColumnAmount(); col++){
                assertEquals(0, matrix.getValue(row, col), 0.001);
            }
        }
    }

    //TODO: Probably is a better way to combine these
    @Test
    public void testStandardMatrix(){
        double[][] m = {
            {1, 8, 6, 0, 5, 4, 2, 1},
            {5, 9, 4, 0, 0, 1, 2, 7},
            {2, 7, 8, 6, 5, 2, 1, 9},
            {1, 8, 7, 5, 3, 9, 0, 9},
            {9, 11, -2, -1, 7, 7, 21, 1},
            {9, 8, 7, 6, 4, 0, 4, 9},
            {5, -9, 6, 3, 2, 2, 9, 9}
        };
        Matrix matrix = new Matrix(m);
        matrix.rref();

        double[][] e = {
            {1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.6936571201999644},
            {0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.4022079716403161},
            {0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.2388822072935406},
            {0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.2018856853857027},
            {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, -2.0287453533611677},
            {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, -0.23548605231359537},
            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0641855396882063}
        };
        Matrix expectedOutput = new Matrix(e);

        assertTrue(matrix.equals(expectedOutput));
    }

    @Test
    public void testZeroRowRref(){
        double[][] m = {
            {1, 1, 2, 3},
            {0, 0, 1, 1},
            {1, 1, 2, 3},
        };
        Matrix matrix = new Matrix(m);
        matrix.rref();

        double[][] e = {
            {1.0, 1.0, 0.0, 1.0},
            {0.0, 0.0, 1.0, 1.0},
            {0.0, 0.0, 0.0, 0.0}
        };
        Matrix expectedOutput = new Matrix(e);

        assertTrue(matrix.equals(expectedOutput));
    }


}
