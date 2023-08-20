package src;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

public class TitleScreen extends Rectangle {

    static final int SCREENWIDTH = 1080;
    static final int SCREENHEIGHT = 650;
    BufferedImage image;

    public TitleScreen(int x, int y, int width, int height) {
        super(x,y,width,height);
    }

    public void draw(Graphics g) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("SpaceRaceTitle.png"))); // reading the image
            image = resizeImage(image, SCREENWIDTH, SCREENHEIGHT);
            g.drawImage(image, 0, 0, null);
          }
          catch (IOException e) {
            System.out.println(e.getMessage());
          }
    }

    public int keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) { // if you press enter
            return 1;
        }
        return 0;
    }

    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }
}