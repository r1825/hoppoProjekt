package r1825.syoribu.entity.enemy;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.ImageObject;

public class EntityEnemyBase extends ImageObject {

    public Image tama;
    public int time;

    EntityEnemyBase (Image image, Pane pane, double x, double y, Image tama ) {
        super(image, pane, x, y);
        this.tama = tama;
        time = 0;
    }

    public void update ( ) {

    }
}
