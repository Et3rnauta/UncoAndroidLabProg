package conexion;

// @author guido
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

abstract class Connector {

    Socket socket;
    protected BufferedReader inputStream;
    protected PrintWriter outputStream;
    protected MessageListener listener;

    protected static final String STARTCONNECTION = "/sv_start/",//This message should be first sent and received by the Connector
            ENDCONNECTION = "/sv_close/";//This message should be last sent and received by the Connection

    /**
     * Starts the connection with the Server and sets up a Listener on a
     * diffrent Thread to answer Requests, should be run first. Ends the
     * connection in case of error
     *
     * @param handler : handles the requests received
     * @return true - if able to connect. false - if not able to start a
     * connect.
     */
    public boolean startConnection(ConnectionRequestHandler handler) {
        try {
            outputStream = new PrintWriter(socket.getOutputStream());
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            return false;
        }

        listener = new MessageListener(handler, this);

        outputStream.println(STARTCONNECTION);
        outputStream.flush();

        try {
            if (!inputStream.readLine().substring(0, STARTCONNECTION.length()).equals(STARTCONNECTION)) {
                endConnection();
                return false;
            }
        } catch (IOException ex) {
            return false;
        }

        new Thread(listener, "MessageListener").start();
        return true;
    }

    /**
     * Makes a request and waits for an answer.
     *
     * @param msgReq : the message to send through the connection.
     * @return the answer received or null if the server doesnt send one or if
     * there was an error
     */
    public String makeRequest(String msgReq) {
        return listener.makeRequest(msgReq);
    }

    /**
     * Closes the connection with the opposing Connector. Should be run always
     * at the end of the program.
     *
     * @return true - if able to end the connection. false - if there was an
     * error.
     */
    public boolean endConnection() {
        listener.isListening = false;
        sendMsg(ENDCONNECTION);
        outputStream.close();
        try {
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Private methods used only by the MessageListener. Asynchronically sends a
     * Message.
     */
    void sendMsg(String msg) {
        outputStream.println(msg);
        outputStream.flush();
    }

    /**
     * Recieves a Message (waiting if the opposing Connector hasn't sent it yet).
     * @return null if the opposing Connector has closed the connection or there is an error in the reception
     */
    String recvMsg() {
        String message;
        try {
            message = inputStream.readLine();
        } catch (IOException ex) {
            message = null;
        }
        if (message != null && message.equals(ENDCONNECTION)) {
            message = null;
        }
        return message;
    }
}
