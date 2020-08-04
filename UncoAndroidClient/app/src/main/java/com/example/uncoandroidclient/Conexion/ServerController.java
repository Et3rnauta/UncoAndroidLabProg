package com.example.uncoandroidclient.Conexion;

// @author guido
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class ServerController {

    private ServerSocket serverSocket;
    private int port;
    private boolean isOpen = false;
    
    public static final int DEFAULTPORT = 49999;

    public ServerController(int port) {
        this.port = port;
    }
    
    /**
     * Creates a controller with a default port
     */
    public ServerController() {
        this.port = DEFAULTPORT;
    }

    /**
     * Starts the server
     * @return true - if able to start the server.
     * false - if not able to start the server
     */
    public boolean startServer() {
        try {
            serverSocket = new ServerSocket(port);
            synchronized(this){
                isOpen = true;
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * Recieves a new Client in a ServerConnector
     * @return null if no client was recieved or an error was found
     */
    public ServerConnector recieveClient() {
        try {
            return new ServerConnector(serverSocket.accept());
        } catch (IOException ex) {
            return null;
        }
    }
    
    public synchronized boolean isOpen(){
        return isOpen;
    }

    /**
     * Closes the server
     * @return false if not possible or if there is a thread waiting to accept a client
     */
    public boolean endServer() {
        try {
            serverSocket.close();            
            synchronized(this){
                isOpen = false;
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
    
    /** 
     * Gives the current Ip Address 
     * @return null if not possible
     */
    public static String getIpAdress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            return null;
        }
    }
}
