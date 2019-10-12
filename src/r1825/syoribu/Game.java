package r1825.syoribu;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import r1825.syoribu.entity.EntityPlayer;
import r1825.syoribu.entity.enemy.EntityEnemyBase;
import r1825.syoribu.entity.enemy.EntityEnemyDivide;
import r1825.syoribu.entity.enemy.EntityEnemyLaser;
import r1825.syoribu.entity.enemy.EntityEnemyNormal;
import r1825.syoribu.entity.item.EntityItem;
import r1825.syoribu.entity.item.EntityItemEquipment;
import r1825.syoribu.entity.item.EntityItemRepair;
import r1825.syoribu.entity.tama.EntityTamaBase;
import r1825.syoribu.entity.tama.EntityTamaEnemyNormal;
import r1825.syoribu.entity.tama.EntityTamaSelf;
import r1825.syoribu.entity.tama.EntityTamaSelfSearch;

import java.awt.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    public static final int HEIGHT = 720;
    public static final int WIDTH = 1280;
    public static final int SCORE_WIDTH = 256;
    public static final int GAME_WIDTH = WIDTH - SCORE_WIDTH;

    public static int minEnemyNum = 3;

    SecureRandom rnd = new SecureRandom();

    Pane root = new Pane();

    public EntityPlayer player;
    ImageView background = new ImageView();

    public Image imageTamaSelf = new Image("r1825/syoribu/img/tama1.png");
    public Image imageTamaSelfNaname = new Image("r1825/syoribu/img/enemy.png");
    public Image imageTamaSelfSearch = new Image("r1825/syoribu/img/enemy3.png");
    public Image imageEnemy = new Image("r1825/syoribu/img/koba2.png");
    public Image imageEnemyLaser = new Image("r1825/syoribu/img/koba3.png");
    public Image imageEnemyDivide = new Image("r1825/syoribu/img/koba4.png");
    public Image imageTamaEnemy = new Image("r1825/syoribu/img/tama2.png");
    public Image imageTamaEnemyNaname = new Image("r1825/syoribu/img/enemy2.png");
    public Image imageTamaEnemyLaser = new Image("r1825/syoribu/img/tama4.png");

    public Image imageItemPowerup = new Image("r1825/syoribu/img/power.png");
    public Image imageItemRepair = new Image("r1825/syoribu/img/repair.png");


    public List<EntityEnemyBase> listEnemy = new ArrayList<>();
    List<EntityTamaBase> listEnemyTama = new ArrayList<>();
    public List<EntityTamaBase> listEnemyTamaAdd = new ArrayList<>();
    List<EntityItem> listItem = new ArrayList<>();

    BigInteger score = BigInteger.ZERO;

    AnimationTimer animationTimer;

    public void begin ( Stage stage ) {

        Scene scene = new Scene( root, WIDTH, HEIGHT );

        stage.setTitle("北方Projekt");
        stage.setScene(scene);
        stage.show();

        Image imageSelf = new Image("r1825/syoribu/img/_i_icon_13948_icon_139480_64.png");
        Image imageBackground = new Image("r1825/syoribu/img/backtmp.jpg");
        Image imageScoreBack = new Image("r1825/syoribu/img/scoreBack.jpg");
        background.setImage(imageBackground);

        background.setFitHeight(HEIGHT);
        background.setFitWidth(WIDTH);
        root.getChildren().add(background);

        ImageView scoreBack = new ImageView(imageScoreBack);
        scoreBack.setFitWidth(256);
        scoreBack.setFitHeight(HEIGHT);
        scoreBack.setX(WIDTH-256);
        root.getChildren().add(scoreBack);

        Text textScore = new Text();
        textScore.setText(score.toString());
        textScore.setY(16*2);
        textScore.setX(GAME_WIDTH + 16);
        textScore.setFont(new Font(20));
        textScore.setFill(Color.WHITE);
        root.getChildren().add(textScore);

        Text textLife = new Text();
        textLife.setText(1 + "");
        textLife.setY(16*4);
        textLife.setX(GAME_WIDTH + 16);
        textLife.setFont(new Font(20));
        textLife.setFill(Color.WHITE);
        root.getChildren().add(textLife);

        player = new EntityPlayer(imageSelf, root, 255, 255, new Vector2(0, 0), 3);

        scene.setOnMouseMoved(this::mouseMoved);
        scene.setOnKeyPressed(this::keyPressed);

        animationTimer = new AnimationTimer() {

            @Override
            public void handle(long now) {

                player.update();

                textLife.setText( player.getLife() + String.format("\n####-DEBUG-####\n%d:%d\n%d:%d\n%d:%d\n%d\n##############", player.intervalNormalTama, player.cntNormalTama, player.intervalNanameTama, player.cntNanameTama, player.intervalSearchTama, player.cntSearchTama, minEnemyNum));
                textScore.setText(score.toString());

                if ( rnd.nextInt(1000) < 1 ) minEnemyNum++;
                popItem();

                //新しい敵の出現
                while ( listEnemy.size() < minEnemyNum ) {
                    popEnemy();
                }

                // 自機の弾の移動
                {
                    var iteratorMyTama = player.listTama.iterator();
                    while (iteratorMyTama.hasNext()) {
                        var i = iteratorMyTama.next();
                        boolean flg = i.update();
                        if (flg || i.isOutside()) {
                            iteratorMyTama.remove();
                            i.setY(-70);
                        }
                    }
                }

                // 敵の移動
                {
                    var iteratorEnemy = listEnemy.iterator();
                    while ( iteratorEnemy.hasNext() ) {
                        var i = iteratorEnemy.next();
                        boolean flg = i.update();
                        if ( flg || i.isOutside() ) {
                            i.setX(-70);
                            iteratorEnemy.remove();
                        }
                    }
                }

                // 敵の弾の移動
                {
                    var iteratorEnemyTama = listEnemyTama.iterator();
                    while (iteratorEnemyTama.hasNext()) {
                        var i = iteratorEnemyTama.next();
                        boolean flg = i.update();
                        if (flg || i.isOutside()) {
                            i.setX(-70);
                            iteratorEnemyTama.remove();
                        }
                    }
                }

                // アイテムの移動
                {
                    var iteratorItem = listItem.iterator();
                    while ( iteratorItem.hasNext() ) {
                        var i = iteratorItem.next();
                        i.update();
                        if ( i.isOutside() ) {
                            i.setX(-70);
                            iteratorItem.remove();
                        }
                    }
                }

                // 敵と自機の衝突判定と敵と自弾の衝突判定
                {
                    boolean isPlayerHit = false;
                    var iteratorEnemy = listEnemy.iterator();
                    while (iteratorEnemy.hasNext()) {
                        var i = iteratorEnemy.next();
                        if (ImageObject.dist(i, player) <= 32) {
                            isPlayerHit = true;
                        }

                        var iteratorMyTama = player.listTama.iterator();
                        boolean flg = false;
                        int damage = 0;
                        while (iteratorMyTama.hasNext()) {
                            var j = iteratorMyTama.next();
                            if (ImageObject.dist(i, j) <= 32) {
                                if ( !j.canGoThrough() ) {
                                    j.setY(-70);
                                    iteratorMyTama.remove();
                                }
                                flg = true;
                                damage = Math.max(damage, j.getDamage());
                            }
                        }
                        if (flg) {
                            i.damage(damage);
                            if ( i.isDead() ) {
                                i.setY(-70);
                                iteratorEnemy.remove();
                                score = score.add(new BigInteger("" + i.getScore()));
                            }
                        }
                    }
                    if ( isPlayerHit ) {
                        player.damage(1);
                        if ( player.isDead() ) {
                            gameFinish();
                        }
                    }
                }

                // 敵の弾と自機の衝突判定
                {
                    int damage = 0;
                    var iteratorEnemyTama = listEnemyTama.iterator();
                    while (iteratorEnemyTama.hasNext()) {
                        var i = iteratorEnemyTama.next();
                        if (ImageObject.dist(i, player) <= 32) {
                            damage = Math.max(i.getDamage(), damage);
                            if ( !i.canGoThrough() ) {
                                iteratorEnemyTama.remove();
                                i.setY(-70);
                            }
                        }
                    }
                    player.damage(damage);
                    if ( player.isDead() ) {
                        gameFinish();
                    }
                }

                // アイテムの取得判定
                {
                    var iteratorItem = listItem.iterator();
                    while ( iteratorItem.hasNext() ) {
                        var i = iteratorItem.next();
                        if ( ImageObject.dist(i, player) <= 32 ) {
                            i.effect(player);
                            i.setX(-70);
                            iteratorItem.remove();
                        }
                    }
                }

                listEnemyTama.addAll(listEnemyTamaAdd);
                listEnemyTamaAdd.clear();
            }
        };

        //animationTimer.start();

    }

    public void gameFinish ( ) {
        System.out.println(score.toString());
        animationTimer.stop();
        System.exit(0);
    }

    private void mouseMoved (MouseEvent mouseEvent) {
        player.setY(mouseEvent.getY() - ( player.imgH >> 1 ) );
        if ( mouseEvent.getX() + (player.imgW >> 1) > GAME_WIDTH ) {
            player.setX(GAME_WIDTH - player.imgW );
        }
        else {
            player.setX((mouseEvent.getX() - (player.imgW >> 1)));
        }
    }

    private void popEnemy () {
        int yPos = -64;
        int xPos = rnd.nextInt(WIDTH);
        double xMove = rnd.nextDouble()*2.0;
        if ( ( rnd.nextInt() & 1 ) == 0 ) xMove *= -1;
        listEnemy.add(new EntityEnemyNormal(imageEnemy, root, xPos, yPos, imageTamaEnemy, new Vector2(xMove, 4)));

        if ( rnd.nextInt(100) < 25 ) {
            listEnemy.add(new EntityEnemyDivide(imageEnemyDivide, root, rnd.nextInt(WIDTH), yPos, imageTamaEnemyNaname, new Vector2(0, 5)));
        }
        if ( rnd.nextInt(100) < 10 ) {
            listEnemy.add(new EntityEnemyLaser(imageEnemyLaser, root, rnd.nextInt(WIDTH), yPos, imageTamaEnemyLaser, new Vector2(xMove*1.5, 3)));
        }
    }

    private void popItem () {
        if ( rnd.nextInt(1000) < 10 ) {
            if ( rnd.nextInt(5) == 0 ) {
                listItem.add(new EntityItemRepair(imageItemRepair, root, rnd.nextInt(WIDTH), -65, new Vector2(0, 4)));
            }
            else {
                listItem.add(new EntityItemEquipment(imageItemPowerup, root, rnd.nextInt(WIDTH), -65, new Vector2(0, 4)));
            }
        }
    }

    private void keyPressed (KeyEvent keyEvent){
        if ( keyEvent.getCode() == KeyCode.SPACE ) {
            animationTimer.start();
        }
    }
}
