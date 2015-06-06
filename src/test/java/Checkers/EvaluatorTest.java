package Checkers;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by krzysztofkaczor on 6/6/15.
 */
public class EvaluatorTest {

    @Test
    public void evaluateStartingStateForWhite() {
        Board board = new Board();
        State state = new State(board, true);

        Evaluator eval = new Evaluator();

        Assert.assertEquals(0, eval.EvaluateColor(state, true));
    }

    @Test
    public void evaluateStartingStateForBlack() {
        Board board = new Board();
        State state = new State(board, true);

        Evaluator eval = new Evaluator();

        Assert.assertEquals(0, eval.EvaluateColor(state, false));
    }

    @Test
    public void evaluateLoosingStateForWhite() {
        Board board = new Board();
        State state = new State(board, true);

        board.lightPieces.remove(0);

        Evaluator eval = new Evaluator();

        Assert.assertEquals(-1, eval.EvaluateColor(state, true));
    }

    @Test
    public void evaluateWinningStateForWhite() {
        Board board = new Board();
        State state = new State(board, true);

        board.darkPieces.remove(0);

        Evaluator eval = new Evaluator();

        Assert.assertEquals(1, eval.EvaluateColor(state, true));
    }
}