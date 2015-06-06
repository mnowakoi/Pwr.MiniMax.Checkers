package Checkers;

/**
 * Created by Monis on 5/24/15.
 */
public class State {
    public Board board;
    public boolean isLightTurn;

    public State(Board board, boolean isLightTurn) {
        this.board = board;
        this.isLightTurn = isLightTurn;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isLightTurn() {
        return isLightTurn;
    }

    public boolean isFinished() {
        return board.darkPieces.isEmpty() || board.lightPieces.isEmpty();
    }
}
