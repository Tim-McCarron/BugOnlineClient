/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duckonline;

import Unit.Player;
import MapManager.MapManager;
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
    
    public static int v_id_player;
    public static int i_id_player;
    public static int t_id_player;
    
    public static int v_id_world;
    public static int i_id_world;
    public static int t_id_world;
    
    public static void bufferPlayer() {
        
        FloatBuffer playerBuffer = BufferUtils.createFloatBuffer(Player.vertices.length);
        FloatBuffer worldBuffer = BufferUtils.createFloatBuffer(MapManager.vertices.length);
        playerBuffer.put(Player.vertices);
        worldBuffer.put(MapManager.vertices);
        playerBuffer.flip();
        worldBuffer.flip();
        // bind and buffer the vertices
        v_id_player = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, v_id_player);
        glBufferData(GL_ARRAY_BUFFER, playerBuffer, GL_STATIC_DRAW);
        
        v_id_world = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, v_id_world);
        glBufferData(GL_ARRAY_BUFFER, worldBuffer, GL_STATIC_DRAW);
        
        // create buffer for indices and bind/buffer them
        i_id_player = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id_player);
        IntBuffer intBuffPlayer = BufferUtils.createIntBuffer(Player.indices.length);
        intBuffPlayer.put(Player.indices);
        intBuffPlayer.flip();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, intBuffPlayer, GL_STATIC_DRAW);
        
        i_id_world = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id_world);
        IntBuffer intBuffMap = BufferUtils.createIntBuffer(MapManager.indices.length);
        intBuffMap.put(MapManager.indices);
        intBuffMap.flip();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, intBuffMap, GL_STATIC_DRAW);
        
        // create buffer for texture then bind/buffer them
        t_id_player = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, t_id_player);
        FloatBuffer textures_player = BufferUtils.createFloatBuffer(Player.tex_coords.length);
        textures_player.put(Player.tex_coords);
        textures_player.flip();
        glBufferData(GL_ARRAY_BUFFER, textures_player, GL_STATIC_DRAW);
        
        t_id_world = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, t_id_world);
        FloatBuffer textures_world = BufferUtils.createFloatBuffer(MapManager.tex_coords.length);
        textures_world.put(MapManager.tex_coords);
        textures_world.flip();
        glBufferData(GL_ARRAY_BUFFER, textures_world, GL_STATIC_DRAW);
        
        // unbind both buffers
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glEnable(GL_DEPTH_TEST);
    }
    
}
