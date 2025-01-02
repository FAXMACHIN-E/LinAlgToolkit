public class Matrix {
    //TODO: Add error catching for rounding errors
    //TODO: Rename all i and j to row and column
    //TODO: Is having Matrix be a non-abstract class even useful?
    //TODO: Format all ADTs in javadoc comments
    //TODO: Make consistent formating for everything (column vs. col)
    //TODO: Throw errors if illegal parameters given
    //'struct' is the 2d matrix that stores all the values of the class
    private double[][] struct;

    /***************************************************/
    /*** -------------- CONSTRUCTORS -------------- ***/
    /*************************************************/

    /*Creates a new, empty matrix with 'rows' amount of rows and 'columns' amount of columns*/
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

    /*Creates a matrix from a 1D double array */
    public Matrix(double[] row){
        this.struct = new double[1][row.length];
        for(int column=0; column<row.length; column++){
            this.struct[0][column] = row[column];
        }
    }

    /*Creates a matrix from an existing 2D double array */
    public Matrix(double[][] struct){
        this.struct = struct;
    }

    /**********************************************/
    /*** -------------- GETTERS -------------- ***/
    /********************************************/

    public int getColumnAmount(){
        return struct[0].length;
    }

    public int getRowAmount(){
        return struct.length;
    }

    public int getSize(){
        return getColumnAmount() * getRowAmount();
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

    public double[] getRow(int rowIndex){
        return struct[rowIndex];
    }

    /**********************************************/
    /*** -------------- SETTERS -------------- ***/
    /********************************************/
    public void setValue(int row, int column, double val){
        struct[row][column] = val;
    }

    
    /*Augments this matrix with the given vector */
    //TODO: Make this output an error if the dimensions of the vector and matrix are mismatched
    public void augment(Vector b){
        double[][] originalMatrix = new double[getRowAmount()][getColumnAmount()];

        for(int row=0; row<getRowAmount(); row++){
            originalMatrix[row] = getRow(row);
        }

        struct = new double[getRowAmount()][getColumnAmount()+1];

        for(int row=0; row<getRowAmount(); row++){
            for(int column=0; column<getColumnAmount()-1; column++){
                struct[row][column] = originalMatrix[row][column];
            }
            struct[row][getColumnAmount()-1] = b.getValue(row);
        }

    }

    public void printMatrix(){
        for (double[] row : struct) {
            for(double element : row){
                System.out.print(element + "    ");
            }
            System.out.println();
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

    /****************************************************************/
    /*** -------------- ELEMENTARY ROW OPERATIONS -------------- ***/
    /**************************************************************/

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
            //setValue(row, i, getValue(row, i)*scale);
        }
    }

    /*Changes the value of 'targetRow' to 'targetRow + scaledRow*scale' */
    public void rowReplace(int targetRow, int scaledRow, double scale){
        for(int i=0; i<struct[targetRow].length; i++){
            struct[targetRow][i] = struct[targetRow][i] + scale*struct[scaledRow][i];
        }
    }

    /*Returns true if the matrix is in rref form, false if not */
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

        //This part checks if all the rows of zeros are at the bottom
        //When the first row of zeros is hit, this boolean becomes true.
        //If any rows below that are not full of zeros, then it is not in rref.
        boolean rowsOfZerosHit = false;

        for(int row=0; row<getRowAmount();row++){
            //If we do not yet have a row of zeros, we iterate through rows to find one.
            if(!rowsOfZerosHit){
                //rowOfZerosHit is true by default. If we hit a non-zero value we switch it back false.
                rowsOfZerosHit = true;
                for(int col=0; col<getColumnAmount(); col++){
                    if(getValue(row, col)!= 0.0){
                        rowsOfZerosHit = false;
                        break;
                    }
                }
            }else{
                //If rowOfZerosHit is true, then we check the rows below it.
                //If we find a non-zero value we know it is not rref.
                for(int col=0; col<getColumnAmount(); col++){
                    if(getValue(row, col)!= 0.0){
                        return false;
                    }
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

                //TODO: Remove the part about moving over zeros at the bottom, and replace it with if a pivot is not found in that column, move over a column.

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
                for(int row=0; row<getRowAmount();row++){
                    if(row!=pivotrow){
                        //Find the common factor between them and use row replacement to make the position in the lower row 0
                        double factor = -(getValue(row, col)/getValue(pivotrow, col));
                        rowReplace(row, pivotrow, factor);
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

    public boolean isSquare(){
        return getRowAmount()==getColumnAmount();
    }

    public double determinant(){
        if(!isSquare()){
            throw new IllegalArgumentException("Matrix must be square");
        }else{
            //Base Case: If the matrix is 1x1, return the singular value
            if(getSize()==1){
                return getValue(0, 0);
            //Other Case: Go across the first row of the matrix and multiply each value by the determinant of the sub matrix
            }else{
                //Where we store the submatrices
                Matrix[] subMatrices = new Matrix[getColumnAmount()];

                //Iterate through the top row once and get all of the sub matrices for each value
                for(int i=0; i<getColumnAmount();i++){
                    Matrix subMatrix = new Matrix(getColumnAmount()-1, getRowAmount()-1);

                    for(int row=1; row<getRowAmount(); row++){
                        int colCount = 0;
                        for(int col=0; col<getColumnAmount(); col++){
                            if(col!=i){
                                subMatrix.setValue(row-1, colCount, getValue(row, col));
                                colCount++;
                            }
                        }
                    }

                    subMatrices[i] = subMatrix;
                }

                //TODO: Integrate this step into the first loop
                double finalsum = 0;

                for(int i=0; i<getColumnAmount(); i++){
                    if((i+1)%2==1){
                        finalsum += getValue(0, i) * subMatrices[i].determinant();
                    }else{
                        finalsum -= getValue(0, i) * subMatrices[i].determinant();
                    }
                }

                return finalsum;
            }
        }
    }

    public void test(){
        Matrix[] subDeterminants = new Matrix[getColumnAmount()];

        for(int i=0; i<getColumnAmount();i++){
            Matrix subMatrix = new Matrix(getColumnAmount()-1, getRowAmount()-1);

            int rowCount = -1;
            int colCount = 0;

            for(int row=0; row<getRowAmount(); row++){
                colCount = 0;
                if(row!=i){
                    rowCount++;
                    for(int col=0; col<getColumnAmount(); col++){
                        if(col!=i){
                            subMatrix.setValue(rowCount, colCount, getValue(row, col));
                            colCount ++;
                        }
                    }
                }
                
            }

            subDeterminants[i] = subMatrix;
        }

        for (Matrix matrix : subDeterminants) {
            matrix.printMatrix();
            System.out.println();
        }
    }

    //TODO:These two
    public boolean isInvertible(){
        return false;
    }

    public void invert(){
        
    }

    //TODO: Make this do something when there is infinitly many solutions/no solutions
    //TODO: Make this make sense
    public Vector getAnswer(){
        //This needs to change
        if(getColumnAmount()<=getRowAmount()){
            return null;
        }

        Matrix tempMatrix = new Matrix(struct);
        tempMatrix.rref();
        return tempMatrix.getColumn(getColumnAmount()-1);
    }

    //TODO: This
    public Vector getAnswer(Vector b){
        return null;
    }
           




}