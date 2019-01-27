package duckonline;


import MapManager.MapManager;
import Client.Client;
import Unit.*;
import Util.QueuedCommand;
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
    private QueuedCommand input = new QueuedCommand();
    private boolean upActive = false;
    private boolean downActive = false;
    private boolean leftActive = false;
    private boolean rightActive = false;
    private double moveSpeed = 10;
    
    
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
        if (client.isConnected()) {
            // game loop
            while (running) {
                start = System.nanoTime();
                checkInputs();
                client.setPayload(input);
                input.clear();
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
            g.drawImage(sprites.get("player"), (int) Math.round(v.getX()), (int) Math.round(v.getY()), 100, 100, null);
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
    
    public void checkInputs() {
        if (upActive && leftActive) {
            input.sendUpLeft(moveSpeed);
        } else if (upActive && rightActive) {
            input.sendUpRight(moveSpeed);
        } else if (downActive && leftActive) {
            input.sendDownLeft(moveSpeed);
        } else if (downActive && rightActive) {
            input.sendDownRight(moveSpeed);
        } else if (upActive) {
            input.sendUp(moveSpeed);
        } else if (downActive) {
            input.sendDown(moveSpeed);
        } else if (leftActive) {
            input.sendLeft(moveSpeed);
        } else if (rightActive) {
            input.sendRight(moveSpeed);
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == UP) {
            upActive = true;
            downActive = false;
        }
        if (e.getKeyCode() == LEFT) {
            leftActive = true;
            rightActive = false;
        }
        if (e.getKeyCode() == DOWN) {
            downActive = true;
            upActive = false;
        }
        if (e.getKeyCode() == RIGHT) {
            rightActive = true;
            leftActive = false;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == UP) {
            upActive = false;
        }
        if (e.getKeyCode() == LEFT) {
            leftActive = false;
        }
        if (e.getKeyCode() == DOWN) {
            downActive = false;
        }
        if (e.getKeyCode() == RIGHT) {
            rightActive = false;
        }
    }
    
}
