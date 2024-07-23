package com.GameLogic;

import com.GameLogic.Board.Board;
import com.GameLogic.Board.Die;
import com.GameLogic.Board.Pieces.*;
import com.GameLogic.Players.Player;

public class Game {

    public static Player currentPlayer;
    public static Boolean turnColor = false;
    public Player white, black;
    public Die die;
    public Piece movePiece;
    public Board board;

    public Game(Player white, Player black) {
        board = new Board();
        board.setupBoard();
        die = new Die();
        this.white = white;
        this.black = black;
        currentPlayer = this.white;
        movePiece = die.rollDie(currentPlayer.team, board);
        movePiece.color = Piece.colours.white;

        if (white.type == Player.PlayerType.AGENT) currentPlayer.sendMove(this, movePiece);
        //System.out.println(movePiece);
    }

    public Board getState() {
        return board;
    }

    //TODO: add the turn switching as well as game loop i guess where we wait for new move (Maybe done in ui but will make a text one for testing)

    public boolean updateState(Vector2[] move, Piece piece) {
        boolean validMove = false;
        if (currentPlayer.team == Piece.colours.white) {
            turnColor = false;
            currentPlayer = white;

            validMove = applyMoves(currentPlayer, move, piece);
            if (validMove) {
                currentPlayer = black;
                if (winCheck() == null) {
                    movePiece = die.rollDie(currentPlayer.team, board);
                    movePiece.color = Piece.colours.black;
                }
                //System.out.println(movePiece+"Black");
            }

        } else if (currentPlayer.team == Piece.colours.black) {
            turnColor = true;
            currentPlayer = black;
            validMove = applyMoves(currentPlayer, move, piece);
            if (validMove) {
                currentPlayer = white;
                if (winCheck() == null) {
                    movePiece = die.rollDie(currentPlayer.team, board);
                    movePiece.color = Piece.colours.white;
                }
                //System.out.println(movePiece+"White");
            }
        }

        return validMove;
    }

    //TODO: add nice print for game state
    public void printCurrentState() {

    }

    public boolean applyMoves(Player currentPlayer, Vector2[] move, Piece piece) {
        boolean valid = false;
        //System.out.println(board.getPieceAtPos(move[0]));

        if (movePiece == null || (movePiece.getClass() != piece.getClass() || piece.color != currentPlayer.team || move[0] == move[1])) {
            return valid;
        }

        valid = piece.move(move[1], board);

        if (piece.color != currentPlayer.team) {
            valid = false;
        }
//        System.out.println(this.board);
        if (valid) {
            if (board.getPieceAtPos(move[0]).getClass() == piece.getClass()) {
                this.board.movePiece(move[0], move[1]);

                movePiece = null;
            }
        }

        promoteCheck();
        winCheck();
        return valid;
    }

    public void setMovePiece(Piece movePiece) {
        this.movePiece = movePiece;
    }

    public Piece.colours winCheck() {
        boolean whiteKing = false;
        boolean blackKing = false;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.board[i][j] != null) {
                    if (board.board[i][j] instanceof King) {
                        if (board.board[i][j].color == Piece.colours.white) {
                            whiteKing = true;
                        } else {
                            blackKing = true;
                        }

                    }
                }
            }
        }

        if (!whiteKing) {
            return Piece.colours.black;
        } else if (!blackKing) {
            return Piece.colours.white;
        }


        return null;
    }

    public void promoteCheck() {
        Piece newPieceType;

        if (currentPlayer.team == Piece.colours.white) {
            for (int i = 0; i < 8; i++) {
                if (board.getPieceAtPos(new Vector2(0, i)) instanceof Pawn) {
                    newPieceType = die.promotionRoll();
                    if (newPieceType instanceof Knight) {
                        board.board[0][i] = new Knight(new Vector2(0, i), 3, Piece.colours.white);
                    } else if (newPieceType instanceof Bishop) {
                        board.board[0][i] = new Bishop(new Vector2(0, i), 3, Piece.colours.white);
                    } else if (newPieceType instanceof Rook) {
                        board.board[0][i] = new Rook(new Vector2(0, i), 5, Piece.colours.white);
                    } else if (newPieceType instanceof Queen) {
                        board.board[0][i] = new Queen(new Vector2(0, i), 9, Piece.colours.white);
                    }
                }
            }
        } else {
            for (int i = 0; i < 8; i++) {
                if (board.getPieceAtPos(new Vector2(7, i)) instanceof Pawn) {
                    newPieceType = die.promotionRoll();
                    if (newPieceType instanceof Knight) {
                        board.board[7][i] = new Knight(new Vector2(7, i), 3, Piece.colours.black);
                    } else if (newPieceType instanceof Bishop) {
                        board.board[7][i] = new Bishop(new Vector2(7, i), 3, Piece.colours.black);
                    } else if (newPieceType instanceof Rook) {
                        board.board[7][i] = new Rook(new Vector2(7, i), 5, Piece.colours.black);
                    } else if (newPieceType instanceof Queen) {
                        board.board[7][i] = new Queen(new Vector2(7, i), 9, Piece.colours.black);
                    }
                }
            }
        }
    }
}
