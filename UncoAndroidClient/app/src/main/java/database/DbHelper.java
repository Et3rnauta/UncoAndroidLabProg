package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_Version = 1;    // Database Version
    static final String DATABASE_NAME = "myDatabase",    // Database Name
            TABLE_NAME = "myTable",   // Table Name
            UID = "_id",     // Column I (Primary Key)
            NAME = "Name",    //Column II
            PASSWORD = "Password",    // Column III
            MAXSCORE = "MaxScore",    // Column III
            CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                    " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + NAME + " VARCHAR(255) ,"
                    + PASSWORD + " VARCHAR(225),"
                    + MAXSCORE + " INTEGER);",
            DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private Context context;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        } catch (Exception e) {
            System.out.println("Error al crear la base de datos");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        } catch (Exception e) {
            System.out.println("Error al modificar la base de datos");
        }
    }
}
