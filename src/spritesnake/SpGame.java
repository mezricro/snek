
package spritesnake;

import gfxsnake.Sprite;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import snek.Animation;
import snek.Direction;

public class SpGame {
    
    public static final BufferedImage HEAD_SPRITE = SpSprite.loadSprite("head", 1);
    public static final BufferedImage BODY_SPRITE = SpSprite.loadSprite("body", 1);
    public static final BufferedImage TAIL_SPRITE = SpSprite.loadSprite("tail", 1);
    public static final BufferedImage FOOD_SPRITE = SpSprite.loadSprite("alma", 1);
    public static final BufferedImage WALL_SPRITE = SpSprite.loadSprite("wall", 1);
    
    SpSnake snek;
    SpMap map;
    Overlay overlay;
    SpFood food;
    Animation animation;
    SpDisplay display;
    Direction inputBuffer = null;
    
    public SpGame(int mapX, int mapY, ArrayList<SpWall> walls) {
        map = new SpMap(mapX, mapY);
        map.setWalls(walls);
        overlay = new Overlay(mapX, mapY) {
            @Override
            public void paintSprites(Graphics g) {
                snek.paint(g);
                food.paint(g);
            }
        };
        
        snek = new SpSnake(10, map.getSpawn());
        
        controlsSetup();
        display = new SpDisplay();
        display.setMap(map);
        display.add(overlay);
        
        updateFood(2, 10);
        timerSetup(110);
    }

    public SpGame() {
        this(640, 480, SpGame.initWalls(640, 480));
    }
    
    private void updateGameState() {
        snek.changeDirection(inputBuffer);
        inputBuffer = null;
        snek.moveFromBack();
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
                if (!isColliding()) {
                    overlay.repaint();
                } else {
                    System.out.println("Collision!");
                    animation.end();
                }
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
    
    public static ArrayList<SpWall> initWalls(int maxX, int maxY) {
        ArrayList<SpWall> resultWalls;
        
        ArrayList<SpWall> outerWalls;
        ArrayList<SpWall> otherWalls;
        
        int spritesize = 16;
        
        outerWalls = new ArrayList<>(4);
        outerWalls.add(0, new SpWall(0, 0, maxX / spritesize, true)); // top
        outerWalls.add(1, new SpWall(0, 0, maxY / spritesize, false)); //left
        outerWalls.add(2, new SpWall(maxX - spritesize, 0, maxY / spritesize, false)); //right
        outerWalls.add(3, new SpWall(0, maxY - spritesize, maxX / spritesize, true)); //bottom
        
        otherWalls = new ArrayList<>(4);
        otherWalls.add(0, new SpWall(maxX / 2 - spritesize, 0, 8, false));
        otherWalls.add(1, new SpWall(maxX / 2 - 2 * spritesize, 0, 8, false));
        otherWalls.add(2, new SpWall(maxX / 2 + spritesize, maxY - 8 * spritesize, 8, false));
        otherWalls.add(3, new SpWall(maxX / 2 + 2 * spritesize, maxY - 8 * spritesize, 8, false));
        
        resultWalls = new ArrayList<SpWall>(outerWalls.size() + otherWalls.size());
        for (SpWall wall : outerWalls) {
            resultWalls.add(wall);
        }
        
        for (SpWall wall : otherWalls) {
            resultWalls.add(wall);
        }
        
//        System.arraycopy(outerWalls, 0, resultWalls, 0, outerWalls.size());
//        System.arraycopy(otherWalls, 0, resultWalls, outerWalls.size(), otherWalls.size());
        
        return resultWalls;
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
        SpGame g = new SpGame(640, 480, MapInterpreter.detectWalls(MapInterpreter.getMapMatrix(MapInterpreter.testMap)));

        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                g.display.setVisible(true);
                g.animation.start();
            }
        });
    }
}
