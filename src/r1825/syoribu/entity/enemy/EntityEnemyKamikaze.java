package r1825.syoribu.entity.enemy;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.Main;
import r1825.syoribu.Vector2;
import r1825.syoribu.entity.enemy.EntityEnemyBase;
import r1825.syoribu.entity.tama.EntityTamaEnemyNormal;

public class EntityEnemyKamikaze extends EntityEnemyBase {

    public EntityEnemyKamikaze (Image image, Pane pane, double x, double y, Image tama, Vector2 move ) {
        super(image, pane, x, y, tama, move, 0x3f3f3f3f, 0);
        time = 0;
    }

    @Override
    public boolean update ( ) {
        this.move(vectorMove);
        return false;
    }
}
