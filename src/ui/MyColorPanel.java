package ui;

import drawinglogic.CanvaPanel;
import javax.swing.*;
import java.awt.*;

public class MyColorPanel extends JPanel {
    CanvaPanel canvaPanel;

    MyColorPanel(CanvaPanel canvaPanel){
        this.canvaPanel = canvaPanel;
        setLayout(new GridLayout(20,1));

        // setting buttons
        JButton blackButton = new JButton();
        blackButton.setBackground(Color.black);
        blackButton.addActionListener(e -> canvaPanel.canva.setMyColor(0));

        JButton redButton = new JButton();
        redButton.setBackground(Color.red);
        redButton.addActionListener(e -> canvaPanel.canva.setMyColor(1));

        JButton blueButton = new JButton();
        blueButton.setBackground(Color.blue);
        blueButton.addActionListener(e -> canvaPanel.canva.setMyColor(2));

        JButton whiteButton = new JButton();
        whiteButton.setBackground(Color.white);
        whiteButton.addActionListener(e -> canvaPanel.canva.setMyColor(3));

        // adding buttons
        add(blackButton);
        add(redButton);
        add(blueButton);
        add(whiteButton);

    }
}
