import java.util.*;

public class game {
    static char[][] board = new char[8][8];
    static boolean[][] king = new boolean[8][8];
    static int[][] ref = {  
        {0, 1, 0, 2, 0, 3, 0, 4},
        {5, 0, 6, 0, 7, 0, 8, 0},
        {0, 9, 0, 10, 0, 11, 0, 12},
        {13, 0, 14, 0, 15, 0, 16, 0},
        {0, 17, 0, 18, 0, 19, 0, 20},
        {21, 0, 22, 0, 23, 0, 24, 0},
        {0, 25, 0, 26, 0, 27, 0, 28},
        {29, 0, 30, 0, 31, 0, 32, 0}
    };
    static Tree<Node> tree;
    static boolean run = true;
    static char turn = 'R';
    static Scanner s = new Scanner(System.in); 
    
    public static void main(String[] args) {
        pieceCreation();
        System.out.println("Welcome to CheckerBot");
        while (run) {
            System.out.println();
            score();
            printTurn();
            printBoard();
            newInput();
            createTree();
            if (turn == 'R')
                turn = 'W';
            else
                turn = 'R';
        }
        s.close();
    }

    public static void move(int currentPos, int movePos) {
        // takes 4 possible inputs 
        if (movePos < 1 || movePos > 4) {
            System.out.println("Invalid Move, must be an integer from 1-4. Move given: " + movePos + ".");
            System.out.println("Try another move below.");
            newInput();
            return;
        }
        
        // if not king (bc kings can move all 4 directions)
        if (turn == 'R' && (movePos == 3 || movePos == 4)) {
            System.out.println("Invalid Move, this piece must move forward. Move given: " + movePos + ".");
            newInput();
            return;
        }
        else if (turn == 'W' && (movePos == 1 || movePos == 2)) {
            System.out.println("Invalid Move, this piece must move forward. Move given: " + movePos + ".");
            newInput();
            return;
        }

        int row = -1; // even or odd row
        int mod = currentPos % 8; 
        if (mod == 1 || mod == 2 || mod == 3 || mod == 4) {
            row = 1;
        }
        else if (mod == 5 || mod == 6 || mod == 7 || mod == 0) {
            row = 2;
        }

        int[] currentPosConverted = convert(currentPos);
        // error check for off board
        if (turn == 'R' && currentPosConverted[1] == 0 && movePos == 1) {
            System.out.println("Invalid Move, this piece must move ON THE BOARD to an open space. Move given: " + movePos + ".");
            newInput();
            return;
        }
        if (turn == 'W' && currentPosConverted[1] == 0 && movePos == 4) {
            System.out.println("Invalid Move, this piece must move ON THE BOARD to an open space. Move given: " + movePos + ".");
            newInput();
            return;
        }
        if (turn == 'R' && currentPosConverted[1] == 7 && movePos == 2) {
            System.out.println("Invalid Move, this piece must move ON THE BOARD to an open space. Move given: " + movePos + ".");
            newInput();
            return;
        }
        if (turn == 'W' && currentPosConverted[1] == 7 && movePos == 3) {
            System.out.println("Invalid Move, this piece must move ON THE BOARD to an open space. Move given: " + movePos + ".");
            newInput();
            return;
        }
        if (!(board[currentPosConverted[0]][currentPosConverted[1]] == turn)) { // if try move other player piece
            System.out.println("Invalid Piece, must be " + turn);
            System.out.println("Try another move below.");
            newInput();
            return;
        }
        char temp = board[currentPosConverted[0]][currentPosConverted[1]];
        boolean kingTemp = king[currentPosConverted[0]][currentPosConverted[1]];
        board[currentPosConverted[0]][currentPosConverted[1]] = '\0';  // delete current position
        
        // check if row is even or odd & increment (move)

        if (movePos == 1 && row == 1) {
            currentPos -= 4;
        }
        else if (movePos == 1 && row == 2) {
            currentPos -= 5;
        }
        else if (movePos == 2 && row == 1) {
            currentPos -= 3;
        }
        else if (movePos == 2 && row == 2) {
            currentPos -= 4;
        }
        else if (movePos == 3 && row == 1){
            currentPos += 5;
        }
        else if (movePos == 3 && row == 2){
            currentPos += 4;
        }
        else if (movePos == 4 && row == 1){
            currentPos += 4;
        }
        else if (movePos == 4 && row == 2){
            currentPos += 3;
        }

        int[] movePosConverted = convert(currentPos);
        char movePiece = board[movePosConverted[0]][movePosConverted[1]];
        if (movePiece==turn) { // check if you will hit your own piece
            System.out.println("Invalid Move, this piece will run into your own piece. Move given: " + movePos + ".");
            newInput();
            return;
        }
        char check = ' ';
        if (temp == 'R') {
            check = 'W';
        }
        else if (temp == 'W') {
            check = 'R';
        }
        if (board[movePosConverted[0]][movePosConverted[1]] == check) { //checking if there is an oppoenent piece, then taking it
            board[movePosConverted[0]][movePosConverted[1]] = '\0';
            if (movePos == 1){
                movePosConverted[0] -= 1;
                movePosConverted[1] -= 1;
            }
            else if (movePos == 2){
                movePosConverted[0] -= 1;
                movePosConverted[1] += 1;
            }
            else if (movePos == 3){
                movePosConverted[0] += 1;
                movePosConverted[1] += 1;
            }
            else if (movePos == 4){
                movePosConverted[0] += 1;
                movePosConverted[1] -= 1;
            }
        }
        board[movePosConverted[0]][movePosConverted[1]] = temp;

        // swapping king array positions
        king[currentPosConverted[0]][currentPosConverted[1]] = king[movePosConverted[0]][movePosConverted[1]];
        king[movePosConverted[0]][movePosConverted[1]] = kingTemp;
        
        // changes piece into a king if reaches the end
        if (turn == 'R' && movePosConverted[0] == 0) {
            king[movePosConverted[0]][movePosConverted[1]] = true;
        }
        else if (turn == 'W' && movePosConverted[0] == 7) {
            king[movePosConverted[0]][movePosConverted[1]] = true;
        }

    }

