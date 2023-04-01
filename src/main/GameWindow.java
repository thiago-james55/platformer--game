package main;

import javax.swing.*;

public class GameWindow {

    private JFrame jFrame;

    public GameWindow(GamePanel gamePanel) {
        config(gamePanel);
    }

    private void config(GamePanel gamePanel) {
        jFrame = new JFrame();
        jFrame.add(gamePanel);
        jFrame.setSize(400,400);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
}
