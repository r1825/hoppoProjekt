package r1825.syoribu.entity.tama;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class EntityTamaSelf extends EntityTamaBase {

    public EntityTamaSelf(Image image, Pane pane, double x, double y) {
        super(image, pane, x, y);
    }

    @Override
    public void update ( ) {
        this.setY(this.getY()-8);
    }
}
