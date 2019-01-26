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
//        System.out.println(pl);
        json.clear();
        System.out.println(pl);
        String[] units = pl.split("/");
        Player[] players = new Player[units.length];
        for (int i = 0; i < units.length; i++) {
            String[] unit = units[i].split(":");
            try {
                // public Player(int id, int x, int y, String name, String sprite, boolean me) {
                // 213231:duck master 9:100:150
                Player player = new Player(unit[0], Integer.parseInt(unit[2]), Integer.parseInt(unit[3]), unit[1], "../resources/duck-R.png", false);
                players[i] = player;
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        return players;
    }
    
}
