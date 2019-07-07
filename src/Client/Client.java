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
import java.util.Random;
/**
 *
 * @author Able
 */
public class Client implements Runnable {
    
//    private static HashMap<String, Player> list = new HashMap();
    private static Player me;
    private static int port = 9999;
    private String clientId;
    String serverName = "127.0.0.1";
    private Socket sock;
    private OutputStream outToServer;
    private DataOutputStream out;
    private boolean connected = false;
    private InputStream inFromServer;
    private DataInputStream in;
    private HashMap<String, Unit> list = new HashMap();
    private String outboundPayload = "";
    
    private void assignPayload(String payload) {
        Unit[] load = Util.parsePayload(payload);
        for (int i = 0; i < load.length; i++) {
            list.put(String.valueOf(load[i].getId()), load[i]);
        }
        
    }

    public void run() {
        try {
            out = new DataOutputStream(sock.getOutputStream());
            inFromServer = sock.getInputStream();
            in = new DataInputStream(inFromServer);
            clientId = getRandomHexString(6);
            out.writeUTF(clientId);
            while (connected) {
                out.writeUTF(outboundPayload);
                assignPayload(in.readUTF());
            }
        } catch (Exception e) {
            if (e.getMessage() != "Socket closed") {
                System.out.println("Client closed connection");
            } else {
                e.printStackTrace();
            }
            connected = false;
        }
    }
    
    public HashMap<String, Unit> getUnitList() {
        return list;
    }
    
    public boolean isReady() {
        return !list.isEmpty();
    }
    
    public void setPayload() {
        outboundPayload = QueuedCommand.getCommandString();
    }
    
    public boolean isConnected() {
        return connected;
    }
    
    public boolean connect() {
        try {
            System.out.println("connecting");
            sock = new Socket(serverName, port);
//            outToServer = sock.getOutputStream();
//            out = new DataOutputStream(outToServer);
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
    
    private static String getRandomHexString(int numchars){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while (sb.length() < numchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, numchars);
    }
    
    public String getClientId() {
        return clientId;
    }
}
