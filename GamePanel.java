import java.awt.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
    static final int SCREENWIDTH = 1280;
    static final int SCREENHEIGHT = 1200;
    Thread game; //separate from all the tasks
    Graphics graphics;
    static final Dimension SCREEN = new Dimension(SCREENWIDTH, SCREENHEIGHT);
    public enum modes { //create a new class to show specfic screen
        TITLE,
        MODE,
        GAME,
        OVER
    }
    private modes start;
    public GamePanel() { //constructor; always run first
        this.setFocusable(true);
        this.setPreferredSize(SCREEN);
        this.setMaximumSize(SCREEN);
        this.setMinimumSize(SCREEN);
    }
    public void run() {
        ;
    }
}
