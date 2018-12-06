
package gfxsnake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.URL;

public class GfxFood extends Sprite {
    
    private int value;
    private final int size = GfxGame.COMMON_THICCNESS;
    
    int x;
    int y;
    
    URL sprite = getClass().getResource("/sprites/alma.png");
    Image img = Toolkit.getDefaultToolkit().createImage(sprite);
    
    public GfxFood(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }
    
    public GfxFood(Point p, int value) {
        this(p.x, p.y, value);
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(img, x, y, null);
    }
    
    public Point getCoordinates() {
        return new Point(this.x,this.y);
    }
}
