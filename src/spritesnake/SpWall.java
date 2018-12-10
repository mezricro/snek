package spritesnake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class SpWall extends SpSprite{
    
    int length;
    int thicc;
    boolean horizontal;
    private final Rectangle hitbox;
    
    public SpWall(int x, int y, int lengthInUnits, boolean horizontal) {
        super(x, y, SpGame.WALL_SPRITE);
        this.x = x;
        this.y = y;
        this.length = lengthInUnits;
        if (this.horizontal = horizontal) {
            this.hitbox = new Rectangle(x, y, length * size, size);
        } else {
            this.hitbox = new Rectangle(x, y, size, length * size);
        }
    }
    public SpWall(int x, int y, double length, boolean horizontal) {
        super(x, y, SpGame.WALL_SPRITE);
        this.x = x;
        this.y = y;
        
        this.length = (int) length / size;
        
        if (this.horizontal = horizontal) {
            this.hitbox = new Rectangle(x, y, this.length * size, size);
        } else {
            this.hitbox = new Rectangle(x, y, size, this.length * size);
        }
        
    }

    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < length; i++) {
            if (horizontal) {
                super.paint(g, 0, i * size, 0, false);
            } else {
                super.paint(g, 0, 0, i * size, false);
            }
        }
    }

    @Override
    public Rectangle getHitbox() {
        return this.hitbox;
    }
}
