package gfxsnake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JPanel;

public class GfxMap extends JPanel{
    
    private Wall[] walls;
  
    private Point spawn;
    private final int roundedTo = GfxGame.COMMON_THICCNESS;
    
    URL bgSprite = getClass().getResource("/sprites/background.png");
    Image img = Toolkit.getDefaultToolkit().createImage(bgSprite);
    
    public GfxMap(int width, int height, Point spawn) {
        super();
        Dimension size = new Dimension(width, height);
        walls = initWalls(width, height);
        this.setPreferredSize(size);
        this.spawn = spawn;
        this.setBackground(new Color(15, 15, 25));
    }
    
    public GfxMap(int width, int height) {
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
        int timesX = this.getWidth() / roundedTo;
        int timesY = this.getWidth() / roundedTo;
        
        for (int i = 0; i < timesX; i++) {
            for (int j = 0; j < timesY; j++) {
                g.drawImage(img, i * 16, j * 16, null);
            }
        }
        this.paintWalls(g);
        this.paintSprites(g);
    }
    
    public void paintSprites(Graphics g) {
        
    }
       
    private void paintWalls(Graphics g) {
//        wallAround.paint(g);
        for (Wall w : walls) {
            w.paint(g);
        }
    }
    
    public Wall[] getWalls() {
        return walls;
    }
    
    private Wall[] initWalls(int maxX, int maxY) {
        Wall[] w;
        
        Wall[] boxWall;
        Wall[] otherWalls;
        int thiccness = GfxGame.COMMON_THICCNESS;
        
        boxWall = new Wall[4];
        boxWall[0] = Wall.createLinearWall(0, 0, maxX, thiccness, true); //top
        boxWall[1] = Wall.createLinearWall(0, 0, maxY, thiccness, false); //left
        boxWall[2] = Wall.createLinearWall(maxX - thiccness, 0, maxY, thiccness, false); //right
        boxWall[3] = Wall.createLinearWall(0, maxY - thiccness, maxX, thiccness, true); //bottom
        
        otherWalls = new Wall[2];
        otherWalls[0] = Wall.createLinearWall(288, 0, 160, thiccness, false);
        otherWalls[1] = Wall.createLinearWall(336, maxY - 160, 160, thiccness, false);
        
        w = new Wall[boxWall.length + otherWalls.length];
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
            for (Wall w : walls) {
                if (w.contains(p)) {
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
