package r1825.syoribu.entity;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.Vector2;

public class EntitySelf extends EntityLiving {

    private int damageCoolDown = 20;

    public EntitySelf(Image image, Pane pane, double x, double y, Vector2 vec, int life) {
        super(image, pane, x, y, vec, life);
    }

    @Override
    public boolean update () {
        System.out.println(damageCoolDown);
        this.damageCoolDown--;
        return false;
    }

    @Override
    public int damage ( int amount ) {

        if ( amount != 0 && damageCoolDown < 0 ) {
            damageCoolDown = 10;
            this.lifePoint -= amount;
        }
        return this.lifePoint;
    }
}
