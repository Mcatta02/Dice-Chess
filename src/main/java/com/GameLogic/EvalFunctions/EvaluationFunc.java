package com.GameLogic.EvalFunctions;

import com.GameLogic.Board.Board;
import com.GameLogic.Board.Pieces.*;
import com.GameLogic.EvalFunctions.NeuralNet.FileToObject;
import com.GameLogic.EvalFunctions.NeuralNet.ObjectToFile;
import com.GameLogic.Vector2;

import java.io.Serializable;
import java.util.ArrayList;

public class EvaluationFunc implements Heuristic, Serializable{
    /*font

    We need to take in the board and then take values from the board

    Total number of pieces[X]
    Number of white pieces[X]
    Number of black pieces[X]
    Piece value for white[X]
    Piece value for black[X]

    TODO:
    Number of pieces under attack for white/black
    Pawn structure

     */
    public double[] w = new double[11];

    public EvaluationFunc() {
        for (int i = 0; i < w.length; i++) {
            w[i] = Math.random() * 2 - 1;
        }
    }

    public EvaluationFunc(double[] w) {
        System.arraycopy(w, 0, this.w, 0, w.length);
    }


    public double evaluate(Board board) {
        double[] v = evaluation(board);


        return w[0] * v[0] + w[1] * v[1] + w[2] * v[2] + w[3] * v[3] + w[4] * v[4] + w[5] * v[5] + w[6] * v[6] + w[7] * v[7] + w[8] * v[8] + w[9] * v[9] + w[10] * v[10];
    }

    public static double[] evaluation(Board board) {

        double numberOfWhitePieces = 0.0;
        double numberOfBlackPieces = 0.0;
        double valueOfWhitePieces = 0.0;
        double valueOfBlackPieces = 0.0;
        double totalNumberOfPieces = 0.0;
        double whiteQueenMobility = 0.0;
        double blackQueenMobility = 0.0;
        double numOfAttackedPiecesWhite = 0.0;
        double numOfAttackedPiecesBlack = 0.0;
        double valueOfAttackedPiecesWhite = 0.0;
        double valueOfAttackedPiecesBlack = 0.0;
        double numberOfWhiteConnectedRooks = 0.0;
        double numberOfBlackConnectedRooks = 0.0;
        double numberOfWhiteStackedPawns = 0.0;
        double numberOfBlackStackedPawns = 0.0;


        ArrayList<Piece> WhitePieces = new ArrayList<>();
        ArrayList<Piece> BlackPieces = new ArrayList<>();

        ArrayList<int[]> whiteRooks = new ArrayList<>();
        ArrayList<int[]> blackRooks = new ArrayList<>();

        ArrayList<int[]> whitePawns = new ArrayList<>();
        ArrayList<int[]> blackPawns = new ArrayList<>();

        for (int i = 0; i < board.board.length; i++) {
            for (int j = 0; j < board.board.length; j++) {
                if (board.board[i][j] != null) {
                    if (board.board[i][j].color == Piece.colours.white) {
                        ArrayList<Vector2> moves = board.board[i][j].getPossibleMoves(board);
                        for (Vector2 move : moves) {
                            if (board.board[move.x][move.y] != null && board.board[move.x][move.y].color == Piece.colours.black) {
                                numOfAttackedPiecesWhite++;
                                valueOfAttackedPiecesWhite += board.board[move.x][move.y].getValue();
                            }
                        }
                        numberOfWhitePieces++;
                        valueOfWhitePieces += board.board[i][j].value;
                        if (board.board[i][j] instanceof Queen) {
                            whiteQueenMobility = board.board[i][j].getPossibleMoves(board).size() * 1.0;
                        }
                    } else {
                        ArrayList<Vector2> moves = board.board[i][j].getPossibleMoves(board);
                        for (Vector2 move : moves) {
                            if (board.board[move.x][move.y] != null && board.board[move.x][move.y].color == Piece.colours.white) {
                                numOfAttackedPiecesBlack++;
                                valueOfAttackedPiecesBlack += board.board[move.x][move.y].getValue();
                            }
                        }
                        numberOfBlackPieces++;
                        valueOfBlackPieces += board.board[i][j].value;
                        if (board.board[i][j] instanceof Queen) {
                            blackQueenMobility = board.board[i][j].getPossibleMoves(board).size() * 1.0;
                        }
                    }

                }
            }
        }

        totalNumberOfPieces = numberOfBlackPieces + numberOfWhitePieces;


        double[] v = {numberOfWhitePieces,
                numberOfBlackPieces,
                valueOfWhitePieces,
                valueOfBlackPieces,
                totalNumberOfPieces,
                whiteQueenMobility,
                blackQueenMobility,
                numOfAttackedPiecesWhite,
                numOfAttackedPiecesBlack,
                valueOfAttackedPiecesWhite,
                valueOfAttackedPiecesBlack
        };
        return v;
    }

