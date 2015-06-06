package Checkers;


/**
 * Created by Monis on 5/24/15.
 */
public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.print();
        State startState = new State(board, false);

        Checkers checkers = new Checkers();
         checkers.minMaxTwoComputers(startState);
        //checkers.minMaxOneComputer(startState);
//        checkers.minMaxAlfaBetaTwoComputers(startState);
        //checkers.minMaxAlfaBetaOneComputer(startState);
    }
}
