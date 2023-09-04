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
    static final int TIMEWIDTH = 20;
    static final Font font = new Font("Osaka", Font.BOLD, 40);
    static final int FINALSCOREWIN = 7;
    static int counter = 0;
    static int p1score = 0;
    static int p2score = 0;

    static final int P1RECTX1 = (((SCREENWIDTH / 2 + 10) + SCREENWIDTH) / 2) - (SPACESHIPWIDTH / 2);
    static final int P1RECTY1 = SCREENHEIGHT - SPACESHIPHEIGHT - 10;

    static final int P2RECTX1 = ((SCREENWIDTH / 2) - 10) / 2 - (SPACESHIPWIDTH / 2);
    static final int P2RECTY1 = SCREENHEIGHT - SPACESHIPHEIGHT - 10;

    Thread gameThread; // separate from all the tasks
    Graphics graphics;
    Image image;

    public enum modes { // create a new class to show specfic screen
        TITLE,
        MODE,
        TIMEGAME,
        SCOREGAME,
        OVER
    }

    private modes states;

    public TitleScreen titleScreen;
    public ModeScreen modeScreen;
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
        this.endScreen = new EndScreen(0, 0, SCREENWIDTH, SCREENHEIGHT);
        this.time = new Time(SCREENWIDTH / 2 - 10, 2, TIMEWIDTH, SCREENHEIGHT - 5);

        this.spaceship1 = new Spaceship(P1RECTX1, P1RECTY1, SPACESHIPWIDTH, SPACESHIPHEIGHT, 1);
        this.spaceship2 = new Spaceship(P2RECTX1, P2RECTY1, SPACESHIPWIDTH, SPACESHIPHEIGHT, 2);

        ballsArraylist = new ArrayList<>();
        for (int i = 0; i < BALLCOUNT; i++) { // all 30 balls in arraylist
            if (i < BALLCOUNT / 2) {
                ballsArraylist.add(newBallLeft());
            } else {
                ballsArraylist.add(newBallRight());
            }

        }

        this.states = modes.TITLE; // initialize states(enum)
        this.gameThread = new Thread(this); // thread running game panel
        gameThread.start(); // run on this thread

    }

    public Balls newBallLeft() { // a new ball at random y from the left
        Random rand = new Random();
        int randomY = rand.nextInt(SCREENHEIGHT - 90 - BALLRADIUS * 2) + BALLRADIUS * 2; // randint(UPPERBOUND - LOWERBOUND) + LOWERBOUND
        int randomX = rand.nextInt(SCREENWIDTH * 1 / 2);
        return new Balls(randomX, randomY, BALLRADIUS * 2, BALLRADIUS * 2);
    }

    public Balls newBallRight() { // a new ball at random y from the right
        Random rand = new Random();
        int randomY = rand.nextInt(SCREENHEIGHT - 90 - BALLRADIUS * 2) + BALLRADIUS * 2;
        int randomX = rand.nextInt(SCREENWIDTH - SCREENWIDTH * 1 / 2) + SCREENWIDTH * 1 / 2;
        return new Balls(randomX, randomY, BALLRADIUS * 2, BALLRADIUS * 2);
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
                counter++;
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
            if (i < BALLCOUNT / 2) { // first half of the balls move left
                ballsArraylist.get(i).moveLeft();
            } else { // other half move right
                ballsArraylist.get(i).moveRight();
            }

        }
        if (states == modes.TIMEGAME) {
            if (time.y < SCREENHEIGHT) {
                if (counter % 6 == 0) { // time run outs in approximately 1 min
                    time.move();
                }
            }
        }
    }

    public void checkState() {
        if (states == modes.TIMEGAME) { 
            if (time.y >= SCREENHEIGHT) { // if time run outs, end the game
                states = modes.OVER;
            }
        }
        else if (states == modes.SCOREGAME) {
            if (p1score == FINALSCOREWIN || p2score == FINALSCOREWIN) { // if anyone reaches the max score to win, they win
                states = modes.OVER;
            }
        }
    }

    public void checkCollision() {
        checkSpaceShipBallCollision();
        checkBoundaryCollision();
        checkBallCollision();
        checkTimeCollision();
    }

    public void checkBallCollision() { // if ball goes out of screen, generate new balls
        for (int i = 0; i < BALLCOUNT; i++) {
            Balls currentBall = ballsArraylist.get(i);
            if (currentBall.x >= SCREENWIDTH) {
                ballsArraylist.remove(i);
                ballsArraylist.add(i, newBallLeft());
            } else if (currentBall.x <= 0) {
                ballsArraylist.remove(i);
                ballsArraylist.add(i, newBallRight());
            }
        }
    }

    public void checkSpaceShipBallCollision() { // check collision between spaceship and balls
        for (int i = 0; i < BALLCOUNT; i++) {
            Balls currentBall = ballsArraylist.get(i);

            // spaceship 1
            // balls from left hit left side of rectangle
            if (currentBall.x + BALLRADIUS*2 >= spaceship1.x && currentBall.x + BALLRADIUS*2 <= spaceship1.x + SPACESHIPWIDTH) {
                if (currentBall.y + BALLRADIUS *2 >= spaceship1.y && currentBall.y <= spaceship1.y + SPACESHIPHEIGHT) {
                    spaceship1.y = P1RECTY1; // set y to original position
                    spaceship1.x = P1RECTX1; // set x to original position
                }
            }
            // balls from right hit right side of rectangle
            if (currentBall.x <= spaceship1.x + SPACESHIPWIDTH && currentBall.x >= spaceship1.x) {
                if (currentBall.y + BALLRADIUS *2 >= spaceship1.y && currentBall.y <= spaceship1.y + SPACESHIPHEIGHT) {
                    spaceship1.y = P1RECTY1; // set y to original position
                    spaceship1.x = P1RECTX1; // set x to original position
                }
            }

            // left wing collision with balls
            if (currentBall.x + BALLRADIUS*2 >= spaceship1.x - spaceship1.getWingWidth() && currentBall.x + BALLRADIUS*2 <= spaceship1.x) {
                if (currentBall.y + BALLRADIUS *2 <= spaceship1.y + spaceship1.getWingHeight() + SPACESHIPHEIGHT && currentBall.y >= spaceship1.y + 35) {
                    spaceship1.y = P1RECTY1; // set y to original position
                    spaceship1.x = P1RECTX1; // set x to original position
                }
            }

            // right wing collision with balls
            if (currentBall.x <= spaceship1.x + SPACESHIPWIDTH + spaceship1.getWingWidth() && currentBall.x >= spaceship1.x + SPACESHIPWIDTH) {
                if (currentBall.y + BALLRADIUS *2 <= spaceship1.y + spaceship1.getWingHeight() + SPACESHIPHEIGHT && currentBall.y >= spaceship1.y + 35) {
                    spaceship1.y = P1RECTY1; // set y to original position
                    spaceship1.x = P1RECTX1; // set x to original position
                }
            }


            // spaceship 2
            // balls from left hit left side of rectangle
            if (currentBall.x + BALLRADIUS*2 >= spaceship2.x && currentBall.x + BALLRADIUS*2 <= spaceship2.x + SPACESHIPWIDTH) {
                if (currentBall.y + BALLRADIUS *2 >= spaceship2.y && currentBall.y <= spaceship2.y + SPACESHIPHEIGHT) {
                    spaceship2.y = P2RECTY1; // set y to original position
                    spaceship2.x = P2RECTX1; // set x to original position
                }
            }
            // balls from right hit right side of rectangle
            if (currentBall.x <= spaceship2.x + SPACESHIPWIDTH && currentBall.x >= spaceship2.x) {
                if (currentBall.y + BALLRADIUS *2 >= spaceship2.y && currentBall.y <= spaceship2.y + SPACESHIPHEIGHT) {
                    spaceship2.y = P2RECTY1; // set y to original position
                    spaceship2.x = P2RECTX1; // set x to original position
                }
            }

            // left wing collision with balls
            if (currentBall.x + BALLRADIUS*2 >= spaceship2.x - spaceship2.getWingWidth() && currentBall.x + BALLRADIUS*2 <= spaceship2.x) {
                if (currentBall.y + BALLRADIUS *2 <= spaceship2.y + spaceship2.getWingHeight() + SPACESHIPHEIGHT && currentBall.y >= spaceship2.y + 35) {
                    spaceship2.y = P2RECTY1; // set y to original position
                    spaceship2.x = P2RECTX1; // set x to original position
                }
            }

            // right wing collision with balls
            if (currentBall.x <= spaceship2.x + SPACESHIPWIDTH + spaceship2.getWingWidth() && currentBall.x >= spaceship2.x + SPACESHIPWIDTH) {
                if (currentBall.y + BALLRADIUS *2 <= spaceship2.y + spaceship2.getWingHeight() + SPACESHIPHEIGHT && currentBall.y >= spaceship2.y + 35) {
                    spaceship2.y = P2RECTY1; // set y to original position
                    spaceship2.x = P2RECTX1; // set x to original position
                }
            }
        }
    }

    public void checkTimeCollision() { // check collision with spaceship and time frame

        // spaceship 1
        // bottom part of spaceship and time frame top
        if (((spaceship1.x >= time.getX() && spaceship1.x < time.getX() + TIMEWIDTH)
                || (spaceship1.x + SPACESHIPWIDTH >= time.getX() && spaceship1.x < time.getX()))
                && spaceship1.y + SPACESHIPHEIGHT + spaceship1.getWingHeight() >= time.y) {
            spaceship1.y = time.y - SPACESHIPHEIGHT;
        }
        // right wing of spaceship and left side of time frame
        if (spaceship1.x + SPACESHIPWIDTH + spaceship1.getWingWidth() >= time.getX()
                && spaceship1.x + SPACESHIPWIDTH + spaceship1.getWingWidth() < time.getX() + TIMEWIDTH) {
            if (spaceship1.y + SPACESHIPHEIGHT + spaceship1.getWingHeight() >= time.y) {
                spaceship1.x = time.x - spaceship1.getWingWidth() - spaceship1.width;
            }
        }
        // left wing of spaceship and the right side of time frame
        if (spaceship1.x - spaceship1.getWingWidth() <= time.getX() + TIMEWIDTH
                && spaceship1.x - spaceship1.getWingWidth() > time.getX()) {
            if (spaceship1.y + SPACESHIPHEIGHT + spaceship1.getWingHeight() >= time.y) {
                spaceship1.x = time.x + TIMEWIDTH + spaceship1.getWingWidth();
            }
        }

        // spaceship 2
        // bottom part of spaceship and time frame top
        if (((spaceship2.x >= time.getX() && spaceship2.x < time.getX() + TIMEWIDTH)
                || (spaceship2.x + SPACESHIPWIDTH >= time.getX() && spaceship2.x < time.getX()))
                && spaceship2.y + SPACESHIPHEIGHT + spaceship2.getWingHeight() >= time.y) {
            spaceship2.y = time.y - SPACESHIPHEIGHT;
        }
        // right wing of spaceship and left side of time frame
        if (spaceship2.x + SPACESHIPWIDTH + spaceship2.getWingWidth() >= time.getX()
                && spaceship2.x + SPACESHIPWIDTH + spaceship2.getWingWidth() < time.getX() + TIMEWIDTH) {
            if (spaceship2.y + SPACESHIPHEIGHT + spaceship2.getWingHeight() >= time.y) {
                spaceship2.x = time.x - spaceship2.getWingWidth() - spaceship2.width;
            }
        }
        // left wing of spaceship and the right side of time frame
        if (spaceship2.x - spaceship2.getWingWidth() <= time.getX() + TIMEWIDTH
                && spaceship2.x - spaceship2.getWingWidth() > time.getX()) {
            if (spaceship2.y + SPACESHIPHEIGHT + spaceship2.getWingHeight() >= time.y) {
                spaceship2.x = time.x + TIMEWIDTH + spaceship2.getWingWidth();
            }
        }
    }

    public void checkBoundaryCollision() {

        // spaceship 1
        if (spaceship1.y - spaceship1.getHeadHeight() <= 0) { // if the spaceship goes beyond the top boundary
            spaceship1.y = P1RECTY1; // set y to original position
            spaceship1.x = P1RECTX1; // set x to original position
            p1score++;
        } else if (spaceship1.y + SPACESHIPHEIGHT + spaceship1.getWingHeight() >= SCREENHEIGHT) { // bottom boundary
            spaceship1.y = SCREENHEIGHT - SPACESHIPHEIGHT - spaceship1.getWingHeight(); // set the y as 0 so it doesnt go beyond it
        }
        if (spaceship1.x - spaceship1.getWingWidth() <= 0) { // left boundary
            spaceship1.x = spaceship1.getWingWidth();
        } else if (spaceship1.x + SPACESHIPWIDTH + spaceship1.getWingWidth() >= SCREENWIDTH) { // right boundary
            spaceship1.x = SCREENWIDTH - SPACESHIPWIDTH - spaceship1.getWingWidth();
        }

        // spaceship 2
        if (spaceship2.y - spaceship2.getHeadHeight() <= 0) { // if the spaceship goes beyond the top boundary
            spaceship2.y = P2RECTY1; // set y to original position
            spaceship2.x = P2RECTX1; // set x to original position
            p2score++;
        } else if (spaceship2.y + SPACESHIPHEIGHT + spaceship2.getWingHeight() >= SCREENHEIGHT) { // bottom boundary
            spaceship2.y = SCREENHEIGHT - SPACESHIPHEIGHT - spaceship2.getWingHeight(); // set the y as 0 so it doesnt go beyond it
        }
        if (spaceship2.x - spaceship2.getWingWidth() <= 0) { // left boundary
            spaceship2.x = spaceship2.getWingWidth();
        } else if (spaceship2.x + SPACESHIPWIDTH + spaceship2.getWingWidth() >= SCREENWIDTH) { // right boundary
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
            case TIMEGAME:
            case SCOREGAME:
                move();
                checkState();
                checkCollision();
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
                titleScreen.draw(g);
                drawBorder(g);
                break;
            case MODE:
                modeScreen.draw(g);
                drawBorder(g);
                break;
            case TIMEGAME:
            case SCOREGAME:
                for (int i = 0; i < BALLCOUNT; i++) { // draw all 30 balls
                    Balls currentBall = ballsArraylist.get(i);
                    currentBall.draw(g);
                }
                spaceship1.draw(g);
                spaceship2.draw(g);
                time.draw(g);
                drawScore(g);
                drawBorder(g);
                break;
            case OVER:
                if (p1score > p2score) {
                    endScreen.drawP1wins(g);
                }
                else if (p2score > p1score) {
                    endScreen.drawP2wins(g);
                }
                else {
                    endScreen.drawNoWins(g);
                }
                drawBorder(g);
                break;
        }
    }

    public void drawScore(Graphics g) {
        g.setColor(new Color(201, 206, 214));
        g.setFont(font);
        // scoreboard for p1
        g.drawString(Integer.toString(p1score), time.x + TIMEWIDTH + g.getFontMetrics(font).getHeight()/2, SCREENHEIGHT - g.getFontMetrics(font).getHeight()/2);
        // scoreboard for p2
        g.drawString(Integer.toString(p2score), time.x - g.getFontMetrics(font).stringWidth(Integer.toString(p2score)) - g.getFontMetrics(font).getHeight()/2, SCREENHEIGHT - g.getFontMetrics(font).getHeight()/2);
    }

    public void drawBorder(Graphics g) { // border between game screen and panel
        g.setColor(Color.black);
        g.drawRect(0, 0, SCREENWIDTH-1, SCREENHEIGHT-1);
    }

    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (states == modes.TIMEGAME || states == modes.SCOREGAME) {
                spaceship1.keyPressed(e);
                spaceship2.keyPressed(e);
            }
            else if (states == modes.OVER) {
                if (endScreen.keyPressed(e) == 1) { // if user press R
                    spaceship1.x = P1RECTX1;
                    spaceship1.y = P1RECTY1;
                    spaceship2.x = P2RECTX1;
                    spaceship2.y = P2RECTY1;
                    p1score = 0;
                    p2score = 0;
                    states = modes.MODE;
                }
                else if (endScreen.keyPressed(e) == 2) { // if user press esc
                    System.exit(1);
                }
            }
            else if (states == modes.TITLE) {
                if (titleScreen.keyPressed(e) == 1) {
                    states = modes.MODE;
                }
            }
            else if (states == modes.MODE) {
                if (modeScreen.keyPressed(e) == 1) { // if user press T
                    time.y = 0;
                    states = modes.TIMEGAME;
                }
                else if (modeScreen.keyPressed(e) == 2) { // if user press S
                    time.y = 0;
                    states = modes.SCOREGAME;
                }
            }
        }

        public void keyReleased(KeyEvent e) {
            spaceship1.keyReleased(e);
            spaceship2.keyReleased(e);
        }
    }
}
