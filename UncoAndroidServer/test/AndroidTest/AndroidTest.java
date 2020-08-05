package AndroidTest;

// @author guido
import Conexion.ConnectionRequestHandler;
import Conexion.ServerConnector;
import Conexion.ServerController;
import org.junit.Test;

public class AndroidTest {

    static ServerController controller;
    static ServerConnector connector;

    @Test
    public void test1() throws InterruptedException {
        controller = new ServerController();
        controller.startServer();
        connector = controller.recieveClient();
        connector.startConnection(new ServerTestHandler(this));
        controller.endServer();
        connector.makeRequest("Hola Cliente");
        synchronized (this) {
            wait();
        }
    }

    void wake() {
        synchronized (this) {
            notify();
        }
    }

    private static class ServerTestHandler extends ConnectionRequestHandler {

        AndroidTest test;

        private ServerTestHandler(AndroidTest aThis) {
            test = aThis;
        }

        @Override
        public String handle(String msgReq) {
            System.out.println("Cliente dice: " + msgReq);
            test.wake();
            return null;
        }
    }
}
