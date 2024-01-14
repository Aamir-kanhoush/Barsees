import java.util.*;

public class ExpectimaxAI {



    public State expectiminimax(State state, int depth, boolean isMaximizingPlayer) {
        if (depth == 0 || Player.hasWon(state.getCurrentPlayer().getPlayRocks())) {
            State currentState = state;
            while (currentState.getParentState().getParentState() != null) {
                currentState = currentState.getParentState();
            }
            return currentState;
        }

        if (isMaximizingPlayer) {
            State bestState = null;
            float maxValue = Float.NEGATIVE_INFINITY;
            for (State childState : state.getNextStates()) {
                State candidateState = expectiminimax(childState, depth - 1, false);
                float value = evaluateState(candidateState);
                if (value > maxValue) {
                    maxValue = value;
                    bestState = candidateState;
                }
            }
            return bestState;
        } else {
            List<State> childStates = state.getNextStatesWithProbabilities();
            float sumValue = 0;
            float maxExpectedValue = Float.NEGATIVE_INFINITY;
            State bestRepresentativeState = null;

            Map<State, Float> stateProbabilities = new HashMap<>();

            for (State childState : childStates) {
                State candidateState = expectiminimax(childState, depth - 1, true);
                float value = evaluateState(candidateState);

                stateProbabilities.merge(candidateState, value, Float::sum);

                float expectedValue = stateProbabilities.get(candidateState);
                if (expectedValue > maxExpectedValue) {
                    maxExpectedValue = expectedValue;
                    bestRepresentativeState = candidateState;
                }
            }
            return bestRepresentativeState;
        }
    }

    //o evaluae tthe utttility value of terminal state
    //here we should calculate that value // high value for winning and low for losing
    private float evaluateState(State state) {
        float score = 0;

        // Score based on killing the other player's piece
        for (PlayRock playRock1 : state.getCurrentPlayer().getPlayRocks()) {
            if (Rules.kill(state.getBoard(), playRock1, state.getOtherPlayer().getPlayRocks())) {
                score += 100; // Arbitrarily set to 100
            }
        }

        // Score based on reaching the finish line
        for (PlayRock playRock : state.getCurrentPlayer().getPlayRocks()) {
            if (Board.isMatbokh(playRock,state.convertDiceResultToSteps(state.alaa))) {
                score += 500; // Arbitrarily set to 500
            }
        }

        // Score based on tstee7 status
        for (PlayRock playRock : state.getCurrentPlayer().getPlayRocks()) {
            if (playRock.tastee7) {
                score += 200; // Arbitrarily set to 200
            }
        }

        // Score based on counter increment
        for (PlayRock playRock : state.getCurrentPlayer().getPlayRocks()) {
            score += playRock.counter; // Higher counter value means better score
        }
        for (PlayRock playRock : state.getCurrentPlayer().getPlayRocks()) {
            if (playRock.getPosition() != -1) {
                score += 50; // Arbitrarily set to 10
            }
        }
        return score;
    }


    //ti check if the current state is terminal

}