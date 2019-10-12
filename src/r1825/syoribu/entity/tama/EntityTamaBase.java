package r1825.syoribu.entity.tama;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.ImageObject;
import r1825.syoribu.Vector2;
import r1825.syoribu.entity.EntityMovable;

public class EntityTamaBase extends EntityMovable {

    public EntityTamaBase(Image image, Pane pane, double x, double y, Vector2 move ) {
        super(image, pane, x, y, move);
        this.move = move;
    }

    public int getDamage ( ) {
        return 0;
    }

    public boolean canGoThrough () {
        return false;
    }
}
