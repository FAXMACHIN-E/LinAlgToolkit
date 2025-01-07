package src.test.java;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theory;

public class MultiplyTesting {

    @DataPoints
   public static int[] POSSIBLE_ROWS_COLS = {1, 2, 3, 4, 5, 6};

    @Theory
    public void testZeroMatrixMultiplication(int rows, int columns){
        
    }

    @Theory
    public void testZeroVectorMultiplication(int rows, int columns){

    }

    

}
