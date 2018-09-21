package com.rps;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Scoreboard {

    private List<GameResult> scoreboard = new ArrayList<>();

    public List<GameResult> getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(List<GameResult> scoreboard) {
        this.scoreboard = scoreboard;
    }

    public void fillScoreboard(GameResult gameResult) {
        scoreboard.add(gameResult);
    }

    public void showScoreboard() {
        if (!scoreboard.isEmpty()) {
            scoreboard.sort(Comparator.comparing(GameResult::getTotalScore).reversed());
            scoreboard.forEach(System.out::println);
        } else {
            System.out.println("Scoreboard is empty. ");
        }
    }
}