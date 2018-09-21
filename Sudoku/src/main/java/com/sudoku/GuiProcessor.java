package com.sudoku;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sudoku.GameDifficulty.*;

public class GuiProcessor {

    private static GuiProcessor guiProcessor = null;
    private String name;
    private int difficulty;

    private List<SudokuScoreboard> scoreboardList = new ArrayList<>();
    private Map<Integer, GameDifficulty> gameDifficultyChoice = new HashMap<>();

    {
        gameDifficultyChoice.put(0, EASY);
        gameDifficultyChoice.put(1, MEDIUM);
        gameDifficultyChoice.put(2, HARD);
        gameDifficultyChoice.put(3, EXTREME);
    }

    private GuiBoard guiBoard = GuiBoard.getInstance();
    private GuiTimer guiTimer = GuiTimer.getInstance();
    private SudokuBoard sudokuBoard = SudokuBoard.getInstance();
    private SudokuProcessor sudokuProcessor = SudokuProcessor.getInstance();

    private GuiProcessor() {
    }

    public static GuiProcessor getInstance() {
        if (guiProcessor == null) {
            synchronized (GuiBoard.class) {
                if (guiProcessor == null) {
                    guiProcessor = new GuiProcessor();
                }
            }
        }
        return guiProcessor;
    }

    public void finish() {
        if (!sudokuBoard.findEmpty() && sudokuProcessor.checkSudoku(sudokuBoard)) {
            if (guiBoard.checkBoardPanel()) {
                SudokuScoreboard sudokuScoreboard = new SudokuScoreboard(name, guiTimer.getTime(), gameDifficultyChoice.get(difficulty));
                scoreboardList.add(sudokuScoreboard);
                ReadWriteData.serializeScoreboard(scoreboardList);
            }
        }
    }

    public void generateSudoku() {
        setUserChoices();
        if (name != null) {
            guiBoard.deleteBoardPanel();
            sudokuBoard.generateBoard();
            sudokuProcessor.solveSudoku(sudokuBoard);
            sudokuBoard.makeSudoku(gameDifficultyChoice.get(difficulty));
            sudokuBoard.setClonedBoard(sudokuBoard.deepCopy(sudokuBoard.getBoard()));
            guiBoard.fillBoardPanel();
            if (sudokuBoard.findEmpty()) {
                if (guiTimer.getTimer() != null) {
                    guiTimer.getTimer().stop();
                }
                guiTimer.timeCounter();
                guiTimer.timerStart();
            }
            guiBoard.setGenerated(true);
        }
    }

    public void setUserChoices() {
        String[] choices = {"Easy", "Medium", "Hard", "Extreme"};
        difficulty = JOptionPane.showOptionDialog(null,
                "Select difficulty:",
                "Difficulty",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                choices,
                choices[0]);

        name = JOptionPane.showInputDialog("Enter Your name:");
        if (name != null) {
            if (name.equals("")) {
                name = "Guest";
            }
        }
    }

    public void showScoreboard() {
        scoreboardList = ReadWriteData.deserializeScoreboard();
        if (scoreboardList.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "There are no results yet.",
                    "Scoreboard empty",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JTable scoreboardTable = new JTable();
            ScoreboardTableModel model = new ScoreboardTableModel(scoreboardList);
            scoreboardTable.setModel(model);
            scoreboardTable.setAutoCreateRowSorter(true);
            scoreboardTable.getRowSorter().toggleSortOrder(3);
            scoreboardTable.getRowSorter().toggleSortOrder(3);
            JOptionPane.showMessageDialog(null, new JScrollPane(scoreboardTable));
        }
    }
}