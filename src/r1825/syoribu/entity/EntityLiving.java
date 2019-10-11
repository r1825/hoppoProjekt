package r1825.syoribu.entity;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.Vector2;

public class EntityLiving extends EntityMovable {

    protected int lifePoint;

    public EntityLiving(Image image, Pane pane, double x, double y, Vector2 vec, int life) {
        super(image, pane, x, y, vec);
        this.lifePoint = life;
    }

    public int damage ( int amount ) {
        lifePoint -= amount;
        return lifePoint;
    }

    public int getLife ( ) {
        return lifePoint;
    }

    public void setLife ( int amount ) {
        lifePoint = amount;
    }

    public boolean isDead ( ) {
        return lifePoint <= 0;
    }
}
