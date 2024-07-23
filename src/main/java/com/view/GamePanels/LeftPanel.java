package com.view.GamePanels;

import com.GameLogic.Game;
import com.view.Menu;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;

public class LeftPanel extends JPanel {
    private final Timer timer;
    private long lastTime;

    public JLabel currentPlayerIcon = new JLabel();
    public static ImageIcon whiteKing = new ImageIcon("src/main/resources/wk.png");
    public static ImageIcon blackKing = new ImageIcon("src/main/resources/bk.png");

    //    public static JLabel currentPlayerIcon = new JLabel(whiteKing);
    public LeftPanel(Game game, Frame mainFrame) {

        this.setBackground(new Color(136, 136, 227));
        this.setLayout(new FlowLayout());
        JButton button = new JButton("BACK");
        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.addActionListener(e1 -> {
            new Menu();
            mainFrame.dispose();

        });
        this.add(button);

        // TODO: start counting as soon as a piece has been moved.

        // adding a timer
        JLabel timeLabel = new JLabel(String.format("%04d:%02d:%02d.%03d", 0, 0, 0, 0));
        timeLabel.setFont(new Font("Arial", Font.BOLD, 25));

        timer = new Timer(100, e -> {
            long runningTime = System.currentTimeMillis() - lastTime;
            Duration duration = Duration.ofMillis(runningTime);
            long hours = duration.toHours();
            duration = duration.minusHours(hours);
            long minutes = duration.toMinutes();
            duration = duration.minusMinutes(minutes);
            long millis = duration.toMillis();
            long seconds = millis / 1000;
            millis -= (seconds * 1000);
            timeLabel.setText(String.format("%04d:%02d:%02d.%03d", hours, minutes, seconds, millis));
        });
        this.add(timeLabel);

        lastTime = System.currentTimeMillis();
        timer.start();


        JButton stop = new JButton("Stop");
        stop.setFont(new Font("Arial", Font.BOLD, 30));
        stop.addActionListener(e -> timer.stop());
        this.add(stop);

        JLabel currentPlayer = new JLabel("CURRENT PLAYER:");
        currentPlayerIcon = new JLabel(whiteKing);

        this.add(currentPlayer);
        this.add(currentPlayerIcon);

        this.setPreferredSize(new Dimension(200, 550));
    }

    public void changeTeamColor() {
        if (!Game.turnColor) {
            this.remove(currentPlayerIcon);
            currentPlayerIcon = new JLabel(blackKing);
            this.add(currentPlayerIcon);
        } else {
            this.remove(currentPlayerIcon);
            currentPlayerIcon = new JLabel(whiteKing);
            this.add(currentPlayerIcon);
        }
    }
}
