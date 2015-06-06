package Checkers;

import java.util.ArrayList;
import java.util.List;

import static com.sun.org.apache.xalan.internal.lib.ExsltMath.abs;

/**
 * Created by Monis on 5/24/15.
 */
public class Board {
    String ANSI_RESET = "\u001B[0m";
    String ANSI_BLACK = "\u001B[30m";
    String ANSI_RED = "\u001B[31m";
    String ANSI_GREEN = "\u001B[32m";
    String ANSI_YELLOW = "\u001B[33m";
    String ANSI_BLUE = "\u001B[34m";
    String ANSI_PURPLE = "\u001B[35m";
    String ANSI_CYAN = "\u001B[36m";
    String ANSI_WHITE = "\u001B[37m";

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
            if (capturePiece != null && capturePiece.isLight != piece.isLight) {
                Field f = select(field.row + 2, field.column + 2);

                if (f != null && f.isEmpty())
                    return f;
            }
        }
        return null;
    }

    public Field captureUpperLeft(FieldPosition field, Piece piece) {
        Field fieldToCapture = this.select(field.row + 1, field.column - 1);
        if (fieldToCapture != null) {
            Piece capturePiece = fieldToCapture.piece;
            if (capturePiece != null && capturePiece.isLight != piece.isLight) {
                Field f = select(field.row + 2, field.column - 2);

                if (f != null && f.isEmpty())
                    return f;
            }
        }
        return null;
    }

    public Field captureLowerLeft(FieldPosition field, Piece piece) {
        Field fieldToCapture = this.select(field.row - 1, field.column - 1);
        if (fieldToCapture != null) {
            Piece capturePiece = fieldToCapture.piece;
            if (capturePiece != null && capturePiece.isLight != piece.isLight) {
                Field f = select(field.row - 2, field.column - 2);

                if (f != null && f.isEmpty())
                    return f;
            }
        }
        return null;
    }

    public Field captureLowerRight(FieldPosition field, Piece piece) {
        Field fieldToCapture = this.select(field.row - 1, field.column + 1);
        if (fieldToCapture != null) {
            Piece capturePiece = fieldToCapture.piece;
            if (capturePiece != null && capturePiece.isLight != piece.isLight) {
                Field f = select(field.row - 2, field.column + 2);

                if (f != null && f.isEmpty())
                    return f;
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

    public Field selectUpperLeft(FieldPosition piece) {
        return select(piece.row - 1, piece.column - 1);
    }

    public Field selectUpperRight(FieldPosition piece) {
        return select(piece.row - 1, piece.column + 1);
    }

    public Field selectLowerLeft(FieldPosition piece) {
        return select(piece.row + 1, piece.column - 1);
    }

    public Field selectLowerRight(FieldPosition piece) {
        return select(piece.row + 1, piece.column + 1);
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

                int rowDiff = fieldPosition.row - startField.row;
                int colDiff = fieldPosition.column - startField.column;

                if(abs(rowDiff) > 2 || abs(colDiff) > 2 )
                    throw new IllegalArgumentException();

                if(abs(rowDiff) == 2 && abs(colDiff) == 2)
                {
                    int rowOffset = rowDiff / 2;
                    int colOffset = colDiff / 2;

                    Field enemyField = newBoard.select(startField.row + rowOffset, startField.column + colOffset);

                    if (enemyField.piece.isLight == pieceToMove.isLight)  throw new IllegalArgumentException();

                    newBoard.removePiece(enemyField.piece);
                }

                startField = newBoard.board[fieldPosition.row][fieldPosition.column];
            }
            startField.occupyBy(pieceToMove);
        }

        if (pieceToMove.isLight && pieceToMove.field.row == 7)
        {
            Field field = pieceToMove.field;
            Piece queen = new Queen(pieceToMove);
            newBoard.removePiece(pieceToMove);
            field.occupyBy(queen);
            newBoard.lightPieces.add(queen);
        } else if (!pieceToMove.isLight && pieceToMove.field.row == 0)
        {
            Field field = pieceToMove.field;
            Piece queen = new Queen(pieceToMove);
            newBoard.removePiece(pieceToMove);
            field.occupyBy(queen);
            newBoard.darkPieces.add(queen);
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
            return " ";
        }
        if (field.piece.isLight) {
            return field.piece instanceof Queen ? ANSI_CYAN + "W" + ANSI_RESET : ANSI_CYAN + "w" + ANSI_RESET;
        } else
            return field.piece instanceof Queen ? ANSI_YELLOW + "B" + ANSI_RESET : ANSI_YELLOW + "b" + ANSI_RESET;
    }

    private void removePiece(Piece piece) {
        if (piece.isLight) {
            this.lightPieces.remove(piece);
        } else {
            this.darkPieces.remove(piece);
        }
        piece.field.clear();
    }

    public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        Field field = this.select(fromRow, fromCol);
        Piece piece = field.piece;
        field.piece = null;
        Field targetField = this.select(toRow, toCol);
        piece.field = targetField;
        targetField.piece = piece;
    }

    public void removePieceOnField(int row, int col) {
        Field field = this.select(row, col);

        if (field.piece.isLight)
        {
            this.lightPieces.remove(field.piece);
        }
        else
        {
            this.darkPieces.remove(field.piece);
        }

        field.piece = null;
    }
}
