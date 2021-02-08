package r1825.syoribu;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
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
import r1825.syoribu.entity.enemy.*;
import r1825.syoribu.entity.item.EntityItem;
import r1825.syoribu.entity.item.EntityItemEquipment;
import r1825.syoribu.entity.item.EntityItemRepair;
import r1825.syoribu.entity.item.EntityItemTsarBomba;
import r1825.syoribu.entity.tama.EntityTamaBase;
import r1825.syoribu.entity.tama.EntityTamaEnemyNormal;
import r1825.syoribu.entity.tama.EntityTamaSelfTsarBomba;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Game {

    public static int[] dx = {0, 1, 0, -1, 0, 1, 1, -1, -1};
    public static int[] dy = {0, 0, 1, 0, -1, 1, -1, 1, -1};

    public static final int HEIGHT = 720;
    public static final int WIDTH = 1280;
    public static final int SCORE_WIDTH = 256;
    public static final int GAME_WIDTH = WIDTH - SCORE_WIDTH;
    private static final int BOSS_INTERVAL = 50000;

    public static int minEnemyNum = 1;

    SecureRandom rnd = new SecureRandom();

    Pane root = new Pane();

    public EntityPlayer player;
    ImageView background = new ImageView();

    public Image imageTamaSelf = new Image("r1825/syoribu/img/tama1.png");
    public Image imageTamaSelfNaname = new Image("r1825/syoribu/img/enemy.png");
    public Image imageTamaSelfSearch = new Image("r1825/syoribu/img/enemy3.png");
    public Image imageTamaSelfDefence = new Image("r1825/syoribu/img/mai4.png");
    public Image imageTamaSelfBullet = new Image("r1825/syoribu/img/tama5.png");
    public Image imageEnemy = new Image("r1825/syoribu/img/koba2.png");
    public Image imageEnemyLaser = new Image("r1825/syoribu/img/koba3.png");
    public Image imageEnemyDivide = new Image("r1825/syoribu/img/koba4.png");
    public Image imageEnemyKamikaze = new Image("r1825/syoribu/img/kamikaze.png");
    public Image imageEnemyBoss = new Image("r1825/syoribu/img/boss.png");
    public Image imageTamaEnemy = new Image("r1825/syoribu/img/tama2.png");
    public Image imageTamaEnemyNaname = new Image("r1825/syoribu/img/enemy2.png");
    public Image imageTamaEnemyLaser = new Image("r1825/syoribu/img/tama4.png");

    public Image imageItemPowerup = new Image("r1825/syoribu/img/power.png");
    public Image imageItemRepair = new Image("r1825/syoribu/img/repair.png");
    public Image imageItemTsar = new Image("r1825/syoribu/img/nuclear.png");

    public List<EntityEnemyBase> listEnemy = new ArrayList<>();
    public List<EntityTamaBase> listEnemyTama = new ArrayList<>();
    public List<EntityTamaBase> listEnemyTamaAdd = new ArrayList<>();
    List<EntityItem> listItem = new ArrayList<>();

    public int score = 0;

    private boolean isStopped = false;
    private long stopTime = 0;
    
    public boolean isDuringGame = false;
    public boolean hasGameFinished = false;

    private ImageView shownAnime = null;

    public AnimationTimer animationTimer;

    Text centreMessage = new Text();

    public long timeCount = 0;

    private int taepodongTime = 0;
    private int taepodongType = 0;
    private int taepodongPos = 0;

    private int nextBoss = BOSS_INTERVAL;

    public TextField textField;

    public void begin ( Stage stage ) {

        Scene scene = new Scene( root, WIDTH, HEIGHT );

        stage.setTitle("北方Projekt");
        stage.setScene(scene);
        stage.show();

        Image imageSelf = new Image("r1825/syoribu/img/main1.png");
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

        Text testData = new Text();
        testData.setY(16*2);
        testData.setX(GAME_WIDTH + 16);
        testData.setFont(new Font(20));
        testData.setFill(Color.WHITE);
        root.getChildren().add(testData);

        ImageView title = new ImageView(new Image("r1825/syoribu/img/hoppoProjekt.png"));
        title.setY(HEIGHT-144);
        title.setX(WIDTH-256);
        title.setFitWidth(256);
        title.setFitHeight(144);
        root.getChildren().add(title);

        player = new EntityPlayer(imageSelf, root, 255, 255, new Vector2(0, 0), 3);

        scene.setOnMouseMoved(this::mouseMoved);
        scene.setOnMouseDragged(this::mouseMoved);
        scene.setOnKeyPressed(this::keyPressed);

        System.out.println(imageEnemyKamikaze.getHeight());

        animationTimer = new AnimationTimer() {

            @Override
            public void handle(long now) {

                timeCount++;

                if ( (timeCount & 31 ) == 0) {
                    score++;
                }

                if ( player.getLife() == 1 ) {
                    if ( timeCount % 25 == 0 ) {
                        title.setVisible(!title.isVisible());
                    }
                }
                else {
                    if ( !title.isVisible() ) {
                        title.setVisible(true);
                    }
                }

                if ( isStopped && stopTime < System.currentTimeMillis()  ) {
                    isStopped = false;
                    root.getChildren().remove(shownAnime);
                }
                if ( isStopped ) return;
                player.update();

                testData.setText(String.format("Score: %d\nLife: %d\nTsarBomba: %d", score, player.getLife(), player.tsar_bomba));

                if ( rnd.nextInt(1000) < 1 ) Game.minEnemyNum++;
                popItem();

                //新しい敵の出現
                while ( listEnemy.size() < minEnemyNum ) {
                    popEnemy();
                }

                if ( score >= nextBoss ) {
                    nextBoss += BOSS_INTERVAL;
                    popBoss();
                }

                popTaepodong();

                // 自機の弾の移動
                {
                    var iteratorMyTama = player.listTama.iterator();
                    while (iteratorMyTama.hasNext()) {
                        var i = iteratorMyTama.next();
                        boolean flg = i.update();
                        if (flg || i.isOutside()) {
                            iteratorMyTama.remove();
                            root.getChildren().remove(i.imageView);
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
                            root.getChildren().remove(i.imageView);
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
                            root.getChildren().remove(i.imageView);
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
                            root.getChildren().remove(i.imageView);
                            iteratorItem.remove();
                        }
                    }
                }

                // 敵と自機の衝突判定
                {
                    listEnemy.sort(Comparator.comparingInt(x -> x.pos));
                    int x = player.pos % 1000;
                    int y = player.pos / 1000;
                    for ( int I = 0; I < 9; I++ ) {
                        int nx = x + dx[I];
                        int ny = y + dy[I];
                        int npos = nx + 1000 * ny;
                        int pos = lowerBoundEnemy(listEnemy, npos);
                        if ( pos < 0 || listEnemy.size()-1 < pos ) continue;
                        int damage = 0;
                        var iteratorEnemy = listEnemy.listIterator(pos);

                        while (iteratorEnemy.hasNext()) {
                            var i = iteratorEnemy.next();
                            if ( i.pos != npos ) break;
                            if ( ImageObject.isTouching(i, player)) {
                                damage = 1;
                                iteratorEnemy.remove();
                                root.getChildren().remove(i.imageView);
                                i.setY(-70);
                            }
                        }
                        player.damage(damage);
                        if ( player.isDead() ) {
                            gameFinish();
                        }
                    }
                }

                // 敵と自弾の衝突判定
                {
                    player.listTama.sort(Comparator.comparingInt(x -> x.pos));

                    var iteratorEnemy = listEnemy.iterator();
                    while (iteratorEnemy.hasNext()) {
                        var i = iteratorEnemy.next();
                        int x = i.pos % 1000;
                        int y = i.pos / 1000;
                        boolean flg = false;
                        int damage = 0;
                        for ( int I = 0 ; I < 9; I++ ) {
                            int nx = x + dx[I];
                            int ny = y + dy[I];
                            int npos = nx + 1000 * ny;

                            int pos = lowerBoundTama(player.listTama, npos);
                            if ( pos < 0 || player.listTama.size()-1 < pos ) continue;
                            var iteratorMyTama = player.listTama.listIterator(pos);
                            while ( iteratorMyTama.hasNext() ) {
                                var j = iteratorMyTama.next();
                                if ( npos != j.pos ) break;
                                if ( ImageObject.isTouching(i, j) ) {
                                    if ( !j.canGoThrough() ) {
                                        root.getChildren().remove(j.imageView);
                                        iteratorMyTama.remove();
                                    }
                                    flg = true;
                                    damage = Math.max(damage, j.getDamage());
                                }
                            }
                        }
                        if ( flg ) {
                            i.damage(damage);
                            if ( i.isDead() ) {
                                root.getChildren().remove(i.imageView);
                                iteratorEnemy.remove();
                                score = score + i.getScore();
                            }
                        }
                    }
                }

                // 敵の弾と自機の衝突判定
                {
                    Collections.sort(listEnemyTama, Comparator.comparingInt(x -> x.pos));
                    int x = player.pos % 1000;
                    int y = player.pos / 1000;
                    for ( int I = 0; I < 9; I++ ) {
                        int nx = x + dx[I];
                        int ny = y + dy[I];
                        int npos = nx + 1000 * ny;

                        int pos = lowerBoundTama(listEnemyTama, npos);
                        if ( pos < 0 || listEnemyTama.size()-1 < pos ) continue;
                        int damage = 0;
                        var iteratorEnemyTama = listEnemyTama.listIterator(pos);

                        while (iteratorEnemyTama.hasNext()) {
                            var i = iteratorEnemyTama.next();
                            if ( i.pos != npos ) break;
                            if (ImageObject.isTouching(i, player)) {
                                damage = Math.max(i.getDamage(), damage);
                                if ( !i.canGoThrough() ) {
                                    iteratorEnemyTama.remove();
                                    root.getChildren().remove(i.imageView);
                                }
                            }
                        }
                        player.damage(damage);
                        if ( player.isDead() ) {
                            gameFinish();
                        }
                    }
                }

                // アイテムの取得判定
                {
                    var iteratorItem = listItem.iterator();
                    while ( iteratorItem.hasNext() ) {
                        var i = iteratorItem.next();
                        if ( ImageObject.isTouching(i, player) ) {
                            i.effect(player);
                            root.getChildren().remove(i.imageView);
                            iteratorItem.remove();
                            if ( rnd.nextInt(10) < 2 ) {
                                minEnemyNum++;
                            }
                        }
                    }
                }

                listEnemyTama.addAll(listEnemyTamaAdd);
                listEnemyTamaAdd.clear();
            }
        };


        centreMessage.setText("Press Enter to start!");
        centreMessage.setX(HEIGHT >> 1);
        centreMessage.setY(GAME_WIDTH >> 1);
        centreMessage.setFont(Font.font(32));
        root.getChildren().add(centreMessage);

    }

    public void gameFinish ( ) {
        isDuringGame = false;
        hasGameFinished = true;
        System.out.println(score);

        animationTimer.stop();

        textField = new TextField();
        textField.fontProperty().set(new Font(32));
        textField.setLayoutX(400);
        textField.setLayoutY(300);
        root.getChildren().addAll(textField);

        Text text = new Text();
        text.setText("名前を入力してEnter");
        text.fontProperty().set(new Font(24));
        text.setLayoutX(400);
        text.setLayoutY(400);
        root.getChildren().addAll(text);
        //System.exit(0);
    }

    private void mouseMoved (MouseEvent mouseEvent) {
        if ( isStopped ) return;
        player.setY(mouseEvent.getY() - ( player.imgH >> 1 ) );
        if ( mouseEvent.getX() + (player.imgW >> 1) > GAME_WIDTH ) {
            player.setX(GAME_WIDTH - player.imgW );
        }
        else {
            player.setX((mouseEvent.getX() - (player.imgW >> 1)));
        }
        player.pos = ((int)player.getX() >> 5) + 1000 * ((int)player.getY() >> 5);
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
            listEnemy.add(new EntityEnemyLaser(imageEnemyLaser, root, rnd.nextInt(WIDTH), yPos, imageTamaEnemyLaser, new Vector2(xMove * 1.5, 3)));
        }
        if ( rnd.nextInt(100) < 8 ) {
            listEnemy.add(new EntityEnemyKamikaze(imageEnemyKamikaze, root, rnd.nextInt(WIDTH), yPos, imageTamaEnemyLaser, new Vector2(xMove * 1.5, 3)));
        }
    }

    private void popBoss () {
        listEnemy.add(new EntityEnemyBoss(imageEnemyBoss, root, 400, -64, imageTamaEnemyNaname, new Vector2(0, 1)));
    }

    private void popTaepodong () {
        if ( taepodongTime == 0 && rnd.nextInt(500) == 0 ) {
            taepodongTime = 1;
            taepodongType = rnd.nextInt(5 );
        }
        if ( taepodongTime != 0 ) {

            if ( taepodongType == 0 ) {
                if ( taepodongTime == 1) {
                    taepodongPos = rnd.nextInt(1000);
                }
                for ( int i = 0; i < 8; i++ ) {
                    listEnemyTamaAdd.add(new EntityTamaEnemyNormal(imageTamaEnemyLaser, root, taepodongPos + 10*i, -10, new Vector2(0, 8)));
                }
            }
            else {
                listEnemyTamaAdd.add(new EntityTamaEnemyNormal(imageTamaEnemyLaser, root, 0, -10, new Vector2(0, 8)));
                listEnemyTamaAdd.add(new EntityTamaEnemyNormal(imageTamaEnemyLaser, root, 10, -10, new Vector2(0, 8)));
                listEnemyTamaAdd.add(new EntityTamaEnemyNormal(imageTamaEnemyLaser, root, 20, -10, new Vector2(0, 8)));
                listEnemyTamaAdd.add(new EntityTamaEnemyNormal(imageTamaEnemyLaser, root, 30, -10, new Vector2(0, 8)));
                listEnemyTamaAdd.add(new EntityTamaEnemyNormal(imageTamaEnemyLaser, root, 40, -10, new Vector2(0, 8)));
                listEnemyTamaAdd.add(new EntityTamaEnemyNormal(imageTamaEnemyLaser, root, 50, -10, new Vector2(0, 8)));
                listEnemyTamaAdd.add(new EntityTamaEnemyNormal(imageTamaEnemyLaser, root, 60, -10, new Vector2(0, 8)));
                listEnemyTamaAdd.add(new EntityTamaEnemyNormal(imageTamaEnemyLaser, root, 70, -10, new Vector2(0, 8)));
                listEnemyTamaAdd.add(new EntityTamaEnemyNormal(imageTamaEnemyLaser, root, GAME_WIDTH - 90, -10, new Vector2(0, 8)));
                listEnemyTamaAdd.add(new EntityTamaEnemyNormal(imageTamaEnemyLaser, root, GAME_WIDTH - 80, -10, new Vector2(0, 8)));
                listEnemyTamaAdd.add(new EntityTamaEnemyNormal(imageTamaEnemyLaser, root, GAME_WIDTH - 70, -10, new Vector2(0, 8)));
                listEnemyTamaAdd.add(new EntityTamaEnemyNormal(imageTamaEnemyLaser, root, GAME_WIDTH - 60, -10, new Vector2(0, 8)));
                listEnemyTamaAdd.add(new EntityTamaEnemyNormal(imageTamaEnemyLaser, root, GAME_WIDTH - 50, -10, new Vector2(0, 8)));
                listEnemyTamaAdd.add(new EntityTamaEnemyNormal(imageTamaEnemyLaser, root, GAME_WIDTH - 40, -10, new Vector2(0, 8)));
                listEnemyTamaAdd.add(new EntityTamaEnemyNormal(imageTamaEnemyLaser, root, GAME_WIDTH - 30, -10, new Vector2(0, 8)));
                listEnemyTamaAdd.add(new EntityTamaEnemyNormal(imageTamaEnemyLaser, root, GAME_WIDTH - 20, -10, new Vector2(0, 8)));
            }

            taepodongTime++;
            if ( taepodongTime == 500 ) {
                taepodongTime = 0;
            }
        }
    }

    private void popItem () {
        if ( rnd.nextInt(1000) < 10 ) {
            if ( rnd.nextInt(4) == 0 ) {
                listItem.add(new EntityItemRepair(imageItemRepair, root, rnd.nextInt(WIDTH), -65, new Vector2(0, 4)));
                listItem.add(new EntityItemTsarBomba(imageItemTsar, root, rnd.nextInt(WIDTH), -65, new Vector2(0, 4)));
            }
            else {
                listItem.add(new EntityItemEquipment(imageItemPowerup, root, rnd.nextInt(WIDTH), -65, new Vector2(0, 4)));
            }
        }
    }

    private void keyPressed (KeyEvent keyEvent){
        if ( keyEvent.getCode() == KeyCode.ENTER ) {
            if ( hasGameFinished ) {
                try {
                    SQLManager.insertResult(textField.getText(), score);
                }
                catch ( Exception e ) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
            if ( !isDuringGame ) {
                root.getChildren().remove(centreMessage);
                isStopped = false;
                isDuringGame = true;
                animationTimer.start();
            }
        }
        /*
        if ( keyEvent.getCode() == KeyCode.H ) {
            player.damage(-1);
        }
        if ( keyEvent.getCode() == KeyCode.K ) {
            listEnemy.add(new EntityEnemyLaser(imageEnemyLaser, root, player.getX(), player.getY(), imageTamaEnemyLaser, new Vector2(0, 0)));
        }
        if ( keyEvent.getCode() == KeyCode.U ) {
            int type = rnd.nextInt(10);
            if ( type < 4 ) {
                player.decreaseIntervalNormal();
            }
            else if ( type < 8 ) {
                if ( !player.addNaname() ) {
                    player.decreaseIntervalNaname();
                }
            }
            else {
                player.decreaseIntervalSearch();
            }
        }
        if ( keyEvent.getCode() == KeyCode.E ) {
            minEnemyNum++;
        }
         */
        if ( keyEvent.getCode() == KeyCode.SPACE ) {
            if ( isStopped || !isDuringGame ) return;
            if ( player.tsar_bomba <= 0 ) return;
            player.tsar_bomba--;
            for ( double i = 0; i < 360; i += 1) {
                Vector2 vec = new Vector2(Math.cos(Math.toRadians(i)) * 6, Math.sin(Math.toRadians(i)) * 6);

                EntityTamaBase entityTamaBase = new EntityTamaSelfTsarBomba(imageTamaSelfBullet, root, player.getCentreX(), player.getCentreY(), vec);
                entityTamaBase.imageView.setRotate(i+90);
                player.listTama.add(entityTamaBase);
            }
        }
    }

    public void stop ( int milli ) {
        isStopped = true;
        stopTime = milli + System.currentTimeMillis();
    }

    public void showAnime ( int timeMilli, Image anime ) {
        shownAnime = new ImageView(anime);
        shownAnime.setFitHeight(Game.HEIGHT);
        shownAnime.setFitWidth(Game.GAME_WIDTH);
        shownAnime.setX(0);
        shownAnime.setY(0);
        root.getChildren().add(shownAnime);
        Main.game.stop(timeMilli);
    }

    public static int lowerBoundTama (List<EntityTamaBase> list, int val ) {
        int left = -1;
        int right = list.size();
        while ( right - left > 1 ) {
            int mid = left + ( right - left ) / 2;
            if ( list.get(mid).pos >= val ) right = mid;
            else left = mid;
        }
        return right;
    }

    public static int lowerBoundEnemy (List<EntityEnemyBase> list, int val ) {
        int left = -1;
        int right = list.size();
        while ( right - left > 1 ) {
            int mid = left + ( right - left ) / 2;
            if ( list.get(mid).pos >= val ) right = mid;
            else left = mid;
        }
        return right;
    }
}
