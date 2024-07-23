package com.GameLogic.Players;

import com.GameLogic.Board.Board;
import com.GameLogic.Board.Board.pieceAndProb;
import com.GameLogic.Board.Pieces.King;
import com.GameLogic.Board.Pieces.Piece;
import com.GameLogic.EvalFunctions.BlackBasicHeruistic;
import com.GameLogic.EvalFunctions.Heuristic;
import com.GameLogic.EvalFunctions.WhiteBasicHeruistic;
import com.GameLogic.Game;
import com.GameLogic.Vector2;

import java.util.LinkedList;
import java.util.List;

public class ExpectiMaxPlayer extends Player {
    int depth;
    int currentDepth;
    Piece.colours currentPlayer;
    Piece.colours home;
    Piece.colours adversary;
    Heuristic heuristic;


    public ExpectiMaxPlayer(int depth, Piece.colours currentPlayer, Heuristic heuristic) {
        super(currentPlayer, PlayerType.AGENT);
        this.depth = depth;
        currentDepth = depth;
        this.currentPlayer = currentPlayer;
        this.home = currentPlayer;
        this.heuristic = heuristic;

        if (currentPlayer == Piece.colours.white) {
            adversary = Piece.colours.black;
        } else {
            adversary = Piece.colours.white;
        }
    }


    // Piece has to be specific to the pos
    public double expectiminimax(List<pieceAndProb> pieceAndProbs, Board board, Piece.colours currentPlayer, int currentDepth) {
        double value;
        Piece piece = null;
        boolean probLayer = false;

        if (pieceAndProbs.size() == 1) {
            piece = pieceAndProbs.get(0).piece();
        } else {
            probLayer = true;
        }

        if (checkGameState(board) != null || currentDepth == 0) {
            return heuristic.evaluate(board);
        }
        //Min
        if (!probLayer && currentPlayer == adversary) {
            value = Double.MAX_VALUE;
            currentPlayer = home;
            List<Board> children = generateBoardStates(piece, board);
            for (Board child : children) {
                value = Math.min(value, expectiminimax(child.getPieceProbabilities(currentPlayer), child, currentPlayer, currentDepth - 1));
            }
        } else if (!probLayer && currentPlayer == home) {
            value = -Double.MAX_VALUE;
            currentPlayer = adversary;
            List<Board> children = generateBoardStates(piece, board);
            for (Board child : children) {
                value = Math.max(value, expectiminimax(child.getPieceProbabilities(currentPlayer), child, currentPlayer, currentDepth - 1));
            }
        } else {
            value = 0;
            for (pieceAndProb piecewithProb : pieceAndProbs) {
                List<pieceAndProb> tmp = new LinkedList<>();
                tmp.add(piecewithProb);
                value += piecewithProb.prob() * expectiminimax(tmp, board, currentPlayer, currentDepth - 1);
            }
        }
        return value;
    }

    //TODO: REFACTOR THIS SHIT LATER
    public Piece.colours checkGameState(Board board) {
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
        }
        if (!blackKing) {
            return Piece.colours.white;
        }
        return null;
    }

    public List<Board> generateBoardStates(Piece piece, Board board) {
        List<Board> boardStates = new LinkedList<>();
        List<Vector2> moves = piece.getPossibleMoves(board);

        for (Vector2 move : moves) {
            Board newBoard = board.clone();
            newBoard.movePiece(piece.getPos(), move);
            boardStates.add(newBoard);
        }
        return boardStates;
    }

    @Override
    public boolean sendMove(Game game, Piece piece) {

        List<Piece> allPieces = getAllPieces(game.board, piece);
        Vector2 bestMove = null;
        Piece bestPiece = null;
        double value = -Double.MAX_VALUE;
        boolean first = true;

        for (Piece currentPiece : allPieces) {
            List<Vector2> moves = currentPiece.getPossibleMoves(game.board);
            for (Vector2 move : moves) {
                if (move == null) {
                    System.out.println("WTF");
                    break;
                }
                Board cloneboard = game.board.clone();
                cloneboard.movePiece(currentPiece.pos, move);
                List<pieceAndProb> pieceInRightDataStructCauseAmBadProgrammer = cloneboard.getPieceProbabilities(adversary);
                double tmp = expectiminimax(pieceInRightDataStructCauseAmBadProgrammer, cloneboard, piece.color, depth);
                if (first) {
                    value = tmp;
                    bestMove = move;
                    bestPiece = currentPiece;
                    first = false;
                } else if (tmp > value) {
                    value = tmp;
                    bestMove = move;
                    bestPiece = currentPiece;
                }
            }
        }


        if (bestPiece == null) {
            return false;
        }
        Vector2[] move = {bestPiece.pos, bestMove};

        game.updateState(move, bestPiece);
        return true;
    }

    public List<Piece> getAllPieces(Board board, Piece piece) {
        List<Piece> allpiecepos = new LinkedList<>();
        for (int i = 0; i < board.board.length; i++) {
            for (int j = 0; j < board.board.length; j++) {
                if (board.board[i][j] != null && board.board[i][j].getClass() == piece.getClass() && board.board[i][j].color == piece.color) {
                    allpiecepos.add(board.board[i][j]);
                }
            }
        }
        return allpiecepos;
    }

    public static void main(String[] args) {
        ExpectiMaxPlayer whitePlayer = new ExpectiMaxPlayer(5, Piece.colours.white, new WhiteBasicHeruistic());
        ExpectiMaxPlayer blackPlayer = new ExpectiMaxPlayer(5, Piece.colours.black, new BlackBasicHeruistic());
        Game game = new Game(whitePlayer, blackPlayer);
        Piece.colours win = null;
        int num_move = 0;
        while (win == null) {
            whitePlayer.sendMove(game, game.movePiece);
            blackPlayer.sendMove(game, game.movePiece);
            win = game.winCheck();
            num_move++;
        }
    }
}