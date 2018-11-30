
package snek;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

public class Food {
    
    private int x;
    private int y;
    private int value;

    public Food(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }
    
    public Food(Point p, int value) {
        this(p.x, p.y, value);
    }
    
    public Point getCoordinates() {
        return new Point(this.x,this.y);
    }
    
    //Egy hiba, de azért itt hagyom
    @Deprecated
    public Food spawnFoodOnRandom(Point[] availableSpace, int value) {
        int ceiling = availableSpace.length;
        int spawnPointAddr = ThreadLocalRandom.current().nextInt(ceiling);
        Point spawnPoint = availableSpace[spawnPointAddr];
        return new Food(spawnPoint, value);
    }
}
