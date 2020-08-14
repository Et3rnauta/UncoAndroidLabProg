package Logic;

import Conexion.ClientConnector;

public class GameState {

    public static String playerName;
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

    public GameState() {
        this.qDefinition = "";
        this.qNumber = 0;
        this.answers = new String[4];
        this.qTime = 0;
        this.isQuestionActive = false;
        this.playerScore = 0;
        this.rankPos = "0";
    }

    public static void resetGameObject() {
        game = new GameState();
    }

    public static GameState getGameObject() {
        if (game == null) {
            game = new GameState();
        }
        return game;
    }

    public boolean connect(String ipAddress) {
        connector = new ClientConnector(ipAddress);
        return connector.startConnection(new GameClientHandler());
    }

    public void sendAnswer(Integer index) {
        String sendAnswer = "sendAnswer:(" + playerName + ")(" + qNumber + ")(" + answers[index] + ")";
        playerScore += Integer.decode(connector.makeRequest(sendAnswer));
        endQuestion();
    }

    public String getRankPos() {
        return rankPos;
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
        waitingThread.isLast();
        waitingThread.wake();
    }
}
