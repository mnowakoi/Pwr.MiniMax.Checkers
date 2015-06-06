package Checkers;

import org.junit.Assert;
import org.junit.Test;


public class MinMaxTest {

    @Test
    public void minmaxBasicTest() {
        Board board = new Board();

        board.movePiece(2, 5, 4, 5);

        board.removePieceOnField(5, 4);

        State state = new State(board, false);

        MinMax minmaxAlgo = new MinMax(new ChildStateGenerator(), new Evaluator(), 3);

        Move move = minmaxAlgo.findBestMove(state);

        Assert.assertEquals(new FieldPosition(5, 6), move.source);
        Assert.assertEquals(1, move.target.size());
        Assert.assertEquals(new FieldPosition(3, 4), move.target.get(0));
    }

    @Test
    public void minimaxAiTest() {
        Board board = new Board();

        board.removePieceOnField(1, 0);
        board.removePieceOnField(1, 2);
        board.removePieceOnField(1, 4);
        board.removePieceOnField(1, 6);
        board.removePieceOnField(0,1);
        board.removePieceOnField(0,3);
        board.removePieceOnField(0,7);
        board.removePieceOnField(2,1);
        board.removePieceOnField(2,5);
        board.removePieceOnField(2,7);

        board.movePiece(5,4, 4, 3);
        board.movePiece(5,2, 4, 1);
        board.movePiece(5,6, 4, 5);

        board.print();

        State state = new State(board, true);

        MinMax minmaxAlgo = new MinMax(new ChildStateGenerator(), new Evaluator(), 3);

        Move move = minmaxAlgo.findBestMove(state);

        Assert.assertEquals(new FieldPosition(0, 5), move.source);


    }

}