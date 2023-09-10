package othello;

import java.util.Scanner;

/**
 * Determines a simple human player
 */
class HumanPlayer extends Player {
    /**
     * Constructor of player
     *
     * @param name  player name
     * @param piece player piece
     */
    HumanPlayer(String name, Piece piece) {
        super(name, piece);
    }

    /**
     * Gets next move as a string from console and returns that input as player's next move.
     *
     * @return input move string
     */
    public String getNextMove() {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        return line.trim();
    }
}
