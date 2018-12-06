package gfxsnake;

import snek.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.Arrays;

public class GfxSnake extends Sprite{
    
    private GfxSegment[] segments;
    private final GfxSegment head;
    
    Image headSprite = getSprite("head");

    /**Construct a new snake with a given number of {@code Segment}s. 
     * 
     * 
     * @param segmentNumber initial number of {@code Segments} in the snake
     * <br>
     * @param spawn the offset for the {@code x} and {@code y} values for
     *              each {@link Segment}
     */
    public GfxSnake(int segmentNumber, Point spawn) {
        System.out.println(getClass().getResource("/sprites/head.png"));
        segments = new GfxSegment[segmentNumber];
        
        segments[0] = new GfxSegment(spawn, Direction.N){
            @Override
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                AffineTransform old = g2.getTransform();
                Point p = getCoordinates();
                
                double angle = 0;
                switch (getDirection()) {
                    case N: angle = -90; break;
                    case S: angle = +90; break;
                    case E: angle = 0; break;
                    case W: angle = 180; break;
                }
                angle = Math.toRadians(angle);
                
                AffineTransform tx = new AffineTransform();
                tx.translate(p.x, p.y); //Move image into place
                tx.rotate(angle, 8, 8); //Rotate around center
                
                g2.setTransform(tx);
                g2.drawImage(headSprite, 0, 0, null); //Draw image - 0,0 --> IMAGE ALREADY IN PLACE
                g2.setTransform(old);
            }
        };
        
        
        for (int i = 1; i < segmentNumber; i++) {
            segments[i] = new GfxSegment(spawn.x, spawn.y, Direction.N);
        }
        head = segments[0];
    }
    
    /**Return the coordinates of the entire snake
     * 
     * @return A point array of every segment found in the snake.
     */
    public Point[] getCoordinates() {
        Point[] temp = new Point[segments.length];
        for (int i = 0; i < segments.length; i++) {
            temp[i] = segments[i].getCoordinates();
        }
        return temp;
    }
    
    /**Return the coordinates of the snake's body
     * 
     * @return A point array of every segment, except for the head.
     */
    public Point[] getBodyCoordinates() {
        Point[] temp = new Point[segments.length - 1];
        for (int i = 1; i < segments.length; i++) {
            temp[i - 1] = segments[i].getCoordinates();
        }
        return temp;
    }
    
    //hátulról vagy kettővel szereted?
    //hátulról:
    /**Starting from the back, move every {@code Segment} to the position of the one
     * ahead of it. Afterwards, move the head based on its direction.
     */
    public void moveFromBack() {
        for (int i = segments.length-1; i > 0; i--) {
            segments[i].setCoordinates(segments[i - 1].getCoordinates());
            segments[i].setDirection(segments[i - 1].getDirection());
        }
        head.move();
    }
    
    //kettővel
    @Deprecated
    public void moveFromHead() {
        //két ideiglenes változóval:
    }

    @Override
    public void paint(Graphics g) {
        for (GfxSegment s : segments) {
            s.paint(g);
        }
    }
    
    /**Add a {@code count} number of {@code Segments} to the end of the snake,
     * with the same direction and coordinates as the last segment.
     * 
     * @param count the number of segments to add
     */
    public void addSegments(int count) {
        for (int i = 0; i < count; i++) {
            int oldLen = segments.length;
            segments = Arrays.copyOf(segments, oldLen + 1);
            
            GfxSegment lastSegment = segments[oldLen - 1];
            segments[oldLen] = new GfxSegment(
                    lastSegment.getCoordinates(), lastSegment.getDirection()
            );
        }
    }
    
    /**Check whether the head is located at a given point.
     * 
     * @param coords coordinates to check against
     * @return whether the head is on the given {@code Point}
     */
    public boolean collides(Point coords) {
        Point headCoords = head.getCoordinates();
        return headCoords.equals(coords);
    }
    
    /**Check whether or not the head can be found in a given set of coordinates.
     * 
     * @param coords Array of points to check against
     * @return true if any of the points' {@code x} and {@code y} values
     *         match the coordinates of the {@code head}
     */
    public boolean collides(Point[] coords) {
        for (Point c : coords) {
            if (collides(c)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean collides(Rectangle hitbox) {
        Point p = head.getCoordinates();
        return hitbox.contains(p);
    }
    
    @Deprecated
    public boolean inBounds(Rectangle bounds) {
        Point p = head.getCoordinates();
        return p.x < 0 || p.y < 0 ||
               p.x > bounds.width - 1 || p.y > bounds.height - 1;
    }
    
    @Deprecated
    public Direction getDirection() {
        return head.getDirection();
    }
    
    public void changeDirection(Direction d) {
        if (d != null) {
            head.setDirection(d);
        }
    }
}
