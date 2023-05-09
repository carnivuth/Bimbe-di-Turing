package it.unibo.ai.didattica.competition.tablut.AI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.plaf.SliderUI;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

public class MinMax {

    private int currDepthLimit;
    private Turn player;
    private Turn enemy;
    private final Heuristic heuristic;

    // risultati
    private static Set<Action> possibleActions;

    private Expansion myExpansion = new Expansion();

    public MinMax(int currDepthLimit, State.Turn player) {
        this.currDepthLimit = currDepthLimit;
        this.player = player;
        this.enemy = (player.equalsTurn("W")) ? Turn.valueOf("BLACK") : Turn.valueOf("WHITE");
        this.heuristic = new HeuristicGA();

        possibleActions = new HashSet<>();

    }

    public Action minmaxDecision(StateTablut currentState) {
        Action resultAction = null;
        System.out.println("MINMAX DECISION");
        System.out.println(currentState.boardString());

        System.out.println("DepthLimit: " + this.currDepthLimit);
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();
        double bestValue = Double.NEGATIVE_INFINITY;
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;
        List<Action> bestActions = new ArrayList<>();
        // System.out.println(currentState.toString());

        possibleActions = myExpansion.actions(currentState, StateUtils.getPawns(currentState, "B"),
                StateUtils.getKing(currentState), StateUtils.getPawns(currentState, "W"), player);

        for (Action a : possibleActions) {
            System.out.println("Action: " + a.toString());
            StateTablut newState = myExpansion.result(new BimbeState(currentState), a);
            double value = minValue(newState, alpha, beta, 0);
            System.out.println("Value ritornato: " + value);
            if (value >= bestValue) {
                if (value == bestValue) {
                    bestActions.add(a);
                }
                bestActions.clear();
                bestActions.add(a);

                bestValue = value;
                resultAction = a;
            }
            endTime = System.currentTimeMillis();
            if ((endTime - startTime) / 1000 > 55) {
                System.out.println("TIMEOUT------------------------");
                break;
            }
        }
        endTime = System.currentTimeMillis();

        System.out.println("----------------------");
        System.out.println("Time: " + (endTime - startTime) / 1000 + " s");
        System.out.println(currentState.boardString());
        if (bestActions.size() > 1) {
            resultAction = bestActions.get((int) (Math.random() * bestActions.size()));
        } else {
            resultAction = bestActions.get(0);
        }

        System.out.println("Best value: " + bestValue);
        System.out.println("Result action: " + resultAction);

        return resultAction;
    }

    private double maxValue(StateTablut state, double alpha, double beta, int depth) {

        List<int[]> blackPawns = StateUtils.getPawns(state, "B");
        List<int[]> whitePawns = StateUtils.getPawns(state, "W");
        if (isTerminal(state) || depth == currDepthLimit) {
            System.out.println("Min TERMINAL");
            return evaluate(state, player, blackPawns.size(), whitePawns.size());
        }

        double value = Double.NEGATIVE_INFINITY;
        for (Action a : myExpansion.actions(state, blackPawns, StateUtils.getKing(state), whitePawns, player)) {
            StateTablut newState = myExpansion.result(new BimbeState(state), a);
            value = Math.max(value, minValue(newState, alpha, beta, depth + 1));
            System.out.println("min: " + (depth + 1) + " -> " + value);

            // System.out.println(value);
            if (value >= beta) {
                System.out.println("PRUNING");
                return value;
            }
            alpha = Math.max(alpha, value);
        }

        return value;
    }

    private double minValue(StateTablut state, double alpha, double beta, int depth) {

        List<int[]> blackPawns = StateUtils.getPawns(state, "B");
        List<int[]> whitePawns = StateUtils.getPawns(state, "W");

        if (isTerminal(state) || depth == currDepthLimit) {
            return evaluate(state, enemy, blackPawns.size(), whitePawns.size());
        }
        System.out.println("MIN VALUE " + depth + "alpha: " + alpha + " beta: " + beta);
        double value = Double.POSITIVE_INFINITY;
        for (Action a : myExpansion.actions(state, blackPawns, StateUtils.getKing(state), whitePawns, this.enemy)) {
            StateTablut newState = myExpansion.result(new BimbeState(state), a);
            value = Math.min(value, maxValue(newState, alpha, beta, depth + 1));
            System.out.println("max: " + (depth + 1) + " -> " + value);
            if (value <= alpha) {
                System.out.println("PRUNING");
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

    private double evaluate(StateTablut state, Turn color, int blackPawns, int whitePawns) {

        return heuristic.evaluate(state);
    }

}
