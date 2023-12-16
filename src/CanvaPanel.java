import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CanvaPanel extends JPanel {
    private final FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("image.png","png");
    private static File file = null;
    Canva canva = new Canva();
    CanvaPanel(){
        setLayout(new GridLayout(1,1));
        add(canva);
        //setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    public void chooseImage() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(fileNameExtensionFilter);
        if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
            file = fileChooser.getSelectedFile();
            this.canva.setImage(file);
            this.canva.repaint();
            JOptionPane.showMessageDialog(null, "File chosen!");
        }
    }

    public void saveImage() throws IOException {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(fileNameExtensionFilter);
                BufferedImage imageToSave = canva.recreateImage();
                if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
                    File toSave = fileChooser.getSelectedFile();
                    String absPath = toSave.getAbsolutePath();
                    try{
                        ImageIO.write(imageToSave,"png", new File(absPath));
                        System.out.println("Image Saved!");
                    } catch (IOException ex){
                        System.out.println("Image Save Error!");
                        throw new RuntimeException(ex);
                    }

                }
                return null;
            }
        }.execute();
    }



}
