/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duckonline;

import Unit.Player;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

/**
 *
 * @author abe
 */
public class Render {
    
    public static int v_id;
    public static int i_id;
    public static int t_id;
    
    public static void bufferPlayer() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(Player.vertices.length);
        buffer.put(Player.vertices);
        buffer.flip();
        // bind and buffer the vertices
        v_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, v_id);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        // create buffer for indices and bind/buffer them
        i_id = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
        IntBuffer intBuff = BufferUtils.createIntBuffer(Player.indices.length);
        intBuff.put(Player.indices);
        intBuff.flip();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, intBuff, GL_STATIC_DRAW);
        // create buffer for texture then bind/buffer them
        t_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, t_id);
        FloatBuffer textures = BufferUtils.createFloatBuffer(Player.tex_coords.length);
        textures.put(Player.tex_coords);
        textures.flip();
        glBufferData(GL_ARRAY_BUFFER, textures, GL_STATIC_DRAW);
        // unbind both buffers
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glEnable(GL_DEPTH_TEST);
    }
    
}
