package r1825.syoribu.entity;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import r1825.syoribu.Main;
import r1825.syoribu.Vector2;
import r1825.syoribu.entity.tama.EntityTamaBase;
import r1825.syoribu.entity.tama.EntityTamaSelf;
import r1825.syoribu.entity.tama.EntityTamaSelfDefence;
import r1825.syoribu.entity.tama.EntityTamaSelfSearch;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class EntityPlayer extends EntityLiving {

    private final Vector2[] vec = { new Vector2(4, 0), new Vector2(-4, 0), new Vector2(0, -4),
            new Vector2(4, 4), new Vector2(4, -4), new Vector2(-4, 4), new Vector2(-4, -4)};

    private int damageCoolDown = 40;

    public int cntNormalTama = 20;
    public int cntNanameTama = 20;
    public int cntSearchTama = 0x3f3f3f3f;

    public int intervalNormalTama = 20;
    public int intervalNanameTama = 20;
    public int intervalSearchTama = 100;

    public int tsar_bomba = 1;

    private int numNaname = 0;

    public List<EntityTamaBase> listTama = new ArrayList<>();

    public EntityPlayer(Image image, Pane pane, double x, double y, Vector2 vec, int life) {
        super(image, pane, x, y, vec, life);
    }

    @Override
    public boolean update () {
        this.damageCoolDown--;
        this.launch();
        return false;
    }

    public boolean addNaname () {
        if ( numNaname < 7 ) {
            numNaname++;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean decreaseIntervalNormal () {
        if ( intervalNormalTama > 3 ) {
            intervalNormalTama--;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean decreaseIntervalNaname () {
        if ( intervalNanameTama > 3 ) {
            intervalNanameTama--;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean decreaseIntervalSearch () {
        cntSearchTama = 5;
        if ( intervalSearchTama > 10 ) {
            intervalSearchTama -= 5;
            return true;
        }
        else {
            return false;
        }
    }

    private void launch () {
        cntNormalTama--;
        cntNanameTama--;
        cntSearchTama--;
        if ( cntNormalTama < 0 ) {
            cntNormalTama = intervalNormalTama;
            listTama.add(new EntityTamaSelf(Main.game.imageTamaSelf, this.pane, this.getX() + 24, this.getY() - 8, new Vector2(0, -8)));
        }
        if ( cntNanameTama < 0 ) {
            cntNanameTama = intervalNanameTama;
            for ( int i = 0; i < numNaname; i++ ) {
                listTama.add(new EntityTamaSelf(Main.game.imageTamaSelfNaname, this.pane, this.getX() + 24, this.getY() - 8, vec[i]));
            }
        }
        if ( cntSearchTama < 0 ) {
            cntSearchTama = intervalSearchTama;
            listTama.add(new EntityTamaSelfSearch(Main.game.imageTamaSelfSearch, pane, this.getX() + 24, this.getY() - 8, new Vector2(-4, -8)));
            listTama.add(new EntityTamaSelfDefence(Main.game.imageTamaSelfDefence, pane, this.getX() + 24, this.getY() - 8, new Vector2(-4, 8)));
        }
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
