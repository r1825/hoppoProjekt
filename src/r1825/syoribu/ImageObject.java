package r1825.syoribu;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ImageObject {

    protected Pane pane;
    ImageView imageView = new ImageView();

    public ImageObject (Image image, Pane pane, double x, double y ) {
        imageView.setImage(image);
        imageView.setX(x);
        imageView.setY(y);
        pane.getChildren().add(imageView);
        this.pane = pane;
    }

    public double getX() {
        return imageView.getX();
    }

    public double getY() {
        return imageView.getY();
    }

    public void setX(double x) {
        imageView.setX(x);
    }

    public void setY(double y) {
        imageView.setY(y);
    }

    public static double dist ( ImageObject a, ImageObject b ) {
        double x = a.getX() - b.getX();
        double y = a.getY() - b.getY();
        return Math.sqrt(x*x+y*y);
        //return Math.pow(x*x*x+y*y*y, 1.0/3.0);
    }

    public static double dist ( ImageObject a, ImageView b ) {
        double x = a.getX() - b.getX();
        double y = a.getY() - b.getY();
        return Math.sqrt(x*x+y*y);
        //return Math.pow(x*x*x+y*y*y, 1.0/3.0);
    }
}
