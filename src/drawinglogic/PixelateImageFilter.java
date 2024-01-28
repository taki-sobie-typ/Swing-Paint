package drawinglogic;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class PixelateImageFilter implements Serializable {
    private final Canva canva;

    public PixelateImageFilter(Canva canva) {
        this.canva = canva;
    }

    public void pixelateImage(int n) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                BufferedImage dummyImage = canva.getImage();
                BufferedImage pixImg = new BufferedImage(dummyImage.getWidth(), dummyImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

                for (int y = 0; y < dummyImage.getHeight() - n; y += n) {
                    for (int x = 0; x < dummyImage.getWidth() - n; x += n) {
                        int[] pixelSum = new int[]{0, 0, 0, 0}; // ARGB

                        for (int a = 0; a < n; a++) {
                            for (int b = 0; b < n; b++) {
                                int rgb = dummyImage.getRGB(x + a, y + b);
                                pixelSum[0] += (rgb >> 24) & 0xFF; // Alpha
                                pixelSum[1] += (rgb >> 16) & 0xFF; // Red
                                pixelSum[2] += (rgb >> 8) & 0xFF;  // Green
                                pixelSum[3] += rgb & 0xFF;         // Blue
                            }
                        }

                        for (int a = 0; a < n; a++) {
                            for (int b = 0; b < n; b++) {
                                int avgAlpha = pixelSum[0] / (n * n);
                                int avgRed = pixelSum[1] / (n * n);
                                int avgGreen = pixelSum[2] / (n * n);
                                int avgBlue = pixelSum[3] / (n * n);

                                int p = (avgAlpha << 24) | (avgRed << 16) | (avgGreen << 8) | avgBlue;
                                pixImg.setRGB(x + a, y + b, p);
                            }
                        }
                    }
                }
                canva.setBackground(pixImg);
                canva.repaint();
                return null;
            }
        }.execute();
    }
}