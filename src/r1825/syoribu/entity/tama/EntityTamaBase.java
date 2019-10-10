package r1825.syoribu.entity.tama;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.ImageObject;
import r1825.syoribu.Vector2;

public class EntityTamaBase extends ImageObject {

    protected Vector2 move;

    public EntityTamaBase(Image image, Pane pane, double x, double y, Vector2 move ) {
        super(image, pane, x, y);
        this.move = move;
    }

    public boolean update ( ) {
        return false;
    }
}
