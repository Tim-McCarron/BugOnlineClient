/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapManager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.imageio.ImageIO;
import org.joml.Vector2f;
import org.joml.Vector3f;

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
    
    public static float[] vertices;
    public static float[] tex_coords;
    public static int[] indices;
    
    
    public static void load() {
        try {
            File file = new File("D:\\Documents\\NetBeansProjects\\DuckOnline\\src\\resources\\nicegrass.obj"); 
            Scanner sc = new Scanner(file);
            ArrayList<Vector3f> inVertices = new ArrayList<Vector3f>();
            ArrayList<Vector2f> inTexCoords = new ArrayList<Vector2f>();
            ArrayList<Integer> inIndices = new ArrayList<Integer>();
            
            while (true) {
                String line = sc.nextLine();
                if (line.startsWith("v ")) {
                    String thisV[] = line.split(" ");
                    Vector3f vertex = new Vector3f(Float.parseFloat(thisV[1]), Float.parseFloat(thisV[2]), Float.parseFloat(thisV[3]));
                    inVertices.add(vertex);
                } else if (line.startsWith("vt ")) {
                    String thisVt[] = line.split(" ");
                    Vector2f texture = new Vector2f(Float.parseFloat(thisVt[1]), Float.parseFloat(thisVt[2]));
                    inTexCoords.add(texture);
                } else if (line.startsWith("f ")) {
                    tex_coords = new float[inVertices.size() * 2];
                    break;
                }
            }
           
            while (sc.hasNext()) {
                String line = sc.nextLine();
                if (line.startsWith("f ")) {
                    String[] currentLine = line.split(" ");
                    String[] vertex1 = currentLine[1].split("/");
                    String[] vertex2 = currentLine[2].split("/");
                    String[] vertex3 = currentLine[3].split("/");
                    processVertex(vertex1, inIndices, inTexCoords, tex_coords);
                    processVertex(vertex2, inIndices, inTexCoords, tex_coords);
                    processVertex(vertex3, inIndices, inTexCoords, tex_coords);
                }
                
            }

            vertices = new float[inVertices.size() * 3];
            indices = new int[inIndices.size()];
            
            int vertexPointer = 0;
            
            for (Vector3f vertex: inVertices) {
                vertices[vertexPointer++] = vertex.x;
                vertices[vertexPointer++] = vertex.y;
                vertices[vertexPointer++] = vertex.z;
            }
            
            for (int i = 0; i < inIndices.size(); i++) {
                indices[i] = inIndices.get(i);
            }
                
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
    
    private static void processVertex(String[] vertexData, ArrayList<Integer> indices, ArrayList<Vector2f> textures, float[] textureArray) {
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);
        Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
        textureArray[currentVertexPointer * 2] = currentTex.x;
        textureArray[currentVertexPointer * 2 + 1] = 1 - currentTex.y;
    }
    
}
