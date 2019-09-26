package r1825.syoribu;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import r1825.syoribu.entity.enemy.EntityEnemyBase;
import r1825.syoribu.entity.enemy.EntityEnemyNormal;
import r1825.syoribu.entity.tama.EntityTamaBase;
import r1825.syoribu.entity.tama.EntityTamaEnemyNormal;
import r1825.syoribu.entity.tama.EntityTamaSelf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    Pane root = new Pane();

    ImageView self = new ImageView();
    ImageView background = new ImageView();

    Image imageTamaSelf = new Image("r1825/syoribu/img/_i_icon_13029_icon_130290_48.png");
    Image imageEnemy = new Image("r1825/syoribu/img/_i_icon_10777_icon_107770_64.png");
    Image imageTamaEnemy = new Image("r1825/syoribu/img/_i_icon_15400_icon_154000_48.png");

    List<EntityTamaBase> listMyTama = new ArrayList<>();
    List<EntityEnemyBase> listEnemy = new ArrayList<>();
    public List<EntityTamaBase> listEnemyTama = new ArrayList<>();

    int selfTamaCoolTCnt = 0;
    int enemyPopupTCnt = 0;

    AnimationTimer animationTimer;

    public void begin ( Stage stage ) {

        Scene scene = new Scene( root, 1980, 1080 );

        stage.setTitle("北方Projekt");
        stage.setScene(scene);
        stage.show();

        Image imageSelf = new Image("r1825/syoribu/img/_i_icon_13948_icon_139480_64.png");
        Image imageBackground = new Image("r1825/syoribu/img/backtmp.jpg");
        self.setImage(imageSelf);
        background.setImage(imageBackground);

        background.setFitHeight(1080);
        background.setFitWidth(1980);
        root.getChildren().add(background);
        root.getChildren().add(self);

        scene.setOnMouseMoved(event -> mouseMoved(event));
        scene.setOnKeyPressed(event -> keyPressed(event));

        animationTimer = new AnimationTimer() {

            @Override
            public void handle(long now) {

                //新しい敵の出現
                if ( enemyPopupTCnt <= 0 ) {
                    int yPos = -50;
                    int xPos = new Random().nextInt(1900);
                    listEnemy.add(new EntityEnemyNormal(imageEnemy, root, xPos, yPos, imageTamaEnemy));
                    enemyPopupTCnt = 30;
                }
                else {
                    enemyPopupTCnt--;
                }

                // 自機の弾のクールダウンカウントを減らす
                if ( selfTamaCoolTCnt > 0 ) selfTamaCoolTCnt--;
                // 自機の弾の移動
                var iteratorMyTama = listMyTama.iterator();
                while ( iteratorMyTama.hasNext() ) {
                    var i = iteratorMyTama.next();
                    i.update();
                    if ( i.getY() <= -50 ) {
                        iteratorMyTama.remove();
                    }
                }

                // 敵の移動
                for ( var i : listEnemy ) {
                    i.update();
                }

                // 敵の弾の移動
                var iteratorEnemyTama = listEnemyTama.iterator();
                while ( iteratorEnemyTama.hasNext() ) {
                    var i = iteratorEnemyTama.next();
                    i.update();
                    if ( i.getY() >= 1500 ) {
                        iteratorEnemyTama.remove();
                    }
                }

                // 衝突判定
                var iteratorEnemy = listEnemy.iterator();
                while ( iteratorEnemy.hasNext() ) {
                    var i = iteratorEnemy.next();
                    if ( ImageObject.dist(i, self) <= 32 ) {
                        animationTimer.stop();
                        System.exit(0);
                    }
                    iteratorMyTama = listMyTama.iterator();
                    while ( iteratorMyTama.hasNext() ) {
                        var j = iteratorMyTama.next();
                        if ( ImageObject.dist(i, j) <= 24 ) {
                            i.setY(-70);
                            j.setY(-70);
                            iteratorEnemy.remove();
                            iteratorMyTama.remove();
                            break;
                        }
                    }
                }

                iteratorEnemyTama = listEnemyTama.iterator();
                while ( iteratorEnemyTama.hasNext() ) {
                    var i = iteratorEnemyTama.next();
                    if ( ImageObject.dist(i, self) <= 24 ) {
                        animationTimer.stop();
                        System.exit(0);
                    }
                }
            }
        };

        animationTimer.start();

    }
    private void mouseMoved (MouseEvent mouseEvent) {
        self.setY(mouseEvent.getY()-32);
        self.setX(mouseEvent.getX()-32);
    }

    private void keyPressed (KeyEvent keyEvent){
        if ( keyEvent.getCode() == KeyCode.SPACE ) {
            if ( selfTamaCoolTCnt > 0 ) return;
            listMyTama.add(new EntityTamaSelf(imageTamaSelf, root, self.getX() + 8, self.getY() - 32));
            selfTamaCoolTCnt = 5;
        }
    }
}
