package Checkers;

/**
 * Created by Monis on 5/24/15.
 */
public class FieldPosition {
    public int row;
    public int column;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldPosition that = (FieldPosition) o;

        if (column != that.column) return false;
        if (row != that.row) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }

    public FieldPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
