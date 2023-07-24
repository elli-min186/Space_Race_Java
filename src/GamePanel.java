package src;

import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
    static final int SCREENWIDTH = 1280;
    static final int SCREENHEIGHT = 1200;
    Thread gameThread; //separate from all the tasks
    Graphics graphics;
    static final Dimension SCREEN = new Dimension(SCREENWIDTH, SCREENHEIGHT);

    public enum modes { //create a new class to show specfic screen
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
    
    public GamePanel() { //constructor; always run first
        this.setFocusable(true);
        this.setPreferredSize(SCREEN);
        this.setMaximumSize(SCREEN);
        this.setMinimumSize(SCREEN);

        this.titleScreen = new TitleScreen(0,0,SCREENWIDTH,SCREENHEIGHT);
        this.modeScreen = new ModeScreen(0,0,SCREENWIDTH,SCREENHEIGHT);
        this.gameScreen = new GameScreen(0,0,SCREENWIDTH,SCREENHEIGHT);
        this.endScreen = new EndScreen(0,0,SCREENWIDTH,SCREENHEIGHT);

        this.states = modes.GAME; //initialize states(enum)
        this.gameThread = new Thread(this); // thread running game panel
        gameThread.start(); //run on this thread
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {

        long time = System.nanoTime(); //the current time in nano seconds
        double framePerSec = 60.0;
        double refresh = 1000000000/framePerSec;
        double changeTime = 0;
        while(true) {
            long currentTime = System.nanoTime();
            changeTime += (currentTime-time) / refresh; //in seconds (not nano)
            if(changeTime >= 1) { // if the second is refresh, refresh
                mainGame();
                changeTime--;
            }
            time = currentTime; //to prevent same x
        }
    }

    public void mainGame() {

    }
}
