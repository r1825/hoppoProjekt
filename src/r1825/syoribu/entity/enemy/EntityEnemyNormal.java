package r1825.syoribu.entity.enemy;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.Main;
import r1825.syoribu.entity.tama.EntityTamaEnemyNormal;

public class EntityEnemyNormal extends EntityEnemyBase {

    public EntityEnemyNormal (Image image, Pane pane, double x, double y, Image tama ) {
        super(image, pane, x, y, tama);
        time = 0;
    }

    @Override
    public void update ( ) {
        this.setY(this.getY()+5);
        this.time++;
        if ( this.time >= 40 ) {
            this.time = 0;
            Main.game.listEnemyTama.add(new EntityTamaEnemyNormal(this.tama, this.pane, this.getX(), this.getY()));
        }
    }
}
