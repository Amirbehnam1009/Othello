package othello;

import java.util.Scanner;

/**
 * The Othello game class that performs the othello game.
 */
public class Othello {
    /**
     * A two member array that holds the game players, first member is player1 and second member is player2
     */
    private Player[] players;
    /**
     * Current turn of the game
     */
    private Player turn;
    /**
     * The board of the game, all movements and rules will be applied to this board.
     */
    private Board board;

    /**
     * Constructor of the othello.
     */
    private Othello() {
        players = new Player[2];
    }

    /**
     * The main method of the othello game.
     *
     * @param args program arguments
     */
    public static void main(String[] args) {
        Othello othello = new Othello();
        othello.play();
    }

    /**
     * Operates as the playing process of the othello game is implemented here. It asks for determining te game type by
     * selecting from one player (play with computer) and two player (play with opponent) and handles moves and board
     * state and game rules and regulations and Finally determines the winner (or draw state).
     */
    private void play() {
        String game = selectGameMode();
        while (!game.equals("0")) {
            board = new Board();
            switch (game) {
                case "2":
                    playHumanToHuman();
                    break;
                case "1":
                    playHumanToComputer();
                    break;
                default:
                    System.out.println("Invalid game mode, try again");
            }
            System.out.println();
            game = selectGameMode();
        }
    }

    /**
     * Gets game mode from user and returns it.
     *
     * @return game mode
     */
    private String selectGameMode() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select game mode,");
        System.out.println("0. Exit");
        System.out.println("1. One player (play with computer)");
        System.out.println("2. Two player (play with opponent)");
        return scanner.nextLine().trim();
    }

    /**
     * Performs two player game mode that is playing human to human.
     */
    private void playHumanToHuman() {
        players[0] = new HumanPlayer("Player1", Board.BLACK_PIECE);
        players[1] = new HumanPlayer("Player2", Board.WHITE_PIECE);
        turn = players[0];
        doPlaying();
    }

    /**
     * The game cycle will be handled here.
     */
    private void doPlaying() {
        boolean end = false;
        while (!end) {
            printBoard();
            printTurn();
            String nextMove = turn.getNextMove();
            //gets next move from user until input string has valid syntax
            while (isInvalidMoveString(nextMove)) {
                System.out.println("Input format must be like \"I C\", I is a number in range [1-8] and C is a character in range [A-H].");
                nextMove = turn.getNextMove();
            }
            int[] indexes = convertNextMoveToBoardIndex(nextMove);
            //gets next move from user until input string is a valid move on board
            while (!board.isValidMove(turn.getPiece(), indexes[0], indexes[1])) {
                System.out.println(turn.toString() + " can't have \"" + nextMove + "\" move, please choose a valid move.");
                nextMove = turn.getNextMove();
                //gets next move from user until input string has valid syntax
                while (isInvalidMoveString(nextMove)) {
                    System.out.println("Input format must be like \"I C\", I is a number in range [1-8] and C is a character in range [A-H].");
                    nextMove = turn.getNextMove();
                }
                indexes = convertNextMoveToBoardIndex(nextMove);
            }
            //apply next move to the board
            board.putPiece(turn.getPiece(), indexes[0], indexes[1]);
            //checks that game is finished after applying the move
            if (board.isGameFinished()) {
                printBoard();
                win();
                end = true;
            } else {
                changeTurn();
            }
        }
    }

    /**
     * Prints the current board shape.
     */
    private void printBoard() {
        board.print();
    }

    /**
     * Print current player turn before asking for selected move of current player.
     */
    private void printTurn() {
        System.out.println(turn.toString() + ":");
    }

    /**
     * Validates syntax of the move string that entered be user. The input string format is "I C" that 'I' part is a
     * number in range 1 to 8 and 'C' part is an upper case character in range A to H.
     *
     * @param moveString validating move string
     * @return {@code true} if the move string is valid, {@code false} otherwise
     */
    private boolean isInvalidMoveString(String moveString) {
        char row;
        char column;
        //input format should be 3 character in format "I C"
        if (moveString.length() != 3) {
            return true;
        }
        row = moveString.charAt(0);
        column = moveString.charAt(2);
        if (row != '1' && row != '2' && row != '3' && row != '4' && row != '5' && row != '6' && row != '7' && row != '8') {
            return true;
        }
        if (moveString.charAt(1) != ' ') {
            return true;
        }
        if (column != 'A' && column != 'B' && column != 'C' && column != 'D' && column != 'E' && column != 'F' && column != 'G' && column != 'H') {
            return true;
        }
        return false;
    }

    /**
     * Converts the move string to row and column indexes in the board. The input string format is "I C" that 'I' part
     * is a number in range 1 to 8 and 'C' part is an upper case character in range A to H. Computed row and column
     * indexes are both numbers in the range 0 to 7.
     *
     * @param nextMove move string
     * @return an array with two members, 0 index indicates computed row and 1 index indicates computed column
     */
    private int[] convertNextMoveToBoardIndex(String nextMove) {
        int[] result = new int[2];
        result[0] = Character.getNumericValue(nextMove.charAt(0)) - 1;
        result[1] = nextMove.charAt(2) - 65;
        return result;
    }

    /**
     * If there is no other valid moves, this method determines the winner and prints the game results.
     */
    private void win() {
        int blackPieces = board.getColorCount(Board.BLACK_PIECE);
        int whitePieces = board.getColorCount(Board.WHITE_PIECE);
        System.out.println(players[0].toString() + ": " + blackPieces + ", " + players[1] + ": " + whitePieces);
        if (blackPieces > whitePieces) {
            System.out.println(players[0] + " Wins");
        } else if (blackPieces < whitePieces) {
            System.out.println(players[1] + " Wins");
        } else {
            System.out.println("Draw!!");
        }
    }

    /**
     * Determines next turn of the game and changes {@link Othello#turn} respectively. First, gives turn to the other
     * player. If there is no valid move to new player, it passes its turn and gives turn to current player,
     * and otherwise new player can choose a new move.
     */
    private void changeTurn() {
        //give turn to other user
        if (turn.equals(players[0])) {
            turn = players[1];
        } else {
            turn = players[0];
        }
        //checks the other user has valid move or not
        if (board.hasNoValidMoves(turn.getPiece())) {
            printBoard();
            //pass if there is no valid move for current user
            printTurn();
            System.out.println("Pass");
            if (turn.equals(players[0])) {
                turn = players[1];
            } else {
                turn = players[0];
            }
        }
    }

    /**
     * Performs two player game mode that is playing human to computer. The player1 will be human player
     * and the player2 will be computer player.
     */
    private void playHumanToComputer() {
        players[0] = new HumanPlayer("Player1", new BlackPiece());
        players[1] = new ComputerPlayer(board);
        turn = players[0];
        doPlaying();
    }
}
