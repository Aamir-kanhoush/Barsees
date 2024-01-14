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

    private float evaluateState(State state) {
        float score = 0;

        for (PlayRock playRock1 : state.getCurrentPlayer().getPlayRocks()) {
            if (Rules.kill(state.getBoard(), playRock1, state.getOtherPlayer().getPlayRocks())) {
                score += 100;
            }
        }
        for (PlayRock playRock : state.getCurrentPlayer().getPlayRocks()) {
            if (Board.isMatbokh(playRock, state.convertDiceResultToSteps(state.alaa))) {
                score += 500;
            }
        }

        for (PlayRock playRock : state.getCurrentPlayer().getPlayRocks()) {
            if (playRock.tastee7) {
                score += 200;
            }
        }

        for (PlayRock playRock : state.getCurrentPlayer().getPlayRocks()) {
            score += playRock.counter;
        }
        for (PlayRock playRock : state.getCurrentPlayer().getPlayRocks()) {
            if (playRock.getPosition() != -1) {
                score += 50;
            }
        }
        return score;
    }


}