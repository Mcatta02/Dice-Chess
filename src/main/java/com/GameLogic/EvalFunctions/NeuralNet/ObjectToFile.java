package com.GameLogic.EvalFunctions.NeuralNet;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectToFile {

    public static void writeObjectToFile(Object obj, String fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(obj);
            objectOut.close();
            fileOut.close();
            System.out.println("The Object  was succesfully written to a file");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}