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

    static final int CANTPLAY = 10;

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
        System.out.println("--Running test 1");
        System.out.println("Playing round 0");
        game.playRound(0);
        System.out.println("Beggining Assert");
        for (TestPlayer player : players) {
            System.out.println(player.name + " test");
            if (player.answer.equals("4")) {
                assertTrue(player.serverResponse);
            } else {
                assertFalse(player.serverResponse);
            }
        }
    }

    @Test
    public void testMultipleQuestions() {

    }

    public static String[][] createQuestions() {
        String[] pregunta1 = {"¿Cuanto es 2 + 2?", "4", "3", "6", "5"},
                pregunta2 = {"¿Cual es la capital de Argentina?",
                    "Buenos Aires", "Neuquen", "Cipolletti", "Vaca Muerta"},
                pregunta3 = {"¿Cual es la mejor materia en la FAI?",
                    "Laboratorio de Programacion", "Diseño de Base de Datos", "Analisis de Algoritmos", "Introduccion a la Computacion"};

        return new String[][]{pregunta1, pregunta2, pregunta3};
    }
}
