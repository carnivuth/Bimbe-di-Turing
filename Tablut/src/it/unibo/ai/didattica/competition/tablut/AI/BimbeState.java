package it.unibo.ai.didattica.competition.tablut.AI;

import java.util.ArrayList;
import java.util.List;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;

/**
 *
 * @author RootLeo00, carnivuth, hjsteve
 *
 */

public class BimbeState extends StateTablut{
    private List<int []> blackPawns;
    private List<int []> whitePawns;
   
    public BimbeState(State state){
        this.board=state.getBoard();
        blackPawns=initPawns(state,Pawn.BLACK);
        whitePawns=initPawns(state,Pawn.WHITE);
        this.setTurn(state.getTurn());

    }
    public BimbeState(){

    }
 

    public List<int[]> getPawns(Pawn color){
        return (color.equalsPawn(Pawn.WHITE))?this.whitePawns:this.blackPawns;
    }

    public  List<int[]> initPawns(State state, Pawn color) {
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

        BimbeState result = new BimbeState();

		Pawn oldboard[][] = this.getBoard();
		Pawn newboard[][] = result.getBoard();

		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				newboard[i][j] = oldboard[i][j];
			}
		}

		result.setBoard(newboard);
		result.setTurn(this.turn);
        result.blackPawns=new ArrayList<>(this.blackPawns);
        result.whitePawns=new ArrayList<>(this.whitePawns);
		return result;
    }

    public void deletePawn(int x, int y,Pawn color){

        if(color.equalsPawn(Pawn.WHITE)){
            for (int i =0; i<this.whitePawns.size();i++){
                if (whitePawns.get(i)[0]==x &&whitePawns.get(i)[1]==y){
                    whitePawns.remove(i);
                    this.board[x][y]=Pawn.EMPTY;
                    return;
                }
            }
        }else {
            for (int i =0; i<this.blackPawns.size();i++){
                if (blackPawns.get(i)[0]==x &&blackPawns.get(i)[1]==y){
                    blackPawns.remove(i);
                    this.board[x][y]=Pawn.EMPTY;
                    return;
                }
            }
        }
    }
    public  int[] getKing() {

        int[] result = new int[2];
        for (int i = 0; i < this.getBoard().length; i++) {
            for (int j = 0; j < this.getBoard().length; j++) {
                if (this.getPawn(i, j).equalsPawn("K")) {

                    result[0] = i;
                    result[1] = j;
                    return result;
                }

            }
        }
        return result;
    }
    public boolean isBlackPiece(int x, int y) {
        for (int[] is : this.blackPawns) {
            if (is[0] == x && is[1] == y)
                return true;
        }
        return false;
    }
    public double kingIsCaptured() {
        double count = 0;
        int[] king= getKing();
        int x = king[0];
        int y = king[1] + 1;

        //special cases for king
        if (this.isBlackPiece(x, y) || StateUtils.isIn(x, y, StateUtils.citadels)  ||(x==4 && y==4 )) {
            count++;
        }
        x = king[0];
        y = king[1] - 1;
        if (this.isBlackPiece(x, y) || StateUtils.isIn(x, y, StateUtils.citadels) || (x==4 && y==4 )) {
            count++;
        }
        x = king[0] + 1;
        y = king[1];
        if (this.isBlackPiece(x, y) || StateUtils.isIn(x, y, StateUtils.citadels) || (x==4 && y==4 )) {
            count++;
        }
        x = king[0] - 1;
        y = king[1];
        if (this.isBlackPiece(x, y) || StateUtils.isIn(x, y, StateUtils.citadels) || (x==4 && y==4 )) {
            count++;
        }
        if (count==4)return 1;
        
        //check for normal captures
        if(king[0]-1<9 &&king[0]+1<9 && this.isBlackPiece(king[0]-1, king[1]) && this.isBlackPiece(king[0]+1, king[1])) return 1;
        if(king[1]-1<9 &&king[1]+1<9 && this.isBlackPiece(king[0], king[1]-1) && this.isBlackPiece(king[0], king[1]+1)) return 1;
        

        return 0;
    }
}
