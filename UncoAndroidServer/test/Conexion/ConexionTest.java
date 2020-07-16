package Conexion;

import Conexion.Utils.TestClient;
import Conexion.Utils.TestServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author guido
 */
public class ConexionTest {

    static ServerController controller;
    static TestServer[] servers;
    static TestClient[] clients;

    static final int NUMCLIENTS = 100,
            PORT = 9999;

    @BeforeClass
    public static void setUpClass() {
        controller = new ServerController(PORT);
        clients = new TestClient[NUMCLIENTS];
        servers = new TestServer[NUMCLIENTS];
        controller.startServer();
    }

    @AfterClass
    public static void tearDownClass() {
        controller.endServer();
    }

    @Test
    public void test1() {
        new Thread(new Receiver(), "Receiver").start();

        try {
            System.out.println("Creando Clientes");
            for (int i = 0; i < NUMCLIENTS; i++) {
                clients[i] = new TestClient(new ClientConnector("localhost", PORT), "client-" + i);
                new Thread(clients[i]).start();
                Thread.sleep(100);
            }
            System.out.println("Esperando envio de mensajes");
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
        }
        
        for (int i = 0; i < NUMCLIENTS; i++) {
            System.out.println("Verificando conjunto " + i);
            assertEquals("server-" + i + "-answer:client-" + i, clients[i].getAnswer());
            assertEquals("client-" + i + "-answer:server-" + i, servers[i].getAnswer());
        }
    }

    private static class Receiver implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < NUMCLIENTS; i++) {
                servers[i] = new TestServer(controller.recieveClient(), "server-" + i);
                new Thread(servers[i]).start();
            }
        }
    }
}
