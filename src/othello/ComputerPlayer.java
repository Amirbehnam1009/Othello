package othello;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Determines a semi-intelligent computer player that can choose its next move based on some heuristic rules.
 */
class ComputerPlayer extends Player {
    /**
     * The othello game board
     */
    private Board board;

    /**
     * Constructor that initializes player.
     *
     * @param board The othello game board
     */
    ComputerPlayer(Board board) {
        super("Computer", new WhitePiece());
        this.board = board;
    }

    /**
     * Chooses the best move from available moves to be used as the current move of computer player in play to computer
     * game mode.<br>
     * The board contains six parts with the following priorities:<br>
     * 1. corners,<br>
     * 2. 4 by 4 center square,<br>
     * 3. edges except cells near corners,<br>
     * 4. cells between center square and edges except cells near corners,<br>
     * 5. cells at edges and near corners,<br>
     * 6. cells at diameters and near corners.<br>
     * <br>
     * In every turn, list of valid moves will be categorized in this six groups and next move will be selected from
     * group with highest priority that contains any valid moves. If there exists more than one move in selected group,
     * the move with most earned pieces will be selectd.
     *
     * @return next move string for computer player
     */
    public String getNextMove() {
        List<int[]> validMoves = board.getValidMoves(getPiece());

        int[] selected;
        if (validMoves.size() == 0) {
            return "";
        } else if (validMoves.size() == 1) {
            selected = validMoves.get(0);
        } else {
            //six groups
            List<int[]> corners = new ArrayList<>();
            List<int[]> centers = new ArrayList<>();
            List<int[]> edges = new ArrayList<>();
            List<int[]> betweenEdgeAndCenters = new ArrayList<>();
            List<int[]> aroundCornersInEdge = new ArrayList<>();
            List<int[]> aroundCornersInDiameter = new ArrayList<>();

            //categorizing all valid moves to the six groups
            for (int[] currentMove : validMoves) {
                if ((currentMove[0] == 0 && currentMove[1] == 0) || (currentMove[0] == 0 && currentMove[1] == 7)
                        || (currentMove[0] == 7 && currentMove[1] == 0) || (currentMove[0] == 7 && currentMove[1] == 7)) {
                    corners.add(currentMove);
                } else if (currentMove[0] >= 2 && currentMove[0] <= 5 && currentMove[1] >= 2 && currentMove[1] <= 5) {
                    centers.add(currentMove);
                } else if (((currentMove[0] == 0 || currentMove[0] == 7) && currentMove[1] >= 2 && currentMove[1] <= 5)
                        || ((currentMove[1] == 0 || currentMove[1] == 7) && currentMove[0] >= 2 && currentMove[0] <= 5)) {
                    edges.add(currentMove);
                } else if (((currentMove[0] == 1 || currentMove[0] == 6) && currentMove[1] >= 2 && currentMove[1] <= 5)
                        || ((currentMove[1] == 1 || currentMove[1] == 6) && currentMove[0] >= 2 && currentMove[0] <= 5)) {
                    betweenEdgeAndCenters.add(currentMove);
                } else if ((currentMove[0] == 0 && currentMove[1] == 1) || (currentMove[0] == 0 && currentMove[1] == 6)
                        || (currentMove[0] == 1 && currentMove[1] == 0) || (currentMove[0] == 1 && currentMove[1] == 7)
                        || (currentMove[0] == 6 && currentMove[1] == 0) || (currentMove[0] == 6 && currentMove[1] == 7)
                        || (currentMove[0] == 7 && currentMove[1] == 1) || (currentMove[0] == 7 && currentMove[1] == 6)) {
                    aroundCornersInEdge.add(currentMove);
                } else if ((currentMove[0] == 1 && currentMove[1] == 1) || (currentMove[0] == 1 && currentMove[1] == 6)
                        || (currentMove[0] == 6 && currentMove[1] == 1) || (currentMove[0] == 6 && currentMove[1] == 6)) {
                    aroundCornersInDiameter.add(currentMove);
                }
            }
            List<int[]> higherPriorityWithValidMoves;
            //selecting highest priority that contains any valid moves
            if (!corners.isEmpty()) {
                higherPriorityWithValidMoves = corners;
            } else if (!centers.isEmpty()) {
                higherPriorityWithValidMoves = centers;
            } else if (!edges.isEmpty()) {
                higherPriorityWithValidMoves = edges;
            } else if (!betweenEdgeAndCenters.isEmpty()) {
                higherPriorityWithValidMoves = betweenEdgeAndCenters;
            } else if (!aroundCornersInEdge.isEmpty()) {
                higherPriorityWithValidMoves = aroundCornersInEdge;
            } else {
                higherPriorityWithValidMoves = aroundCornersInDiameter;
            }

            //choosing the move with the most earned pieces
            selected = chooseWithMostEarnPieces(higherPriorityWithValidMoves);
        }

        //converting indexes to move string with format "I C"
        String selectedMoveStr = convertToMoveStr(selected);
        System.out.println(selectedMoveStr);
        return selectedMoveStr;
    }

    /**
     * Chooses the move that lead to maximum earn pieces from specified move list. If there is more than one so called
     * moves, it chooses one randomly.
     *
     * @param moves list of available moves to choose
     * @return move that lead to maximum earn pieces
     */
    private int[] chooseWithMostEarnPieces(List<int[]> moves) {
        if (moves.isEmpty()) {
            return null;
        } else if (moves.size() == 1) {
            return moves.get(0);
        } else {
            //finding move with max earn pieces
            List<int[]> maxEarnMoves = new ArrayList<>();
            int maxEarned = 0;
            for (int[] move : moves) {
                int earnedWithMove = board.getEarnedPiecesCountWithMove(getPiece(), move[0], move[1]);
                if (earnedWithMove > maxEarned) {
                    maxEarned = earnedWithMove;
                    maxEarnMoves.clear();
                    maxEarnMoves.add(move);
                } else if (earnedWithMove == maxEarned) {
                    maxEarnMoves.add(move);
                }
            }
            if (maxEarnMoves.size() == 1) {
                return maxEarnMoves.get(0);
            } else {
                Random random = new Random();
                int selectedMoveIndex = random.nextInt(maxEarnMoves.size());
                return maxEarnMoves.get(selectedMoveIndex);
            }
        }
    }

    /**
     * Converts row and column indexes to move string format. Row and column indexes are both numbers in the range
     * 0 to 7. Computed input string format is "I C" that 'I' part is a number in range 1 to 8 and 'C' part is an
     * upper case character in range A to H.
     *
     * @param ints an array with two members, 0 index indicates computed row and 1 index indicates computed column
     * @return move string with format "I C", that 'I' part is a number in range 1 to 8 and 'C' part is an upper case
     * character in range A to H.
     */
    private String convertToMoveStr(int[] ints) {
        char row = (char) ((ints[0] + 1) + '0');
        char column = (char) (ints[1] + 65);
        return row + " " + column;
    }
}
