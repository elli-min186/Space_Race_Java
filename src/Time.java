package src;

import java.awt.*;

public class Time extends Rectangle {
    private int timeMode = 100;
    private int yVelocityTime;

    public Time(int x, int y, int width, int height) {
        super(x, y, width, height);
        yVelocityTime = 2;
    }

    public void move() {
        y += yVelocityTime;
    }

    public void draw(Graphics g, int xTime, int yTime, int widthTime, int heightTime) {
        g.setColor(Color.white);
        g.fillRoundRect(xTime, yTime, widthTime, heightTime, 10, 10);
    }
}
