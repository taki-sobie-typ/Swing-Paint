import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Canva extends JPanel {
    public void setRubberOn(Boolean rubberOn) {
        this.rubberOn = rubberOn;
    }

    private Boolean rubberOn = false;
    private BufferedImage image = null;

    public void setWidthOfStroke(int widthOfStroke) {
        this.widthOfStroke = widthOfStroke;
    }

    private int widthOfStroke = 1;

    public void setMyColor(int myColor) {
        this.myColor = myColor;
    }

    private int myColor = 0;

    public void setPointsToNull() {
        this.pointsBlack.clear();
        this.pointsRed.clear();
        this.pointsBlue.clear();
        this.pointsWhite.clear();
    }

    private final ArrayList<Point> pointsBlack = new ArrayList<>();
    private final ArrayList<Point> pointsRed = new ArrayList<>();
    private final ArrayList<Point> pointsBlue = new ArrayList<>();
    private final ArrayList<Point> pointsWhite = new ArrayList<>();
    public Canva() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                if(rubberOn){
                    pointsBlack.remove(e.getPoint());
                    pointsRed.remove(e.getPoint());
                    pointsBlue.remove(e.getPoint());
                    pointsWhite.remove(e.getPoint());
                } else {
                    switch (myColor){
                        case 0:
                            pointsBlack.add(e.getPoint());
                            break;
                        case 1:
                            pointsRed.add(e.getPoint());
                            break;
                        case 2:
                            pointsBlue.add(e.getPoint());
                            break;
                        case 3:
                            pointsWhite.add(e.getPoint());
                            break;
                        default:
                            break;
                    }
                }
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e){
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(rubberOn){
                    pointsBlack.remove(e.getPoint());
                    pointsRed.remove(e.getPoint());
                    pointsBlue.remove(e.getPoint());
                    pointsWhite.remove(e.getPoint());
                } else {
                    switch (myColor){
                        case 0:
                            pointsBlack.add(e.getPoint());
                            break;
                        case 1:
                            pointsRed.add(e.getPoint());
                            break;
                        case 2:
                            pointsBlue.add(e.getPoint());
                            break;
                        case 3:
                            pointsWhite.add(e.getPoint());
                            break;
                        default:
                            break;
                    }
                }
                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics graphicsElement) {
        Graphics2D graphics2D = (Graphics2D) graphicsElement;
        int xWhole = getWidth();
        int yWhole = getHeight();
        graphics2D.setColor(Color.WHITE);
        if (image != null) {
            int x = (getWidth() - image.getWidth()) / 2;
            int y = (getHeight() - image.getHeight()) / 2;
            graphics2D.fillRect(0,0,xWhole,yWhole);
            graphics2D.drawImage(image, x, y, this);
        } else {
            graphics2D.fillRect(0,0,xWhole,yWhole);
        }

        graphics2D.setStroke(new BasicStroke(widthOfStroke));

        graphics2D.setColor(Color.BLACK);
        for (int i=0 ; i < pointsBlack.size()-1 ; i++){
            Point p1 = pointsBlack.get(i);
            graphics2D.drawLine(p1.x, p1.y, p1.x, p1.y);
        }
        graphics2D.setColor(Color.RED);
        for (int i=0 ; i < pointsRed.size()-1 ; i++){
            Point p1 = pointsRed.get(i);
            graphics2D.drawLine(p1.x, p1.y, p1.x, p1.y);
        }
        graphics2D.setColor(Color.BLUE);
        for (int i=0 ; i < pointsBlue.size()-1 ; i++){
            Point p1 = pointsBlue.get(i);
            graphics2D.drawLine(p1.x, p1.y, p1.x, p1.y);
        }
        graphics2D.setColor(Color.WHITE);
        for (int i=0 ; i < pointsWhite.size()-1 ; i++){
            Point p1 = pointsWhite.get(i);
            graphics2D.drawLine(p1.x, p1.y, p1.x, p1.y);
        }
    }

    public void setImage(File file) throws IOException {
        this.image = ImageIO.read(file);
    }

    public void setImage(){
        this.image = null;
    }

    public BufferedImage recreateImage() {
        BufferedImage imageToSave = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = imageToSave.createGraphics();
        this.paint(graphics2D);
        graphics2D.dispose();
        return imageToSave;
    }

    public void toGrayScale(){
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground(){
                BufferedImage dummyImage = image;
                System.out.println("Applying GrayScale");
                BufferedImage grayImage = new BufferedImage(dummyImage.getWidth(), dummyImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
                Graphics g = grayImage.getGraphics();
                g.drawImage(dummyImage,0,0,null);
                g.dispose();
                image = grayImage;
                return null;
            }
        }.execute();
        repaint();
    }

    // Gaussian Blur is basically the same as pixelating, but instead of an average, we use a weighted average.
    // And we are going to use a weight matrix for that,
    // basically the center is heaviest, and the outer edges of a matrix are lighter
    public void blurImageGauss(){
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground(){
                BufferedImage dummyImage = image;
                System.out.println("Applying Gaussian Blur");
                BufferedImage blurredImage = new BufferedImage(dummyImage.getWidth()-2, dummyImage.getHeight()-2, BufferedImage.TYPE_INT_ARGB);

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

                image = blurredImage;
                repaint();
                return null;
            }

            private static int getWeight(int a, int b) {
                //System.out.println("Calculating pixel weight");
                // Calculate the weight based on the distance from the center
                int distance = Math.abs(a - 1) + Math.abs(b - 1);
                return 9 - distance; // Higher weight towards the center
            }

            private static int getTotalWeight() {
                //System.out.println("Calculating total weight");
                // Calculate the total weight for normalization
                int totalWeight = 0;
                for (int a = 0; a < 3; a++) {
                    for (int b = 0; b < 3; b++) {
                        totalWeight += getWeight(a, b);
                    }
                }
                return totalWeight;
            }

        }.execute();
        repaint();
    }


    public void pixelateImage(int n){
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground(){
                BufferedImage dummyImage = image;
                BufferedImage pixImg = new BufferedImage(dummyImage.getWidth(), dummyImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

                for (int y = 0; y < dummyImage.getHeight() - n; y += n) {
                    for (int x = 0; x < dummyImage.getWidth() - n; x += n) {
                        //for different color values Opacity, Red, Green, Blue
                        //if not there we use one value then we turn the image Gray
                        int[] pixelSum = new int[]{0, 0, 0, 0}; // ARGB

                        for (int a = 0; a < n; a++) {
                            for (int b = 0; b < n; b++) {
                                int rgb = dummyImage.getRGB(x + a, y + b);
                                //That's first go through an image chunk by chunk to get the values of all pixels
                                pixelSum[0] += (rgb >> 24) & 0xFF; // Alpha
                                pixelSum[1] += (rgb >> 16) & 0xFF; // Red
                                pixelSum[2] += (rgb >> 8) & 0xFF;  // Green
                                pixelSum[3] += rgb & 0xFF;         // Blue
                            }
                        }

                        for (int a = 0; a < n; a++) {
                            for (int b = 0; b < n; b++) {
                                //there we calculate an average
                                int avgAlpha = pixelSum[0] / (n * n);
                                int avgRed = pixelSum[1] / (n * n);
                                int avgGreen = pixelSum[2] / (n * n);
                                int avgBlue = pixelSum[3] / (n * n);

                                //apply an average to all the different color codes
                                int p = (avgAlpha << 24) | (avgRed << 16) | (avgGreen << 8) | avgBlue;
                                pixImg.setRGB(x + a, y + b, p);
                            }
                        }
                    }
                }
                image = pixImg;
                repaint();
                return null;
            }
        }.execute();
    }

}
