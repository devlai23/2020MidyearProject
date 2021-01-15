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
    public void start(Stage primaryStage) {
        System.out.println("Hello");
        Pane pane = new Pane();

        for (int row = 0; row<8; row++) {
            for (int column = 0; column < 8; column++) {
                Rectangle r = new Rectangle();
                
                r.widthProperty().bind(pane.widthProperty().divide(8));
                r.heightProperty().bind(pane.heightProperty().divide(8));
                r.xProperty().bind(pane.widthProperty().divide(8).multiply(column).subtract(pane.widthProperty().divide(4)));
                r.yProperty().bind(pane.heightProperty().divide(8).multiply(row));

                r.setFill(Color.BLACK);
                if ((row +column) % 2 == 0) {
                    r.setFill(Color.RED);
                }

                // add a new checker piece every other space(odd/even columns) if we are in row 0-2 or 5-7 

                pane.getChildren().add(r);
            }
        }
        // Button b = new Button("Button"); // eventually addEventListener to whatever this becomes, testing formatting
        // later-- bind this location so the window can be stretched and button will be in same general place
        // the below code doesnt work, says xProperty doesn't exist
        // b.xProperty().bind(pane.widthProperty().add(pane.widthProperty().subtract(pane.widthProperty().divide(8))));
        // pane.getChildren().add(b);

        Scene scene = new Scene(pane, 400, 375);
        primaryStage.setTitle("Welcome to CheckerBot!"); 
        primaryStage.setScene(scene); 
        primaryStage.show();

        EventHandler<ActionEvent> eventHandler = e -> {
            
        };
    }
}