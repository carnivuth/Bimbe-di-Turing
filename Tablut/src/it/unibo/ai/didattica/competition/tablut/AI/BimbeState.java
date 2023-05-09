package it.unibo.ai.didattica.competition.tablut.AI;

import java.util.ArrayList;
import java.util.List;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public class BimbeState {
    private List<int []> blackPawns;
    private List<int []> whitePawns;
    public BimbeState(State state){
        blackPawns=initPawns(state,"B");
        whitePawns=initPawns(state,"W");

    }
    public List<int[]> getPawns(String color){
        return (color.equals("W"))?whitePawns:blackPawns;
    }
    public  List<int[]> initPawns(State state, String color) {
        int[] coordinates;
        List<int[]> result = new ArrayList<int[]>();
        for (int i = 0; i < state.getBoard().length; i++) {
            for (int j = 0; j < state.getBoard().length; j++) {
                if (state.getPawn(i, j).equalsPawn(color)) {

                    coordinates = new int[2];
                    coordinates[0] = i;
                    coordinates[1] = j;
                    result.add(coordinates);
                }

            }
        }
        return result;
    }
}
