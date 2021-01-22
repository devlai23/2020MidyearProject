package javafx.src;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class gui_board extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Pane pane = new Pane();
        // see if i need more layers when adding checkers
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    Rectangle rect = new Rectangle();

                    rect.widthProperty().bind(pane.widthProperty().divide(8));
                    rect.heightProperty().bind(pane.heightProperty().divide(8));
                    rect.xProperty().bind(pane.widthProperty().divide(8).multiply(c));
                    rect.yProperty().bind(pane.heightProperty().divide(8).multiply(r));

                    rect.setFill(Color.WHITE);
                    if ((r+c) % 2 == 1) {
                        rect.setFill(Color.BLACK);
                    }

                    pane.getChildren().add(rect);
                }
            }

            Scene scene = new Scene(pane, 600, 600);
            primaryStage.setTitle("Checkers: Java");
            primaryStage.setScene(scene);
            primaryStage.show();
        // Button b = new Button("Button"); // eventually addEventListener to whatever this becomes, testing formatting
        // later-- bind this location so the window can be stretched and button will be in same general place
        // the below code doesnt work, says xProperty doesn't exist
        // b.xProperty().bind(pane.widthProperty().add(pane.widthProperty().subtract(pane.widthProperty().divide(8))));
        // pane.getChildren().add(b);

        // EventHandler<ActionEvent> eventHandler = e -> {
            
        // };
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }

    public void startRedPieces() {

    }

    public void startWhitePieces() {

    }

}