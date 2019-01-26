/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Unit;

/**
 *
 * @author abe
 */
public class Player extends Unit {
    
    private boolean me;
    
    public Player(String id, int x, int y, String name, String sprite, boolean me) {
        super(x, y, name, sprite, id);
        this.me = me;
    }
    
    
}
