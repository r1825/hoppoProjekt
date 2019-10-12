package r1825.syoribu.entity.item;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.Main;
import r1825.syoribu.Vector2;
import r1825.syoribu.entity.EntityPlayer;
import r1825.syoribu.entity.tama.EntityTamaBase;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class EntityItemRepair extends EntityItem {

    private static Random rnd = new SecureRandom();

    public EntityItemRepair(Image image, Pane pane, double x, double y, Vector2 vec) {
        super(image, pane, x, y, vec);
    }

    @Override
    public void effect (EntityPlayer entityPlayer){
        entityPlayer.damage(-1);
    }
}
