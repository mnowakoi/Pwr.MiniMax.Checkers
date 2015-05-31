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

    public Move(FieldPosition source, FieldPosition target) {
        this.source = source;
        this.target = new ArrayList<>();
        this.target.add(target);
        isCapture = false;
    }

    public Move(FieldPosition source, List<FieldPosition> target) {
        this.source = source;
        this.target = new ArrayList<>();
        for(FieldPosition targetField : target)
        {
            FieldPosition fieldPosition = new FieldPosition(targetField.row, targetField.column);
            target.add(fieldPosition);
        }
        isCapture = true;
    }

    public Move getCopy()
    {
        FieldPosition newSource = new FieldPosition(this.source.row, this.source.column);
        return new Move(newSource, this.target);
    }
}
