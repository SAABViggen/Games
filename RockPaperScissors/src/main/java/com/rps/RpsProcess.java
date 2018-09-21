package com.rps;

public class RpsProcess {

    public GameChoice getComputerChoice(GameDifficulty mode, GameChoice choice) {

        double random = Math.random();
        double a = 0;
        double b = 0;
        GameChoice result;
        GameChoice first = GameChoice.PAPER;
        GameChoice second = GameChoice.ROCK;
        GameChoice third = GameChoice.SCISSORS;

        switch (mode) {
            case EASY:
                a = 0.15;
                b = 0.40;
                break;
            case MEDIUM:
                a = 0.33;
                b = 0.66;
                break;
            case HARD:
                a = 0.40;
                b = 0.75;
                break;
        }

        switch (choice) {
            case ROCK:
                break;
            case PAPER:
                first = GameChoice.SCISSORS;
                second = GameChoice.PAPER;
                third = GameChoice.ROCK;
                break;
            case SCISSORS:
                first = GameChoice.ROCK;
                second = GameChoice.SCISSORS;
                third = GameChoice.PAPER;
                break;
        }

        if (random >= 0 && random < a) {
            result = first;
        } else if (random >= a && random < b) {
            result = second;
        } else {
            result = third;
        }
        return result;
    }
}