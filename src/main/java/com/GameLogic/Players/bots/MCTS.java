package com.GameLogic.Players.bots;

import com.GameLogic.Board.Board;
import com.GameLogic.Board.Pieces.Piece;
import com.GameLogic.EvalFunctions.BlackBasicHeruistic;
import com.GameLogic.EvalFunctions.Heuristic;
import com.GameLogic.EvalFunctions.WhiteBasicHeruistic;
import com.GameLogic.Game;
import com.GameLogic.Players.Player;
import com.GameLogic.Vector2;

import java.util.ArrayList;
import java.util.List;

public class MCTS extends Player {

    private final Heuristic heuristic;
    private final Piece.colours team;
    int depth;
    int maxDepth;
    static Game game;

    public MCTS(int maxDepth, Piece.colours team, Heuristic heuristic) {
        super(team, PlayerType.AGENT);

        this.team = team;
        this.heuristic = heuristic;
        depth = 0;
        this.maxDepth = maxDepth;
    }

    // First step of monte carlos
    private Node selection(Node current) {
        double max_value = -999;
        Node selectedChild = null;

        for (int i = 0; i < current.getChildren().size(); i++) {
            if (current.getChildren().get(i).getEvalOutcome() > max_value) {
                max_value = current.getChildren().get(i).getEvalOutcome();
                selectedChild = current.getChildren().get(i);
            }
        }
        return selectedChild;
    }

    // Second step of monte carlos
    // adding a leaf node to the tree
    // keep calling the child with maximum priority until we reach the current leaf node
    private Node expansion(Node selected) {
        if (depth == maxDepth) return selected;

        List<Piece> pieces = selected.getChessBoard().getMovablePieces(team);
        List<Board> childBoards = new ArrayList<>();

        for (Piece piece : pieces) {
            List<Board> boardStates = selected.getChessBoard().generateBoardStates(piece);
            childBoards.addAll(boardStates);
        }

        for (Board childBoard : childBoards) {
            Node newChild = new Node(childBoard, (float) heuristic.evaluate(childBoard), selected);
            selected.addChild(newChild);
        }

        float bestEval = -999;
        Node bestChild = null;

        for (int i = 0; i < selected.getChildren().size(); i++) {
            if (selected.getChildren().get(i).getEvalOutcome() > bestEval) {
                bestEval = selected.getChildren().get(i).getEvalOutcome();
                bestChild = selected.getChildren().get(i);
            }
        }
        assert bestChild != null;
        depth++;
        return expansion(bestChild);
    }

    // Last step of monte carlo tree search
    // Traverse the reward from step 2 until the root of the tree

    private Node backpropagation(Node current) {

        while (current.getParent() != null) {
            current = current.getParent();
        }
        return current;
    }

    // Make the best possible move
    @Override
    public boolean sendMove(Game game, Piece pieceToMove) {
        Node root = new Node(game.board.clone(), null);

        List<Piece> allPieces = game.board.getAllPieces(pieceToMove);

        for (Piece currentPiece : allPieces) {
            List<Vector2> moves = currentPiece.getPossibleMoves(game.board);
            for (Vector2 move : moves) {
                if (move == null) {
                    break;
                }

                Board cloneboard = game.board.clone();
                cloneboard.movePiece(currentPiece.pos, move);

                List<Vector2> pieceMoves = currentPiece.getPossibleMoves(root.getChessBoard());

                for (Vector2 pieceMove : pieceMoves) {
                    Board childBoard = root.getChessBoard().clone();
                    childBoard.movePiece(currentPiece.getPos(), pieceMove);

                    // The parent of these children must be null, since we back propagate until there is no parent.
                    // Since the root is the original state of the game, we need one of its children to choose as the next move.
                    Node newChild = new Node(childBoard, (float) heuristic.evaluate(childBoard), null);
                    newChild.setPiece(currentPiece);
                    newChild.setMove(pieceMove);

                    // We do add the children to the root, so that we can pass the root onto the mcts algorithm.
                    root.addChild(newChild);
                }
            }
        }

        Node mctsOutcome = backpropagation(expansion(selection(root)));

        if (mctsOutcome == null) {
            return false;
        }

        Vector2[] move = {mctsOutcome.getPiece().getPos(), mctsOutcome.getMove()};
        game.updateState(move, mctsOutcome.getPiece());

        return true;
    }

    public static void play() {
        MCTS whitePlayer = new MCTS(5, Piece.colours.white, new WhiteBasicHeruistic());
        MCTS blackPlayer = new MCTS(5, Piece.colours.black, new BlackBasicHeruistic());
        game = new Game(whitePlayer, blackPlayer);
        Piece.colours win = null;

        // roll the die for a move and play the game until someone has won
        while (win == null) {
            whitePlayer.sendMove(game, game.movePiece);
            win = game.winCheck();
            if (win != null) {
                break;
            }
            blackPlayer.sendMove(game, game.movePiece);
            win = game.winCheck();
        }

        // check who won the game
        if (win == Piece.colours.white) {
            System.out.println("You won!");
        } else {
            System.out.println("You lost!");
        }
    }

    public static void main(String[] args) {
        play();
    }
}
