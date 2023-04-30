package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    protected float x;
    protected float y;
    protected int width;
    protected int height;
    protected Rectangle2D.Float hitBox;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void drawHitBox(Graphics g) {
        //For debugging the hitbox
        g.setColor(Color.PINK);
        g.drawRect((int) hitBox.x,(int)  hitBox.y,(int)  hitBox.width,(int)  hitBox.height);
    }

    protected void initializeHitBox(float x, float y, float width, float height) {
        hitBox = new Rectangle2D.Float(x,y,width,height);
    }

    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }
}
