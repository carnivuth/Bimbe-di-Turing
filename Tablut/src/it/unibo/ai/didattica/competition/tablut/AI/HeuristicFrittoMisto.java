package it.unibo.ai.didattica.competition.tablut.AI;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;
import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author E.Cerulo, V.M.Stanzione
 *
 */

public class HeuristicFrittoMisto implements Heuristic {

    /*** cost ***/
    public static final int KING_MANHATTAN = 0;
    public static final int KING_CAPTURED_SIDES = 1;
    public static final int PAWNS_DIFFERENCE = 2;
    public static final int PAWNS_WHITE = 3;
    public static final int PAWNS_BLACK = 6;
    public static final int VICTORY = 5;
    public static final int VICTORY_PATH = 4;

    private int[] castle;
    private int[][] citadels;
    private int[][] winPos;
    private static double[] weight;

    /*** color ***/
    private final State.Turn playerColor; // il colore del client
    private final double color;

    /**************** WIN ***********************/

    public HeuristicFrittoMisto(StateTablut state) {
        this.playerColor = state.getTurn();

        this.color = ((playerColor == State.Turn.WHITE || playerColor == State.Turn.WHITEWIN) ? 1 : -1);

        initWeights();
    }

    public static void setWeight(double[] weight) {
        HeuristicFrittoMisto.weight = weight;
    }

    private void initWeights() {
        weight = new double[7];

        // double pawnsCoef = (initialBlack) / initialWhite; //(16.0/9.0)

        /*
         * weight[KING_MANHATTAN] = 50; //manhattan
         * weight[KING_CAPTURED_SIDES] = -100; //king capture
         * weight[PAWNS_DIFFERENCE] = 100; //lost pawns
         * weight[PAWNS_WHITE] = 100 * pawnsCoef; //white pieces (difference ?)
         * weight[VICTORY_PATH] = 300; //victory path
         * weight[VICTORY] = 5000; //victory
         * weight[PAWNS_BLACK] = -100; //black pieces
         */

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
    public double evaluate(StateTablut state) {

        // double color = ((playerColor == State.Turn.WHITE || playerColor ==
        // State.Turn.WHITEWIN) ? 1 : -1);

        // pawns
        Pawn[][] pieces = state.getBoard();

        List<int[]> blackPieces = StateUtils.getPawns(state, Pawn.BLACK.toString());
        List<int[]> whitePieces = StateUtils.getPawns(state, Pawn.WHITE.toString());
        int[] king = StateUtils.getKing(state);

        double V = weight[KING_MANHATTAN] * kingManhattan(king) +
                weight[KING_CAPTURED_SIDES] * kingCapture(king, blackPieces) +
                // weight[PAWNS_DIFFERENCE] * lostPaws(blackPieces, whitePieces,
                // state.getTurn()) +
                weight[PAWNS_WHITE] * whitePieces.size() +
                // weight[VICTORY_PATH] * victoryPaths(king, blackPieces, whitePieces) +
                // weight[VICTORY] * winCondition(state.getTurn()) +
                weight[PAWNS_BLACK] * blackPieces.size();

        return V * color;
    }

    /******************************************************/

    /********************** FUNC *************************/
    /**
     * WEIGHT 0
     * finds the minimum distance between the king object and the objects in the
     * winPos
     * assuming that '6' is the maximum distance from the winPos
     **/
    private double kingManhattan(int[] king) {
        double minDistance = 6;
        for (int[] row : winPos) {
            for (int col : row) {
                double distance = king[0] - row[0] + king[1] - col;
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }

        }
        return 6 - minDistance;
    }

    /**
     * WEIGHT 1
     * finds the number of black pieces or walls that are close to the king
     **/
    private double kingCapture(int[] king, List<int[]> blackPieces) {
        double count = 0;

        // black pieces near king
        for (int[] black : blackPieces) {
            if (Math.abs(king[0] - black[0]) <= 1 && Math.abs(king[1] - black[1]) <= 1) {
                count++;
            }
        }

        // castle near king
        if (king[0] == castle[0] && Math.abs(king[1] - castle[1]) <= 1
                || king[1] == castle[1] && Math.abs(king[0] - castle[0]) <= 1) {
            count++;
        }

        // citadels near king
        for (int[] citadel : citadels) {
            if (Math.abs(king[0] - citadel[0]) <= 1 && Math.abs(king[1] - citadel[1]) <= 1) {
                count++;
            }
        }

        return count;
    }

    /**
     * WEIGHT 2
     * finds the difference between the initial number of pawns and the current
     * number of pawns
     **/
    private double lostPaws(List<int[]> black, List<int[]> white, State.Turn turn) {
        // double coeff = 16/9; //per equilibrare la situazione di pezzi
        // return - (coeff)*(initialWhite - white.size()) + (initialBlack -
        // black.size());
        return 0;
    }

    /** 3 **/
    // pezzi bianchi

    /**
     * WEIGHT 4
     * finds the number of open paths to victory for the king
     **/
    public double victoryPaths(int[] king, List<int[]> blackPieces, List<int[]> whitePieces) {
        int paths = 0;
        // check if king is in the same row or column of an escape
        List<int[]> victoryPos = victoryRoads(king);

        if (victoryPos.isEmpty())
            return 0;

        // check if there are pieces between the king and the victoryPos
        for (int[] victory : victoryPos) {
            for (int[] black : blackPieces) {
                // check if piece is in the same row of the victoryPos
                if (black[0] == victory[0]) {
                    return 0;
                }
                // check if piece is in the same column of the victoryPos
                if (black[1] == victory[1]) {
                    return 0;
                }
            }
            for (int[] white : whitePieces) {
                // check if piece is in the same row of the victoryPos
                if (white[0] == victory[0]) {
                    return 0;
                }
                // check if piece is in the same column of the victoryPos
                if (white[1] == victory[1]) {
                    return 0;
                }
            }
            paths++;
        }
        return paths;

    }

    /***
     * check if king is in the same row or column of an escape
     ***/
    private List<int[]> victoryRoads(int[] king) {
        List<int[]> victoryPos = new ArrayList<int[]>();
        //check column
        for (int[] row_escape : StateUtils.getEscapes()) {
            for (int r : row_escape) {
                if (king[0] == r) {
                    victoryPos.add(row_escape);
                }
            }
        }
        //check row
        return victoryPos;
    }

    /*** 5 ***/
    // vittoria o sconfitta
    // private double winCondition(State.Turn turn, int depth) {
    //     if (turn == State.Turn.WHITEWIN)
    //         return 1.0 * depthBonus(depth);
    //     if (turn == State.Turn.BLACKWIN)
    //         return -1.0 * depthBonus(depth);

    //     return 0;
    // }

    // // bonus su distanza da root
    // private double depthBonus(int depth) {
    //     // return depth == 0 ? 2 : 1;
    //     return (double) (depthLimit - depth) / (double) depthLimit + 1.0;
    // }

    /** 6 **/
    // pezzi neri
}