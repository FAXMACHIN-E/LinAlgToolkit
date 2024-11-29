import java.util.Arrays;

public class Matrix {
    
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

    //TODO: this
    public void rref(){
        boolean[] pivotColumns = new boolean[getColumnAmount()];

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
                    pivotColumns[col] = true;
                    break;
                }
            }

            //If there is no pivot, we end this iteration as we cannot reduce the rows below
            if(!pivotFound){
                continue;
            }

            //If there is a pivot, it will reduce all the values below the pivot to zero
            //TODO: THE ISSUE IS HERE
            int pivotRow = col;
            for(int row=pivotRow+1; row<getRowAmount()-1;row++){
                if(getValue(row, col)!=0){
                    double f1 = getValue(row, col);
                    double f2 = getValue(row, col);

                    double factor = -(getValue(row+1, col)/getValue(pivotRow, col));
                    rowReplace(row+1, row, factor);
                }
            }
        }

        for(int row=getRowAmount()-1;row>-1;row--){
            boolean pivotRow = true;
            for(int col=0; col<getColumnAmount(); col++){
                
            }
        }

        System.out.println(Arrays.toString(pivotColumns));
        
    }




}