package com.rps;

import java.io.Serializable;
import java.util.Objects;

public class GameResult implements Serializable {

    private String name;
    private GameDifficulty mode;
    private int difficultyMultiplier;
    private int userScore;
    private int computerScore;
    private int rounds;

    public GameResult(final String name, final GameDifficulty mode, final int userScore, final int computerScore, final int rounds) {
        this.name = name;
        this.mode = mode;
        this.userScore = userScore;
        this.computerScore = computerScore;
        this.rounds = rounds;
    }

    public int getDifficultyMultiplier(GameDifficulty mode) {
        switch (mode) {
            case EASY:
                difficultyMultiplier = 100;
                break;
            case MEDIUM:
                difficultyMultiplier = 250;
                break;
            case HARD:
                difficultyMultiplier = 500;
        }
        return difficultyMultiplier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, userScore, computerScore, rounds);
    }

    @Override
    public String toString() {
        return "NAME: " + name + " | Mode: " + mode.name() + " | User score: " + userScore + " | Computer score: " +
                computerScore + " | Rounds: " + rounds + " | TOTAL SCORE: " + getTotalScore();
    }

    public int getTotalScore() {
        if (computerScore < userScore) {
            return (userScore - computerScore) * getDifficultyMultiplier(mode);
        } else {
            return 0;
        }
    }
}