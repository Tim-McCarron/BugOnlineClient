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
    private static double x;
    private static double y;
    private static double z;
    private static float direction;
    
    public QueuedCommand() {
        x = 0;
        y = 0;
        z = 0;
        direction = 0;
    }
    
    public static void clear() {
        x = 0;
        y = 0;
        z = 0;
    }
    
    public static String getCommandString() {
        return x + ":" + y + ":" + z + ":" + direction;
    }
    
    public static void setCommandString(double setX, double setY, double setZ, float setD) {
        x = setX;
        y = setY;
        z = setZ;
        direction = setD;
    }
    
    public static void sendY(double speed) {
        double toAdd = speed;
        toAdd = Double.parseDouble(String.format("%.2f", y + toAdd));
        y = toAdd;
    }
    
    public static void sendX(double speed) {
        double toAdd = speed;
        toAdd = Double.parseDouble(String.format("%.2f", x + toAdd));
        x = toAdd;
    }
    
    public static void sendZ(double speed) {
        double toAdd = speed;
        toAdd = Double.parseDouble(String.format("%.2f", z + toAdd));
        z = toAdd;
    }
    
    public static void sendDir(float d) {
        direction = d;
    }
    
    public static void sendUpLeft(double speed) {
        double toAdd = Math.sqrt(Math.pow(speed, 2) + Math.pow(speed, 2)) / 2;
        // GET THE NICE PRECISION FCKIN RAW BRO NICE
        toAdd = Double.parseDouble(String.format("%.2f", toAdd));
        y -= toAdd;
        x -= toAdd;
        // fuckin hacked dude haha!!!
    }
    
    public static void sendUpRight(double speed) {
        double toAdd = Math.sqrt(Math.pow(speed, 2) + Math.pow(speed, 2)) / 2;
        toAdd = Double.parseDouble(String.format("%.2f", toAdd));
        y -= toAdd;
        x += toAdd;
    }
    
    public static void sendDownLeft(double speed) {
        double toAdd = Math.sqrt(Math.pow(speed, 2) + Math.pow(speed, 2)) / 2;
        toAdd = Double.parseDouble(String.format("%.2f", toAdd));
        y += toAdd;
        x -= toAdd;
    }
    
    public static void sendDownRight(double speed) {
        double toAdd = Math.sqrt(Math.pow(speed, 2) + Math.pow(speed, 2)) / 2;
        toAdd = Double.parseDouble(String.format("%.2f", toAdd));
        y += toAdd;
        x += toAdd;
    }
    
}
