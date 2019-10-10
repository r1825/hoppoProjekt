package r1825.syoribu.entity.enemy;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.ImageObject;
import r1825.syoribu.Vector2;

public class EntityEnemyBase extends ImageObject {

    protected Image tama;
    protected int time;
    protected Vector2 move;

    EntityEnemyBase (Image image, Pane pane, double x, double y, Image tama, Vector2 move ) {
        super(image, pane, x, y);
        this.tama = tama;
        time = 0;
        this.move = move;
    }

    public void update ( ) {

    }
}
