package Checkers;


/**
 * Created by Monis on 5/24/15.
 */
public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.print();
        State startState = new State(board, true);

        Checkers checkers = new Checkers();
        checkers.MiniMaxTwoComputersMode(startState);
    }
}
