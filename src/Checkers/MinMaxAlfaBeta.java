package Checkers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Monis on 6/4/15.
 */
public class MinMaxAlfaBeta {
    private IChildStateGenerator stateGenerator;
    private IEvaluator evaluator;
    private int recursionDepth;

    public MinMaxAlfaBeta(IChildStateGenerator stateGenerator, IEvaluator evaluator, int recursionDepth) {
        this.stateGenerator = stateGenerator;
        this.evaluator = evaluator;
        this.recursionDepth = recursionDepth;
    }

    public Move findBestMove(State initialState)
    {
        return findBestMoveMinMax(initialState, 0, -99999, 99999, initialState.isLightTurn()).move;
    }

    private EvaluatedMove findBestMoveMinMax(State parentState, int counter, int alfa, int beta, boolean evaluateLight)
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

        List<EvaluatedMove> evaluatedMoves = new ArrayList<>();
        if(parentState.isLightTurn && evaluateLight)
        {
            for (MoveAndState moveAndState : movesAndStatesList)
            {
                EvaluatedMove betaCand = findBestMoveMinMax(moveAndState.state, counter + 1, alfa, beta, evaluateLight);
                beta = betaCand.value < beta ? betaCand.value : beta;
                if (alfa < beta)
                    evaluatedMoves.add(findBestMoveMinMax(moveAndState.state, counter + 1, alfa, beta, evaluateLight));
            }
        }
        else
        {
            for (MoveAndState moveAndState : movesAndStatesList)
            {
                EvaluatedMove alfaCand = findBestMoveMinMax(moveAndState.state, counter + 1, alfa, beta, evaluateLight);
                alfa = alfaCand.value > alfa ? alfaCand.value : alfa;
                if (alfa < beta)
                    evaluatedMoves.add(findBestMoveMinMax(moveAndState.state, counter + 1, alfa, beta, evaluateLight));
            }
        }

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
