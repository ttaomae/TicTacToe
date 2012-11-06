package tictactoe;

import java.util.*;

/**
 * Representation of a TicTacToe Board. X always goes first.
 * <p>
 * The spaces are indicated with a zero-based index starting at the top-left
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

    /**
     * Constructs a new {@code Board} from the specified initial state. Assumes that the
     * initial state is valid.
     *
     * @param   init    initial state
     */
    private Board (Mark[] init)
    {
        int t = 0;
        this.board = new Mark[init.length];

        for (int i = 0; i < init.length; i++) {
            this.board[i] = init[i];
            if (init[i] == Mark.X || init[i] == Mark.O) {
                t++;
            }
        }
        this.turn = t;
    }

    /**
     * Finds winner of this board. Assumes that the board is valid.
     *
     * @return  the winner of this Board
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
     * Plays the specified move on this board.
     * Mark depends on which turn it is.
     *
     * @param   move    position of move to play
     * @throws IllegalMoveException if the specified move is not a valid position
     *              or if the position is occupied.
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

    /**
     * Returns that current turn of this Board.
     *
     * @return the current turn of this board
     */
    public int getTurn()
    {
        return this.turn;
    }

    /**
     * Returns the {@code Mark} for the current turn.
     *
     * @return      the {@code Mark} for the current turn.
     */
    public Mark getCurrentPlayer()
    {
        if (this.turn % 2 == 0) {
            return Mark.X;
        } else {
            return Mark.O;
        }
    }

    /**
     * Returns the {@code Mark} at the specified position.
     *
     * @param   pos   position to check for mark.
     * @return  the mark at the specified position.
     * @throws  IllegalArgumentException if the specified position is not a valid space.
     */
    public Mark markAt(int pos)
    {
        if (pos < 0 || pos >= this.board.length) {
            throw new IllegalArgumentException();
        } else {
            return this.board[pos];
        }
    }

    public Object clone()
    {
        return new Board(this.board);
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
