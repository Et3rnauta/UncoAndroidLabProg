package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbAdapter {
    DbHelper myhelper;

    public DbAdapter(Context context) {
        myhelper = new DbHelper(context);
    }

    /**
     * Registra un nuevo usuario
     *
     * @param name Nombre de Usuario
     * @param pass Contraseña del Usuario
     * @return El id del usuario en la tabla
     */
    public long insertData(String name, String pass) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.NAME, name);
        contentValues.put(DbHelper.PASSWORD, pass);
        contentValues.put(DbHelper.MAXSCORE, 0);
        return myhelper.getWritableDatabase().insert(DbHelper.TABLE_NAME,
                null, contentValues);
    }

    /**
     * Modifica el puntaje del usuario si es mayor que el actual
     *
     * @param name     Nombre del usuario
     * @param newScore Puntaje a registrar si es mayor
     * @return true si el score era mayor, false si no
     */
    public boolean updateScore(String name, int newScore) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        boolean esMayor = isScoreMax(name, newScore, db);
        if (esMayor) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbHelper.MAXSCORE, newScore);
            db.update(DbHelper.TABLE_NAME,
                    contentValues,
                    DbHelper.NAME + " = '" + name + "';",
                    null);
        }
        return esMayor;
    }

    /**
     * Consulta si los datos del usuario son correctos
     *
     * @param name Nombre del usuario
     * @param pass Contraseña del usuario
     * @return true si el Nombre y la Contraseña son correctos
     */
    public boolean checkUserData(String name, String pass) {
        boolean check = false;
        String[] columns = {DbHelper.NAME, DbHelper.PASSWORD};
        Cursor cursor = myhelper.getWritableDatabase().query(DbHelper.TABLE_NAME,
                columns,
                DbHelper.NAME + "='" + name + "';",
                null, null, null, null);
        if (cursor.moveToNext()) {
            check = cursor.getString(cursor.getColumnIndex(DbHelper.PASSWORD)).equals(pass);
        }
        return check;
    }

    /**
     * Consulta la disponibilidad del nombre del usuario
     *
     * @param name Nombre del usuario
     * @return true si el nombre YA EXISTE para otro usuario
     */
    public boolean checkUserName(String name) {
        boolean check;
        String[] columns = {DbHelper.NAME};
        Cursor cursor = myhelper.getWritableDatabase().query(DbHelper.TABLE_NAME,
                columns,
                DbHelper.NAME + "='" + name + "';",
                null, null, null, null);
        check = cursor.moveToNext();
        return check;
    }

    /**
     * Consulta si el nuevo puntaje es mayor
     *
     * @param name     Nombre del usuario
     * @param newScore Puntaje a comparar
     * @param db       La base de datos donde se revisara
     * @return true si el newScore es mayor
     */
    private boolean isScoreMax(String name, int newScore, SQLiteDatabase db) {
        //TODO: test
        int valor = -1;
        String[] columns = {DbHelper.NAME, DbHelper.MAXSCORE};
        Cursor cursor = db.query(DbHelper.TABLE_NAME,
                columns,
                DbHelper.NAME + " = '" + name + "';",
                null, null, null, null);
        if (cursor.moveToNext()) {
            valor = cursor.getInt(cursor.getColumnIndex(DbHelper.MAXSCORE));
        }
        return valor < newScore;
    }
}
