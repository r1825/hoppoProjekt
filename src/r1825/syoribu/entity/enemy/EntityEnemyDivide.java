package r1825.syoribu.entity.enemy;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.Main;
import r1825.syoribu.Vector2;
import r1825.syoribu.entity.tama.EntityTamaEnemyDivide;

public class EntityEnemyDivide extends EntityEnemyBase {

    public EntityEnemyDivide (Image image, Pane pane, double x, double y, Image tama, Vector2 move) {
        super(image, pane, x, y, tama, move, 1, 50);
        time = 0;
    }

    @Override
    public boolean update ( ) {
        this.move(vectorMove);
        this.time++;
        if ( this.time >= 80 ) {
            this.time = 0;
            Main.game.listEnemyTamaAdd.add(new EntityTamaEnemyDivide(this.tama, this.pane, this.getX() + (this.imgW / 3), this.getCentreY(), new Vector2(0, 8)));
        }
        return false;
    }
}