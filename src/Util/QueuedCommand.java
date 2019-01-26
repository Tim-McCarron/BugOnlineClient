/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

/**
 *
 * @author abe
 */
public class QueuedCommand {
    // these variables are in caps
    // do not mess with these fuckin vars or u'll regret it 
    private double x;
    private double y;
    
    public QueuedCommand() {
        x = 0;
        y = 0;
    }
    
    public void clear() {
        x = 0;
        y = 0;
    }
    
    public String getCommandString() {
        return x + ":" + y;
    }
    
    public void sendUp(double speed) {
        y -= speed;
    }
    
    public void sendDown(double speed) {
        y += speed;
    }
    
    public void sendLeft(double speed) {
        x -= speed;
    }
    
    public void sendRight(double speed) {
        x += speed;
    }
    
    public void sendUpLeft(double speed) {
        double toAdd = Math.sqrt(Math.pow(speed, 2) + Math.pow(speed, 2)) / 2;
        // GET THE NICE PRECISION FCKIN RAW BRO NICE
        toAdd = Double.parseDouble(String.format("%.2f", toAdd));
        y -= toAdd;
        x -= toAdd;
        // fuckin hacked dude haha!!!
    }
    
    public void sendUpRight(double speed) {
        double toAdd = Math.sqrt(Math.pow(speed, 2) + Math.pow(speed, 2)) / 2;
        toAdd = Double.parseDouble(String.format("%.2f", toAdd));
        y -= toAdd;
        x += toAdd;
    }
    
    public void sendDownLeft(double speed) {
        double toAdd = Math.sqrt(Math.pow(speed, 2) + Math.pow(speed, 2)) / 2;
        toAdd = Double.parseDouble(String.format("%.2f", toAdd));
        y += toAdd;
        x -= toAdd;
    }
    
    public void sendDownRight(double speed) {
        double toAdd = Math.sqrt(Math.pow(speed, 2) + Math.pow(speed, 2)) / 2;
        toAdd = Double.parseDouble(String.format("%.2f", toAdd));
        y += toAdd;
        y += toAdd;
    }
    
}
