package com.view.GamePanels;

import com.GameLogic.Board.Pieces.*;
import com.GameLogic.Game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class DiePanel extends JPanel {
    public Piece movePiece;
    public Game game;
    public static JLabel piece = new JLabel();

    public DiePanel(Game game) {
        this.game = game;
        this.setBackground(new Color(136, 136, 227));
        this.setMinimumSize(new Dimension(150, 440));
        this.setPreferredSize(new Dimension(150, 440));
        this.setLayout(new GridLayout(2, 1));

        ImageIcon whitePawn = new ImageIcon("src/main/resources/wp.png");
        ImageIcon whiteRook = new ImageIcon("src/main/resources/wr.png");
        ImageIcon whiteKnight = new ImageIcon("src/main/resources/wn.png");
        ImageIcon whiteKing = new ImageIcon("src/main/resources/wk.png");
        ImageIcon whiteQueen = new ImageIcon("src/main/resources/wq.png");
        ImageIcon whiteBishop = new ImageIcon("src/main/resources/wb.png");
        ImageIcon blackPawn = new ImageIcon("src/main/resources/bp.png");
        ImageIcon blackRook = new ImageIcon("src/main/resources/br.png");
        ImageIcon blackKnight = new ImageIcon("src/main/resources/bn.png");
        ImageIcon blackKing = new ImageIcon("src/main/resources/bk.png");
        ImageIcon blackQueen = new ImageIcon("src/main/resources/bq.png");
        ImageIcon blackBishop = new ImageIcon("src/main/resources/bb.png");

        Border whiteBorder = BorderFactory.createLineBorder(Color.WHITE, 4);
        Border blackBorder = BorderFactory.createLineBorder(Color.BLACK, 4);

        JButton diceButton = new JButton();


        diceButton.setMinimumSize(new Dimension(150, 220));
        diceButton.setBackground(new Color(136, 136, 227));

        diceButton.setLayout(new BorderLayout());
        int butWidth = 70;
        int butHeight = 50;
        diceButton.setSize(butWidth, butHeight);
        JLabel buttonData = new JLabel("ROLL", SwingConstants.CENTER);
        buttonData.setFont(new Font(buttonData.getFont().getName(), Font.BOLD, 30));
        Font oldFont = buttonData.getFont();
        //Creates the change listner to show that the button is being selected by changing the border and font
        diceButton.addChangeListener(e -> {
            if (diceButton.getBorder() == blackBorder) {
                diceButton.setBorder(whiteBorder);
                diceButton.setSize((int) (butWidth * 1.25), (int) (butHeight * 1.25));
                buttonData.setFont(new Font(buttonData.getFont().getName(), Font.BOLD, 50));
                diceButton.setBackground(new Color(168, 168, 241));
            } else {
                diceButton.setBorder(blackBorder);
                diceButton.setSize(butWidth, butHeight);
                buttonData.setFont(oldFont);
                diceButton.setBackground(new Color(136, 136, 227));

            }
        });
        diceButton.setBorder(blackBorder);


        buttonData.setForeground(Color.WHITE);
        diceButton.add(buttonData, BorderLayout.CENTER);
        //Creates the action listener that starts the thread that will start and run the game
        diceButton.addActionListener(e -> {
            if (this.game.movePiece == null) {

                this.setVisible(false);
                this.setVisible(true);
                if (movePiece instanceof Pawn) {
                    if (Game.currentPlayer.team == Piece.colours.white) {
                        piece.setIcon(whitePawn);

                    } else {
                        piece.setIcon(blackPawn);

                    }
                } else if (movePiece instanceof Knight) {
                    if (Game.currentPlayer.team == Piece.colours.white) {
                        piece.setIcon(whiteKnight);

                    } else {
                        piece.setIcon(blackKnight);

                    }
                } else if (movePiece instanceof Bishop) {
                    if (Game.currentPlayer.team == Piece.colours.white) {
                        piece.setIcon(whiteBishop);

                    } else {
                        piece.setIcon(blackBishop);

                    }

                } else if (movePiece instanceof Rook) {
                    if (Game.currentPlayer.team == Piece.colours.white) {
                        piece.setIcon(whiteRook);

                    } else {
                        piece.setIcon(blackRook);

                    }
                } else if (movePiece instanceof Queen) {
                    if (Game.currentPlayer.team == Piece.colours.white) {
                        piece.setIcon(whiteQueen);
                    } else {
                        piece.setIcon(blackQueen);

                    }
                } else if (movePiece instanceof King) {
                    if (Game.currentPlayer.team == Piece.colours.white) {
                        piece.setIcon(whiteKing);
                    } else {
                        piece.setIcon(blackKing);
                    }
                }
            }
        });

        //diceButton.setBounds(50,50,70,50);

        this.add(piece);

    }
}