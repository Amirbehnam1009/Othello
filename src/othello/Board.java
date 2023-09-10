package othello;

import java.util.ArrayList;
import java.util.List;

/**
 * Board class to manage board of othello game. This class manages current state of game board and takes all actions
 * that take place when a move occurs.
 */
class Board {
    /**
     * White piece state of board cell
     */
    final static Piece WHITE_PIECE = new WhitePiece();
    /**
     * Black piece state of board cell
     */
    final static Piece BLACK_PIECE = new BlackPiece();
    /**
     * An 8 by 8 array that keeps current board state
     */
    private Cell[][] boardState = new Cell[8][8];

    /**
     * Constructor to initialize board state at the beginning of the game. The start state determines as bellow:
     * <br>
     * All cells of the board will be initialized to empty, except four central cells that will contain two whites and
     * two blacks.
     */
    Board() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardState[i][j] = new Cell();
            }
        }
        boardState[3][3].putPiece(WHITE_PIECE);
        boardState[4][4].putPiece(WHITE_PIECE);
        boardState[3][4].putPiece(BLACK_PIECE);
        boardState[4][3].putPiece(BLACK_PIECE);
    }

    /**
     * Prints current state of board.
     */
    void print() {
        System.out.println("     A     B     C     D     E     F     G     H");
        for (int i = 0; i < 8; i++) {
            System.out.println("  ------------------------------------");
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 8; j++) {
                System.out.print("| " + boardState[i][j].toString() + " ");
            }
            System.out.println("|");
        }
        System.out.println("  ------------------------------------");
    }

    /**
     * Checks the validity of putting input piece piece into the cell with row and column index.
     * <br>
     * The move is valid only if the cell pointer by row and column index was empty and there exists at least
     * one straight (horizontal, vertical, or diagonal) occupied line between the new piece and another same colored piece,
     * with one or more contiguous opposite piece pieces between them.
     *
     * @param piece  piece of piece to check validity of move
     * @param row    row index of the move
     * @param column column index of the move
     * @return {@code true} if the move is valid, {@code false} otherwise
     */
    boolean isValidMove(Piece piece, int row, int column) {
        if (!boardState[row][column].isEmpty()) {
            return false;
        }
        if (checkDown(piece, row, column) || checkUp(piece, row, column) ||
                checkLeft(piece, row, column) || checkRight(piece, row, column) ||
                checkUpLeft(piece, row, column) || checkUpRight(piece, row, column) ||
                checkDownLeft(piece, row, column) || checkDownRight(piece, row, column)) {
            return true;
        }
        return false;
    }

    /**
     * Checks validity of move only at the left (west) horizontal direction.
     *
     * @param piece  piece of piece to check validity of move
     * @param row    row index of the move
     * @param column column index of the move
     * @return {@code true} if the move is valid, {@code false} otherwise
     */
    private boolean checkLeft(Piece piece, int row, int column) {
        boolean foundOppositeColor = false;
        Cell currentCell;
        //Should find all opposite colors in straight line until reaches same piece
        for (int i = column - 1; i >= 0; i--) {
            currentCell = boardState[row][i];
            //if before finding opposites until same piece found an empty cell, the situation is not valid
            if (currentCell.isEmpty()) {
                return false;
            }
            //first should find an opposite piece
            if (!foundOppositeColor) {
                if (!currentCell.isSameColor(piece)) {
                    foundOppositeColor = true;
                } else { //if finds same piece before finding opposite piece, this situation is not valid
                    return false;
                }
            } else {
                //finding same piece after finding opposite piece is a valid situation
                if (currentCell.isSameColor(piece)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks validity of move only at the right (east) horizontal direction.
     *
     * @param piece  piece of piece to check validity of move
     * @param row    row index of the move
     * @param column column index of the move
     * @return {@code true} if the move is valid, {@code false} otherwise
     */
    private boolean checkRight(Piece piece, int row, int column) {
        Cell currentCell;
        boolean foundOppositeColor = false;
        //Should find all opposite colors in straight line until reaches same piece
        for (int i = column + 1; i < 8; i++) {
            currentCell = boardState[row][i];
            //if before finding opposites until same piece found an empty cell, the situation is not valid
            if (currentCell.isEmpty()) {
                return false;
            }
            //first should find an opposite piece
            if (!foundOppositeColor) {
                if (!currentCell.isSameColor(piece)) {
                    foundOppositeColor = true;
                } else { //if finds same piece before finding opposite piece, this situation is not valid
                    return false;
                }
            } else {
                //finding same piece after finding opposite piece is a valid situation
                if (currentCell.isSameColor(piece)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks validity of move only at the up (north) vertical direction.
     *
     * @param piece  piece of piece to check validity of move
     * @param row    row index of the move
     * @param column column index of the move
     * @return {@code true} if the move is valid, {@code false} otherwise
     */
    private boolean checkUp(Piece piece, int row, int column) {
        Cell currentCell;
        boolean foundOppositeColor = false;
        //Should find all opposite colors in straight line until reaches same piece
        for (int i = row - 1; i >= 0; i--) {
            currentCell = boardState[i][column];
            //if before finding opposites until same piece found an empty cell, the situation is not valid
            if (currentCell.isEmpty()) {
                return false;
            }
            //first should find an opposite piece
            if (!foundOppositeColor) {
                if (!currentCell.isSameColor(piece)) {
                    foundOppositeColor = true;
                } else { //if finds same piece before finding opposite piece, this situation is not valid
                    return false;
                }
            } else {
                //finding same piece after finding opposite piece is a valid situation
                if (currentCell.isSameColor(piece)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks validity of move only at the down (south) vertical direction.
     *
     * @param piece  piece of piece to check validity of move
     * @param row    row index of the move
     * @param column column index of the move
     * @return {@code true} if the move is valid, {@code false} otherwise
     */
    private boolean checkDown(Piece piece, int row, int column) {
        Cell currentCell;
        boolean foundOppositeColor = false;
        //Should find all opposite colors in straight line until reaches same piece
        for (int i = row + 1; i < 8; i++) {
            currentCell = boardState[i][column];
            //if before finding opposites until same piece found an empty cell, the situation is not valid
            if (currentCell.isEmpty()) {
                return false;
            }
            //first should find an opposite piece
            if (!foundOppositeColor) {
                if (!currentCell.isSameColor(piece)) {
                    foundOppositeColor = true;
                } else { //if finds same piece before finding opposite piece, this situation is not valid
                    return false;
                }
            } else {
                //finding same piece after finding opposite piece is a valid situation
                if (currentCell.isSameColor(piece)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks validity of move only at the up-left (north-west) diagonal direction.
     *
     * @param piece  piece of piece to check validity of move
     * @param row    row index of the move
     * @param column column index of the move
     * @return {@code true} if the move is valid, {@code false} otherwise
     */
    private boolean checkUpLeft(Piece piece, int row, int column) {
        Cell currentCell;
        boolean foundOppositeColor = false;
        //Should find all opposite colors in straight line until reaches same piece
        for (int i = row - 1, j = column - 1; i >= 0 && j >= 0; i--, j--) {
            currentCell = boardState[i][j];
            //if before finding opposites until same piece found an empty cell, the situation is not valid
            if (currentCell.isEmpty()) {
                return false;
            }
            //first should find an opposite piece
            if (!foundOppositeColor) {
                if (!currentCell.isSameColor(piece)) {
                    foundOppositeColor = true;
                } else { //if finds same piece before finding opposite piece, this situation is not valid
                    return false;
                }
            } else {
                //finding same piece after finding opposite piece is a valid situation
                if (currentCell.isSameColor(piece)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks validity of move only at the up-right (north-east) diagonal direction.
     *
     * @param piece  piece of piece to check validity of move
     * @param row    row index of the move
     * @param column column index of the move
     * @return {@code true} if the move is valid, {@code false} otherwise
     */
    private boolean checkUpRight(Piece piece, int row, int column) {
        Cell currentCell;
        boolean foundOppositeColor = false;
        //Should find all opposite colors in straight line until reaches same piece
        for (int i = row - 1, j = column + 1; i >= 0 && j < 8; i--, j++) {
            currentCell = boardState[i][j];
            //if before finding opposites until same piece found an empty cell, the situation is not valid
            if (currentCell.isEmpty()) {
                return false;
            }
            //first should find an opposite piece
            if (!foundOppositeColor) {
                if (!currentCell.isSameColor(piece)) {
                    foundOppositeColor = true;
                } else { //if finds same piece before finding opposite piece, this situation is not valid
                    return false;
                }
            } else {
                //finding same piece after finding opposite piece is a valid situation
                if (currentCell.isSameColor(piece)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks validity of move only at the down-left (south-west) diagonal direction.
     *
     * @param piece  piece of piece to check validity of move
     * @param row    row index of the move
     * @param column column index of the move
     * @return {@code true} if the move is valid, {@code false} otherwise
     */
    private boolean checkDownLeft(Piece piece, int row, int column) {
        Cell currentCell;
        boolean foundOppositeColor = false;
        //Should find all opposite colors in straight line until reaches same piece
        for (int i = row + 1, j = column - 1; i < 8 && j >= 0; i++, j--) {
            currentCell = boardState[i][j];
            //if before finding opposites until same piece found an empty cell, the situation is not valid
            if (currentCell.isEmpty()) {
                return false;
            }
            //first should find an opposite piece
            if (!foundOppositeColor) {
                if (!currentCell.isSameColor(piece)) {
                    foundOppositeColor = true;
                } else { //if finds same piece before finding opposite piece, this situation is not valid
                    return false;
                }
            } else {
                //finding same piece after finding opposite piece is a valid situation
                if (currentCell.isSameColor(piece)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks validity of move only at the down-right (south-east) diagonal direction.
     *
     * @param piece  piece of piece to check validity of move
     * @param row    row index of the move
     * @param column column index of the move
     * @return {@code true} if the move is valid, {@code false} otherwise
     */
    private boolean checkDownRight(Piece piece, int row, int column) {
        Cell currentCell;
        boolean foundOppositeColor = false;
        //Should find all opposite colors in straight line until reaches same piece
        for (int i = row + 1, j = column + 1; i < 8 && j < 8; i++, j++) {
            currentCell = boardState[i][j];
            //if before finding opposites until same piece found an empty cell, the situation is not valid
            if (currentCell.isEmpty()) {
                return false;
            }
            //first should find an opposite piece
            if (!foundOppositeColor) {
                if (!currentCell.isSameColor(piece)) {
                    foundOppositeColor = true;
                } else { //if finds same piece before finding opposite piece, this situation is not valid
                    return false;
                }
            } else {
                //finding same piece after finding opposite piece is a valid situation
                if (currentCell.isSameColor(piece)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * After placing the new piece, this method reverses all opposite piece pieces lying on a straight line
     * between the new piece and any anchoring pieces with same piece at the right (east) horizontal direction.
     *
     * @param piece  piece of new piece
     * @param row    row index of the move
     * @param column column index of the move
     */
    private void reversRight(Piece piece, int row, int column) {
        Cell currentCell;
        if (checkRight(piece, row, column)) {
            int i = column + 1;
            currentCell = boardState[row][i];
            //reversing pieces until receives to same colored piece
            while (!currentCell.isSameColor(piece)) {
                boardState[row][i].putPiece(piece);
                i++;
                currentCell = boardState[row][i];
            }
        }
    }

    /**
     * After placing the new piece, this method reverses all opposite piece pieces lying on a straight line
     * between the new piece and any anchoring pieces with same piece at the left (west) horizontal direction.
     *
     * @param piece  piece of new piece
     * @param row    row index of the move
     * @param column column index of the move
     */
    private void reverseLeft(Piece piece, int row, int column) {
        Cell currentCell;
        if (checkLeft(piece, row, column)) {
            int i = column - 1;
            currentCell = boardState[row][i];
            //reversing pieces until receives to same colored piece
            while (!currentCell.isSameColor(piece)) {
                boardState[row][i].putPiece(piece);
                i--;
                currentCell = boardState[row][i];
            }
        }
    }

    /**
     * After placing the new piece, this method reverses all opposite piece pieces lying on a straight line
     * between the new piece and any anchoring pieces with same piece at the up (north) vertical direction.
     *
     * @param piece  piece of new piece
     * @param row    row index of the move
     * @param column column index of the move
     */
    private void reverseUp(Piece piece, int row, int column) {
        Cell currentCell;
        if (checkUp(piece, row, column)) {
            int i = row - 1;
            currentCell = boardState[i][column];
            //reversing pieces until receives to same colored piece
            while (!currentCell.isSameColor(piece)) {
                boardState[i][column].putPiece(piece);
                i--;
                currentCell = boardState[i][column];
            }
        }
    }

    /**
     * After placing the new piece, this method reverses all opposite piece pieces lying on a straight line
     * between the new piece and any anchoring pieces with same piece at the down (south) vertical direction.
     *
     * @param piece  piece of new piece
     * @param row    row index of the move
     * @param column column index of the move
     */
    private void reverseDown(Piece piece, int row, int column) {
        Cell currentCell;
        if (checkDown(piece, row, column)) {
            int i = row + 1;
            currentCell = boardState[i][column];
            //reversing pieces until receives to same colored piece
            while (!currentCell.isSameColor(piece)) {
                boardState[i][column].putPiece(piece);
                i++;
                currentCell = boardState[i][column];
            }
        }
    }

    /**
     * After placing the new piece, this method reverses all opposite piece pieces lying on a straight line
     * between the new piece and any anchoring pieces with same piece at the up-left (north-west) diagonal direction.
     *
     * @param piece  piece of new piece
     * @param row    row index of the move
     * @param column column index of the move
     */
    private void reverseUpLeft(Piece piece, int row, int column) {
        Cell currentCell;
        if (checkUpLeft(piece, row, column)) {
            int i = row - 1;
            int j = column - 1;
            currentCell = boardState[i][j];
            //reversing pieces until receives to same colored piece
            while (!currentCell.isSameColor(piece)) {
                boardState[i][j].putPiece(piece);
                i--;
                j--;
                currentCell = boardState[i][j];
            }
        }
    }

    /**
     * After placing the new piece, this method reverses all opposite piece pieces lying on a straight line
     * between the new piece and any anchoring pieces with same piece at the up-right (north-east) diagonal direction.
     *
     * @param piece  piece of new piece
     * @param row    row index of the move
     * @param column column index of the move
     */
    private void reverseUpRight(Piece piece, int row, int column) {
        Cell currentCell;
        if (checkUpRight(piece, row, column)) {
            int i = row - 1;
            int j = column + 1;
            currentCell = boardState[i][j];
            //reversing pieces until receives to same colored piece
            while (!currentCell.isSameColor(piece)) {
                boardState[i][j].putPiece(piece);
                i--;
                j++;
                currentCell = boardState[i][j];
            }
        }
    }

    /**
     * After placing the new piece, this method reverses all opposite piece pieces lying on a straight line
     * between the new piece and any anchoring pieces with same piece at the down-right (south-east) diagonal direction.
     *
     * @param piece  piece of new piece
     * @param row    row index of the move
     * @param column column index of the move
     */
    private void reverseDownRight(Piece piece, int row, int column) {
        Cell currentCell;
        if (checkDownRight(piece, row, column)) {
            int i = row + 1;
            int j = column + 1;
            currentCell = boardState[i][j];
            //reversing pieces until receives to same colored piece
            while (!currentCell.isSameColor(piece)) {
                boardState[i][j].putPiece(piece);
                i++;
                j++;
                currentCell = boardState[i][j];
            }
        }
    }

    /**
     * After placing the new piece, this method reverses all opposite piece pieces lying on a straight line
     * between the new piece and any anchoring pieces with same piece at the down-left (south-west) diagonal direction.
     *
     * @param piece  piece of new piece
     * @param row    row index of the move
     * @param column column index of the move
     */
    private void reverseDownLeft(Piece piece, int row, int column) {
        Cell currentCell;
        if (checkDownLeft(piece, row, column)) {
            int i = row + 1;
            int j = column - 1;
            currentCell = boardState[i][j];
            //reversing pieces until receives to same colored piece
            while (!currentCell.isSameColor(piece)) {
                boardState[i][j].putPiece(piece);
                i++;
                j--;
                currentCell = boardState[i][j];
            }
        }
    }

    /**
     * Puts new piece with the specified piece to the cell pointed by row and column index and do reverse at any
     * directions that is possible.
     *
     * @param piece  piece of new piece
     * @param row    row index of the move
     * @param column column index of the move
     */
    void putPiece(Piece piece, int row, int column) {
        if (isValidMove(piece, row, column)) {
            boardState[row][column].putPiece(piece);
            reverseUp(piece, row, column);
            reverseDown(piece, row, column);
            reversRight(piece, row, column);
            reverseLeft(piece, row, column);
            reverseDownRight(piece, row, column);
            reverseDownLeft(piece, row, column);
            reverseUpRight(piece, row, column);
            reverseUpLeft(piece, row, column);
        }
    }

    /**
     * Checks if the game is finished or not using three rules:<br>
     * 1. All pieces in the board has single color,<br>
     * 2. There is no empty cell,<br>
     * 3. There is no valid move for both black and white players.
     *
     * @return {@code true} if the game is finished, {@code false} otherwise
     */
    boolean isGameFinished() {
        if (isAllSingleColor()) {
            return true;
        }
        if (!isEmptyExists()) {
            return true;
        }
        if (hasNoValidMoves(BLACK_PIECE) && hasNoValidMoves(WHITE_PIECE)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if all pieces in the board has single color.
     *
     * @return {@code true} if the game is finished, {@code false} otherwise
     */
    private boolean isAllSingleColor() {
        boolean black = false;
        boolean white = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (boardState[i][j].isSameColor(BLACK_PIECE)) {
                    black = true;
                }
                if (boardState[i][j].isSameColor(WHITE_PIECE)) {
                    white = true;
                }
            }
        }
        //xor black and white, all should be black or white
        return black ^ white;
    }

    /**
     * Checks if there exist any empty cell on the board.
     *
     * @return {@code true} if the game is finished, {@code false} otherwise
     */
    private boolean isEmptyExists() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (boardState[i][j].isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Computes count of pieces with the specified piece on the board.
     *
     * @param piece piece piece that will be counted
     * @return count of specified piece pieces
     */
    int getColorCount(Piece piece) {
        int sum = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (boardState[i][j].isSameColor(piece)) {
                    sum += 1;
                }
            }
        }
        return sum;
    }

    /**
     * Finds a list of all possible and valid moves of specified piece piece. The returned list contains all index pairs
     * that determines cells that can be selected as next valid move.
     *
     * @param piece piece piece to find valid moves
     * @return a list of all valid moves
     */
    List<int[]> getValidMoves(Piece piece) {
        List<int[]> resultList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isValidMove(piece, i, j)) {
                    int[] ints = new int[2];
                    ints[0] = i;
                    ints[1] = j;
                    resultList.add(ints);
                }
            }
        }
        return resultList;
    }

    /**
     * Checks if the specified piece has any valid move at the next turn.
     *
     * @param piece piece piece to check existence of valid move
     * @return {@code true} if the game is finished, {@code false} otherwise
     */
    boolean hasNoValidMoves(Piece piece) {
        return getValidMoves(piece).isEmpty();
    }

    /**
     * Computes all possible earned pieces if the specified move apply.
     *
     * @param piece  piece of new piece
     * @param row    row index of the move
     * @param column column index of the move
     */
    int getEarnedPiecesCountWithMove(Piece piece, int row, int column) {
        int earns = 0;
        if (isValidMove(piece, row, column)) {
            earns += getEarnedPiecesCountLeft(piece, row, column);
            earns += getEarnedPiecesCountRight(piece, row, column);
            earns += getEarnedPiecesCountUp(piece, row, column);
            earns += getEarnedPiecesCountDown(piece, row, column);
            earns += getEarnedPiecesCountUpLeft(piece, row, column);
            earns += getEarnedPiecesCountUpRight(piece, row, column);
            earns += getEarnedPiecesCountDownLeft(piece, row, column);
            earns += getEarnedPiecesCountDownRight(piece, row, column);
        }
        return earns;
    }

    /**
     * If the specified move apply, computes all possible earned pieces lying on a straight line between the new piece
     * and any anchoring pieces with same piece at the left (west) horizontal direction.
     *
     * @param piece  piece of new piece
     * @param row    row index of the move
     * @param column column index of the move
     */
    private int getEarnedPiecesCountLeft(Piece piece, int row, int column) {
        int sum = 0;
        Cell currentCell;
        if (checkLeft(piece, row, column)) {
            int i = column - 1;
            currentCell = boardState[row][i];
            while (!currentCell.isSameColor(piece)) {
                sum++;
                i--;
                currentCell = boardState[row][i];
            }
        }
        return sum;
    }

    /**
     * If the specified move apply, computes all possible earned pieces lying on a straight line between the new piece
     * and any anchoring pieces with same piece at the right (east) horizontal direction.
     *
     * @param piece  piece of new piece
     * @param row    row index of the move
     * @param column column index of the move
     */
    private int getEarnedPiecesCountRight(Piece piece, int row, int column) {
        int sum = 0;
        Cell currentCell;
        if (checkRight(piece, row, column)) {
            int i = column + 1;
            currentCell = boardState[row][i];
            while (!currentCell.isSameColor(piece)) {
                sum++;
                i++;
                currentCell = boardState[row][i];
            }
        }
        return sum;
    }

    /**
     * If the specified move apply, computes all possible earned pieces lying on a straight line between the new piece
     * and any anchoring pieces with same piece at the up (north) vertical direction.
     *
     * @param piece  piece of new piece
     * @param row    row index of the move
     * @param column column index of the move
     */
    private int getEarnedPiecesCountUp(Piece piece, int row, int column) {
        int sum = 0;
        Cell currentCell;
        if (checkUp(piece, row, column)) {
            int i = row - 1;
            currentCell = boardState[i][column];
            while (!currentCell.isSameColor(piece)) {
                sum++;
                i--;
                currentCell = boardState[i][column];
            }
        }
        return sum;
    }

    /**
     * If the specified move apply, computes all possible earned pieces lying on a straight line between the new piece
     * and any anchoring pieces with same piece at the down (south) vertical direction.
     *
     * @param piece  piece of new piece
     * @param row    row index of the move
     * @param column column index of the move
     */
    private int getEarnedPiecesCountDown(Piece piece, int row, int column) {
        int sum = 0;
        Cell currentCell;
        if (checkDown(piece, row, column)) {
            int i = row + 1;
            currentCell = boardState[i][column];
            while (!currentCell.isSameColor(piece)) {
                sum++;
                i++;
                currentCell = boardState[i][column];
            }
        }
        return sum;
    }

    /**
     * If the specified move apply, computes all possible earned pieces lying on a straight line between the new piece
     * and any anchoring pieces with same piece at the up-left (north-west) diagonal direction.
     *
     * @param piece  piece of new piece
     * @param row    row index of the move
     * @param column column index of the move
     */
    private int getEarnedPiecesCountUpLeft(Piece piece, int row, int column) {
        int sum = 0;
        Cell currentCell;
        if (checkUpLeft(piece, row, column)) {
            int i = row - 1;
            int j = column - 1;
            currentCell = boardState[i][j];
            while (!currentCell.isSameColor(piece)) {
                sum++;
                i--;
                j--;
                currentCell = boardState[i][j];
            }
        }
        return sum;
    }

    /**
     * If the specified move apply, computes all possible earned pieces lying on a straight line between the new piece
     * and any anchoring pieces with same piece at the up-right (north-east) diagonal direction.
     *
     * @param piece  piece of new piece
     * @param row    row index of the move
     * @param column column index of the move
     */
    private int getEarnedPiecesCountUpRight(Piece piece, int row, int column) {
        int sum = 0;
        Cell currentCell;
        if (checkUpRight(piece, row, column)) {
            int i = row - 1;
            int j = column + 1;
            currentCell = boardState[i][j];
            while (!currentCell.isSameColor(piece)) {
                sum++;
                i--;
                j++;
                currentCell = boardState[i][j];
            }
        }
        return sum;
    }

    /**
     * If the specified move apply, computes all possible earned pieces lying on a straight line between the new piece
     * and any anchoring pieces with same piece at the down-left (south-west) diagonal direction.
     *
     * @param piece  piece of new piece
     * @param row    row index of the move
     * @param column column index of the move
     */
    private int getEarnedPiecesCountDownLeft(Piece piece, int row, int column) {
        int sum = 0;
        Cell currentCell;
        if (checkDownLeft(piece, row, column)) {
            int i = row + 1;
            int j = column - 1;
            currentCell = boardState[i][j];
            while (!currentCell.isSameColor(piece)) {
                sum++;
                i++;
                j--;
                currentCell = boardState[i][j];
            }
        }
        return sum;
    }

    /**
     * If the specified move apply, computes all possible earned pieces lying on a straight line between the new piece
     * and any anchoring pieces with same piece at the down-right (south-east) diagonal direction.
     *
     * @param piece  piece of new piece
     * @param row    row index of the move
     * @param column column index of the move
     */
    private int getEarnedPiecesCountDownRight(Piece piece, int row, int column) {
        int sum = 0;
        Cell currentCell;
        if (checkDownRight(piece, row, column)) {
            int i = row + 1;
            int j = column + 1;
            currentCell = boardState[i][j];
            while (!currentCell.isSameColor(piece)) {
                sum++;
                i++;
                j++;
                currentCell = boardState[i][j];
            }
        }
        return sum;
    }
}
