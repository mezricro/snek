package spritesnake;

import java.awt.Graphics;
import snek.Direction;
import java.awt.Point;

/**
 *
 * @author robi
 */
public class SpSegment extends SpSprite {
    
    private Direction direction;

    public SpSegment(int x, int y, Direction direction) {
        super(x, y, SpGame.BODY_SPRITE);
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
    
    public void move(int increment) {
        this.setCoordinates(new Point(
                x + direction.getX() * increment,
                y + direction.getY() * increment
        ));
    }
    public void move() {
        this.move(size);
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
