package com.GameLogic.Experiments;

import com.GameLogic.Board.Pieces.Piece;
import com.GameLogic.EvalFunctions.EvaluationFunc;
import com.GameLogic.EvalFunctions.GeneticAlgForEval;
import com.GameLogic.Game;
import com.GameLogic.Players.ExpectiMaxPlayer;
import com.GameLogic.Players.Player;
import com.GameLogic.Players.randomAgent;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.StandardOpenOption;

public class Phase2Experiments {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            try {
                FileWriter myWriter = new FileWriter("numberOfmovesWhite.txt",true);
                myWriter.write("Gen: "+i*10);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            try {
                FileWriter myWriter = new FileWriter("numberOfmovesBlack.txt",true);
                myWriter.write("Gen: "+i*10);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            EvaluationFunc[]weights = new GeneticAlgForEval(i*1).train();

            int whiteExpectiWins = 0;
            int blackExpectiWins = 0;

            int whiteRandomWins = 0;
            int blackRandomWins = 0;

            int drawsWhiteExpecti = 0;
            int drawsBlackExpecti = 0;

            int numberOfMovesWhiteExpecti = 0;
            int numberOfMovesBlackExpecti = 0;

            for (int j = 0; j < 100; j++) {
                Player player1 = new ExpectiMaxPlayer(3,Piece.colours.white,weights[0]);
                Player player2 = new randomAgent(Piece.colours.black, Player.PlayerType.AGENT);

                int[] out = play(player1,player2);
                numberOfMovesWhiteExpecti += out[1];

                if(out[0] == 1){
                    blackRandomWins++;
                }else if(out[0] == 2){
                    whiteExpectiWins++;
                }else{
                    drawsWhiteExpecti++;
                }

                try {
                    FileWriter myWriter = new FileWriter("numberOfmovesWhite.txt",true);
                    myWriter.write(out[1]+"");
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                 player2 = new ExpectiMaxPlayer(3,Piece.colours.black,weights[1]);
                 player1 = new randomAgent(Piece.colours.white, Player.PlayerType.AGENT);

                 out = play(player1,player2);
                 numberOfMovesBlackExpecti += out[1];

                try {
                    FileWriter myWriter = new FileWriter("numberOfmovesBlack.txt",true);
                    myWriter.write(out[1]+"");
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                if(out[0] == 1){
                    blackExpectiWins++;
                }else if(out[0] == 2){
                    whiteRandomWins++;
                }else{
                    drawsBlackExpecti++;
                }
            }
            int avgWhiteNum = numberOfMovesWhiteExpecti/100;
            int avgBlackNum = numberOfMovesBlackExpecti/100;

            try {
                FileWriter myWriter = new FileWriter("experiments.txt",true);
                myWriter.write("White ExpectiMax wins: "+whiteExpectiWins+" Black Random Wins: "+blackRandomWins+ " White ExpectiMax draws: "+drawsWhiteExpecti+" Average number Of moves: "+avgWhiteNum+"\n");
                myWriter.write("Black ExpectiMax wins: "+blackExpectiWins+" White Random Wins: "+whiteRandomWins+ " Black ExpectiMax draws: "+drawsBlackExpecti+" Average number Of moves: "+avgBlackNum+"\n");
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        }

    }
    public static int[] play(Player whitePlayer, Player blackPlayer){
        int []outp;
        Game game = new Game(whitePlayer, blackPlayer);
        Piece.colours win = null;
        int num_move = 0;
        while (win == null && num_move < 100) {


            if(!whitePlayer.sendMove(game, game.movePiece)){
                outp = new int[]{0, num_move};
                return outp;
            }

            win = game.winCheck();
            if(win != null){
                break;
            }

            if(!blackPlayer.sendMove(game, game.movePiece)){
                outp = new int[]{0, num_move};
                return outp;
            }

            win = game.winCheck();
            if(win != null){
                break;
            }
            num_move++;
        }
        if (num_move == 100) {
            int num_white_pieces = EvaluationFunc.NumberOfWhitePieces(game.board);
            int num_black_pieces = EvaluationFunc.NumberOfWhitePieces(game.board);

            if (num_white_pieces == num_black_pieces) {
                outp = new int[]{0, num_move};
                return outp;
            }
            if (num_white_pieces > num_black_pieces) {
                outp = new int[]{2, num_move};
                return outp;
            } else if (num_black_pieces > num_white_pieces){
                outp = new int[]{1, num_move};
                return outp;
            }else {
                outp = new int[]{0, num_move};
                return outp;
            }
        }
        if (win == Piece.colours.white) {
            outp = new int[]{2, num_move};
            return outp;
        } else  if(win == Piece.colours.black) {
            outp = new int[]{1, num_move};
            return outp;
        }else{
            outp = new int[]{0, num_move};
            return outp;
        }

    }
}
