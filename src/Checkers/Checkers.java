package Checkers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Monis on 5/31/15.
 */
public class Checkers {
    MinMax minmax;
    MinMax minmax2;
    int moveCounter = 0;

    public Checkers()
    {
        Evaluator evaluator = new Evaluator();
        ChildStateGenerator stateGenerator = new ChildStateGenerator();
        minmax = new MinMax(stateGenerator, evaluator, 6);
        minmax2 = new MinMax(stateGenerator, evaluator, 6);
    }

    public void minMaxTwoComputers(State state)
    {
        this.moveCounter = 0;
        GameStatus status = this.miniMaxTwoComputersMode(state, 0, state.board.lightPieces.size() + state.board.darkPieces.size());
        printStatus(status);
    }

    public void minMaxOneComputer(State state)
    {
        this.moveCounter = 0;
        GameStatus status = this.minMaxOnePlayer(state, 0, state.board.lightPieces.size() + state.board.darkPieces.size());
        printStatus(status);
    }

    private void printStatus(GameStatus status)
    {
        switch (status) {
            case lightWin:
                System.out.println("Bialy kolor wygrywa");
                break;
            case darkWin:
                System.out.println("Czarny kolor wygrywa");
                break;
            case draw:
                System.out.println("Remis");
                break;
            case lightBlocked:
                System.out.println("Czarny kolor wygrywa");
                break;
            case darkBlocked:
                System.out.println("Bialy kolor wygrywa");
        }
    }

    private GameStatus miniMaxTwoComputersMode(State state, int nochangesCounter, int piecesCount)
    {
        if(!state.isFinished() && nochangesCounter < 30) {

            System.out.println("State:");
            System.out.println("Move counter: " + ++moveCounter);
            System.out.println("Turn: " + (state.isLightTurn() ? "white" : "black"));
            System.out.println("Light pieces: " + state.board.lightPieces.size());
            System.out.println("Dark pieces: " + state.board.darkPieces.size());

            if ((state.board.lightPieces.size() + state.board.darkPieces.size()) == piecesCount) {
                nochangesCounter++;
            } else {
                piecesCount = (state.board.lightPieces.size() + state.board.darkPieces.size());
                nochangesCounter = 0;
            }
            Move bestMove = state.isLightTurn() ? minmax2.findBestMove(state) :  minmax.findBestMove(state);
            if (bestMove == null) return state.isLightTurn ? GameStatus.lightBlocked : GameStatus.darkBlocked;
            Board newBoard = state.board.makeMove(bestMove);
            newBoard.print();
            return miniMaxTwoComputersMode(new State(newBoard, !state.isLightTurn()), nochangesCounter, piecesCount);
        }

        return getGameStatus(state);
    }

    public GameStatus minMaxOnePlayer(State state, int nochangesCounter, int piecesCount)
    {
        if ((state.board.lightPieces.size() + state.board.darkPieces.size()) == piecesCount) {
            nochangesCounter++;
        } else {
            piecesCount = (state.board.lightPieces.size() + state.board.darkPieces.size());
            nochangesCounter = 0;
        }

        if(!state.isFinished() && nochangesCounter < 30) {
            if (state.isLightTurn) {
                Move bestMove = minmax.findBestMove(state);
                if (bestMove == null) return state.isLightTurn ? GameStatus.lightBlocked : GameStatus.darkBlocked;
                Board newBoard = state.board.makeMove(bestMove);
                newBoard.print();
                return minMaxOnePlayer(new State(newBoard, !state.isLightTurn()), nochangesCounter, piecesCount);
            } else {
                System.out.println("Podaj ruch w formacie 01 21 23 numerujac wszystkie pola na ktorych stanie pionek");

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                ArrayList<FieldPosition> fieldPositions = new ArrayList<>();
                try {
                    String fieldsList = br.readLine();
                    String[] splited = fieldsList.split(" ");
                    for (String position : splited) {
                        String[] fieldPositionString = position.split("");
                        FieldPosition fieldPosition = new FieldPosition(Integer.parseInt(fieldPositionString[0]), Integer.parseInt(fieldPositionString[1]));
                        fieldPositions.add(fieldPosition);
                    }
                } catch (IOException ioe) {
                    System.out.println("Niepoprawny format");
                }

               // try {
                    Move move = new Move(fieldPositions);
                    Board newBoard = state.board.makeMove(move);
                    newBoard.print();
                    return minMaxOnePlayer(new State(newBoard, !state.isLightTurn()), nochangesCounter, piecesCount);
//                } catch (Exception e) {
//
//                }
            }
        }
        return getGameStatus(state);
    }

    private GameStatus getGameStatus(State state)
    {
        if (state.board.lightPieces.size() > state.board.darkPieces.size())
        {
            return GameStatus.lightWin;
        }
        else if (state.board.lightPieces.size() < state.board.darkPieces.size())
        {
            return GameStatus.darkWin;
        }
        else return GameStatus.draw;
    }

    enum GameStatus
    {
        lightWin, darkWin, draw, lightBlocked, darkBlocked
    }
}
