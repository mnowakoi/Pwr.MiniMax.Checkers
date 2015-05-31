package Checkers;

import java.util.List;

/**
 * Created by Monis on 5/24/15.
 */
public interface IChildStateGenerator {
    public List<MoveAndState> generateChildren(State parentState);
}
