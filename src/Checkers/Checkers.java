package Checkers;

/**
 * Created by Monis on 5/31/15.
 */
public class Checkers {
    MinMax minmax;

    public Checkers()
    {
        Evaluator evaluator = new Evaluator();
        ChildStateGenerator stateGenerator = new ChildStateGenerator();
        minmax = new MinMax(stateGenerator, evaluator, 4);
    }

    public void MiniMaxTwoComputersMode(State state)
    {
        //if(isFinished)
        Move bestMove = minmax.findBestMove(state);
        Board newBoard = state.board.makeMove(bestMove);
        newBoard.print();
        MiniMaxTwoComputersMode(new State(newBoard, !state.isLightTurn()));
    }
}