    public static int[] convert(int currentPos){
        int[] ret = new int[2];
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (ref[i][j] == currentPos){
                    ret[0] = i;
                    ret[1] = j;
                    return ret;
                }
            }
        }
        return ret;
    }

    public static void pieceCreation() {
        // white pieces
        for (int i = 1; i < 8; i+=2){
            board[0][i] = 'W';
        } 
        for (int i = 0; i < 8; i+=2){
            board[1][i] = 'W';
        }
        for (int i = 1; i < 8; i+=2){
            board[2][i] = 'W';
        }

        // red pieces
        for (int i = 0; i < 8; i+=2){
            board[5][i] = 'R';
        }
        for (int i = 1; i < 8; i+=2){
            board[6][i] = 'R';
        }
        for (int i = 0; i < 8; i+=2){
            board[7][i] = 'R';
        }
    }

    public static void printBoard(){
        for (int i = 0; i < 8; i++){
            System.out.println(Arrays.toString(board[i]));
        }
    }

    public static void score(){
        int red = 0;
        int white = 0;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (board[i][j] == 'R'){
                    red++;
                }
                else if (board[i][j] == 'W'){
                    white++;
                }
            }
        }
        if (red == 0){
            System.out.println("White Won!");
            run = false;
        }
        else if (white == 0){
            System.out.println("Red Won!");
            run = false;
        }
        else{
            System.out.printf("Red: %d\nWhite: %d\n", red, white);
        }
    }

    public static void createTree(){
        Node root = new Node();
        tree = new Tree<>(root);
    }

    public static void printTurn() {
        if (turn == 'R') {
            System.out.println("It is red's turn.");
        }
        else {
            System.out.println("It is white's turn.");
        }
    }

    public static void newInput() {
        String str = s.nextLine();
        String[] splitted = str.split(" ");
        move(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]));
    }

}