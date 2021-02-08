package r1825.syoribu.entity.tama;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.Game;
import r1825.syoribu.ImageObject;
import r1825.syoribu.Main;
import r1825.syoribu.Vector2;

import java.util.Collections;
import java.util.Comparator;

public class EntityTamaSelfTsarBomba extends EntityTamaBase {

    public EntityTamaSelfTsarBomba(Image image, Pane pane, double x, double y, Vector2 move) {
        super(image, pane, x, y, move);
    }

    @Override
    public boolean update ( ) {
        this.move(vectorMove);
        if ( Main.game.listEnemyTama.size() == 0 ) return false;

        {
            Collections.sort(Main.game.listEnemyTama, Comparator.comparingInt(x -> x.pos));
            int x = this.pos % 1000;
            int y = this.pos / 1000;
            for ( int I = 0; I < 9; I++ ) {
                int nx = x + Game.dx[I];
                int ny = y + Game.dy[I];
                int npos = nx + 1000 * ny;

                int pos = Game.lowerBoundTama(Main.game.listEnemyTama, npos);
                if ( pos < 0 || Main.game.listEnemyTama.size()-1 < pos ) continue;
                int damage = 0;
                var iteratorEnemyTama = Main.game.listEnemyTama.listIterator(pos);

                while (iteratorEnemyTama.hasNext()) {
                    var i = iteratorEnemyTama.next();
                    if ( i.pos != npos ) break;
                    if (ImageObject.dist(i, this) <= 16) {
                        damage = Math.max(i.getDamage(), damage);
                        if ( !i.canGoThrough() ) {
                            iteratorEnemyTama.remove();
                            pane.getChildren().remove(i.imageView);
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean canGoThrough () {
        return true;
    }

    @Override
    public int getDamage () {
        return 0x3f3f;
    }
}