    public static int NumberOfWhitePieces(Board board) {
        int count = 0;
        for (int i = 0; i < board.board.length; i++) {
            for (int j = 0; j < board.board.length; j++) {
                if (board.board[i][j] != null && board.board[i][j].color == Piece.colours.white) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int NumberOfBlackPieces(Board board) {
        int count = 0;
        for (int i = 0; i < board.board.length; i++) {
            for (int j = 0; j < board.board.length; j++) {
                if (board.board[i][j] != null && board.board[i][j].color == Piece.colours.black) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int ValueOfWhitePieces(Board board) {
        int count = 0;
        for (int i = 0; i < board.board.length; i++) {
            for (int j = 0; j < board.board.length; j++) {
                if (board.board[i][j] != null && board.board[i][j].color == Piece.colours.white) {
                    count += board.board[i][j].value;
                }
            }
        }
        return count;
    }

    public static int ValueOfBlackPieces(Board board) {
        int count = 0;
        for (int i = 0; i < board.board.length; i++) {
            for (int j = 0; j < board.board.length; j++) {
                if (board.board[i][j] != null && board.board[i][j].color == Piece.colours.black) {
                    count += board.board[i][j].value;
                }
            }
        }
        return count;
    }

    public static int TotalNumberOfPieces(Board board) {
        int count = 0;
        for (int i = 0; i < board.board.length; i++) {
            for (int j = 0; j < board.board.length; j++) {
                if (board.board[i][j] != null && board.board[i][j] != null) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int WhiteQueenMobility(Board board) {
        int count = 0;
        for (int i = 0; i < board.board.length; i++) {
            for (int j = 0; j < board.board.length; j++) {
                if (board.board[i][j] != null && board.board[i][j] instanceof Queen && board.board[i][j].color == Piece.colours.white) {
                    count = board.board[i][j].getPossibleMoves(board).size();
                }
            }
        }
        return count;
    }

    public static int BlackQueenMobility(Board board) {
        int count = 0;
        for (int i = 0; i < board.board.length; i++) {
            for (int j = 0; j < board.board.length; j++) {
                if (board.board[i][j] != null && board.board[i][j] instanceof Queen && board.board[i][j].color == Piece.colours.black) {
                    count = board.board[i][j].getPossibleMoves(board).size();
                }
            }
        }
        return count;
    }

    public static ArrayList<Piece> WhitePieces(Board board) {
        ArrayList<Piece> Pieces = new ArrayList<>();
        for (int i = 0; i < board.board.length; i++) {
            for (int j = 0; j < board.board.length; j++) {
                if (board.board[i][j] != null && board.board[i][j].color == Piece.colours.white) {
                    Pieces.add(board.board[i][j]);
                }
            }
        }
        return Pieces;
    }

    public static ArrayList<Piece> BlackPieces(Board board) {
        ArrayList<Piece> Pieces = new ArrayList<>();
        for (int i = 0; i < board.board.length; i++) {
            for (int j = 0; j < board.board.length; j++) {
                if (board.board[i][j] != null && board.board[i][j].color == Piece.colours.black) {
                    Pieces.add(board.board[i][j]);
                }
            }
        }
        return Pieces;
    }

    public static int NumberOfAttackedPiecesByWhite(ArrayList<Piece> BlackPieces, ArrayList<Piece> WhitePieces, Board board) {
        int count = 0;

        for (Piece BlackPiece : BlackPieces) {
            for (Piece WhitePiece : WhitePieces) {
                ArrayList<Vector2> PossibleMoves = WhitePiece.getPossibleMoves(board);
                for (int i = 0; i < PossibleMoves.size(); i++) {
                    if (PossibleMoves.get(i).x == BlackPiece.getPos().x && PossibleMoves.get(i).y == BlackPiece.getPos().y) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static int NumberOfAttackedPiecesByBlack(ArrayList<Piece> BlackPieces, ArrayList<Piece> WhitePieces, Board board) {
        int count = 0;
        for (Piece BlackPiece : BlackPieces) {
            for (Piece WhitePiece : WhitePieces) {
                ArrayList<Vector2> PossibleMoves = BlackPiece.getPossibleMoves(board);
                for (int i = 0; i < PossibleMoves.size(); i++) {
                    if (PossibleMoves.get(i).x == WhitePiece.getPos().x && PossibleMoves.get(i).y == WhitePiece.getPos().y) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static int ValueOfAttackedPiecesByWhite(Board board) {
        int TotalValue = 0;
        ArrayList<Piece> BlackPieces = BlackPieces(board);
        ArrayList<Piece> WhitePieces = WhitePieces(board);
        for (Piece BlackPiece : BlackPieces) {
            for (Piece WhitePiece : WhitePieces) {
                ArrayList<Vector2> PossibleMoves = WhitePiece.getPossibleMoves(board);
                for (int i = 0; i < PossibleMoves.size(); i++) {
                    if (PossibleMoves.get(i).x == BlackPiece.getPos().x && PossibleMoves.get(i).y == BlackPiece.getPos().y) {
                        TotalValue += BlackPiece.getValue();
                    }
                }
            }
        }
        return TotalValue;
    }

    public static int ValueOfAttackedPiecesByBlack(Board board) {
        int TotalValue = 0;
        ArrayList<Piece> BlackPieces = BlackPieces(board);
        ArrayList<Piece> WhitePieces = WhitePieces(board);
        for (Piece BlackPiece : BlackPieces) {
            for (Piece WhitePiece : WhitePieces) {
                ArrayList<Vector2> PossibleMoves = BlackPiece.getPossibleMoves(board);
                for (int i = 0; i < PossibleMoves.size(); i++) {
                    if (PossibleMoves.get(i).x == WhitePiece.getPos().x && PossibleMoves.get(i).y == WhitePiece.getPos().y) {
                        TotalValue += WhitePiece.getValue();
                    }
                }
            }
        }
        return TotalValue;
    }

    public static int NumberOfWhiteConnectedRooks(ArrayList<int[]> rooks, Board board) {
        int count = 0;
        if (rooks.size() == 1 || rooks.size() == 0) return 0;
        int tempSum = 0;
        for (int i = 0; i < rooks.size(); i++) {
            for (int j = 1; j < rooks.size() - 1; j++) {
                if (rooks.get(i)[0] == rooks.get(j)[0]) {
                    for (int r = rooks.get(i)[1] + 1; r < rooks.get(j)[1]; r++) {
                        if (board.board[rooks.get(i)[0]][i] == null) tempSum++;

                    }
                }
                if (rooks.get(i)[1] == rooks.get(j)[1]) {
                    for (int r = rooks.get(i)[0] + 1; r < rooks.get(j)[0]; r++) {
                        if (board.board[i][rooks.get(i)[1]] == null) tempSum++;

                    }
                }
                if (tempSum == ((rooks.get(j)[1] - rooks.get(i)[1]) - 1)) {
                    //tempSum = 0;
                    count++;
                }
            }
            rooks.remove(i);
        }
        return count;
    }

    public static int NumberOfBlackConnectedRooks(ArrayList<int[]> rooks, Board board) {
        int count = 0;

        if (rooks.size() == 1 || rooks.size() == 0) return 0;
        int tempSum = 0;
        for (int i = 0; i < rooks.size(); i++) {
            for (int j = 1; j < rooks.size() - 1; j++) {
                if (rooks.get(i)[0] == rooks.get(j)[0]) {
                    for (int r = rooks.get(i)[1] + 1; r < rooks.get(j)[1]; r++) {
                        if (board.board[rooks.get(i)[0]][i] == null) tempSum++;

                    }
                }
                if (rooks.get(i)[1] == rooks.get(j)[1]) {
                    for (int r = rooks.get(i)[0] + 1; r < rooks.get(j)[0]; r++) {
                        if (board.board[i][rooks.get(i)[1]] == null) tempSum++;

                    }
                }
                if (tempSum == ((rooks.get(j)[1] - rooks.get(i)[1]) - 1)) {
                    //tempSum = 0;
                    count++;
                }
            }
            rooks.remove(i);
        }
        return count;
    }

    // Method that counts the amount of stacked Pawns the given player has
    // Stacked Pawns are counted and returned as an int. Stacked Pawns = two Pawns above each other.
    public static int NumberOfWhiteStackedPawns(ArrayList<int[]> pawns, Board board) {


        int count = 0;
        for (int x = 0; x < pawns.size(); x++) {
            for (int y = 0; y < pawns.size(); y++) {
                if (pawns.get(x)[0] == pawns.get(y)[0] + 1 && pawns.get(x)[1] == pawns.get(y)[1]) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int NumberOfBlackStackedPawns(ArrayList<int[]> pawns, Board board) {
        int count = 0;
        for (int x = 0; x < pawns.size(); x++) {
            for (int y = 0; y < pawns.size(); y++) {
                if (pawns.get(x)[0] == pawns.get(y)[0] + 1 && pawns.get(x)[1] == pawns.get(y)[1]) {
                    count++;
                }
            }
        }
        return count;
    }

    // Counts the number of passed Pawns the given player has.
    // A passed Pawn is a pawn that is on the opponents side of the board.
    // The method returns an int which is the number of past pawns the player has.


    // NOT USING THESE FOR NOW
    public static int NumberOfWhitePassedPawns(Board board) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.board[i][j] != null && board.board[i][j] instanceof Pawn && board.board[i][j].color == Piece.colours.white)
                    count++;
            }
        }
        return count;
    }

    public static int NumberOfBlackPassedPawns(Board board) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.board[i][j] != null && board.board[i][j] instanceof Pawn && board.board[i][j].color == Piece.colours.black)
                    count++;
            }
        }
        return count;
    }

    public static int WhiteRookOnKingFile(Board board) {
        int[] kingPos = new int[2];
        ArrayList<int[]> rooks = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.board[i][j] != null && board.board[i][j] instanceof Rook && board.board[i][j].color == Piece.colours.white) {
                    rooks.add(new int[]{i, j});
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.board[i][j] != null && board.board[i][j] instanceof King && board.board[i][j].color == Piece.colours.black) {
                    kingPos[0] = i;
                    kingPos[1] = j;
                }
            }
        }
        for (int i = 0; i < rooks.size(); i++) {
            if (rooks.get(i)[0] == kingPos[0]) {
                return 1;
            }
            if (rooks.get(i)[1] == kingPos[1]) {
                return 1;
            }
        }
        return 0;
    }

    public static int BlackRookOnKingFile(Board board) {
        int[] kingPos = new int[2];
        ArrayList<int[]> rooks = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.board[i][j] != null && board.board[i][j] instanceof Rook && board.board[i][j].color == Piece.colours.black) {
                    rooks.add(new int[]{i, j});
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.board[i][j] != null && board.board[i][j] instanceof King && board.board[i][j].color == Piece.colours.white) {
                    kingPos[0] = i;
                    kingPos[1] = j;
                }
            }
        }
        for (int i = 0; i < rooks.size(); i++) {
            if (rooks.get(i)[0] == kingPos[0]) {
                return 1;
            }
            if (rooks.get(i)[1] == kingPos[1]) {
                return 1;
            }
        }
        return 0;
    }

    public String writetofile() {
        String outp = "";
        for (int i = 0; i < w.length; i++) {
            outp += "" + i + " " + w[i] + "\n";
        }
        return outp;
    }
}