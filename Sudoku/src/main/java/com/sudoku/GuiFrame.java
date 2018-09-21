package com.sudoku;

import javax.swing.*;
import java.awt.*;

public class GuiFrame extends JFrame {

    private GuiBoard guiBoard = GuiBoard.getInstance();
    private GuiButtons guiButtons = new GuiButtons();

    public GuiFrame() {
        JFrame guiFrame = new JFrame();
        GuiTextFieldProcessor guiTextFieldProcessor = new GuiTextFieldProcessor();
        GuiTimer guiTimer = GuiTimer.getInstance();
        guiFrame.setLayout(new BorderLayout(3, 3));
        guiFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        guiFrame.setTitle("Sudoku");
        guiFrame.setSize(550, 580);
        guiFrame.setMinimumSize(new Dimension(450, 470));
        guiFrame.setLocationRelativeTo(null);

        guiFrame.add(guiTimer.getTimeShow(), BorderLayout.NORTH);
        guiFrame.add(createBoardPanel(), BorderLayout.CENTER);
        guiFrame.add(createMenuPanel(), BorderLayout.SOUTH);
        pack();

        guiButtons.addButtonFunctions();
        guiTextFieldProcessor.addTextFieldOperations(guiBoard.getSudokuBoardFields());
        guiTimer.createTimer();

        guiFrame.setVisible(true);
    }

    private JPanel createMenuPanel() {
        return guiButtons.menuPanel();
    }

    private JPanel createBoardPanel() {
        return guiBoard.boardPanel();
    }
}