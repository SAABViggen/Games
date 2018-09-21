package com.sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;

import static com.sudoku.GameColors.*;

public class GuiTextFieldProcessor {

    private GuiBoard guiBoard = GuiBoard.getInstance();
    private GuiProcessor guiProcessor = GuiProcessor.getInstance();
    private GuiTextFieldColorManager colorManager = new GuiTextFieldColorManager();
    private GuiTextFieldKeyboardManager keyboardManager = new GuiTextFieldKeyboardManager();

    public void addTextFieldOperations(Map<Integer, JTextField> sudokuBoardFields) {
        for (Map.Entry<Integer, JTextField> field : sudokuBoardFields.entrySet()) {
            addOperations(field.getValue(), sudokuBoardFields);
        }
    }

    private void addOperations(JTextField textField, Map<Integer, JTextField> sudokuBoardFields) {
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (textField.getText().length() >= 1) {
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                char inputChar = e.getKeyChar();
                int inputCode = e.getKeyCode();
                if (Character.isDigit(inputChar)) {
                    if (!textField.getText().equals("")) {
                        textField.setForeground(USER_INPUT_COLOR);
                        colorManager.illuminateNumbers(textField, sudokuBoardFields);
                        guiBoard.readBoardPanel();
                        guiProcessor.finish();
                    }
                } else if (Character.isLetter(inputChar) || Character.isDefined(inputChar)) {
                    if (textField.getText().equals("") || !Character.isDigit(textField.getText().charAt(0)) || inputCode == KeyEvent.VK_DELETE) {
                        textField.setForeground(GAME_INPUT_COLOR);
                        textField.setText("");
                    }
                } else {
                    keyboardManager.nonAlphanumericKeys(textField, inputCode);
                }
            }
        });

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                colorManager.illuminateNeighbours(textField, sudokuBoardFields);
                colorManager.illuminateNumbers(textField, sudokuBoardFields);
            }

            @Override
            public void focusLost(FocusEvent e) {
                colorManager.deilluminateNumbers(textField, sudokuBoardFields);
            }
        });
        textField.setFont(new Font("Verdana", Font.BOLD, textField.getFont().getSize() + 10));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setBackground(DEFAULT_BACKGROUND);
    }
}