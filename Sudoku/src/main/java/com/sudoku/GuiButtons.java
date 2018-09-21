package com.sudoku;

import javax.swing.*;
import java.awt.*;

public class GuiButtons {

    private JButton generate = new JButton("New");
    private JButton check = new JButton("Check");
    private JButton solve = new JButton("Solve");
    private JButton pause = new JButton("Pause");
    private JButton scoreboard = new JButton("Scoreboard");
    private JButton reset = new JButton("Reset");
    private JButton clear = new JButton("Clear");
    private JButton exit = new JButton("Exit");
    private int dialogButton = JOptionPane.YES_NO_OPTION;
    private GuiBoard guiBoard = GuiBoard.getInstance();
    private GuiProcessor guiProcessor = GuiProcessor.getInstance();
    private GuiTimer guiTimer = GuiTimer.getInstance();

    public JPanel menuPanel() {
        final JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(2, 4));

        menuPanel.add(generate);
        menuPanel.add(check);
        menuPanel.add(solve);
        menuPanel.add(pause);
        menuPanel.add(scoreboard);
        menuPanel.add(reset);
        menuPanel.add(clear);
        menuPanel.add(exit);

        return menuPanel;
    }

    public void addButtonFunctions() {
        generate.addActionListener(event -> guiProcessor.generateSudoku());
        check.addActionListener(event -> guiBoard.checkBoardPanel());
        solve.addActionListener(event -> guiBoard.solveBoardPanel());
        pause.addActionListener(e -> {
            if (guiTimer.getTimer() != null) {
                if (pause.getText().equals("Pause")) {
                    pause.setText("Resume");
                    guiBoard.clearBoardPanel();
                    guiTimer.timerPause();
                } else if (pause.getText().equals("Resume")) {
                    pause.setText("Pause");
                    guiBoard.fillBoardPanel();
                    guiTimer.timerResume();
                }
            }
        });
        scoreboard.addActionListener(event -> guiProcessor.showScoreboard());
        reset.addActionListener(event -> {
            int answer = JOptionPane.showConfirmDialog(null, "Are You sure You want to reset?", "Reset", dialogButton);
            if (answer == JOptionPane.YES_OPTION) {
                guiBoard.resetBoardPanel();
            }
        });
        clear.addActionListener(event -> guiBoard.clearBoardPanel());
        exit.addActionListener(event -> {
            int answer = JOptionPane.showConfirmDialog(null, "Are You sure You want to quit?", "Exit", dialogButton);
            if (answer == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
}