package com.GameLogic.Players;

import com.GameLogic.Board.Pieces.Piece;
import com.GameLogic.Game;

public abstract class Player {

    public enum PlayerType {
        USER, AGENT
    }

    public Piece.colours team;
    public PlayerType type;

    public Player(Piece.colours team, PlayerType type) {
        this.type = type;
        this.team = team;
    }

    //When implemented in ui or ai just inherit from class and overide this method
    // Return null if want to promote
    abstract public boolean sendMove(Game game, Piece piece);

}
