package logica;

import conexion.ClientConnector;

public class GameState {

    public static String playerName;
    public static boolean isSpanish = true;
    public static GameState game;

    public String qDefinition,
            rankPos;
    public String[] answers;
    public int qNumber,
            qTime,
            playerScore;

    private boolean isQuestionActive;

    private ClientConnector connector;
    private QuestionThread questionThread;
    private WaitingThread waitingThread;

    private GameState() {
        //TODO: Cambiar en diagrama los metodos estaticos
        this.qDefinition = "";
        this.qNumber = 0;
        this.answers = new String[4];
        this.qTime = 0;
        this.isQuestionActive = false;
        this.playerScore = 0;
        this.rankPos = "0";
    }

    /**
     * Obtiene el objeto Singleton
     */
    public static GameState getGameObject() {
        if (game == null) {
            game = new GameState();
        }
        return game;
    }

    /**
     * Reinicia los valores del juego
     */
    public static void resetGameObject() {
        game = new GameState();
    }

    public int getScore() {
        return this.playerScore;
    }

    /**
     * Se conecta al juego
     *
     * @param ipAddress la direccion Ip del Server del Juego
     * @return true si se pudo conectar, false si hubo error
     */
    public boolean connect(String ipAddress) {
        connector = new ClientConnector(ipAddress);
        return connector.startConnection(new GameClientHandler());
    }

    /**
     * Envia la respuesta seleccionada
     *
     * @param index el numero de respuesta seleccionada
     */
    public void sendAnswer(Integer index) {
        String sendAnswer = "sendAnswer:(" + playerName + ")(" + qNumber + ")(" + answers[index] + ")";
        playerScore += Integer.decode(connector.makeRequest(sendAnswer));
        endQuestion();
    }

    /**
     * Obtiene la posicion en el ranking de la partida
     */
    public String getRankPos() {
        return rankPos;
    }

    /**
     * Modifica el lenguaje: si es Español a Ingles o viceversa
     */
    public static void changeLanguage() {
        isSpanish = !isSpanish;
    }


    //Manejo de Threads de Control de Pantallas
    public void setQuestionThread(QuestionThread questionThread) {
        this.questionThread = questionThread;
    }

    public void endQuestion() {
        if (isQuestionActive) {
            qNumber++;
            isQuestionActive = false;
        }
        questionThread.wake();
    }

    public void setWaitingThread(WaitingThread waitingThread) {
        this.waitingThread = waitingThread;
    }

    public void startQuestion() {
        waitingThread.wake();
        isQuestionActive = true;
    }

    public void endGame(String rankPos) {
        this.rankPos = rankPos;
        waitingThread.setLast();
        waitingThread.wake();
    }
}
