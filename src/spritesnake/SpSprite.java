package spritesnake;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;

public abstract class SpSprite {
    
    int x;
    int y;
    int size;
    BufferedImage sprite;

    public SpSprite(int x, int y, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.size = sprite.getHeight();
    }
    
    public void paint(Graphics g, double rotationAngle, int offsetX, int offsetY) {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform old = g2.getTransform();
        
        AffineTransform tx = new AffineTransform();
        tx.translate(x + offsetX, y + offsetY); //Move image into place
        tx.rotate(
                Math.toRadians(rotationAngle), 
                sprite.getWidth(null) / 2, 
                sprite.getHeight(null) / 2
        ); //Rotate around center

        g2.setTransform(tx);
        g2.drawImage(sprite, 0, 0, null); //Draw image - 0,0 --> IMAGE ALREADY IN PLACE
        g2.setTransform(old);
    }
    public void paint(Graphics g, int unitOffsetX, int unitOffsetY) {
        this.paint(g, 0, unitOffsetX * size, unitOffsetY * size);
    }
    public void paint(Graphics g, double rotationAngle) {
        this.paint(g, rotationAngle, 0, 0);
    }
    public void paint(Graphics g) {
        this.paint(g, 0);
    }
    
    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }
    
    public Point getCoordinates() {
        return new Point(this.x, this.y);
    }

    public void setCoordinates(Point p) {
        this.x = p.x;
        this.y = p.y;
    }
    
    public static BufferedImage loadSprite(String spriteName) {
        String src = "/sprites/" + spriteName + ".png";
        URL spriteSource = SpSprite.class.getClass().getResource(src);
//        Image sprite = Toolkit.getDefaultToolkit().getImage(spriteSource);
        try {
            BufferedImage sprite = ImageIO.read(spriteSource);
            return sprite;
        } catch (Exception e) {
            System.out.println("Sprite " + spriteName + " not found!");
            return null;
        }
    }
    
    public Rectangle getHitbox() {
        return new Rectangle(x, y, size, size);
    }
}
