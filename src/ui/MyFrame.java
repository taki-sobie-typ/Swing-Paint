package ui;

import drawinglogic.CanvaPanel;
import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    public MyFrame(){
        // config
        setTitle("Paint2.0");
        setSize(1100,750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // default canvas pane creation
        final CanvaPanel canvaPanel = new CanvaPanel();
        add(canvaPanel, BorderLayout.CENTER);

        // menu panel
        MyMenuBar myMenuBar = new MyMenuBar(canvaPanel);
        setJMenuBar(myMenuBar);

        // color panel
        MyColorPanel myColorPanel = new MyColorPanel(canvaPanel);
        add(myColorPanel, BorderLayout.WEST);
    }
}
