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
    public static void setUpClass(){
        players = new TestPlayer[CANTPLAY];
        createQuestions();
        game = new GameServer(questions);
    }
        
    @Before
    public void setUp() {        
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

    @After
    public void tearDown() {
        game.endGame();
    }

    @Test
    /**
     * Test One Question
     */
    public void test1() {
        System.out.println("--Running test 1");
        System.out.println("Playing round 0");
        game.playRound(0);
        System.out.println("Beggining Check");
        for (TestPlayer player : players) {
            if (player.answer.equals(questions[0][1])) {
                assertTrue(player.serverResponse);
                System.out.println(player.name + " checks true");
            } else {
                assertFalse(player.serverResponse);
                System.out.println(player.name + " checks false");
            }
        }
        System.out.println("--Finish test 1");
    }

    @Test
    /**
     * Test Multiple Questions
     */
    public void test2() {
        System.out.println("--Running test 2");
        for (int i = 0; i < questions.length; i++) {
            System.out.println("Playing round " + i);
            game.playRound(i);
            System.out.println("Beggining Check");
            for (TestPlayer player : players) {
                if (player.answer.equals(questions[i][1])) {
                    assertTrue(player.serverResponse);
                    System.out.println(player.name + " checks true");
                } else {
                    assertFalse(player.serverResponse);
                    System.out.println(player.name + " checks false");
                }
            }
        }
        System.out.println("--Finish test 2");
    }

    public static void createQuestions() {
        String[] pregunta1 = {"¿Cuanto es 2 + 2?", "4", "3", "6", "5"},
                pregunta2 = {"¿Cual es la capital de Argentina?",
                    "Buenos Aires", "Neuquen", "Cipolletti", "Vaca Muerta"},
                pregunta3 = {"¿Cual es la mejor materia en la FAI?",
                    "Laboratorio de Programacion", "Diseño de Base de Datos", "Analisis de Algoritmos", "Introduccion a la Computacion"};

        questions = new String[][]{pregunta1, pregunta2, pregunta3};
    }
}
