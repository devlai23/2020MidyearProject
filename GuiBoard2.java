import javafx.application.Application; import javafx.geometry.HPos; import javafx.geometry.VPos; import javafx.scene.Scene; import javafx.scene.layout.BorderPane; import javafx.scene.layout.ColumnConstraints; import javafx.scene.layout.GridPane; import javafx.scene.layout.RowConstraints; import javafx.scene.paint.Color; import javafx.scene.shape.Circle; import javafx.scene.shape.Rectangle; import javafx.stage.Stage;

public class GuiBoard2 extends Application {

private static final int BOARD_SIZE = 8;
private static final int SQUARE_SIZE = 64;
private static final int NUM_PIECES = 12;
private static final int PLAYER_ONE_CHECKER_TYPE = 1;
private static final int PLAYER_TWO_CHECKER_TYPE = 2;

@Override
public void start(Stage primaryStage) {
    GridPane checkerBoard = new GridPane();

    configureBoardLayout(checkerBoard);

    addSquaresToBoard(checkerBoard);

    Circle[] playerOnePieces = new Circle[NUM_PIECES];
    Circle[] playerTwoPieces = new Circle[NUM_PIECES];

    addPiecesToBoard(checkerBoard, playerOnePieces, playerTwoPieces);

    // for(int i = 0; i < playerOnePieces.length; i++) {
    //     playerOnePieces[i].setOnMouseClicked(new CheckerHandler(i, PLAYER_ONE_CHECKER_TYPE));
    // }

    // for(int i = 0; i < playerTwoPieces.length; i++) {
    //     playerTwoPieces[i].setOnMouseClicked(new CheckerHandler(i, PLAYER_TWO_CHECKER_TYPE));
    // }

    BorderPane root = new BorderPane(checkerBoard);
    primaryStage.setScene(new Scene(root, 500, 500));
    primaryStage.setTitle("Checkers");
    primaryStage.setResizable(false);
    primaryStage.show();
}

private void addPiecesToBoard(GridPane checkerBoard, Circle[] playerOnePieces, Circle[] playerTwoPieces) {
    for (int i=0; i<NUM_PIECES; i++) {
        playerOnePieces[i] = new Circle(SQUARE_SIZE/2-4, Color.RED);
        playerOnePieces[i].setStroke(Color.BLACK);
        checkerBoard.add(playerOnePieces[i], i % (BOARD_SIZE / 2) * 2 + (2 * i / BOARD_SIZE) % 2, BOARD_SIZE - 1 - (i * 2) / BOARD_SIZE);

        playerTwoPieces[i] = new Circle(SQUARE_SIZE/2 -4, Color.BLACK);
        playerTwoPieces[i].setStroke(Color.WHITE);
        checkerBoard.add(playerTwoPieces[i], i % (BOARD_SIZE / 2) * 2 + (1 + 2 * i / BOARD_SIZE)%2, (i*2) / BOARD_SIZE);
    }
}

private void addSquaresToBoard(GridPane board) {
    Color[] squareColors = new Color[] {Color.WHITE, Color.GRAY};
    for (int row = 0; row < BOARD_SIZE; row++) {
        for (int col = 0; col < BOARD_SIZE; col++) {
            board.add(new Rectangle(SQUARE_SIZE, SQUARE_SIZE, squareColors[(row+col)%2]), col, row);
        }
    }
}

private void configureBoardLayout(GridPane board) {
    for (int i=0; i<BOARD_SIZE; i++) {
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setMinHeight(SQUARE_SIZE);
        rowConstraints.setPrefHeight(SQUARE_SIZE);
        rowConstraints.setMaxHeight(SQUARE_SIZE);
        rowConstraints.setValignment(VPos.CENTER);
        board.getRowConstraints().add(rowConstraints);

        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setMinWidth(SQUARE_SIZE);
        colConstraints.setMaxWidth(SQUARE_SIZE);
        colConstraints.setPrefWidth(SQUARE_SIZE);
        colConstraints.setHalignment(HPos.CENTER);
        board.getColumnConstraints().add(colConstraints);
    }
}

public static void main(String[] args) {
    launch(args);
}
}