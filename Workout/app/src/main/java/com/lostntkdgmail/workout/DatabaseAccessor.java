package com.lostntkdgmail.workout;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public abstract class DatabaseAccessor extends SQLiteOpenHelper {
    public static final String databaseName = "Workout.db";
    private String tableName;
    private String[] col;

    public DatabaseAccessor(Context context, String name,String[]cols) {
        super(context, databaseName, null, 1);
        tableName = name;
        col = cols;
        Log.d("Debug", databaseName.substring(0,databaseName.length()-3) +"."+tableName+" object created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        WeightDatabaseAccessor.createTable(db);
        LiftDatabaseAccessor.createTable(db);
    }

    /**
     * Creates the table in the Database. Must be overriden in the table class extinding this one
     * @param db database to create the table in
     */
    public static void createTable(SQLiteDatabase db) {
        throw new RuntimeException("This method must be overridden");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("Debug","Upgrading Database: "+ tableName);
        db.execSQL("DROP TABLE IF EXISTS "+ tableName);
        onCreate(db);
    }

    public int getSize() {
        return getReadableDatabase().query(tableName, col, null, null, null, null, null).getCount();
    }
    public ArrayList<String> getAllData() {
        Cursor cursor = getReadableDatabase().query(tableName, col, null, null, null, null, null);
        ArrayList<String> result = new ArrayList<String>();

        while(cursor.moveToNext()) {
            String s = "";
            int i = 0;
            for(; i < col.length-1; i++) {
                s += cursor.getString(i) + ",";
            }
            s += cursor.getString(i);
            result.add(s);
        }
        return result;
    }

    public Integer deleteData (String id) {
        Log.d("Debug","Deleting data at id: " + id);
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName, "ID = ?",new String[] {id});
    }
}
