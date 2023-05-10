package it.unibo.ai.didattica.competition.tablut.AI;

import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;

import java.util.List;

/**
 *
 * @author RootLeo00
 *
 */

public class HeuristicBimbe implements Heuristic {

    /*** cost ***/
    public static final int KING_MANHATTAN = 0;
    public static final int KING_CAPTURED_SIDES = 1;
    public static final int PAWNS_DIFFERENCE = 2;
    public static final int PAWNS_WHITE = 3;
    public static final int PAWNS_BLACK = 6;
    public static final int VICTORY = 5;
    public static final int VICTORY_PATH = 4;

    private int[][] escapes;
    private static int[] weight;

    /**************** WIN ***********************/



    public HeuristicBimbe() {
        this.escapes = StateUtils.getEscapes();

        initWeights();
    }

    public static void setWeight(int[] weight) {
        HeuristicBimbe.weight = weight;
    }

    private void initWeights() {
        weight = new int[7];

        // double pawnsCoef = (initialBlack) / initialWhite; //(16.0/9.0)

        // POST GENETIC
        weight[KING_MANHATTAN] = 42; // manhattan
        weight[KING_CAPTURED_SIDES] = -147; // king capture
        weight[PAWNS_DIFFERENCE] = -22; // lost pawns
        weight[PAWNS_WHITE] = 250; // white pieces (difference ?)
        weight[VICTORY_PATH] = 195; // victory path
        weight[VICTORY] = 5000; // victory
        weight[PAWNS_BLACK] = -164; // black pieces
    }

    /********************* eval ********************************/
    @Override
    public double evaluate(BimbeState state) {

        // get turn color
        if(state.getTurn().equalsTurn("WW")){
            return Double.POSITIVE_INFINITY;
        }

        if(state.getTurn().equalsTurn("BW")){
            return Double.NEGATIVE_INFINITY;
        }
      
        double color = ((state.getTurn().equalsTurn("W") || state.getTurn().equalsTurn("WW")) ? 1 : -1);

        // pawns
        // Pawn[][] pieces = state.getBoard();

        List<int[]> blackPieces = state.getPawns(Pawn.BLACK);
        List<int[]> whitePieces = state.getPawns(Pawn.WHITE);
        int[] king = state.getKing();

        double V = weight[KING_MANHATTAN] * kingManhattan(king) +
        weight[KING_CAPTURED_SIDES] * kingCapture(king, state) +
        // weight[PAWNS_DIFFERENCE] * lostPaws(blackPieces, whitePieces,
        // state.getTurn()) +
                weight[PAWNS_WHITE] * whitePieces.size() +
                weight[VICTORY_PATH] * victoryPaths(king, blackPieces, whitePieces) +
                // weight[VICTORY] * winCondition(state.getTurn()) +
                weight[PAWNS_BLACK] * blackPieces.size();

        return V * color;
    }

    /******************************************************/

    /********************** FUNC *************************/
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
     * WEIGHT 1
     * finds the number of black pieces or walls that are close to the king
     **/
    public double kingCapture(int[] king, BimbeState state) {
        double count = 0;
        int x = king[0];
        int y = king[1] + 1;

        if (state.isBlackPiece(x, y) || StateUtils.isIn(x, y, StateUtils.citadels)  ||(x==4 && y==4 )) {
            count++;
        }
        x = king[0];
        y = king[1] - 1;
        if (state.isBlackPiece(x, y) || StateUtils.isIn(x, y, StateUtils.citadels) || (x==4 && y==4 )) {
            count++;
        }
        x = king[0] + 1;
        y = king[1];
        if (state.isBlackPiece(x, y) || StateUtils.isIn(x, y, StateUtils.citadels) || (x==4 && y==4 )) {
            count++;
        }
        x = king[0] - 1;
        y = king[1];
        if (state.isBlackPiece(x, y) || StateUtils.isIn(x, y, StateUtils.citadels) || (x==4 && y==4 )) {
            count++;
        }

        //System.out.println("king capture - castls + blacks + citadels: " + count);

        return count;
    }

    



    /**
     * WEIGHT 2
     * finds the difference between the initial number of pawns and the current
     * number of pawns
     **/
   /*  private double lostPaws(List<int[]> black, List<int[]> white, State.Turn turn) {
        // double coeff = 16/9; //per equilibrare la situazione di pezzi
        // return - (coeff)*(initialWhite - white.size()) + (initialBlack -
        // black.size());
        return 0;
    }*/

    /** 3 **/
    // pezzi bianchi

    /**
     * WEIGHT 4
     * finds the number of open paths to victory for the king
     **/
    public int victoryPaths(int[] king, List<int[]> blackPieces, List<int[]> whitePieces) {
        int piecesInTheRow = 0;
        int piecesInTheCol =0;

        // check if king is in the same row or column of an escape
        if (!StateUtils.isIn(king[0], king[1], StateUtils.possibleOpenPaths))
            return 0;
        else {
            // check if there is a black piece in the same row or column of king
            for (int[] black : blackPieces) {
                if (black[0] == king[0] ) {
                    piecesInTheRow++;
                }
                if(black[1] == king[1]){
                    piecesInTheCol++;
                }
            }
            // check if there is a white piece in the same row or column of king
            for (int[] white : whitePieces) {
                if (white[0] == king[0] || white[1] == king[1]) {
                    if (white[0] == king[0] ) {
                        piecesInTheRow++;
                    }
                    if(white[1] == king[1]){
                        if(piecesInTheRow!=0) return 0;
                        piecesInTheCol++;
                    }
                }
            }
        }
        if(piecesInTheRow!=0 || piecesInTheCol!=0) {
            return 1;
        } 
        else return 0;

    }

    /*** 5 ***/
    // vittoria o sconfitta
    // private double winCondition(State.Turn turn, int depth) {
    // if (turn == State.Turn.WHITEWIN)
    // return 1.0 * depthBonus(depth);
    // if (turn == State.Turn.BLACKWIN)
    // return -1.0 * depthBonus(depth);

    // return 0;
    // }

    // // bonus su distanza da root
    // private double depthBonus(int depth) {
    // // return depth == 0 ? 2 : 1;
    // return (double) (depthLimit - depth) / (double) depthLimit + 1.0;
    // }

    /** 6 **/
    // pezzi neri

    public static void printBoard(Pawn[][] board) {
        for (Pawn[] row : board) {
            for (Pawn p : row) {
                System.out.print(p.toString());
            }
            System.out.println();
        }
    }

    public static void printBoard(int[][] board) {
        for (int[] b : board) {
            System.out.println("(" + b[0] + ", " + b[1] + ")");
        }

    }

}