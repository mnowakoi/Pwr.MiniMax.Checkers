package Checkers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monis on 6/1/15.
 */
public class Queen extends Piece {
    public Queen(Piece piece) {
        this.isLight = piece.isLight;
        this.field = piece.field;
    }

    private Queen(boolean isLight, Field field) {
        this.isLight = isLight;
        this.field = field;
    }

    @Override
    public List<Move> findPossibleCaptures(Board board) {
        List<List<FieldPosition>> traces = new ArrayList<>();

        traces.add(addNextMoveLowerLeft(board, new ArrayList<>(), this.field));
        traces.add(addNextMoveUpperLeft(board, new ArrayList<>(), this.field));
        traces.add(addNextMoveLowerRight(board, new ArrayList<>(), this.field));
        traces.add(addNextMoveUpperRight(board, new ArrayList<>(), this.field));

        List<Move> captures = new ArrayList<>();

        for (List<FieldPosition> trace : traces) {

            Field currentField = new Field();
            List<List<FieldPosition>> currentTraces = addNextCapture(board, new ArrayList<>(), board.select(currentField.row, currentField.column));

            if (trace.isEmpty()) {
                continue;
            }

            FieldPosition source = trace.get(0);
            trace.remove(0);

            for (List<FieldPosition> currentTrace : currentTraces) {
                List<FieldPosition> concatenatedTrace = new ArrayList<>(trace);
                concatenatedTrace.addAll(currentTrace);

                if (currentTrace.size() < 2)
                    continue;

                Move move = new Move(source, trace, currentTrace.size());
                captures.add(move);
            }

        }
        return captures;
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

            for (Field field : fieldsToProcess) {
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
        if (upperLeft != null && !trace.contains(upperLeft.getPosition())) {
            fieldsToProcess.add(upperLeft);
        }
    }

    @Override
    public List<Move> findPossibleMoves(Board board) {
        List<List<FieldPosition>> traces = new ArrayList<>();

        traces.add(addNextMoveLowerLeft(board, new ArrayList<>(), this.field));
        traces.add(addNextMoveUpperLeft(board, new ArrayList<>(), this.field));
        traces.add(addNextMoveLowerRight(board, new ArrayList<>(), this.field));
        traces.add(addNextMoveUpperRight(board, new ArrayList<>(), this.field));

        List<Move> moves = new ArrayList<>();

        for (List<FieldPosition> trace : traces) {
            if (trace.size() >= 2) {
                FieldPosition source = trace.get(0);
                trace.remove(0);
                List<FieldPosition> currentSteps = new ArrayList<>();

                for (FieldPosition field : trace) {
                    currentSteps = new ArrayList<>(currentSteps);
                    currentSteps.add(field);

                    Move move = new Move(source, currentSteps, 0);
                    moves.add(move);
                }
            }
        }

        return moves;
    }

    private List<FieldPosition> addNextMoveUpperLeft(Board board, List<FieldPosition> trace, Field currentField) {
        trace.add(currentField.getPosition());

        Field field = board.selectUpperLeft(currentField.getPosition());
        while (field != null && field.isEmpty()) {
            trace.add(field.getPosition());
            field = board.selectUpperLeft(field.getPosition());
        }
        return trace;
    }

    private List<FieldPosition> addNextMoveUpperRight(Board board, List<FieldPosition> trace, Field currentField) {
        trace.add(currentField.getPosition());

        Field field = board.selectUpperRight(currentField.getPosition());
        while (field != null && field.isEmpty()) {
            trace.add(field.getPosition());
            field = board.selectUpperRight(field.getPosition());
        }
        return trace;
    }

    private List<FieldPosition> addNextMoveLowerLeft(Board board, List<FieldPosition> trace, Field currentField) {
        trace.add(currentField.getPosition());

        Field field = board.selectLowerLeft(currentField.getPosition());
        while (field != null && field.isEmpty()) {
            trace.add(field.getPosition());
            field = board.selectLowerLeft(field.getPosition());
        }
        return trace;
    }

    private List<FieldPosition> addNextMoveLowerRight(Board board, List<FieldPosition> trace, Field currentField) {
        trace.add(currentField.getPosition());

        Field field = board.selectLowerRight(currentField.getPosition());
        while (field != null && field.isEmpty()) {
            trace.add(field.getPosition());
            field = board.selectLowerRight(field.getPosition());
        }
        return trace;
    }

    @Override
    public Piece getCopy(Field field) {
        return new Queen(this.isLight, field);
    }
}
