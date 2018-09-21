package com.sudoku;

import java.io.Serializable;

public class SudokuScoreboard implements Serializable {

    private String name;
    private int time;
    private GameDifficulty difficulty;
    private int difficultyScore;

    public SudokuScoreboard(String name, int time, GameDifficulty difficulty) {
        this.name = name;
        this.time = time;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        int seconds = time % 60;
        int minutes = (time / 60) % 60;
        int hours = (time / 3600) % 24;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public String getDifficulty() {
        String difficultyName = "";
        switch (difficulty) {
            case EASY:
                difficultyName = "Easy";
                difficultyScore = 1;
                break;
            case MEDIUM:
                difficultyName = "Medium";
                difficultyScore = 2;
                break;
            case HARD:
                difficultyName = "Hard";
                difficultyScore = 3;
                break;
            case EXTREME:
                difficultyName = "Extreme";
                difficultyScore = 4;
        }
        return difficultyName;
    }

    public int getScore() {
        int score;
        if (time <= 3600) {
            score = (3600 - time) * (difficultyScore);
        } else {
            score = 0;
        }
        return score;
    }
}