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
    private PlayerReceiver receiver;

    GameServer(String[][] questions) {
        players = new ArrayList<>();
        playerNames = new ArrayList<>();
        this.questions = new Question[questions.length];
        for (int i = 0; i < questions.length; i++) {
            this.questions[i] = new Question(questions[i]);
        }
        controller = new ServerController();
        receiver = new PlayerReceiver(this);
    }

    public void openRoom() {
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

//        Clock.sleep(1);
//        String startQuestion = "startQuestion:";
//        players.forEach((player) -> {
//            player.makeRequest(startQuestion);
//        });
        Clock.sleep(3);
        String endQuestion = "endQuestion:";
        players.forEach((player) -> {
            player.makeRequest(endQuestion);
        });
    }

    public void endGame() {
        String endGame = "endGame:";
        players.forEach((player) -> {
            player.makeRequest(endGame);
        });
        players.forEach((player) -> {
            player.endConnection();
        });
    }

    int userAnswer(String userName, Integer idQuestion, String answer) {
        int indice = playerNames.indexOf(userName);    
        return (questions[idQuestion].isRightAns(answer)) ? 1 : 0;
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
                    players.add(player);
                    playerNames.add("player " + i);
                    i++;
                }
            }
        }

        private void closeRoom() {
            isOpen = false;
        }
    }
}
