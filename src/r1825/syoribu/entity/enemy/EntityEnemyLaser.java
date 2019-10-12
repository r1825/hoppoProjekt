package r1825.syoribu.entity.enemy;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.Main;
import r1825.syoribu.Vector2;
import r1825.syoribu.entity.tama.EntityTamaEnemyNormal;

public class EntityEnemyLaser extends EntityEnemyBase {

    public EntityEnemyLaser (Image image, Pane pane, double x, double y, Image tama, Vector2 move ) {
        super(image, pane, x, y, tama, move, 1, 30);
        time = 0;
    }

    @Override
    public boolean update ( ) {
        this.setY(this.getY()+move.getY());
        this.setX(this.getX()+move.getX());
        Main.game.listEnemyTamaAdd.add(new EntityTamaEnemyNormal(this.tama, this.pane, this.getX() + (this.imgW / 3), this.getY(), new Vector2(0, 8)));
        return false;
    }
}
