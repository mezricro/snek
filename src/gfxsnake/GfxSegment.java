package gfxsnake;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import snek.*;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author robi
 */
public class GfxSegment extends Sprite{
    private int x;
    private int y;
    
    private int sizeX = GfxGame.COMMON_THICCNESS;
    private int sizeY = GfxGame.COMMON_THICCNESS;
    
    Image body = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/sprites/body.png"));
    
    private Direction direction;

    public GfxSegment(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    
    public GfxSegment(Point coordinates, Direction direction) {
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
    
    public void move() {
        this.setCoordinates(new Point(
                x + direction.getX() * sizeX,
                y + direction.getY() * sizeY
        ));
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform reset = g2.getTransform();
        
        Direction d = this.getDirection();
        if (d.equals(Direction.N) || d.equals(Direction.S)) {
            double rotateDegrees = Math.toRadians(90);
            AffineTransform tx = AffineTransform.getRotateInstance(rotateDegrees, this.x, this.y);
            g2.setTransform(tx);
            g2.drawImage(body, x, y - 16, null);
        } else {
            g2.drawImage(body, x, y, null);
        }
        
        g2.setTransform(reset);
        
//        g.fillRect(p.x, p.y, sizeX, sizeY);
//        g.setColor(Color.BLACK);
//        g.drawRect(p.x, p.y, sizeX, sizeY);
    }
    
    public void setDirection(Direction d) {
        if (!this.direction.isOpposite(d)) {
            this.direction = d;
        }
    }
}
