package snek;

import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    
    public void leftArrow() {
        System.out.println("does nothing");
    }
    public void rightArrow() {
        System.out.println("does nothing");
    }
    public void upArrow() {
        System.out.println("does nothing");
    }
    public void downArrow() {
        System.out.println("does nothing");
    }
    
    public void updateDisplay(Image gameState) {
        disp.setIcon(new ImageIcon(gameState));
        disp.revalidate();
    }
    
}
