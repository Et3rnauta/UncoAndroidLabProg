package components;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

import database.DbAdapter;

public class DatabaseState {

    private static DatabaseState databaseObject;

    private DbAdapter adapter;

    private DatabaseState(Context context) {
        adapter = new DbAdapter(context);

        //TODO: ver si conviene dejarlo, agrega usuarios para que el sistema este precargado
        if (adapter.getRankData().size() <= 0) {
            adapter.insertData("admin", "1234");
            adapter.updateScore("admin", 20);
            String name = "user_", pass = "user_";
            Random r = new Random();
            for (int i = 0; i < 5; i++) {
                if (adapter.insertData(name + i, pass + i) <= 0) {
                    adapter.updateScore(name + i, r.nextInt(200));
                }
            }
        }
    }

    public static DatabaseState getDatabaseObject(Context context) {
        if (databaseObject == null) {
            databaseObject = new DatabaseState(context);
        }
        return databaseObject;
    }

    /**
     * Consulta si los datos del usuario son correctos
     *
     * @param user Nombre del usuario
     * @param pass Contraseña del usuario
     * @return true si el Nombre y la Contraseña son correctos
     */
    public boolean checkLogIn(String user, String pass) {
        return adapter.checkUserData(user, pass);
    }

    /**
     * Consulta la disponibilidad del nombre del usuario
     *
     * @param user Nombre del usuario
     * @return true si el nombre YA EXISTE para otro usuario
     */
    public boolean checkUserName(String user) {
        return adapter.checkUserName(user);
    }

    /**
     * Registra al usuario en la Base de Datos
     *
     * @param user el Nombre del Usuario
     * @param pass la Contraseña del Usuario
     * @return true si se pudo registrar correctamente
     */
    public boolean registerUser(String user, String pass) {
        long id = adapter.insertData(user, pass);
        return id != -1;
    }

    /**
     * Devuelve una lista con tuplas {UserName, MaxScore} de todos los usuarios
     */
    public ArrayList<String[]> getRankData() {
        return adapter.getRankData();
    }

    /**
     * Si el score es mayor al registrado, lo guarda
     *
     * @param name  nombre del usuario
     * @param score puntaje a registrar
     * @return true si el puntaje es mayor y fue registrado
     */
    public boolean newMaxScore(String name, int score) {
        return adapter.updateScore(name, score);
    }
}
