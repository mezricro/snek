package spritesnake;

import java.awt.Graphics;
import javax.swing.JFrame;

public class SpDisplay extends JFrame{

    public SpDisplay(SpMap map) {
        initFrame();
        setMap(map);
    }    
    
    public SpDisplay() {
        initFrame();
    }
    
    public final void setMap(SpMap m) {
        this.setContentPane(m);
        pack();
        setLocationRelativeTo(null);
    }
 
    private void initFrame() {
        setTitle("Snek");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setResizable(false);
    }
}