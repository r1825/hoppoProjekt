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

import java.util.ArrayList;
import java.util.List;

public class Game {

    Pane root = new Pane();

    ImageView self = new ImageView();
    ImageView background = new ImageView();

    Image imageTama = new Image("r1825/syoribu/img/_i_icon_13029_icon_130290_48.png");

    List<ImageObject> listMyTama = new ArrayList<>();

    int timeCount = 0;

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

        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                if ( timeCount > 0 ) timeCount--;
                var it = listMyTama.iterator();
                while ( it.hasNext() ) {
                    var i = it.next();
                    i.setY(i.getY()-5);
                    if ( i.getY() <= -50 ) {
                        it.remove();
                    }
                }
            }
        };

        timer.start();

    }
    private void mouseMoved (MouseEvent mouseEvent) {
        self.setY(mouseEvent.getY()-32);
        self.setX(mouseEvent.getX()-32);
    }

    private void keyPressed (KeyEvent keyEvent){
        if ( keyEvent.getCode() == KeyCode.SPACE ) {
            if ( timeCount > 0 ) return;
            listMyTama.add(new ImageObject(imageTama, root, self.getX() + 8, self.getY() - 32));
            timeCount = 10;
        }
    }
}
