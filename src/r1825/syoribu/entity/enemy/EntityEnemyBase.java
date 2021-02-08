package r1825.syoribu.entity.enemy;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.Vector2;
import r1825.syoribu.entity.EntityLiving;

public class  EntityEnemyBase extends EntityLiving {

    protected Image tama;
    protected int time;
    private final int SCORE;

    EntityEnemyBase (Image image, Pane pane, double x, double y, Image tama, Vector2 move, int life, int score ) {
        super(image, pane, x, y, move, life);
        this.tama = tama;
        time = 0;
        this.SCORE = score;
    }

    public int getScore ( ) {
        return SCORE;
    }
}
