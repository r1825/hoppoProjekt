package r1825.syoribu.entity.tama;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.ImageObject;
import r1825.syoribu.Main;
import r1825.syoribu.Vector2;
import r1825.syoribu.entity.enemy.EntityEnemyBase;

public class EntityTamaSelfSearch extends EntityTamaBase {

    public EntityTamaSelfSearch(Image image, Pane pane, double x, double y, Vector2 move) {
        super(image, pane, x, y, move);
    }

    @Override
    public boolean update ( ) {
        if ( Main.game.listEnemy.size() == 0 ) return false;

        EntityEnemyBase entityEnemyBase = Main.game.listEnemy.get(0);
        double tmp = ImageObject.dist2(entityEnemyBase, this);
        for ( var i : Main.game.listEnemy ) {
            if ( ImageObject.dist2(i, this) < tmp ) {
                tmp = ImageObject.dist2(i, this);
                entityEnemyBase = i;
            }
        }

        double x = entityEnemyBase.getX()-this.getX();
        double y = entityEnemyBase.getY()-this.getY();
        double length = Math.sqrt(x*x + y*y);

        this.vectorMove = new Vector2(x/length * 8, y/length * 8);
        this.move(vectorMove);
        return false;
    }

    @Override
    public int getDamage ( ) {
        return 1;
    }
}

