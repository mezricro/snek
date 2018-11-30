/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snek;

/**
 *
 * @author robi
 */
public enum Direction {
    N(0,-1), E(1,0), S(0,1), W(-1,0);
    
    private int x;
    private int y;
    
    private Direction(int x, int y) {
        this.x = x;
        this.y = y;
        
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    
    
    public boolean isOpposite(Direction d) {
        return (this.x == d.x * -1) && (this.y == d.y * -1);
    }
}
