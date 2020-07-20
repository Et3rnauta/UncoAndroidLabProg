package Logic;

import Logic.Utils.TestPlayer;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

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
        createQuestions();
        game = new GameServer(questions);
    }

    @Before
    public void setUp() {
        System.out.println("Opening room");
        game.openRoom();
        System.out.println("Creating players");
        Random r = new Random();
        for (int i = 0; i < players.length; i++) {
            if (r.nextInt(2) == 1) {
                players[i] = new TestPlayer("testName " + i);
            } else {
                players[i] = new TestPlayer();
            }
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

    @Ignore
    @Test
    /**
     * Test One Question
     */
    public void test1() {
        System.out.println("--Running test 1");
        Clock.sleep(1);
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
            Clock.sleep(1);
            System.out.println("Playing round " + i);
            game.playRound(i);
            System.out.println("Beggining Check");
            for (TestPlayer player : players) {
                System.out.println(player.name + " starts checking");
                if (player.answer.equals(questions[i][1])) {                    
                    assertTrue(player.serverResponse);
                    assertEquals(player.questionTime * 100 / Integer.decode(questions[i][5]), player.questionScore);
                    System.out.println(player.name + " checks true");
                } else {
                    assertFalse(player.serverResponse);
                    System.out.println(player.name + " checks false");
                }
            }
        }
        System.out.println("--Finish test 2");
    }

    @Ignore
    @Test
    /**
     * Test Name
     */
    public void test3() {
        System.out.println("--Running test 3");

        System.out.println("--Finish test 3");
    }

    public static void createQuestions() {
        //question: {question, 4 * answer, time}
        String[] pregunta1 = {"¿Cuanto es 2 + 2?", "4", "3", "6", "5", "20"},
                pregunta2 = {"¿Cual es la capital de Argentina?",
                    "Buenos Aires", "Neuquen", "Cipolletti", "Vaca Muerta", "30"},
                pregunta3 = {"¿Cual es la mejor materia en la FAI?",
                    "Laboratorio de Programacion", "Diseño de Base de Datos", "Analisis de Algoritmos", "Introduccion a la Computacion", "30"};

        questions = new String[][]{pregunta1, pregunta2, pregunta3};
    }
}
