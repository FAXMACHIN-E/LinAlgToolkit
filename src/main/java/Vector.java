package src.main.java;

public class Vector extends Matrix{
    
    public Vector(int rows){
        super(rows, 1);
    }

    //TODO: Make a constructor that can just take a 1D double array
    // public Vector(double[] v){

    // }

    public double getValue(int row){
        return getValue(row, 0);
    }
    
    public void setValue(int row, double val){
        setValue(row, 0, val);
    }
}
