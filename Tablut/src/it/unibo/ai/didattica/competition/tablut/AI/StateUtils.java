package it.unibo.ai.didattica.competition.tablut.AI;

import java.util.ArrayList;
import java.util.List;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public class StateUtils {

    public static final int[][] citadels = { { 0, 3 }, { 0, 4 }, { 0, 5 }, { 1, 4 },
            { 8, 3 }, { 8, 4 }, { 8, 5 }, { 7, 4 },
            { 3, 8 }, { 4, 8 }, { 5, 8 }, { 4, 7 },
            { 3, 0 }, { 4, 0 }, { 5, 0 }, { 4, 1 } };

    public static final int[][] safeCitadels = { { 0, 3 }, { 0, 4 }, { 0, 5 },
            { 8, 3 }, { 8, 4 }, { 8, 5 },
            { 3, 8 }, { 4, 8 }, { 5, 8 },
            { 3, 0 }, { 4, 0 }, { 5, 0 } };

    public static final int[][] escapes = {{0,1},{0,2},{0,6},{0,7}, 
                        {8,1},{8,2},{8,6},{8,7},
                        {1,0},{2,0},{6,0},{7,0},
                        {1,8},{2,8},{6,8},{7,8}};
                        public static final int[][] possibleOpenPaths = {{0,1},{0,2},{0,6},{0,7}, 
                        {8,1},{8,2},{8,6},{8,7},
                        {1,0},{2,0},{6,0},{7,0},
                        {1,8},{2,8},{6,8},{7,8}};

    public static final int[] castle = { 4, 4 };

    public static boolean isIn(int x, int y, int[][] elements) {
        for (int i = 0; i < elements.length; i++) {
            if (x == elements[i][0] && y == elements[i][1])
                return true;
        }
        return false;

    }


    public static List<int[]> getPawns(State state, String color) {
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


    public static int[] getKing(State state) {

        int[] result = new int[2];
        for (int i = 0; i < state.getBoard().length; i++) {
            for (int j = 0; j < state.getBoard().length; j++) {
                if (state.getPawn(i, j).equalsPawn("K")) {

                    result[0] = i;
                    result[1] = j;
                }

            }
        }
        return result;
    }

    public static String createActionString(int x, int y) {
        
        return Character.toString(Character.toChars(y+97)[0]) + Integer.toString(x + 1);

    }

    public static String printPawns(List<int[]> pawns) {
        String result = "";
        for (int[] pawn : pawns) {
            result += Integer.toString(pawn[0]) + Integer.toString(pawn[1]) + "\n";

        }
        return result;
    }

    public static int[] getCastle() {
        return castle;
    }

    public static boolean isCastle(int[] position) {
        if (position[0] == castle[0] && position[1] == castle[1])
            return true;
        return false;
    }

    public static boolean isCastle(int row, int col) {
        if (row == castle[0] && col == castle[1])
            return true;
        return false;
    }

    public static int[][] getCitadels() {
        return citadels;
    }

    public static int[][] getSafecitadels() {
        return safeCitadels;
    }

    public static int[][] getEscapes() {
        return escapes;
    }

    

}
