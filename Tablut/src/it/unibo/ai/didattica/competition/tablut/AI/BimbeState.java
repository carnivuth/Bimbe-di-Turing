package it.unibo.ai.didattica.competition.tablut.AI;

import java.util.ArrayList;
import java.util.List;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;

public class BimbeState extends StateTablut{
    private List<int []> blackPawns;
    private List<int []> whitePawns;
   
    public BimbeState(State state){
        this.board=state.getBoard();
        blackPawns=initPawns(state,"B");
        whitePawns=initPawns(state,"W");

    }
    public BimbeState(State state,List<int []> blackPawns,List<int []> whitePawns){
        super.clone();
        this.blackPawns=new ArrayList<>(blackPawns);
        this.whitePawns=new ArrayList<>(blackPawns);

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
    public BimbeState clone(){
        BimbeState result =new BimbeState(this,this.blackPawns,this.whitePawns);
        return result;
    }
    public void deletePawn(int x, int y,Pawn color){
        this.board[x][y]=Pawn.EMPTY;
        if(color.equalsPawn(Pawn.WHITE)){
            for (int i =0; i<this.whitePawns.size();i++){
                if (whitePawns.get(i)[0]==x &&whitePawns.get(i)[1]==y){
                    whitePawns.remove(i);
                    return;
                }
            }
        }else{
            for (int i =0; i<this.blackPawns.size();i++){
                if (blackPawns.get(i)[0]==x &&blackPawns.get(i)[1]==y){
                    blackPawns.remove(i);
                    return;
                }
            }
        }
    }
}
