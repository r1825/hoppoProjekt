package r1825.syoribu.entity.tama;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import r1825.syoribu.ImageObject;
import r1825.syoribu.Main;
import r1825.syoribu.Vector2;
import r1825.syoribu.entity.enemy.EntityEnemyBase;

public class EntityTamaSelfDefence extends EntityTamaBase {

    public EntityTamaSelfDefence(Image image, Pane pane, double x, double y, Vector2 move) {
        super(image, pane, x, y, move);
    }

    @Override
    public boolean update ( ) {
        if ( Main.game.listEnemyTama.size() == 0 ) return false;

        EntityTamaBase entityTamaBase = Main.game.listEnemyTama.get(0);
        double tmp = ImageObject.dist2(entityTamaBase, this);

        boolean isDead = false;

        var iterator = Main.game.listEnemyTama.iterator();
        while ( iterator.hasNext() ) {
            var i = iterator.next();
            if ( ImageObject.dist(i, this) < tmp ) {
                tmp = ImageObject.dist2(i, this);
                entityTamaBase = i;
                if ( tmp < 32 ) {
                    isDead = true;
                    pane.getChildren().remove(i.imageView);
                    iterator.remove();
                }
            }
        }

        double x = entityTamaBase.getCentreX()-this.getCentreX();
        double y = entityTamaBase.getCentreY()-this.getCentreY();
        double length = Math.sqrt(x*x + y*y);

        this.vectorMove = new Vector2(x/length * 16, y/length * 16);
        this.move(vectorMove);
        return isDead;
    }

    @Override
    public boolean canGoThrough () {
        return true;
    }

    @Override
    public int getDamage () {
        return 0;
    }
}
