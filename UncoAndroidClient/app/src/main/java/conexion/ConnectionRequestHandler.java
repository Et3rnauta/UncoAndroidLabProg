package Conexion;

// @author guido
public abstract class ConnectionRequestHandler {

    public abstract String handle(String msgReq);

    String listenerHandle(String msgReq) {
        //Shouldn't be replaced, handles the id aspect of the request
        return handle(msgReq.substring(0, msgReq.length() - 38)) + msgReq.substring(msgReq.length() - 38);
    }
}
