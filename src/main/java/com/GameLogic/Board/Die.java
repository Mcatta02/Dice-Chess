package com.GameLogic.Board;

import com.GameLogic.Board.Pieces.*;
import com.GameLogic.Vector2;

import java.util.ArrayList;

/*
    board is 2D array of pieces
    we then need to select a piece which is playable and for that color
    is stored in game
 */
public class Die {
    public Piece rollDie(Piece.colours player, Board board) {

        // Need to check what pieces can be moved
        // Then need to set the random to go for that
        // Then choose random number with in set and if it equals the number of piece re do the random
        // once the random number return it as string i guess

        ArrayList<Piece> possiblePieces = new ArrayList<>();

        boolean pawnChecked = false;
        boolean knightChecked = false;
        boolean bishopChecked = false;
        boolean rookChecked = false;
        boolean queenChecked = false;
        boolean kingChecked = false;

        if (player == Piece.colours.white) {
            for (int i = 0; i < board.board.length; i++) {
                for (int j = 0; j < board.board.length; j++) {
                    if (board.board[i][j] == null) {
                        continue;
                    } else if (board.board[i][j] instanceof Pawn && board.board[i][j].color == Piece.colours.white) {
                        board.board[i][j].pos = new Vector2(i, j);
                        if (board.board[i][j].getPossibleMoves(board).size() > 0) {
                            pawnChecked = true;
                        }
                    } else if (board.board[i][j] instanceof Knight && board.board[i][j].color == Piece.colours.white) {
                        board.board[i][j].pos = new Vector2(i, j);
                        if (board.board[i][j].getPossibleMoves(board).size() > 0) {
                            knightChecked = true;
                        }
                    } else if (board.board[i][j] instanceof Bishop && board.board[i][j].color == Piece.colours.white) {
                        board.board[i][j].pos = new Vector2(i, j);
                        if (board.board[i][j].getPossibleMoves(board).size() > 0) {
                            bishopChecked = true;
                        }
                    } else if (board.board[i][j] instanceof Rook && board.board[i][j].color == Piece.colours.white) {
                        board.board[i][j].pos = new Vector2(i, j);
                        if (board.board[i][j].getPossibleMoves(board).size() > 0) {
                            rookChecked = true;
                        }
                    } else if (board.board[i][j] instanceof Queen && board.board[i][j].color == Piece.colours.white) {
                        board.board[i][j].pos = new Vector2(i, j);
                        if (board.board[i][j].getPossibleMoves(board).size() > 0) {
                            queenChecked = true;
                        }
                    } else if (board.board[i][j] instanceof King && board.board[i][j].color == Piece.colours.white) {
                        board.board[i][j].pos = new Vector2(i, j);
                        if (board.board[i][j].getPossibleMoves(board).size() > 0) {
                            kingChecked = true;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < board.board.length; i++) {
                for (int j = 0; j < board.board.length; j++) {
                    if (board.board[i][j] == null) {
                        continue;
                    } else if (board.board[i][j] instanceof Pawn && board.board[i][j].color == Piece.colours.black) {
                        board.board[i][j].pos = new Vector2(i, j);
                        if (board.board[i][j].getPossibleMoves(board).size() > 0) {
                            pawnChecked = true;
                        }
                    } else if (board.board[i][j] instanceof Knight && board.board[i][j].color == Piece.colours.black) {
                        board.board[i][j].pos = new Vector2(i, j);
                        if (board.board[i][j].getPossibleMoves(board).size() > 0) {
                            knightChecked = true;
                        }
                    } else if (board.board[i][j] instanceof Bishop && board.board[i][j].color == Piece.colours.black) {
                        board.board[i][j].pos = new Vector2(i, j);
                        if (board.board[i][j].getPossibleMoves(board).size() > 0) {
                            bishopChecked = true;
                        }
                    } else if (board.board[i][j] instanceof Rook && board.board[i][j].color == Piece.colours.black) {
                        board.board[i][j].pos = new Vector2(i, j);
                        if (board.board[i][j].getPossibleMoves(board).size() > 0) {
                            rookChecked = true;
                        }
                    } else if (board.board[i][j] instanceof Queen && board.board[i][j].color == Piece.colours.black) {
                        board.board[i][j].pos = new Vector2(i, j);
                        if (board.board[i][j].getPossibleMoves(board).size() > 0) {
                            queenChecked = true;
                        }
                    } else if (board.board[i][j] instanceof King && board.board[i][j].color == Piece.colours.black) {
                        board.board[i][j].pos = new Vector2(i, j);
                        if (board.board[i][j].getPossibleMoves(board).size() > 0) {
                            kingChecked = true;
                        }
                    }
                }
            }
        }
        if (pawnChecked) {
            possiblePieces.add(new Pawn());
        }
        if (knightChecked) {
            possiblePieces.add(new Knight());
        }
        if (bishopChecked) {
            possiblePieces.add(new Bishop());
        }
        if (rookChecked) {
            possiblePieces.add(new Rook());
        }
        if (queenChecked) {
            possiblePieces.add(new Queen());
        }
        if (kingChecked) {
            possiblePieces.add(new King());
        }
        if (possiblePieces.size() == 0) {
            System.out.println(board);
        }
        int result = (int) (Math.random() * possiblePieces.size());

        return possiblePieces.get(result);
    }

    public Piece promotionRoll() {
        Piece[] pieces = {new Knight(), new Bishop(), new Rook(), new Queen()};

        return pieces[(int) (Math.random() * pieces.length)];
    }

    /*
        The way im checking if a piece has available moves is by doing every possible move for every type of piece until a
        true is removed.
        The actual checks themselves is simply iterating through all the possible moves for that piece and then
        checking with the already made legal move checker to see if it works on a clone of the board,
        so it doesn't affect the current state of the board
     */

    //Need to check all the knight moves
    //Knight move in L shape (Hold the L)
    public boolean knightCheck(Piece piece, Board board) {
        // Up
        if (piece.move(new Vector2(piece.pos.x + 1, piece.pos.y + 2), board)) {
            return true;
        }
        if (piece.move(new Vector2(piece.pos.x - 1, piece.pos.y + 2), board)) {
            return true;
        }
        // Right
        if (piece.move(new Vector2(piece.pos.x + 2, piece.pos.y + 1), board)) {
            return true;
        }
        if (piece.move(new Vector2(piece.pos.x + 2, piece.pos.y - 1), board)) {
            return true;
        }
        // Down

        if (piece.move(new Vector2(piece.pos.x + 1, piece.pos.y - 2), board)) {
            return true;
        }

        if (piece.move(new Vector2(piece.pos.x - 1, piece.pos.y - 2), board)) {
            return true;
        }
        // Left
        if (piece.move(new Vector2(piece.pos.x - 2, piece.pos.y + 1), board)) {
            return true;
        }
        return piece.move(new Vector2(piece.pos.x - 2, piece.pos.y - 1), board);
    }

    public boolean bishopCheck(Piece piece, Board board) {

        // two loops I guess on for each diagonal
        // So start at bottom of each diagonal within the bounds and then just iterate for both until a true is found
        // I guess 4 separate loops for each direction but it could be 2 loops but even if you do two loops it will still result in
        // same amount of iterations
        int up = 8 - piece.pos.y;
        int down = 8 - up - 1;

        Vector2 future = new Vector2(piece.pos.x, piece.pos.y);

        for (int i = 0; i < down; i++) {
            future.x = future.x + 1;
            future.y = future.y + 1;

            if (piece.move(future, board)) {
                return true;
            }
        }

        future.x = piece.pos.x;
        future.y = piece.pos.y;

        for (int i = 0; i < down; i++) {

            future.x = future.x - 1;
            future.y = future.y + 1;

            if (piece.move(future, board)) {
                return true;
            }
        }

        future.x = piece.pos.x;
        future.y = piece.pos.y;

        for (int i = 0; i < up; i++) {

            future.x = future.x + 1;
            future.y = future.y - 1;

            if (piece.move(future, board)) {
                return true;
            }
        }

        future.x = piece.pos.x;
        future.y = piece.pos.y;

        for (int i = 0; i < up; i++) {

            future.x = future.x - 1;
            future.y = future.y - 1;

            if (piece.move(future, board)) {
                return true;
            }
        }

        return false;
    }

    public boolean rookCheck(Piece piece, Board board) {
        // One loops less goo

        for (int i = 0; i < 8; i++) {
            if (i != piece.pos.x) {
                if (piece.move(new Vector2(piece.pos.x, i), board)) {
                    return true;
                }
            }

            if (i != piece.pos.y) {
                if (piece.move(new Vector2(i, piece.pos.y), board)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean pawnCheck(Piece piece, Board board) {
        // Three moves
        if (piece.color == Piece.colours.white) {
            for (int i = -1; i < 2; i++) {
                if (piece.move(new Vector2(piece.pos.x - 1, piece.pos.y + i), board)) {
                    return true;
                }
            }
        } else {
            for (int i = -1; i < 2; i++) {
                if (piece.move(new Vector2(piece.pos.x + 1, piece.pos.y + i), board)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean queenCheck(Piece piece, Board board) {
        return rookCheck(piece, board) || bishopCheck(piece, board);
    }

    //TODO: da check for castling need to ask how we interpret it when we recieve x and y pos form player(Should proly just read da code hehe)

    public boolean kingCheck(Piece piece, Board board) {
        // Up
        if (piece.move(new Vector2(piece.pos.x + 1, piece.pos.y + 1), board)) {
            return true;
        }
        if (piece.move(new Vector2(piece.pos.x - 1, piece.pos.y + 1), board)) {
            return true;
        }
        // Right
        if (piece.move(new Vector2(piece.pos.x, piece.pos.y + 1), board)) {
            return true;
        }
        if (piece.move(new Vector2(piece.pos.x, piece.pos.y - 1), board)) {
            return true;
        }
        // Down
        if (piece.move(new Vector2(piece.pos.x + 1, piece.pos.y), board)) {
            return true;
        }
        if (piece.move(new Vector2(piece.pos.x - 1, piece.pos.y), board)) {
            return true;
        }
        // Left
        if (piece.move(new Vector2(piece.pos.x - 1, piece.pos.y + 1), board)) {
            return true;
        }
        return piece.move(new Vector2(piece.pos.x + 1, piece.pos.y + 1), board);
    }

}