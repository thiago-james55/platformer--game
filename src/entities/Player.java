package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.PlayerConstants.*;

public class Player extends Entity {

    private BufferedImage bufferedImage;
    private BufferedImage[][] animations;

    private final int artWidth = 64;
    private final int artHeight = 40;

    private int animationTick;
    private int animationIndex;
    private int animationSpeed = 15;

    private int playerAction = RUNNING;
    private boolean playerIsMoving = false;
    private boolean playerIsAttacking = false;
    private boolean left, up, right, down;
    private float playerSpeed = 2.0f;


    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int) x, (int) y, artWidth * 2, artHeight * 2, null);
    }

    private void loadAnimations() {

        try (InputStream is = getClass().getResourceAsStream("/player_sprites.png")) {
            bufferedImage = ImageIO.read(is);

            animations = new BufferedImage[9][6];

            for (int j = 0; j < animations.length; j++) {
                for (int i = 0; i < animations[j].length; i++) {
                    animations[j][i] = bufferedImage.getSubimage(i * artWidth, j * artHeight, artWidth, artHeight);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updatePosition() {

        playerIsMoving = false;

        if (left && !right) {
            x -= playerSpeed;
            playerIsMoving = true;
        } else if (right && !left) {
            x += playerSpeed;
            playerIsMoving = true;
        }

        if (up && !down) {
            y -= playerSpeed;
            playerIsMoving = true;
        } else if (down && !up) {
            y += playerSpeed;
            playerIsMoving = true;
        }


    }

    private void updateAnimationTick() {

        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex > getSpriteAmount(playerAction)) {
                animationIndex = 0;
                playerIsAttacking = false;
            }
        }
    }

    public void setAnimation() {

        int startAnimation = playerAction;

        if (playerIsMoving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if (playerIsAttacking) {
            playerAction = ATTACK_1;
        }

        if (startAnimation != playerAction) {
            resetAnimationTick();
        }
    }

    private void resetAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void resetDirectionBooleans() {
        setRight(false);
        setDown(false);
        setLeft(false);
        setRight(false);
    }

    public boolean isPlayerIsAttacking() {
        return playerIsAttacking;
    }

    public void setPlayerIsAttacking(boolean playerIsAttacking) {
        this.playerIsAttacking = playerIsAttacking;
    }
}
