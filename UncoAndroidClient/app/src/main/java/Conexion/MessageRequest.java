package Conexion;
// @author guido
import java.util.UUID;
import java.util.concurrent.Semaphore;

class MessageRequest {

    private final String request;
    private String answer;
    private final String id;
    private final Semaphore waitingThread;

    MessageRequest(String request) {
        this.request = request;
        this.answer = null;
        this.id = "/" + UUID.randomUUID().toString() + "/";
        this.waitingThread = new Semaphore(0);
    }

    String getRequest() {
        return request + id;
    }

    /**
     * If the answer isn't available, it waits for it.
     * @return The answer received.
     * @throws InterruptedException 
     */
    String getAnswer() throws InterruptedException {
        waitingThread.acquire();
        return answer;
    }

    /**
     * Checks if the id on the answer matches the id of the request.  
     * The answer should have the form: "[messageAnswer]/id/" 
     * @param ans : Answer to check.
     * @return true - if the answer checks out. false - if it doesnt.
     */
    boolean isAnswer(String ans) {
        boolean isAnswer = ans.endsWith(id);
        if (isAnswer) {
            answer = ans.substring(0, ans.length() - id.length());
            waitingThread.release();
        }
        return isAnswer;
    }
}
