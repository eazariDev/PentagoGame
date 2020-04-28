/**
 * This class would manage the board of the pentago game
 * It would place beads in the board and rotate the boards.
 */
public class GameBoard {
    //An array which holds the place of the beads
    private int[][] gameMatrix;

    /**
     * The class constructor would set the default settings and it would create a board base on the size which user enters.
     * @param size It determine the size of the board.
     */
    public GameBoard(int size){
        gameMatrix = new int[size][size];
        for ( int i = 0 ; i < size ; i++)
            for (int j = 0 ; j < size ; j++)
                gameMatrix[i][j] = 0;
    }

    /**
     * This method would return the state of the determined place in the board
     * @param i It shows the vertical coordinate of the desired place
     * @param j It shows the horizontal coordinate of the desired place
     * @return It would return the state of the desired place
     */
    public int getGameMatrix(int i, int j ){
        return gameMatrix[i][j];
    }

    /**
     * This method would rotate the chosen board by player
     * @param fRow  It shows the starting point of the desired board's row
     * @param tRow  It shows the ending point of the desired board's row
     * @param fCol  It shows the starting point of the desired board's column
     * @param tCol  It shows the ending point of the desired board's column
     * @param state It would determined, whether the rotation is clockwise or anti-clockwise.
     */
    public void Rotation(int fRow, int tRow, int fCol, int tCol, String state){
        int[][] oldTemp = new int[3][3];
        int[][] newTemp = new int[3][3];
        for (int i = fRow, m = 0 ; i < tRow ; i++, m++)
            for ( int j = fCol, n = 0 ; j < tCol ; j++, n++)
                oldTemp[m][n] = gameMatrix[i][j];
        if(state.equals("clockwise")) {
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    newTemp[i][Math.abs(j - 2)] = oldTemp[j][i];
        }else{
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    newTemp[Math.abs(j-2)][i] = oldTemp[i][j];
        }
        for (int i = fRow, m = 0 ; i < tRow ; i++, m++)
            for ( int j = fCol, n = 0 ; j < tCol ; j++, n++)
                gameMatrix[i][j] = newTemp[m][n];
    }

    /**
     * This method would check whether the user's inputs are valid or not.
     * @param x It shows the vertical coordinate of the move
     * @param y It shows the horizontal coordinate of the move
     * @return  It would returns true, if it is a valid move and returns false if the move is not valid
     */
    public Boolean validMove(int x, int y){
        if  ( x <= gameMatrix.length && x > 0 && y > 0 && y <= gameMatrix.length){
            if ( gameMatrix[x-1][y-1] == 0)
                return true;
        }
        System.out.println("Invalid move!\nPlease enter again: ");
        return false;
    }

    /**
     * This method would set the new bead in the board
     * @param player    It shows which player sets the new bead
     * @param x It shows the vertical coordinate of the new bead
     * @param y It shows the horizontal coordinate of the new bead
     */
    public void setMove(int player, int x, int y){
        gameMatrix[x-1][y-1] = player;
    }

    /**
     * It would check if there is any board without any beats, and if there is, player can choose not to rotate any board
     * @param fRow  It shows the ending point of the desired board's row
     * @param tRow  It shows the ending point of the desired board's row
     * @param fCol  It shows the ending point of the desired board's row
     * @param tCol  It shows the ending point of the desired board's row
     * @return  True if there is a board without any beats yet and false if there is no more board without beat
     */
    public Boolean checkBoards(int fRow, int tRow, int fCol, int tCol){
        for (int i = fRow ; i < tRow ; i++)
            for ( int j = fCol ; j < tCol ; j++)
                if (gameMatrix[i][j] != 0)
                    return false;

        return true;
    }

    /**
     * It would check if there is any places remain for new beads.
     * @return  True if there is more places for new beads and false if there is no more places left.
     */
    public Boolean remainedPlaces(){
        for (int i = 0 ; i < gameMatrix.length ; i++)
            for (int j = 0 ; j < gameMatrix.length ; j++)
                if ( gameMatrix[i][j] == 0)
                    return true;
        return false;
    }

    /**
     * This method would check game for any possible winner.
     * @param player The player to check.
     * @return  Returns if the player wins the game and return 0 if no player wins yet.
     */
    public int checkForWinner(int player){
        for (int i = 0 ; i < gameMatrix.length ; i++){
            for ( int j = 0 ; j < gameMatrix.length ; j++) {
                if ( gameMatrix[i][j] == player) {
                    if (beadCounter(-1, -1, i, j, player) == 5)
                        return 1;
                    if (beadCounter(-1, 0, i, j, player) == 5)
                        return 1;
                    if (beadCounter(-1, 1, i, j, player) == 5)
                        return 1;
                    if (beadCounter(0, 1, i, j, player) == 5)
                        return 1;
                    if (beadCounter(1, 1, i, j, player) == 5)
                        return 1;
                    if (beadCounter(1, 0, i, j, player) == 5)
                        return 1;
                    if (beadCounter(1, -1, i, j, player) == 5)
                        return 1;
                    if (beadCounter(0, -1, i, j, player) == 5)
                        return 1;
                }
            }
        }
        return 0;
    }

    /**
     * This method would count the number of beads in a row, a column and diagonals
     * @param rBase The vertical base to move
     * @param cBase The horizontal base to move
     * @param r The vertical coordinate of the chosen beat to check
     * @param c The horizontal coordinate of the chosen beat to check
     * @param player    The player to check beats for.
     * @return  Returns the number of same color beats in a row, a column and diagonals.
     */
    public int beadCounter(int rBase, int cBase, int r, int c, int player){
        int counter = 0 ;
            while(r + rBase > -1 && r + rBase < gameMatrix.length && c + cBase > -1 && c + cBase < gameMatrix.length && gameMatrix[r][c] == player){
                r += rBase;
                c += cBase;
                counter++;
            }
        return counter;
    }
}
