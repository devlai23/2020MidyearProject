package javafx.src;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class gui_board {

    public void start(Stage primaryStage) {
        Pane pane = new Pane();



        Scene scene = new Scene(null, 300, 200);
        primaryStage.setTitle("Checkers: Java");
        primaryStage.setScene(scene);
        primaryStage.show();

        EventHandler<ActionEvent> eventHandler = e -> {
            
        };
    }
}