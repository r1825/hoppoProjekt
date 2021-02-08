package r1825.syoribu.entity.enemy;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.Main;
import r1825.syoribu.Vector2;
import r1825.syoribu.entity.tama.EntityTamaEnemyNormal;

public class EntityEnemyNormal extends EntityEnemyBase {

    public EntityEnemyNormal (Image image, Pane pane, double x, double y, Image tama, Vector2 move ) {
        super(image, pane, x, y, tama, move, 1, 30);
        time = 0;
    }

    @Override
    public boolean update ( ) {
        move(vectorMove);
        this.time++;
        if ( this.time >= 40 ) {
            this.time = 0;
            Main.game.listEnemyTamaAdd.add(new EntityTamaEnemyNormal(this.tama, this.pane, this.getX() + (this.imgW / 3), this.getCentreY(), new Vector2(0, 8)));
        }
        return false;
    }
}
