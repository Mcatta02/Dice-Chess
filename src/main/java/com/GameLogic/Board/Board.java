package com.GameLogic.Board;

import com.GameLogic.Board.Pieces.*;
import com.GameLogic.Vector2;

import java.util.LinkedList;
import java.util.List;

public class Board {
    public Piece[][] board = new Piece[8][8];
    public Piece moveHelper;

    public Piece[][] setupBoard() {

        for (int i = 0; i < board.length; i++) {
            board[1][i] = new Pawn(new Vector2(1, i), 1, Piece.colours.black);
            board[6][i] = new Pawn(new Vector2(6, i), 1, Piece.colours.white);

            switch (i) {
                case 0 -> {
                    board[0][i] = new Rook(new Vector2(0, i), 5, Piece.colours.black);// rook
                    board[7][i] = new Rook(new Vector2(7, i), 5, Piece.colours.white);
                    board[0][7 - i] = new Rook(new Vector2(0, 7 - i), 5, Piece.colours.black);
                    board[7][7 - i] = new Rook(new Vector2(7, 7 - i), 5, Piece.colours.white);
                }
                case 1 -> {
                    board[0][i] = new Knight(new Vector2(0, i), 3, Piece.colours.black);// Knight
                    board[7][i] = new Knight(new Vector2(7, i), 3, Piece.colours.white);
                    board[0][7 - i] = new Knight(new Vector2(0, 7 - i), 3, Piece.colours.black);
                    board[7][7 - i] = new Knight(new Vector2(7, 7 - i), 3, Piece.colours.white);
                }
                case 2 -> {
                    board[0][i] = new Bishop(new Vector2(0, i), 3, Piece.colours.black);// rook
                    board[7][i] = new Bishop(new Vector2(7, i), 3, Piece.colours.white);
                    board[0][7 - i] = new Bishop(new Vector2(0, 7 - i), 3, Piece.colours.black);
                    board[7][7 - i] = new Bishop(new Vector2(7, 7 - i), 3, Piece.colours.white);
                }
                case 3 -> {
                    board[0][i] = new Queen(new Vector2(0, i), 9, Piece.colours.black);
                    board[7][i] = new Queen(new Vector2(7, i), 9, Piece.colours.white);
                    board[0][7 - i] = new King(new Vector2(0, 7 - i), 10, Piece.colours.black);
                    board[7][7 - i] = new King(new Vector2(7, 7 - i), 10, Piece.colours.white);
                }
            }
        }

        return board;
    }

    public Piece getPieceAtPos(Vector2 pos) {
        if (pos == null) {
            return null;
        }
        return board[pos.x][pos.y];
    }

