package javafx.src;

import java.util.*;

public class Game {
    static char[][] board = {
        {' ', 'W', ' ', 'W', ' ', 'W', ' ', 'W'},
        {'W', ' ', 'W', ' ', 'W', ' ', 'W', ' '},
        {' ', 'W', ' ', 'W', ' ', 'W', ' ', 'W'},
        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
        {'R', ' ', 'R', ' ', 'R', ' ', 'R', ' '},
        {' ', 'R', ' ', 'R', ' ', 'R', ' ', 'R'},
        {'R', ' ', 'R', ' ', 'R', ' ', 'R', ' '}
    };
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
    static int redkings = 0;
    static int whitekings = 0; 
    static char[][] arraycopy = new char[board.length][board[0].length];
    
    public static void main(String[] args) {
        System.out.println("Welcome to CheckerBot");
        while (run) {
            System.out.println();
            score();
            if (run){
                printTurn();
                printBoard();
                Node choice = null;

                if (turn == 'W'){
                    Tree<Node> t = createTree();
                    ArrayList<int[]> totalValidMoves = new ArrayList<int[]>();
                    for (int i = 0; i < 8; i++){
                        for (int j = 0; j < 8; j++){
                            if (board[i][j] == 'W'){
                                ArrayList<int[]> array = validMoves(i, j);
                                for (int k = 0; k < array.size(); k++){
                                    totalValidMoves.add(array.get(k));
                                }
                            }
                        }
                    }

                    // for (int i = 0; i < totalValidMoves.size(); i++){
                    //     System.out.println(Arrays.toString(totalValidMoves.get(i)));
                    // }
                    // prints all valid bot moves
                    for (int i = 0; i < totalValidMoves.size(); i++){ //test all valid moves
                        for (int j = 0; j < board.length; j++){
                            for (int k = 0; k < board[j].length; k++){
                                arraycopy[j][k] = board[j][k];
                            }
                        }
                        int[] botinput = new int[3];
                        botinput[0] = totalValidMoves.get(i)[0];
                        botinput[1] = totalValidMoves.get(i)[1];
                        botinput[2] = totalValidMoves.get(i)[2];
                        
                        botmove(botinput);
                        // for (int j = 0; j < 8; j++){
                        //     System.out.println(Arrays.toString(arraycopy[j]));
                        // }
                        // prints the simulated bot moves
                        
                        //note the score
                        evaluationscore es = new evaluationscore(arraycopy, redkings, whitekings);
                        double score = es.ret();
                        // System.out.println(score);
                        // System.out.println();
                        // prints eval score of each simulated move
                        t.root.addChild(score, botinput[0], botinput[1], botinput[2]); //MAKE IT EVALUATE FARTHER DOWN LATER

                        //reset arraycopy for next time
                        arraycopyclear();
                    }

                    Collections.sort(t.root.children);
                    // t.printTree();
                    double baseval = t.root.children.get(0).value;

                    for (int i = t.root.children.size()-1; i >= 0; i--){ //narrow down to optimal moves
                        if (t.root.children.get(i).value > baseval){
                            t.root.children.remove(i);
                        }
                    }
                    Random r = new Random();
                    choice = t.root.children.get(r.nextInt(t.root.children.size()));
                }

                if (turn == 'W'){
                    int[] param = new int[2];
                    param[0] = choice.i;
                    param[1] = choice.j;
                    int moveParam = reverseConvert(param);
                    move(moveParam, choice.direction);
                }
                if (turn == 'R'){
                    newInput();
                }


                if (turn == 'R')
                    turn = 'W';
                else
                    turn = 'R';
            }
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

        int row = -1; // even or odd row
        int mod = currentPos % 8; 
        if (mod == 1 || mod == 2 || mod == 3 || mod == 4) {
            row = 1;
        }
        else if (mod == 5 || mod == 6 || mod == 7 || mod == 0) {
            row = 2;
        }

        int[] currentPosConverted = convert(currentPos);

        if (!king[currentPosConverted[0]][currentPosConverted[1]]){
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
        }

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
        board[currentPosConverted[0]][currentPosConverted[1]] = ' ';  // delete current position
        
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
        ArrayList<Character> direction = new ArrayList<>();
        if (movePos == 2){
            int y = movePosConverted[1];
            for (int x = movePosConverted[0]; x >= 0; x--){
                if (x >= 0 && y >= 0 && x < 8 && y < 8){
                    direction.add(board[x][y]); 
                    y++;
                }
            }
        }
        else if (movePos == 1){
            int y = movePosConverted[1];
            for (int x = movePosConverted[0]; x >= 0; x--){
                if (x >= 0 && y >= 0 && x < 8 && y < 8){
                    direction.add(board[x][y]); 
                    y--;
                }
            }
        }
        else if (movePos == 3){
            int y = movePosConverted[1];
            for (int x = movePosConverted[0]; x <= 7; x++){
                if (x >= 0 && y >= 0 && x < 8 && y < 8){
                    direction.add(board[x][y]); 
                    y++;
                }
            }
        }
        else if (movePos == 4){
            int y = movePosConverted[1];
            for (int x = movePosConverted[0]; x <= 7; x++){
                if (x >= 0 && y >= 0 && x < 8 && y < 8){
                    direction.add(board[x][y]); 
                    y--;
                }
            }
        }
        
        int x = 0;
        int jumpdistance = 0;
        while(x < direction.size() && direction.get(x) == check){
            if (x+1<direction.size() && direction.get(x+1) == ' '){
                jumpdistance+=2;
            }
            else{
                if (jumpdistance<2) {
                    System.out.println("Invalid Move, there must be an empty space behind.");
                    newInput();
                    return;
                }
            }
            x+=2;
        }

        if (jumpdistance > 0){
            if (movePos == 1){
                movePosConverted[0] = currentPosConverted[0] - jumpdistance;
                movePosConverted[1] = currentPosConverted[1] - jumpdistance;
                int counter = 1;
                for (int i = 0; i < jumpdistance/2; i++){
                    board[currentPosConverted[0] - counter][currentPosConverted[1] - counter] = ' ';
                    counter += 2;
                }
            }
            else if (movePos == 2){
                movePosConverted[0] = currentPosConverted[0] - jumpdistance;
                movePosConverted[1] = currentPosConverted[1] + jumpdistance;
                int counter = 1;
                for (int i = 0; i < jumpdistance/2; i++){
                    board[currentPosConverted[0] - counter][currentPosConverted[1] + counter] = ' ';
                    counter += 2;
                }
            }
            else if (movePos == 3){
                movePosConverted[0] = currentPosConverted[0] + jumpdistance;
                movePosConverted[1] = currentPosConverted[1] + jumpdistance;
                int counter = 1;
                for (int i = 0; i < jumpdistance/2; i++){
                    board[currentPosConverted[0] + counter][currentPosConverted[1] + counter] = ' ';
                    counter += 2;
                }
            }
            else if (movePos == 4){
                movePosConverted[0] = currentPosConverted[0] + jumpdistance;
                movePosConverted[1] = currentPosConverted[1] - jumpdistance;
                int counter = 1;
                for (int i = 0; i < jumpdistance/2; i++){
                    board[currentPosConverted[0] + counter][currentPosConverted[1] - counter] = ' ';
                    counter += 2;
                }
            }
        }

        board[movePosConverted[0]][movePosConverted[1]] = temp;
        // swapping king array positions
        king[currentPosConverted[0]][currentPosConverted[1]] = king[movePosConverted[0]][movePosConverted[1]];
        king[movePosConverted[0]][movePosConverted[1]] = kingTemp;
        
        // changes piece into a king if reaches the end
        if (movePosConverted[0] == 0) {
            king[movePosConverted[0]][movePosConverted[1]] = true;
            redkings++;
        }
        else if (movePosConverted[0] == 7) {
            king[movePosConverted[0]][movePosConverted[1]] = true;
            whitekings++;
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

    public static int reverseConvert(int[] currentPos){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if ((i == currentPos[0]) && (j == currentPos[1])){
                    return ref[i][j];
                }
            }
        }
        return -1;
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

    public static Tree<Node> createTree(){
        Node root = new Node();
        tree = new Tree<>(root);
        return tree;
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
        try{
            String str = s.nextLine();
            String[] splitted = str.split(" ");
            move(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]));
        }
        catch (NumberFormatException e){
            System.out.println(e);
            System.out.println("Please enter a valid input.");
            newInput();
        }
    }

