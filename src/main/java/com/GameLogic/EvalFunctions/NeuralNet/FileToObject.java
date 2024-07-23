package com.GameLogic.EvalFunctions.NeuralNet;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class FileToObject {

    public static Object readObjectFromFile(String fileName) {
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object obj = objectIn.readObject();
            objectIn.close();
            fileIn.close();
            return obj;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
