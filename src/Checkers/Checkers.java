package Checkers;

/**
 * Created by Monis on 5/31/15.
 */
public class Checkers {
    MinMax minmax;
    int moveCounter = 0;

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
        System.out.println("State:");
        System.out.println("Move counter: " + ++moveCounter);
        System.out.println("Turn: " + (state.isLightTurn()? "LIGHT" : "DARK"));
        System.out.println("Light pieces: " + state.board.lightPieces.size());
        System.out.println("Dark pieces: " + state.board.darkPieces.size());
        newBoard.print();
        MiniMaxTwoComputersMode(new State(newBoard, !state.isLightTurn()));
    }
}
