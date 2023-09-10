package othello;

/**
 * Determines a white piece.
 */
class WhitePiece implements Piece {
    /**
     * Specifies the string presentation of a white piece.
     *
     * @return string presentation of a white piece
     */
    @Override
    public String toString() {
        return "○";
    }

    /**
     * Checks if current piece is equals to input piece. The logic is checking equality of classes of pieces.
     *
     * @param obj input piece to check equality
     * @return {@code true} if pieces are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass().equals(obj.getClass());
    }
}
