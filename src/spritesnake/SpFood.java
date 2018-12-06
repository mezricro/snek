
package spritesnake;

import java.awt.Point;

public class SpFood extends SpSprite {
    
    int value;
    
    public SpFood(int x, int y, int value) {
        super(x, y, SpSprite.loadSprite("alma"));
        this.value = value;
    }
    
    public SpFood(Point p, int value) {
        this(p.x, p.y, value);
    }
}
