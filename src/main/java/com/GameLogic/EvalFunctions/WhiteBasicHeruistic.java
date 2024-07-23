package com.GameLogic.EvalFunctions;

import com.GameLogic.Board.Board;

public class WhiteBasicHeruistic implements Heuristic {
    @Override
    public double evaluate(Board board) {
        return EvaluationFunc.NumberOfWhitePieces(board) - EvaluationFunc.NumberOfBlackPieces(board);
    }
}
