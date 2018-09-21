package com.sudoku;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.sudoku.GameColors.*;
import static com.sudoku.GameConfig.*;

public class GuiTextFieldColorManager {

    private GuiBoard guiBoard = GuiBoard.getInstance();

    public void illuminateNumbers(JTextField textField, Map<Integer, JTextField> sudokuBoardFields) {
        for (int field = 0; field <= FIELDS_SIZE; field++) {
            if (sudokuBoardFields.get(field) != null && !sudokuBoardFields.get(field).getText().equals("")) {
                if (sudokuBoardFields.get(field).getText().equals(textField.getText())) {
                    sudokuBoardFields.get(field).setBackground(HIGHLIGHTED_NUMBERS_BACKGROUND);
                }
            }
        }
        textField.setBackground(HIGHLIGHTED_BACKGROUND);
    }

    public void deilluminateNumbers(JTextField textField, Map<Integer, JTextField> sudokuBoardFields) {
        for (int field = 0; field <= FIELDS_SIZE; field++) {
            if (sudokuBoardFields.get(field) != null) {
                sudokuBoardFields.get(field).setBackground(DEFAULT_BACKGROUND);
            }
        }
        textField.setBackground(DEFAULT_BACKGROUND);
    }

    public void illuminateNeighbours(JTextField textField, Map<Integer, JTextField> sudokuBoardFields) {
        illuminateBox(textField, sudokuBoardFields);
        illuminateCol(textField, sudokuBoardFields);
        illuminateRow(textField, sudokuBoardFields);
    }

    private void illuminateBox(JTextField textField, Map<Integer, JTextField> sudokuBoardFields) {
        Integer num = guiBoard.getSudokuBoardFieldsNumbers().get(textField);
        int box = num / GAME_SIZE;
        if (num % GAME_SIZE == 0) {
            box--;
        }
        for (int i = 0; i < FIELDS_SIZE; i++) {
            if (i % GAME_SIZE != 0 && i / GAME_SIZE == box) {
                sudokuBoardFields.get(i).setBackground(HIGHLIGHTED_FIELDS_BACKGROUND);
            } else if (i % GAME_SIZE == 0 && i / GAME_SIZE == box) {
                sudokuBoardFields.get(i + GAME_SIZE).setBackground(HIGHLIGHTED_FIELDS_BACKGROUND);
            }
        }
    }

    private void illuminateRow(JTextField textField, Map<Integer, JTextField> sudokuBoardFields) {
        Integer num = guiBoard.getSudokuBoardFieldsNumbers().get(textField);
        List<Integer> firstCol = new ArrayList<>(Arrays.asList(1, 4, 7, 28, 31, 34, 55, 58, 61));
        int start = num % BOX_JUMP;
        if (start == 0) {
            start = BOX_JUMP;
        }
        if (num <= BOX_JUMP) {
            while (start > GAME_SIZE) {
                start -= GAME_SIZE;
            }
        } else if (num <= 2 * BOX_JUMP) {
            while (start > GAME_SIZE) {
                start -= GAME_SIZE;
            }
            start += BOX_JUMP;
        } else {
            while (start > GAME_SIZE) {
                start -= GAME_SIZE;
            }
            start += 2 * BOX_JUMP;
        }
        while (!firstCol.contains(start)) {
            start -= 1;
        }
        int addBox = 0;
        for (int col = 0; col < BOX_SIZE; col++) {
            int addCol = 0;
            for (int box = 0; box < BOX_SIZE; box++) {
                sudokuBoardFields.get(start + addCol + addBox).setBackground(HIGHLIGHTED_FIELDS_BACKGROUND);
                addCol++;
            }
            addBox += GAME_SIZE;
        }
    }

    private void illuminateCol(JTextField textField, Map<Integer, JTextField> sudokuBoardFields) {
        Integer num = guiBoard.getSudokuBoardFieldsNumbers().get(textField);
        List<Integer> firstRow = new ArrayList<>(Arrays.asList(1, 2, 3, 10, 11, 12, 19, 20, 21));
        int col = num % BOX_JUMP;
        int start = 1;
        while (start % BOX_JUMP != col) {
            start++;
        }
        while (!firstRow.contains(start)) {
            start -= BOX_SIZE;
        }
        int addBox = 0;
        for (int row = 0; row < BOX_SIZE; row++) {
            int addRow = 0;
            for (int box = 0; box < BOX_SIZE; box++) {
                sudokuBoardFields.get(start + addRow + addBox).setBackground(HIGHLIGHTED_FIELDS_BACKGROUND);
                addRow += BOX_SIZE;
            }
            addBox += BOX_JUMP;
        }
    }
}