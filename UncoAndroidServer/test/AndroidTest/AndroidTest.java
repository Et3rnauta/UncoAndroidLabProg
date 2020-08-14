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
        System.out.println("Esperando Cliente");
        controller.startServer();        
        connector = controller.recieveClient(); 
        connector.startConnection(new ServerTestHandler(this));
        System.out.println("Cliente Conectado");               
        controller.endServer();        
        System.out.println("Enviando Mensaje");
        connector.makeRequest("Hola Cliente");        
        System.out.println("Mensaje Enviado");
        synchronized (this) {
            wait();
        }
        System.out.println("Mensaje Recibido");
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
