package gfxsnake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Wall extends Sprite{
    
    int x, y, height, width;
    int thicc;
    private Color color = new Color(230, 230, 230);
    
    Image img = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/sprites/wall.png"));
    
    public Wall(int x, int y, int height, int width, int thiccness) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.thicc = thiccness;
    }
    
    public Wall(int height, int width, int thiccness) {
        this(0, 0, height, width, thiccness);
    }
    
    public Wall(Dimension d, int thiccness) {
        this(d.height, d.width, thiccness);
    }
    
    public Wall(int thiccness) {
        this(0, 0, thiccness);
    }
    
    public static Wall createLinearWall(int x, int y, int length, int thiccness, boolean horizontal) {
        Wall w = new Wall(thiccness);
        w.setLocation(x, y);
        if (horizontal) {
            w.setSize(length, thiccness);
        } else {
            w.setSize(thiccness, length);
        }
        return w;
    }
    
    private void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    private void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public boolean contains(Point p) {
        return new Rectangle(x, y, width, height).contains(p);
    }
    
    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);
    }
    
    public void setColor(Color c) {
        this.color = c;
    }
    
    @Override
    public void paint(Graphics g) {
        int timesX = width / thicc;
        int timesY = height / thicc;
        
//        g.setColor(color);
//        g.fillRect(x, y, width, height); 
        for (int i = 0; i < timesX; i++) {
            for (int j = 0; j < timesY; j++) {
                g.drawImage(img, x + (i * 16), y + (j * 16), null);
            }
        }
        g.drawImage(img, x, y, null);
    }
    
}
