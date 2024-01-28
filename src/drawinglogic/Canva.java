package drawinglogic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Canva extends JPanel {
    private final BlurImageFilter blurImage = new BlurImageFilter(this);
    private final PixelateImageFilter pixelateImage = new PixelateImageFilter(this);
    private final GrayScaleFilter grayScaleFilter = new GrayScaleFilter(this);
    private Boolean rubberOn = false;
    private BufferedImage image = null;
    private int widthOfStroke = 1;
    private int myColor = 0;
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

    public void setRubberOn(Boolean rubberOn) {
        this.rubberOn = rubberOn;
    }

    public void setWidthOfStroke(int widthOfStroke) {
        this.widthOfStroke = widthOfStroke;
    }

    public void setMyColor(int myColor) {
        this.myColor = myColor;
    }

    public void setPointsToNull() {
        this.pointsBlack.clear();
        this.pointsRed.clear();
        this.pointsBlue.clear();
        this.pointsWhite.clear();
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
        grayScaleFilter.toGrayScale();
    }

    public void pixelateImage(int n){
        pixelateImage.pixelateImage(n);
    }

    public void blurImageGauss(){
        blurImage.blurImageGauss();
    }

    public void setBackground(BufferedImage passedImage){
        image = passedImage;
    }

    public BufferedImage getImage() {
        return image;
    }
}
