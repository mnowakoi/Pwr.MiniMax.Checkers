package Checkers;

/**
 * Created by Monis on 5/24/15.
 */
public class Field {
    public int row;
    public int column;
    public Piece piece;

    public boolean isEmpty()
    {
        return piece == null;
    }

    public FieldPosition getPosition()
    {
        return new FieldPosition(row, column);
    }
}
