package javafx.src;

import java.io.IOException;

import javax.lang.model.util.ElementScanner6;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class GuiBoard extends Application {

    Game game = new Game();

    //CheckerBoard board;
    public static Pane pane = new Pane();

    //public static ImageView redImageView = new ImageView(new Image("red_piece.png"));
    //public static ImageView blackImageView = new ImageView(new Image("black_piece.png"));

    @Override
    public void start(Stage primaryStage) throws IOException {

        drawStarterBoard();
        // see if i need more layers when adding checkers
        
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
        
    }

    private static void moveChecker() {

    }

    public void drawStarterBoard() {
        
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
                    rect.setFill(Color.LIGHTBLUE);
                }

                pane.getChildren().add(rect);
            }
        }
    }

    private static class CheckersInfo {
        static final int EMPTY = 0;
        static final int RED = 1;
        static final int WHITE = 2;
        static final int REDKING = 3;
        static final int WHITEKING = 4;

        int[][] board;

        CheckersInfo() {
            board = new int[8][8];
            setUpPieces();
        }

        void setUpPieces() {
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    if (r % 2 == c % 2) {
                        if (r <= 2)
                            board[r][c] = WHITE;
                        else if (r >= 5)
                            board[r][c] = RED;
                        else
                            board[r][c] = EMPTY;
                    }
                    else {
                        board[r][c] = EMPTY;
                    }
                }
            }
        }

        int retPiece(int row, int column) {
            return board[row][column];
        }

        


    }

    

}
