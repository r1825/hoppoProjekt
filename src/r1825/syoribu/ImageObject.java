package r1825.syoribu;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ImageObject {

    protected Pane pane;
    public ImageView imageView = new ImageView();
    protected final int imgH, imgW;

    public ImageObject (Image image, Pane pane, double x, double y ) {
        imageView.setImage(image);
        imageView.setX(x);
        imageView.setY(y);
        pane.getChildren().add(imageView);
        this.pane = pane;
        imgH = (int)image.getHeight();
        imgW = (int)image.getWidth();
    }

    public boolean isOutside ( ) {
        if ( this.getX() <= -this.imgW  ) return true;
        if ( this.getX() >= Game.GAME_WIDTH-this.imgW ) return true;
        if ( this.getY() <= -100  ) return true;
        if ( this.getY() >= Game.HEIGHT ) return true;
        return false;
    }

    public double getX() {
        return imageView.getX();
    }

    public double getY() {
        return imageView.getY();
    }

    public double getCentreX () {
        return this.getX() + ( this.imgW >> 1 );
    }

    public double getCentreY () {
        return this.getY() + ( this.imgH >> 1 );
    }

    public void setX(double x) {
        imageView.setX(x);
    }

    public void setY(double y) {
        imageView.setY(y);
    }

    public static double dist ( ImageObject a, ImageObject b ) {
        double x = Math.abs( a.getCentreX() - b.getCentreX() );
        double y = Math.abs( a.getCentreY() - b.getCentreY() );
        return Math.pow(x*x*x+y*y*y, 1.0/3.0);
    }

    public static double dist2 ( ImageObject a, ImageObject b ) {
        double x = Math.abs( a.getCentreX() - b.getCentreX() );
        double y = Math.abs( a.getCentreY() - b.getCentreY() );
        return Math.sqrt(x*x+y*y);
    }

    public static boolean isTouching ( ImageObject a, ImageObject b ) {
        double d = dist(a, b);
        double x = (a.imgW + b.imgW) / 4.0;
        double y = (a.imgH + b.imgH) / 4.0;
        double D = Math.pow(x * x * x + y * y * y, 0.333333333333333333333333333333333);
        return ( d <= D );
    }
}
