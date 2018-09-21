package com.rps;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadWriteData {

    public static final String DATA_FILE = "RPSScoreboard.ser";

    public static void serializeScoreboard(List<GameResult> scoreboard) {
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(DATA_FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(scoreboard);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<GameResult> deserializeScoreboard() {
        List<GameResult> scoreboard = new ArrayList<>();
        if (new File(DATA_FILE).exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(DATA_FILE);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                scoreboard = (List<GameResult>) in.readObject();
                in.close();
                fileIn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return scoreboard;
    }
}