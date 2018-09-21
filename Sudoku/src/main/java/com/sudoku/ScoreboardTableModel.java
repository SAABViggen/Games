package com.sudoku;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ScoreboardTableModel extends AbstractTableModel {

    private static final String[] columnNames = {"Name", "Difficulty", "Time", "Score"};
    private List<SudokuScoreboard> scoreboardList;

    public ScoreboardTableModel(List<SudokuScoreboard> scoreboardList) {
        this.scoreboardList = scoreboardList;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return scoreboardList.size();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
        switch (column) {
            case 0:
                return scoreboardList.get(row).getName();
            case 1:
                return scoreboardList.get(row).getDifficulty();
            case 2:
                return scoreboardList.get(row).getTime();
            case 3:
                return scoreboardList.get(row).getScore();
        }
        return null;
    }
}