package Logic;

import Logic.Utils.TestPlayer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author guido
 */
public class SimpleGameTest {

    static GameServer game;
    static TestPlayer[] players;
    static String[][] questions;

    static final int CANTPLAY = 1;

    @BeforeClass
    public static void setUpClass() {
        players = new TestPlayer[CANTPLAY];
        game = new GameServer(createQuestions());
        System.out.println("Opening room");
        game.openRoom();
        System.out.println("Creating players");
        for (int i = 0; i < players.length; i++) {            
            players[i] = new TestPlayer("player " + i);
            new Thread(players[i]).start();
            Clock.sleep(1);            
        }
        System.out.println("Waiting for players to connect");
        Clock.sleep(1);
        System.out.println("Closing room");
        game.closeRoom();
    }

    @AfterClass
    public static void tearDownClass() {
        game.endGame();
    }

    @Test
    public void testOneQuestion() {
        game.playRound(0);
        for (TestPlayer player : players) {
            if (player.answer.equals("4")) {
                assertTrue(player.serverResponse);
            } else {
                assertFalse(player.serverResponse);
            }
        }
    }
    
    @Test
    public void testTenQuestions(){
        
    }

    public static String[][] createQuestions() {
        String[] pregunta1 = {"¿Cuanto es 2 + 2?", "4", "3", "6", "5"};

        return new String[][]{pregunta1};
    }
}
