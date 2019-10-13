package r1825.syoribu.entity.item;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.ImageObject;
import r1825.syoribu.Main;
import r1825.syoribu.Vector2;
import r1825.syoribu.entity.EntityPlayer;
import r1825.syoribu.entity.enemy.EntityEnemyBase;

public class EntityItemTsarBomba extends EntityItem {

    public EntityItemTsarBomba(Image image, Pane pane, double x, double y, Vector2 vec) {
        super(image, pane, x, y, vec);
    }

    @Override
    public void effect (EntityPlayer entityPlayer) {
        entityPlayer.tsar_bomba++;
        /*
        Main.game.showAnime(4000, Main.game.imageTsarGif);

        Main.game.listEnemyTama.forEach(this::kill);
        Main.game.listEnemy.forEach(this::kill);
        Main.game.listEnemyTamaAdd.forEach(this::kill);
        Main.game.player.listTama.forEach(this::kill);
        Main.game.listEnemyTama.clear();
        Main.game.listEnemy.clear();
        Main.game.listEnemyTamaAdd.clear();
        Main.game.player.listTama.clear();
         */
    }

    private void kill ( ImageObject object ) {
        if ( object instanceof EntityEnemyBase ) {
            EntityEnemyBase entityEnemyBase = (EntityEnemyBase) object;
            Main.game.score += entityEnemyBase.getScore();
        }
        else {
            Main.game.score++;
        }
        object.setX(-70);
    }
}
