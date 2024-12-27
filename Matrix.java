import java.util.Arrays;

public class Matrix {
    //TODO: Add error catching for rounding errors
    //'struct' is the 2d matrix that stores all the values of the class
    private double[][] struct;


    //Creates a new, empty matrix with 'rows' amount of rows and 'columns' amount of columns
    public Matrix(int rows, int columns){
        this.struct = new double[rows][columns];
    }

    /*Creates a matrix whose columns are the vectors provided here */
    public Matrix(Vector... vectors){
        this.struct = new double[vectors[0].getRowAmount()][vectors.length];
        for(int i=0; i<struct.length; i++){
            for(int j=0; j<struct[0].length; j++){
                struct[i][j] = vectors[j].getValue(i);
            }
        }
    }

    public Matrix(double[][] struct){
        this.struct = struct;
    }

    //TODO
    public void augment(Vector b){

    }

    //TODO: Make nicer, remove the commas and shit
    public void printMatrix(){
        for (double[] row : struct) {
            System.out.println(Arrays.toString(row));
        }
    }

    /*Replaces all values in a matrix with consecutive numbers starting from 1 until the end. Left to right, top to bottom.*/
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

    /*ELEMENTARY ROW OPPERATIONS*/
    //TODO:Stop dealing with struct directly and instead use getters and setters here
    /*Input the index of 2 rows and it swaps them*/
    public void rowInterchange(int row1, int row2){
        double[] temp = struct[row2];
        struct[row2] = struct[row1];
        struct[row1] = temp;
    }

    /*Performs scalar multiplicaiton on 1 row. */
    public void rowScale(int row, double scale){
        for(int i=0; i<struct[row].length; i++){
            struct[row][i] *= scale;
        }
    }

    /*Changes the value of 'targetRow' to 'targetRow + scaledRow*scale' */
    public void rowReplace(int targetRow, int scaledRow, double scale){
        for(int i=0; i<struct[targetRow].length; i++){
            struct[targetRow][i] = struct[targetRow][i] + scale*struct[scaledRow][i];
        }
    }

    /*Returns true if the matrix is in rref form, false if not */
    //TODO: Check if rows of 0s at the bottom
    public boolean inRref(){

        for(int row=0; row<getRowAmount(); row++){

            //Iterates through each value in the row
            for(int col=0; col<getColumnAmount(); col++){
                //Finds the first non-zero value. If it is not 1.0, return false. Otherwise see if all other values in the pivot column are 0.
                if(getValue(row, col) != 0){
                    if(getValue(row, col) != 1.0){
                        return false;
                    }

                    //If a value above or below the pivot position is not 0.0, return false.
                    for(int i=0; i<getRowAmount(); i++){
                        if(getValue(i, col) != 0.0 && i!=row){
                            return false;
                        }
                    }

                    break;
                }               
            }

        }

        return true;
    }

    public void rref(){

        //Repeat the reduction process until it is in rref
        while(!this.inRref()){
            //the two for loops interate over the matrix, it first iterates over the column to find the pivot
            for(int col=0; col<getColumnAmount(); col++){
                //boolean that checks if the pivot was found, starts 'false' at the beginning of each column
                boolean pivotFound = false;
                
                //iterates over each row on and below the diagonal.
                //we don't look above the diagonal as those are already pivot rows
                for(int row=col; row<getRowAmount(); row++){
                    //Swaps the current row with every row below it until it is non-zero
                    //When it is non zero it stops searching and breaks the loop
                    if(getValue(row, col)!= 0.0){
                        rowInterchange(row, col);
                        pivotFound = true;
                        break;
                    }
                }

                //If there is no pivot, we end this iteration as we cannot reduce the rows below
                if(!pivotFound){
                    continue;
                }

                //If there is a pivot, it will reduce all the values below the pivot to zero
                //First we save the index of the row that contains the pivot
                int pivotrow = col;

                //We scale the pivot row so that it pivot is equal to 1
                rowScale(pivotrow, 1/getValue(pivotrow, col));

                //We iterate through all rows below the pivot row
                for(int row=pivotrow+1; row<getRowAmount();row++){
                    if(getValue(row, col)!=0){
                        //Find the common factor between them and use row replacement to make the position in the lower row 0
                        double factor = -(getValue(row, col)/getValue(pivotrow, col));
                        rowReplace(row, pivotrow, factor);
                    }
                }
            }


            //Going back up the matrix, starting from the lowest row, reducing up
            for(int row=getRowAmount()-1;row>-1;row--){
                //'pivot' will store the pivot position
                int pivot = 0;
                //iterates through the row to find the pivot index
                for(int col=0; col<getColumnAmount(); col++){
                    if(getValue(row, col)==1.0){
                        pivot = col;
                        break;
                    }
                }
                //if a pivot exists, iterate through rows above to reduce them
                if(pivot!=0){
                    for(int targetRow=row-1; targetRow>-1;targetRow--){
                        double factor = -(getValue(targetRow, pivot)/getValue(row, pivot));
                        rowReplace(targetRow, row, factor);
                    }
                }
            }

            //This is the part that puts the rows of zeros at the bottom
            //Tracks the index of non-zero rows already moved to the top
            int count = 0;
            
            for(int row=0; row<getRowAmount();row++){
                //Start off by assuming that it is a row of zeros.
                //If while iterating we find a non-zero value, we know it is not a row of zeros.
                boolean rowOfZeros = true;
                for(int col=0; col<getColumnAmount(); col++){
                    if(getValue(row, col)!= 0.0){
                        rowOfZeros = false;
                        break;
                    }
                }

                //If it is not a row of zeros, swap it with the top non-checked row.
                if(!rowOfZeros){
                    rowInterchange(row, count);
                    count++;
                }

            }
        }     
    }
        

        




}