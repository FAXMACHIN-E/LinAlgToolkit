package src.main.java;

public class Vector extends Matrix{
    
    public Vector(int rows){
        super(rows, 1);
    }

    public Vector(double[] v){
        super(v.length, 1);

        for(int i=0; i<getRowAmount(); i++){
            setValue(i, v[i]);
        }
    }

    public double getValue(int row){
        return getValue(row, 0);
    }
    
    public void setValue(int row, double val){
        setValue(row, 0, val);
    }
}
