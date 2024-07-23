package com.GameLogic.EvalFunctions.NeuralNet;

import com.GameLogic.Game;
import com.GameLogic.Board.Pieces.Piece;
import com.GameLogic.EvalFunctions.BlackBasicHeruistic;
import com.GameLogic.EvalFunctions.EvaluationFunc;
import com.GameLogic.EvalFunctions.NeuralNetHeuristic;
import com.GameLogic.EvalFunctions.WhiteBasicHeruistic;
import com.GameLogic.Players.ExpectiMaxPlayer;
import com.GameLogic.Players.Player;
import com.GameLogic.Players.randomAgent;
import com.GameLogic.Players.Player.PlayerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.Executors.newFixedThreadPool;
//TODO: Optimise and make really fancy so everything all good :)))))

import java.io.Serializable;

class genome implements Serializable{
    NeuralNet heuristic;
    int numberOfWins;

    public genome(NeuralNet heuristic, int numberOfWins) {
        this.heuristic = heuristic;
        this.numberOfWins = numberOfWins;
    }
}

public class NeuroEvolution implements Serializable{

    int inputSize;
    int hiddenSize;
    int outputSize;
    NeuralNet best;
    static genome whitegenomes[];
    static genome blackgenomes[];
    CountDownLatch terminatedTasks;
    int gen;
    final int THREAD_COUNT = 20;
    double[] ww = {
            -0.7919967651754334,
            0.9823884486732601,
            -0.6813830574352919,
            -0.5228713909764557,
            0.5833762789759536,
            0.6264175321095509,
            -0.6555119632060087,
            -0.1175578027883637,
            -0.802415194650421,
            0.25465944726446876,
            -0.2390846386926555
    };
    double[] wb = {
            -0.9074233326072669,
            -0.528668792415472,
            0.03940722461452917,
            0.521223653672312,
            0.466437965474106,
            -0.37583994317876823,
            0.7362650450030388,
            -0.9223082063497579,
            0.9478598430697069,
            -0.6682927021122793,
            0.4265190012414326
    };

    public NeuroEvolution(int inputSize, int hiddenSize, int outputSize, int genrations, int populationSize) {
        whitegenomes = new genome[populationSize / 2];
        blackgenomes = new genome[populationSize / 2];
        gen = genrations;

        for (int i = 0; i < whitegenomes.length; i++) {
            whitegenomes[i] = new genome(new NeuralNet(inputSize, hiddenSize, outputSize, 0.0), 0);
            blackgenomes[i] = new genome(new NeuralNet(inputSize, hiddenSize, outputSize, 0.0), 0);
        }
    }

    // TODO make the other constructor one

    /*
     * 
     * 50 games for 1 training cycle at moment
     * Play against itself
     * Play against random
     * Play against ga with expecgtimax
     * Using neuroevolution to train
     */

    public class Train implements Runnable{
        int index;

        public Train(int index){
            this.index = index;
        }

        @Override
        public void run() {
            ExpectiMaxPlayer whiteGAPlayer = new ExpectiMaxPlayer(3, Piece.colours.white, new EvaluationFunc(ww));
            ExpectiMaxPlayer blackGAPlayer = new ExpectiMaxPlayer(3, Piece.colours.black, new EvaluationFunc(wb));

            ExpectiMaxPlayer whiteBasicPlayer = new ExpectiMaxPlayer(3, Piece.colours.white, new WhiteBasicHeruistic());
            ExpectiMaxPlayer blackBasicPlayer = new ExpectiMaxPlayer(3, Piece.colours.black, new BlackBasicHeruistic());



            ExpectiMaxPlayer whitePlayer = new ExpectiMaxPlayer(3, Piece.colours.white,
                    new NeuralNetHeuristic(whitegenomes[index].heuristic));
            ExpectiMaxPlayer blackPlayer = new ExpectiMaxPlayer(3, Piece.colours.black,
                    new NeuralNetHeuristic(blackgenomes[index].heuristic));
            
                // for (int j = 0; j < 5; j++) {

                //     randomAgent whiteRandom = new randomAgent(Piece.colours.white, PlayerType.AGENT);
                //         randomAgent blackRandom = new randomAgent(Piece.colours.black, PlayerType.AGENT);
        
                //         whitegenomes[index].numberOfWins += play(whitePlayer, blackRandom);
                //         blackgenomes[index].numberOfWins += play(whiteRandom, blackPlayer);
                //     }

            for (int j = 0; j < 8; j++) {
                whitegenomes[index].numberOfWins += play(whitePlayer, new ExpectiMaxPlayer(3, Piece.colours.black,
                        new NeuralNetHeuristic(
                                blackgenomes[(int) (Math.random() * blackgenomes.length)].heuristic)));
                blackgenomes[index].numberOfWins += play(
                        new ExpectiMaxPlayer(3, Piece.colours.white,
                                new NeuralNetHeuristic(
                                        whitegenomes[(int) (Math.random() * blackgenomes.length)].heuristic)),
                        blackPlayer);

            }


                whitegenomes[index].numberOfWins += play(whitePlayer, blackGAPlayer);
                blackgenomes[index].numberOfWins += play(whiteGAPlayer, blackPlayer);
       


                whitegenomes[index].numberOfWins += play(whitePlayer, blackBasicPlayer);
                blackgenomes[index].numberOfWins += play(whiteBasicPlayer, blackPlayer);


         
            terminatedTasks.countDown();

        }
    }

