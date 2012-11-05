package tictactoe;

import java.util.*;

/**
 * Representation of a TicTacToe Board. <br />
 * X always goes first.
 *
 * <p>The spaces are indicated with a zero-based index starting at the top-left
 * proceeding right, then down.
 *
 * @author Todd Taomae
 */

public class Board implements Cloneable
{
    // class variables
    private Mark[] board;
    private int turn;

    /**
     * Creates a new, empty board
     */
    public Board()
    {
        this.board = new Mark[9];
        Arrays.fill(board, Mark.NONE);

        this.turn = 0;
    }

    // assume t is correct turn number
    private Board (Mark[] b, int t)
    {
        this.board = new Mark[b.length];
        System.arraycopy(b, 0, this.board, 0, b.length);
        this.turn = t;
    }

    /**
     * Finds winner of this board. Assumes that the board is valid.
     *
     * @return  the winner of the board; X, O, NONE, or DRAW
     */
    public Mark getWinner()
    {
        Mark result = Mark.NONE;

        // if board is full, assume draw unless find winner
        if (this.getTurn() == this.board.length) {
            result = Mark.DRAW;
        }

        for (int i = 0; i < 3; i++) {
            // check horizontal
            if (board[i*3 + 0] == board[i*3 + 1] && board[i*3 + 1] == board[i*3 + 2]) {
                result = board[i*3 + 0];
            }
            // check vertical
            else if (board[0 + i] == board[3+i] && board[3+i] == board[6+i]) {
                result = board[0 + i];
            }
        }

        // check diaganols
        if ((board[0] == board[4] && board[4] == board[8])
            || (board[2] == board[4] && board[4] == board[6])) {
            result = board[4];
        }

        return result;
    }

    /**
     * Plays a move on this board.
     * Mark depends on which turn it is.
     */
    public void play(int move) throws IllegalMoveException
    {
        if (move < 0 || move >= this.board.length) {
            throw new IllegalArgumentException("move = " + move + ": must be between 0 and 8");
        } else if (this.board[move] != Mark.NONE) {
            throw new IllegalMoveException("space " + move + " is already occupied.");
        } else {
            this.board[move] = this.getCurrentPlayer();
            this.turn++;
        }
    }

    public int getTurn()
    {
        return this.turn;
    }

    public Mark getCurrentPlayer()
    {
        if (this.turn % 2 == 0) {
            return Mark.X;
        } else {
            return Mark.O;
        }
    }

    public Mark markAt(int i)
    {
        if (i < 0 || i >= this.board.length) {
            throw new IllegalArgumentException();
        } else {
            return this.board[i];
        }
    }

    public static Mark opposite(Mark m) {
        if (m == Mark.X) {
            return Mark.O;
        } else if (m == Mark.O) {
            return Mark.X;
        } else {
            throw new IllegalArgumentException("Mark must be X or O");
        }
    }

    public Mark[] getBoard()
    {
        return Arrays.copyOf(this.board, this.board.length);
    }

    public Object clone()
    {
        return new Board(this.board, this.turn);
    }

    public String toString()
    {
        String result = "";

        for (int i = 0; i < this.board.length; i++) {
            if (i > 0 && i % 3  == 0) {
                result += "\n";
            }

            switch (this.board[i]) {
                case X:
                    result += "X";
                    break;
                case O:
                    result += "O";
                    break;
                case NONE:
                    result += "_";
                    break;
            }

        }

        return result;
    }
}
