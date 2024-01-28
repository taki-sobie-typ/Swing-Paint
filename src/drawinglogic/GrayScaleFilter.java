package drawinglogic;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class GrayScaleFilter implements Serializable {
    private final Canva canva;

    public GrayScaleFilter(Canva canva) {
        this.canva = canva;
    }

    public void toGrayScale() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                BufferedImage dummyImage = canva.getImage();
                System.out.println("Applying GrayScale");
                BufferedImage grayImage = new BufferedImage(dummyImage.getWidth(), dummyImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
                Graphics g = grayImage.getGraphics();
                g.drawImage(dummyImage, 0, 0, null);
                g.dispose();
                canva.setBackground(grayImage);
                canva.repaint();
                return null;
            }
        }.execute();
    }
}