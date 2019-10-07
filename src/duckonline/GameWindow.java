package duckonline;


import MapManager.MapManager;
import Client.Client;
import Unit.*;
import Util.QueuedCommand;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.awt.event.MouseEvent;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.GL_ALPHA;
import static org.lwjgl.opengl.GL11.GL_BLUE;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author abe
 */
public class GameWindow implements Runnable {
//    public static final int WIDTH = 1200;
//    public static final int HEIGHT = 1000;
    public static final int SCALE = 2;
    private int FPS = 60;

    private double mouseX;
    private double mouseY;
    
    private BufferedImage crosshair;
    private HashMap<String, Unit> unitList = new HashMap();
    private HashMap<String, BufferedImage> sprites = new HashMap();
//    private HashMap<String, Player> players = new HashMap<String, Player>();
    private Client client;
    
    public static long window;
    public static final int width = 1400, height = 800;
    private static boolean[] keys = new boolean[GLFW_KEY_LAST];
    private static boolean[] mouseButtons = new boolean[GLFW_MOUSE_BUTTON_LAST];
    
    public void run() {
        if  (!glfwInit()) {
            System.err.println("couldnt init nibba");
        }
        
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        
        // 0s for not making it fullscreen and not stretching between monitors
        window = glfwCreateWindow(width, height, "BugOnline", 0, 0);
        
        
        // set the mode to the gucci montior
        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // set window position on screen and show that shit
        glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
        glfwShowWindow(window);
        
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glClearColor(0, 0, GL_BLUE, GL_ALPHA);
        Shader shader = new Shader("shader");
        
	long start;
	long elapsed;
	long wait;
        // load map and sprites
//	MapManager manager = new MapManager();
//        manager.load(MapManager.MAP1);
        
        System.out.println(crosshair);
        // connect to server
        client = new Client();
        client.connect();
        client.setPayload();
        Thread connection = new Thread(client);
        connection.start();
        
        Texture ground = new Texture("./src/resources/grass.jpg");
        Texture tex = new Texture("./src/resources/bren.png");
        
        float myX = 0, myY = 0, myZ = 0, direction = 0;
        
        float horizontalToRotate = 0;
        float verticalToRotate = 0;
        
        if (client.isConnected()) {
            Player.load();
            MapManager.load();
            Render.bufferPlayer();
            shader.bind();
            Camera camera = new Camera(width, height);
            // game loop
            mouseX = getMouseX();
            mouseY = getMouseY();
            
            GLFW.glfwSetScrollCallback(window, (window, xoffset, yoffset) -> {
                if (yoffset > 0) {
                    camera.zoomIn();
                } else if (yoffset < 0) {
                    camera.zoomOut();
                } 
            });
            
            while (!glfwWindowShouldClose(window)) {
                start = System.nanoTime();
                
                if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GL_TRUE) {
                    glfwSetWindowShouldClose(window, true);
                }
                
                for (int i = 0; i < GLFW_KEY_LAST; i++) keys[i] = isKeyDown(i);
                for (int i = 0; i < GLFW_MOUSE_BUTTON_LAST; i++) mouseButtons[i] = isMouseDown(i);
                if (mouseButtons[1]) {
                    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
                    float mouseDX = (float) ((getMouseX() - mouseX) * .2);
                    horizontalToRotate += mouseDX;
                    camera.rotateHorizontal(horizontalToRotate);
                    mouseX = getMouseX();
                    horizontalToRotate = 0;
                    
                } else {
                    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
                    mouseX = getMouseX();
                }
                if (isKeyDown(GLFW_KEY_D)) {
                    camera.strafeRight();
                }
                if (isKeyDown(GLFW_KEY_A)) {
                    camera.strafeLeft();
                }

                if (isKeyDown(GLFW_KEY_E)) {
                    camera.rotateHorizontal(-6);
                }
                
                if (isKeyDown(GLFW_KEY_Q)) {
                    camera.rotateHorizontal(6);
                }

                if (isKeyDown(GLFW_KEY_W)) {
                    camera.forward();
                }

                if (isKeyDown(GLFW_KEY_S)) {
                    camera.backward();
                }

                if (isKeyDown(GLFW_KEY_SPACE)) {
                    camera.up();
                }

                if (isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
                    camera.down();
                }
                glfwPollEvents();
                if (client.isReady()) {
                    unitList = client.getUnitList();
                    
                    ground.bind(0);

                    glEnableVertexAttribArray(glGetAttribLocation(1, "ant"));
                    glEnableVertexAttribArray(glGetAttribLocation(1, "ant_tex"));

                    glBindBuffer(GL_ARRAY_BUFFER, Render.v_id_world);
                    glVertexAttribPointer(0, 3, GL_FLOAT, true, 0, 0);
                    glBindBuffer(GL_ARRAY_BUFFER, Render.t_id_world);
                    glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
                    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, Render.i_id_world);
                    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                    Matrix4f worldMVP = camera.getMVP(direction, 0, myX, (float) 0 - myY, myZ);
                    shader.setUniform("mvp", worldMVP);
                    glDrawElements(GL_TRIANGLES, MapManager.indices.length, GL_UNSIGNED_INT, 0);
                    
                    tex.bind(0);
                    glBindBuffer(GL_ARRAY_BUFFER, Render.v_id_player);
                    
                    glVertexAttribPointer(0, 3, GL_FLOAT, true, 0, 0);
                    glBindBuffer(GL_ARRAY_BUFFER, Render.t_id_player);
                    glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
                    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, Render.i_id_player);
                    
                    
                    ArrayList<Unit> toDraw = new ArrayList();
                    for (Unit thisUnit : unitList.values()) {
                        if (thisUnit.getId().equals(client.getClientId())) {
                            myX = (float) thisUnit.getX();
                            myY = (float) thisUnit.getY();
                            myZ = (float) thisUnit.getZ();
                            direction = thisUnit.getDir();
                            thisUnit.setIsMe(true);
                        } else {
                            thisUnit.setIsMe(false);
                        }
                        toDraw.add(thisUnit);
                    }
//                    System.out.println(toDraw.size());
                    for (int i = 0; i < toDraw.size(); i++) {
                        if (toDraw.get(i).getIsMe()) {
                            Matrix4f mvp = camera.getMVP(0, 0, 0, 0, 0);
                            shader.setUniform("mvp", mvp);
                        } else {
//                            System.out.println(direction + toDraw.get(i).getDir());
                            Matrix4f mvp = camera.getMVP(direction, toDraw.get(i).getDir(), myX - (float) toDraw.get(i).getX(), (float) toDraw.get(i).getY() - myY, myZ - (float) toDraw.get(i).getZ());
//                            Vector3f unitTranslate = new Vector3f(myX - (float) toDraw.get(i).getX(), (float) toDraw.get(i).getY() - myY, myZ - (float) toDraw.get(i).getZ());
                            shader.setUniform("mvp", mvp);
                        }
                        // 0 is for vertex and 1 is for textures
                        glDrawElements(GL_TRIANGLES, Player.indices.length, GL_UNSIGNED_INT, 0);
                    }
                    
//                    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                    
                    // unbind buffers
                    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
                    glBindBuffer(GL_ARRAY_BUFFER, 0);
                    glDisableVertexAttribArray(glGetAttribLocation(1, "ant"));
                    glDisableVertexAttribArray(glGetAttribLocation(1, "ant_tex"));
                }
                
