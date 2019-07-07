package r1825.syoribu;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        Game game = new Game();
        game.begin(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
