package Logic;

import java.util.ArrayList;

import Conexion.ConnectionRequestHandler;

public class GameClientHandler extends ConnectionRequestHandler {

    private GameState gameState;

    public GameClientHandler() {
        this.gameState = GameState.getGameObject();
    }

    @Override
    public String handle(String msgReq) {
        ArrayList<String> function = decodeMessage(msgReq);
        String msgReturn = "";
        switch (function.get(0)) {
            case "setQuestion":
                //(question)..(respuestas)..
                gameState.qDefinition = function.get(1);
                gameState.answers[0] = function.get(2);
                gameState.answers[1] = function.get(3);
                gameState.answers[2] = function.get(4);
                gameState.answers[3] = function.get(5);
                gameState.qTime = Integer.decode(function.get(6));
                break;
            case "startQuestion":
                gameState.startQuestion();
                break;
            case "endQuestion":
                gameState.endQuestion();
                break;
            case "endGame":
                gameState.endGame(function.get(1));
                break;
            case "getName":
                msgReturn = gameState.playerName;
                break;
            case "setName":
                gameState.playerName = function.get(1);
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

        //nombre de la funcion
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
