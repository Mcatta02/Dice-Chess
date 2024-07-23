package com.GameLogic.EvalFunctions;

import com.GameLogic.Game;
import com.GameLogic.Board.Board;
import com.GameLogic.Board.Pieces.Piece;
import com.GameLogic.EvalFunctions.NeuralNet.NeuralNet;
import com.GameLogic.Players.ExpectiMaxPlayer;
import com.GameLogic.Players.Player;

public class NeuralNetHeuristic  implements Heuristic{
    NeuralNet heuristic;


    public NeuralNetHeuristic(int inputSize, int hiddenSize, int outputSize,double learningRate,double [][] hiddenWeights,double [][] outputWeights,double [] hiddenBias, double[] outputBias){
        heuristic = new NeuralNet(inputSize,hiddenSize,outputSize,learningRate,hiddenWeights,outputWeights,hiddenBias,outputBias);
    }
    public NeuralNetHeuristic(NeuralNet heuristic){
        this.heuristic = heuristic;
    }



    
    @Override
    public double evaluate(Board board) {
        // TODO Auto-generated method stub
        return heuristic.predict(board.encode())[0];
    }
    
}
