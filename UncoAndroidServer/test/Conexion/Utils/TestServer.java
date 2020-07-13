package Conexion.Utils;

// @author guido
import Conexion.ConnectionRequestHandler;
import Conexion.ServerConnector;

public class TestServer implements Runnable {

    private ServerConnector connector;
    private String name,
            answer;

    public TestServer(ServerConnector connector, String name) {
        this.connector = connector;
        this.name = name;
    }

    @Override
    public void run() {
        connector.startConnection(new TestHandler(name));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
        }
        answer = connector.makeRequest(name);

        try {
            synchronized (this) {
                wait();
            }
        } catch (InterruptedException ex) {
        }

        connector.endConnection();
    }

    public String getAnswer() {
        synchronized (this) {
            notify();
        }
        return answer;
    }

    private static class TestHandler extends ConnectionRequestHandler {

        String name;

        public TestHandler(String name) {
            this.name = name;
        }

        @Override
        public String handle(String msgReq) {
            return name + "-answer:" + msgReq;
        }
    }
}
