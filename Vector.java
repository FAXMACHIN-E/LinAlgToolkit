public class Vector extends Matrix{
    
    public Vector(int rows){
        super(rows, 1);
    }
    
    public void setValue(int row, double val){
        setValue(row, 0, val);
    }
}
