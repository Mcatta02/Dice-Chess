package com.GameLogic.EvalFunctions.NeuralNet;

import java.io.Serializable;
import java.util.Random;
import com.GameLogic.Players.Player;

public class NeuralNet implements Serializable {
    
    public double[] input;
    public double[] hidden; //One hidden for now adpat later :)
    public double[] output;
    public double[][] hiddenWeights;
    public double[][] outputWeights;
    public double[] hiddenBias;
    public double[] outputBias;
    public final int inputSize;
    public final int hiddenSize;
    public final int outputSize;
    public final Random random;
    public final double learningRate;

    // For now no biases but implement tmrw
    // Creates new neural network which needs to be trained
    // Make hidden layers more dynamic hehe

    public NeuralNet(int inputSize, int hiddenSize, int outputSize, double learningRate) {
        this.inputSize = inputSize;
        this.hiddenSize = hiddenSize;
        this.outputSize = outputSize;
        input = new double[inputSize];
        hidden = new double[hiddenSize];
        output = new double[outputSize];
        hiddenWeights = new double[inputSize][hiddenSize];
        outputWeights = new double[hiddenSize][outputSize];
        hiddenBias = new double[hiddenSize];
        outputBias = new double[outputSize];
        this.learningRate = learningRate;
        random = new Random();
        initWeights();
    }

    //Creates a neural network with predefined weights and biases
    public NeuralNet(int inputSize, int hiddenSize, int outputSize,double learningRate,double [][] hiddenWeights,double [][] outputWeights,double [] hiddenBias, double[] outputBias){
        this.inputSize = inputSize;
        this.hiddenSize = hiddenSize;
        this.outputSize = outputSize;
        input = new double[inputSize];
        hidden = new double[hiddenSize];
        output = new double[outputSize];
        this.hiddenWeights = hiddenWeights;
        this.outputWeights = outputWeights;
        this.hiddenBias = hiddenBias;
        this.outputBias = outputBias;
        this.learningRate = learningRate;
        random = new Random();
    }

    public void initWeights() {
        for (int i = 0; i < inputSize; i++) {
            for (int j = 0; j < hiddenSize; j++) {
                hiddenWeights[i][j] = random.nextGaussian();
            }
            
        }
        for (int i = 0; i < hiddenSize; i++) {
            for (int j = 0; j < outputSize; j++) {
                outputWeights[i][j] = random.nextGaussian();
            }
          
        }
    }

    public void sigmoid(double[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sigmoid(arr[i]);
        }
    }

    public double sigmoid(double x) {
        double y = 1 / (1 + Math.exp(-x));
        return y;
    }

    public void sigmoidGradient(double[] arr){
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sigmoid(arr[i])*(1-sigmoid(arr[i]));
        }
    }
    public double sigmoidGradient(double x){
        return sigmoid(x)*(1-sigmoid(x));
    }

    //TODO: update for biases
    public double[] predict(double input[]){
        this.input = input.clone();

        /*
         * So we take the input run it through all the hidden weights and then apply sigmoid function to it to recivece the values
         * We then take those hidden layer values and run it through the same shit to get the output layer 
         * Done :)
         */

        
        for (int j = 0; j < inputSize; j++) {
            double[] weighted = dotProduct(input[j], hiddenWeights[j]);
            sigmoid(weighted);
            for (int i = 0; i < weighted.length; i++) {
                hidden[i]+=weighted[i];
            }
        }

        for (int j = 0; j < hiddenSize; j++) {
            double[] weighted = dotProduct(hidden[j], outputWeights[j]);
            sigmoid(weighted);
            for (int i = 0; i < weighted.length; i++) {
                output[i]+=weighted[i];
            }
        }

        double[]outp = output.clone();


        output = new double[output.length];
        hidden = new double[hidden.length];


        return outp;
    }

    // Use polymorphism for other dot product scenarois
    public double[] dotProduct(double x,double[]y) {
        double[] z = new double[y.length];
        for (int i = 0; i < y.length; i++) {
            z[i] = x*y[i];
        }
        return z;
    }

    /*
     * So we need to back prop
     * we take a win loss draw ratio as our reward function
     * 
     */

     public void backPropagete(double[] input,double reward) {
        // Feed forward
        predict(input);
    
        // Compute the error for the output layer
        double[] outputError = new double[output.length];
        for (int i = 0; i < output.length; i++) {
            outputError[i] = reward - output[i];
        }

        // Compute the error for the hidden layer
        double[] hiddenError = new double[hidden.length];
        for (int i = 0; i < hidden.length; i++) {
            double sum = 0;
            for (int j = 0; j < output.length; j++) {
                sum += outputError[j] * hiddenWeights[i][j];
            }
            hiddenError[i] = sum * sigmoidGradient(hidden[i]);
        }

        // Update the weights between the hidden and output layers
        for (int i = 0; i < hidden.length; i++) {
            for (int j = 0; j < output.length; j++) {
                double delta = outputError[j] * hidden[i];
                hiddenWeights[i][j] += learningRate * delta;
            }
        }

        // Update the weights between the input and hidden layers
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < hidden.length; j++) {
                double delta = hiddenError[j] * input[i];
                hiddenWeights[i][j] += learningRate * delta;
            }
        }
    }




    /*
     * Play will return a 1 for player 1 winning 0 for draw and -1 for loss
     */



}