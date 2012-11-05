package tictactoe.applet;
import tictactoe.*;

import javax.swing.JApplet;

/**
 * {@code JApplet} for a {@code TicTacToePanel}.
 *
 * @author Todd Taomae
 */
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