package tictactoe.applet;

import tictactoe.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class TicTacToePanel extends JPanel implements ActionListener, Runnable
{
    private BoardPanel board;
    private JLabel status;
    private JButton newGameButton;

    private ButtonGroup gameMode;
    private JRadioButton onePlayerButtonA;
    private JRadioButton onePlayerButtonB;
    private JRadioButton twoPlayerButton;

    public TicTacToePanel()
    {
        this.setLayout(new BorderLayout(0, 10));
        this.setPreferredSize(new Dimension(150, 250));

        this.board = this.board = new BoardPanel(PlayerType.MOUSE, PlayerType.ALPHABETA);

        this.status = new JLabel(" ");
        this.newGameButton = new JButton("New game");
        this.newGameButton.addActionListener(this);

        this.gameMode = new ButtonGroup();
        this.onePlayerButtonA = new JRadioButton("One Player [X]", true);
        this.onePlayerButtonB = new JRadioButton("One Player [O]", false);
        this.twoPlayerButton  = new JRadioButton("Two players", false);
        this.gameMode.add(this.onePlayerButtonA);
        this.gameMode.add(this.onePlayerButtonB);
        this.gameMode.add(this.twoPlayerButton);

        // panel with button group
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(3, 1));
        p.setPreferredSize(new Dimension(150, 45));
        p.add(this.onePlayerButtonA);
        p.add(this.onePlayerButtonB);
        p.add(this.twoPlayerButton);


        this.add(this.board, BorderLayout.PAGE_START);
        this.add(this.status, BorderLayout.LINE_START);
        this.add(this.newGameButton, BorderLayout.LINE_END);
        this.add(p, BorderLayout.PAGE_END);

    }

    public void actionPerformed(ActionEvent ae)
    {
        if (ae.getSource() == this.newGameButton) {
            this.newGameButton.setEnabled(false);

            // create new board
            if (this.onePlayerButtonA.isSelected()) {
                this.board.reset(PlayerType.MOUSE, PlayerType.ALPHABETA);
            } else if (this.onePlayerButtonB.isSelected()) {
                this.board.reset(PlayerType.ALPHABETA, PlayerType.MOUSE);
            } else if (this.twoPlayerButton.isSelected()) {
                this.board.reset(PlayerType.MOUSE, PlayerType.MOUSE);
            }

            new Thread(this.board).start();
        }
    }

    public void run()
    {
        while (true) {
            try {
                // wait until boardPanel notifies for game over or game start
                synchronized(this.board) {
                    this.board.wait();

                    this.newGameButton.setEnabled(true);

                    switch (this.board.getWinner()) {
                        case X:
                            this.status.setText("X wins!");
                            break;
                        case O:
                            this.status.setText("O wins!");
                            break;
                        case DRAW:
                            this.status.setText("Draw!");
                            break;
                        case NONE:
                            this.status.setText(" ");
                            this.newGameButton.setEnabled(false);
                            break;
                    }
                }



            } catch (InterruptedException ie) {
                // do nothing
            }
        }
    }


    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }

    public static void createAndShowGui()
    {
        JFrame frame = new JFrame("TicTacToe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TicTacToePanel p = new TicTacToePanel();
        frame.add(p);

        frame.pack();
        frame.setVisible(true);

        new Thread(p).start();
    }
}