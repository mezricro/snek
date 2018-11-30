package snek;

import java.awt.Point;

/**
 *
 * @author robi
 */
public class Segment {
    private int x;
    private int y;
    private Direction direction;

    public Segment(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    
    public Segment(Point coordinates, Direction direction) {
        this(coordinates.x, coordinates.y, direction);
    }
    
    public Point getCoordinates() {
        return new Point(this.x, this.y);
    }

    public void setCoordinates(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public Direction getDirection() {
        return direction;
    }
    
    public void setDirection(Direction d) {
        if (!this.direction.isOpposite(d)) {
            this.direction = d;
        }
    }
}
