package r1825.syoribu.entity.tama;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.Vector2;

public class EntityTamaEnemyNormal extends EntityTamaBase {

    public EntityTamaEnemyNormal(Image image, Pane pane, double x, double y, Vector2 move) {
        super(image, pane, x, y, move);
    }

    @Override
    public int getDamage ( ) {
        return 1;
    }
}
