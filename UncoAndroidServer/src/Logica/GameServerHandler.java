package Logica;

// @author guido
import Conexion.ConnectionRequestHandler;
import java.util.ArrayList;

public class GameServerHandler extends ConnectionRequestHandler {

    private GameServer game;

    public GameServerHandler(GameServer game) {
        this.game = game;
    }

    @Override
    public String handle(String msgReq) {
        ArrayList<String> function = decodeMessage(msgReq);
        String msgReturn = "";
        switch (function.get(0)) {
            case "sendAnswer":
                //(nombreDeUser)(numPregunta)(respuesta)
                msgReturn = "" + game.userAnswer(function.get(1), Integer.decode(function.get(2)), function.get(3));;
                break;
            case "getUserScore":
                //(nombreDeUser)
                msgReturn = "" + game.getUserScore(function.get(1));
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
