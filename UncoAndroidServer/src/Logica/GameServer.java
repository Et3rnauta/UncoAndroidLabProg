package Logica;

// @author guido
import Conexion.ServerConnector;
import Conexion.ServerController;
import java.util.ArrayList;

public class GameServer {

    private Question[] questions;
    private ServerController controller;
    private ArrayList<ServerConnector> players;
    private ArrayList<String> playerNames, playersRanked;
    private ArrayList<Boolean> playerLangsSpanish;
    private PlayerReceiver receiver;
    private int[] playerScores;

    public GameServer(String[][] questions, String[][] questionsEnglish) {
        this.questions = new Question[questions.length];
        for (int i = 0; i < questions.length; i++) {
            this.questions[i] = new Question(questions[i], questionsEnglish[i]);
        }
        controller = new ServerController();
    }

    /**
     * Abre la sala para recibir nuevos jugadores
     */
    public void openRoom() {
        receiver = new PlayerReceiver(this);
        players = new ArrayList<>();
        playerNames = new ArrayList<>();
        playerLangsSpanish = new ArrayList<>();
        controller.startServer();
        new Thread(receiver, "PlayerReceiver").start();
    }

    /**
     * Cierra la sala, evitando el ingreso de nuevos jugadores
     */
    public void closeRoom() {
        receiver.closeRoom();
        controller.endServer();
    }

    /**
     * Juega una ronda de una pregunta
     *
     * @param questionRound El numero de pregunta a jugar
     */
    public void playRound(int questionRound) {

        System.out.println("Enviando preguntas...");

        for (int i = 0; i < players.size(); i++) {
            players.get(i).makeRequest(questions[questionRound].toRequest(playerLangsSpanish.get(i)));
        }

        Clock.sleep(1);

        System.out.println("Comienza Pregunta N° " + questionRound);

        String startQuestion = "startQuestion:";
        players.forEach((player) -> {
            player.makeRequest(startQuestion);
        });

        Clock.startCountdown(questions[questionRound].time, true);
        Clock.sleep(questions[questionRound].time);

        System.out.println("Fin de la Pregunta");

        players.forEach((player) -> {
            player.makeRequest("endQuestion:");
        });
    }

    /**
     * Termina la partida, debe ser llamado primero
     */
    public void endGame() {
        System.out.println("Fin del Juego");
        playersRanked = new ArrayList<>();
        ArrayList<Integer> scoresOrdered = new ArrayList<>();

        for (int i = 0; i < playerNames.size(); i++) {
            String name = playerNames.get(i);
            int score = playerScores[i];
            for (int j = 0; j <= i; j++) {
                if (j == i) {
                    playersRanked.add(name);
                    scoresOrdered.add(score);
                    break;
                } else if (scoresOrdered.get(j) < score) {
                    playersRanked.add(j, name);
                    scoresOrdered.add(j, score);
                    break;
                }
            }
        }

        for (int i = 0; i < players.size(); i++) {
            String endGame = "endGame:(" + (playersRanked.indexOf(playerNames.get(i)) + 1) + ")";
            players.get(i).makeRequest(endGame);
        }
    }

    public void printGameStatistics() {
        System.out.println("Los resultados del Juego son:");
        System.out.println("-----------------------------");
        for (int i = 0; i < playerNames.size(); i++) {
            System.out.println((i + 1) + ". " + playerNames.get(i) + " -- " + playerScores[i]);
        }
        System.out.println("-----------------------------");
    }

    /**
     * Cierra la conexión con los jugadores, debe ser llamado último
     */
    public void closeConnection() {
        System.out.println("Se desconectan los jugadores");
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
        if (indice == -1 || idQuestion >= questions.length || idQuestion < 0) {
            return -1;
        }

        int score = 0;

        if (questions[idQuestion].isRightAns(answer, playerLangsSpanish.get(indice))) {
            score = Clock.getCountdownTime() * 100 / questions[idQuestion].time;
        }
        playerScores[indice] += score;

        System.out.println(userName + " respondio y obtuvo: " + score + "pts");

        return score;
    }

    /**
     * Devuelve el puntaje total del usuario
     *
     * @param userName nombre del usuario a consultar
     * @return el puntaje del usuario o null si encontro un error
     */
    String getUserScore(String userName) {
        int indice = playerNames.indexOf(userName);
        if (indice == -1) {
            return null;
        }
        return "" + playerScores[indice];
    }

    private class PlayerReceiver implements Runnable {

        GameServer game;
        private boolean isOpen = true;

        public PlayerReceiver(GameServer game) {
            this.game = game;
        }

        @Override
        public void run() {
            int i = 0;
            while (isOpen) {
                //Cada jugador debe crear el conector, llamar a startConnection 
                //y poder responder su nombre para iniciar correctamente
                ServerConnector player = controller.recieveClient();
                if (player != null) {

                    System.out.println("Se recibe un nuevo jugador");

                    player.startConnection(new GameServerHandler(game));

                    String playerNameAux = player.makeRequest("getName:");
                    if ("".equals(playerNameAux)) {
                        playerNameAux = "player " + i;
                        player.makeRequest("setName:(" + playerNameAux + ")");
                        i++;
                    }

                    players.add(player);
                    playerNames.add(playerNameAux);
                    String resp = player.makeRequest("isSpanish:");
                    playerLangsSpanish.add(resp.equals("true"));
                }
            }
            playerScores = new int[players.size()];
        }

        private void closeRoom() {
            isOpen = false;
        }
    }
}
