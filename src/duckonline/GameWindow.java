package duckonline;


import MapManager.MapManager;
import Client.Client;
import Unit.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.swing.JPanel;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author abe
 */
public class GameWindow extends JPanel implements Runnable, KeyListener, MouseMotionListener {
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
    
    public void run() {
        
        init();
	
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
        Thread connection = new Thread(client);
        connection.start();
        g = getGraphics();
        if (client.connect()) {
            // game loop
            while (running) {
                start = System.nanoTime();
                // put tick code in here
                if (client.isReady()) {
                    unitList = client.getUnitList();
                }
                drawToScreen();
//                draw();

                // end tick code
                elapsed = System.nanoTime() - start;
                wait = targetTime - elapsed / 1000000;
                if (wait < 0) wait = 5;

                try {
                    Thread.sleep(wait);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public GameWindow() {
        super();
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus();
        addMouseMotionListener(this);
    }

    public void init() {		
	running = true;
    }
    
    public void paintComponent(Graphics g) {
       super.paintComponent(g);
        g.drawImage(background, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        unitList.forEach((k, v) -> {
            g.drawImage(sprites.get("player"), v.getX(), v.getY(), 100, 100, null);
        });
        g.drawImage(crosshair, (int) mouseX, (int) mouseY, 19, 19, null);
       repaint();
    }
    
    private void drawToScreen() {
        paintComponent(g);
    }
    
    private void draw() {
        
    }
    
    private void update() {
    
//        players.clear();
    
    }
    
    public void addNotify() {
        super.addNotify();
	if(thread == null) {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
	}
    }
    
    public void keyTyped(KeyEvent e) {   
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    
    public void mouseDragged(MouseEvent e) {}

    public void keyPressed(KeyEvent e) {
        
        if (e.getKeyCode() == UP) {
            System.out.println("UP");
        }
        if (e.getKeyCode() == LEFT) {
            System.out.println("LEFT");
        }
        if (e.getKeyCode() == DOWN) {
            System.out.println("DOWN");
        }
        if (e.getKeyCode() == RIGHT) {
            System.out.println("RIGHT");
        }
        
        
    }

    public void keyReleased(KeyEvent e) {
//        System.out.println("shit");
    }
    
}
