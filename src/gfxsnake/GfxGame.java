
package gfxsnake;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import snek.Animation;
import snek.Direction;


//Create graphics for
//food
//head
//body
//tail
//environment


public class GfxGame {
    
    GfxSnake snek;
    GfxMap map;
    GfxFood food;
    Animation animation;
    GfxDisplay display = new GfxDisplay();
    Direction inputBuffer = null;
    
    public static final int COMMON_THICCNESS = 16;    
    
    public GfxGame() {
        map = new GfxMap(640, 480) {
            @Override
            public void paintSprites(Graphics g) {
                snek.paint(g);
                food.paint(g);
            }
        };
        snek = new GfxSnake(5, map.getSpawn());
        timerSetup(100);
        
        controlsSetup();
        display.setMap(map);
        updateFood(2, 10);
    }
    
    private void updateGameState() {
        snek.changeDirection(inputBuffer);
        inputBuffer = null;
        
        snek.moveFromBack();
        
        if (isColliding()) {
            System.out.println("Collision!");
            animation.end();
        }
        updateFood(2, 10);
    }
    
    //Check collision w/ map bounds, walls, self
    private boolean isColliding() {
        boolean collision;
        for (Wall w : map.getWalls()) {
            collision = snek.collides(w.getHitbox());
            if (collision) {
                return true;
            }
        }
        collision = snek.collides(snek.getBodyCoordinates());
        return collision;
    }
    
    //Create an animation to update 
    private void timerSetup(int cycleTime) {
        animation = new Animation(cycleTime) {
            @Override
            public void animate() {
                updateGameState();
                map.repaint();
            }
        };
    }
    
    private void controlsSetup() {
        map.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT: inputBuffer = Direction.W; break;
                    case KeyEvent.VK_RIGHT: inputBuffer = Direction.E; break;
                    case KeyEvent.VK_UP: inputBuffer = Direction.N; break;
                    case KeyEvent.VK_DOWN: inputBuffer = Direction.S; break;
                    case KeyEvent.VK_A: 
                        int toAdd = 1;
                        System.out.println("DEBUG - " + toAdd + " segment(s) added");
                        snek.addSegments(toAdd); 
                        break;
                    case KeyEvent.VK_SPACE:
                        if(animation.isRunning()) {
                            animation.end();
                        } else {
                            animation.start();
                        }
                        break;
                    case KeyEvent.VK_R: 
                        snek = new GfxSnake(10, map.getSpawn());
                        animation.start();
//                        timer.start();
                        break;
                    case KeyEvent.VK_ADD:
                        System.out.println("DEBUG - Cycle time increased");
                        animation.setCycleTime(animation.getCycleTime() + 50);
                        break;
                    case KeyEvent.VK_SUBTRACT:
                        System.out.println("DEBUG - Cycle time decreased");
                        animation.setCycleTime(animation.getCycleTime() - 50);
                        break;
                    case KeyEvent.VK_F:
                        food = new GfxFood(map.getReachableRandomPoint(snek.getCoordinates()), 10);
                        break;
                }
            }
        });
        map.setFocusable(true);
        map.requestFocus();
    }
    
    //Create food if there's none available, respawn food if eaten
    private void updateFood(int segmentValue, int pointsValue) {
        if (food == null) {
            //Exclude the snake's points for the spawn pool
            food = new GfxFood(map.getReachableRandomPoint(snek.getCoordinates()), pointsValue);
        } else {
            if (snek.collides(food.getCoordinates())) {
                food = new GfxFood(map.getReachableRandomPoint(snek.getCoordinates()), pointsValue);
                snek.addSegments(segmentValue);
            }
        }
    }
    
    public static void main(String[] args) {
        GfxGame g = new GfxGame();
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                g.display.setVisible(true);
                g.animation.start();
            }
        });
    }
}
