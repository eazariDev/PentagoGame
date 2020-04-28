/**
 * This class designed to manage the whole pentago game.
 *
 * @author Erfan. Azari
 */

import java.util.Scanner;
public class GameManager {
    Scanner input = new Scanner(System.in);

    //It determine whose turn is to play
    private int currentPlayer;
    //It would create a new board for beads for a new game
    private GameBoard board1;

    /**
     * The constructor of the class would create a new pentago game with a new board.
     * @param currentPlayer Determine who is the first player to play the game.
     * @param board1 It is the board of the new game and it would manage the beads and moves.
     */
    public GameManager(){
        currentPlayer = -1;
        board1 = new GameBoard(6);

    }

    /**
     * This method would start the game and in each step, it would call other methods inside the class to make game progress.
     *
     */
    public void gameStart(){
        int xCoor, yCoor;
        do{
            //The printGame method would be called to print the board of game in the console
            printGame();
            currentPlayer *= -1;
            if ( currentPlayer == 1)
                System.out.println("Player1, it's your turn!\nWhere do you want to put your bead: ");
            else
                System.out.println("Player2, it's your turn!\nWhere do you want to put your bead: ");
            do{
                xCoor = input.nextInt();
                yCoor = input.nextInt();
            }while(!board1.validMove(xCoor, yCoor)); //The validMove method in board class would be called to check if the move is allowed or not.
            board1.setMove(currentPlayer, xCoor, yCoor);
            //The rotation method inside class would be called to rotate the boards base on user choices.
            rotation();
        }while(!gameIsOver()); //This method would check each time to see if game ends or not.
        //This method would be called, when the game ends and it would show the result of the game.
        showResult();
    }

    /**
     * This method would check if after each round, game ends or not. The conditions for a game to be ended are:
     *  - Either one of player have 5 beats in a row or column.
     *  - There won't bo any more places for placing new beats
     * @return Return true, if the game ends and return false if game can be continued.
     */
    public Boolean gameIsOver(){
        if ( !board1.remainedPlaces() || board1.checkForWinner(1) != 0 || board1.checkForWinner(-1) != 0)
            return true;
        return false;
    }

    /**
     * This method would print the game in the console to see whole state of the game.
     */
    public void printGame(){
        System.out.println("\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585");
        for (int i = 0 ; i < 6 ; i++){
            for (int j = 0 ; j < 6 ; j++){
                if ( i == 3 && j == 0)
                    System.out.println("\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585");
                if ( j == 3 )
                    System.out.print("\u258B" + "  ");
                if ( j == 0 )
                    System.out.print("\u258B" + "  ");
                if (board1.getGameMatrix(i,j) == 0 ) {
                    System.out.print(Color.WHITE_BOLD);
                    System.out.print("\u2B24" + "  ");
                    System.out.print(Color.RESET);
                }else if (board1.getGameMatrix(i,j) == 1) {
                    System.out.print(Color.BLACK);
                    System.out.print("\u2B24" + "  ");
                    System.out.print(Color.RESET);
                }else {
                    System.out.print(Color.RED);
                    System.out.print("\u2B24" + "  ");
                    System.out.print(Color.RESET);
                }
                if ( j == 5)
                    System.out.print("\u258B" + " ");

            }
            System.out.println();
        }
        System.out.println("\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585" + " " + "\u2585");
    }

    /**
     * Thie method would show the result at the end of the game and determines which player ahs won the game.
     */
    public void showResult(){
        if ( board1.checkForWinner(1) == 1 && board1.checkForWinner(-1) == 1)
            System.out.println("No winner!\nGame is draw!");
        else if ( board1.checkForWinner(1) == 1)
            System.out.println("Player1 won the game!\nCongratulation player1!");
        else if ( board1.checkForWinner(-1) == 1)
            System.out.println("Player2 won the game!\nCongratulation player2!");

    }

    /**
     * This method would do the rotation of the boards based on the player choices.
     */
    public void rotation(){
        //The block to be rotated.
        int blockToRotate;
        String rotationState;
        if ( board1.checkBoards(0, 3, 0, 3) || board1.checkBoards(0, 3, 3, 6) || board1.checkBoards(3,6,0,3) || board1.checkBoards(3,6,3,6)) {
            System.out.println("Do you want to rotate any blocks?(yes/no) ");
            if (input.next().equals("no"))
                return;
        }
        System.out.println("Which block and how do you want it to be rotated[1,2,3,4], [clockwise or anticlockwise]? ");
        blockToRotate = input.nextInt();
        rotationState = input.next();

            //Base on the player choice, it would call a method in Board class, to rotate the specific board.
            switch (blockToRotate){
                case 1:
                    board1.Rotation(0,3,0,3,rotationState);
                    break;
                case 2:
                    board1.Rotation(0,3,3,6,rotationState);
                    break;
                case 3:
                    board1.Rotation(3,6,0,3,rotationState);
                    break;
                case 4:
                    board1.Rotation(3,6,3,6,rotationState);
                    break;
            }

    }

