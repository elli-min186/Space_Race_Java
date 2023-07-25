package src;

import java.awt.*;

public class Time extends Rectangle {
    public Time(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void draw(Graphics g, int xTime, int yTime, int widthTime, int heightTime) {
        g.drawRect(x, y, width, height);
        g.setColor(Color.white);
        g.fillRoundRect(xTime, yTime, widthTime, heightTime, 10, 10);
    }
}
