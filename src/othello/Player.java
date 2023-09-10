package othello;

/**
 * An othello player
 */
abstract class Player {
    /**
     * Player display name
     */
    private String name;
    /**
     * Player piece that can put on board
     */
    private Piece piece;

    /**
     * Constructor of player
     *
     * @param name  player name
     * @param piece player piece
     */
    Player(String name, Piece piece) {
        this.name = name;
        this.piece = piece;
    }

    /**
     * Gets player's piece.
     *
     * @return player's piece
     */
    Piece getPiece() {
        return piece;
    }

    /**
     * Gets display string of the player.
     *
     * @return display string of the player
     */
    @Override
    public String toString() {
        return name + " (" + getPiece().toString() + ")";
    }

    /**
     * Gets next move that player chooses.
     *
     * @return next move of player
     */
    public abstract String getNextMove();
}
