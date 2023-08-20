package src;

import java.awt.*;
import java.awt.event.KeyEvent;

public class EndScreen extends Rectangle {
    static final int SCREENWIDTH = 1080;
    static final int SCREENHEIGHT = 650;
    static final Font gameOverFont = new Font("Osaka", Font.BOLD, 80);
    static final Font winnerFont = new Font("Osaka", Font.PLAIN, 40);
    static final Font questionFont = new Font("Osaka", Font.PLAIN, 30);
    static final Font restartQuitFont = new Font("Osaka", Font.PLAIN, 20);

    public EndScreen(int x, int y, int width, int height) {
        super(x,y,width,height);
    }

    public void drawP1wins(Graphics g) {
        g.setColor(new Color(255, 73, 73));
        g.setFont(winnerFont);
        g.drawString("Player 1 wins!", SCREENWIDTH/2 - g.getFontMetrics(winnerFont).stringWidth("Player 1 wins!")/2, SCREENHEIGHT/3 - g.getFontMetrics(winnerFont).getHeight()/2);
    }

    public void drawP2wins(Graphics g) {
        g.setColor(new Color(87, 35, 196));
        g.setFont(winnerFont);
        g.drawString("Player 2 wins!", SCREENWIDTH/2 - g.getFontMetrics(winnerFont).stringWidth("Player 2 wins!")/2, SCREENHEIGHT/3 - g.getFontMetrics(winnerFont).getHeight()/2);
    }

    public void drawGameOver(Graphics g) {
        g.setColor(new Color(214, 0, 0));
        g.setFont(gameOverFont);
        g.drawString("GAME OVER!", SCREENWIDTH/2 - g.getFontMetrics(gameOverFont).stringWidth("GAME OVER!")/2, SCREENHEIGHT/2);
    }

    public void drawQuestion(Graphics g) {
        g.setColor(Color.white);
        g.setFont(questionFont);
        g.drawString("Do you want to play again?", SCREENWIDTH/2 - g.getFontMetrics(questionFont).stringWidth("Do you want to play again?")/2, SCREENHEIGHT*3/4 - g.getFontMetrics(questionFont).getHeight());
    }

    public void drawRestart(Graphics g) {
        g.setColor(new Color(160, 235, 148));
        g.setFont(restartQuitFont);
        g.drawString("Press 'R' to restart the game", SCREENWIDTH/4 - g.getFontMetrics(restartQuitFont).stringWidth("Press 'R' to restart the game")/2, SCREENHEIGHT*3/4 + g.getFontMetrics(restartQuitFont).getHeight() + 40);
    }

    public void drawQuit(Graphics g) {
        g.setColor(new Color(240, 179, 177));
        g.setFont(restartQuitFont);
        g.drawString("Press 'Esc' to quit the game", SCREENWIDTH*3/4 - g.getFontMetrics(restartQuitFont).stringWidth("Press 'Esc' to quit the game")/2, SCREENHEIGHT*3/4 + g.getFontMetrics(restartQuitFont).getHeight() + 40);
    }

    public int keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) { // if you press R
            return 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { // if you press escape
            return 2;
        }
        return 0;
    }
}