package com.GameLogic.Board.Pieces;

import com.GameLogic.Board.Board;
import com.GameLogic.Vector2;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Pawn extends Piece {
    public Pawn() {
    }

    public Pawn(Vector2 pos, int value, colours color) {
        super(pos, value, color);
    }

    @Override
    public ArrayList<Vector2> getPossibleMoves(Board chessBoard) {
        ArrayList<Vector2> moves = new ArrayList<>();

        if (this.color == colours.white) {
            if (this.move(new Vector2(this.pos.x - 2, this.pos.y), chessBoard)) {
                moves.add(new Vector2(this.pos.x - 2, this.pos.y));
            }
            for (int i = -1; i < 2; i++) {
                if (this.move(new Vector2(pos.x - 1, pos.y + i), chessBoard)) {
                    moves.add(new Vector2(pos.x - 1, pos.y + i));
                }

            }
        } else {
            if (this.move(new Vector2(this.pos.x + 2, this.pos.y), chessBoard)) {
                moves.add(new Vector2(this.pos.x + 2, this.pos.y));

            }
            for (int i = -1; i < 2; i++) {
                if (this.move(new Vector2(pos.x + 1, pos.y + i), chessBoard)) {
                    moves.add(new Vector2(pos.x + 1, pos.y + i));
                }
            }
        }
        return moves;
    }

    @Override
    public Boolean move(Vector2 chosenMove, Board currentBoard) {
        if (inBoundsCheck(chosenMove)) {
            return false;
        }

        int currentColumn = this.pos.getY();
        int currentRow = this.pos.getX();
        int chosenColumn = chosenMove.getY();
        int chosenRow = chosenMove.getX();
        int deltaColumn = abs(chosenColumn - currentColumn);
        int deltaRow = abs(chosenRow - currentRow);

        if (deltaRow == 2 && deltaColumn == 0 && currentBoard.getPieceAtPos(chosenMove)==null) { // this move is only possible in the first move
            return ((currentRow == 6) && (this.color.equals(colours.white))) || // check whether it's the first move
                    ((currentRow == 1) && (this.color.equals(colours.black)));
            // No need to check whether there's a piece in the way since it's the first move
        }

        if (deltaRow == 1) { // only legal move after the first move is one step forwards or backwards
            if (deltaColumn > 1) { // it's moving in a vertical line
                return false; // there's a piece in the way, but we can only capture diagonally
            }

            if (currentBoard.getPieceAtPos(this.getPos()) == null) {
                return false;
            }
            if (currentBoard.getPieceAtPos(this.getPos()).color == colours.white) {
                if (((currentRow - chosenRow) == 1) && (deltaColumn == 0) && currentBoard.getPieceAtPos(chosenMove) == null) {
                    return true;
                }
                if (((currentRow - chosenRow) == 1) && (deltaColumn == 1) && currentBoard.getPieceAtPos(chosenMove) != null) {
                    return currentBoard.getPieceAtPos(chosenMove).color == colours.black;
                }
            }
            if (currentBoard.getPieceAtPos(this.getPos()).color == colours.black) {
                if (((currentRow - chosenRow) == -1) && (deltaColumn == 0) && currentBoard.getPieceAtPos(chosenMove) == null) {
                    return true;
                }
                if (((currentRow - chosenRow) == -1) && (deltaColumn == 1) && currentBoard.getPieceAtPos(chosenMove) != null) {
                    return currentBoard.getPieceAtPos(chosenMove).color == colours.white;
                }
            }
        }
        return false;
    }

    @Override
    public Piece clone() {
        return new Pawn(new Vector2(this.pos.x, this.pos.y), value, color);
    }
}