package com.GameLogic.Players;

import com.GameLogic.Board.Pieces.Piece;
import com.GameLogic.Game;
import com.GameLogic.Vector2;

public class UserPlayer extends Player {

    Vector2[] move;

    public UserPlayer(Piece.colours team) {
        super(team, PlayerType.USER);
    }

    // TODO: need to define what happpppens when we need to do promoties
    public void setMove(Vector2[] move) {
        this.move = move;
    }

    @Override
    public boolean sendMove(Game game, Piece piece) {
        return game.updateState(move, piece);
    }

}