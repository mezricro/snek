
package spritesnake;

import gfxsnake.*;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import snek.Animation;
import snek.Direction;

public class SpGame {
    
    SpSnake snek;
    SpMap map;
    SpFood food;
    Animation animation;
    SpDisplay display = new SpDisplay();
    Direction inputBuffer = null;
    
    public static final int COMMON_THICCNESS = 16;    
    
    public SpGame() {
        map = new SpMap(640, 480) {
            @Override
            public void paintSprites(Graphics g) {
                snek.paint(g);
                food.paint(g);
            }
        };
        snek = new SpSnake(10, map.getSpawn());
        
        controlsSetup();
        display.setMap(map);
        updateFood(2, 10);
        timerSetup(100);
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
        for (SpWall w : map.getWalls()) {
            collision = snek.isColliding(w.getHitbox());
            if (collision) {
                return true;
            }
        }
        collision = snek.collisionWithSelf();
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
//                        snek.addSegments(toAdd); 
                        break;
                    case KeyEvent.VK_SPACE:
                        if(animation.isRunning()) {
                            animation.end();
                        } else {
                            animation.start();
                        }
                        break;
                    case KeyEvent.VK_R: 
                        snek = new SpSnake(10, map.getSpawn());
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
                        food = new SpFood(map.getReachableRandomPoint(snek.getBounds()), 10);
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
            food = new SpFood(map.getReachableRandomPoint(snek.getBounds()), pointsValue);
        } else {
            if (snek.isColliding(food.getHitbox())) {
                food = new SpFood(map.getReachableRandomPoint(snek.getBounds()), pointsValue);
                snek.addSegments(segmentValue);
            }
        }
    }
    
    public static void main(String[] args) {
        SpGame g = new SpGame();
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                g.display.setVisible(true);
                g.animation.start();
            }
        });
    }
}
