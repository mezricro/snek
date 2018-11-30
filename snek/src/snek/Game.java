package snek;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.Timer;


public class Game {
    
    Snake snek;
    Map map;
    Display display = new Display();
    Timer timer;
    Direction inputBuffer = null;

    public Game() {        
        map = new Map(50, 50);        
        snek = new Snake(10, map.getSpawn());
        timerSetup(300);
        controlsSetup();
    }
    
    //Generate a new BufferedImage from an existing one
    private BufferedImage deepCopy(BufferedImage source) {
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }
    
    /**Updating the game state means:<br>
     * <ol>
     * <li>Turning snake based on the buffered input, then clearing the {@code inputBuffer}</li>
     * <li>Making sure the snake doesn't collide with itself or the map boundaries</li>
     * <li>Moving the snake</li>
     * </ol>
     */
    private void updateGamestate() {
        if (inputBuffer == null) {
            inputBuffer = snek.getDirection();
        }
        snek.changeDirection(inputBuffer);
        inputBuffer = null;
        
        if (!snek.collides(snek.getBodyCoordinates()) && 
            !snek.collides(map.getBounds())) {
            
            snek.moveFromBack();
        } else {
            timer.stop();
            System.out.println("Collision!");
        }
        
    }  
    private Image getGameState() {
       BufferedImage gameState = deepCopy(map);
       
       boolean isHead = true;
       for(Point px : snek.getCoordinates()) {
           if (isHead) {
                gameState.setRGB(px.x, px.y, Color.GREEN.getRGB());
                isHead = false;
           } else {
                gameState.setRGB(px.x, px.y, Color.WHITE.getRGB());   
           }
       }
        
       return getScaled(gameState, 15);
    };
    
    private Image getScaled(BufferedImage img, int scale) {
        return img.getScaledInstance(img.getWidth() * scale, img.getHeight() * scale, Image.SCALE_FAST);
    }
    
    
    private void timerSetup(int delay) {
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGamestate();
                display.updateDisplay(getGameState());
            }
        });
        timer.start();
    }
    
    /**Pressing an arrow key feeds input to the {@code inputBuffer}, which is used
     * to update the snake's direction during each animation cycle.
     * 
     * Controls:<br>
     * Arrow keys: turn snake<br>
     * R: restart game<br>
     * <br>
     * Debug:<br>
     * SPACE - pause<br>
     * A - add segment<br>
     */
    private void controlsSetup() {
        display.disp.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT: inputBuffer = Direction.W; break;
                    case KeyEvent.VK_RIGHT: inputBuffer = Direction.E; break;
                    case KeyEvent.VK_UP: inputBuffer = Direction.N; break;
                    case KeyEvent.VK_DOWN: inputBuffer = Direction.S; break;
                    case KeyEvent.VK_A: snek.addSegment(1); break;
                    case KeyEvent.VK_SPACE:
                        if (timer.isRunning()) {
                            timer.stop();
                        } else {
                            timer.start();
                        }
                        break;
                    case KeyEvent.VK_R: 
                        snek = new Snake(10, map.getSpawn());
                        timer.start();
                        break;
                }
            }
        });
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game g = new Game();
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                g.display.updateDisplay(g.getGameState());
                g.display.setVisible(true);
                g.display.pack();
                g.display.setLocationRelativeTo(null);
            }
        });
    }
    
}
