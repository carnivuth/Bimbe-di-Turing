package it.unibo.ai.didattica.competition.tablut.AI;

import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;
import it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;

public class Heuristic {

    private double[] weights;

    public Heuristic() {
        // initialize weights with [10, -1, 1, 1, 1, -2, -4, 2, 5, 1, 1]
        this.weights = new double[11];
        this.weights[0] = 10;
        this.weights[1] = -1;
        this.weights[2] = 1;
        this.weights[3] = 1;
        this.weights[4] = 1;
        this.weights[5] = -2;
        this.weights[6] = -4;
        this.weights[7] = 2;
        this.weights[8] = 5;
        this.weights[9] = 1;
        this.weights[10] = 1;
    }

    public double evaluate(StateTablut state) {

        // get white and black pawns
        int w = state.getNumberOf(StateTablut.Pawn.WHITE);
        int b = state.getNumberOf(StateTablut.Pawn.BLACK);
        int[] king_pos = StateUtils.getKing(state);

        double val = (weights[7] * (weights[9] * w - weights[10] * b) + weights[8] * evalKingPos(state, king_pos));

        return val;
    }

    public int evalKingPos(StateTablut state, int[] king_pos) {
        int row = king_pos[0];
        int col = king_pos[1];
        int score = 0;

        // analyze column movement
        int moveCol = col - 1;
        while (moveCol >= 0) {
            if (StateUtils.isCastle(row, moveCol)) {
                score += weights[2];
                break;
            }
            if (state.getPawn(row, moveCol).equalsPawn(Pawn.BLACK)) {
                if (moveCol == col - 1) {
                    score += weights[6];
                } else {
                    score += weights[5];
                }
                break;
            }
            if (state.getPawn(row, moveCol).equalsPawn(Pawn.WHITE)) {
                if (moveCol == col - 1) {
                    score += weights[4];
                } else {
                    score += weights[3];
                }
                break;
            }
            // ciclo su tutte le coppie row,col di citadels per trovarne una corrispondente
            boolean stop = false;
            for (int r = 0; !stop && r < StateUtils.getCitadels()[0].length; r++) {
                for (int c = 0; c < StateUtils.getCitadels()[1].length; c++) {
                    if (r == row && c == moveCol) {
                        score += weights[1];
                        stop = true;
                        break;
                    }
                }
            }
            stop = false;
            // ciclo su tutte le coppie row,col di escapes per trovarne una corrispondente
            for (int r = 0; !stop && r < StateUtils.getEscapes()[0].length; r++) {
                for (int c = 0; c < StateUtils.getEscapes()[1].length; c++) {
                    if (r == row && c == moveCol) {
                        score += weights[0];
                        stop = true;
                        break;
                    }
                }
            }
            moveCol -= 1;

        }

        moveCol = col + 1;
        while (moveCol < 9) {
            if (StateUtils.isCastle(row, moveCol)) {
                score += weights[2];
                break;
            }
            if (state.getPawn(row, moveCol).equalsPawn(Pawn.BLACK)) {
                if (moveCol == col + 1) {
                    score += weights[6];
                } else {
                    score += weights[5];
                }
                break;
            }
            if (state.getPawn(row, moveCol).equalsPawn(Pawn.WHITE)) {
                if (moveCol == col + 1) {
                    score += weights[4];
                } else {
                    score += weights[3];
                }
                break;
            }
            // ciclo su tutte le coppie row,col di citadels per trovarne una corrispondente
            boolean stop = false;
            for (int r = 0; !stop && r < StateUtils.getCitadels()[0].length; r++) {
                for (int c = 0; c < StateUtils.getCitadels()[1].length; c++) {
                    if (r == row && c == moveCol) {
                        score += weights[1];
                        stop = true;
                        break;
                    }
                }
            }
            stop = false;
            for (int r = 0; !stop && r < StateUtils.getEscapes()[0].length; r++) {
                for (int c = 0; c < StateUtils.getEscapes()[1].length; c++) {
                    if (r == row && c == moveCol) {
                        score += weights[0];
                        stop = true;
                        break;
                    }
                }
            }

            moveCol += 1;
        }

        // analyze row movement
        int moveRow = row - 1;
        while (moveRow >= 0) {
            if (StateUtils.isCastle(moveRow, col)) {
                score += weights[2];
                break;
            }
            if (state.getPawn(moveRow, col).equalsPawn(Pawn.BLACK)) {
                if (moveRow == row - 1) {
                    score += weights[6];
                } else {
                    score += weights[5];
                }
                break;
            }
            if (state.getPawn(moveRow, col).equalsPawn(Pawn.WHITE)) {
                if (moveRow == row - 1) {
                    score += weights[4];
                } else {
                    score += weights[3];
                }
                break;
            }
            // ciclo su tutte le coppie row,col di citadels per trovarne una corrispondente
            boolean stop = false;
            for (int r = 0; !stop && r < StateUtils.getCitadels()[0].length; r++) {
                for (int c = 0; c < StateUtils.getCitadels()[1].length; c++) {
                    if (r == moveRow && c == col) {
                        score += weights[1];
                        stop = true;
                        break;
                    }
                }
            }
            stop = false;
            for (int r = 0; !stop && r < StateUtils.getEscapes()[0].length; r++) {
                for (int c = 0; c < StateUtils.getEscapes()[1].length; c++) {
                    if (r == moveRow && c == col) {
                        score += weights[0];
                        stop = true;
                        break;
                    }
                }
            }
            moveRow -= 1;

        }

        moveRow = row + 1;
        while (moveRow < 9) {
            if (StateUtils.isCastle(moveRow, col)) {
                score += weights[2];
                break;
            }
            if (state.getPawn(moveRow, col).equalsPawn(Pawn.BLACK)) {
                if (moveRow == row + 1) {
                    score += weights[6];
                } else {
                    score += weights[5];
                }
                break;
            }
            if (state.getPawn(moveRow, col).equalsPawn(Pawn.WHITE)) {
                if (moveRow == row + 1) {
                    score += weights[4];
                } else {
                    score += weights[3];
                }
                break;
            }
            // ciclo su tutte le coppie row,col di citadels per trovarne una corrispondente
            boolean stop = false;
            for (int r = 0; !stop && r < StateUtils.getCitadels()[0].length; r++) {
                for (int c = 0; c < StateUtils.getCitadels()[1].length; c++) {
                    if (r == moveRow && c == col) {
                        score += weights[1];
                        stop = true;
                        break;
                    }
                }
            }
            stop = false;
            // ciclo su tutte le coppie row,col di escapes per trovarne una corrispondente
            for (int r = 0; !stop && r < StateUtils.getEscapes()[0].length; r++) {
                for (int c = 0; c < StateUtils.getEscapes()[1].length; c++) {
                    if (r == moveRow && c == col) {
                        score += weights[0];
                        stop = true;
                        break;
                    }
                }
            }

            moveRow += 1;
        }
        return score;
    }

}
