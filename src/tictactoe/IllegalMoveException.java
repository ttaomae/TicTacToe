package tictactoe;

@SuppressWarnings("serial")
class IllegalMoveException extends Exception {
    public IllegalMoveException(String message) {
        super(message);
    }
}