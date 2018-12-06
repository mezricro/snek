package gfxsnake;

import java.awt.Graphics;
import javax.swing.JFrame;

public class GfxDisplay extends JFrame{

    public GfxDisplay(GfxMap map) {
        initFrame();
        setMap(map);
    }    
    
    public GfxDisplay() {
        initFrame();
    }
    
    public final void setMap(GfxMap m) {
        this.setContentPane(m);
        pack();
        setLocationRelativeTo(null);
    }
    
    /**Override this method to paint sprites on the map
     * 
     * @param g 
     */
    public void paintOnMap(Graphics g) {
        
    }
    
    private void initFrame() {
        setTitle("Snek");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setResizable(false);
    }
}