package r1825.syoribu;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ImageObject {

    ImageView imageView = new ImageView();

    ImageObject (Image image, Pane pane, double x, double y ) {
        imageView.setImage(image);
        imageView.setX(x);
        imageView.setY(y);
        pane.getChildren().add(imageView);
    }

    double getX() {
        return imageView.getX();
    }

    double getY() {
        return imageView.getY();
    }

    void setX(double x) {
        imageView.setX(x);
    }

    void setY(double y) {
        imageView.setY(y);
    }

    public static double dist ( ImageObject a, ImageObject b ) {
        double x = a.getX() - b.getX();
        double y = a.getY() - b.getY();
        return Math.sqrt(x*x+y*y);
    }

    public static double dist ( ImageObject a, ImageView b ) {
        double x = a.getX() - b.getX();
        double y = a.getY() - b.getY();
        return Math.sqrt(x*x+y*y);
    }
}
