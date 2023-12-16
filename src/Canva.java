import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Canva extends JPanel{
    private BufferedImage image = null;

    public void setMyColor(Color myColor) {
        this.myColor = myColor;
    }

    private Color myColor = Color.BLACK;

    public void setPointsToNull() {
        this.points.clear();
    }

    private final ArrayList<Point> points = new ArrayList<>();
    public Canva() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                points.add(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e){
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                points.add(e.getPoint());
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


        graphics2D.setColor(myColor);
        graphics2D.setStroke(new BasicStroke(5));
        for (int i=0 ; i < points.size()-1 ; i++){
            Point p1 = points.get(i);
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

        // clear after rendering
        graphics2D.dispose();
        return imageToSave;
    }

}
