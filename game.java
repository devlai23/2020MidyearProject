import java.util.*;

public class game {
    static char[][] board = new char[8][8];
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
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

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
        System.out.println("Welcome to CheckerBot");


        printBoard();
        String str = s.nextLine();
        String[] splitted = str.split(" ");
        move(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1])); // move the selected position (first number) in direction (second number)
        printBoard();

        
        s.close();
    }

    public static void move(int currentPos, int movePos){
        int row = -1; // move the selected position (first number) in direction (second number)
        int mod = currentPos % 8;
        if (mod == 1 || mod == 2 || mod == 3 || mod == 4){ // see if the position is in an odd row
            row = 1;
        }
        else if (mod == 5 || mod == 6 || mod == 7 || mod == 0){ // see if the position is in an even row
            row = 2;
        }

        int[] currentPosConverted = convert(currentPos);  // this will call convert, which converts currentPos into a...
        char temp = board[currentPosConverted[0]][currentPosConverted[1]]; // ...coordinate for the original "board" array to access 
        board[currentPosConverted[0]][currentPosConverted[1]] = '\0';  // this will "delete" the current checker because it's being moved (set it to null)
        
        // the following conditional statements check if the row is even or odd, and increment the current position by a different number to have the correct move
        // later (Tuesday) -- work on exceptions/invalid moves and how to handle them
        if (movePos == 1 && row == 1){
            currentPos -= 4;
        }
        else if (movePos == 1 && row == 2){
            currentPos -= 5;
        }
        else if (movePos == 2 && row == 1){
            currentPos -= 3;
        }
        else if (movePos == 2 && row == 2){
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
        int[] movePosConverted = convert(currentPos); // once the checker has been moved correctly according to the currentPos number system,
        board[movePosConverted[0]][movePosConverted[1]] = temp; // we can implement this on the actual board the game is being played on.
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

    public static void printBoard(){
        for (int i = 0; i < 8; i++){
            System.out.println(Arrays.toString(board[i]));
        }
    }
}