package userdatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import userdatabase.tables.userWeightTables;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    /**
    *  1. save name of database
    *  2. version of current database
    * */
    public static final String databaseName = "userData.db";
    public static final int DB_VER = 1;

    public MyDatabaseHelper(Context context) {
        super(context, databaseName, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * Executes the SQL command from userWeightTables.java which should create a database (table) for the user
         **/
        db.execSQL(userWeightTables.CMD_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
