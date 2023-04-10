package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.Directions.*;
import static utils.Constants.PlayerConstants.*;


public class GamePanel extends JPanel {

    private KeyboardInputs keyboardInputs;
    private MouseInputs mouseInputs;

    private float xDelta = 0;
    private float yDelta = 0;

    private final int artWidth = 64;
    private final int artHeight = 40;

    private BufferedImage bufferedImage;
    private BufferedImage[][] animations;

    private int animationTick;
    private int animationIndex;
    private int animationSpeed = 15;

    private int playerAction = RUNNING;
    private int playerDirection = 1;
    private boolean playerIsMoving = false;

    public GamePanel() {

        keyboardInputs = new KeyboardInputs(this);
        mouseInputs = new MouseInputs(this);

        importImage();
        loadAnimations();

        setPanelSize();

        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void importImage() {

        try (InputStream is = getClass().getResourceAsStream("/player_sprites.png")) {
            bufferedImage = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAnimations() {
        animations = new BufferedImage[9][6];

        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = bufferedImage.getSubimage(i*artWidth,j*artHeight,artWidth,artHeight);
            }
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280,720);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(animations[playerAction][animationIndex],(int) xDelta,(int) yDelta,artWidth*2,artHeight*2,null);

        Toolkit.getDefaultToolkit().sync();

    }

    private void updatePosition() {

        if (playerIsMoving) {
            switch (playerDirection) {
                case LEFT -> xDelta -= 5;
                case UP -> yDelta -= 5;
                case RIGHT -> xDelta += 5;
                case DOWN -> yDelta += 5;
            }
        }
    }

    private void updateAnimationTick() {

        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex > getSpriteAmount(playerAction) ) {
                animationIndex = 0;
            }
        }
    }

    public void setAnimation() {
        if (playerIsMoving) {
            playerAction = RUNNING;
        } else {
            playerAction =IDLE;
        }
    }

    public void setPlayerDirection(int playerDirection) {
        this.playerDirection = playerDirection;
        setPlayerIsMoving(true);
    }

    public void setPlayerIsMoving(boolean playerIsMoving) {
        this.playerIsMoving = playerIsMoving;
    }

    public void updateGame() {
        updateAnimationTick();
        setAnimation();
        updatePosition();
    }
}
