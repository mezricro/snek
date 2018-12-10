package spritesnake;

import java.awt.Point;
import java.util.ArrayList;

public class MapIntepreter {
    static String testMap = 
            "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
            "W.......................................W\n" + 
            "W.......................................W\n" +
            "W.......................................W\n" +
            "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n";
    
    public static char[][] getMapMatrix(String map) {
        String[] mapRows = map.split("\n");
        
        int cols = mapRows[0].length();
        int rows = mapRows.length;
        
        System.out.println(rows);
        
        char[][] maprix = new char[rows][cols];
        
        
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                maprix[r][c] = mapRows[r].charAt(c);
            }
        }
        
        return maprix;
    }
    private static ArrayList<SpWall> detectWalls(char[][] mapMatrix) {
        //Check for linear walls in 'x' direction
        ArrayList<SpWall> walls = new ArrayList<>();
        ArrayList<Point> exclude = new ArrayList<>();
        
        int cols = mapMatrix[0].length;
        int rows = mapMatrix.length;
        
        //check horizontal walls
        int len = 0;
        for (int y = 0; y < rows; y++) {
            Point origin = null;
            
            for (int x = 0; x < cols; x++) {
                char chk = mapMatrix[y][x];
//                System.out.println("checking " + x+":"+y);
                if (chk == 'W') {
                    if (origin == null) {
                        origin = new Point(x, y);
                    }
                    if (!exclude.contains(origin)) {
                        len++;
                    }
                } else {
                    if (len > 1) {
                        exclude.add(origin);
                        System.out.println("row " + y + " - wall length: " + len + ", origin: " + origin);
                    }
                    len = 0;
                }
            }
            if (len > 1) {
                exclude.add(origin);
                System.out.println("row " + y + " - wall length: " + len + ", origin: " + origin);
            }
            len = 0;
        }
        
        //check vertical walls
        for (int y = 0; y < cols; y++) {
            Point origin = null;
            
            for (int x = 0; x < rows; x++) {
                char chk = mapMatrix[x][y];
//                System.out.println("checking " + x+":"+y);
                if (chk == 'W') {
//                    System.out.println("checking " + y+":"+x);
                    if (origin == null) {
                        origin = new Point(x, y);
                        if (exclude.contains(origin)) {
                            origin = null;
                        }
                    }
                    if (!exclude.contains(origin)) {
                        len++;
                    }
                } else {
                    if (len > 1) {
                        exclude.add(origin);
                        System.out.println("column " + y + " - wall length: " + len + ", origin: " + origin);
                    }
                    len = 0;
                }
            }
            if (len > 1) {
                exclude.add(origin);
                System.out.println("column " + y + " - wall length: " + len + ", origin: " + origin);
            }
            len = 0;
        }
        
        
        return walls;
    }
   
    public static void main(String[] args) {
        char[][] maprix = getMapMatrix(testMap);
        for (char[] cs : maprix) {
            System.out.println(cs);
        }
        detectWalls(maprix);
    }
}
