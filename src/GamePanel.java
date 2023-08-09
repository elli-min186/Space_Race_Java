package src;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    static final int SCREENWIDTH = 1080;
    static final int SCREENHEIGHT = 650;
    static final Dimension SCREEN = new Dimension(SCREENWIDTH, SCREENHEIGHT);
    static final int SPACESHIPWIDTH = 100;
    static final int SPACESHIPHEIGHT = 100;

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
        this.spaceship1 = new Spaceship((((SCREENWIDTH/2 + 10) + SCREENWIDTH)/2) - (SPACESHIPWIDTH/2), SCREENHEIGHT - SPACESHIPHEIGHT - 10, SPACESHIPWIDTH, SPACESHIPHEIGHT, 1);
        this.spaceship2 = new Spaceship(((SCREENWIDTH/2)-10)/2 - (SPACESHIPWIDTH/2),SCREENHEIGHT - SPACESHIPHEIGHT - 10,SPACESHIPWIDTH,SPACESHIPHEIGHT,2);

        this.states = modes.GAME; // initialize states(enum)
        this.gameThread = new Thread(this); // thread running game panel
        gameThread.start(); // run on this thread

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

    public void mainGame() {
        switch (states) {
            case TITLE:
                repaint(); // if something moves repaint
                break;
            case MODE:
                repaint();
                break;
            case GAME:
                repaint();
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
