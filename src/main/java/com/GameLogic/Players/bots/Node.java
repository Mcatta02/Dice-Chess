package com.GameLogic.Players.bots;

import com.GameLogic.Board.Board;
import com.GameLogic.Board.Pieces.Piece;
import com.GameLogic.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private Board chessBoard;
    private float evalOutcome;
    private Node parent;
    private final List<Node> children = new ArrayList<>();
    private int visits;
    private Piece piece;
    private Vector2 move;

    public Node(Board chessBoard, Node parent) {
        this.chessBoard = chessBoard;
        this.parent = parent;
        visits = 0;
    }

    public Node(Board chessBoard, float evalOutcome, Node parent) {
        this.chessBoard = chessBoard;
        this.evalOutcome = evalOutcome;
        this.parent = parent;
    }

    public Board getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(Board chessBoard) {
        this.chessBoard = chessBoard;
    }

    public float getEvalOutcome() {
        return evalOutcome;
    }

    public void setEvalOutcome(float evalOutcome) {
        this.evalOutcome = evalOutcome;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addChild(Node childNode) {
        this.children.add(childNode);
    }

    public int getVisits() {
        return visits;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setMove(Vector2 move) {
        this.move = move;
    }

    public Vector2 getMove() {
        return move;
    }
}