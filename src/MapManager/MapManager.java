/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapManager;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author abe
 */
public class MapManager {
    
    public ArrayList<String> paths = new ArrayList();
    public static final int MAP1 = 0;
    private BufferedImage currentMap;
    private HashMap<String, BufferedImage> spriteList = new HashMap();
    private BufferedImage cursor;
    
    public MapManager() {
        paths.add("../resources/map.jpg");
    }
    
    public BufferedImage getCurrent() {
        return currentMap;
    }
    
    public void load(int map) {
        try {
            currentMap = ImageIO.read(getClass().getResourceAsStream(paths.get(map)));
            spriteList.clear();
            spriteList.put("player", ImageIO.read(getClass().getResourceAsStream("../resources/duck-R.png")));
            cursor = ImageIO.read(getClass().getResourceAsStream("../resources/select.png"));
//            spriteList.add(ImageIO.read(getClass().getResourceAsStream("../resources/duck-R.png")));
            if (map == MAP1) {
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public HashMap<String, BufferedImage> getSprites() {
        return spriteList;
    }
    
    public BufferedImage getCursor() {
        return cursor;
    }
    
}
