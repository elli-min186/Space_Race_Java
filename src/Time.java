package src;

import java.awt.*;

public class Time extends Rectangle {
    private int yVelocityTime;

    public Time(int x, int y, int width, int height) {
        super(x, y, width, height);
        yVelocityTime = 1;
    }

    public void move() { 
        y += yVelocityTime; 
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillRoundRect(x, y, width, height, 10, 10);
    }
}
