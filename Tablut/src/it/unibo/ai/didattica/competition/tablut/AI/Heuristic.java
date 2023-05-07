package it.unibo.ai.didattica.competition.tablut.AI;

import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;

public class Heuristic {
    public Heuristic() {
    }

    public double evaluate(StateTablut state, int depth) {
        // return a double radom between 0 and 100
        return Math.random() * 100;
    }
}
