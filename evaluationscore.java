public class evaluationscore {
    static char[][] board;
    static double evalScore; //positive favors red, negative favors white
    static int redkings;
    static int whitekings;

    public evaluationscore(char[][] board, int redkings, int whitekings){
        evaluationscore.board = board;
        evaluationscore.redkings = redkings;
        evaluationscore.whitekings = whitekings;
    }

    public void colors(){
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
        evalScore += red-white;
    }

    public void kings(){
        evalScore += redkings;
        evalScore -= whitekings;
    }

    public void depth(){
        int whitedepth = 0;
        int reddepth = 0;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (board[i][j] == 'W'){
                    whitedepth += i;
                }
            }
        }

        for (int i = 7; i >= 0; i--){
            for (int j = 0; j < 8; j++){
                if (board[i][j] == 'R'){
                    reddepth += 7-i;
                }
            }
        }

        double diff = reddepth - whitedepth;
        evalScore += diff * 0.25;
    }

    public double ret(){
        colors();
        kings();
        depth();
        return evalScore;
    }
}
