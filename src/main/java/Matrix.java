package src.main.java;

public class Matrix {
    //maven library to learn how these things are supposed to be structured

    //TODO: Consult Lior
    
    //TODO: Format all ADTs in javadoc comments
    //TODO: Make consistent formating for everything
    //TODO: Beware of overcommenting
    //TODO: More explicit exceptions;
  
    //TODO:Make getRow() and getColumn() return consistent types
    
    //'struct' is the 2d matrix that stores all the values of the class
    private double[][] struct;

    /***************************************************/
    /*** -------------- CONSTRUCTORS -------------- ***/
    /*************************************************/

    /*Creates a new, empty matrix with 'rows' amount of rows and 'columns' amount of columns*/
    public Matrix(int rows, int columns){
        if(rows==0 || columns==0){
            throw new IllegalArgumentException("Dimensions cannot be 0");
        }

        this.struct = new double[rows][columns];
    }

    /*Creates a matrix whose columns are the vectors provided here */
    public Matrix(Vector... vectors){
        int vectorSize = vectors[0].getRowAmount();
        for (Vector vector : vectors) {
            if(vector.getRowAmount() != vectorSize){
                throw new IllegalArgumentException("Vectors must be the same size.");
            }
        }

        this.struct = new double[vectors[0].getRowAmount()][vectors.length];

        for(int row=0; row<struct.length; row++){
            for(int col=0; col<struct[0].length; col++){
                struct[row][col] = vectors[col].getValue(row);
            }
        }
    }

    /*Creates a matrix from a 1D double array */
    public Matrix(double[] row){
        if(row.length==0){
            throw new IllegalArgumentException("Matrix cannot be empty");
        }
        
        this.struct = new double[1][row.length];
        for(int col=0; col<row.length; col++){
            this.struct[0][col] = row[col];
        }
    }

    /*Creates a matrix from an existing 2D double array */
    public Matrix(double[][] struct){
        if(struct.length == 0){
            throw new IllegalArgumentException("Matrix cannot be empty");
        }

        int rowSize = struct[0].length;
        for (double[] row : struct) {
            if(row.length != rowSize){
                throw new IllegalArgumentException("Rows must be the same size");
            }
        }

        this.struct = struct;
    }

    //TODO: Format this better maybe? Maybe child class?
    public static Matrix identityMatrix(int dimension){
        Matrix i = new Matrix(dimension, dimension);

        for(int diagonal=0; diagonal<dimension; diagonal++){
            i.setValue(diagonal, diagonal, 1);
        }

        return i;
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

        for(int row=0; row<vectorLength; row++){
            v.setValue(row, getValue(row, columnIndex));
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

    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        }

        if(!(o instanceof Matrix)){
            return false;
        }

        Matrix other = (Matrix)o;

        if(getSize() != other.getSize()){ 
            return false;
        }

        for(int row=0; row<getRowAmount(); row++){
            for(int col=0; col<getColumnAmount(); col++){
                //Delta of 0.001 for doubles
                if((getValue(row, col) - other.getValue(row, col)) >= 0.001 || (getValue(row, col) - other.getValue(row, col)) <= -0.001){
                    return false;
                }
            }
        }
        
        return true;
    }

    /****************************************************************/
    /*** -------------- ELEMENTARY ROW OPERATIONS -------------- ***/
    /**************************************************************/

    /*Input the index of 2 rows and it swaps them*/
    public void rowInterchange(int row1, int row2){
        double[] temp = struct[row2];
        struct[row2] = struct[row1];
        struct[row1] = temp;
    }

    /*Performs scalar multiplicaiton on 1 row. */
    public void rowScale(int row, double scale){
        for(int col=0; col<struct[row].length; col++){
            struct[row][col] *= scale;
            //setValue(row, i, getValue(row, i)*scale);
        }
    }

    /*Changes the value of 'targetRow' to 'targetRow + scaledRow*scale' */
    public void rowReplace(int targetRow, int scaledRow, double scale){
        for(int col=0; col<struct[targetRow].length; col++){
            struct[targetRow][col] = struct[targetRow][col] + scale*struct[scaledRow][col];
        }
    }

