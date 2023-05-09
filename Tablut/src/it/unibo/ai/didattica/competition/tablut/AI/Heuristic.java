package it.unibo.ai.didattica.competition.tablut.AI;

import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;

/**
 *
 * @author 
 *
 */

public interface Heuristic {
    double evaluate(StateTablut state);
}
