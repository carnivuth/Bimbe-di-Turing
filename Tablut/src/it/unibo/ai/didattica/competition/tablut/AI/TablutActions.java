package ai;

import java.util.HashSet;
import java.util.Set;

import ai.state.GameState;
import ai.state.Pawn;
import ai.state.TablutAction;
import aima.core.agent.Action;
import aima.core.search.framework.problem.ActionsFunction;

public class TablutActions implements ActionsFunction {

    @Override
    public Set<Action> actions(Object s) {
        Set<Action> result=new HashSet<>();
        if(s instanceof GameState){
            
            GameState state = (GameState)s;
            //get moves
            if(state.color==GameState.WHITE){
                for (int i=0;i<state.whitePawns.length;i++) {
                    result.addAll(getPossibleMovements(state.whitePawns[i], state));
                }
                result.addAll(getPossibleMovements(state.king, state));
            }else{
                for (int i=0;i<state.blackPawns.length;i++) {
                    result.addAll(getPossibleMovements(state.blackPawns[i], state));           
                }
            }
        }

        return result;
    }
    public Set<Action> getPossibleMovements(Pawn pawn, GameState state){
        Set<Action> result=new HashSet<>();
        int newColumn=pawn.y-1;

        //move pawn to top
        while(newColumn>=0){
            //check if throne
            if(pawn.x==GameState.throne[0] && newColumn==GameState.throne[1]){
                break;
            }
            //check for citadel cells 
            if(GameState.isIn(pawn, GameState.safeCitadels)&& (pawn.type==GameState.WHITE || !GameState.isIn(pawn,GameState.citadels))){
                break;
            }
            //check for not free cells
            if (state.getState(pawn.x,newColumn)!=GameState.FREE){
                break;
            }
            result.add(new TablutAction(pawn, new int[]{pawn.x,newColumn}));
            newColumn-=1;
        }

        //move pawn to bottom
        newColumn=pawn.y+1;
        while(newColumn<9){
            //check if throne
            if(pawn.x==GameState.throne[0] && newColumn==GameState.throne[1]){
                break;
            }
            //check for citadel cells 
            if(GameState.isIn(pawn, GameState.safeCitadels)&& (pawn.type==GameState.WHITE || !GameState.isIn(pawn,GameState.citadels))){
                break;
            }
            //check for not free cells
            if (state.getState(pawn.x,newColumn)!=GameState.FREE){
                break;
            }
            result.add(new TablutAction(pawn, new int[]{pawn.x,newColumn}));
            newColumn+=1;
        }

         //move pawn to right
         int newRaw=pawn.x-1;
         while(newRaw>=0){
            //check if throne
            if(pawn.y==GameState.throne[1] && newRaw==GameState.throne[0]){
                break;
            }
            //check for citadel cells 
            if(GameState.isIn(pawn, GameState.safeCitadels)&& (pawn.type==GameState.WHITE || !GameState.isIn(pawn,GameState.citadels))){
                break;
            }
            //check for not free cells
            if (state.getState(newRaw,pawn.y)!=GameState.FREE){
                break;
            }
            result.add(new TablutAction(pawn, new int[]{newRaw,pawn.y}));
            newRaw-=1;
        }

        //move pown to left
        newRaw=pawn.x+1;
        while(newRaw<9){

            //check if throne
            if(pawn.y==GameState.throne[1] && newRaw==GameState.throne[0]){
                break;
            }
            //check for citadel cells 
            if(GameState.isIn(pawn, GameState.safeCitadels)&& (pawn.type==GameState.WHITE || !GameState.isIn(pawn,GameState.citadels))){
                break;
            }
            //check for not free cells
            if (state.getState(newRaw,pawn.y)!=GameState.FREE){
                break;
            }
            result.add(new TablutAction(pawn, new int[]{newRaw,pawn.y}));
            newRaw+=1;
        }

        return result;
    }
    
}
