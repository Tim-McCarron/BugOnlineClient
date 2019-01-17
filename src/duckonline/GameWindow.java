package duckonline;


import MapManager.MapManager;
import Client.Client;
import Unit.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author abe
 */
public class GameWindow extends JPanel implements Runnable, KeyListener {
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
    
    private BufferedImage background;
    private HashMap<String, Unit> unitList = new HashMap<String, Unit>();
    private HashMap<String, BufferedImage> sprites = new HashMap();
//    private HashMap<String, Player> players = new HashMap<String, Player>();
    private Graphics2D g;
    private Client client;
    
    public void run() {
        
        init();
	
	long start;
	long elapsed;
	long wait;
	MapManager manager = new MapManager();
        manager.load(MapManager.MAP1);
        background = manager.getCurrent();
        sprites = manager.getSprites();
//        sprites.add(new Player(50, 50, "Player1", "../resources/duck-R.png", 1, true));
	// game loop
        client = new Client();
        client.connect();
        Thread connection = new Thread(client);
        connection.start();
        
        if (client.connect()) {
            
            while (running) {
                start = System.nanoTime();
                // put tick code in here
                if (client.isReady()) {
                    unitList = client.getUnitList();
                }
                                
                drawToScreen();
                draw();

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
        
    }

    public void init() {
//        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
//	g = (Graphics2D) image.getGraphics();
		
	running = true;
//        addKeyListener(this);
    }
    
    private void drawToScreen() {
	Graphics g2 = getGraphics();
	g2.drawImage(background, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
	g2.dispose();
    }
    
    private void draw() {
        Graphics g2 = getGraphics();
      
        
        unitList.forEach((k,v) -> {
            boolean drawingMaybe = g2.drawImage(sprites.get("player"), v.getX(), v.getY(), 100, 100, null);
            System.out.println(drawingMaybe);
        });
//        for (Object value : sprites.values()) {
//            System.out.println(value.toString());
//        }
        g2.dispose();
    
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
