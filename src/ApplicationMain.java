import ui.MyFrame;

import javax.swing.*;

public class ApplicationMain {
    /**
     * Main function of SwingPain
     * @param args
     */
    public static void main(String[] args){
        SwingUtilities.invokeLater(() ->{
            MyFrame myFrame = new MyFrame();
            myFrame.setVisible(true);
        });
    }
}
