import java.util.*;
import java.util.Arrays;

public class State {
    private Board board;
    private Player currentPlayer;
    private Player otherPlayer;
    private boolean isPlayer1Turn;
    private DiceRolls diceRolls;
    String alaa;

    public State(Board board, Player player1, Player player2, DiceRolls diceRolls , String alaa) {
        // Create copies of the players using their copy constructors
        Player copyPlayer1 = new Player(player1);
        Player copyPlayer2 = new Player(player2);

        // Assign the copies to the currentPlayer and otherPlayer
        this.currentPlayer = new Player(player1);
        this.otherPlayer = new Player(player2);

        this.diceRolls = diceRolls;
        this.alaa = alaa;

        // Copy the board state including the play rocks
        this.board = new Board(board); // Use the copy constructor
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
    List<State> successors = new ArrayList<>();
//    String diceResult = diceRolls.countOnesAndNameState();
    String diceResult = alaa;
    int steps = convertDiceResultToSteps(diceResult);
    System.out.println(diceResult);
    Board newBoard = new Board(this.board);
    Player curr = new Player(currentPlayer);
    if (canSet(currentPlayer)&&!Rules.win(currentPlayer)) {
        if (!notAllRocksOut() ) {
            for (int x = 0; x < currentPlayer.getPlayRocks().length; x++) {
                if (currentPlayer.getPlayRocks()[x].getPosition() == -1) {
                    Move.Uncle(currentPlayer.getPlayRocks()[x], newBoard, 1);
                    break;
                }
            }
            for (int i = 0; i < currentPlayer.getPlayRocks().length; i++) {
                Player currentCopy = new Player(currentPlayer);
                PlayRock rock = currentCopy.getPlayRocks()[i];
                if (canMoveRock(rock, steps)) {
                    Board newBoard1 = new Board(this.board);
                    Move.DoMove(rock, newBoard1, diceResult);
                    boolean nextPlayerTurn = !this.isPlayer1Turn;
                    Player nextPlayer = nextPlayerTurn ? currentCopy : otherPlayer;
                    State successorState = new State(newBoard1, currentCopy, otherPlayer, diceRolls , alaa);
                    successors.add(successorState);
                    System.out.println(successorState);
                }
            }
        }else {

            for (int j = 0; j < 2; j++) {
                if (j == 0) {
                    Board newBoard1 = new Board(newBoard);
                    Player currentCopy = new Player(currentPlayer);

                    for (int x = 0; x < currentCopy.getPlayRocks().length; x++) {
                        if (currentCopy.getPlayRocks()[x].getPosition() == -1) {
                            Move.Uncle(currentCopy.getPlayRocks()[x], newBoard1, 1);
                            break;
                        }
                    }


                    for (int i = 0; i < currentCopy.getPlayRocks().length; i++) {
                        Player currentCopy1 = new Player(currentCopy);
                        PlayRock rock = currentCopy1.getPlayRocks()[i];
                        if (canMoveRock(rock, steps)) {
                            Board newBoard2 = new Board(newBoard1);
                            Move.DoMove(rock, newBoard2, diceResult);
                            boolean nextPlayerTurn = !this.isPlayer1Turn;
                            Player nextPlayer = nextPlayerTurn ? currentCopy1 : otherPlayer;
                            State successorState = new State(newBoard2, currentCopy1, otherPlayer, diceRolls,alaa);
                            successors.add(successorState);
                            System.out.println(successorState);
                        }
                    }
                }
                if (j == 1) {
                    for (int i = 0; i < currentPlayer.getPlayRocks().length; i++) {
                        Player currentCopy = new Player(currentPlayer);
                        Board newBoard1 = new Board(newBoard);
                        if (canMoveRock(currentCopy.getPlayRocks()[i],1 ))
                        {
                            Move.Uncle(currentCopy.getPlayRocks()[i],newBoard,2 );
                            State successorState = new State(newBoard1, currentCopy, otherPlayer, diceRolls,alaa);
                            successors.add(successorState);
                            System.out.println(successorState);
                        }
                        for (int k = 0; k < currentCopy.getPlayRocks().length; k++) {
                            Player currentCopy1 = new Player(currentCopy);
                            PlayRock rock = currentCopy1.getPlayRocks()[k];
                            System.out.println( "can move? " + canMoveRock(rock,10 ));
                            if (canMoveRock(rock,10 ))
                            {
                                Move.DoMove(rock,newBoard1,diceResult );
                                State successorState = new State(newBoard1, currentCopy1, otherPlayer, diceRolls,alaa);
                                successors.add(successorState);
                                System.out.println(successorState);
                            }

                        }
                    }

                }
            }

        }
    } else {
        Player currentCopy = new Player(currentPlayer);
        Board newBoard1 = new Board(newBoard);
        for (int k = 0; k < currentCopy.getPlayRocks().length; k++) {

            Player currentCopy1 = new Player(currentCopy);
            PlayRock rock = currentCopy1.getPlayRocks()[k];
            if (canMoveRock(rock,steps ))
            {
                Move.DoMove(rock,newBoard1,diceResult);
                State successorState = new State(newBoard1, currentCopy1, otherPlayer, diceRolls, alaa);
                successors.add(successorState);
                System.out.println(successorState);
            }

        }
    }


    return successors;
}




