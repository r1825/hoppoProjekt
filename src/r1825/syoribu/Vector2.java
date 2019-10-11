package r1825.syoribu;

public class Vector2 {

    private double x, y;
    public Vector2 ( double x, double y ) {
        this.x = x;
        this.y = y;
    }

    public double getX ( ) {
        return x;
    }

    public double getY ( ) {
        return y;
    }

    public double getLength ( ) {
        return Math.sqrt ( x*x + y*y );
    }
}
