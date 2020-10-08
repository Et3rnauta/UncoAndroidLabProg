package conexion;
// @author guido
import java.util.ArrayList;

class MessageListener implements Runnable {
    //Is used by a Connector to be constantly listening and receiving messages that are sent to the handler

    public boolean isListening = true;
    private ConnectionRequestHandler handler;
    private Connector connector;
    private ArrayList<MessageRequest> arrRequest;

    private static final String REQUESTHEADER = "/rq/",
            ANSWERHEADER = "/aw/";

    public MessageListener(ConnectionRequestHandler handler, Connector connector) {
        this.handler = handler;
        this.connector = connector;
        this.arrRequest = new ArrayList<>();
    }

    @Override
    public void run() {
        String message;

        while (isListening) {
            message = connector.recvMsg();

            if (message == null) {
                isListening = false;
            } else if (message.startsWith(REQUESTHEADER)) {
                //If the message is a Request, it handles it and sends an answer
                connector.sendMsg(ANSWERHEADER + handler.listenerHandle(message.substring(REQUESTHEADER.length())));
            } else if (message.startsWith(ANSWERHEADER)) {
                //If the message is an Answer, it's sent to the corresponding Request
                answerRequest(message.substring(ANSWERHEADER.length()));
            } else {
                System.out.println("Unknown Message: " + message);
                return;
            }
        }
    }

    private void answerRequest(String answer) {
        //It answers a specific Request
        int i = 0, length = arrRequest.size();
        while (i < length && !arrRequest.get(i).isAnswer(answer)) {
            i++;
        }
    }

    String makeRequest(String msgReq) {        
        MessageRequest r = new MessageRequest(msgReq);
        arrRequest.add(r);
        connector.sendMsg(REQUESTHEADER + r.getRequest());
        try {
            return r.getAnswer();
        } catch (InterruptedException ex) {
            return null;
        }
    }
}
