package com.GameLogic.Board.Pieces;

import com.GameLogic.Board.Board;
import com.GameLogic.Vector2;

import java.util.ArrayList;

public class Knight extends Piece {
    public Knight() {
    }

    public Knight(Vector2 pos, int value, colours color) {
        super(pos, value, color);
    }

    @Override
    public ArrayList<Vector2> getPossibleMoves(Board chessBoard) {

        ArrayList<Vector2> legalMoves = new ArrayList<Vector2>();

        int X = this.pos.getX();
        int Y = this.pos.getY();

        for (int y = Y - 2; y < Y + 3; y++) {
            if (y < 0) continue;
            if (y > 7) break;

            for (int x = X - 2; X < X + 3; x++) {
                if (x < 0) continue;
                if (x > 7) break;
                if (y == Y || x == X) continue;
                if (Math.abs(Y - y) == 1 && Math.abs(X - x) == 1) continue;

                var newPos = new Vector2(x, y);

                if (chessBoard.getPieceAtPos(newPos) != null) {
                    if (!this.sameColour(this.pos, newPos, chessBoard)) {
                        isLegalMove(this.pos, newPos, legalMoves);
                    }
                } else {
                    isLegalMove(this.pos, newPos, legalMoves);
                }
            }
        }
        return legalMoves;
    }

    public void isLegalMove(Vector2 currPos, Vector2 newPos, ArrayList<Vector2> legalMoves) {
        int deltaX = Math.abs(currPos.getX() - newPos.getX());
        int deltaY = Math.abs(currPos.getY() - newPos.getY());
        if (deltaX * deltaY == 2) legalMoves.add(newPos);
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
        return new Knight(new Vector2(this.pos.x, this.pos.y), value, color);
    }
}