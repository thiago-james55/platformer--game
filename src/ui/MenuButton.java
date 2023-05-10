package ui;

import gamestates.GameState;
import utils.LoadSave;
import static utils.Constants.UI.Buttons.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuButton {

    private int xPosition;
    private int yPosition;
    private int rowIndex;
    private int index;
    private int xOffSetCenter =  BUTTON_WIDTH/2;

    private boolean mouseOver;
    private boolean mousePressed;

    private GameState state;

    private Rectangle bounds;
    private BufferedImage[] bufferedImages;

    public MenuButton(int xPosition, int yPosition, int rowIndex, GameState state) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImages();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPosition - xOffSetCenter, yPosition, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    private void loadImages() {
        bufferedImages = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTONS);
        for (int i = 0; i < bufferedImages.length; i++) {
            bufferedImages[i] = temp.getSubimage(
                    i * BUTTON_WIDTH_DEFAULT,rowIndex * BUTTON_HEIGHT_DEFAULT,
                    BUTTON_WIDTH_DEFAULT,BUTTON_HEIGHT_DEFAULT);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(bufferedImages[index],xPosition-xOffSetCenter,yPosition,BUTTON_WIDTH,BUTTON_HEIGHT,null);
    }

    public void update() {
        index = 0;
        if(mouseOver) {
            index = 1;
        }
        if (mousePressed) {
            index = 2;
        }
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void applyGameState() {
        GameState.state = state;
    }

    public void resetBooleans() {
        mouseOver = false;
        mousePressed = false;
    }

    public Rectangle getBounds() {
        return bounds;
    }


}
