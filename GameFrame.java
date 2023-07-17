import java.awt.Color;
import java.awt.GridBagLayout;

import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {
    GamePanel panel;
    public GameFrame(){
        panel = new GamePanel();
        this.setTitle("Space Racer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setBackground(Color.BLACK);
        this.getContentPane().setBackground(Color.BLACK);

        this.setResizable(true);
        this.setLayout(new GridBagLayout());
        this.add(panel);

        this.pack(); //all the objects in preferred sizes
        this.setLocationRelativeTo(null); //start the window in the center of the screen
        this.setVisible(true); //makes the screen visible
    }
}
