package Checkers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Monis on 5/31/15.
 */
public class Checkers {
    MinMax minmax;
    MinMax minmax2;
    MinMaxAlfaBeta minMaxAlfaBeta;
    MinMaxAlfaBeta minMaxAlfaBeta2;
    int moveCounter = 0;

    public Checkers() {
        Evaluator evaluator = new Evaluator();
        ChildStateGenerator stateGenerator = new ChildStateGenerator();
        minmax = new MinMax(stateGenerator, evaluator, 6);
        minmax2 = new MinMax(stateGenerator, evaluator, 6);
        minMaxAlfaBeta = new MinMaxAlfaBeta(stateGenerator, evaluator, 6);
        minMaxAlfaBeta2 = new MinMaxAlfaBeta(stateGenerator, evaluator, 6);
    }

    public void minMaxTwoComputers(State state) {
        this.moveCounter = 0;
        GameStatus status = this.minMaxTwoComputersMode(state, 0, state.board.lightPieces.size() + state.board.darkPieces.size());
        printStatus(status);
    }

    public void minMaxAlfaBetaTwoComputers(State state) {
        this.moveCounter = 0;
        GameStatus status = this.minMaxAlfaBetaTwoComputersMode(state, 0, state.board.lightPieces.size() + state.board.darkPieces.size());
        printStatus(status);
    }

    public void minMaxOneComputer(State state) {
        this.moveCounter = 0;
        GameStatus status = this.minMaxOnePlayer(state, 0, state.board.lightPieces.size() + state.board.darkPieces.size());
        printStatus(status);
    }

    private GameStatus minMaxTwoComputersMode(State state, int noChangesCounter, int piecesCount) {
        if (!state.isFinished() && noChangesCounter < 30) {
            printCurrentState(state);

            if ((state.board.lightPieces.size() + state.board.darkPieces.size()) == piecesCount) {
                noChangesCounter++;
            } else {
                piecesCount = (state.board.lightPieces.size() + state.board.darkPieces.size());
                noChangesCounter = 0;
            }

            Move bestMove = state.isLightTurn() ? minmax2.findBestMove(state) : minmax.findBestMove(state);
            if (bestMove == null) return state.isLightTurn ? GameStatus.lightBlocked : GameStatus.darkBlocked;
            Board newBoard = state.board.makeMove(bestMove);
            newBoard.print();

            return minMaxTwoComputersMode(new State(newBoard, !state.isLightTurn()), noChangesCounter, piecesCount);
        }

        return getGameStatus(state);
    }

    private GameStatus minMaxAlfaBetaTwoComputersMode(State state, int noChangesCounter, int piecesCount) {
        if (!state.isFinished() && noChangesCounter < 30) {
            printCurrentState(state);

            if ((state.board.lightPieces.size() + state.board.darkPieces.size()) == piecesCount) {
                noChangesCounter++;
            } else {
                piecesCount = (state.board.lightPieces.size() + state.board.darkPieces.size());
                noChangesCounter = 0;
            }

            Move bestMove = state.isLightTurn() ? minMaxAlfaBeta.findBestMove(state) : minMaxAlfaBeta2.findBestMove(state);
            if (bestMove == null) return state.isLightTurn ? GameStatus.lightBlocked : GameStatus.darkBlocked;
            Board newBoard = state.board.makeMove(bestMove);
            newBoard.print();

            return minMaxTwoComputersMode(new State(newBoard, !state.isLightTurn()), noChangesCounter, piecesCount);
        }

        return getGameStatus(state);
    }

    private GameStatus minMaxOnePlayer(State state, int noChangesCounter, int piecesCount) {
        if (!state.isFinished() && noChangesCounter < 30) {
            printCurrentState(state);

            if ((state.board.lightPieces.size() + state.board.darkPieces.size()) == piecesCount) {
                noChangesCounter++;
            } else {
                piecesCount = (state.board.lightPieces.size() + state.board.darkPieces.size());
                noChangesCounter = 0;
            }

            if (state.isLightTurn) {
                Move bestMove = minmax.findBestMove(state);

                if (bestMove == null) return state.isLightTurn ? GameStatus.lightBlocked : GameStatus.darkBlocked;

                Board newBoard = state.board.makeMove(bestMove);
                newBoard.print();
                return minMaxOnePlayer(new State(newBoard, !state.isLightTurn()), noChangesCounter, piecesCount);
            } else {
                boolean isValid = false;

                while (!isValid) {
                    System.out.println("Podaj ruch w formacie wiersz/kolumna np. 01 21 23 poczynajac od pola na ktrym stoi pionek numerujac wszystkie pola na ktorych stanie pionek");
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
                    } catch (Exception e) {
                    }

                    try {
                        Move move = new Move(fieldPositions);
                        Board newBoard = state.board.makeMove(move);
                        newBoard.print();
                        return minMaxOnePlayer(new State(newBoard, !state.isLightTurn()), noChangesCounter, piecesCount);
                    } catch (Exception e) {
                        System.out.println("Nielegalny ruch!");
                    }
                }
            }
        }
        return getGameStatus(state);
    }

    private void printStatus(GameStatus status) {
        switch (status) {
            case lightWin:
                System.out.println("Niebieski kolor wygrywa");
                break;
            case darkWin:
                System.out.println("Zolty kolor wygrywa");
                break;
            case draw:
                System.out.println("Remis");
                break;
            case lightBlocked:
                System.out.println("Zolty kolor wygrywa");
                break;
            case darkBlocked:
                System.out.println("Niebieski kolor wygrywa");
        }
    }

    private void printCurrentState(State state)
    {
        System.out.println("Stan:");
        System.out.println("Ilosc ruchow: " + ++moveCounter);
        System.out.println("Ruch koloru: " + (state.isLightTurn() ? "Niebieskie" : "Zolte"));
        System.out.println("Ilosc niebieskich: " + state.board.lightPieces.size());
        System.out.println("Ilosc zoltych: " + state.board.darkPieces.size());
    }

    private GameStatus getGameStatus(State state) {
        if (state.board.lightPieces.size() > state.board.darkPieces.size()) {
            return GameStatus.lightWin;
        } else if (state.board.lightPieces.size() < state.board.darkPieces.size()) {
            return GameStatus.darkWin;
        } else return GameStatus.draw;
    }

    private enum GameStatus {
        lightWin, darkWin, draw, lightBlocked, darkBlocked
    }
}
