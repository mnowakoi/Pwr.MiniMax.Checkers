package Checkers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monis on 5/24/15.
 */
public class Move {
    public FieldPosition source;
    public List<FieldPosition> target;
    public Boolean isCapture;
    int deep;

    public Move(FieldPosition source, FieldPosition target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException();
        }
        this.source = source;
        this.target = new ArrayList<>();
        this.target.add(target);
        isCapture = false;
    }

    public Move(FieldPosition source, List<FieldPosition> target, int deep) {
        if (source == null || target == null || target.size() == 0) {
            throw new IllegalArgumentException();
        }
        this.source = new FieldPosition(source.row, source.column);
        this.target = new ArrayList<>();
        for(FieldPosition targetField : target)
        {
            FieldPosition fieldPosition = new FieldPosition(targetField.row, targetField.column);
            this.target.add(fieldPosition);
        }
        isCapture = true;
        this.deep = deep;
    }

    public Move(List<FieldPosition> trace)
    {
        this.source = trace.get(0);
        trace.remove(0);
        Move move = new Move(source, trace, 0);
        this.target = move.target;
        this.deep = 0;
        this.isCapture = true;
    }
}
