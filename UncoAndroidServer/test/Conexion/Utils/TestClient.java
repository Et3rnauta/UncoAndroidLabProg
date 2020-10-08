package Conexion.Utils;

import Conexion.ClientConnector;
import Conexion.ConnectionRequestHandler;

public class TestClient implements Runnable {

    private ClientConnector connector;
    private String name,
            answer;

    public TestClient(ClientConnector connector, String name) {
        this.connector = connector;
        this.name = name;
    }

    @Override
    public void run() {
        connector.startConnection(new TestHandler(name));
        answer = connector.makeRequest(name);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
        }

        try {
            synchronized (this) {
                wait();
            }
        } catch (InterruptedException ex) {
        }
               
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
