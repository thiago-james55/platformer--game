package entities;

import static utils.HelpMethods.*;

import main.Game;
import utils.LoadSave;

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
    private boolean left, up, right, down, jump;
    private float playerSpeed = 2.0f;
    private int[][] levelData;

    private float xDrawOffSet = 21 * Game.SCALE;
    private float yDrawOffSet = 4 * Game.SCALE;

    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    public Player(float x, float y, int width, int height) {
        super(x, y,width,height);
        loadAnimations();
        initializeHitBox(x,y,20*Game.SCALE,27*Game.SCALE);
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int) (hitBox.x - xDrawOffSet), (int) (hitBox.y - yDrawOffSet), width, height, null);
        //drawHitBox(g);
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
        if (!IsEntityOnFloor(hitBox,levelData)) { inAir = true; }
    }

    private void updatePosition() {

        playerIsMoving = false;

        if (jump) {
            jump();
        }

        if (!left && !right && !inAir) {
            return;
        }

        float xSpeed = 0;

        if (left) { xSpeed -= playerSpeed; }
        if (right) { xSpeed += playerSpeed; }
        if (!inAir && !IsEntityOnFloor(hitBox, levelData)) {
            if (!IsEntityOnFloor(hitBox, levelData)) {
                inAir = true;
            }
        }

        if (inAir) {
            if (CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, levelData)) {
                hitBox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitBox.y = GetEntityYPosUnderRoofOrAboveFloor(hitBox,airSpeed);
                if (airSpeed > 0) {
                    resetInAir();
                } else {
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }
        } else {
            updateXPos(xSpeed);
        }

        playerIsMoving = true;

    }

    private void jump() {
        if (inAir) { return; }
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, levelData)) {
            hitBox.x += xSpeed;
        } else {
            hitBox.x = GetEntityXPosNextToWall(hitBox, xSpeed);
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

        if (inAir) { playerAction = airSpeed < 0 ? JUMP : FALLING; }

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

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
