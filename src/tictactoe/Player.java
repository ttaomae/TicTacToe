package tictactoe;

import java.awt.event.*;
import java.util.*;

public interface Player extends ActionListener
{
    public int getMove(Board b);
}

class SequentialPlayer implements Player
{
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

class KeyboardPlayer implements Player
{
    private Scanner keybd;

    public KeyboardPlayer()
    {
        this.keybd = new Scanner(System.in);
    }

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

class MinimaxPlayer implements Player
{
    private static final int MIN_SCORE = -100;
    private static final int MAX_SCORE = 100;

    private int maxDepth;
    private Mark myMark;
    private Random rng;

    public MinimaxPlayer(int d)
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

        return minimaxRoot(b, this.maxDepth);
    }

    // returns best move
    public int minimaxRoot(Board board, int depth)
    {
        HashMap<Integer, Board> possibleMoves = new HashMap<Integer, Board>();
        int bestHeuristic = MIN_SCORE;
        ArrayList<Integer> bestMoves = new ArrayList<Integer>();


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
// System.out.println("\n" + entry.getValue() + " = " + heuristic + "\n");
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
    public int minimax(Board board, int depth)
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

        for (int i = 0; i < 9; i++) {
            Board temp = (Board)board.clone();
            try {
                temp.play(i);
                possibleMoves.add(temp);

            } catch (IllegalMoveException e) {
                // do not add to list
            }
        }

        int result;
        if (board.getCurrentPlayer() == this.myMark) {
            result = MIN_SCORE;
            for (Board b : possibleMoves) {
                result = Math.max(result, minimax(b, depth-1));
            }
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