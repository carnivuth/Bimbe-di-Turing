package it.unibo.ai.didattica.competition.tablut.bimbe;

import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

import java.util.List;

/**
 *
 * @author RootLeo00, carnivuth, hjsteve
 *
 */

public class HeuristicBimbe implements Heuristic {

    /*** WEIGHTS ***/
    public static final int KING_MANHATTAN = 0;
    public static final int KING_CAPTURED_SIDES = 1;
    public static final int PAWNS_DIFFERENCE = 2;
    public static final int PAWNS_WHITE = 3;
    public static final int PAWNS_BLACK = 6;
    public static final int VICTORY = 5;
    public static final int VICTORY_PATH = 4;

    private int[][] escapes;
    private static int[] weight;
    private int color;
    private int depthlimit;


    public HeuristicBimbe(Turn color, int depthlimit) {
        this.escapes = StateUtils.getEscapes();
        this.color = (color.equalsTurn("W") || color.equalsTurn("WW")) ? 1 : -1;
        initWeights();
        this.depthlimit=depthlimit+1;
    }

    public static void setWeight(int[] weight) {
        HeuristicBimbe.weight = weight;
    }

    private void initWeights() {
        weight = new int[7];
        weight[KING_MANHATTAN] = 42;
        weight[KING_CAPTURED_SIDES] = -147;
        weight[PAWNS_DIFFERENCE] = -22; 
        weight[PAWNS_WHITE] = 250; 
        weight[VICTORY_PATH] = 195; 
        weight[VICTORY] = 5000; 
        weight[PAWNS_BLACK] = -164; 
    }

    /**
     * evaluation function based on manhattan distance, number of white pawns and number of black pawns
     */
    @Override
    public double evaluate(BimbeState state,int depth) {

        List<int[]> blackPieces = state.getPawns(Pawn.BLACK);
        List<int[]> whitePieces = state.getPawns(Pawn.WHITE);
        int[] king = state.getKing();
        double v;
        if(winCondition(state.getTurn())!=0){
            return color*weight[VICTORY] *(depthlimit-depth)* winCondition(state.getTurn()) ;
        }else{
            v= weight[KING_MANHATTAN] * kingManhattan(king) +
            weight[PAWNS_WHITE] * whitePieces.size() +
            //weight[VICTORY_PATH] * victoryPaths(king, blackPieces, whitePieces) +
            
            weight[PAWNS_BLACK] * blackPieces.size();
        }
       return color*v;


    }


    /**
     * WEIGHT 0
     * finds the minimum distance between the king object and the objects in the
     * escapes
     * assuming that '6' is the maximum distance from the escapes
     **/
    public double kingManhattan(int[] king) {
        double minDistance = 6;
        for (int[] escape : escapes) {
            double distance = Math.abs(king[0] - escape[0]) + Math.abs(king[1] - escape[1]);
            if (distance < minDistance) {
                minDistance = distance;
            }
        }
        return 6 - minDistance;
    }
    
    /**
     * WEIGHT 3
     * finds the number of open paths to victory for the king (king between two escapes)
     **/
    public int victoryPaths(int[] king, List<int[]> blackPieces, List<int[]> whitePieces) {
        int piecesInTheRow = 0;
        int piecesInTheCol = 0;

        // check if king is in the same row or column of an escape
        if (!StateUtils.isIn(king[0], king[1], StateUtils.possibleOpenPaths))
            return 0;
        else {
            // check if there is a black piece in the same row or column of king
            for (int[] black : blackPieces) {
                if (black[0] == king[0]) {
                    piecesInTheRow++;
                }
                if (black[1] == king[1]) {
                    piecesInTheCol++;
                }
            }
            // check if there is a white piece in the same row or column of king
            for (int[] white : whitePieces) {
                if (white[0] == king[0] || white[1] == king[1]) {
                    if (white[0] == king[0]) {
                        piecesInTheRow++;
                    }
                    if (white[1] == king[1]) {
                        if (piecesInTheRow != 0)
                            return 0;
                        piecesInTheCol++;
                    }
                }
            }
        }
        if (piecesInTheRow != 0 || piecesInTheCol != 0) {
            return 1;
        } else
            return 0;

    }

    /*** WEIGHT 4
     * check if we are on a terminal state
     * ***/
    private double winCondition(Turn turn) {
        if (turn.equalsTurn("WW"))
            return 1;
        if (turn.equalsTurn("BW"))
            return -1;
            return 0;
    }


}