package  it.unibo.ai.didattica.competition.tablut.AI;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;
import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;


public class Expansion {

    public static final int [][] citadels ={{0,3},{0,4},{0,4},{1,4},
                                            {8,3},{8,4},{8,5},{7,4},
                                            {3,8},{4,8},{5,8},{4,7},
                                            {3,0},{4,0},{5,0},{4,1}};
    
    public static final int [][] safeCitadels ={{0,3},{0,4},{0,4},
                                            {8,3},{8,4},{8,5},
                                            {3,8},{4,8},{5,8},
                                            {3,0},{4,0},{5,0}};

    public Set<Action> actions(StateTablut state) {
        Set<Action> result=new HashSet<>();
     
            //get moves
            if(state.getTurn().equals(Turn.WHITE)){
                for (int i=0;i<state.getBoard().length;i++) {
                    for (int j=0;j<state.getBoard().length;j++) {
                        if(state.getPawn(i, j).equals(Pawn.WHITE)){
                     
                            result.addAll(getPossibleMovements(state.getPawn(i, j),i,j, state));
                        }
                        if(state.getPawn(i, j).equals(Pawn.KING)){
                     
                            result.addAll(getPossibleMovements(state.getPawn(i, j),i,j, state));
                        }
                    }
                }
            }else{
                for (int i=0;i<state.getBoard().length;i++) {
                    for (int j=0;j<state.getBoard().length;j++) {
                        if(state.getPawn(i, j).equals(Pawn.BLACK)){
                     
                            result.addAll(getPossibleMovements(state.getPawn(i, j),i,j, state));
                        }
                    
                    }
                }
            }
        

        return result;
    }
    public Set<Action> getPossibleMovements(Pawn pawn,int x,int y, StateTablut state){
        Set<Action> result=new HashSet<>();
        int newColumn=y-1;

        //move pawn to top
        while(newColumn>=0){
            //check if throne
            if(state.getPawn(x, newColumn).equals(Pawn.THRONE)){
                break;
            }
            //check for citadel cells 
           if(isIn(x, newColumn, safeCitadels)&& (state.getTurn().equalsTurn("WHITE")|| !isIn(x,newColumn,citadels))){
            break;
            }

            //check for not free cells
            if (!state.getPawn(x, newColumn).equals(Pawn.EMPTY)){
                break;
            }
            try {
                result.add(new Action(createActionString(x, y),createActionString(x, newColumn),state.getTurn() ));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            newColumn-=1;
        }

        //move pawn to bottom
        newColumn=y+1;
        while(newColumn<9){
            //check if throne
            if(state.getPawn(x, newColumn).equals(Pawn.THRONE)){
                break;
            }
            //check for citadel cells 
            if(isIn(x, newColumn, safeCitadels)&& (state.getTurn().equalsTurn("WHITE")|| !isIn(x,newColumn,citadels))){
                break;
            }

            //check for not free cells
            if (!state.getPawn(x, newColumn).equals(Pawn.EMPTY)){
                break;
            }
            try {
                result.add(new Action(createActionString(x, y),createActionString(x, newColumn),state.getTurn() ));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            newColumn+=1;
        }

         //move pawn to right
         int newRaw=x-1;
         while(newRaw>=0){
            //check if throne
            if(state.getPawn(newRaw, y).equals(Pawn.THRONE)){
                break;
            }
            //check for citadel cells 
            if(isIn(newRaw, y, safeCitadels)&& (state.getTurn().equalsTurn("WHITE")|| !isIn(newRaw,y,citadels))){
                break;
            }
            
            //check for not free cells
            if (!state.getPawn(newRaw, y).equals(Pawn.EMPTY)){
                break;
            }
            try {
                result.add(new Action(createActionString(x, y),createActionString(newRaw, y),state.getTurn() ));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            newRaw-=1;
        }

        //move pown to left
        newRaw=x+1;
        while(newRaw<9){

            //check if throne
            if(state.getPawn(newRaw, y).equals(Pawn.THRONE)){
                break;
            }

            //check for citadel cells 
            if(isIn(newRaw, y, safeCitadels)&& (state.getTurn().equalsTurn("WHITE")|| !isIn(newRaw,y,citadels))){
                break;
            }
            
            //check for not free cells
            if (!state.getPawn(newRaw, y).equals(Pawn.EMPTY)){
                break;
            }
            try {
                result.add(new Action(createActionString(x, y),createActionString(newRaw, y),state.getTurn() ));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            newRaw+=1;
        }

        return result;
    }
    private String createActionString(int x, int y){
        String column="";

        switch(y+1){
            case 1:
            column="a";
            break;
            case 2:
            column="b";
            break;
            case 3:
            column="c";
            break;
            case 4:
            column="d";
            break;
            case 5:
            column="e";
            break;
            case 6:
            column="f";
            break;
            case 7:
            column="g";
            break;
            case 8:
            column="h";
            break;
            case 9:
            column="i";
            break;
            
        }
        return column+Integer.toString(x+1);

    }
    public StateTablut result(StateTablut state, Action action) {
        //TODO
        //clone state
        StateTablut result=state.clone();
        //get future state
        int x= action.getRowFrom();
        int y= action.getColumnFrom();
        int toX= action.getRowTo();
        int toY= action.getColumnTo();

        
        result.getBoard()[toX][toY]=result.getPawn(x, y);
        result.getBoard()[x][y]=Pawn.EMPTY;
        //check for rules integrity

        //return new state
        return result;
    }

    public static boolean isIn(int x,int y, int[][] elements){
        for(int i=0;i<elements.length;i++){
            if(x==elements[i][0] &&y==elements[i][1])return true;
        }
        return false;

    }
    
}
