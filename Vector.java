public class Vector extends Matrix{
    
    public Vector(int rows){
        super(rows, 1);
    }

    public double getValue(int row){
        return getValue(row, 0);
    }
    
    public void setValue(int row, double val){
        setValue(row, 0, val);
    }
}
