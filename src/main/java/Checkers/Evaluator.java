package Checkers;

import java.util.List;

/**
 * Created by Monis on 5/24/15.
 */
public class Evaluator implements IEvaluator {

//    @Override
//    public int Evaluate(State state) {
//        int evaluation = 0;
//        List<Piece> pieces = state.isLightTurn() ? state.board.lightPieces : state.board.darkPieces;
//        for (Piece piece : pieces) {
//            Field field = piece.field;
//            if (field.row == 0 || field.row == 7 || field.column == 0 || field.row == 7) {
//                evaluation += 4;
//            } else
//                evaluation += 2;
//        }
//        return evaluation;
//        //return state.isLightTurn() ? state.board.lightPieces.size() : state.board.darkPieces.size();
//    }

    public int EvaluateColor(State state, boolean evaluateLight) {
        int evaluation = 0;
        List<Piece> pieces = evaluateLight ? state.board.lightPieces : state.board.darkPieces;
        List<Piece> enemyPieces = !evaluateLight ? state.board.lightPieces : state.board.darkPieces;

        for (Piece piece : pieces) {
            if (piece instanceof Queen) {
                evaluation += 4;
            } else {
                evaluation += 1;
            }
        }

        for (Piece piece : enemyPieces) {
            if (piece instanceof Queen) {
                evaluation -= 4;
            } else {
                evaluation -= 1;
            }
        }

        return evaluation;
    }
}
