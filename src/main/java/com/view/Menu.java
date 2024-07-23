package com.view;

import com.GameLogic.Board.Pieces.Piece;
import com.GameLogic.EvalFunctions.BlackBasicHeruistic;
import com.GameLogic.EvalFunctions.EvaluationFunc;
import com.GameLogic.EvalFunctions.NeuralNet.FileToObject;
import com.GameLogic.EvalFunctions.NeuralNet.NeuralNet;
import com.GameLogic.EvalFunctions.NeuralNetHeuristic;
import com.GameLogic.EvalFunctions.WhiteBasicHeruistic;
import com.GameLogic.Players.ExpectiMaxPlayer;
import com.GameLogic.Players.Player;
import com.GameLogic.Players.UserPlayer;
import com.GameLogic.Players.bots.MCTS;
import com.GameLogic.Players.randomAgent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu implements ActionListener {

    /**
     * Creating new Frame (window)
     */

    JFrame window = new JFrame("GROUP 2");
    JFrame rules = new JFrame("Rules");
    JLabel rulesText = new JLabel();

    /**
     * Declaring the images
     */

    ImageIcon image2 = new ImageIcon("src/main/resources/play.png");
    ImageIcon image3 = new ImageIcon("src/main/resources/settings.png");
    ImageIcon image4 = new ImageIcon("src/main/resources/exit.png");
    ImageIcon image = new ImageIcon("src/main/resources/background.png");
    ImageIcon logo = new ImageIcon("src/main/resources/logo.png");

    /**
     * Create a general label .Creating 3 Buttons and add their icons (play , settings , exit ).
     */

    JLabel label = new JLabel();
    JLabel label2 = new JLabel();

    JButton play = new JButton();
    JButton settings = new JButton();

    JButton setPlayers = new JButton();
    JButton exit = new JButton();

    Color color1 = new Color(0, 0, 0);

    public Menu() {

        rulesText.setText("<html>" + "This game is a variant of classic Chess, which adds a random factor to the strategy. The following list describes all differences of the rules:\n" +
                "There is no check or checkmate, it is allowed to move the king to a square attacked by opponent's piece. The goal is to capture opponent's king.\n" +
                "A die is rolled for every move. The die is displayed below the game board and the number determines which piece can be used to make the move. 1 - pawn, 2 - knight, 3 - bishop, 4 - rook, 5 - queen, 6 - king. \n" +
                "If a pawn is to be promoted (would advance to the last row), the player can move it even if the die does not show 1. However, he can only promote it to the piece chosen by the die roll - for example, if 3 is rolled, the pawn can be promoted to a bishop only. If 1 is rolled, the pawn can be promoted to any piece." + "</html>");

        rules.add(rulesText);
        /**
         * Implement the play button. set the bounds,the background color,the border and add the actionlistener
         */
        Border whiteBorder = BorderFactory.createLineBorder(Color.WHITE, 4);
        Border grayBorder = BorderFactory.createLineBorder(Color.GRAY, 4);

        int butWidth = 120;
        int butHeight = 40;
        play.setBounds(340, 400, 120, 40);
        JLabel buttonData = new JLabel("PLAY", SwingConstants.CENTER);
        Font oldFont = buttonData.getFont();
        //Creates the change listner to show that the button is being selected by changing the border and font
        play.addChangeListener(e -> {
            if (play.getBorder() == grayBorder) {
                play.setBorder(whiteBorder);
                //play.setSize((int)(butWidth*1.25),(int)(butHeight*1.25));
                buttonData.setFont(new Font(buttonData.getFont().getName(), Font.BOLD, 20));
            } else {
                play.setBorder(grayBorder);
                play.setSize(butWidth, butHeight);
                buttonData.setFont(oldFont);
            }
        });
        play.setBorder(grayBorder);
        play.setBackground(Color.black);

        buttonData.setForeground(Color.WHITE);
        play.add(buttonData, SwingConstants.CENTER);
        //Creates the action listner that starts the thread that will start and run the game


        play.addActionListener(this);

        //   play.setBorder(BorderFactory.createBevelBorder(1, Color.white,Color.CYAN ,Color.white ,Color.CYAN));

        /**
         * Implement the settings button. set the bounds,the background color,the border and add the actionlistener
         */

        settings.setBounds(340, 455, 120, 40);
        JLabel settingbuttonData = new JLabel("RULES", SwingConstants.CENTER);
        Font setoldFont = settingbuttonData.getFont();
        //Creates the change listner to show that the button is being selected by changing the border and font
        settings.addChangeListener(e -> {
            if (settings.getBorder() == grayBorder) {
                settings.setBorder(whiteBorder);
                //settings.setSize((int)(butWidth*1.25),(int)(butHeight*1.25));
                settingbuttonData.setFont(new Font(settingbuttonData.getFont().getName(), Font.BOLD, 20));
            } else {
                settings.setBorder(grayBorder);
                settings.setSize(butWidth, butHeight);
                settingbuttonData.setFont(setoldFont);
            }
        });
        settings.setBorder(grayBorder);
        settings.setBackground(Color.black);

        settingbuttonData.setForeground(Color.WHITE);
        settings.add(settingbuttonData, SwingConstants.CENTER);


        settings.addActionListener(this);
        settings.setBackground(color1);
        // settings.setBorder(BorderFactory.createBevelBorder(1, Color.white,Color.CYAN ,Color.white ,Color.CYAN));

        /**
         * Implement the exit button. set the bounds,the background color,the border and add the actionlistener
         */

        setPlayers.setBounds(340, 505, 120, 40);
        JLabel setPlayersButtonData = new JLabel("SET PLAYERS", SwingConstants.CENTER);
        Font playeroldFont = setPlayersButtonData.getFont();
        //Creates the change listner to show that the button is being selected by changing the border and font
        setPlayers.addChangeListener(e -> {
            if (setPlayers.getBorder() == grayBorder) {
                setPlayers.setBorder(whiteBorder);
                //settings.setSize((int)(butWidth*1.25),(int)(butHeight*1.25));
                setPlayersButtonData.setFont(new Font(setPlayersButtonData.getFont().getName(), Font.BOLD, 20));
            } else {
                setPlayers.setBorder(grayBorder);
                setPlayers.setSize(butWidth, butHeight);
                setPlayersButtonData.setFont(playeroldFont);
            }
        });
        setPlayers.setBorder(grayBorder);
        setPlayers.setBackground(Color.black);

        setPlayersButtonData.setForeground(Color.WHITE);
        setPlayers.add(setPlayersButtonData, SwingConstants.CENTER);


        setPlayers.addActionListener(this);
        setPlayers.setBackground(color1);


        exit.setBounds(340, 555, 120, 40);

        JLabel exitbuttonData = new JLabel("EXIT", SwingConstants.CENTER);
        Font exitoldFont = exitbuttonData.getFont();
        //Creates the change listner to show that the button is being selected by changing the border and font
        exit.addChangeListener(e -> {
            if (exit.getBorder() == grayBorder) {
                exit.setBorder(whiteBorder);
                //settings.setSize((int)(butWidth*1.25),(int)(butHeight*1.25));
                exitbuttonData.setFont(new Font(exitbuttonData.getFont().getName(), Font.BOLD, 20));
            } else {
                exit.setBorder(grayBorder);
                exit.setSize(butWidth, butHeight);
                exitbuttonData.setFont(exitoldFont);
            }
        });
        exit.setBorder(grayBorder);
        exit.setBackground(Color.black);

        exitbuttonData.setForeground(Color.WHITE);
        exit.add(exitbuttonData, SwingConstants.CENTER);


        exit.addActionListener(this);
        exit.setBackground(color1);
        //    exit.setBorder(BorderFactory.createBevelBorder(1, Color.white,Color.CYAN ,Color.white ,Color.CYAN));


        label2.setIcon(logo);
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setVerticalAlignment(JLabel.CENTER);
        label2.setBounds(200, 80, 400, 130);

        /**
         *  Set the label icon
         *  Set the label location
         *  Set the label background color.
         *  Add the buttons to one label.
         */
        label.setIcon(image);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        label.add(play);
        label.add(settings);
        label.add(setPlayers);
        label.add(exit);

        /**
         *  Set how to close the frame.
         *  Set the size of the frame.
         *  Adding the label to the frame.
         *  Set the frame visible.
         */
        window.setUndecorated(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setLocationRelativeTo(null);
        window.add(label2);
        window.add(label);
        window.setVisible(true);

    }

    /**
     * Creat an Action listener method.
     *
     * @param e which is the buttons that have an ActionListener.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        /**
         *  If Play button clicked create new Play class.
         *  Dispose the current frame.
         */

        if (e.getSource() == play) {
            new MainChess();
            window.dispose();
            //Play Play = new Play();

        } else if (e.getSource() == settings) {
            rules.setSize(800, 600);
            rules.setLocationRelativeTo(null);
            rules.setVisible(true);

        }
        if (e.getSource() == setPlayers) {
            JFrame players = new JFrame();
            players.setSize(400, 400);
            players.setVisible(true);
            players.setLayout(new GridLayout());

            JButton expectimaxPlayerBlack = new JButton("Expectimax PlayerBlack");
            JButton expectimaxPlayerWhite = new JButton("Expectimax PlayerWhite");
            JButton mctsPlayerBlack = new JButton("mctsPlayerBlack");
            JButton mctsPlayerWhite = new JButton("mctsPlayerWhite");
            JButton randomPlayerBlack = new JButton("Random Player Black");
            JButton randomPlayerWhite = new JButton("Random Player White");

            expectimaxPlayerBlack.addActionListener(e1 -> {
                MainChess.player1 = new UserPlayer(Piece.colours.white);
                double[] w = {
                         -0.7919967651754334,
                         0.9823884486732601,
                         -0.6813830574352919,
                         -0.5228713909764557,
                         0.5833762789759536,
                         0.6264175321095509,
                         -0.6555119632060087,
                         -0.1175578027883637,
                         -0.802415194650421,
                         0.25465944726446876,
                         -0.2390846386926555
                };
                MainChess.player2 = new ExpectiMaxPlayer(3, Piece.colours.black, new EvaluationFunc(w));
            });
            expectimaxPlayerWhite.addActionListener(e1 -> {
                double[] w = {
                         -0.9074233326072669,
                         -0.528668792415472,
                         0.03940722461452917,
                         0.521223653672312,
                         0.466437965474106,
                         -0.37583994317876823,
                         0.7362650450030388,
                         -0.9223082063497579,
                         0.9478598430697069,
                         -0.6682927021122793,
                         0.4265190012414326
                };
                MainChess.player1 = new ExpectiMaxPlayer(3, Piece.colours.white, new EvaluationFunc(w));
                MainChess.player2 = new UserPlayer(Piece.colours.black);
            });
            randomPlayerBlack.addActionListener(e1 -> {
                MainChess.player1 = new UserPlayer(Piece.colours.white);
                MainChess.player2 = new randomAgent(Piece.colours.black, Player.PlayerType.AGENT);
            });
            randomPlayerWhite.addActionListener(e1 -> {
                MainChess.player1 = new randomAgent(Piece.colours.white, Player.PlayerType.AGENT);
                MainChess.player2 = new UserPlayer(Piece.colours.black);
            });
            mctsPlayerBlack.addActionListener(e1 -> {
                MainChess.player2 = new ExpectiMaxPlayer(3,Piece.colours.black, new NeuralNetHeuristic((NeuralNet) FileToObject.readObjectFromFile("BlackNN118.txt")));
                MainChess.player1 = new UserPlayer(Piece.colours.white);
            });
            mctsPlayerWhite.addActionListener(e1 -> {
                MainChess.player1 = new MCTS(5,Piece.colours.white,new WhiteBasicHeruistic());
                MainChess.player2 = new UserPlayer(Piece.colours.white);
            });

            players.add(expectimaxPlayerBlack);
            players.add(expectimaxPlayerWhite);
            players.add(mctsPlayerBlack);
            players.add(mctsPlayerWhite);
            players.add(randomPlayerBlack);
            players.add(randomPlayerWhite);
            players.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        }

        /**
         *  If Play Quit clicked close the frame.
         *  Dispose the current frame.
         */

        if (e.getSource() == exit) {
            window.dispose();
        }


    }

    public static void main(String[] args) {
        new Menu();
    }
}
