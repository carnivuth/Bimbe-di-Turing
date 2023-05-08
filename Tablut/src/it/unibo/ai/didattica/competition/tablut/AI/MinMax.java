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
    private Turn enemy;
    private List<String> citadels = new ArrayList<>();
    private final Heuristic heuristic;

    // risultati
    private static Action result;
    private static Set<Action> possibleActions;

    private Expansion myExpansion = new Expansion();

    public MinMax(int currDepthLimit, State.Turn player) {
        this.currDepthLimit = currDepthLimit;
        this.player = player;
        this.enemy=(player.equalsTurn("W"))?Turn.valueOf("BLACK"):Turn.valueOf("WHITE");
        this.heuristic = new Heuristic();

        possibleActions = new HashSet<>();

    }

    public Action minmaxDecision(StateTablut currentState) {
        Action resultAction = null;

        double bestValue = Double.NEGATIVE_INFINITY;
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;
        System.out.println(currentState.toString());

        possibleActions = myExpansion.actions(currentState,StateUtils.getPawns(currentState, "B"),StateUtils.getKing(currentState),StateUtils.getPawns(currentState,"W"),player.name());

        for (Action a : possibleActions) {
            
            StateTablut newState = myExpansion.result(currentState, a);
            double value = minValue(newState, alpha, beta, 0);
            if (value > bestValue) {
                bestValue = value;
                resultAction = a;
            }
        }
        System.out.println("PAWNS LISTS");
        System.out.println(StateUtils.printPawns(StateUtils.getPawns(currentState, "W")));
        System.out.println(StateUtils.printPawns(StateUtils.getPawns(currentState, "B")));
        System.out.println(currentState.boardString());
        System.out.println("Best value: " + bestValue);
        System.out.println("Result action: " + resultAction);
        return resultAction;
    }

    private double maxValue(StateTablut state, double alpha, double beta, int depth) {
        if (isTerminal(state) || depth == currDepthLimit) {
            return evaluate(state);
        }
        double value = Double.NEGATIVE_INFINITY;
        for (Action a : myExpansion.actions(state,StateUtils.getPawns(state, "B"),StateUtils.getKing(state),StateUtils.getPawns(state,"W"),player.name())) {
            StateTablut newState = myExpansion.result(state, a);
            value = Math.max(value, minValue(newState, alpha, beta, depth + 1));
            System.out.println(value);
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
        for (Action a : myExpansion.actions(state,StateUtils.getPawns(state, "B"),StateUtils.getKing(state),StateUtils.getPawns(state,"W"),this.enemy.name())) {
            StateTablut newState = myExpansion.result(state, a);
            value = Math.min(value, maxValue(newState, alpha, beta, depth + 1));
            System.out.println(value);
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
