package com.view.GamePanels;

import com.GameLogic.Board.Board;
import com.GameLogic.Board.Pieces.*;
import com.GameLogic.Game;
import com.GameLogic.Players.Player;
import com.GameLogic.Players.UserPlayer;
import com.GameLogic.Vector2;
import com.view.MainChess;
import com.view.Menu;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Chessboard extends JPanel {

    public JPanel board;
    public int moveCount;

    public boolean isFirstMove;
    public Vector2 firstChosenMove;
    public Vector2 secondChosenMove;
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
    JButton[][] squares = new JButton[8][8];
    public static final int LENGTH = 8;
    private final Color[] COLOR_ARRAY = {new Color(28, 82, 105), Color.WHITE};
    Game gameState;
    JFrame mainFrame;

    public Chessboard(Game game, JFrame mainFrame) {
        this.mainFrame = mainFrame;
        board = new JPanel(new GridLayout(LENGTH, LENGTH)); //grid layout 8x8
        Border blackborder = BorderFactory.createLineBorder(Color.BLACK, 4);
        this.setBackground(Color.BLACK);
        this.setBorder(blackborder);
        gameState = game;
        Piece movePiece = gameState.movePiece;
        JLabel piece = DiePanel.piece;
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

    public void updateState(Vector2 first, Vector2 second) {
        if (Game.currentPlayer instanceof UserPlayer) {
            ((UserPlayer) Game.currentPlayer).setMove(new Vector2[]{first, second});
            if (Game.currentPlayer.sendMove(gameState, gameState.getState().getPieceAtPos(first))) {
                MainChess.getLeftPanel().changeTeamColor();
            }
        }
        if (Game.currentPlayer.type == Player.PlayerType.AGENT) {
            Game.currentPlayer.sendMove(gameState, gameState.movePiece);
        }
        updatePieces();

    }

    public void updatePieces() {
        this.removeAll();
        setOpaque(false); //make it transparent
        setLayout(new GridLayout(LENGTH, LENGTH));
        drawSquare();

        Board board = gameState.getState();


        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                if ((board.board[i][j] instanceof Pawn) && (board.board[i][j].color == com.GameLogic.Board.Pieces.Piece.colours.white)) {
                    squares[i][j].add(new JLabel(new ImageIcon("src/main/resources/wp.png")));
                }
                if ((board.board[i][j] instanceof Rook) && (board.board[i][j].color == com.GameLogic.Board.Pieces.Piece.colours.white)) {
                    squares[i][j].add(new JLabel(new ImageIcon("src/main/resources/wr.png")));
                }
                if ((board.board[i][j] instanceof Knight) && (board.board[i][j].color == com.GameLogic.Board.Pieces.Piece.colours.white)) {
                    squares[i][j].add(new JLabel(new ImageIcon("src/main/resources/wn.png")));
                }
                if ((board.board[i][j] instanceof King) && (board.board[i][j].color == com.GameLogic.Board.Pieces.Piece.colours.white)) {
                    squares[i][j].add(new JLabel(new ImageIcon("src/main/resources/wk.png")));
                }
                if ((board.board[i][j] instanceof Queen) && (board.board[i][j].color == com.GameLogic.Board.Pieces.Piece.colours.white)) {
                    squares[i][j].add(new JLabel(new ImageIcon("src/main/resources/wq.png")));
                }
                if ((board.board[i][j] instanceof Bishop) && (board.board[i][j].color == com.GameLogic.Board.Pieces.Piece.colours.white)) {
                    squares[i][j].add(new JLabel(new ImageIcon("src/main/resources/wb.png")));
                }
                if ((board.board[i][j] instanceof Pawn) && (board.board[i][j].color == com.GameLogic.Board.Pieces.Piece.colours.black)) {
                    squares[i][j].add(new JLabel(new ImageIcon("src/main/resources/bp.png")));
                }
                if ((board.board[i][j] instanceof Rook) && (board.board[i][j].color == com.GameLogic.Board.Pieces.Piece.colours.black)) {
                    squares[i][j].add(new JLabel(new ImageIcon("src/main/resources/br.png")));
                }
                if ((board.board[i][j] instanceof Knight) && (board.board[i][j].color == com.GameLogic.Board.Pieces.Piece.colours.black)) {
                    squares[i][j].add(new JLabel(new ImageIcon("src/main/resources/bn.png")));
                }
                if ((board.board[i][j] instanceof King) && (board.board[i][j].color == com.GameLogic.Board.Pieces.Piece.colours.black)) {
                    squares[i][j].add(new JLabel(new ImageIcon("src/main/resources/bk.png")));
                }
                if ((board.board[i][j] instanceof Queen) && (board.board[i][j].color == com.GameLogic.Board.Pieces.Piece.colours.black)) {
                    squares[i][j].add(new JLabel(new ImageIcon("src/main/resources/bq.png")));
                }
                if ((board.board[i][j] instanceof Bishop) && (board.board[i][j].color == com.GameLogic.Board.Pieces.Piece.colours.black)) {
                    squares[i][j].add(new JLabel(new ImageIcon("src/main/resources/bb.png")));
                }
                //else()
            }
        }


        refresh();
    }

    public void refresh() {
        this.setVisible(false);
        this.setVisible(true);
    }

    public void drawSquare() {

        int numView = 1;

        isFirstMove = true;
        //tiles color determined by odd/even
        for (int i = 0; i < LENGTH; i++) {
            numView = (numView == 0) ? 1 : 0;
            for (int j = 0; j < LENGTH; j++) {
                squares[i][j] = new ChessTile(new Vector2(i, j));
                squares[i][j].setBorder(BorderFactory.createLineBorder(Color.black));

                squares[i][j].addActionListener(e -> {
                    ChessTile currentTile = (ChessTile) e.getSource();
                    if (isFirstMove) {
                        if (gameState.getState().getPieceAtPos(currentTile.pos) != null) {
                            firstChosenMove = currentTile.pos;
                            isFirstMove = false;
                        }
                    } else {
                        secondChosenMove = currentTile.pos;
                        isFirstMove = true;
                        updateState(firstChosenMove, secondChosenMove);
                        JButton button = new JButton("CLOSE");
                        button.addActionListener(e1 -> {
                            new Menu();
                            mainFrame.dispose();
                        });
                        Piece movePiece = gameState.movePiece;
                        JLabel piece = DiePanel.piece;
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

                        if (gameState.winCheck() == Piece.colours.white) {
                            JOptionPane.showMessageDialog(null, "White wins");
                        } else if (gameState.winCheck() == Piece.colours.black) {
                            JOptionPane.showMessageDialog(null, "Black wins");
                        }
                    }
                });
                squares[i][j].setBackground(COLOR_ARRAY[numView]);
                add(squares[i][j]);
                numView = (numView == 0) ? 1 : 0;
            }
        }
    }
}
