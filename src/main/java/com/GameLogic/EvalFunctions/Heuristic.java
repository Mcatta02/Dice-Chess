package com.GameLogic.EvalFunctions;

import com.GameLogic.Board.Board;

public interface Heuristic {
    double evaluate(Board board);
}
