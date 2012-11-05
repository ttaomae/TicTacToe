package tictactoe;

import java.awt.event.*;
import java.util.*;

public class AlphaBetaPlayer implements Player
{
    private static final int MIN_SCORE = -100;
    private static final int MAX_SCORE = 100;

    private int maxDepth;
    private Mark myMark;
    private Random rng;

    public AlphaBetaPlayer(int d)
    {
        this.maxDepth = Math.max(d, 2);
        this.myMark = Mark.NONE;
        this.rng = new Random();
    }

    public int getMove(Board b)
    {
        this.myMark = b.getCurrentPlayer();

        // special case: first turn - slect corner
        if (b.getTurn() == 0) {
            return 0;
        }

        return alphabetaRoot(b, this.maxDepth, MIN_SCORE-1, MAX_SCORE+1);
    }

    // returns best move
    public int alphabetaRoot(Board board, int depth, int alpha, int beta)
    {
        HashMap<Integer, Board> possibleMoves = new HashMap<Integer, Board>();
        // list of all moves with higest score
        ArrayList<Integer> bestMoves = new ArrayList<Integer>();
        int bestHeuristic = MIN_SCORE;


        // populate list of possible moves
        for (int i = 0; i < 9; i++) {
            Board temp = (Board)board.clone();
            try {
                temp.play(i);
                possibleMoves.put(i, temp);

            } catch (IllegalMoveException e) {
                // should never reach here, since valid moves are checked above
            }
        }

        for (Map.Entry<Integer, Board> entry : possibleMoves.entrySet()) {
            int heuristic = alphabeta(entry.getValue(), depth - 1, alpha, beta);
            if (heuristic > bestHeuristic) {
                bestMoves = new ArrayList<Integer>();
                bestMoves.add(entry.getKey());
                bestHeuristic = heuristic;
            } else if (heuristic == bestHeuristic) {
                bestMoves.add(entry.getKey());
            }
        }

        return bestMoves.get(this.rng.nextInt(bestMoves.size()));
    }

    // returns heuristic value of move
    public int alphabeta(Board board, int depth, int alpha, int beta)
    {
        if (depth == 0 || board.getWinner() != Mark.NONE) {
            if (board.getWinner() == this.myMark) {
                return MAX_SCORE;
            } else if (board.getWinner() == Board.opposite(this.myMark)) {
                return MIN_SCORE;
            } else {
                return 0;
            }
        }

        ArrayList<Board> possibleMoves = new ArrayList<Board>();

        // create list of possible moves
        for (int i = 0; i < 9; i++) {
            Board temp = (Board)board.clone();
            try {
                if (temp.markAt(i) == Mark.NONE) {
                    temp.play(i);
                    possibleMoves.add(temp);
                }
            } catch (IllegalMoveException e) {
                // should never reach here, since valid moved are checked above
            }
        }

        // maximize
        if (board.getCurrentPlayer() == this.myMark) {
            for (Board b : possibleMoves) {
                alpha = Math.max(alpha, alphabeta(b, depth-1, alpha, beta));
                if (beta <= alpha) {
                    break;
                }
            }

            return alpha;

        // minimize
        } else {
            for (Board b : possibleMoves) {
                beta = Math.min(beta, alphabeta(b, depth-1, alpha, beta));
                if (beta <= alpha) {
                    break;
                }
            }

            return beta;
        }
    }

    public void actionPerformed(ActionEvent ae) { /* do nothing */ }
}