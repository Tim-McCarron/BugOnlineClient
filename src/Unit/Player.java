/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Unit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 *
 * @author abe
 */
public class Player extends Unit {
    
    private boolean me;
        
    public static float[] vertices;
    public static float[] tex_coords;
    public static int[] indices;
    
    
    public static void load() {
        try {
            File file = new File("D:\\Documents\\NetBeansProjects\\DuckOnline\\src\\resources\\piss.obj"); 
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
    
//    public static float[] vertices = new float[] {
//        0.5f,  0.5f, 0.5f, // 0 top right front
//        0.5f, -0.5f, 0.5f, // 1 bottom right front
//        -0.5f, -0.5f, 0.5f, // 2 bottom left front
//        -0.5f,  0.5f, 0.5f, // 3 top left front
//        0.5f,  0.5f, -0.5f, // 4 top right back
//        0.5f, -0.5f, -0.5f, // 5 bottom right back
//        -0.5f, -0.5f, -0.5f, // 6 bottom left back
//        -0.5f,  0.5f, -0.5f, // 7 top left back
//    };
//
//    public static int[] indices = new int[] {
//        0, 1, 2, // front
//        2, 3, 0,
//
//        0, 4, 5, // right
//        5, 1, 0,
//
//        0, 4, 7, // top
//        0, 7, 3,
//
//        3, 2, 6, // left
//        6, 3, 7,
//
//        7, 4, 5, // back
//        5, 7, 6,
//
//        6, 2, 1, // bottom
//        1, 6, 5
//
//    };
//
//    public static float[] tex_coords = new float[] {
//        0.0f,  0.0f,
//         0.0f,  1.0f,
//         1.0f, 1.0f,
//        1.0f, 0.0f,
//    };
    
    
    
    public Player() {
        super();
    }
    public Player(String id, double x, double y, double z, float dir, String name, String sprite, double speed, boolean me) {
        super(x, y, z, dir, name, sprite, id, speed);
        this.me = me;
    }

}