    public static int convertDiceResultToSteps(String diceResult) {
        // Mapping dice results to steps
        switch (diceResult) {
            case "Dest":
                return 10;
            case "Duwag":
                return 2;
            case "Thalatha":
                return 3;
            case "Arba'a":
                return 4;
            case "Bara":
                return 10;
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
            result = false;
        }
        if (Rules.isMaksora(board,rock, otherPlayer.getPlayRocks()))
        {
            return false;
        }
        // Check if the rock is in the kitchen and if it can exit or move within the kitchen
        if (rock.tastee7) {
            if (rock.getPosition() + steps <= board.getPlayerKitchen(rock).length) {
                System.out.println("rock is in the kitchen and if it can exit or move within the kitchen");
                result = true;
            } else return false;
            if (!rock.tastee7) {
                if (rock.getPlayer().id == 1 && rock.counter + steps <= board.getPlayerKitchen(rock).length + (board.getPath().length - rock.getPosition())) {
                    result = true;
                } else if (steps <= board.getPlayerKitchen(rock).length +
                        (board.getPath().length - rock.counter + 1)) {
                    result = true;
                } else return false;
            }
        } else {
            if (!rock.tastee7 && rock.getPosition() != -1) {
                if (rock.getPlayer().id == 1 && steps <= board.getPlayerKitchen(rock).length +
                        (board.getPath().length - rock.getPosition())) {
                    result = true;
                } else if (rock.getPlayer().id ==2 && steps <= board.getPlayerKitchen(rock).length +
                        (board.getPath().length - rock.counter + 1)) {
                    result = true;
                } else return false;
            }else return false;
        }
        //Check if the rock is in path and it can reenter the kitchen


        // If none of the above conditions are met, the rock cannot move
        result = true;
        return result;
    }
    public boolean canSet(Player player) {
//        String diceResult = diceRolls.countOnesAndNameState();
        String diceResult = alaa;
        if (diceResult.equals("Dest") || diceResult.equals("Bunja")) {
            for (PlayRock rock : player.getPlayRocks()) {
                if (!rock.finish && rock.getPosition() == -1) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean notAllRocksOut() {
        for (PlayRock rock : currentPlayer.getPlayRocks()) {

            if (rock.getPosition() != -1) {
                System.out.println(rock.getPosition());
                return true;
            }


        }
        return false;
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
//        sb.append("Dice Rolls: ").append(diceRolls.countOnesAndNameState()).append("\n");
//        sb.append("Board:\n").append(board.printBoard());

        return sb.toString();
    }
    public List<State> getNextStatesWithProbabilities() {
        List<State> allNextStates = new ArrayList<>();

        // Define the probabilities for each dice roll outcome
        Map<String, Double> probabilities = new HashMap<>();
        probabilities.put("Dest", 0.1);
        probabilities.put("Bunja", 0.1);
        probabilities.put("Shakka", 0.1);
        probabilities.put("Bara", 0.1);
        probabilities.put("Arba'a", 0.2);
        probabilities.put("Thalatha", 0.2);
        probabilities.put("Duwag", 0.2);
        System.out.println("raghaddddddddddddddddddddddddddddddddddddd");
        // For each possible dice roll outcome
        for (Map.Entry<String, Double> entry : probabilities.entrySet()) {
            String diceRollOutcome = entry.getKey();
            System.out.println("llllllllllllllllllllllllllllllllll" + diceRollOutcome);
            double probability = entry.getValue();

            // Generate the next states based on the dice roll outcome
            State state = new State(board,getCurrentPlayer(),getOtherPlayer(),null,diceRollOutcome);
            List<State> nextStates = state.getNextStates();

            // Repeat the next states according to the probability
            for (int i = 0; i < probability * 100; i++) {
                allNextStates.addAll(nextStates);
            }
        }

        return allNextStates;
    }


}

// Other methods to manipulate the state, check for end conditions, generate successors, etc.

