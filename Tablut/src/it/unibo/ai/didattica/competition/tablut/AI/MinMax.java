package it.unibo.ai.didattica.competition.tablut.AI;

import java.util.ArrayList;
import java.util.List;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

public class MinMax {

    private int currDepthLimit;
    private Turn player;
    private List<String> citadels = new ArrayList<>();
    // private final Heuristic heuristic;

    // risultati
    private static Action result;
    private static List<Action> possibleActions;

    private Game game = new Game();

    public MinMax(int currDepthLimit, State.Turn player) {
        this.currDepthLimit = currDepthLimit;
        this.player = player;
        // this.heuristic = new MyHeuristicF(player, currDepthLimit);

        this.citadels = new ArrayList<>();
        possibleActions = new ArrayList<>();

        this.citadels.add("a4");
        this.citadels.add("a5");
        this.citadels.add("a6");
        this.citadels.add("b5");
        this.citadels.add("d1");
        this.citadels.add("e1");
        this.citadels.add("f1");
        this.citadels.add("e2");
        this.citadels.add("i4");
        this.citadels.add("i5");
        this.citadels.add("i6");
        this.citadels.add("h5");
        this.citadels.add("d9");
        this.citadels.add("e9");
        this.citadels.add("f9");
        this.citadels.add("e8");
    }

    public Action minmaxDecision(State currentState) {
        result = null;
        possibleActions = game.getPossibleActions(currentState);
        double v = Double.NEGATIVE_INFINITY;
        for (Action a : possibleActions) {
            double temp = minValue(state.movePawn(a), 0);
            if (temp > v) {
                v = temp;
                result = a;
            }
        }
        return result;
    }

}
