package  it.unibo.ai.didattica.competition.tablut.AI;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;
import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;


public class Expansion {

    

    public Set<Action> actions(StateTablut state,List<int []> blackPawns,int[] king,List<int []> whitePawns,String color) {
        Set<Action> result=new HashSet<>();
     
            //get moves
            if(color.equals("WHITE")){
                for (int[] coordinates : whitePawns) {
                    result.addAll(getPossibleMovements(state.getPawn(coordinates[0],coordinates[1] ),coordinates[0],coordinates[1], state,color));
                }
                result.addAll(getPossibleMovements(state.getPawn(king[0],king[1] ),king[0],king[1], state,color));


                /*for (int i=0;i<state.getBoard().length;i++) {
                    for (int j=0;j<state.getBoard().length;j++) {
                        if(state.getPawn(i, j).equals(Pawn.WHITE)){
                     
                            result.addAll(getPossibleMovements(state.getPawn(i, j),i,j, state));
                        }
                        if(state.getPawn(i, j).equals(Pawn.KING)){
                     
                            result.addAll(getPossibleMovements(state.getPawn(i, j),i,j, state));
                        }
                    }
                }*/
            }else{
                for (int[] coordinates : blackPawns) {
                    result.addAll(getPossibleMovements(state.getPawn(coordinates[0],coordinates[1] ),coordinates[0],coordinates[1], state,color));
                }
                /*for (int i=0;i<state.getBoard().length;i++) {
                    for (int j=0;j<state.getBoard().length;j++) {
                        if(state.getPawn(i, j).equals(Pawn.BLACK)){
                     
                            result.addAll(getPossibleMovements(state.getPawn(i, j),i,j, state));
                        }
                    
                    }
                }*/
            }
        

        return result;
    }
    public Set<Action> getPossibleMovements(Pawn pawn,int x,int y, StateTablut state,String color){
        Set<Action> result=new HashSet<>();
        int newColumn=y-1;

        //move pawn to top
        while(newColumn>=0){
            //check if throne
            if(state.getPawn(x, newColumn).equals(Pawn.THRONE)){
                break;
            }
            //check for citadel cells 
           if(StateUtils.isIn(x, newColumn, StateUtils.citadels)&& color.equals("WHITE")){
            break;
            }
            if(StateUtils.isIn(x, newColumn, StateUtils.citadels)&& color.equals("BLACK") && !StateUtils.isIn(x, y,StateUtils.citadels)){
                break;
            }

            //check for not free cells
            if (!state.getPawn(x, newColumn).equals(Pawn.EMPTY)){
                break;
            }
            try {
                result.add(new Action(StateUtils.createActionString(x, y),StateUtils.createActionString(x, newColumn),state.getTurn() ));
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
            if(StateUtils.isIn(x, newColumn, StateUtils.citadels)&& color.equals("WHITE")){
                break;
            }
             if(StateUtils.isIn(x, newColumn, StateUtils.citadels)&& color.equals("BLACK") && !StateUtils.isIn(x, y,StateUtils.citadels)){
                break;
            }

            //check for not free cells
            if (!state.getPawn(x, newColumn).equals(Pawn.EMPTY)){
                break;
            }
            try {
                result.add(new Action(StateUtils.createActionString(x, y),StateUtils.createActionString(x, newColumn),state.getTurn() ));
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
            if(StateUtils.isIn(newRaw, y, StateUtils.citadels)&& color.equals("WHITE")){
                break;
            }
            if(StateUtils.isIn(newRaw, y, StateUtils.citadels)&& color.equals("BLACK") && !StateUtils.isIn(x, y,StateUtils.citadels)){
                break;
            }

            //check for not free cells
            if (!state.getPawn(newRaw, y).equals(Pawn.EMPTY)){
                break;
            }
            try {
                result.add(new Action(StateUtils.createActionString(x, y),StateUtils.createActionString(newRaw, y),state.getTurn() ));
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
            if(StateUtils.isIn(newRaw, y, StateUtils.citadels)&& color.equals("WHITE")){
                break;
            }
            if(StateUtils.isIn(newRaw, y, StateUtils.citadels)&& color.equals("BLACK") && !StateUtils.isIn(x, y,StateUtils.citadels)){
                break;
            }
            
            //check for not free cells
            if (!state.getPawn(newRaw, y).equals(Pawn.EMPTY)){
                break;
            }
            try {
                result.add(new Action(StateUtils.createActionString(x, y),StateUtils.createActionString(newRaw, y),state.getTurn() ));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            newRaw+=1;
        }

        return result;
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

    
    
}
