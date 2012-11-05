package tictactoe;

import java.awt.event.*;
import java.util.*;

/**
 * A player of TicTacToe.
 *
 * @author Todd Taomae
 */
public interface Player extends ActionListener
{
    /**
     * Returns the selected move for the specified {@code Board}.
     * It is the responsibility of the user to determine, if necessary,
     * which player they are.
     *
     * @param   b   the board to select a move for.
     * @return  the selected move for the specified {@code Board}
     */
    public int getMove(Board b);
}

/**
 * Implementation of the {@code Player} interface which uses a sequential selection.
 * <p>
 * This {@code Player} will choose the first available move as indexed by the {@Board}.
 *
 * @author Todd Taomae
 */
class SequentialPlayer implements Player
{
    /**
     * Returns the first available move for the specified board.
     *
     * @param   b   the board to select a move for.
     * @return  the selected move for the specified {@code Board}
     */
    public int getMove(Board b)
    {
        for (int i = 0; i < 9; i++) {
            if (b.markAt(i) == Mark.NONE) {
                return i;
            }
        }

        // return -1 by default
        return -1;
    }

    public void actionPerformed(ActionEvent ae) { /* do nothing */ }
}

/**
 * Implementation of the {@code Player} interface which uses {@code System.in} as an
 * input method.
 *
 * @author Todd Taomae
 */
class KeyboardPlayer implements Player
{
    private Scanner keybd;

    /**
     * Constructs a new {@code KeyboardPlayer}.
     */
    public KeyboardPlayer()
    {
        this.keybd = new Scanner(System.in);
    }

    /**
     * Returns the move indicated by input from {@code System.in}.
     *
     * @param   b   the board to select a move for.
     * @return  the selected move for the specified {@code Board}
     */
    public int getMove(Board b)
    {
        int result = -1;

        while (result == -1) {
            try {
                result = Integer.parseInt(keybd.nextLine());
            } catch (NumberFormatException e) {
                result = -1;
            }

            if (result < 0  || result >= 9) {
                result = -1;
            }
        }

        return result;
    }

    public void actionPerformed(ActionEvent ae) { /* do nothing */ }
}

/**
 * Implementation of the {@code Player} interface which uses a minimax algorithm.
 * @author Todd Taomae
 */
class MinimaxPlayer implements Player
{
    /** Minimum possible heuristic score */
    private static final int MIN_SCORE = -100;
    /** Maximum possible heuristic score */
    private static final int MAX_SCORE = 100;

    private int maxDepth;
    private Mark myMark;
    private Random rng;

    /**
     * Constructs a new player with the specified maximum search depth.
     *
     * @param   depth   maximum search depth
     */
    public MinimaxPlayer(int d)
    {
        this.maxDepth = Math.max(d, 2);
        this.myMark = Mark.NONE;
        this.rng = new Random();
    }

    /**
     * Returns the selected move for the specified {@code Board}.
     *
     * @param   b   board to evaluate
     * @return      selected move for the specified {@code Board}
     */
    public int getMove(Board b)
    {
        this.myMark = b.getCurrentPlayer();

        // special case: first turn - slect corner
        if (b.getTurn() == 0) {
            return 0;
        }

        return minimaxRoot(b, this.maxDepth);
    }

    /**
     * Returns the best move for the specified Board.
     *
     * @param   board   board that is being searched
     * @param   depth   maximum search depth
     * @return  the index of the selected move
     */
    public int minimaxRoot(Board board, int depth)
    {
        int bestHeuristic = MIN_SCORE;
        HashMap<Integer, Board> possibleMoves = new HashMap<Integer, Board>();
        ArrayList<Integer> bestMoves = new ArrayList<Integer>();


        // populate list of possible moves
        for (int i = 0; i < 9; i++) {
            Board temp = (Board)board.clone();
            try {
                temp.play(i);
                possibleMoves.put(i, temp);

            } catch (IllegalMoveException e) {
                // do not add to list
            }
        }

        for (Map.Entry<Integer, Board> entry : possibleMoves.entrySet()) {
            int heuristic = minimax(entry.getValue(), depth - 1);
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

    /**
     * Returns the heuristic value for the best move of the specified Board.
     *
     * @param   board   board that is being searched
     * @param   depth   maximum search depth
     * @return  heuristic value of the best move
     */
    public int minimax(Board board, int depth)
    {
        // if terminal node, return heuristic.
        if (depth == 0 || board.getWinner() != Mark.NONE) {
            if (board.getWinner() == this.myMark) {
                return MAX_SCORE;
            } else if (board.getWinner() == Board.opposite(this.myMark)) {
                return MIN_SCORE;
            } else {
                return 0;
            }
        }

        // create list of possible moves
        ArrayList<Board> possibleMoves = new ArrayList<Board>();

        for (int i = 0; i < 9; i++) {
            Board temp = (Board)board.clone();
            try {
                if (temp.markAt(i) == Mark.NONE) {
                    temp.play(i);
                    possibleMoves.add(temp);
                }
            } catch (IllegalMoveException e) {
                // should never reach here, since valid moves are checked above
            }
        }

        int result;
        // maximize
        if (board.getCurrentPlayer() == this.myMark) {
            result = MIN_SCORE;
            for (Board b : possibleMoves) {
                result = Math.max(result, minimax(b, depth-1));
            }

        // minimize
        } else {
            result = MAX_SCORE;
            for (Board b : possibleMoves) {
                result = Math.min(result, minimax(b, depth-1));
            }
        }

        return result;
    }

    public void actionPerformed(ActionEvent ae) { /* do nothing */ }
}