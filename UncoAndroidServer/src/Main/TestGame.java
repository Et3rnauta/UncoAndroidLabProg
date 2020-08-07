
package Main;

// @author guido

import Logic.GameServer;

public class TestGame {
    
    static GameServer game;
    
    public static void main(String[] args) {
        
        System.out.println("Abriendo Sala");
        game.openRoom();
        
        System.out.println("Esperando jugadores, presiones \"Enter\" para comenzar el Juego.");
        TecladoIn.readLine();
        
        game.closeRoom();
        
        game.playRound(1);
        //....
        
        game.endGame();
        game.closeConnection();
    }
    
    //TODO: Definir Preguntas

}
