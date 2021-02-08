package r1825.syoribu.entity.enemy;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.Main;
import r1825.syoribu.Vector2;
import r1825.syoribu.entity.tama.EntityTamaEnemyDivide;

import java.security.SecureRandom;
import java.util.Random;

public class EntityEnemyBoss extends EntityEnemyBase {

    private final Vector2[] vec1 = { new Vector2(0, 4), new Vector2(4, 0), new Vector2(-4, 0), new Vector2(0, -4),
            new Vector2(4, 4), new Vector2(4, -4), new Vector2(-4, 4), new Vector2(-4, -4)};
    private final Vector2[] vec2 = { new Vector2(1, 3), new Vector2(3, 1), new Vector2(-3, 1), new Vector2(1, -3),
            new Vector2(3, 5), new Vector2(3, -5), new Vector2(-3, 5), new Vector2(-3, -5)};
    private final Vector2[] vec3 = { new Vector2(1, 3), new Vector2(3, 1), new Vector2(-3, 1), new Vector2(1, -3),
            new Vector2(5, 3), new Vector2(5, -3), new Vector2(-5, 3), new Vector2(-5, -3)};
    Random rnd = new SecureRandom();

    public EntityEnemyBoss (Image image, Pane pane, double x, double y, Image tama, Vector2 move ) {
        super(image, pane, x, y, tama, move, 50, 10000);
        time = 0;
    }

    @Override
    public boolean update ( ) {
        time++;
        if ( time % 10 == 0 ) {
            this.move(vectorMove);
        }
        if ( time % 80 == 0 ) {
            int type = rnd.nextInt(3);
            if ( type == 0 ) {
                for ( int i = 0; i < 8; i++ ) {
                    Main.game.listEnemyTamaAdd.add(new EntityTamaEnemyDivide(this.tama, this.pane, this.getX() + (this.imgW / 3), this.getCentreY(), vec1[i]));
                }
            }
            if ( type == 1 ) {
                for ( int i = 0; i < 8; i++ ) {
                    Main.game.listEnemyTamaAdd.add(new EntityTamaEnemyDivide(this.tama, this.pane, this.getX() + (this.imgW / 3), this.getCentreY(), vec2[i]));
                }
            }
            if ( type == 2 ) {
                for ( int i = 0; i < 8; i++ ) {
                    Main.game.listEnemyTamaAdd.add(new EntityTamaEnemyDivide(this.tama, this.pane, this.getX() + (this.imgW / 3), this.getCentreY(), vec3[i]));
                }
            }
        }
        return false;
    }

    @Override
    public int damage ( int amount ) {
        lifePoint -= 1;
        return lifePoint;
    }
}
