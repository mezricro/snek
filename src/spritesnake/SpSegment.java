package spritesnake;

import java.awt.Graphics;
import snek.*;
import java.awt.Point;

/**
 *
 * @author robi
 */
public class SpSegment extends SpSprite {
    
    private Direction direction;

    public SpSegment(int x, int y, Direction direction) {
        super(x, y, loadSprite("body"));
        this.direction = direction;
    }
    
    public SpSegment(Point coordinates, Direction direction) {
        this(coordinates.x, coordinates.y, direction);
    }
    
    public SpSegment(Point coordinates) {
        this(coordinates, null);
    }

    public Direction getDirection() {
        return direction;
    }
    
    public void move() {
//        System.out.println(this.getCoordinates());
//        System.out.println(sprite.getHeight(null));
        this.setCoordinates(new Point(
                x + direction.getX() * size,
                y + direction.getY() * size
        ));
//        System.out.println(this.getCoordinates());
    }
    
    @Override
    public void paint(Graphics g) {
        if (direction.equals(Direction.N) || direction.equals(Direction.S)) {
            super.paint(g, 90);
        } else {
            super.paint(g, 0);
        }
    }
    
    public void setDirection(Direction d) {
        if (!this.direction.isOpposite(d)) {
            this.direction = d;
        }
    }
}
