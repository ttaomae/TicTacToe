package tictactoe.applet;
import tictactoe.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MousePlayer implements Player, ActionListener
{
    private int myMove;

    public MousePlayer()
    {
        this.myMove = -1;
    }

    public int getMove(Board b)
    {
        this.myMove = -1;

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