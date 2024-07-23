package com.view;

import com.GameLogic.Board.Pieces.Piece;
import com.GameLogic.Game;
import com.GameLogic.Players.Player;
import com.GameLogic.Players.UserPlayer;
import com.view.GamePanels.Chessboard;
import com.view.GamePanels.DiePanel;
import com.view.GamePanels.LeftPanel;
import com.view.GamePanels.TitlePanel;

import javax.swing.*;
import java.awt.*;

public class MainChess extends JFrame {

    JPanel gamePanel;
    Chessboard board;
    static LeftPanel leftPanel;
    public static Player player1 = new UserPlayer(Piece.colours.white);
    public static Player player2 = new UserPlayer(Piece.colours.black);

    public MainChess() {
        // adding the title
        Game game = new Game(player1, player2);

        this.add(gamePanel = new JPanel(new BorderLayout(5, 5)));


        TitlePanel titlePanel = new TitlePanel();
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(136, 136, 227));
        board = new Chessboard(game, this);
        board.updatePieces();

        DiePanel dicePanel = new DiePanel(game);
        leftPanel = new LeftPanel(game, this);
        gamePanel.add(titlePanel, BorderLayout.PAGE_START);
        gamePanel.add(board, BorderLayout.CENTER);


        gamePanel.add(dicePanel, BorderLayout.LINE_END);

        gamePanel.add(leftPanel, BorderLayout.LINE_START);
        gamePanel.add(bottomPanel, BorderLayout.PAGE_END);

        setSize(1200, 950);
        setVisible(true);
        gamePanel.setBackground(new Color(136, 136, 227));
        this.setResizable(false);
        System.out.println("test");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static LeftPanel getLeftPanel() {
        return leftPanel;
    }
}