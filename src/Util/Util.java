/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.util.HashMap;
import Unit.*;
/**
 *
 * @author Able
 */
public class Util {
    
    public static HashMap<String, Unit> json = new HashMap<String, Unit>();
    
    public static Unit[] parsePayload(String pl) {
        json.clear();
        String[] units = pl.split("/");
        Player[] players = new Player[units.length];
        System.out.println(pl);
        for (int i = 0; i < units.length; i++) {
            String[] unit = units[i].split(":");
            try {
                // public Player(int id, int x, int y, String name, String sprite, boolean me) {
                // 213231:duck master 9:100:150
//                System.out.println(unit[0] + " 27");
//                System.out.println(unit[2] + " 28");
//                System.out.println(unit[3] + " 29");
//                System.out.println(unit[1] + " 30");
// public Player(String id, double x, double y, String name, String sprite, double speed, boolean me) {
                Player player = new Player(unit[0], Double.parseDouble(unit[2]), Double.parseDouble(unit[3]), unit[1], "../resources/duck-R.png", 10, false);
                players[i] = player;
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        return players;
    }
    
}
