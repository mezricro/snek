
package snek;

import java.awt.Point;

public class Food {
    
    private int x;
    private int y;
    private int value;

    public Food(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }
    
    public Point getCoordinates() {
        return new Point(this.x,this.y);
    }
}
