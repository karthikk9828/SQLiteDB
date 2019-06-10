package com.example.sqlitedb;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactsDB {

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "person_name";
    public static final String KEY_PHONE = "_phone";

    private final String DATABASE_NAME = "ContactsDB";
    private final String DATABASE_TABLE = "ContactsTable";

    private final int DATABASE_VERSION = 1;

    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public ContactsDB(Context context) {

        ourContext = context;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        /**
         * called when the database version is changed
         *
         * @param db
         * @param oldVersion
         * @param newVersion
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }

        /**
         * called only when the database is created for the first time
         * @param db
         */
        @Override
        public void onCreate(SQLiteDatabase db) {

            String query = "CREATE TABLE " + DATABASE_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NAME + " TEXT NOT NULL, " +
                    KEY_PHONE + " TEXT NOT NULL);";

            db.execSQL(query);
        }
    }

    public ContactsDB open() throws SQLException {

        ourHelper = new DBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();

        return this;
    }

    public void close() {
        ourHelper.close();
    }

    public long insertRow(String name, String phone) {

        ContentValues cv = new ContentValues();

        cv.put(KEY_NAME, name);
        cv.put(KEY_PHONE, phone);

        return ourDatabase.insert(DATABASE_TABLE, null, cv);
    }

    public String getData() {

        String[] columns = new String[] {KEY_ID, KEY_NAME, KEY_PHONE};

        Cursor cursor = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);

        String result = "";

        int iId = cursor.getColumnIndex(KEY_ID);
        int iName = cursor.getColumnIndex(KEY_NAME);
        int iPhone = cursor.getColumnIndex(KEY_PHONE);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            result = result + cursor.getString(iId) + ": " + cursor.getString(iName) + " " +
                    cursor.getString(iPhone) + "\n";
        }

        cursor.close();

        return result;
    }

    public long deleteRow(String rowId) {

        return ourDatabase.delete(DATABASE_TABLE, KEY_ID + " = ?", new String[] {rowId});
    }

    public long updateRow(String rowId, String name, String phone) {

        ContentValues cv = new ContentValues();

        cv.put(KEY_NAME, name);
        cv.put(KEY_PHONE, phone);

        return ourDatabase.update(DATABASE_TABLE, cv, KEY_ID + " = ?", new String[] {rowId});
    }
}
