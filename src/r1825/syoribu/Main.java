package r1825.syoribu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main extends Application {

    Text text = new Text("北方Projekt");

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox( 10.0 );

        text.setFont(Font.font(64));
        text.setTextAlignment( TextAlignment.CENTER );

        root.getChildren().add(text);
        Scene scene = new Scene( root, 1980, 1080 );

        primaryStage.setTitle("北方Projekt");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed(event -> keyPressed(event));
    }

    private void keyPressed (KeyEvent keyEvent) {
        var code = keyEvent.getCode();
        if ( code == KeyCode.A ) {
            text.setX(text.getX()-3);
        }
        else if ( code == KeyCode.D ) {
            text.setX(text.getX()+3);
        }
        else if ( code == KeyCode.W ) {
            text.setY(text.getY()-3);
        }
        else if ( code == KeyCode.S ) {
            text.setY(text.getY()+3);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
