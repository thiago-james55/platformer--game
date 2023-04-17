package entities;

import main.Game;
import utils.LoadSave;
import static utils.HelpMethods.CanMoveHere;
import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.PlayerConstants.*;

public class Player extends Entity {

    private BufferedImage[][] animations;

    private final int artWidth = 64;
    private final int artHeight = 40;

    private int animationTick;
    private int animationIndex;
    private int animationSpeed = 20;

    private int playerAction = RUNNING;
    private boolean playerIsMoving = false;
    private boolean playerIsAttacking = false;
    private boolean left, up, right, down;
    private float playerSpeed = 2.0f;
    private int[][] levelData;

    private float xDrawOffSet = 21 * Game.SCALE;
    private float yDrawOffSet = 4 * Game.SCALE;


    public Player(float x, float y, int width, int height) {
        super(x, y,width,height);
        loadAnimations();
        initializeHitBox(x,y,20*Game.SCALE,28*Game.SCALE);
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int) (hitBox.x - xDrawOffSet), (int) (hitBox.y - yDrawOffSet), width, height, null);
        drawHitBox(g);
    }

    private void loadAnimations() {

        BufferedImage bufferedImage = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[9][6];

        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = bufferedImage.getSubimage(i * artWidth, j * artHeight, artWidth, artHeight);
            }
        }

    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
    }

    private void updatePosition() {

        playerIsMoving = false;

        if (!left && !right && !up && !down) {
            return;
        }

        float xSpeed = 0;
        float ySpeed = 0;

        if (left && !right) {
            xSpeed = -playerSpeed;
        } else if (right && !left) {
            xSpeed = playerSpeed;
        }

        if (up && !down) {
            ySpeed = -playerSpeed;
        } else if (down && !up) {
            ySpeed = playerSpeed;
        }

//        if (CanMoveHere(x+xSpeed,y+ySpeed,width,height,levelData)) {
//            this.x += xSpeed;
//            this.y += ySpeed;
//            playerIsMoving = true;
//        }

        if (CanMoveHere(hitBox.x + xSpeed,hitBox.y + ySpeed,hitBox.width,hitBox.height,levelData)) {
            hitBox.x += xSpeed;
            hitBox.y += ySpeed;
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
