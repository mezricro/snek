package snek;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

public class Map extends BufferedImage{
    
    private Point spawn;
    
    public Map(int width, int height, Point spawn) {
        super(width, height, BufferedImage.TYPE_INT_RGB);
        this.spawn = spawn;
    }
    
    public Map(int width, int height) {
        this(width, height, new Point(width / 2, height / 2));
    }
    
    /**Returns a {@code Point} array representing the boundaries of the map.
     * Note that this is not the same as the frame of the underlying 
     * {@code BufferedImage}.
     * 
     * @return the frame of the map
     */
    public Point[] getBounds() {
        Point[] temp = new Point[2 * this.getWidth() + 2 * this.getHeight()];
        int i = 0;
        for (int j = 0; j < this.getWidth(); j++) {
            temp[i++] = new Point(j, 0);
            temp[i++] = new Point(j, this.getHeight() - 1);
        }
        for (int j = 0; j < this.getHeight(); j++) {
            temp[i++] = new Point(0, j);
            temp[i++] = new Point(this.getWidth() - 1, j);
        }
        return temp;
    }

    public Point getSpawn() {
        return spawn;
    }
    
    /**Get a random point on the map.
     * 
     * @return a random point from the map
     */
    public Point getRandomPoint() {
        int randomX = ThreadLocalRandom.current().nextInt(getWidth());
        int randomY = ThreadLocalRandom.current().nextInt(getHeight());
        return new Point(randomX, randomY);
    }
    
    /**Get a reasonably reachable random point, meaning it's not too close to the 
     * edges.
     * 
     * @return a perfectly reachable random point from the map
     */
    public Point getReachableRandomPoint() {
        double minX = getWidth() * 0.1;
        double minY = getWidth() * 0.8;
        int randomX = ThreadLocalRandom.current().nextInt((int) minX, (int) minY);
        
        minX = getHeight() * 0.1;
        minY = getHeight() * 0.8;
        int randomY = ThreadLocalRandom.current().nextInt((int) minX, (int) minY);
        return new Point(randomX, randomY);
    }
    
    /**Get a reachable random point, that's not too close to the edges. This
     * method excludes any points contained in the {@code excluded} parameter.
     * 
     * @param excluded points removed from the possible return pool
     * @return a reachable, unobstructed random point from the map
     */
    public Point getReachableRandomPoint(Point[] excluded) {      
        Point p = getReachableRandomPoint();
        for (Point ex : excluded) {
            if (p.equals(ex)) {
                p = getReachableRandomPoint(excluded);
            }
        }
        return p;
    }
}
