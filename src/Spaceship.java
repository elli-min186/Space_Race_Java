package src;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Spaceship extends Rectangle{
    private int xVelocity;
    private int yVelocity;
    private int id;
    private int speed = 3;
    private int topX;
    private int topY;
    private int leftX;
    private int rightX;
    private int bottomY;

    public Spaceship(int x, int y, int width, int height, int id) {
        super(x,y,width,height);
        this.id = id;
        xVelocity = 0;
        yVelocity = 0;
        topX = x + width/2; // very top x of head of spaceship
        topY = y - 10; // very top y of head of spaceship
        leftX = x - 10; // leftmost x of spaceship
        rightX = x + width + 10; // rightmost x of spaceship
        bottomY = y + height + 3; // bottommost y of spaceship
    }

    public int getXvelocity() {
        return xVelocity;
    }
    public int getYvelocity() {
        return yVelocity;
    }
    public int getId() {
        return id;
    }
    
    public int getTopX() {
        return topX;
    }

    public int getTopY() {
        return topY;
    }

    public int getLeftX() {
        return leftX;
    }

    public int getRightX() {
        return rightX;
    }

    public int getBottomY() {
        return bottomY;
    }

    
    public void setXvelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }
    public void setYvelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void draw(Graphics g) {
        if (id == 1) {  // color of body
            g.setColor(new Color(255, 73, 73));
        }
        else if (id == 2) {
            g.setColor(new Color(87, 35, 196)); 
        }
        g.fillRect(x, y, width, height);
        fillTriangle(g, x, y, topX, topY, x + width, y); // head of spaceship
        if (id == 1) { // color of wings
            g.setColor(new Color(15, 67, 146));
        }
        else if (id == 2) {
            g.setColor(new Color(195, 149, 245));
        }
        fillTriangle(g, x - 10, y + height + 3, x, y + 20, x, y + height); // left wing of spaceship
        fillTriangle(g, x + width + 10, y + height + 3, x + width, y + 20, x + width, y + height); // right wing of spaceship
        

    }

    public void fillTriangle(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3) {
        g.fillPolygon(new int[] {x1, x2, x3}, new int[] {y1, y2, y3}, 3);
    }

    public void moveX() {
        x += xVelocity;
    }
    public void moveY() {
        y += yVelocity;
    }

    public void keyPressed(KeyEvent e) {
        switch(id) {
            case 1:
                if (e.getKeyCode() == KeyEvent.VK_DOWN) { //if down arrow is pressed
                    setYvelocity(speed);
                    moveY();
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    setXvelocity(-speed);
                    moveX();
                }
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    setXvelocity(speed);
                    moveX();
                }
                else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    setYvelocity(-speed);
                    moveY();
                }
                break;
            case 2:
                if (e.getKeyCode() == KeyEvent.VK_D) { // right
                    setXvelocity(speed);
                    moveX();
                }
                else if (e.getKeyCode() == KeyEvent.VK_A) { // left
                    setXvelocity(-speed);
                    moveX();
                }
                else if (e.getKeyCode() == KeyEvent.VK_W) { // up
                    setYvelocity(-speed);
                    moveY();
                }
                else if (e.getKeyCode() == KeyEvent.VK_S) { //if down arrow is pressed with ASDW
                    setYvelocity(speed);
                    moveY();
                }
                break;
        }
    }

    public void keyReleased(KeyEvent e) { // if the button is not pressed
        switch(id) {
            case 1:
                if (e.getKeyCode() == KeyEvent.VK_UP) { //if up arrow is pressed
                    setYvelocity(0);
                    moveY();
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    setXvelocity(0);
                    moveX();
                }
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    setXvelocity(0);
                    moveX();
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    setYvelocity(0);
                    moveY();
                }
                break;
            case 2:
                if (e.getKeyCode() == KeyEvent.VK_D) { // right
                    setXvelocity(0);
                    moveX();
                }
                else if (e.getKeyCode() == KeyEvent.VK_A) { // left
                    setXvelocity(0);
                    moveX();
                }
                else if (e.getKeyCode() == KeyEvent.VK_S) { // down
                    setYvelocity(0);
                    moveY();
                }
                else if (e.getKeyCode() == KeyEvent.VK_W) { //if up is pressed with ASDW
                    setYvelocity(0);
                    moveY();
                }
                break;
        }
    }
}
