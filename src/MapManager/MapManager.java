/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapManager;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author abe
 */
public class MapManager {
    
    public ArrayList<String> paths = new ArrayList();
    public static final int MAP1 = 0;
    private BufferedImage currentMap;
    private ArrayList<BufferedImage> spriteList = new ArrayList();
    
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
            spriteList.add(ImageIO.read(getClass().getResourceAsStream("../resources/duck-R.png")));
            if (map == MAP1) {
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<BufferedImage> getSprites() {
        return spriteList;
    }
    
}
