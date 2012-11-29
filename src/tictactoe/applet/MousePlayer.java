package tictactoe.applet;
import tictactoe.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Implementation of the {@code Player} interface which listens to buttons to
 * determine its move.
 * <p>
 * This class checks the action command of the source of the action to
 * determine its selection. It is the responsibility of the user of this class
 * to correctly set the action command and add this as an {@code ActionListener}.
 * The action commands should be set to a string representation of the
 * index of the space it represents.
 *
 * @author Todd Taomae
 */
class MousePlayer implements Player, ActionListener
{
    private int myMove;

    /**
     * Returns the selected move for the specified {@code Board}.
     *
     * @param   b   board to evaluate
     * @return      best move for the given board
     */
    public int getMove(Board b)
    {
        this.myMove = Board.INVALID_MOVE;

        // wait until actionPerformed() notifies
        try {
            synchronized(this) {
                this.wait();
            }
        } catch (InterruptedException ie) {
            // do nothing?
        }

        return this.myMove;
    }

    /**
     * Sets the move for this {@code MousePlayer} based on the action command
     * of the source of the action.
     */
    public void actionPerformed(ActionEvent ae)
    {
        String command = ae.getActionCommand();

        if (command.equals("0")) {
            this.myMove = 0;
        } else if (command.equals("1")) {
            this.myMove = 1;
        } else if (command.equals("2")) {
            this.myMove = 2;
        } else if (command.equals("3")) {
            this.myMove = 3;
        } else if (command.equals("4")) {
            this.myMove = 4;
        } else if (command.equals("5")) {
            this.myMove = 5;
        } else if (command.equals("6")) {
            this.myMove = 6;
        } else if (command.equals("7")) {
            this.myMove = 7;
        } else if (command.equals("8")) {
            this.myMove = 8;
        }

        // notify to resume getMove()
        synchronized(this) {
            this.notify();
        }
    }
}