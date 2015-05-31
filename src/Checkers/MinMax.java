package Checkers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Monis on 5/24/15.
 */
public class MinMax {
    private IChildStateGenerator stateGenerator;
    private IEvaluator evaluator;
    private int recursionDepth;

    public MinMax(IChildStateGenerator stateGenerator, IEvaluator evaluator, int recursionDepth) {
        this.stateGenerator = stateGenerator;
        this.evaluator = evaluator;
        this.recursionDepth = recursionDepth;
    }

    public Move findBestMove(State initialState)
    {
        return findBestMoveMinMax(initialState, 0).move;
    }

    private EvaluatedMove findBestMoveMinMax(State parentState, int counter)
    {
        if (counter == recursionDepth)
        {
            return new EvaluatedMove(null, evaluator.Evaluate(parentState));
        }
        List<MoveAndState> movesAndStatesList = stateGenerator.generateChildren(parentState);

        List<EvaluatedMove> evaluatedMoves = movesAndStatesList.stream().map(moveAndState -> {
            EvaluatedMove res = findBestMoveMinMax(moveAndState.state, counter + 1);
            res.move = moveAndState.move;
            return res;
        }).collect(Collectors.toList());

        return counter % 2 == 0 ?
                evaluatedMoves.stream().max((e1, e2) -> new Integer(e1.value).compareTo(e2.value)).get()
                : evaluatedMoves.stream().min((e1, e2) -> new Integer(e1.value).compareTo(e2.value)).get();
    }

    class EvaluatedMove
    {
        public Move move;
        public int value;

        public EvaluatedMove(Move move, int value) {
            this.move = move;
            this.value = value;
        }
    }
}
