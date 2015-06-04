package Checkers;

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

        long seed = System.nanoTime();
        Collections.shuffle(movesAndStatesList, new Random(seed));

        EvaluatedMove best =  findBestMoveMinMax(movesAndStatesList.get(0).state, counter + 1, alfa, beta, evaluateLight);
        best.move = movesAndStatesList.get(0).move;
        movesAndStatesList.remove(0);

        if(counter % 2 == 0)
        {
            for (MoveAndState moveAndState : movesAndStatesList)
            {
                EvaluatedMove betaCand = findBestMoveMinMax(moveAndState.state, counter + 1, alfa, beta, evaluateLight);
                if(betaCand.value < beta)
                {
                    beta = betaCand.value;
                    best = betaCand;
                    best.move = moveAndState.move;
                }
                if (alfa >= beta)
                    return best;
            }
        }
        else
        {
            for (MoveAndState moveAndState : movesAndStatesList)
            {
                EvaluatedMove alfaCand = findBestMoveMinMax(moveAndState.state, counter + 1, alfa, beta, evaluateLight);
                if(alfaCand.value > beta)
                {
                    beta = alfaCand.value;
                    best = alfaCand;
                    best.move = moveAndState.move;
                }
                if (alfa >= beta)
                    return best;
            }
        }
        return best;
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
