package src;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Spaceship extends Rectangle{
    private int xVelocity;
    private int yVelocity;
    private int id;
    private int speed = 3;

    public Spaceship(int x, int y, int width, int height, int id) {
        super(x,y,width,height);
        this.id = id;
        xVelocity = 0;
        yVelocity = 0;
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
    public int getSpeed() {
        return speed;
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
        if (id == 1) {
            g.setColor(Color.red);
        }
        else if (id == 2) {
            g.setColor(new Color(230,230,250));
        }
        g.fillRect(x, y, width, height);
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
