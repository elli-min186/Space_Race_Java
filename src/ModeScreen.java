package src;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class ModeScreen extends Rectangle {
    static final int SCREENWIDTH = 1080;
    static final int SCREENHEIGHT = 650;
    BufferedImage image;

    public ModeScreen(int x, int y, int width, int height) {
        super(x,y,width,height);
    }

    public void draw(Graphics g) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("SpaceRaceMode.png"))); // reading the image
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
        if (e.getKeyCode() == KeyEvent.VK_T) { // if you press T
            return 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_S) { // if you press S
            return 2;
        }
        return 0;
    }
}