package Checkers;

import java.util.Collections;
import java.util.List;
import java.util.Random;
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
        return findBestMoveMinMax(initialState, 0, initialState.isLightTurn()).move;
    }

    private EvaluatedMove findBestMoveMinMax(State parentState, int counter, boolean evaluateLight)
    {
        if (counter == recursionDepth)
        {
            return new EvaluatedMove(null, evaluator.EvaluateColor(parentState, evaluateLight));
        }
        List<MoveAndState> movesAndStatesList = stateGenerator.generateChildren(parentState);

        if(movesAndStatesList.isEmpty())
        {
            return new EvaluatedMove(null, evaluator.EvaluateColor(parentState, evaluateLight));
        }

        List<EvaluatedMove> evaluatedMoves = movesAndStatesList.stream().map(moveAndState -> {
            EvaluatedMove res = findBestMoveMinMax(moveAndState.state, counter + 1, evaluateLight);
            res.move = moveAndState.move;
            return res;
        }).collect(Collectors.toList());


        long seed = System.nanoTime();
        Collections.shuffle(evaluatedMoves, new Random(seed));

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
