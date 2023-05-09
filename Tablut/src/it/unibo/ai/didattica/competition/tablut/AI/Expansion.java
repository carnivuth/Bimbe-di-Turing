package  it.unibo.ai.didattica.competition.tablut.AI;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;
import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;


public class Expansion {

    

    public Set<Action> actions(StateTablut state,List<int []> blackPawns,int[] king,List<int []> whitePawns,Turn color) {
        Set<Action> result=new HashSet<>();
     
            //get moves
            if(color.equalsTurn("W")){
                result.addAll(getPossibleMovements(state.getPawn(king[0],king[1] ),king[0],king[1], state,color));
               
                for (int[] coordinates : whitePawns) {
                    result.addAll(getPossibleMovements(state.getPawn(coordinates[0],coordinates[1] ),coordinates[0],coordinates[1], state,color));
                }
            
            }else{
              
                for (int[] coordinates : blackPawns) {
                    result.addAll(getPossibleMovements(state.getPawn(coordinates[0],coordinates[1] ),coordinates[0],coordinates[1], state,color));
                }
            }
        

        return result;
    }
    public Set<Action> getPossibleMovements(Pawn pawn,int x,int y, StateTablut state,Turn color){
        Set<Action> result=new HashSet<>();
        int newColumn=y-1;

        //move pawn to top
        while(newColumn>=0){
            //check if throne
            if(state.getPawn(x, newColumn).equals(Pawn.THRONE)){
                break;
            }
            //check for citadel cells 
           if(StateUtils.isIn(x, newColumn, StateUtils.citadels)&& color.equalsTurn("W")){
            break;
            }
            if(StateUtils.isIn(x, newColumn, StateUtils.citadels)&& color.equalsTurn("B") && !StateUtils.isIn(x, y,StateUtils.citadels)){
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
            if(StateUtils.isIn(x, newColumn, StateUtils.citadels)&& color.equalsTurn("W")){
                break;
            }
             if(StateUtils.isIn(x, newColumn, StateUtils.citadels)&& color.equalsTurn("B") && !StateUtils.isIn(x, y,StateUtils.citadels)){
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
            if(StateUtils.isIn(newRaw, y, StateUtils.citadels)&& color.equalsTurn("W")){
                break;
            }
            if(StateUtils.isIn(newRaw, y, StateUtils.citadels)&& color.equalsTurn("B") && !StateUtils.isIn(x, y,StateUtils.citadels)){
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
            if(StateUtils.isIn(newRaw, y, StateUtils.citadels)&& color.equalsTurn("W")){
                break;
            }
            if(StateUtils.isIn(newRaw, y, StateUtils.citadels)&& color.equalsTurn("B") && !StateUtils.isIn(x, y,StateUtils.citadels)){
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
        //TODO check for captured pawns and update lists
        
        //return new state
        return result;
    }

    
    
}
