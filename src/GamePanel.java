package src;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    static final int SCREENWIDTH = 1080;
    static final int SCREENHEIGHT = 650;
    static final Dimension SCREEN = new Dimension(SCREENWIDTH, SCREENHEIGHT);
    static final int SPACESHIPWIDTH = 20;
    static final int SPACESHIPHEIGHT = 50;
    static final int BALLRADIUS = 5;
    static final int BALLCOUNT = 30;


    static final int P1RECTX1 = (((SCREENWIDTH/2 + 10) + SCREENWIDTH)/2) - (SPACESHIPWIDTH/2);
    static final int P1RECTY1 = SCREENHEIGHT - SPACESHIPHEIGHT - 10;

    static final int P2RECTX1 = ((SCREENWIDTH/2)-10)/2 - (SPACESHIPWIDTH/2);
    static final int P2RECTY1 = SCREENHEIGHT - SPACESHIPHEIGHT - 10;
    

    Thread gameThread; // separate from all the tasks
    Graphics graphics;
    Image image;

    public enum modes { // create a new class to show specfic screen
        TITLE,
        MODE,
        GAME,
        OVER
    }

    private modes states;

    public TitleScreen titleScreen;
    public ModeScreen modeScreen;
    public GameScreen gameScreen;
    public EndScreen endScreen;
    public Time time;
    public Spaceship spaceship1;
    public Spaceship spaceship2;
    public ArrayList<Balls> ballsArraylist;


    public GamePanel() { // constructor; always run first
        
        this.setFocusable(true);
        this.setPreferredSize(SCREEN);
        this.setMaximumSize(SCREEN);
        this.setMinimumSize(SCREEN);
        this.addKeyListener(new AL());

        this.titleScreen = new TitleScreen(0, 0, SCREENWIDTH, SCREENHEIGHT);
        this.modeScreen = new ModeScreen(0, 0, SCREENWIDTH, SCREENHEIGHT);
        this.gameScreen = new GameScreen(0, 0, SCREENWIDTH, SCREENHEIGHT);
        this.endScreen = new EndScreen(0, 0, SCREENWIDTH, SCREENHEIGHT);
        this.time = new Time(0, 0, SCREENWIDTH, SCREENHEIGHT);

        
        this.spaceship1 = new Spaceship(P1RECTX1, P1RECTY1, SPACESHIPWIDTH, SPACESHIPHEIGHT, 1);
        this.spaceship2 = new Spaceship(P2RECTX1, P2RECTY1, SPACESHIPWIDTH, SPACESHIPHEIGHT, 2);

        ballsArraylist = new ArrayList<>();
        for (int i = 0; i < BALLCOUNT; i++) { // all 30 balls in arraylist
            ballsArraylist.add(newBall());
        }

  

        this.states = modes.GAME; // initialize states(enum)
        this.gameThread = new Thread(this); // thread running game panel
        gameThread.start(); // run on this thread

    }

    public Balls newBall() { // a new ball at random y
        Random rand = new Random();
        int randomY = rand.nextInt(SCREENHEIGHT-90);
        int randomX = rand.nextInt(SCREENWIDTH* 3/4);
        return new Balls(randomX,randomY,BALLRADIUS * 2,BALLRADIUS * 2);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() { // game refresh every 1/60 seconds

        long time = System.nanoTime(); // the current time in nano seconds
        double framePerSec = 60.0;
        double refresh = 1000000000 / framePerSec;
        double changeTime = 0;
        while (true) {
            long currentTime = System.nanoTime();
            changeTime += (currentTime - time) / refresh; // in seconds (not nano)
            if (changeTime >= 1) { // if the second is refresh, refresh
                mainGame();
                changeTime--;
            }
            time = currentTime; // to prevent same x
        }
    }

    public void move() {
        spaceship1.moveX();
        spaceship1.moveY();
        spaceship2.moveX();
        spaceship2.moveY();
        for (int i = 0; i < BALLCOUNT; i++) {
            ballsArraylist.get(i).move();
        }
        time.move();
    }

    public void checkCollision() {
        checkBoundaryCollision();
    }

    public void checkBoundaryCollision() {

        // spaceship 1
        if (spaceship1.y - spaceship1.getHeadHeight() <= 0) { // if the spaceship goes beyond the top boundary
            spaceship1.y = P1RECTY1; // set y to original position
            spaceship1.x = P1RECTX1; // set x to original position
        }
        else if (spaceship1.y + SPACESHIPHEIGHT + spaceship1.getWingHeight() >= SCREENHEIGHT) { // bottom boundary
            spaceship1.y = SCREENHEIGHT - SPACESHIPHEIGHT - spaceship1.getWingHeight(); // set the y as 0 so it doesnt go beyond it
        }
        if (spaceship1.x - spaceship1.getWingWidth() <= 0) { // left boundary
            spaceship1.x = spaceship1.getWingWidth();
        }
        else if (spaceship1.x + SPACESHIPWIDTH + spaceship1.getWingWidth() >= SCREENWIDTH) { // right boundary
            spaceship1.x = SCREENWIDTH - SPACESHIPWIDTH - spaceship1.getWingWidth();
        }

        // spaceship 2
        if (spaceship2.y - spaceship2.getHeadHeight() <= 0) { // if the spaceship goes beyond the top boundary
            spaceship2.y = P2RECTY1; // set y to original position
            spaceship2.x = P2RECTX1; // set x to original position
        }
        else if (spaceship2.y + SPACESHIPHEIGHT + spaceship2.getWingHeight() >= SCREENHEIGHT) { // bottom boundary
            spaceship2.y = SCREENHEIGHT - SPACESHIPHEIGHT - spaceship2.getWingHeight(); // set the y as 0 so it doesnt go beyond it
        }
        if (spaceship2.x - spaceship2.getWingWidth() <= 0) { // left boundary
            spaceship2.x = spaceship2.getWingWidth();
        }
        else if (spaceship2.x + SPACESHIPWIDTH + spaceship2.getWingWidth() >= SCREENWIDTH) { // right boundary
            spaceship2.x = SCREENWIDTH - SPACESHIPWIDTH - spaceship2.getWingWidth();
        }
    }

    public void mainGame() {
        switch (states) {
            case TITLE:
                repaint(); // if something moves repaint
                break;
            case MODE:
                repaint();
                break;
            case GAME:
                move();
                checkBoundaryCollision();
                repaint(); // everything that has draw function is called again
                break;
            case OVER:
                repaint();
                break;
        }
    }

    public void paint(Graphics g) { // overwrites graphics in Jpanel
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        switch (states) {
            case TITLE:

                break;
            case MODE:

                break;
            case GAME:
                time.draw(g, SCREENWIDTH / 2 - 10, 2, 20, SCREENHEIGHT - 5);
                spaceship1.draw(g);
                spaceship2.draw(g);
                for (int i = 0; i < BALLCOUNT; i++) { // draw all 30 balls
                    Balls currentBall = ballsArraylist.get(i);
                    currentBall.draw(g);
                }
                break;
            case OVER:

                break;
        }
    }

    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            spaceship1.keyPressed(e);
            spaceship2.keyPressed(e);
        }
        public void keyReleased(KeyEvent e) {
            spaceship1.keyReleased(e);
            spaceship2.keyReleased(e);
        }
    }
}