    public void train() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) newFixedThreadPool(THREAD_COUNT);
        for (int z = 0; z < gen; z++) {
            System.out.println(z);

            terminatedTasks = new CountDownLatch(THREAD_COUNT);
            for (int i = 0; i < whitegenomes.length; i++) {
                
                executor.execute(new Train(i));
            }
            try {
                terminatedTasks.await();
            } catch (InterruptedException e) {
                throw new RuntimeException("failed to find all samples");
            }

            for (int j = 0; j < whitegenomes.length; j++) {
                for (int k = 0; k < whitegenomes.length; k++) {
                    genome swap;
                    if (whitegenomes[j].numberOfWins > whitegenomes[k].numberOfWins) {
                        swap = whitegenomes[j];
                        whitegenomes[j] = whitegenomes[k];
                        whitegenomes[k] = swap;
                    }
                    if (blackgenomes[j].numberOfWins > blackgenomes[k].numberOfWins) {
                        swap = blackgenomes[j];
                        blackgenomes[j] = blackgenomes[k];
                        blackgenomes[k] = swap;
                    }
                }
            }

            for (int i = 0; i < blackgenomes.length / 2; i++) {

                crossover(whitegenomes[(int) (Math.random() * blackgenomes.length / 2)].heuristic,
                        whitegenomes[(int) (Math.random() * blackgenomes.length / 2)].heuristic,
                        whitegenomes[i + blackgenomes.length / 2].heuristic);
                crossover(blackgenomes[(int) (Math.random() * blackgenomes.length / 2)].heuristic,
                        blackgenomes[(int) (Math.random() * blackgenomes.length / 2)].heuristic,
                        blackgenomes[i + blackgenomes.length / 2].heuristic);

                mutate(whitegenomes[i + blackgenomes.length / 2].heuristic);
                mutate(blackgenomes[i + blackgenomes.length / 2].heuristic);

            }
            ObjectToFile.writeObjectToFile(whitegenomes[0].heuristic,"WhiteNN"+z+".txt");
            ObjectToFile.writeObjectToFile(blackgenomes[0].heuristic,"BlackNN"+z+".txt");
            ObjectToFile.writeObjectToFile(this,"GEN:"+z+".txt");
        }

    }



    public void crossover(NeuralNet mama, NeuralNet papa, NeuralNet baba) {

        for (int i = 0; i < baba.hiddenWeights.length; i++) {
            baba.hiddenWeights[i] = crossover(mama.hiddenWeights[i], papa.hiddenWeights[i]);
        }
        for (int i = 0; i < baba.outputWeights.length; i++) {
            baba.outputWeights[i] = crossover(mama.outputWeights[i], papa.outputWeights[i]);
        }

    }

    public void mutate(NeuralNet genome) {

        for (int i = 0; i < genome.hiddenWeights.length; i++) {
            mutatate(genome.hiddenWeights[i]);
        }
        for (int i = 0; i < genome.outputWeights.length; i++) {
            mutatate(genome.outputWeights[i]);
        }
    }

    public double[] crossover(double[] genome1, double[] genome2) {
        double[] newWeights = new double[genome1.length];

        for (int i = 0; i < newWeights.length; i++) {
            double coinflip = Math.random();
            if (coinflip >= 0.5) {
                newWeights[i] = genome1[i];
            } else {
                newWeights[i] = genome2[i];
            }
        }
        return newWeights;
    }

    public void mutatate(double[] weights) {
        for (int i = 0; i < weights.length; i++) {
            double prob = Math.random();
            if (prob > 0.93) {
                weights[i] = Math.random() * 2 - 1;
                ;
            }
        }
    }

    public int play(Player whitePlayer, Player blackPlayer) {

        /*
         * Need to fix this
         */

        Game game = new Game(whitePlayer, blackPlayer);
        Piece.colours win = null;
        int num_move = 0;
        while (win == null && num_move < 100) {

            if (!whitePlayer.sendMove(game, game.movePiece)) {
                return 0;
            }

            win = game.winCheck();
            if (win != null) {
                break;
            }

            if (!blackPlayer.sendMove(game, game.movePiece)) {
                return 0;
            }

            win = game.winCheck();
            if (win != null) {
                break;
            }
            num_move++;
        }
        if (num_move == 100) {
            int num_white_pieces = EvaluationFunc.NumberOfWhitePieces(game.board);
            int num_black_pieces = EvaluationFunc.NumberOfWhitePieces(game.board);

            if (num_white_pieces == num_black_pieces) {
                return 0;
            }
            if (num_white_pieces > num_black_pieces) {
                return 2;
            } else if (num_black_pieces > num_white_pieces) {
                return 1;
            } else {
                return 0;
            }
        }
        if (win == Piece.colours.white) {
            return 2;
        } else if (win == Piece.colours.black) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        NeuroEvolution test = new NeuroEvolution(64, 32, 1, 1000, 40);
        test.train();
    }
}
