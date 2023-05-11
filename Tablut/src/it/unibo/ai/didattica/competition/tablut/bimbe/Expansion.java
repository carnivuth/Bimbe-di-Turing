package  it.unibo.ai.didattica.competition.tablut.bimbe;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;
import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

/**
 *
 * @author RootLeo00, carnivuth, hjsteve
 *
 */


public class Expansion {

    

    public Set<Action> actions(BimbeState state) {
        Set<Action> result=new HashSet<>();
        int[] king=state.getKing();
     
            //get moves
            if(state.getTurn().equalsTurn("W")){
                result.addAll(getPossibleMovements(state.getPawn(king[0],king[1] ),king[0],king[1], state,state.getTurn()));
               
                for (int[] coordinates : state.getPawns(Pawn.WHITE)) {

                    result.addAll(getPossibleMovements(Pawn.WHITE,coordinates[0],coordinates[1], state,state.getTurn()));
                }
            
            }else{
              
                for (int[] coordinates : state.getPawns(Pawn.BLACK)) {
                    result.addAll(getPossibleMovements(Pawn.BLACK,coordinates[0],coordinates[1], state,state.getTurn()));
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
                
                e.printStackTrace();
            }
            newRaw+=1;
        }

        return result;
    }
        public BimbeState result(BimbeState state, Action action) {
        
        //clone state
        BimbeState result=state.clone();
        int[] king=result.getKing();
        
        if(StateUtils.isIn(king[0],king[1],StateUtils.escapes)){
            result.setTurn(Turn.WHITEWIN);
        }else if(result.kingIsCaptured()==1)
        {
            result.setTurn(Turn.BLACKWIN);
        }else{
            result.setTurn((result.getTurn().equalsTurn("B"))?Turn.WHITE:Turn.BLACK);
        }
        //get future state
        int x= action.getRowFrom();
        int y= action.getColumnFrom();
        int toX= action.getRowTo();
        int toY= action.getColumnTo();

        
        result.getBoard()[toX][toY]=result.getPawn(x, y);
        result.getBoard()[x][y]=Pawn.EMPTY;
        this.checkCapture(result, toX, toY);
       
        //return new state
        return result;
    }

    public void checkCapture(BimbeState state,int x, int y){
        int leftSide=x-1;
        int rightSide=x+1;
        int upSide=y-1;
        int bottomSide=y+1;
        Pawn pawn =state.getPawn(x, y);
        Pawn enemy=(pawn.equalsPawn(Pawn.BLACK))?Pawn.WHITE:Pawn.BLACK;
        //check for limit
        if(bottomSide+1<9){
            if(state.getPawn(x, bottomSide).equalsPawn(enemy)){
                if(state.getPawn(x, bottomSide+1).equalsPawn(pawn) ||
                 (state.getPawn(x, bottomSide+1).equalsPawn(Pawn.THRONE) || StateUtils.isIn(x, bottomSide+1, StateUtils.cornerCitadels) )){
                    ///capture bottom
                    state.deletePawn(x, bottomSide,enemy );
                }
             
            } 
            
        }
        if(upSide-1>=0){
            if(state.getPawn(x, upSide).equalsPawn(enemy)){
                if(state.getPawn(x, upSide-1).equalsPawn(pawn) || 
                (state.getPawn(x, upSide-1).equalsPawn(Pawn.THRONE) || StateUtils.isIn(x, upSide-1, StateUtils.cornerCitadels) )){
                        //capture top
                        state.deletePawn(x, upSide,enemy );
                }
             

            }
              
        }
        if(leftSide-1>=0){
            if(state.getPawn(leftSide, y).equalsPawn(enemy) ){
                if(state.getPawn(leftSide-1, y).equalsPawn(pawn) ||
                (state.getPawn(leftSide-1, y).equalsPawn(Pawn.THRONE) || StateUtils.isIn(leftSide-1, y, StateUtils.cornerCitadels) )){
                    //capture left
                    state.deletePawn(leftSide, y,enemy );
                }


            }   
           
        }
        if(rightSide+1<9){
            if(state.getPawn(rightSide, y).equalsPawn(enemy) ){
                if(state.getPawn(rightSide+1, y).equalsPawn(pawn) ||
                (state.getPawn(rightSide+1, y).equalsPawn(Pawn.THRONE) || StateUtils.isIn(rightSide+1, y, StateUtils.cornerCitadels) )){
                    //capture right
                    state.deletePawn(rightSide, y,enemy );
                }
                

            }    
            
        }
    
        
    }

    
    
}
