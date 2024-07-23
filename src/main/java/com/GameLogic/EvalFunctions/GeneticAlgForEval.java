package com.GameLogic.EvalFunctions;

import com.GameLogic.Board.Board;
import com.GameLogic.Board.Pieces.Piece;
import com.GameLogic.EvalFunctions.NeuralNet.ObjectToFile;
import com.GameLogic.Game;
import com.GameLogic.Players.ExpectiMaxPlayer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class GeneticAlgForEval {
    EvaluationFunc[] whiteGenomes;
    EvaluationFunc[] blackGenomes;
    final int THREAD_COUNT = 10;
    int generations;
    CountDownLatch terminatedTasks;

    static int[] whiteScores;
    static int[] blackScores;

    public GeneticAlgForEval(int gen) {
        generations = gen;
    }

    public record genomeWinRate(EvaluationFunc genome, int numberOfWins) {

    }


    public EvaluationFunc[] train() {
        whiteGenomes = new EvaluationFunc[20];
        blackGenomes = new EvaluationFunc[20];
        for (int i = 0; i < whiteGenomes.length; i++) {
            whiteGenomes[i] = new EvaluationFunc();
            blackGenomes[i] = new EvaluationFunc();
        }
        ThreadPoolExecutor executor = (ThreadPoolExecutor) newFixedThreadPool(THREAD_COUNT);

        int i = 0;
        while (i < generations) {

            genomeWinRate[] whiteGenomeScore = new genomeWinRate[20];
            genomeWinRate[] blackGenomeScore = new genomeWinRate[20];

            System.out.println("Gen: " + i);
            for (int j = 0; j < whiteGenomes.length; j++) {
                whiteScores = new int[10];
                blackScores = new int[10];
                terminatedTasks = new CountDownLatch(10);
                //TODO make it play against all genomes
                for (int k = 0; k < THREAD_COUNT; k++) {
                    executor.execute(new Play(k, whiteGenomes[j], blackGenomes[(int) (Math.random() * blackGenomes.length)], blackGenomes[j], whiteGenomes[(int) (Math.random() * whiteGenomes.length)]));
                }

                try {
                    terminatedTasks.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException("failed to find all samples");
                }

//                executor.allowCoreThreadTimeOut(true);

                int whiteCount = 0;
                int blackCount = 0;
                for (int k = 0; k < whiteScores.length; k++) {
                    if (whiteScores[k] == 2) {
                        whiteCount++;
                    }
                    if (blackScores[k] == 1) {
                        blackCount++;
                    }
                }

//
                if (whiteCount > 5) {
                    System.out.println("Your sort shit");
                }
                whiteGenomeScore[j] = new genomeWinRate(whiteGenomes[j], whiteCount);
                blackGenomeScore[j] = new genomeWinRate(blackGenomes[j], blackCount);
            }

            for (int j = 0; j < whiteGenomeScore.length; j++) {
                for (int k = 0; k < whiteGenomeScore.length; k++) {
                    genomeWinRate swap;
                    if (whiteGenomeScore[j].numberOfWins > whiteGenomeScore[k].numberOfWins) {
                        swap = whiteGenomeScore[j];
                        whiteGenomeScore[j] = whiteGenomeScore[k];
                        whiteGenomeScore[k] = swap;
                    }
                    if (blackGenomeScore[j].numberOfWins > blackGenomeScore[k].numberOfWins) {
                        swap = blackGenomeScore[j];
                        blackGenomeScore[j] = blackGenomeScore[k];
                        blackGenomeScore[k] = swap;
                    }
                }
            }

            for (int j = 0; j < whiteGenomes.length; j++) {
                if (j < whiteGenomes.length / 2) {
                    whiteGenomes[j] = whiteGenomeScore[j].genome;
                    blackGenomes[j] = blackGenomeScore[j].genome;
                } else {
                    int index1 = (int) (Math.random() * whiteGenomes.length / 2);
                    int index2 = (int) (Math.random() * whiteGenomes.length / 2);
                    whiteGenomes[j] = generateNewEval(whiteGenomes[index1], whiteGenomes[index2]);
                    blackGenomes[j] = generateNewEval(blackGenomes[index1], blackGenomes[index2]);
                }
            }
            try {
                FileWriter myWriter = new FileWriter("Weight.txt");
                String outp = "Generation: " + i + "\n" + "WhiteWeights: \n" + whiteGenomes[0].writetofile() + "Score: " + whiteGenomeScore[0].numberOfWins + "\n BlackWeights: \n" + blackGenomes[0].writetofile() + "Score: " + blackGenomeScore[0].numberOfWins;
                System.out.println("Generation: " + i + "\n" + "WhiteWeights: \n" + whiteGenomes[0].writetofile() + "Score: " + whiteGenomeScore[0].numberOfWins + "\n BlackWeights: \n" + blackGenomes[0].writetofile() + "Score: " + blackGenomeScore[0].numberOfWins);
                myWriter.write(outp);
            } catch (IOException e) {
                System.out.println(e);
            }
            i++;


            ObjectToFile.writeObjectToFile(whiteGenomes[0],"WhiteGA"+i+".txt");
            ObjectToFile.writeObjectToFile(blackGenomes[0],"BlackGA"+i+".txt");
        }
        EvaluationFunc[]  outp = {whiteGenomes[0],blackGenomes[0]};

        return outp;
    }

    public EvaluationFunc generateNewEval(EvaluationFunc genome1, EvaluationFunc genome2) {
        double[] newWeights = crossover(genome1, genome2);
        mutatate(newWeights);
        return new EvaluationFunc(newWeights);
    }

    public double[] crossover(EvaluationFunc genome1, EvaluationFunc genome2) {
        double[] newWeights = new double[genome1.w.length];

        for (int i = 0; i < newWeights.length; i++) {
            double coinflip = Math.random();
            if (coinflip >= 0.5) {
                newWeights[i] = genome1.w[i];
            } else {
                newWeights[i] = genome2.w[i];
            }
        }
        return newWeights;
    }

    public void mutatate(double[] weights) {
        for (int i = 0; i < weights.length; i++) {
            double prob = Math.random();
            if (prob > 0.93) {
                weights[i] = Math.random() * 2 - 1;
            }
        }
    }

    // Returns true if first genome wins
    class Play implements Runnable {
        int index;
        EvaluationFunc whiteHeuristic;
        EvaluationFunc whiteOpponent;
        EvaluationFunc blackHeuristic;
        EvaluationFunc blackOpponent;


        public Play(int i, EvaluationFunc whiteGenome, EvaluationFunc whiteOpponent, EvaluationFunc blackGenome, EvaluationFunc blackOpponent) {
            index = i;
            this.whiteHeuristic = whiteGenome;
            this.blackHeuristic = blackGenome;
            this.whiteOpponent = whiteOpponent;
            this.blackOpponent = blackOpponent;

        }

        @Override
        public void run() {
            whiteScores[index] = play(whiteHeuristic, whiteOpponent);
            blackScores[index] = play(blackHeuristic, blackOpponent);
            terminatedTasks.countDown();
        }

        public int play(EvaluationFunc whiteHeuristic, EvaluationFunc blackHeuristic) {
            ExpectiMaxPlayer whitePlayer = new ExpectiMaxPlayer(3, Piece.colours.white, whiteHeuristic);
            ExpectiMaxPlayer blackPlayer = new ExpectiMaxPlayer(3, Piece.colours.black, blackHeuristic);
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
    }

    public boolean checkformoves(Board board, Piece.colours color) {

        for (int i = 0; i < board.board.length; i++) {
            for (int j = 0; j < board.board.length; j++) {
                if (board.board[i][j] != null && board.board[i][j].color == color && board.board[i][j].getPossibleMoves(board).size() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        GeneticAlgForEval test = new GeneticAlgForEval(1000);
        test.train();
    }
}
