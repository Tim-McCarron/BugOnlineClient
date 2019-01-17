/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Unit.*;
import Unit.Player;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import Util.*;
/**
 *
 * @author Able
 */
public class Client implements Runnable {
    
//    private static HashMap<String, Player> list = new HashMap();
    private static Player me;
    private static int port = 9999;
    String serverName = "127.0.0.1";
    private Socket sock;
    private OutputStream outToServer;
    private DataOutputStream out;
    private boolean connected = false;
    private InputStream inFromServer;
    private DataInputStream in;
    private HashMap<String, Unit> list = new HashMap();
    
    private void assignPayload(String payload) {
        Unit[] load = Util.parsePayload(payload);
        for (int i = 0; i < load.length; i++) {
            list.put(String.valueOf(load[i].getId()), load[i]);
        }
    }
    
    public void run() {
        try {
            while (connected) {
                out = new DataOutputStream(sock.getOutputStream());
                // mod with your own payload below
                out.writeUTF("put your json here....");
                inFromServer = sock.getInputStream();
                in = new DataInputStream(inFromServer);
                assignPayload(in.readUTF());
//                System.out.println(in.readUTF());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public HashMap<String, Unit> getUnitList() {
        return list;
    }
    
    public boolean isReady() {
        return !list.isEmpty();
    }
    
    public boolean connect() {
        try {
            sock = new Socket(serverName, port);
            outToServer = sock.getOutputStream();
            out = new DataOutputStream(outToServer);
            connected = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Socket getClientSock() {
        return sock;
    }
    
    public void close() {
        try {
            sock.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
//    public 
    
    public Player getMe() {
        return me;
    }
    
}
