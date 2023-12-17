import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class MyFrame extends JFrame implements ActionListener {
    MyFrame(){
        // config
        setTitle("Paint2.0");
        setSize(1100,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // default Pane creation
        CanvaPanel canvaPanel = new CanvaPanel();
        add(canvaPanel, BorderLayout.CENTER);

        // whole MENUS
        JMenuBar myMenuBar = new JMenuBar();
        setJMenuBar(myMenuBar);

        // color PANEL
        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new GridLayout(20,1));

        JButton blackButton = new JButton();
        blackButton.setBackground(Color.black);
        blackButton.addActionListener(e ->{
            canvaPanel.canva.setMyColor(0);
        });

        JButton redButton = new JButton();
        redButton.setBackground(Color.red);
        redButton.addActionListener(e ->{
            canvaPanel.canva.setMyColor(1);
        });

        JButton blueButton = new JButton();
        blueButton.setBackground(Color.blue);
        blueButton.addActionListener(e ->{
            canvaPanel.canva.setMyColor(2);
        });

        JButton whiteButton = new JButton();
        whiteButton.setBackground(Color.white);
        whiteButton.addActionListener(e ->{
            canvaPanel.canva.setMyColor(3);
        });

        colorPanel.add(blackButton);
        colorPanel.add(redButton);
        colorPanel.add(blueButton);
        colorPanel.add(whiteButton);

        add(colorPanel, BorderLayout.WEST);

        //creation of menus
        JMenu fileMenu = new JMenu("File");
        JMenu penMenu = new JMenu("Tools");
        JMenu effectMenu = new JMenu("Effects");

        // creation of menuItems
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
        JMenuItem thicknessItem = new JMenuItem("Thickness");
        // for effectMenu
        JMenuItem blurItem = new JMenuItem("Blur");
        JMenuItem grayScaleItem = new JMenuItem("Gray Scale");
        grayScaleItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvaPanel.canva.toGrayScale();
            }
        });


        // adding menus
        myMenuBar.add(fileMenu);
        myMenuBar.add(penMenu);
        myMenuBar.add(effectMenu);

        // adding items
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(clearItem);
        fileMenu.add(helpItem);

        penMenu.add(rubberPenSwitcherItem);
        penMenu.add(thicknessItem);

        effectMenu.add(blurItem);
        effectMenu.add(grayScaleItem);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}