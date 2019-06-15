package duckonline;


import MapManager.MapManager;
import Client.Client;
import Unit.*;
import Util.QueuedCommand;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.awt.event.MouseEvent;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
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
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;
    private Thread thread;
    private boolean running;
    private int FPS = 60;
    private long targetTime = 1000 / FPS;
    private final int UP = 87;
    private final int LEFT = 65;
    private final int DOWN = 83;
    private final int RIGHT = 68;
    private double mouseX;
    private double mouseY;
    
    private BufferedImage crosshair;
    private BufferedImage background;
    private HashMap<String, Unit> unitList = new HashMap();
    private HashMap<String, BufferedImage> sprites = new HashMap();
//    private HashMap<String, Player> players = new HashMap<String, Player>();
    private Graphics g;
    private Client client;
    private boolean upActive = false;
    private boolean downActive = false;
    private boolean leftActive = false;
    private boolean rightActive = false;
    private double moveSpeed = 10;
    
    public static long window;
    public static final int width = 800, height = 600;
    private static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private static boolean[] mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    
    public void run() {
        if  (!GLFW.glfwInit()) {
            System.err.println("couldnt init nibba");
        }
        
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        
        // 0s for not making it fullscreen and not stretching between monitors
        window = GLFW.glfwCreateWindow(width, height, "ducks", 0, 0);
        
        
        // set the mode to the gucci montior
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        // set window position on screen and show that shit
        GLFW.glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
        GLFW.glfwShowWindow(window);
        
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glClearColor(0, 0, GL_BLUE, GL_ALPHA);
        Shader shader = new Shader("shader");
        
	long start;
	long elapsed;
	long wait;
        // load map and sprites
	MapManager manager = new MapManager();
        manager.load(MapManager.MAP1);
        background = manager.getCurrent();
        sprites = manager.getSprites();
        crosshair = manager.getCursor();
        // connect to server
        client = new Client();
        client.connect();
        client.setPayload();
        Thread connection = new Thread(client);
        connection.start();
        Texture tex = new Texture("./src/resources/duck-R.png");
        if (client.isConnected()) {
            Camera camera = new Camera(width, height);
            // game loop
            while (!GLFW.glfwWindowShouldClose(window)) {
                start = System.nanoTime();
                
                if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GL_TRUE) {
                    glfwSetWindowShouldClose(window, true);
                }
                
                for (int i = 0; i < GLFW.GLFW_KEY_LAST; i++) keys[i] = isKeyDown(i);
                for (int i = 0; i < GLFW.GLFW_MOUSE_BUTTON_LAST; i++) mouseButtons[i] = isMouseDown(i);

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
                    QueuedCommand.sendForward(1);
//                    camera.forward();
                }

                if (isKeyDown(GLFW_KEY_S)) {
                    QueuedCommand.sendBackward(1);
//                    camera.backward();
                }

                if (isKeyDown(GLFW_KEY_SPACE)) {
                    QueuedCommand.sendUp(1);
//                    camera.up();
                }

                if (isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
                    QueuedCommand.sendDown(1);
//                    camera.down();
                }
                GLFW.glfwPollEvents();
                if (client.isReady()) {
                    unitList = client.getUnitList();
                    Render.bufferPlayer();
                    tex.bind(0);
                    glEnableVertexAttribArray(glGetAttribLocation(1, "vertices"));
                    glEnableVertexAttribArray(glGetAttribLocation(1, "textures"));
                    glBindBuffer(GL_ARRAY_BUFFER, Render.v_id);
                    glVertexAttribPointer(0, 3, GL_FLOAT, true, 0, 0);
                    glBindBuffer(GL_ARRAY_BUFFER, Render.t_id);
                    glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
                    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, Render.i_id);
                    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                    shader.bind();
                    ArrayList<Unit> toDraw = new ArrayList();
                    for (Unit thisUnit : unitList.values()) {
                        if (thisUnit.getId().equals(client.getClientId())) {
                            thisUnit.setIsMe(true);
                        } else {
                            thisUnit.setIsMe(false);
                        }
                        toDraw.add(thisUnit);
                    }
                    
                    toDraw.forEach((thisUnit) -> {
                        Matrix4f mvp = camera.getMVP();
                        Vector3f unitTranslate = new Vector3f((float) thisUnit.getX(), (float) thisUnit.getY(), (float) thisUnit.getZ());
                        shader.setUniform("mvp", mvp.translate(unitTranslate));
                        // 0 is for vertex and 1 is for textures
                        glDrawElements(GL_TRIANGLES, Player.indices.length, GL_UNSIGNED_INT, 0);
                    }); 
                    
                    // unbind buffers
                    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
                    glBindBuffer(GL_ARRAY_BUFFER, 0);
                    glDisableVertexAttribArray(glGetAttribLocation(1, "vertices"));
                    glDisableVertexAttribArray(glGetAttribLocation(1, "textures"));
                }
                GLFW.glfwSwapBuffers(window);
                // end tick code
            }
            client.close();
            GLFW.glfwTerminate();
        }
    }
    
        // some input shit
    public static boolean isKeyDown(int keyCode) {
	return GLFW.glfwGetKey(window, keyCode) == 1;
    }

    public static boolean isMouseDown(int mouseButton) {
            return GLFW.glfwGetMouseButton(window, mouseButton) == 1;
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
            GLFW.glfwGetCursorPos(window, buffer, null);
            return buffer.get(0);
    }

    public static double getMouseY() {
            DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
            GLFW.glfwGetCursorPos(window, null, buffer);
            return buffer.get(0);
    }
    
    public void mouseDragged(MouseEvent e) {}

    
}
