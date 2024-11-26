import java.util.Arrays;

public class Matrix {
    
    //'struct' is the 2d matrix that stores all the values of the class
    private double[][] struct;

    //Creates a new, empty matrix with 'rows' amount of rows and 'columns' amount of columns
    public Matrix(int rows, int columns){
        this.struct = new double[rows][columns];
    }

    //TODO: Finish this function which will create a matrix out of a list of vectors
    // public Matrix(Vector[] vectors){
    //     this.struct = new double[vectors][]
    // }

    public void augment(Vector v){

    }

    //TODO: Make nicer, remove the commas and shit
    public void printMatrix(){
        for (double[] row : struct) {
            System.out.println(Arrays.toString(row));
        }
    }

    //Replaces all values in a matrix with consecutive numbers starting from 1 until the end. Left to right, top to bottom.
    public void populate(){
        int count = 1;

        for(int i=0; i<getRowAmount(); i++){
            for(int j=0; j<getColumnAmount(); j++){
                setValue(i, j, count);
                count++;
            }
        }
    }

    public int getColumnAmount(){
        return struct[0].length;
    }

    public int getRowAmount(){
        return struct.length;
    }

    public double getValue(int row, int column){
        return struct[row][column];
    }

    /*Returns the column at the specified index as a vector.*/
    public Vector getColumn(int columnIndex){
        int vectorLength = this.getRowAmount();
        Vector v = new Vector(vectorLength);

        for(int i=0; i<vectorLength; i++){
            v.setValue(i, getValue(i, columnIndex));
        }

        return v;
    }

    public void setValue(int row, int column, double val){
        struct[row][column] = val;
    }



}