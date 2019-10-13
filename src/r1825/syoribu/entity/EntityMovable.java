package r1825.syoribu.entity;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.ImageObject;
import r1825.syoribu.Vector2;

public class EntityMovable extends ImageObject {

    protected Vector2 vectorMove;
    public int pos;

    public EntityMovable(Image image, Pane pane, double x, double y, Vector2 vec) {
        super(image, pane, x, y);
        this.vectorMove = vec;
        pos = ((int)x >> 5) + 1000 * ((int)y >> 5);
    }

    public boolean update ( ) {
        this.move(vectorMove);
        return false;
    }

    protected void move ( Vector2 vector2 ) {
        this.setY(this.getY()+vector2.getY());
        this.setX(this.getX()+vector2.getX());
        pos = ((int)getX() >> 5) + 1000 * ((int)getY() >> 5);
    }

}
