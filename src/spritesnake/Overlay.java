
package spritesnake;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Overlay extends JPanel {

    public Overlay(int w, int h) {
        super();
        Dimension d = new Dimension(w, h);
        this.setPreferredSize(d);
        this.setOpaque(false);
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.paintSprites(g);
    }
    
    public void paintSprites(Graphics g) {
        
    }
    
    public void refresh() {
        this.repaint();
    }
    
}
