package com.GameLogic.Board.Pieces;

import com.GameLogic.Board.Board;
import com.GameLogic.Vector2;

import java.util.ArrayList;

public class King extends Piece {
    public King() {
    }

    public King(Vector2 pos, int value, colours color) {
        super(pos, value, color);
    }

    @Override
    public ArrayList<Vector2> getPossibleMoves(Board chessBoard) {
        ArrayList<Vector2> legalMoves = new ArrayList<Vector2>();

        int X = this.pos.getX();
        int Y = this.pos.getY();

        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                if (y == 0 && x == 0) continue;

                int newY = Y + y;
                int newX = X + x;

                if (newY > 7 || newY < 0 || newX > 7 || newX < 0) continue;

                Vector2 tempVec = new Vector2(newX, newY);

                if (chessBoard.getPieceAtPos(tempVec) != null) {
                    if (!this.sameColour(this.pos, tempVec, chessBoard)) {
                        legalMoves.add(tempVec);
                    }
                } else {
                    legalMoves.add(tempVec);
                }
            }
        }
        return legalMoves;
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
        return new King(new Vector2(this.pos.x, this.pos.y), value, color);
    }
}
