package spritesnake;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
    
    public void paint(Graphics g, double rotationAngle, int offsetX, int offsetY, boolean mirror) {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform old = g2.getTransform();
        
        AffineTransform tx = new AffineTransform();
        tx.translate(x + offsetX, y + offsetY); //Move image into place
        if (rotationAngle != 0) {
            tx.rotate(
                Math.toRadians(rotationAngle), 
                sprite.getWidth(null) / 2, 
                sprite.getHeight(null) / 2
            ); //Rotate around center
        }
        
        g2.setTransform(tx);
        if (mirror) {
            g2.drawImage(mirror(sprite, true), 0, 0, null);
        } else {
            g2.drawImage(sprite, 0, 0, null); //Draw image - 0,0 --> IMAGE ALREADY IN PLACE
        }
        g2.setTransform(old);
    }
    public void paint(Graphics g, double rotationAngle) {
        this.paint(g, rotationAngle, 0, 0, false);
    }
    public void paint(Graphics g) {
        this.paint(g, 0, 0, 0, false);
    }
    
    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
        this.size = sprite.getHeight();
    }
    
    private BufferedImage mirror(BufferedImage source, boolean vertical) {
        BufferedImage mirror = new BufferedImage(
                source.getWidth(), 
                source.getHeight(), 
                BufferedImage.TYPE_INT_ARGB
        );
        
        int w = source.getWidth();
        int h = source.getHeight();

        for (int i = 0; i < w ; i++) {
            for (int j = 0; j < h; j++) {
                if (vertical) {
                    
                    //Mirror vertically
                    mirror.setRGB(i, j, source.getRGB(w-1 - i, j));
                } else {
                    
                    //Mirror horizontally
                    mirror.setRGB(i, j, source.getRGB(i, h-1 - j));
                }
                
            }
        }

        return mirror;
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
        System.out.println(spriteSource);
        try {
            BufferedImage sprite = ImageIO.read(spriteSource);
            return sprite;
        } catch (IOException e) {
            System.out.println("Sprite '" + spriteName + "' not found!");
            e.printStackTrace();
            return null;
        }
    }
    
    public static BufferedImage loadSprite(String spriteName, int scale) {
        BufferedImage sprite = loadSprite(spriteName);
        
        if (scale == 1) {
            return sprite;
        } else {
            BufferedImage scaledSprite = new BufferedImage(
                sprite.getWidth() * scale, 
                sprite.getHeight() * scale, 
                BufferedImage.TYPE_INT_ARGB
            );

            int w = sprite.getWidth() * scale;
            int h = sprite.getHeight() * scale;


            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    scaledSprite.setRGB(i, j, sprite.getRGB(i / scale, j / scale));
                }
            }

            return scaledSprite;
        }
    }
    
    public Rectangle getHitbox() {
        return new Rectangle(x, y, size, size);
    }
}
