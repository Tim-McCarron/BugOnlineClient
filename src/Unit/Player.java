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
    
    public static float[] vertices = new float[] {
        0.5f,  0.5f, 0.5f, // 0 top right front
        0.5f, -0.5f, 0.5f, // 1 bottom right front
        -0.5f, -0.5f, 0.5f, // 2 bottom left front
        -0.5f,  0.5f, 0.5f, // 3 top left front
        0.5f,  0.5f, -0.5f, // 4 top right back
        0.5f, -0.5f, -0.5f, // 5 bottom right back
        -0.5f, -0.5f, -0.5f, // 6 bottom left back
        -0.5f,  0.5f, -0.5f, // 7 top left back
    };

    public static int[] indices = new int[] {
        0, 1, 2, // front
        2, 3, 0,

        0, 4, 5, // right
        5, 1, 0,

        0, 4, 7, // top
        0, 7, 3,

        3, 2, 6, // left
        6, 3, 7,

        7, 4, 5, // back
        5, 7, 6,

        6, 2, 1, // bottom
        1, 6, 5

    };

    public static float[] tex_coords = new float[] {
        0.0f,  0.0f,
         0.0f,  1.0f,
         1.0f, 1.0f,
        1.0f, 0.0f,
    };
    
    
    
    public Player() {
        super();
    }
    public Player(String id, double x, double y, double z, String name, String sprite, double speed, boolean me) {
        super(x, y, z, name, sprite, id, speed);
        this.me = me;
    }

    
//    public float[] getVertices() {
//        return this.vertices;
//    }
//    
//    public int[] getIndices() {
//        return this.indices;
//    }
//    
//    public float[] getTexCoords() {
//        return this.tex_coords;
//    }
    
    
}
