package com.GameLogic.Board.Pieces;

import com.GameLogic.Board.Board;
import com.GameLogic.Vector2;

import java.util.ArrayList;

public class Rook extends Piece {

    public Rook() {
    }

    public Rook(Vector2 pos, int value, colours color) {
        super(pos, value, color);
    }

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
    public ArrayList<Vector2> getPossibleMoves(Board currentBoard) {


        ArrayList<Vector2> legalMoves = new ArrayList<Vector2>();

        int X = this.pos.getX();
        int Y = this.pos.getY();

        while (true) {

            X++;
            if (Y < 0 || X > 7 || X < 0 || Y > 7) {
                break;
            }
            Vector2 tempVec = new Vector2(X, Y);

            if (currentBoard.getPieceAtPos(tempVec) != null) {
                if (!this.sameColour(this.pos, tempVec, currentBoard)) {
                    legalMoves.add(tempVec);
                }
                break;
            }
            legalMoves.add(tempVec);
        }

        X = this.pos.getX();
        Y = this.pos.getY();

        while (true) {
            Y++;

            if (Y < 0 || X > 7 || X < 0 || Y > 7) {
                break;
            }
            Vector2 tempVec = new Vector2(X, Y);

            if (currentBoard.getPieceAtPos(tempVec) != null) {
                if (!this.sameColour(this.pos, tempVec, currentBoard)) {
                    legalMoves.add(tempVec);
                }
                break;
            }
            legalMoves.add(tempVec);
        }

        X = this.pos.getX();
        Y = this.pos.getY();


        while (true) {

            X--;
            if (Y < 0 || X > 7 || X < 0 || Y > 7) {
                break;
            }
            Vector2 tempVec = new Vector2(X, Y);

            if (currentBoard.getPieceAtPos(tempVec) != null) {
                if (!this.sameColour(this.pos, tempVec, currentBoard)) {
                    legalMoves.add(tempVec);
                }
                break;
            }
            legalMoves.add(tempVec);
        }

        X = this.pos.getX();
        Y = this.pos.getY();

        while (true) {
            Y--;

            if (Y < 0 || X > 7 || X < 0 || Y > 7) {
                break;
            }
            Vector2 tempVec = new Vector2(X, Y);

            if (currentBoard.getPieceAtPos(tempVec) != null) {
                if (!this.sameColour(this.pos, tempVec, currentBoard)) {
                    legalMoves.add(tempVec);
                }
                break;
            }
            legalMoves.add(tempVec);
        }


        return legalMoves;
    }

    @Override
    public Piece clone() {
        return new Rook(new Vector2(this.pos.x, this.pos.y), value, color);
    }
}
