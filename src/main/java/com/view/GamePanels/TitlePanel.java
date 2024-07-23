package com.view.GamePanels;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {
    public TitlePanel() {
        this.setBackground(new Color(136, 136, 227));
        JLabel title = new JLabel("DICE CHESS");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        this.setPreferredSize(new Dimension(800, 50));
        this.add(title);
    }
}
