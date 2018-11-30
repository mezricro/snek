package snek;

import java.awt.Point;
import java.util.Arrays;

public class Snake {
    
    private Segment[] segments;
    private final Segment head;

    /**Construct a new snake with a given number of {@code Segment}s. 
     * 
     * 
     * @param segmentNumber initial number of {@code Segments} in the snake
     * <br>
     * @param spawn the offset for the {@code x} and {@code y} values for
     *              each {@link Segment}
     */
    public Snake(int segmentNumber, Point spawn) {
        segments = new Segment[segmentNumber];
        for (int i = 0; i < segmentNumber; i++) {
            segments[i] = new Segment(spawn.x, spawn.y + i, Direction.N);
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
            segments[i].setCoordinates(segments[i-1].getCoordinates());
        }
        head.setCoordinates(new Point(
                head.getCoordinates().x + head.getDirection().getX(),
                head.getCoordinates().y + head.getDirection().getY())
        );
    }
    
    //kettővel
    public void moveFromHead() {
        //két ideiglenes változóval:
    }
    
    /**Add a {@code count} number of {@code Segments} to the end of the snake,
     * with the same direction as the last segment.
     * 
     * @param count the number of segments to add
     */
    public void addSegment(int count) {
        for (int i = 0; i < count; i++) {
            int oldLen = segments.length;
            segments = Arrays.copyOf(segments, oldLen + 1);
            
            Segment lastSegment = segments[oldLen - 1];
            segments[oldLen] = new Segment(lastSegment.getCoordinates(), lastSegment.getDirection());
        }
    }
    
    /**Check whether or not the head can be found in a given set of coordinates.
     * 
     * @param coords Array of points to check against
     * @return true if any of the points' {@code x} and {@code y} values
     *         match the coordinates of the {@code head}
     */
    public boolean collides(Point[] coords) {
        Point headCoords = head.getCoordinates();
        for (Point c : coords) {
            if (headCoords.equals(c)) {
                return true;
            }
        }
        return false;
    }
    
    
    public Direction getDirection() {
        return head.getDirection();
    }
    public void changeDirection(Direction d) {
        head.setDirection(d);
    }
}