    /*Returns true if the matrix is in rref form, false if not */
    public boolean inRref(){
        int latestPivot = -1;

        for(int row=0; row<getRowAmount(); row++){
            //Iterates through each value in the row
            for(int col=0; col<getColumnAmount(); col++){
                //Finds the first non-zero value. If it is not 1.0, return false. Otherwise see if all other values in the pivot column are 0.
                if(getValue(row, col) != 0){
                    if(getValue(row, col) != 1.0){
                        return false;
                    }

                    //Makes sure this pivot is not behind others
                    if(col<latestPivot){
                        return false;
                    }

                    latestPivot = col;

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
        while(!inRref()){
            int foundPivots = 0;
            //the two for loops interate over the matrix, it first iterates over the column to find the pivot
            for(int col=0; col<getColumnAmount(); col++){
                boolean pivotFound = false;

                //Iterates over rows under those that already have pivots
                for(int row=foundPivots; row<getRowAmount(); row++){
                    //Swaps the current row with every row below it until it is non-zero
                    //When it is non zero it stops searching and breaks the loop
                    if(getValue(row, col)!= 0.0){
                        rowInterchange(row, foundPivots);
                        pivotFound = true;
                        foundPivots++;
                        break;
                    }
                }

                //If there is no pivot, we end this iteration as we cannot reduce the rows below
                if(!pivotFound){
                    continue;
                }

                //If there is a pivot, it will reduce all the values below the pivot to zero
                //First we save the index of the row that contains the pivot
                int pivotrow = foundPivots-1;

                //We scale the pivot row so that it pivot is equal to 1
                rowScale(pivotrow, 1/getValue(pivotrow, col));

                for(int row=0; row<getRowAmount();row++){
                    if(getValue(row, col)!=0 && row!=pivotrow){
                        //Make all other values in the pivot column 0
                        double factor = -(getValue(row, col)/getValue(pivotrow, col));
                        rowReplace(row, pivotrow, factor);
                    }
                }
            }
        }     
    }

    public boolean isSquare(){
        return getRowAmount()==getColumnAmount();
    }

    public double determinant(){
        //Matrices must be square to take the determinant
        if(!isSquare()){
            throw new IllegalArgumentException("Matrix must be square");
        }else{
            //Base Case: If the matrix is 1x1, return the singular value
            if(getSize()==1){
                return getValue(0, 0);
            //Other Case: Go across the first row of the matrix and multiply each value by the determinant of the sub matrix and alternatingly add and subtract
            }else{
                //Where the sum of the values multiplied by submatrices will be stored
                double finalSum = 0;

                //Iterate through each value in the top row once
                for(int topRowIndex=0; topRowIndex<getColumnAmount();topRowIndex++){
                    //Create a submatrix that is size (n-1)x(n-1)
                    Matrix subMatrix = new Matrix(getColumnAmount()-1, getRowAmount()-1);

                    //Go across the matrix starting at the second row, because the first row will never be part of the submatrix
                    for(int row=1; row<getRowAmount(); row++){
                        //Keeps track of the next column index to be filled within the submatrix
                        //We can't just use col as the matrix has larger dimensions than the submatrix
                        int colCount = 0;

                        for(int col=0; col<getColumnAmount(); col++){
                            //If the value is in the same column as the current top row value, skip it.
                            if(col!=topRowIndex){
                                //1 is subtracted from row because the iteration over the rows of the matrix starts at 1 while iteration over the rows of the submatrix must go over 0
                                subMatrix.setValue(row-1, colCount, getValue(row, col));
                                colCount++;
                            }
                        }
                    }

                    //If the value of topRowIndex is odd, add to finalSum, if even, subtract.
                    if((topRowIndex+1)%2==1){
                        finalSum += getValue(0, topRowIndex) * subMatrix.determinant();
                    }else{
                        finalSum -= getValue(0, topRowIndex) * subMatrix.determinant();
                    }
                }

                return finalSum;
            }
        }
    }

    public boolean isInvertible(){
        return determinant()!=0;
    }

    public void invert(){
        if(!isInvertible()){
            throw new IllegalArgumentException("Matrix must be invertible");
        }else{
            Matrix identity = identityMatrix(getColumnAmount());

            while(!inRref()){
                int foundPivots = 0;
                //the two for loops interate over the matrix, it first iterates over the column to find the pivot
                for(int col=0; col<getColumnAmount(); col++){
                    boolean pivotFound = false;
    
                    //Iterates over rows under those that already have pivots
                    for(int row=foundPivots; row<getRowAmount(); row++){
                        //Swaps the current row with every row below it until it is non-zero
                        //When it is non zero it stops searching and breaks the loop
                        if(getValue(row, col)!= 0.0){
                            rowInterchange(row, foundPivots);
                            identity.rowInterchange(row, foundPivots);
                            pivotFound = true;
                            foundPivots++;
                            break;
                        }
                    }
    
                    //If there is no pivot, we end this iteration as we cannot reduce the rows below
                    if(!pivotFound){
                        continue;
                    }
    
                    //If there is a pivot, it will reduce all the values below the pivot to zero
                    //First we save the index of the row that contains the pivot
                    int pivotrow = foundPivots-1;
    
                    //We scale the pivot row so that it pivot is equal to 1
                    identity.rowScale(pivotrow, 1/getValue(pivotrow, col));
                    rowScale(pivotrow, 1/getValue(pivotrow, col));
    
                    for(int row=0; row<getRowAmount();row++){
                        if(getValue(row, col)!=0 && row!=pivotrow){
                            //Make all other values in the pivot column 0
                            double factor = -(getValue(row, col)/getValue(pivotrow, col));
                            rowReplace(row, pivotrow, factor);
                            identity.rowReplace(row, pivotrow, factor);
                        }
                    }
                }
            }   

            this.struct = identity.struct;
        }
    }

    public void multiply(Matrix other){
        if(getColumnAmount() != other.getRowAmount()){
            throw new IllegalArgumentException("Must have valid dimensions");
        }

        double[][] tempStruct = new double[getRowAmount()][other.getColumnAmount()];

        for(int i=0; i<getRowAmount(); i++){
            double[] row = getRow(i);
            for(int j=0; j<other.getColumnAmount(); j++){
                Vector column = other.getColumn(j); 
                double sum = 0;
                for(int value=0; value<row.length; value++){
                    sum += row[value] * column.getValue(value);
                }

                tempStruct[i][j] = sum;
            }
        }

        this.struct = tempStruct;
    }

    public boolean isLinearlyIndependent(){
        Matrix tempMatrix = new Matrix(struct);

        if(tempMatrix.getColumnAmount()>tempMatrix.getRowAmount()){
            return false;
        }

        tempMatrix.rref();
        for(int diagonal=0; diagonal<tempMatrix.getColumnAmount(); diagonal++){
            if(tempMatrix.getValue(diagonal, diagonal) != 1.0){
                return false;
            }
        }

        return true;
    }

    //TODO:Check this
    public boolean isBasis(){
        if(isLinearlyIndependent() && getColumnAmount()==getRowAmount()){
            return true;
        }

        return false;
    }

    public Vector getAnswer(){
        Vector[] checkVectors = new Vector[getColumnAmount()-1];
        for (int col=0; col<getColumnAmount()-1; col++) {
            checkVectors[col] = getColumn(col);
        }
        Matrix checkMatrix = new Matrix(checkVectors);

        if(!checkMatrix.isInvertible()){
            throw new IllegalArgumentException("Matrix must be invertible");
        }

        Matrix tempMatrix = new Matrix(struct);
        tempMatrix.rref();
        return tempMatrix.getColumn(getColumnAmount()-1);
    }

    public Vector getAnswer(Vector b){
        if(!isInvertible()){
            throw new IllegalArgumentException("Matrix must be invertible");
        }

        Matrix tempMatrix = new Matrix(struct);
        tempMatrix.invert();
        tempMatrix.multiply(b);

        Vector output = tempMatrix.getColumn(0);

        return output;
    }
           


}