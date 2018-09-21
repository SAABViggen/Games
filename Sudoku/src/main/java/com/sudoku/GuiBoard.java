package com.sudoku;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sudoku.GameColors.DEFAULT_BACKGROUND;
import static com.sudoku.GameColors.GAME_INPUT_COLOR;
import static com.sudoku.GameConfig.*;

public class GuiBoard {

    private static GuiBoard guiBoard = null;
    private boolean generated = false;
    private Map<Integer, JTextField> sudokuBoardFields = new HashMap<>();
    private Map<JTextField, Integer> sudokuBoardFieldsNumbers = new HashMap<>();
    private SudokuBoard sudokuBoard = SudokuBoard.getInstance();
    private SudokuProcessor sudokuProcessor = SudokuProcessor.getInstance();
    private GuiTimer guiTimer = GuiTimer.getInstance();
    private ImageIcon iconWin = new ImageIcon(getClass().getResource("images/Win.png"));
    private ImageIcon iconSolution = new ImageIcon(getClass().getResource("images/Solution.png"));
    private ImageIcon iconNoSolution = new ImageIcon(getClass().getResource("images/NoSolution.png"));

    private GuiBoard() {
    }

    public static GuiBoard getInstance() {
        if (guiBoard == null) {
            synchronized (GuiBoard.class) {
                if (guiBoard == null) {
                    guiBoard = new GuiBoard();
                }
            }
        }
        return guiBoard;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }

    public Map<Integer, JTextField> getSudokuBoardFields() {
        return sudokuBoardFields;
    }

    public Map<JTextField, Integer> getSudokuBoardFieldsNumbers() {
        return sudokuBoardFieldsNumbers;
    }

    public JPanel boardPanel() {
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        int field = 1;
        for (int row = 0; row < BOX_SIZE; ++row) {
            for (int col = 0; col < BOX_SIZE; ++col) {
                JPanel box = new JPanel(new GridLayout(3, 3));
                box.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                for (int cell = 0; cell < GAME_SIZE; ++cell) {
                    JTextField textField = new JTextField();
                    box.add(textField);
                    sudokuBoardFields.put(field, textField);
                    sudokuBoardFieldsNumbers.put(textField, field);
                    field++;
                }
                boardPanel.add(box);
            }
        }
        return boardPanel;
    }

    public void fillBoardPanel() {
        for (int row = 0; row < GAME_SIZE; row++) {
            for (int col = 0; col < GAME_SIZE; col++) {
                int number = sudokuBoard.getNumber(row, col);
                if (number != EMPTY) {
                    String text = Integer.toString(number);
                    int boxMultiplier = (row / BOX_SIZE) + (row / BOX_SIZE) * 2 + col / BOX_SIZE;
                    int boxAdder = (row % BOX_SIZE * BOX_SIZE) + col % BOX_SIZE;
                    int result = (boxMultiplier * GAME_SIZE + boxAdder) + 1;
                    sudokuBoardFields.get(result).setText(text);
                    sudokuBoardFields.get(result).setForeground(GAME_INPUT_COLOR);
                    sudokuBoardFields.get(result).setBackground(DEFAULT_BACKGROUND);
                }
            }
        }
    }

    public void readBoardPanel() {
        List<JTextField> fields = new ArrayList<>();
        List<SudokuRow> rows = new ArrayList<>();
        fillList(fields);
        int add = 0;
        for (int row = 0; row < GAME_SIZE; row++) {
            SudokuRow sudokuRow = new SudokuRow();
            List<Integer> nums = new ArrayList<>();
            for (int num = add; num < GAME_SIZE + add; num++) {
                if (fields.get(num).getText().equals("")) {
                    nums.add(EMPTY);
                } else {
                    nums.add(Integer.parseInt(fields.get(num).getText()));
                }
            }
            sudokuRow.setRow(nums);
            rows.add(sudokuRow);
            add += GAME_SIZE;
        }
        sudokuBoard.clearBoard();
        sudokuBoard.setBoard(rows);
        if (!generated) {
            sudokuBoard.setClonedBoard(sudokuBoard.deepCopy(sudokuBoard.getBoard()));
        }
        sudokuBoard.setActualBoard(sudokuBoard.deepCopy(sudokuBoard.getBoard()));
    }

    public void fillList(List<JTextField> fields) {
        int begin = 1;
        for (int box = 0; box < BOX_SIZE; box++) {
            int addRow = 0;
            for (int row = 0; row < BOX_SIZE; row++) {
                int add = 0;
                for (int boxRow = 0; boxRow < BOX_SIZE; boxRow++) {
                    fields.add(sudokuBoardFields.get(begin + add + addRow));
                    fields.add(sudokuBoardFields.get(begin + 1 + add + addRow));
                    fields.add(sudokuBoardFields.get(begin + 2 + add + addRow));
                    add += GAME_SIZE;
                }
                addRow += BOX_SIZE;
            }
            begin += GAME_SIZE * BOX_SIZE;
        }
    }

    public boolean checkBoardPanel() {
        boolean check = false;
        if (sudokuBoardFields.size() == FIELDS_SIZE) {
            readBoardPanel();
            if (sudokuBoard.findEmpty() && sudokuProcessor.solveSudoku(sudokuBoard)) {
                JOptionPane.showMessageDialog(null,
                        "Solution available.",
                        "Check ok",
                        JOptionPane.PLAIN_MESSAGE,
                        iconSolution);
                sudokuBoard.setBoard(sudokuBoard.deepCopy(sudokuBoard.getActualBoard()));
            } else if (sudokuProcessor.checkSudoku(sudokuBoard)) {
                if (guiTimer.getTimer() != null) {
                    guiTimer.getTimer().stop();
                }
                JOptionPane.showMessageDialog(null,
                        "Sudoku solved!",
                        "Good job!",
                        JOptionPane.PLAIN_MESSAGE,
                        iconWin);
                check = true;
            } else if (!sudokuProcessor.checkSudoku(sudokuBoard)) {
                JOptionPane.showMessageDialog(null,
                        "No solution, please try again.",
                        "Failed",
                        JOptionPane.PLAIN_MESSAGE,
                        iconNoSolution);
            }
        }
        return check;
    }

    public void solveBoardPanel() {
        readBoardPanel();
        if (!sudokuProcessor.solveSudoku(sudokuBoard)) {
            JOptionPane.showMessageDialog(null,
                    "No solution, please try again.",
                    "Failed",
                    JOptionPane.PLAIN_MESSAGE,
                    iconNoSolution);
        }
        sudokuProcessor.solveSudoku(sudokuBoard);
        fillBoardPanel();
    }

    public void resetBoardPanel() {
        if (sudokuBoard.getBoard().size() != EMPTY) {
            clearBoardPanel();
            sudokuBoard.setBoard(sudokuBoard.deepCopy(sudokuBoard.getClonedBoard()));
            fillBoardPanel();
        }
    }

    public void clearBoardPanel() {
        if (sudokuBoardFields.size() != EMPTY) {
            sudokuBoardFields.entrySet().stream()
                    .map(Map.Entry::getValue)
                    .forEach(e -> e.setText(""));
        }
    }

    public void deleteBoardPanel() {
        sudokuBoard.clearBoard();
        clearBoardPanel();
    }
}