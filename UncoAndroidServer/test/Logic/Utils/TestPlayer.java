package Logic.Utils;

import Logica.Clock;
import Conexion.ClientConnector;
import Conexion.ConnectionRequestHandler;
import java.util.ArrayList;
import java.util.Random;

// @author guido
public class TestPlayer implements Runnable {

    public boolean serverResponse;
    public String answer;
    public String name;
    public int gameTime, questionTime, questionScore, gameScore, scoreAcum = 0;
    public Clock clock = new Clock();

    private ClientConnector connector;
    private boolean isPlaying = true;

    public TestPlayer() {
        this.name = "";
        connector = new ClientConnector("localhost");
    }

    @Override
    public void run() {
        connector.startConnection(new TestClientHandler(this));
        int i = 0;
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        while (isPlaying) {
            Clock.sleep(new Random().nextInt(5) + 1);
            String sendAnswer = "sendAnswer:(" + name + ")(" + i + ")(" + answer + ")";
            questionTime = clock.getCountdownTime();
            questionScore = Integer.decode(connector.makeRequest(sendAnswer));
            serverResponse = !(0 == questionScore || -1 == questionScore);
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                }
            }
            i++;
        }
        String getUserScore = "getUserScore:(" + name + ")";
        System.out.println(name + " pregunta su score final");
        gameScore = Integer.decode(connector.makeRequest(getUserScore));
        System.out.println(name + " recibe su score final");
        connector.endConnection();
    }

    public void addScore(int score) {
        scoreAcum += score;
    }

    public void wake() {
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
            String msgReturn = "";
            switch (function.get(0)) {
                case "setQuestion":
                    //(question)..(respuestas)..
                    Random r = new Random();
                    player.answer = function.get(r.nextInt(4) + 2);
                    player.gameTime = Integer.decode(function.get(6));
                    break;
                case "startQuestion":
                    player.clock.startCountdown(player.gameTime, false);
                    player.wake();
                    break;
                case "endQuestion":
                    break;
                case "endGame":
                    player.isPlaying = false;
                    player.wake();
                    break;
                case "getName":
                    msgReturn = player.name;
                    break;
                case "setName":
                    player.name = function.get(1);
                    break;
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
