package tictactoe.applet;
import tictactoe.*;

import javax.swing.JApplet;

@SuppressWarnings("serial")
public class TicTacToeApplet extends JApplet
{
    public TicTacToePanel panel;

    public void init()
    {
        this.panel = new TicTacToePanel();

        this.add(this.panel);

        new Thread(this.panel).start();
    }
}