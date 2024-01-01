package ui;

import drawinglogic.CanvaPanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class MyMenuBar extends JMenuBar implements ActionListener {
    CanvaPanel canvaPanel;

    MyMenuBar(CanvaPanel canvaPanel){
        //creation of menus
        this.canvaPanel = canvaPanel;
        JMenu fileMenu = new JMenu("File");
        JMenu penMenu = new JMenu("Tools");
        JMenu thicknessMenu = new JMenu("Thickness");
        JMenu effectMenu = new JMenu("Effects");

        // creation of menuItems and actionListeners

        // for fileMenu
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(e -> {
            try {
                canvaPanel.chooseImage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> {
            try {
                canvaPanel.saveImage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JMenuItem clearItem = new JMenuItem("Clear");
        clearItem.addActionListener(e -> {
            canvaPanel.canva.setImage();
            canvaPanel.canva.setPointsToNull();
            canvaPanel.canva.repaint();
        });

        JMenuItem helpItem = new JMenuItem("Help");
        helpItem.addActionListener(e->displayHelp());

        // for penMenu
        JMenuItem rubberPenSwitcherItem = new JMenuItem("Rubber");
        rubberPenSwitcherItem.addActionListener(e -> {
            if(Objects.equals(rubberPenSwitcherItem.getText(), "Rubber")){
                rubberPenSwitcherItem.setText("Pen");
                canvaPanel.canva.setRubberOn(true);
            } else {
                rubberPenSwitcherItem.setText("Rubber");
                canvaPanel.canva.setRubberOn(false);
            }
        });

        JMenuItem thickness1xItem = new JMenuItem("1");
        thickness1xItem.addActionListener(this);

        JMenuItem thickness2xItem = new JMenuItem("2");
        thickness2xItem.addActionListener(this);

        JMenuItem thickness4xItem = new JMenuItem("4");
        thickness4xItem.addActionListener(this);

        JMenuItem thickness8xItem = new JMenuItem("8");
        thickness8xItem.addActionListener(this);

        // for effectMenu
        JMenuItem blurItem = new JMenuItem("Blur");
        blurItem.addActionListener(e-> canvaPanel.canva.blurImageGauss());

        JMenuItem pixelateItem = new JMenuItem("Pixelate");
        pixelateItem.addActionListener(e-> canvaPanel.canva.pixelateImage(5));

        JMenuItem grayScaleItem = new JMenuItem("Gray Scale");
        grayScaleItem.addActionListener(e-> canvaPanel.canva.toGrayScale());


        // adding menus
        add(fileMenu);
        add(penMenu);
        add(effectMenu);

        // adding items

        // fileMenu
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(clearItem);
        fileMenu.add(helpItem);

        // penMenu
        penMenu.add(rubberPenSwitcherItem);
        penMenu.add(thicknessMenu);

        // thicknessMenu
        thicknessMenu.add(thickness1xItem);
        thicknessMenu.add(thickness2xItem);
        thicknessMenu.add(thickness4xItem);
        thicknessMenu.add(thickness8xItem);

        // effectMenu
        effectMenu.add(blurItem);
        effectMenu.add(pixelateItem);
        effectMenu.add(grayScaleItem);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem selectedJMenuItem = (JMenuItem) e.getSource();
        canvaPanel.canva.setWidthOfStroke(Integer.parseInt(selectedJMenuItem.getText()));
    }

    private void displayHelp() {
        JOptionPane.showMessageDialog(null, "App info:\nPaint2.0 app in early development state\nIf help needed go to Github");
    }
}