    /**
     * This enum is used from StackOverFlow to change the color of texts.
     */
    enum Color {
        //Color end string, color reset
        RESET("\033[0m"),
        // Regular Colors. Normal color, no bold, background color etc.
        BLACK("\033[0;30m"),    // BLACK
        RED("\033[0;31m"),      // RED
        GREEN("\033[0;32m"),    // GREEN
        YELLOW("\033[0;33m"),   // YELLOW
        BLUE("\033[0;34m"),     // BLUE
        MAGENTA("\033[0;35m"),  // MAGENTA
        CYAN("\033[0;36m"),     // CYAN
        WHITE("\033[0;37m"),    // WHITE

        // Bold
        BLACK_BOLD("\033[1;30m"),   // BLACK
        RED_BOLD("\033[1;31m"),     // RED
        GREEN_BOLD("\033[1;32m"),   // GREEN
        YELLOW_BOLD("\033[1;33m"),  // YELLOW
        BLUE_BOLD("\033[1;34m"),    // BLUE
        MAGENTA_BOLD("\033[1;35m"), // MAGENTA
        CYAN_BOLD("\033[1;36m"),    // CYAN
        WHITE_BOLD("\033[1;37m"),   // WHITE

        // Underline
        BLACK_UNDERLINED("\033[4;30m"),     // BLACK
        RED_UNDERLINED("\033[4;31m"),       // RED
        GREEN_UNDERLINED("\033[4;32m"),     // GREEN
        YELLOW_UNDERLINED("\033[4;33m"),    // YELLOW
        BLUE_UNDERLINED("\033[4;34m"),      // BLUE
        MAGENTA_UNDERLINED("\033[4;35m"),   // MAGENTA
        CYAN_UNDERLINED("\033[4;36m"),      // CYAN
        WHITE_UNDERLINED("\033[4;37m"),     // WHITE

        // Background
        BLACK_BACKGROUND("\033[40m"),   // BLACK
        RED_BACKGROUND("\033[41m"),     // RED
        GREEN_BACKGROUND("\033[42m"),   // GREEN
        YELLOW_BACKGROUND("\033[43m"),  // YELLOW
        BLUE_BACKGROUND("\033[44m"),    // BLUE
        MAGENTA_BACKGROUND("\033[45m"), // MAGENTA
        CYAN_BACKGROUND("\033[46m"),    // CYAN
        WHITE_BACKGROUND("\033[47m"),   // WHITE

        // High Intensity
        BLACK_BRIGHT("\033[0;90m"),     // BLACK
        RED_BRIGHT("\033[0;91m"),       // RED
        GREEN_BRIGHT("\033[0;92m"),     // GREEN
        YELLOW_BRIGHT("\033[0;93m"),    // YELLOW
        BLUE_BRIGHT("\033[0;94m"),      // BLUE
        MAGENTA_BRIGHT("\033[0;95m"),   // MAGENTA
        CYAN_BRIGHT("\033[0;96m"),      // CYAN
        WHITE_BRIGHT("\033[0;97m"),     // WHITE

        // Bold High Intensity
        BLACK_BOLD_BRIGHT("\033[1;90m"),    // BLACK
        RED_BOLD_BRIGHT("\033[1;91m"),      // RED
        GREEN_BOLD_BRIGHT("\033[1;92m"),    // GREEN
        YELLOW_BOLD_BRIGHT("\033[1;93m"),   // YELLOW
        BLUE_BOLD_BRIGHT("\033[1;94m"),     // BLUE
        MAGENTA_BOLD_BRIGHT("\033[1;95m"),  // MAGENTA
        CYAN_BOLD_BRIGHT("\033[1;96m"),     // CYAN
        WHITE_BOLD_BRIGHT("\033[1;97m"),    // WHITE

        // High Intensity backgrounds
        BLACK_BACKGROUND_BRIGHT("\033[0;100m"),     // BLACK
        RED_BACKGROUND_BRIGHT("\033[0;101m"),       // RED
        GREEN_BACKGROUND_BRIGHT("\033[0;102m"),     // GREEN
        YELLOW_BACKGROUND_BRIGHT("\033[0;103m"),    // YELLOW
        BLUE_BACKGROUND_BRIGHT("\033[0;104m"),      // BLUE
        MAGENTA_BACKGROUND_BRIGHT("\033[0;105m"),   // MAGENTA
        CYAN_BACKGROUND_BRIGHT("\033[0;106m"),      // CYAN
        WHITE_BACKGROUND_BRIGHT("\033[0;107m");     // WHITE

        private final String code;

        Color(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }
}
