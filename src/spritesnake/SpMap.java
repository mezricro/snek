package spritesnake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JPanel;

public class SpMap extends JPanel{
    
    private SpWall[] walls;
  
    private Point spawn;
    private final int roundedTo = 16;

    public SpMap(int width, int height, Point spawn) {
        super();
        Dimension size = new Dimension(width, height);
        walls = initWalls(width, height);
        this.setPreferredSize(size);
        this.spawn = spawn;
        this.setBackground(new Color(15, 15, 25));
    }
    
    public SpMap(int width, int height) {
        this(width, height, null);
        this.setSpawn(
                roundDownTo(width / 2, roundedTo), 
                roundDownTo(height / 2, roundedTo)
        );
    }

    public Point getSpawn() {
        return spawn;
    }
    
    public void setSpawn(int x, int y) {
        this.spawn = new Point(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.paintWalls(g);
        this.paintSprites(g);
    }
    
    public void paintSprites(Graphics g) {
        
    }
       
    private void paintWalls(Graphics g) {
//        wallAround.paint(g);
        for (SpWall w : walls) {
            w.paint(g);
        }
    }
    
    public SpWall[] getWalls() {
        return walls;
    }
    
    private SpWall[] initWalls(int maxX, int maxY) {
        SpWall[] w;
        
        SpWall[] boxWall;
        SpWall[] otherWalls;
        
        int spritesize = 16;
        
        boxWall = new SpWall[4];
        boxWall[0] = new SpWall(0, 0, maxX / spritesize, true); // top
        boxWall[1] = new SpWall(0, 0, maxY / spritesize, false); //left
        boxWall[2] = new SpWall(maxX - spritesize, 0, maxY / spritesize, false); //right
        boxWall[3] = new SpWall(0, maxY - spritesize, maxX / spritesize, true); //bottom
        
        otherWalls = new SpWall[4];
        otherWalls[0] = new SpWall(maxX / 2 - spritesize, 0, 8, false);
        otherWalls[1] = new SpWall(maxX / 2 - 2 * spritesize, 0, 8, false);
        otherWalls[2] = new SpWall(maxX / 2 + spritesize, maxY - 8 * spritesize, 8, false);
        otherWalls[3] = new SpWall(maxX / 2 + 2 * spritesize, maxY - 8 * spritesize, 8, false);
        
        w = new SpWall[boxWall.length + otherWalls.length];
        System.arraycopy(boxWall, 0, w, 0, boxWall.length);
        System.arraycopy(otherWalls, 0, w, boxWall.length, otherWalls.length);
        return w;
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
        Dimension size = this.getPreferredSize();
        
        double minX = size.width * 0.1;
        double minY = size.width * 0.8;
        int randomX = ThreadLocalRandom.current().nextInt((int) minX, (int) minY);
        randomX = roundDownTo(randomX, roundedTo); //Round down to common unit
        
        minX = size.height * 0.1;
        minY = size.height * 0.8;
        int randomY = ThreadLocalRandom.current().nextInt((int) minX, (int) minY);
        randomY = roundDownTo(randomY, roundedTo); //Round down to common unit
        
        Point p = new Point(randomX, randomY);
        if (walls != null) {
            for (SpWall w : walls) {
                if (w.getHitbox().contains(p)) {
                    getReachableRandomPoint();
                }
            }
        }
        return p;
    }
    
    int roundDownTo(int number, int roundTo) {
        return number - number % roundTo;
    }
    
    /**Get a reachable random point, that's not too close to the edges. This
     * method excludes any points contained in the {@code excluded} parameter.
     * 
     * @param excluded points removed from the possible return pool
     * @return a reachable, unobstructed random point from the map
     */
    public Point getReachableRandomPoint(Rectangle[] excluded) {      
        Point p = getReachableRandomPoint();
        for (Rectangle r : excluded) {
            if (r.contains(p)) {
                p = getReachableRandomPoint();
            }
        }
        for (SpWall wall : walls) {
            if (wall.getHitbox().contains(p)) {
                p = getReachableRandomPoint();
            }
        }
        return p;
    }
}
