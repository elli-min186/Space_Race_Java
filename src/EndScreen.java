package src;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class EndScreen extends Rectangle {
    static final int SCREENWIDTH = 1080;
    static final int SCREENHEIGHT = 650;
    BufferedImage image;

    public EndScreen(int x, int y, int width, int height) {
        super(x,y,width,height);
    }

    public void drawP1wins(Graphics g) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("SpaceRaceEndingP1.png"))); // reading the image
            image = resizeImage(image, SCREENWIDTH, SCREENHEIGHT);
            g.drawImage(image, 0, 0, null);
          }
          catch (IOException e) {
            System.out.println(e.getMessage());
          }
    }

    public void drawP2wins(Graphics g) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("SpaceRaceEndingP2.png"))); // reading the image
            image = resizeImage(image, SCREENWIDTH, SCREENHEIGHT);
            g.drawImage(image, 0, 0, null);
          }
          catch (IOException e) {
            System.out.println(e.getMessage());
          }
    }

    public void drawNoWins(Graphics g) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("SpaceRaceEndingDraw.png"))); // reading the image
            image = resizeImage(image, SCREENWIDTH, SCREENHEIGHT);
            g.drawImage(image, 0, 0, null);
          }
          catch (IOException e) {
            System.out.println(e.getMessage());
          }
    }

    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException { // resize image into java
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }
    
    public int keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) { // if you press R
            return 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { // if you press escape
            return 2;
        }
        return 0;
    }
}