    @Override
    public Board clone() {
        Board newBoard = new Board();

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board.length; j++) {
                if (this.board[i][j] != null)
                    newBoard.board[i][j] = this.board[i][j].clone();
            }
        }
        return newBoard;

    }

    @Override
    public String toString() {

        StringBuilder out = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                out.append(this.board[i][j]);
            }
            out.append("\n");
        }

        return out.toString();
    }

    // TODO: Make this fucking beautiful method
    public List<pieceAndProb> getPieceProbabilities(Piece.colours color) {
        List<pieceAndProb> probs = new LinkedList<>();
        List<Piece> pieces = new LinkedList<>();
        double numTypeOfPieces = 0;
        boolean king = false, knight = false, pawn = false, queen = false, bishop = false, rook = false;

        for (Piece[] value : this.board) {
            for (int j = 0; j < this.board.length; j++) {
                if (value[j] != null && value[j].color == color) {
                    List<Vector2> moves = value[j].getPossibleMoves(this);
                    if (moves.size() != 0) {
                        pieces.add(value[j]);
                        if (value[j] instanceof King) {
                            if (!king) {
                                king = true;
                                numTypeOfPieces++;
                            }
                        } else if (value[j] instanceof Knight) {
                            if (!knight) {
                                knight = true;
                                numTypeOfPieces++;
                            }
                        } else if (value[j] instanceof Queen) {
                            if (!queen) {
                                queen = true;
                                numTypeOfPieces++;
                            }
                        } else if (value[j] instanceof Pawn) {
                            if (!pawn) {
                                pawn = true;
                                numTypeOfPieces++;
                            }
                        } else if (value[j] instanceof Bishop) {
                            if (!bishop) {
                                bishop = true;
                                numTypeOfPieces++;
                            }
                        } else if (value[j] instanceof Rook) {
                            if (!rook) {
                                rook = true;
                                numTypeOfPieces++;
                            }
                        }
                    }
                }
            }
        }

        for (Piece piece : pieces) {
            probs.add(new pieceAndProb(piece, 1 / numTypeOfPieces));
        }

        return probs;
    }

    public void movePiece(Vector2 currentPos, Vector2 chosenPos) {
        moveHelper = this.getPieceAtPos(currentPos);
        moveHelper.pos = chosenPos;
        moveHelper.hasBeenMoved = true;
        this.board[chosenPos.getX()][chosenPos.getY()] = moveHelper;
        this.board[currentPos.getX()][currentPos.getY()] = null;
    }

    public List<Piece> getAllPieces(Piece piece) {
        List<Piece> allpiecepos = new LinkedList<>();
        for (Piece[] pieces : board) {
            for (int j = 0; j < board.length; j++) {
                if (pieces[j] != null && pieces[j].getClass() == piece.getClass() && pieces[j].color == piece.color) {
                    allpiecepos.add(pieces[j]);
                }
            }
        }
        return allpiecepos;
    }

    public Piece.colours checkGameState() {
        boolean whiteKing = false;
        boolean blackKing = false;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    if (board[i][j] instanceof King) {
                        if (board[i][j].color == Piece.colours.white) {
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
        }
        if (!blackKing) {
            return Piece.colours.white;
        }
        return null;
    }

    public List<Piece> getMovablePieces(Piece.colours color) {
        List<Piece> pieces = new LinkedList<>();

        for (Piece[] value : this.board) {
            for (int j = 0; j < this.board.length; j++) {
                if (value[j] != null && value[j].color == color) {
                    List<Vector2> moves = value[j].getPossibleMoves(this);
                    if (moves.size() != 0) {
                        pieces.add(value[j]);
                    }
                }
            }
        }

        return pieces;
    }

    public List<Board> generateBoardStates(Piece piece) {
        List<Board> boardStates = new LinkedList<>();
        List<Vector2> moves = piece.getPossibleMoves(this);

        for (Vector2 move : moves) {
            Board newBoard = clone();
            newBoard.movePiece(piece.getPos(), move);
            boardStates.add(newBoard);
        }
        return boardStates;
    }

    public record pieceAndProb(Piece piece, double prob) {
    }

    /*
     * Might wanna change it so the board stores two boards one with doubles and one
     * with objects as this might
     * fuck the proccessing time
     */

    public double[] encode() {
        double[] encodedBoard = new double[64];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] instanceof King) {
                    if (board[i][j].color == Piece.colours.white) {
                        encodedBoard[i+j] = 6;
                    } else {
                        encodedBoard[i+j] = -6;
                    }

                } else if (board[i][j] instanceof Knight) {
                    if (board[i][j].color == Piece.colours.white) {
                        encodedBoard[i+j] = 2;
                    } else {
                        encodedBoard[i+j] = -2;
                    }

                } else if (board[i][j] instanceof Queen) {
                    if (board[i][j].color == Piece.colours.white) {
                        encodedBoard[i+j] = 5;
                    } else {
                        encodedBoard[i+j] = -5;
                    }

                } else if (board[i][j] instanceof Pawn) {
                    if (board[i][j].color == Piece.colours.white) {
                        encodedBoard[i+j] = 1;
                    } else {
                        encodedBoard[i+j] = -1;
                    }

                } else if (board[i][j] instanceof Bishop) {
                    if (board[i][j].color == Piece.colours.white) {
                        encodedBoard[i+j] = 3;
                    } else {
                        encodedBoard[i+j] = -3;
                    }
                } else if (board[i][j] instanceof Rook) {
                    if (board[i][j].color == Piece.colours.white) {
                        encodedBoard[i+j] = 4;
                    } else {
                        encodedBoard[i+j] = -4;
                    }
                } else {
                    encodedBoard[i+j] = 0;
                }
            }
        }
        return encodedBoard;
    }
}
