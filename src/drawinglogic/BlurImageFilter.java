package drawinglogic;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class BlurImageFilter implements Serializable {
    private final Canva canva;

    public BlurImageFilter(Canva canva) {
        this.canva = canva;
    }

    public void blurImageGauss() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                BufferedImage dummyImage = canva.getImage();
                System.out.println("Applying Gaussian Blur");
                BufferedImage blurredImage = new BufferedImage(dummyImage.getWidth() - 2, dummyImage.getHeight() - 2, BufferedImage.TYPE_INT_ARGB);

                for (int y = 0; y < blurredImage.getHeight(); y++) {
                    for (int x = 0; x < blurredImage.getWidth(); x++) {
                        int[] pixelSum = new int[]{0, 0, 0, 0}; // ARGB

                        for (int a = 0; a < 3; a++) {
                            for (int b = 0; b < 3; b++) {
                                int rgb = dummyImage.getRGB(x + a, y + b);
                                int weight = getWeight(a, b);
                                pixelSum[0] += weight * ((rgb >> 24) & 0xFF); // Alpha
                                pixelSum[1] += weight * ((rgb >> 16) & 0xFF); // Red
                                pixelSum[2] += weight * ((rgb >> 8) & 0xFF);  // Green
                                pixelSum[3] += weight * (rgb & 0xFF);         // Blue
                            }
                        }

                        int totalWeight = getTotalWeight();
                        for (int i = 0; i < 4; i++) {
                            pixelSum[i] /= totalWeight;
                        }

                        int p = (pixelSum[0] << 24) | (pixelSum[1] << 16) | (pixelSum[2] << 8) | pixelSum[3];
                        blurredImage.setRGB(x, y, p);
                    }
                }

                canva.setBackground(blurredImage);
                canva.repaint();
                return null;
            }

            private static int getWeight(int a, int b) {
                int distance = Math.abs(a - 1) + Math.abs(b - 1);
                return 9 - distance; // Higher weight towards the center
            }

            private static int getTotalWeight() {
                int totalWeight = 0;
                for (int a = 0; a < 3; a++) {
                    for (int b = 0; b < 3; b++) {
                        totalWeight += getWeight(a, b);
                    }
                }
                return totalWeight;
            }

        }.execute();
        canva.repaint();
    }
}