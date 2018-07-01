package userdatabase.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.weightogo.weightactivity;

import java.util.ArrayList;

import userdatabase.MyDatabaseHelper;

public class userWeightTables {

    public static final String TAG = "DATABASE";
    public static final String TABLE_NAME = "userweight";
    public static final String COL_1 = "weight";

    public static final String CMD_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS   "
            + TABLE_NAME
            + "( id INTEGER PRIMARY KEY AUTOINCREMENT , weight REAL ) ;";

    public static void addWeight(SQLiteDatabase db, double weight) {
        if (db.isReadOnly()) {
            Log.w(TAG, "Not able to update database");
            return;
        }
        /**
         * in column named weight, insert second argument weight
         * **/
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, weight);

        /**
         * insert the column into the database
         */
        db.insert(TABLE_NAME, null, contentValues);
    }




    public static void removeWeight(SQLiteDatabase db, double weight){
        if(db.isReadOnly()){
            Log.w(TAG, "Not writable");
            return;
        }
        db.delete(TABLE_NAME, "weight = ?", new String[]{""+weight});
    }

    public static ArrayList<Double> fetchWeight(SQLiteDatabase db) {
        Cursor c = db.query(
                TABLE_NAME,
                new String[]{COL_1},
                null, null, null, null, null
        );
        ArrayList<Double> weights = new ArrayList<>();
        if (c.moveToFirst()) {
            // returns index from the column
            int taskColindex = c.getColumnIndex(COL_1);
            do {
                weights.add(c.getDouble(taskColindex));
            } while (c.moveToNext());
        }
        c.close();
        return weights;
    }


}
