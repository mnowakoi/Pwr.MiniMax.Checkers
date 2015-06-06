package Checkers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monis on 5/24/15.
 */
public class Man extends Piece {
    public Man(boolean isLight, Field field) {
        this.isLight = isLight;
        this.field = field;
    }

    @Override
    public List<Move> findPossibleMoves(Board board) {
        List<Move> possibleMoves = new ArrayList<>();

        Field field = board.selectAboveLeft(this);
        if (field != null && field.isEmpty()) possibleMoves.add(new Move(this.field.getPosition(), field.getPosition()));

        field = board.selectAboveRight(this);
        if (field != null && field.isEmpty()) possibleMoves.add(new Move(this.field.getPosition(), field.getPosition()));

        return possibleMoves;
    }

    @Override
    public List<Move> findPossibleCaptures(Board board) {
        List<List<FieldPosition>> traces = addNextCapture(board, new ArrayList<>(), this.field);

        List<Move> moves = new ArrayList<>();

        for(List<FieldPosition> trace : traces)
        {
            if (trace.size() < 2)
                continue;

            FieldPosition source = trace.get(0);
            trace.remove(0);
            Move move = new Move(source, trace, trace.size());
            moves.add(move);
        }

        return moves;
    }

    private List<List<FieldPosition>> addNextCapture(Board board, List<FieldPosition> trace, Field currentField) {
        List<Field> fieldsToProcess = new ArrayList<>();

        checkAndAdd(trace, fieldsToProcess, board.captureUpperLeft(currentField.getPosition(), this));
        checkAndAdd(trace, fieldsToProcess, board.captureUpperRight(currentField.getPosition(), this));
        checkAndAdd(trace, fieldsToProcess, board.captureLowerLeft(currentField.getPosition(), this));
        checkAndAdd(trace, fieldsToProcess, board.captureLowerRight(currentField.getPosition(), this));

        if (fieldsToProcess.isEmpty()) {
            List<List<FieldPosition>> results = new ArrayList<>();
            trace.add(currentField.getPosition());
            results.add(trace);
            return results;
        } else {
            int maxLength = 0;
            List<List<FieldPosition>> results = new ArrayList<>();

            for(Field field : fieldsToProcess) {
                List<FieldPosition> clonedTrace = new ArrayList<>(trace);
                clonedTrace.add(currentField.getPosition());
                List<List<FieldPosition>> capturedResults = addNextCapture(board, clonedTrace, field);

                for (List<FieldPosition> currentTrace : capturedResults) {
                    if (currentTrace.size() > maxLength) {
                        results.clear();
                        maxLength = currentTrace.size();
                    }

                    if (currentTrace.size() == maxLength) {
                        results.add(currentTrace);
                    }
                }
            }
            return results;
        }
    }

    private void checkAndAdd(List<FieldPosition> trace, List<Field> fieldsToProcess, Field upperLeft) {
        if (upperLeft != null && !trace.contains(upperLeft.getPosition()) ) {
            fieldsToProcess.add(upperLeft);
        }
    }

    @Override
    public Piece getCopy(Field field) {
        return new Man(this.isLight, field);
    }
}
