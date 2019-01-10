/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Unit.Player;
import java.io.StringReader;
import java.util.ArrayList;

/**
 *
 * @author Able
 */
public class Client {
    
    private static ArrayList<Player> list = new ArrayList();
    private static Player me;
//    JSONParser parser = Json.createParser(new StringReader("[{\"id\": 2, \"x\":15, \"y\":15, \"name\":\"duck2\"}]"));
    // always put da poppa first (me)
    public static ArrayList<Player> getPlayerList() {
        list.clear();
        
        list.add(me);
//        for (int i = 0; list.size() - 1; i++) {
//            list.add(me)
//        }
        return list;
    }
    
    public Player getMe() {
    
        return me;
    }
    
    
    
}
