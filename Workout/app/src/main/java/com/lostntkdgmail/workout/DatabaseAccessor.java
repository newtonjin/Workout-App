package com.lostntkdgmail.workout;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public abstract class DatabaseAccessor extends SQLiteOpenHelper {
    protected static String databaseName = "Name.db";
    protected static String tableName = "Name";
    protected static String[] col = {};

    public DatabaseAccessor(Context context) {
        super(context, databaseName, null, 1);
        Log.d("Debug", databaseName +" Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if(col.length == 7)
            db.execSQL("create table " + tableName +" ("+ col[0]+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ col[1]+" TEXT,"+ col[2]+" TEXT,"+ col[3]+" TEXT,"+ col[4]+" TEXT,"+ col[5]+" INTEGER,"+ col[6]+" INTEGER)");
        else if(col.length == 3)
            db.execSQL("create table " + tableName +" ("+ col[0]+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ col[1]+" TEXT,"+ col[2]+" TEXT)");
        else {
            Log.d("Debug","col.length does not match for Database: "+databaseName);
            throw new RuntimeException("Length Mismatched");
        }
        Log.d("Debug","Database: "+ tableName + " Created");
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
            result.add(cursor.getString(0) + "," + cursor.getString(1) + "," + cursor.getString(2) + "," +
                    cursor.getString(3) + "," + cursor.getString(4) + "," + cursor.getString(5) + "," + cursor.getString(6));
        }
        return result;
    }

    public Integer deleteData (String id) {
        Log.d("Debug","Deleting data at id: " + id);
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName, "ID = ?",new String[] {id});
    }
}
