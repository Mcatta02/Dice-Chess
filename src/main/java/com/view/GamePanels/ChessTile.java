package com.view.GamePanels;

import com.GameLogic.Vector2;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ChessTile extends JButton {
    public Vector2 pos;

    public ChessTile(Vector2 pos) {
        this.pos = pos;
        Border blackborder = BorderFactory.createLineBorder(Color.BLACK, 2);
        this.setBorder(blackborder);
    }
}