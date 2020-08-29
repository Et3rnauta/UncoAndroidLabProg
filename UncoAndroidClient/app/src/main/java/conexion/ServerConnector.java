package Conexion;

// @author guido
import java.net.Socket;

public class ServerConnector extends Connector {

    ServerConnector(Socket socket) {
        this.socket = socket;
    }
}
