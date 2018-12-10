package spritesnake;

import java.awt.Color;
import java.awt.Graphics;
import snek.*;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;

public class SpSnake {
    
    private SpSegment[] segments;
    private final SpSegment head;
    
    public SpSnake(int segmentCount, Point spawn) {
        segments = new SpSegment[segmentCount];
        segments[0] = head = initHead(spawn);
        
        int size = head.size;
        System.out.println(size);
        for (int i = 1; i < segmentCount - 1; i++) {
            segments[i] = new SpSegment(spawn.x, spawn.y, Direction.N);
        }
        
        segments[segmentCount - 1] = initTail(spawn);
    }
    
    private SpSegment initHead(Point spawn) {
        SpSegment s = new SpSegment(spawn.x, spawn.y, Direction.N) {
            @Override
            public void paint(Graphics g) {                
                double headTurn = 0;
                switch(getDirection()) {
                    case N: headTurn = -90; break;
                    case S: headTurn = +90; break;
                    case E: headTurn = 0; break;
                    case W: headTurn = 180; break;
                }
                super.paint(g, headTurn);
            }
        };
        s.setSprite(SpGame.HEAD_SPRITE);
        return s;
    }
    
    private SpSegment initTail(Point spawn) {
        SpSegment s = new SpSegment(spawn.x + head.size, spawn.y, Direction.N) {
            @Override
            public void paint(Graphics g) {
                double tailTurn = 0;
                switch(getDirection()) {
                    case N: tailTurn = +90; break;
                    case S: tailTurn = -90; break;
                    case E: tailTurn = 180; break;
                    case W: tailTurn = 0; break;
                }
                super.paint(g, tailTurn);
            }
        };
        s.setSprite(SpGame.TAIL_SPRITE);
        return s;
    }
    private SpSegment initTail(SpSegment secondToLast) {
        SpSegment s = new SpSegment(secondToLast.getCoordinates(), secondToLast.getDirection()) {
            @Override
            public void paint(Graphics g) {
                double headTurn = 0;
                switch(getDirection()) {
                    case N: headTurn = +90; break;
                    case S: headTurn = -90; break;
                    case E: headTurn = 180; break;
                    case W: headTurn = 0; break;
                }
                super.paint(g, headTurn);
            }
        };
        s.setSprite(SpGame.TAIL_SPRITE);
        return s;
    }
    
    public Rectangle[] getBounds() {
        Rectangle[] hitbox = new Rectangle[segments.length];
        for (int i = 0; i < segments.length; i++) {
            hitbox[i] = segments[i].getHitbox();
        }
        return hitbox;
    }
    
    private Rectangle[] getBody() {
        Rectangle[] hitbox = new Rectangle[segments.length - 1];
        for (int i = 1; i < segments.length; i++) {
            hitbox[i - 1] = segments[i].getHitbox();
        }
        return hitbox;
    }
    
    public Rectangle getHitbox() {
        return head.getHitbox();
    }
    
    public void paint(Graphics g) {
        for (SpSegment s : segments) {
            s.paint(g);
        }
    }
    
    public boolean isColliding(Rectangle r) {
        return head.getHitbox().intersects(r);
    }
    
    public boolean isColliding(Rectangle[] rs) {
        for (Rectangle r : rs) {
            if (isColliding(r)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean collisionWithSelf() {
        return isColliding(getBody());
    }
    
    public void changeDirection(Direction d) {
        if (d != null) {
            head.setDirection(d);
        }
    }
    
    /**Starting from the back, move every {@code Segment} to the position of the one
     * ahead of it. Afterwards, move the head based on its direction.
     */
    public void moveFromBack() {
        for (int i = segments.length-1; i > 0; i--) {
            segments[i].setCoordinates(segments[i - 1].getCoordinates());
            segments[i].setDirection(segments[i - 1].getDirection());
        }
        head.move();
    }
    
    public void addSegments(int count) {
        int oldLen = segments.length;
        SpSegment lastSegment = segments[oldLen - 1];
        segments = Arrays.copyOf(segments, oldLen + count);
        for (int i = -1; i < count; i++) {
            segments[oldLen + i] = new SpSegment(
                    lastSegment.getCoordinates(), lastSegment.getDirection()
            );
        }
        int newLen = oldLen + count;
        segments[segments.length - 1] = initTail(segments[newLen - 1]);
    }
    
}
