package com.sudoku;

import javax.swing.*;
import java.awt.*;

import static com.sudoku.GameColors.DEFAULT_BACKGROUND;

public class GuiTimer {

    private static GuiTimer guiTimer = null;
    private int time;
    private Timer timer;
    private JTextField timeShow = new JTextField();

    public GuiTimer() {
    }

    public static GuiTimer getInstance() {
        if (guiTimer == null) {
            synchronized (GuiBoard.class) {
                if (guiTimer == null) {
                    guiTimer = new GuiTimer();
                }
            }
        }
        return guiTimer;
    }

    public void createTimer() {
        timeShow.setEditable(false);
        timeShow.setFont(new Font("Verdana", Font.BOLD, timeShow.getFont().getSize() + 5));
        timeShow.setBackground(DEFAULT_BACKGROUND);
        timeShow.setHorizontalAlignment(JTextField.CENTER);
    }

    public int getTime() {
        return time;
    }

    public Timer getTimer() {
        return timer;
    }

    public JTextField getTimeShow() {
        return timeShow;
    }

    public void timeCounter() {
        timer = new Timer(1000, e -> {
            time++;
            int seconds = time % 60;
            int minutes = (time / 60) % 60;
            int hours = (time / 3600) % 24;
            String display = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            timeShow.setText(display);
        });
    }

    public void timerStart() {
        time = 0;
        timer.start();
    }

    public void timerPause() {
        timer.stop();
    }

    public void timerResume() {
        timer.start();
    }
}