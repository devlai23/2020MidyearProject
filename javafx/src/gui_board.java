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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class gui_board extends Application {

    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        // see if i need more layers when adding checkers
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Rectangle rect = new Rectangle();

                rect.widthProperty().bind(pane.widthProperty().divide(8));
                rect.heightProperty().bind(pane.heightProperty().divide(8));
                rect.xProperty().bind(pane.widthProperty().divide(8).multiply(c));
                rect.yProperty().bind(pane.heightProperty().divide(8).multiply(r));

                rect.setFill(Color.BLACK);
                if ((r+c) % 2 == 0) {
                    rect.setFill(Color.WHITE);
                }
                pane.getChildren().add(rect);
            }
        }

        Scene scene = new Scene(pane, 600, 600);
        primaryStage.setTitle("Checkers: Java");
        primaryStage.setScene(scene);
        primaryStage.show();

        // EventHandler<ActionEvent> eventHandler = e -> {
            
        // };
    }

}