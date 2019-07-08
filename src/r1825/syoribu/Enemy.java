package r1825.syoribu;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Enemy extends ImageObject {

    Image tama;

    Enemy (Image image, Pane pane, double x, double y, Image tama ) {
        super(image, pane, x, y);
        this.tama = tama;
    }
}
