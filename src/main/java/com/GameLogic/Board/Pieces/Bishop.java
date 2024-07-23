package com.GameLogic.Board.Pieces;

import com.GameLogic.Board.Board;
import com.GameLogic.Vector2;

import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop() {
    }

    public Bishop(Vector2 pos, int value, colours color) {
        super(pos, value, color);
    }

    public ArrayList<Vector2> getPossibleMoves(Board chessBoard) {

        ArrayList<Vector2> legalMoves = new ArrayList<Vector2>();

        int X = this.pos.getX();
        int Y = this.pos.getY();

        while (true) {
            Y--;
            X++;
            if (Y < 0 || X > 7 || X < 0 || Y > 7) {
                break;
            }
            Vector2 tempVec = new Vector2(X, Y);

            if (chessBoard.getPieceAtPos(tempVec) != null) {
                if (!this.sameColour(this.pos, tempVec, chessBoard)) {
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
            X++;
            if (Y < 0 || X > 7 || X < 0 || Y > 7) {
                break;
            }
            Vector2 tempVec = new Vector2(X, Y);

            if (chessBoard.getPieceAtPos(tempVec) != null) {
                if (!this.sameColour(this.pos, tempVec, chessBoard)) {
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
            X--;
            if (Y < 0 || X > 7 || X < 0 || Y > 7) {
                break;
            }
            Vector2 tempVec = new Vector2(X, Y);

            if (chessBoard.getPieceAtPos(tempVec) != null) {
                if (!this.sameColour(this.pos, tempVec, chessBoard)) {
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
            X--;
            if (Y < 0 || X > 7 || X < 0 || Y > 7) {
                break;
            }
            Vector2 tempVec = new Vector2(X, Y);

            if (chessBoard.getPieceAtPos(tempVec) != null) {
                if (!this.sameColour(this.pos, tempVec, chessBoard)) {
                    legalMoves.add(tempVec);
                }
                break;
            }
            legalMoves.add(tempVec);
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
        return new Bishop(new Vector2(this.pos.x, this.pos.y), value, color);
    }
}