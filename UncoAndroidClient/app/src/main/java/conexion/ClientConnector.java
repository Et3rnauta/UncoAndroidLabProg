package conexion;

// @author guido
import java.io.IOException;
import java.net.Socket;

public class ClientConnector extends Connector {
    //Connects the Client to the server on [ipAdress:port]
    //It may Throw an exception on creation if unable to connect

    private final String ipAdress;
    private final int port;

    public static final int DEFAULTPORT = ServerController.DEFAULTPORT;

    public ClientConnector(String ipAdress, int port){
        this.ipAdress = ipAdress;
        this.port = port;
    }

    public ClientConnector(String ipAdress) {
        this.ipAdress = ipAdress;
        this.port = DEFAULTPORT;
    }

    @Override
    public boolean startConnection(ConnectionRequestHandler handler) {
        try {
            this.socket = new Socket(ipAdress, port);
        } catch (IOException ex) {
            return false;
        }
        return super.startConnection(handler);
    }    
}
