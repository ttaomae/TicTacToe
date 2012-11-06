package tictactoe;

/**
 * Enumeration of possible markings on a TicTacToe board. Also functions as an
 * indicator of the winner of a TicTacToe Board.
 *
 * @author Todd Taomae
 */
public enum Mark {
    X, O, NONE, DRAW;

    /**
     * Returns the opposite of this {@code Mark}.
     *
     * @return  the opposite of this {@code Mark}
     */
    public Mark opposite() {
        switch (this) {
            case X:
                return O;
            case O:
                return X;
            default:
                return this;
        }
    }
}