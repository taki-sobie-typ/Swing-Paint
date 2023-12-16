import javax.swing.*;

public class ApplicationMain {
    public static void main(String[] args){
        SwingUtilities.invokeLater(() ->{
            MyFrame myFrame = new MyFrame();
            myFrame.setVisible(true);
        });
    }
}
