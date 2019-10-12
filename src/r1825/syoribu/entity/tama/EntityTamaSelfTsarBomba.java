package r1825.syoribu.entity.tama;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.ImageObject;
import r1825.syoribu.Main;
import r1825.syoribu.Vector2;

public class EntityTamaSelfTsarBomba extends EntityTamaBase {

    public EntityTamaSelfTsarBomba(Image image, Pane pane, double x, double y, Vector2 move) {
        super(image, pane, x, y, move);
    }

    @Override
    public boolean update ( ) {

        if ( Main.game.listEnemyTama.size() == 0 ) return false;

        var iterator = Main.game.listEnemyTama.iterator();
        while ( iterator.hasNext() ) {
            var i = iterator.next();
            if ( ImageObject.dist2(i, this) < 16 ) {
                iterator.remove();
                pane.getChildren().remove(i);
                i.setX(-70);
            }
        }

        this.move(vectorMove);

        return false;
    }

    @Override
    public boolean canGoThrough () {
        return true;
    }

    @Override
    public int getDamage () {
        return 0x3f3f3f3f;
    }
}
