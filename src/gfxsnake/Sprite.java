package gfxsnake;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public abstract class Sprite {
    int x;
    int y;
    Image sprite;
    
    public void paint(Graphics g, double rotationAngle) {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform old = g2.getTransform();
        
        AffineTransform tx = new AffineTransform();
        tx.translate(x, y); //Move image into place
        tx.rotate(
                Math.toRadians(rotationAngle), 
                sprite.getWidth(null) / 2, 
                sprite.getHeight(null) / 2
        ); //Rotate around center

        g2.setTransform(tx);
        g2.drawImage(sprite, 0, 0, null); //Draw image - 0,0 --> IMAGE ALREADY IN PLACE
        g2.setTransform(old);
    }
    public void paint(Graphics g) {
        this.paint(g, 0);
    }
    
    public static Image getSprite(String spriteName) {
        String src = "/sprites/" + spriteName + ".png";
        URL spriteSource = Sprite.class.getClass().getResource(src);
        Image sprite = Toolkit.getDefaultToolkit().getImage(spriteSource);
        return sprite;
    }
}
