package r1825.syoribu.entity;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.ImageObject;
import r1825.syoribu.Vector2;

public class EntityMovable extends ImageObject {

    protected Vector2 move;

    public EntityMovable(Image image, Pane pane, double x, double y, Vector2 vec) {
        super(image, pane, x, y);
        this.move = vec;
    }

    public boolean update ( ) {
        this.setY(this.getY()+move.getY());
        this.setX(this.getX()+move.getX());
        return false;
    }

}
