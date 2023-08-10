package src;

import java.awt.*;

public class Balls extends Rectangle {
    private int xVelocity;
    private int speed = 4;

    public Balls(int x, int y, int width, int height) {
        super(x, y, width, height);
        xVelocity = 0;
    }

    public int getXvelocity() {
        return xVelocity;
    }

    public int getXpoint() {
        return x;
    }

    public int getYpoint() {
        return y;
    }

    public int getRadius() {
        return width/2;
    }

    public void setXvelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillOval(x - width/2, y - width/2, width, width); // draw a circle
    }

    public void move() {
        setXvelocity(speed);
        x += xVelocity;
    }
}