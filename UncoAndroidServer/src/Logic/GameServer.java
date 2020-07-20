package Logic;

// @author guido
import Conexion.ServerConnector;
import Conexion.ServerController;
import java.util.ArrayList;

public class GameServer {

    private Question[] questions;
    private ServerController controller;
    private ArrayList<ServerConnector> players;
    private ArrayList<String> playerNames;
    private ArrayList<Integer> playerScores;
    private PlayerReceiver receiver;
    private Clock clock;

    GameServer(String[][] questions) {
        this.questions = new Question[questions.length];
        for (int i = 0; i < questions.length; i++) {
            this.questions[i] = new Question(questions[i]);
        }
        controller = new ServerController();
        clock = new Clock();
    }

    public void openRoom() {
        receiver = new PlayerReceiver(this);
        players = new ArrayList<>();
        playerNames = new ArrayList<>();
        playerScores = new ArrayList<>();
        controller.startServer();
        new Thread(receiver, "PlayerReceiver").start();
    }

    public void closeRoom() {
        receiver.closeRoom();
        controller.endServer();
    }

    public void playRound(int questionRound) {
        players.forEach((player) -> {
            player.makeRequest(questions[questionRound].toRequest());
        });

        Clock.sleep(1);
        String startQuestion = "startQuestion:";
        players.forEach((player) -> {
            player.makeRequest(startQuestion);
        });

        clock.startCountdown(questions[questionRound].time, true);
        Clock.sleep(questions[questionRound].time);

        players.forEach((player) -> {
            player.makeRequest("endQuestion:");
        });
    }

    public void endGame() {
        players.forEach((player) -> {
            player.makeRequest("endGame:");
        });
        players.forEach((player) -> {
            player.endConnection();
        });
    }

    /**
     * Usuario envia la respuesta seleccionada.
     *
     * @param userName Nombre del usuario
     * @param idQuestion Id de la pregunta a responder
     * @param answer Respuesta seleccionada
     * @return un puntaje X si es correcta, 0 si es incorrecta, o -1 si encontro
     * un error
     */
    int userAnswer(String userName, Integer idQuestion, String answer) {
        int indice = playerNames.indexOf(userName);
        if (idQuestion >= questions.length || idQuestion < 0) {
            return -1;
        }

        int score = 0;
        if (questions[idQuestion].isRightAns(answer)) {
            score = clock.getCountdownTime() * 100 / questions[idQuestion].time;
        }
        //TODO: revisar
        playerScores.add(playerScores.get(indice) + score, indice);
        playerScores.remove(indice + 1);
        return score;
    }

    private class PlayerReceiver implements Runnable {

        GameServer game;
        boolean isOpen = true;

        public PlayerReceiver(GameServer game) {
            this.game = game;
        }

        @Override
        public void run() {
            int i = 0;
            while (isOpen) {
                ServerConnector player = controller.recieveClient();
                if (player != null) {
                    player.startConnection(new GameServerHandler(game));

                    String playerNameAux = player.makeRequest("getName:");
                    if ("".equals(playerNameAux)) {
                        playerNameAux = "player " + i;
                        player.makeRequest("setName:(" + playerNameAux + ")");
                        i++;
                    }

                    players.add(player);
                    playerNames.add(playerNameAux);
                    playerScores.add(0);
                }
            }
        }

        private void closeRoom() {
            isOpen = false;
        }
    }
}
