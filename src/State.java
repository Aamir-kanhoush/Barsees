import java.util.*;
import java.util.Arrays;

public class State {
    private Board board;
    private Player currentPlayer;
    private Player otherPlayer;
    private boolean isPlayer1Turn;
    private DiceRolls diceRolls;

    public State(Board board, Player player1, Player player2, boolean isPlayer1Turn, DiceRolls diceRolls) {
        this.board = new Board(board.getPath().length, board.getPlayerKitchen(player1.getPlayRocks()[1]).length);

        // Create copies of the players using their copy constructors
        Player copyPlayer1 = new Player(player1);
        Player copyPlayer2 = new Player(player2);

        // Assign the copies to the currentPlayer and otherPlayer
        this.currentPlayer = isPlayer1Turn ? copyPlayer1 : copyPlayer2;
        this.otherPlayer = isPlayer1Turn ? copyPlayer2 : copyPlayer1;
        this.isPlayer1Turn = isPlayer1Turn;
        this.diceRolls = diceRolls;

        // Copy the board state including the play rocks
        copyBoardState(board, this.board);
    }


    private void copyBoardState(Board originalBoard, Board newBoard) {
        copyPlayRocks(originalBoard.getPath(), this.board.getPath());
        copyPlayRocks(originalBoard.getPlayerKitchen(new PlayRock(currentPlayer, null)), this.board.getPlayerKitchen(new PlayRock(currentPlayer, null)));
        copyPlayRocks(originalBoard.getPlayerKitchen(new PlayRock(otherPlayer, null)), this.board.getPlayerKitchen(new PlayRock(otherPlayer, null)));
    }

    private void copyPlayRocks(PlayRock[] original, PlayRock[] copy) {
        for (int i = 0; i < original.length; i++) {
            if (original[i] != null) {
                copy[i] = new PlayRock(original[i].getPlayer(), this.board);
                copy[i].setPosition(original[i].getPosition());
                copy[i].tastee7 = original[i].tastee7;
                copy[i].counter = original[i].counter;
                copy[i].isInTheKitchen = original[i].isInTheKitchen;
                copy[i].finish = original[i].finish;
            }
        }
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getOtherPlayer() {
        return otherPlayer;
    }

    public boolean isPlayer1Turn() {
        return isPlayer1Turn;
    }

    public DiceRolls getDiceRolls() {
        return diceRolls;
    }


    @Override
    public int hashCode() {
        int result = Objects.hash(currentPlayer, otherPlayer, isPlayer1Turn, diceRolls);
        result = 31 * result + Arrays.hashCode(board.getPath());
        result = 31 * result + Arrays.hashCode(board.getPlayerKitchen(new PlayRock(currentPlayer, null)));
        result = 31 * result + Arrays.hashCode(board.getPlayerKitchen(new PlayRock(otherPlayer, null)));
        return result;
    }

    public List<State> getNextStates() {
        int i = 0;
        List<State> successors = new ArrayList<>();
        String diceResult = diceRolls.countOnesAndNameState();
        int steps = convertDiceResultToSteps(diceResult);

        for (PlayRock rock : currentPlayer.getPlayRocks()) {
            if (true) {
                Board newBoard = new Board(board.getPath().length, board.getPlayerKitchen(currentPlayer.getPlayRocks()[1]).length);
                copyBoardState(this.board, newBoard);
                Player currentCopy = new Player(currentPlayer);

                // Create a copy of the PlayRock using the copy constructor
                System.out.println(diceResult + "pooooooooooooo : " + currentCopy.getPlayRocks()[i]);

                Move.DoMove(currentCopy.getPlayRocks()[i], newBoard, diceResult);
                System.out.println(diceResult + "pooooooooooooo : " + currentCopy.getPlayRocks()[i]);

                boolean nextPlayerTurn = !this.isPlayer1Turn;
                Player nextPlayer = nextPlayerTurn ? currentPlayer : otherPlayer;

                State successorState = new State(newBoard, currentCopy, otherPlayer, nextPlayerTurn, diceRolls);
                successors.add(successorState);
                successorState.getBoard().printBoard();
                i++;
            }
        }

        return successors;
    }


    private int convertDiceResultToSteps(String diceResult) {
        // Mapping dice results to steps
        switch (diceResult) {
            case "Dest":
                return 1;
            case "Duwag":
                return 2;
            case "Thalatha":
                return 3;
            case "Arba'a":
                return 4;
            case "Bara":
                return 12;
            case "Shakka":
                return 6;
            case "Bunja":
                // Assuming Bunja allows a specific move, e.g., 5 steps
                return 5;
            default:
                // If the dice result is not recognized, no steps are allowed
                return 0;
        }
    }

    public boolean canMoveRock(PlayRock rock, int steps) {
        boolean result;
        // Check if the rock has finished the game
        if (rock.finish) {
            System.out.println("finished");
            result = false;
        }

        // Check if the rock is in the kitchen and if it can exit or move within the kitchen
        if (rock.tastee7) {
            if (rock.getPosition() + steps <= board.getPlayerKitchen(rock).length) {
                System.out.println("rock is in the kitchen and if it can exit or move within the kitchen");
                result = true;
            } else return false;
            if (!rock.tastee7) {
                if (rock.getPlayer().id == 1 && rock.counter + steps <= board.getPlayerKitchen(rock).length + (board.getPath().length - rock.getPosition())) {
                    System.out.println("rock is in path and it can reenter the kitchen");//its working
                    result = true;
                } else if (steps <= board.getPlayerKitchen(rock).length +
                        (board.getPath().length - rock.counter + 1)) {
                    result = true;
                } else return false;
            }
        } else {
            if (!rock.tastee7) {
                if (rock.getPlayer().id == 1 && steps <= board.getPlayerKitchen(rock).length +
                        (board.getPath().length - rock.getPosition())) {
                    System.out.println("rock is in path and it can reenter the kitchen");//its working
                    result = true;
                } else if (rock.getPlayer().id ==2 && steps <= board.getPlayerKitchen(rock).length +
                        (board.getPath().length - rock.counter + 1)) {
                    result = true;
                } else return false;
            }
        }
        //Check if the rock is in path and it can reenter the kitchen


        // If none of the above conditions are met, the rock cannot move
        System.out.println("none of the above conditions are met, the rock cannot move");
        result = true;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Current Player: ").append(currentPlayer.getName()).append("\n");
        sb.append("PlayRocks:\n");
        for (PlayRock rock : currentPlayer.getPlayRocks()) {
            sb.append("PlayRock: ").append(rock.toString()).append("\n");
        }
        sb.append("Other Player: ").append(otherPlayer.getName()).append("\n");
        sb.append("PlayRocks:\n");
        for (PlayRock rock : otherPlayer.getPlayRocks()) {
            sb.append("PlayRock: ").append(rock.toString()).append("\n");
        }
        sb.append("Is Player 1's Turn: ").append(isPlayer1Turn).append("\n");
        sb.append("Dice Rolls: ").append(diceRolls.countOnesAndNameState()).append("\n");
//        sb.append("Board:\n").append(board.printBoard());

        return sb.toString();
    }

}

// Other methods to manipulate the state, check for end conditions, generate successors, etc.