                glfwSwapBuffers(window);
                client.setPayload();
                QueuedCommand.clear();
                
//                mouseX = getMouseX();
//                mouseY = getMouseY();
                // end tick code
            }
            client.close();
            glfwTerminate();
        }
    }
    
        // some input shit
    public static boolean isKeyDown(int keyCode) {
	return glfwGetKey(window, keyCode) == 1;
    }

    public static boolean isMouseDown(int mouseButton) {
            return glfwGetMouseButton(window, mouseButton) == 1;
    }

    public static boolean isKeyPressed(int keyCode) {
            return isKeyDown(keyCode) && !keys[keyCode];
    }

    public static boolean isKeyReleased(int keyCode) {
            return !isKeyDown(keyCode) && keys[keyCode];
    }

    public static boolean isMousePressed(int mouseButton) {
            return isMouseDown(mouseButton) && !mouseButtons[mouseButton];
    }

    public static boolean isMouseReleased(int mouseButton) {
            return !isMouseDown(mouseButton) && mouseButtons[mouseButton];
    }

    public static double getMouseX() {
            DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
            glfwGetCursorPos(window, buffer, null);
            return buffer.get(0);
    }

    public static double getMouseY() {
            DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
            glfwGetCursorPos(window, null, buffer);
            return buffer.get(0);
    }
    
    public void mouseDragged(MouseEvent e) {}

    
}