    public static ArrayList<int[]> validMoves(int i, int j){
        ArrayList<int[]> ret = new ArrayList<int[]>();
        int savei = i;
        int savej = j;
        int row;
        if (i % 2 == 0){
            row = 1;
        }
        else{
            row = 2;
        }

        //check for: make sure it moves forward unless king, make sure not off board, check it will not hit own piece, 
        //check if jump is valid
        for (int a = 1; a <= 4; a++){
            if (!king[i][j]){
                if (a == 1 || a == 2) {
                    continue;
                }
            }
            if (j == 0 && a == 4) {
                continue;
            }
            if (j == 7 && a == 3) {
                continue;
            }

            int newi;
            int newj;

            if (a == 1){
                newi = i-1;
                newj = j-1;
            }
            else if (a == 2){
                newi = i-1;
                newj = j+1;
            }
            else if (a == 3){
                newi = i+1;
                newj = j+1;
            }
            else{
                newi = i+1;
                newj = j-1;
            }
            char movePiece = board[newi][newj];
            if (movePiece=='W') { // check if you will hit your own piece
                continue;
            }

            ArrayList<Character> direction = new ArrayList<>();
            if (a == 2){
                int y = newj;
                for (int x = newi; x >= 0; x--){
                    if (x >= 0 && y >= 0 && x < 8 && y < 8){
                        direction.add(board[x][y]); 
                        y++;
                    }
                }
            }
            else if (a == 1){
                int y = newj;
                for (int x = newi; x >= 0; x--){
                    if (x >= 0 && y >= 0 && x < 8 && y < 8){
                        direction.add(board[x][y]); 
                        y--;
                    }
                }
            }
            else if (a == 3){
                int y = newj;
                for (int x = newi; x <= 7; x++){
                    if (x >= 0 && y >= 0 && x < 8 && y < 8){
                        direction.add(board[x][y]); 
                        y++;
                    }
                }
            }
            else if (a == 4){
                int y = newj;
                for (int x = newi; x <= 7; x++){
                    if (x >= 0 && y >= 0 && x < 8 && y < 8){
                        direction.add(board[x][y]); 
                        y--;
                    }
                }
            }
            boolean flag = false;
            int x = 0;
            int jumpdistance = 0;
            while(x < direction.size() && direction.get(x) == 'R'){
                if (x+1<direction.size() && direction.get(x+1) == ' '){
                    jumpdistance+=2;
                }
                else{
                    if (jumpdistance<2) {
                        flag = true;
                    }
                }
                x+=2;
            }
            if (flag){
                continue;
            }

            int[] add = new int[3];
            add[0] = savei;
            add[1] = savej;
            add[2] = a;
            ret.add(add);
        }
        return ret;
    }

