package snek;

import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Display extends JFrame{

    BufferedImage imgToDisplay;
    
    public Display() {
        initFrame();
        initComponents();
    }
    
    private void initFrame() {
        setTitle("Snek");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(640, 480);
        setLocationRelativeTo(null);
    }
    
    JLabel disp = new JLabel();
    private void initComponents() {
        JPanel p = (JPanel) this.getContentPane();
        p.add(disp);   
        disp.setFocusable(true);
        disp.requestFocus();   
        
    }
    
    public void updateDisplay(Image gameState) {
        disp.setIcon(new ImageIcon(gameState));
        disp.revalidate();
    }
    
}
