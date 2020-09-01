package Conexion;

// @author guido
import java.io.IOException;
import java.net.Socket;

public class ClientConnector extends Connector {
    //Connects the Client to the server on [ipAddress:port]
    //It may Throw an exception on creation if unable to connect

    private final String ipAddress;
    private final int port;

    public static final int DEFAULTPORT = ServerController.DEFAULTPORT;

    public ClientConnector(String ipAddress, int port){
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public ClientConnector(String ipAddress) {
        this.ipAddress = ipAddress;
        this.port = DEFAULTPORT;
    }

    @Override
    public boolean startConnection(ConnectionRequestHandler handler) {
        try {
            this.socket = new Socket(ipAddress, port);
        } catch (IOException ex) {
            return false;
        }
        return super.startConnection(handler);
    }    
}
