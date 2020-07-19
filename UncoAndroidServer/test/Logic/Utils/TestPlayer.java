package Logic.Utils;

import Conexion.ClientConnector;
import Conexion.ConnectionRequestHandler;
import Logic.*;
import java.util.ArrayList;
import java.util.Random;

// @author guido
public class TestPlayer implements Runnable {

    public boolean serverResponse;
    public String answer;
    public String name;
    private ClientConnector connector;
    private boolean isPlaying = true;

    public TestPlayer(String name) {
        this.name = name;
        connector = new ClientConnector("localhost");
    }

    @Override
    public void run() {
        connector.startConnection(new TestClientHandler(this));

        while (isPlaying) {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                }
            }
            String sendAnswer = "sendAnswer:(" + name + ")(" + 0 + ")(" + answer + ")";
            serverResponse = "1".equals(connector.makeRequest(sendAnswer));
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                }
            }
        }
        connector.endConnection();
    }

    void wake() {
        synchronized (this) {
            notify();
        }
    }

    private static class TestClientHandler extends ConnectionRequestHandler {

        TestPlayer player;

        public TestClientHandler(TestPlayer player) {
            this.player = player;
        }

        @Override
        public String handle(String msgReq) {
            ArrayList<String> function = decodeMessage(msgReq);
            String msgReturn;
            switch (function.get(0)) {
                case "setQuestion":
                    //(question)..(respuestas)..
                    Random r = new Random();
                    player.answer = function.get(r.nextInt(4) + 2);
                    player.wake();
                    msgReturn = "";
                    break;
                case "endQuestion":                    
                    player.wake();
                    msgReturn = "";
                    break;
                case "endGame":
                    player.isPlaying = false;
                    player.wake();
                default:
                    msgReturn = "ERROR";
                    break;
            }
            return msgReturn;
        }

        /**
         * Decodifica un request para obtener el nombre y sus parametros
         *
         * @param msgReq : request a decodificar
         * @return Lista con nombre de request en primera posicion y parametros
         * en el resto
         */
        private ArrayList<String> decodeMessage(String msgReq) {
            ArrayList<String> arrayFunc = new ArrayList<>();

            //nombre
            arrayFunc.add(msgReq.substring(0, msgReq.indexOf(":")));

            //parametros
            String aux = msgReq.substring(msgReq.indexOf(":") + 1);
            while (!"".equals(aux)) {
                arrayFunc.add(aux.substring(1, aux.indexOf(")")));
                aux = aux.substring(aux.indexOf(")") + 1);
            }
            return arrayFunc;
        }
    }
}
