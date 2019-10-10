package r1825.syoribu.entity.tama;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.Main;
import r1825.syoribu.Vector2;

public class EntityTamaEnemyDivide extends EntityTamaBase {

    private final Vector2[] vec = { new Vector2(0, 4), new Vector2(4, 0), new Vector2(-4, 0), new Vector2(0, -4),
            new Vector2(4, 4), new Vector2(4, -4), new Vector2(-4, 4), new Vector2(-4, -4)};

    private int time = 0;

    public EntityTamaEnemyDivide(Image image, Pane pane, double x, double y, Vector2 move) {
        super(image, pane, x, y, move);
    }

    @Override
    public boolean update ( ) {
        time++;
        if ( time >= 20 ) {
            for ( int i = 0; i < 8; i++ ) {
                Main.game.listEnemyTamaAdd.add(new EntityTamaEnemyNormal(Main.game.imageTamaEnemyNaname, this.pane, this.getCentreX(), this.getCentreY(), vec[i]));
            }
            return true;
        }
        this.setY(this.getY()+move.getY());
        this.setX(this.getX()+move.getX());
        return false;
    }
}