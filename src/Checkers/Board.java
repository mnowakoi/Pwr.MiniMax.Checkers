package Checkers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monis on 5/24/15.
 */
public class Board {
    private final int boardSize = 8;
    List<Piece> lightPieces;
    List<Piece> darkPieces;
    private Field[][] board;

    public Board() {
        this.board = new Field[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                this.board[i][j] = new Field();
                this.board[i][j].row = i;
                this.board[i][j].column = j;
            }
        }

        //white pieces
        lightPieces = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Piece piece = new Man(true, board[0][2 * i + 1]);
            board[0][2 * i + 1].piece = piece;
            piece.field = board[0][2 * i + 1];
            lightPieces.add(piece);
            piece = new Man(true, board[1][2 * i]);
            board[1][2 * i].piece = piece;
            lightPieces.add(piece);
            piece.field = board[1][2 * i];
            piece = new Man(true, board[2][2 * i + 1]);
            board[2][2 * i + 1].piece = piece;
            lightPieces.add(piece);
            piece.field = board[2][2 * i + 1];
        }

        //black pieces
        darkPieces = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Piece piece = new Man(false, board[5][2 * i]);
            board[5][2 * i].piece = piece;
            darkPieces.add(piece);
            piece.field = board[5][2 * i];
            piece = new Man(false, board[6][2 * i + 1]);
            board[6][2 * i + 1].piece = piece;
            darkPieces.add(piece);
            piece.field = board[6][2 * i + 1];
            piece = new Man(false, board[7][2 * i]);
            board[7][2 * i].piece = piece;
            darkPieces.add(piece);
            piece.field = board[7][2 * i];
        }
    }

    // copying constructor
    public Board(Board board) {
        this.board = new Field[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                this.board[i][j] = new Field();
                this.board[i][j].row = i;
                this.board[i][j].column = j;
            }
        }

        lightPieces = new ArrayList<>();
        for (Piece piece : board.lightPieces) {
            Piece newPiece = piece.getCopy(this.board[piece.field.row][piece.field.column]);
            this.board[piece.field.row][piece.field.column].piece = newPiece;
            this.lightPieces.add(newPiece);
        }

        darkPieces = new ArrayList<>();
        for (Piece piece : board.darkPieces) {
            Piece newPiece = piece.getCopy(this.board[piece.field.row][piece.field.column]);
            this.board[piece.field.row][piece.field.column].piece = newPiece;
            this.darkPieces.add(newPiece);
        }
    }

    public Field select(int row, int column) {
        if (row > -1 && row < boardSize && column > -1 && column < boardSize) {
            return board[row][column];
        }
        return null;
    }

    public Field captureUpperRight(FieldPosition field, Piece piece) {
        Field fieldToCapture = this.select(field.row + 1, field.column + 1);
        if (fieldToCapture != null) {
            Piece capturePiece = fieldToCapture.piece;
            if (capturePiece != null && !capturePiece.isLight) {
                return select(piece.field.row + 2, piece.field.column + 2);
            }
        }
        return null;
    }

    public Field captureUpperLeft(FieldPosition field, Piece piece) {
        Field fieldToCapture = this.select(field.row + 1, field.column - 1);
        if (fieldToCapture != null) {
            Piece capturePiece = fieldToCapture.piece;
            if (capturePiece != null && !capturePiece.isLight) {
                return select(piece.field.row + 2, piece.field.column - 2);
            }
        }
        return null;
    }

    public Field captureLowerLeft(FieldPosition field, Piece piece) {
        Field fieldToCapture = this.select(field.row - 1, field.column - 1);
        if (fieldToCapture != null) {
            Piece capturePiece = fieldToCapture.piece;
            if (capturePiece != null && !capturePiece.isLight) {
                return select(piece.field.row - 2, piece.field.column - 2);
            }
        }
        return null;
    }

    public Field captureLowerRight(FieldPosition field, Piece piece) {
        Field fieldToCapture = this.select(field.row - 1, field.column + 1);
        if (fieldToCapture != null) {
            Piece capturePiece = fieldToCapture.piece;
            if (capturePiece != null && !capturePiece.isLight) {
                return select(piece.field.row - 2, piece.field.column + 2);
            }
        }
        return null;
    }

    public Field selectAboveRight(Piece piece) {
        if (piece.isLight) {
            return select(piece.field.row + 1, piece.field.column + 1);
        } else {
            return select(piece.field.row - 1, piece.field.column - 1);
        }
    }

    public Field selectAboveLeft(Piece piece) {
        if (piece.isLight) {
            return select(piece.field.row + 1, piece.field.column - 1);
        } else {
            return select(piece.field.row - 1, piece.field.column + 1);
        }
    }

    public Board makeMove(Move move) {
        Board newBoard = new Board(this);

        Field startField = newBoard.board[move.source.row][move.source.column];
        Piece pieceToMove = startField.piece;
        startField.piece = null;

        if (!move.isCapture) {
            Field targetField = newBoard.board[move.target.get(0).row][move.target.get(0).column];
            targetField.piece = pieceToMove;
            pieceToMove.field = targetField;
        } else {
            for (FieldPosition fieldPosition : move.target) {
                pieceToMove = startField.piece;
                startField.piece = null;

                Field targetField = newBoard.board[fieldPosition.row][fieldPosition.column];
                targetField.piece = pieceToMove;
                pieceToMove.field = targetField;

                startField = newBoard.board[fieldPosition.row][fieldPosition.column];
            }
        }

        return newBoard;
    }

    public void print() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print(" | " + getSymbolForField(board[i][j]));
            }
            System.out.print(" | " + i);
            System.out.println();
            System.out.print(" ---------------------------------");
            System.out.println();
        }
        System.out.println("   0   1   2   3   4   5   6   7");
        System.out.println();
    }

    private String getSymbolForField(Field field) {
        if (field.isEmpty()) {
            return "_";
        }
        if (field.piece.isLight) {
            return "w";
        } else
            return "b";
    }
}
