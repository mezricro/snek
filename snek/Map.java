package snek;

import java.awt.Point;
import java.awt.image.BufferedImage;

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
    
    
}
