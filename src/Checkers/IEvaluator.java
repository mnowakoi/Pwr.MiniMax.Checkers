package Checkers;

/**
 * Created by Monis on 5/24/15.
 */
public interface IEvaluator {
    public int Evaluate(State state);
    public int EvaluateColor(State state, boolean evaluateLight);
}
