package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel {

    private KeyboardInputs keyboardInputs;
    private MouseInputs mouseInputs;

    private float xDelta = 0;
    private float yDelta = 0;
    private float xDir = 1f;
    private float yDir = 1f;

    private Color color;
    private Random random;

    public GamePanel() {

        keyboardInputs = new KeyboardInputs(this);
        mouseInputs = new MouseInputs(this);
        random = new Random();

        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        setReactPos();
        g.setColor(color);
        g.fillRect((int) xDelta, (int) yDelta,200,50 );
        Toolkit.getDefaultToolkit().sync();

    }

    public void setReactPos() {
        setxDelta(xDelta += xDir);
        if (xDelta > 400 || xDelta < 0) {
            xDir *= -1;
            color = getRandomColor();
        }

        setyDelta(yDelta += yDir);
        if (yDelta > 400 || yDelta < 0) {
            yDir *= -1;
            color = getRandomColor();
        }
    }

    private Color getRandomColor() {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r,g,b);
    }

    public void setReactPos(int xDelta, int yDelta) {
        setxDelta(xDelta);
        setyDelta(yDelta);
    }

    public void setxDelta(float xDelta) {
        this.xDelta = xDelta;
    }

    public void setyDelta(float yDelta) {
        this.yDelta = yDelta;
    }
}
