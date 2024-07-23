package com.GameLogic.Board.Pieces;

import com.GameLogic.Board.Board;
import com.GameLogic.Vector2;

import java.util.ArrayList;

public abstract class Piece {

    public colours color;
    public Vector2 pos;
    public int value;
    public boolean hasBeenMoved;

    {
        hasBeenMoved = false;
    }

    public Piece() {
    }

    public Piece(Vector2 pos, int value, colours color) {
        this.pos = pos;
        this.value = value;
        this.color = color;
    }

    public abstract ArrayList<Vector2> getPossibleMoves(Board chessBoard);

    public abstract Boolean move(Vector2 chosenMove, Board currentBoard);

    public Boolean sameColour(Vector2 currentPosition, Vector2 chosenMove, Board currentBoard) {
        Piece chosen = currentBoard.board[chosenMove.getX()][chosenMove.getY()];
        Piece current = currentBoard.board[currentPosition.getX()][currentPosition.getY()];
        if (chosen != null && current != null)
            return chosen.color.equals(current.color);

        return false;
    }

    @Override
    public abstract Piece clone();

    public boolean inBoundsCheck(Vector2 move) {
        return ((move.x < 0) || (move.x >= 8) || (move.y < 0) || (move.y >= 8));
    }

    public colours getColor() {
        return color;
    }

    public void setColor(colours color) {
        this.color = color;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isHasBeenMoved() {
        return hasBeenMoved;
    }

    public void setHasBeenMoved(boolean hasBeenMoved) {
        this.hasBeenMoved = hasBeenMoved;
    }

    public enum colours {
        black, white
    }
}