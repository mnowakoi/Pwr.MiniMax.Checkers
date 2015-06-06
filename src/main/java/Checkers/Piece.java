package Checkers;

import java.util.List;

/**
 * Created by Monis on 5/24/15.
 */
public abstract class Piece {
        public boolean isLight;
        public Field field;
        public abstract List<Move> findPossibleMoves(Board board);
        public abstract List<Move> findPossibleCaptures(Board board);
        public abstract Piece getCopy(Field field);
}
