package com.GameLogic.Board.Pieces;

import com.GameLogic.Board.Board;
import com.GameLogic.Vector2;

import java.util.ArrayList;

public class Queen extends Piece {

    /*
    The Queen can move 1-7 squares in any direction, up, down, left, right, or diagonal,
    until the Queen reaches an obstruction or captures a piece;
    however, the Queen cannot jump over pieces and can only capture one piece per turn.
     */

    ArrayList<Vector2> possibleMoves = new ArrayList<>();

    public Queen() {
    }

    public Queen(Vector2 pos, int value, colours color) {
        super(pos, value, color);
    }

    @Override
    public ArrayList<Vector2> getPossibleMoves(Board chessBoard) {
        Rook tempRook = new Rook();
        Bishop tempBishop = new Bishop();
        tempBishop.setPos(this.getPos());
        tempBishop.setColor(this.getColor());
        tempRook.setColor(this.getColor());
        tempRook.setPos(this.getPos());
        possibleMoves = tempRook.getPossibleMoves(chessBoard);
        possibleMoves.addAll(tempBishop.getPossibleMoves(chessBoard));
        return possibleMoves;
    }

    @Override
    public Boolean move(Vector2 chosenMove, Board currentBoard) { // try to move the Knight piece

        ArrayList<Vector2> arrayOfPossibleMoves;
        arrayOfPossibleMoves = this.getPossibleMoves(currentBoard);

        for (Vector2 arrayOfPossibleMove : arrayOfPossibleMoves) {
            if (chosenMove.x == arrayOfPossibleMove.x && chosenMove.y == arrayOfPossibleMove.y) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Piece clone() {
        return new Queen(new Vector2(this.pos.x, this.pos.y), value, color);
    }
}