    public static void botmove(int[] currentPosConverted) {
        int movePos = currentPosConverted[2];

        int row = -1; // even or odd row
        if (currentPosConverted[0] % 2 == 0) {
            row = 2;
        }
        else if (currentPosConverted[0] % 2 == 1) {
            row = 1;
        }

        char temp = arraycopy[currentPosConverted[0]][currentPosConverted[1]];
        arraycopy[currentPosConverted[0]][currentPosConverted[1]] = ' ';  // delete current position
        
        // check if row is even or odd & increment (move)

        int[] movePosConverted = new int[2];
        if (movePos == 1){
            movePosConverted[0] = currentPosConverted[0]-1;
            movePosConverted[1] = currentPosConverted[1]-1;
        }
        else if (movePos == 2){
            movePosConverted[0] = currentPosConverted[0]-1;
            movePosConverted[1] = currentPosConverted[1]+1;
        }
        else if (movePos == 3){
            movePosConverted[0] = currentPosConverted[0]+1;
            movePosConverted[1] = currentPosConverted[1]+1;
        }
        else{
            movePosConverted[0] = currentPosConverted[0]+1;
            movePosConverted[1] = currentPosConverted[1]-1;
        }

        char check = 'R';
        ArrayList<Character> direction = new ArrayList<>();

        if (movePos == 2){
            int y = movePosConverted[1];
            for (int x = movePosConverted[0]; x >= 0; x--){
                if (x >= 0 && y >= 0 && x < 8 && y < 8){
                    direction.add(arraycopy[x][y]); 
                    y++;
                }
            }
        }
        else if (movePos == 1){
            int y = movePosConverted[1];
            for (int x = movePosConverted[0]; x >= 0; x--){
                if (x >= 0 && y >= 0 && x < 8 && y < 8){
                    direction.add(arraycopy[x][y]); 
                    y--;
                }
            }
        }
        else if (movePos == 3){
            int y = movePosConverted[1];
            for (int x = movePosConverted[0]; x <= 7; x++){
                if (x >= 0 && y >= 0 && x < 8 && y < 8){
                    direction.add(arraycopy[x][y]); 
                    y++;
                }
            }
        }
        else if (movePos == 4){
            int y = movePosConverted[1];
            for (int x = movePosConverted[0]; x <= 7; x++){
                if (x >= 0 && y >= 0 && x < 8 && y < 8){
                    direction.add(arraycopy[x][y]); 
                    y--;
                }
            }
        }
        
        int x = 0;
        int jumpdistance = 0;
        while(x < direction.size() && direction.get(x) == check){
            if (x+1<direction.size() && direction.get(x+1) == ' '){
                jumpdistance+=2;
            }
            x+=2;
        }

        if (jumpdistance > 0){
            if (movePos == 1){
                movePosConverted[0] = currentPosConverted[0] - jumpdistance;
                movePosConverted[1] = currentPosConverted[1] - jumpdistance;
                int counter = 1;
                for (int i = 0; i < jumpdistance/2; i++){
                    arraycopy[currentPosConverted[0] - counter][currentPosConverted[1] - counter] = ' ';
                    counter += 2;
                }
            }
            else if (movePos == 2){
                movePosConverted[0] = currentPosConverted[0] - jumpdistance;
                movePosConverted[1] = currentPosConverted[1] + jumpdistance;
                int counter = 1;
                for (int i = 0; i < jumpdistance/2; i++){
                    arraycopy[currentPosConverted[0] - counter][currentPosConverted[1] + counter] = ' ';
                    counter += 2;
                }
            }
            else if (movePos == 3){
                movePosConverted[0] = currentPosConverted[0] + jumpdistance;
                movePosConverted[1] = currentPosConverted[1] + jumpdistance;
                int counter = 1;
                for (int i = 0; i < jumpdistance/2; i++){
                    arraycopy[currentPosConverted[0] + counter][currentPosConverted[1] + counter] = ' ';
                    counter += 2;
                }
            }
            else if (movePos == 4){
                movePosConverted[0] = currentPosConverted[0] + jumpdistance;
                movePosConverted[1] = currentPosConverted[1] - jumpdistance;
                int counter = 1;
                for (int i = 0; i < jumpdistance/2; i++){
                    arraycopy[currentPosConverted[0] + counter][currentPosConverted[1] - counter] = ' ';
                    counter += 2;
                }
            }
        }

        arraycopy[movePosConverted[0]][movePosConverted[1]] = temp;
    }

    public static void arraycopyclear(){
        for (int i = 0; i < arraycopy.length; i++){
            for (int j = 0; j < arraycopy[i].length; j++){
                arraycopy[i][j] = '\0';
            }
        }
    }
}