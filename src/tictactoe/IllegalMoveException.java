package tictactoe;

/**
 * Thrown to indicate that an illegal move has been attempted.
 *
 * @author Todd Taomae
 */
@SuppressWarnings("serial")
class IllegalMoveException extends Exception {
    public IllegalMoveException(String message) {
        super(message);
    }
}