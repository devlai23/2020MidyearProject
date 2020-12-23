import java.util.*;

public class checkerbot {
    static char[][] board = new char[8][8];
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int white = 12;
        int red = 12;
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


        for (int i = 0; i < 8; i++){
            System.out.println(Arrays.toString(board[i]));
        }
        String str = s.nextLine();
        String[] splitted = str.split(" ");
        System.out.println(Arrays.toString(splitted));
    }

    public void move(int currentPos, int movePos) {
        // each space has a name
        
    }
}