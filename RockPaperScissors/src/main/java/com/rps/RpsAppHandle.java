package com.rps;

import java.util.HashMap;
import java.util.Map;

import static com.rps.GameChoice.*;
import static com.rps.GameDifficulty.*;

public class RpsAppHandle {

    private enum GameMatchResult {
        WIN, DRAW, LOOSE
    }

    private final static String EXIT = "x";
    private final static String SCOREBOARD = "s";
    private final static String NEW_GAME = "g";
    private final static String YES = "y";
    private Scoreboard scoreboard = new Scoreboard();
    private Scan scan = new Scan();
    private Map<GameChoice, GameChoice> winByChoice = new HashMap<>();

    {
        winByChoice.put(SCISSORS, PAPER);
        winByChoice.put(PAPER, ROCK);
        winByChoice.put(ROCK, SCISSORS);
    }

    private Map<String, GameDifficulty> gameDifficultyChoice = new HashMap<>();

    {
        gameDifficultyChoice.put("1", EASY);
        gameDifficultyChoice.put("2", MEDIUM);
        gameDifficultyChoice.put("3", HARD);
    }

    private Map<String, GameChoice> userChoice = new HashMap<>();

    {
        userChoice.put("1", ROCK);
        userChoice.put("2", PAPER);
        userChoice.put("3", SCISSORS);
        userChoice.put("x", QUIT);
    }

    public void startApp() {
        boolean run = true;
        do {

            System.out.println("Press \"g\" start a new game. \n" +
                    "Press \"s\" to show scoreboard. \n" +
                    "Press \"x\" to exit. ");
            String choiceMain = scan.scan(Scan.KEYS_MENU);
            switch (choiceMain) {
                case NEW_GAME:
                    newGame();
                    break;
                case SCOREBOARD:
                    scoreboard.setScoreboard(ReadWriteData.deserializeScoreboard());
                    scoreboard.showScoreboard();
                    System.out.print("\n");
                    break;
                case EXIT:
                    run = !confirmExitAttempt();
                    break;
                default:
                    System.out.println("Please choose something from main menu. \n");
            }
        } while (run);
    }

    private void newGame() {
        boolean runGame = true;
        int rounds = 0;
        int userScore = 0;
        int computerScore = 0;
        RpsProcess rpsProcess = new RpsProcess();

        String userName = getUserName();
        GameDifficulty mode = getGameDifficulty();

        while (runGame) {
            GameChoice choiceGame = getUserChoice();
            if (choiceGame.equals(QUIT)) {
                runGame = !confirmExitAttempt();
                scoreboard.fillScoreboard(new GameResult(userName, mode, userScore, computerScore, rounds));
                ReadWriteData.serializeScoreboard(scoreboard.getScoreboard());
            } else {
                GameChoice computerChoice = rpsProcess.getComputerChoice(mode, choiceGame);
                GameMatchResult gameResult = processGameMatchResult(choiceGame, computerChoice);
                System.out.println("Your choice: " + choiceGame.name() + "\n" +
                        "Computer choice: " + computerChoice.name());
                switch (gameResult) {
                    case WIN:
                        userScore++;
                        System.out.println("You win! ");
                        break;
                    case LOOSE:
                        computerScore++;
                        System.out.println("You loose! ");
                        break;
                    case DRAW:
                        System.out.println("Draw. ");
                        break;
                }
                rounds++;
                System.out.println("Round: " + rounds +
                        " | Score: " + userName + ": " + userScore + ", computer: " + computerScore + "\n");
            }
        }
    }

    private boolean confirmExitAttempt() {
        System.out.println("Are You sure You want to quit? y / n. ");
        String choice = scan.scan(Scan.KEYS_EXIT);
        return YES.equals(choice);
    }

    private GameMatchResult processGameMatchResult(GameChoice userChoice, GameChoice computerChoice) {
        if (computerChoice.equals(userChoice)) {
            return GameMatchResult.DRAW;
        } else if (winByChoice.get(userChoice).equals(computerChoice)) {
            return GameMatchResult.WIN;
        } else {
            return GameMatchResult.LOOSE;
        }
    }

    private String getUserName() {
        System.out.println("Please enter Your name: ");
        return scan.scanName();
    }

    private GameDifficulty getGameDifficulty() {
        System.out.println("Please choose game difficulty: \n" +
                "Press \"1\" to choose easy. \n" +
                "Press \"2\" to choose normal. \n" +
                "Press \"3\" to choose hard. ");
        String choice = scan.scan(Scan.KEYS_GAME_MODE);
        return gameDifficultyChoice.get(choice);
    }

    private GameChoice getUserChoice() {
        System.out.println("Press \"1\" to play rock. \n" +
                "Press \"2\" to play paper. \n" +
                "Press \"3\" to play scissors \n" +
                "Press \"x\" to exit to main menu. ");
        String choice = scan.scan(Scan.KEYS_GAME);
        return userChoice.get(choice);
    }
}