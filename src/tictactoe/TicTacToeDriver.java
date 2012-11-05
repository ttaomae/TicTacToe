package tictactoe;

import java.util.Scanner;

public class TicTacToeDriver implements Runnable
{
    private Player playerX;
    private Player playerO;
    private Board board;
    private Mark winner;
    private boolean endGame;

    public TicTacToeDriver(Player x, Player o)
    {
        this.playerX = x;
        this.playerO = o;
        this.board = new Board();
        this.winner = Mark.NONE;
        this.endGame = false;
    }

    public void playGame(boolean print)
    {
        // loop until there is a winner
        while (this.board.getWinner() == Mark.NONE) {
            int move = -1;

            if (this.board.getTurn() % 2 == 0) {
                move = this.playerX.getMove((Board)this.board.clone());
            } else {
                move = this.playerO.getMove((Board)this.board.clone());
            }

            try {
                // notify all threads that a move has been played
                synchronized(this) {
                    this.board.play(move);
                    this.notifyAll();
                }

            } catch (IllegalMoveException e) {
                System.err.println("Player " + this.board.getCurrentPlayer()
                                 + " has performed an illegal move!");
                break;
            }

            if (print) {
                System.out.println(this.board + "\n");
            }
        }

        this.winner = this.board.getWinner();

        if (print) {
            System.out.println("Winner is " + this.winner);
        }
    }

    public void run()
    {
        this.playGame(false);
    }

    public Mark getWinner()
    {
        return this.winner;
    }

    public int getTurn()
    {
        return this.board.getTurn();
    }

    public Mark[] getState()
    {
        return this.board.getBoard();
    }
}