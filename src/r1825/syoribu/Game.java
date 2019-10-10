package r1825.syoribu;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import r1825.syoribu.entity.enemy.EntityEnemyBase;
import r1825.syoribu.entity.enemy.EntityEnemyDivide;
import r1825.syoribu.entity.enemy.EntityEnemyLaser;
import r1825.syoribu.entity.enemy.EntityEnemyNormal;
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

    SecureRandom rnd = new SecureRandom();

    Pane root = new Pane();

    public ImageView self = new ImageView();
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

    List<EntityTamaBase> listMyTama = new ArrayList<>();
    public List<EntityEnemyBase> listEnemy = new ArrayList<>();
    List<EntityTamaBase> listEnemyTama = new ArrayList<>();
    public List<EntityTamaBase> listEnemyTamaAdd = new ArrayList<>();

    int selfTamaCoolTCnt = 0;
    int enemyPopupTCnt = 0;

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
        self.setImage(imageSelf);
        background.setImage(imageBackground);

        background.setFitHeight(HEIGHT);
        background.setFitWidth(WIDTH);
        root.getChildren().add(background);
        root.getChildren().add(self);

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

        scene.setOnMouseMoved(event -> mouseMoved(event));
        scene.setOnKeyPressed(event -> keyPressed(event));

        animationTimer = new AnimationTimer() {

            @Override
            public void handle(long now) {

                //新しい敵の出現
                if ( enemyPopupTCnt <= 0 ) {
                    int yPos = -64;
                    int xPos = rnd.nextInt(WIDTH);
                    double xMove = rnd.nextDouble()*2.0;
                    if ( ( rnd.nextInt() & 1 ) == 0 ) xMove *= -1;
                    listEnemy.add(new EntityEnemyNormal(imageEnemy, root, xPos, yPos, imageTamaEnemy, new Vector2(xMove, 4)));
                    enemyPopupTCnt = 10;
                    if ( rnd.nextInt(100) < 25 ) {
                        listEnemy.add(new EntityEnemyDivide(imageEnemyDivide, root, rnd.nextInt(WIDTH), yPos, imageTamaEnemyNaname, new Vector2(0, 5)));
                    }
                    if ( rnd.nextInt(100) < 10 ) {
                        listEnemy.add(new EntityEnemyLaser(imageEnemyLaser, root, rnd.nextInt(WIDTH), yPos, imageTamaEnemyLaser, new Vector2(xMove*1.5, 3)));
                    }
                }
                else {
                    while ( listEnemy.size() < 10 ) {
                        int yPos = -64;
                        int xPos = rnd.nextInt(WIDTH);
                        double xMove = rnd.nextDouble()*2.0;
                        if ( ( rnd.nextInt() & 1 ) == 0 ) xMove *= -1;
                        listEnemy.add(new EntityEnemyNormal(imageEnemy, root, xPos, yPos, imageTamaEnemy, new Vector2(xMove, 4)));
                        enemyPopupTCnt = 10;
                        if ( rnd.nextInt(100) < 25 ) {
                            listEnemy.add(new EntityEnemyDivide(imageEnemyDivide, root, rnd.nextInt(WIDTH), yPos, imageTamaEnemyNaname, new Vector2(0, 5)));
                        }
                        if ( rnd.nextInt(100) < 10 ) {
                            listEnemy.add(new EntityEnemyLaser(imageEnemyLaser, root, rnd.nextInt(WIDTH), yPos, imageTamaEnemyLaser, new Vector2(xMove*1.5, 3)));
                        }
                    }
                    enemyPopupTCnt--;
                }

                // 自機の弾のクールダウンカウントを減らす
                if ( selfTamaCoolTCnt > 0 ) selfTamaCoolTCnt--;
                // 自機の弾の移動
                var iteratorMyTama = listMyTama.iterator();
                while ( iteratorMyTama.hasNext() ) {
                    var i = iteratorMyTama.next();
                    boolean flg = i.update();
                    if ( flg || i.isOutside() ) {
                        iteratorMyTama.remove();
                        i.setY(-70);
                    }
                }

                // 敵の移動
                {
                    var iteratorEnemy = listEnemy.iterator();
                    while ( iteratorEnemy.hasNext() ) {
                        var i = iteratorEnemy.next();
                        i.update();
                        if ( i.isOutside() ) {
                            i.setX(-70);
                            iteratorEnemy.remove();
                        }
                    }
                }

                // 敵の弾の移動
                var iteratorEnemyTama = listEnemyTama.iterator();
                while ( iteratorEnemyTama.hasNext() ) {
                    var i = iteratorEnemyTama.next();
                    boolean flg = i.update();
                    if ( flg || i.isOutside() ) {
                        i.setX(-70);
                        iteratorEnemyTama.remove();
                    }
                }

                // 衝突判定
                var iteratorEnemy = listEnemy.iterator();
                while ( iteratorEnemy.hasNext() ) {
                    var i = iteratorEnemy.next();
                    if ( ImageObject.dist(i, self) <= 32 ) {
                        gameFinish();
                    }
                    iteratorMyTama = listMyTama.iterator();
                    boolean flg = false;
                    while ( iteratorMyTama.hasNext() ) {
                        var j = iteratorMyTama.next();
                        if ( ImageObject.dist(i, j) <= 32 ) {
                            j.setY(-70);
                            iteratorMyTama.remove();
                            flg = true;
                        }
                    }
                    if ( flg ) {
                        i.setY(-70);
                        iteratorEnemy.remove();
                        score = score.add(new BigInteger("1000"));
                        textScore.setText(score.toString());
                    }
                }

                iteratorEnemyTama = listEnemyTama.iterator();
                while ( iteratorEnemyTama.hasNext() ) {
                    var i = iteratorEnemyTama.next();
                    if ( ImageObject.dist(i, self) <= 32 ) {
                        gameFinish();
                    }
                }

                listEnemyTama.addAll(listEnemyTamaAdd);
                listEnemyTamaAdd.clear();

                if ( selfTamaCoolTCnt <= 0 ) {
                    listMyTama.add(new EntityTamaSelf(imageTamaSelf, root, self.getX() + 24, self.getY() - 8, new Vector2(0, -8)));
                    listMyTama.add(new EntityTamaSelf(imageTamaSelfNaname, root, self.getX() + 24, self.getY() - 8, new Vector2(4, -8)));
                    listMyTama.add(new EntityTamaSelf(imageTamaSelfNaname, root, self.getX() + 24, self.getY() - 8, new Vector2(-4, -8)));
                    if ( rnd.nextInt(3) == 0 )
                        listMyTama.add(new EntityTamaSelfSearch(imageTamaSelfSearch, root, self.getX() + 24, self.getY() - 8, new Vector2(-4, -8)));
                    selfTamaCoolTCnt = 10;
                }
            }
        };

        animationTimer.start();

    }

    public void gameFinish ( ) {
        System.out.println(score.toString());
        animationTimer.stop();
        System.exit(0);
    }

    private void mouseMoved (MouseEvent mouseEvent) {
        self.setY(mouseEvent.getY() - ( (int)self.getImage().getHeight() >> 1 ) );
        if ( mouseEvent.getX() + ((int)self.getImage().getWidth() >> 1) > GAME_WIDTH ) {
            self.setX(GAME_WIDTH - self.getImage().getWidth() );
        }
        else {
            self.setX((mouseEvent.getX() - ((int) self.getImage().getWidth() >> 1)));
        }
    }

    private void keyPressed (KeyEvent keyEvent){
        /*
        if ( keyEvent.getCode() == KeyCode.SPACE ) {
            if ( selfTamaCoolTCnt > 0 ) return;
            listMyTama.add(new EntityTamaSelf(imageTamaSelf, root, self.getX() + 24, self.getY() - 8, new Vector2(0, -8)));
            listMyTama.add(new EntityTamaSelf(imageTamaSelfNaname, root, self.getX() + 24, self.getY() - 8, new Vector2(4, -8)));
            listMyTama.add(new EntityTamaSelf(imageTamaSelfNaname, root, self.getX() + 24, self.getY() - 8, new Vector2(-4, -8)));
            selfTamaCoolTCnt = 5;
        }
        */
    }
}
