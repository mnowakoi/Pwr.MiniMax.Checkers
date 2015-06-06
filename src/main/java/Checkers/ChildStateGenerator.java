package Checkers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Monis on 5/24/15.
 */
public class ChildStateGenerator implements IChildStateGenerator {

    @Override
    public List<MoveAndState> generateChildren(State state) {
        List<Piece> pieces = state.isLightTurn() ? state.board.lightPieces : state.board.darkPieces;

        List<Move> movesList = new ArrayList<>();
        for (Piece piece : pieces) {
            movesList.addAll(piece.findPossibleCaptures(state.board).stream().collect(Collectors.toList()));
        }

        if (movesList.isEmpty()) {
            for (Piece piece : pieces) {
                movesList.addAll(piece.findPossibleMoves(state.board).stream().collect(Collectors.toList()));
            }
        }
        else
        {
            movesList.stream().max((e1, e2) -> new Integer(e1.deep).compareTo(e2.deep)).get();
        }

        List<MoveAndState> children = new ArrayList<>();

        if(!movesList.isEmpty()) {
            for (Move move : movesList) {
                Board newBoard = state.getBoard().makeMove(move);
                State newState = new State(newBoard, !state.isLightTurn());
                children.add(new MoveAndState(move, newState));
            }
        }
        return children;
    }
}
