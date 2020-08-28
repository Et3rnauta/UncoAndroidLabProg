package Main;

// @author guido
import Logic.Clock;
import Logic.GameServer;

public class TestGame {

    static GameServer game;

    public static void main(String[] args) {

        game = new GameServer(conjuntoPreguntas1(), conjuntoPreguntasIngles1());

        System.out.println("Abriendo Sala sobre la IP: 192.168.0.175");
        game.openRoom();

        System.out.println("Esperando jugadores, presione \"Enter\" para comenzar el Juego.");
        TecladoIn.readLine();

        game.closeRoom();

        game.playRound(0);
        Clock.sleep(1);
        game.playRound(1);
        Clock.sleep(1);
        game.playRound(2);
        Clock.sleep(1);

        game.endGame();
        game.printGameStatistics();
        game.closeConnection();
    }

    public static String[][] conjuntoPreguntas1() {
        //[quest][rightAns][ans2][ans3][ans4][time]        
        String[][] testSet = {
            {"¿Cuanto es 2 + 2?", "4", "3", "6", "5", "10"},
            {"¿Cual es la capital de Argentina?", "Buenos Aires", "Neuquen",
                "Cipolletti", "Vaca Muerta", "10"},
            {"¿Cual es la mejor materia en la FAI?", "Laboratorio de Programacion",
                "Diseño de Base de Datos", "Analisis de Algoritmos", "Introduccion a la Computacion", "10"}
        };
        return testSet;
    }

    public static String[][] conjuntoPreguntasIngles1() {
        //[quest][rightAns][ans2][ans3][ans4]        
        String[][] testSet = {
            {"How much is 2 + 2?", "4", "3", "6", "5", "10"},
            {"What's the capital of Argentina?", "Buenos Aires", "Neuquen",
                "Cipolletti", "Vaca Muerta", "10"},
            {"Which is the best class on FAI?", "Laboratorio de Programacion",
                "Diseño de Base de Datos", "Analisis de Algoritmos", "Introduccion a la Computacion", "10"}
        };
        return testSet;
    }

}
