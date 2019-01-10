/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Unit;

/**
 *
 * @author Able
 */
// absolute unit
public class Unit {
    private int x;
    private int y;
    private String name;
    private String spritePath;
    private int id;
//    private final static boolean ABSOLUTE = true;
    
    public Unit(int x, int y, String name, String spritePath, int id) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.spritePath = spritePath;
        
        this.id = id;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public String getName() {
        return name;
    }
    
    public String getSprite() {
        return spritePath;
    }
    
    public int getId() {
        return id;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setSprite(String spritePath) {
        this.spritePath = spritePath;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    
}
