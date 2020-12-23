import java.util.*;

public class game {
    static char[][] board = new char[8][8];
    
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


        for (int i = 0; i < 8; i++){
            System.out.println(Arrays.toString(board[i]));
        }
        String str = s.nextLine();
        String[] splitted = str.split(" ");
        System.out.println(Arrays.toString(splitted));
        move(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]));

        
        s.close();
    }

    public static void move(int currentPos, int movePos){
        int currentx = (currentPos-1) / 4;
        int currenty = (currentPos-1) % 4;
        System.out.println("" + currentx + currenty);
        // int movex = (movePos-1) / 4;
        // int movey = (movePos-1) % 4;
        // char temp = board[currentx][currenty];
        // board[currentx][currenty] = '\0';
        // board[movex][movey] = temp;
    }
}