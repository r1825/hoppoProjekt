package r1825.syoribu.entity.item;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.Vector2;
import r1825.syoribu.entity.EntityPlayer;

import java.security.SecureRandom;
import java.util.Random;

public class EntityItemEquipment extends EntityItem {

    private static Random rnd = new SecureRandom();

    public EntityItemEquipment(Image image, Pane pane, double x, double y, Vector2 vec) {
        super(image, pane, x, y, vec);
    }

    @Override
    public void effect (EntityPlayer entityPlayer){
        int type = rnd.nextInt(10);
        if ( type < 4 ) {
            entityPlayer.decreaseIntervalNormal();
        }
        else if ( type < 8 ) {
            if ( !entityPlayer.addNaname() ) {
                entityPlayer.decreaseIntervalNaname();
            }
        }
        else {
            entityPlayer.decreaseIntervalSearch();
        }
    }
}
