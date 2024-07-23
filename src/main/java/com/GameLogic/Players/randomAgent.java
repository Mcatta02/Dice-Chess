package com.GameLogic.Players;

import com.GameLogic.Board.Pieces.Piece;
import com.GameLogic.Game;
import com.GameLogic.Vector2;

import java.util.ArrayList;

public class randomAgent extends Player {
    public randomAgent(Piece.colours team, com.GameLogic.Players.Player.PlayerType type) {
        super(team, type);
    }

    @Override
    public boolean sendMove(Game game, Piece piece) {
        ArrayList<Vector2[]> possibleMoves = new ArrayList<>();
        for (int i = 0; i < game.board.board.length; i++) {
            for (int j = 0; j < game.board.board.length; j++) {
                if (game.board.board[i][j] != null && game.board.board[i][j].color == super.team && game.board.board[i][j].getClass() == piece.getClass()) {
                    ArrayList<Vector2> moves = game.board.board[i][j].getPossibleMoves(game.board);
                    for (Vector2 move : moves) {
                        Vector2[] m = {game.board.board[i][j].pos, move};
                        possibleMoves.add(m);
                    }
                }
            }
        }
        int index = (int) (Math.random() * possibleMoves.size());
        return game.updateState(possibleMoves.get(index), game.board.getPieceAtPos(possibleMoves.get(index)[0]));
    }
}
