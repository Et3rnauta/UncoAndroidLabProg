package Logic;

public class GameState {

    public static GameState game;

    public static GameState getGameObject(){
        if(game == null){
            game = new GameState();
        }
        return game;
    }

    //TODO: crear juego para cliente

}
