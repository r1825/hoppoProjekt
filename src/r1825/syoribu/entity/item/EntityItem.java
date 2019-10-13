package r1825.syoribu.entity.item;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.Vector2;
import r1825.syoribu.entity.EntityMovable;
import r1825.syoribu.entity.EntityPlayer;

public class EntityItem extends EntityMovable {

    public EntityItem(Image image, Pane pane, double x, double y, Vector2 vec) {
        super(image, pane, x, y, vec);
    }

    public void effect (EntityPlayer entityPlayer){
    }
}
