package com.lostntkdgmail.workout;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public abstract class DatabaseAccessor extends SQLiteOpenHelper {
    protected static String databaseName;
    protected static String tableName;
    protected static String[] col;

    public DatabaseAccessor(Context context, String name,String[]cols) {
        super(context, databaseName, null, 1);
        databaseName = name.toLowerCase() + ".db";
        tableName = name;
        col = cols;
        Log.d("Debug", databaseName +" object created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Debug","onCreate called for: " + tableName);
        if(this instanceof WeightDatabaseAccessor) {
            db.execSQL("create table " + tableName + " (" + col[0] + " INTEGER PRIMARY KEY AUTOINCREMENT," + col[1] + " TEXT," + col[2] + " TEXT," + col[3] + " TEXT," + col[4] + " TEXT," + col[5] + " INTEGER," + col[6] + " INTEGER)");
            Log.d("Debug","Created Table: "+tableName+"("+col[0]+","+col[1]+","+col[2]+col[3]+","+col[4]+","+col[5]+","+col[6]+")");
        }
        else if(this instanceof LiftDatabaseAccessor) {
            db.execSQL("create table " + tableName + " (" + col[0] + " INTEGER PRIMARY KEY AUTOINCREMENT," + col[1] + " TEXT," + col[2] + " TEXT)");
            Log.d("Debug","Created Table: "+tableName+"("+col[0]+","+col[1]+","+col[2]+")");
        }
        else {
            Log.d("Debug","column.length does not match for Database: "+databaseName);
            throw new RuntimeException("Length Mismatched");
        }
        Log.d("Debug","Database: "+ tableName + " Created at: "+databaseName);
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
