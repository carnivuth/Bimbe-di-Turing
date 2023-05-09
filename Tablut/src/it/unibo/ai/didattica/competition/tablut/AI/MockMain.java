package it.unibo.ai.didattica.competition.tablut.AI;

import java.util.ArrayList;
import java.util.List;

import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;

public class MockMain {

    public static void main(String[] args) {

        System.out.println("Test");
        // create board example
        Pawn[][] board = new Pawn[9][9];
        /*
         * populate board with :
         * [java] OOBBOBOOO
         * [java] OOOBOOWOO
         * [java] OOOOOOOOO
         * [java] OBOBWOOBB
         * [java] OBOWKWOOB
         * [java] BOOOWOOOO
         * [java] OOWOOOWOB
         * [java] OOOOOOOOB
         * [java] OOOBBBOOO
         */

        // populate with empty
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = Pawn.EMPTY;
            }
        }

        // populate with black and white
        // row 0: OOBBOBOOO
        board[0][2] = Pawn.BLACK;
        board[0][3] = Pawn.BLACK;
        board[0][5] = Pawn.BLACK;

        // row 1: OOOBOOWOO
        board[1][4] = Pawn.EMPTY;
        board[1][6] = Pawn.KING;
        board[1][7] = Pawn.EMPTY;

        // row 3: OBOBWOOBB
        board[3][1] = Pawn.BLACK;
        board[3][3] = Pawn.BLACK;
        board[3][4] = Pawn.WHITE;
        board[3][7] = Pawn.BLACK;
        board[3][8] = Pawn.BLACK;

        // row 4: OBOWKWOOB
        board[4][1] = Pawn.BLACK;
        board[4][3] = Pawn.WHITE;
        board[4][4] = Pawn.WHITE;
        board[4][5] = Pawn.WHITE;
        board[4][8] = Pawn.BLACK;

        // row 5: BOOOWOOOO
        board[5][0] = Pawn.BLACK;
        board[5][4] = Pawn.WHITE;

        // row 6: OOWOOOWOB
        board[6][2] = Pawn.WHITE;
        board[6][6] = Pawn.WHITE;
        board[6][8] = Pawn.WHITE;

        // row 7: OOOOOOOOB
        board[7][8] = Pawn.BLACK;

        // row 8: OOOBBBOOO
        board[8][3] = Pawn.BLACK;
        board[8][4] = Pawn.BLACK;
        board[8][5] = Pawn.BLACK;

        // instanziate class

        HeuristicFrittoMisto h = new HeuristicFrittoMisto(board);

        // get black pawns
        List<int[]> blackPawns = getPawns(board, "B");

        // get white pawns
        List<int[]> whitePawns = getPawns(board, "W");

        System.out.println("Board created");
        printBoard(board);

        // king
        int[] king = new int[2];
        king[0] = 1;
        king[1] = 1;

        int result = h.victoryPaths(king, blackPawns, whitePawns);
        System.out.println("Result: " + result);
    }

    public static void printBoard(Pawn[][] board) {
        for (Pawn[] row : board) {
            for (Pawn p : row) {
                System.out.print(p.toString());
            }
            System.out.println();
        }
    }

    public static List<int[]> getPawns(Pawn[][] board, String color) {
        int[] coordinates;
        List<int[]> result = new ArrayList<int[]>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].equalsPawn(color)) {

                    coordinates = new int[2];
                    coordinates[0] = i;
                    coordinates[1] = j;
                    result.add(coordinates);
                }

            }
        }
        return result;
    }
}
