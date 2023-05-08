package it.unibo.ai.didattica.competition.tablut.AI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

public class MinMax {

    private int currDepthLimit;
    private Turn player;
    private List<String> citadels = new ArrayList<>();
    private final Heuristic heuristic;

    // risultati
    private static Action result;
    private static Set<Action> possibleActions;

    private Expansion myExpansion = new Expansion();

    public MinMax(int currDepthLimit, State.Turn player) {
        this.currDepthLimit = currDepthLimit;
        this.player = player;
        this.heuristic = new Heuristic();

        possibleActions = new HashSet<>();

    }

    public Action minmaxDecision(StateTablut currentState) {
        Action resultAction = null;

        double bestValue = Double.NEGATIVE_INFINITY;
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;

        possibleActions = myExpansion.actions(currentState,myExpansion.getPawns(currentState, "BLACK"),myExpansion.getKing(currentState),myExpansion.getPawns(currentState,"WHITE"));

        for (Action a : possibleActions) {
            System.out.println(a.toString());
            StateTablut newState = myExpansion.result(currentState, a);
            double value = minValue(newState, alpha, beta, 0);
            if (value > bestValue) {
                bestValue = value;
                resultAction = a;
            }
        }
        System.out.println("Best value: " + bestValue);
        System.out.println("Result action: " + resultAction);
        return resultAction;
    }

    private double maxValue(StateTablut state, double alpha, double beta, int depth) {
        if (isTerminal(state) || depth == currDepthLimit) {
            return evaluate(state);
        }
        double value = Double.NEGATIVE_INFINITY;
        for (Action a : myExpansion.actions(state,myExpansion.getPawns(state, "BLACK"),myExpansion.getKing(state),myExpansion.getPawns(state,"WHITE"))) {
            StateTablut newState = myExpansion.result(state, a);
            value = Math.max(value, minValue(newState, alpha, beta, depth + 1));
            if (value >= beta) {
                return value;
            }
            alpha = Math.max(alpha, value);
        }
        return value;
    }

    private double minValue(StateTablut state, double alpha, double beta, int depth) {
        if (isTerminal(state) || depth == currDepthLimit) {
            return evaluate(state);
        }
        double value = Double.POSITIVE_INFINITY;
        for (Action a : myExpansion.actions(state,myExpansion.getPawns(state, "BLACK"),myExpansion.getKing(state),myExpansion.getPawns(state,"WHITE"))) {
            StateTablut newState = myExpansion.result(state, a);
            value = Math.min(value, maxValue(newState, alpha, beta, depth + 1));
            if (value <= alpha) {
                return value;
            }
            beta = Math.min(beta, value);
        }
        return value;
    }

    private boolean isTerminal(State state) {
        return state.getTurn().equals(Turn.WHITEWIN) || state.getTurn().equals(Turn.BLACKWIN)
                || state.getTurn().equals(Turn.DRAW);
    }

    private double evaluate(StateTablut state) {

        return heuristic.evaluate(state, 0);
    }

}
