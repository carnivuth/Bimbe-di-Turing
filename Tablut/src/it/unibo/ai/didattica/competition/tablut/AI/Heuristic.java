package it.unibo.ai.didattica.competition.tablut.AI;

import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;

/**
 *
 * @author E.Cerulo, V.M.Stanzione
 *
 */

public interface Heuristic {
    double evaluate(StateTablut state );
}