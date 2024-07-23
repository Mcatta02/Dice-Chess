package com.GameLogic.EvalFunctions;

import com.GameLogic.Board.Board;

public class BlackBasicHeruistic implements Heuristic {
    @Override
    public double evaluate(Board board) {
        return EvaluationFunc.NumberOfBlackPieces(board) - EvaluationFunc.NumberOfWhitePieces(board);
    }
}
