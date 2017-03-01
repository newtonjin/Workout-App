package com.lostntkdgmail.workout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;


public abstract class DatabaseAccessor extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Weight.db";
    public static final String TABLE_NAME = "weight";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USER";
    public static final String COL_3 = "DATE";
    public static final String COL_4 = "TYPE";
    public static final String COL_5 = "LIFT";
    public static final String COL_6 = "WEIGHT";
    public static final String COL_7 = "REPS";
    public static final String[] COLUMNS = {COL_1,COL_2,COL_3,COL_4,COL_5,COL_6,COL_7};

    public DatabaseAccessor(Context context) {
        super(context, DATABASE_NAME, null, 1);
        Log.d("Debug",TABLE_NAME+" Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" ("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL_2+" TEXT,"+COL_3+" TEXT,"+COL_4+" TEXT,"+COL_5+" TEXT,"+COL_6+" INTEGER,"+COL_7+" INTEGER)");
        Log.d("Debug","Database: "+ TABLE_NAME + " Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("Debug","Upgrading Database");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insert(String user,String type,String lift, int weight, int reps) {
        SQLiteDatabase db = this.getWritableDatabase();
        String date = new Date().toString();
        date = date.substring(4,10)+ date.substring(23);
        Log.d("Debug","Inserting: \"" + user +", "+ date +", "+ type +", "+ lift +", "+ weight +", "+ reps + "\" into \"" + TABLE_NAME + "\"");
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,user);
        contentValues.put(COL_3,date);
        contentValues.put(COL_4,type);
        contentValues.put(COL_5,lift);
        contentValues.put(COL_6,weight);
        contentValues.put(COL_7,reps);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1) {
            Log.d("Debug", "Failed to inserted");
            return false;
        }
        Log.d("Debug", "Successfully inserted");
        return true;
    }
    /**
     *
     * @param user The user to be selected (Can be left null to return all users)
     * @param type The type of lift to be selected (Can be left null to return all types)
     * @param lift The lift to be selected (Can be left null to return all lifts)
     * @param sorting SQLite sorting algorithm
     * @return Cursor object with all selected values
     */
    public Cursor getCursor(String user, String type, String lift, String sorting) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        if(user != null) {
            if (type != null && lift != null) {
                String[] selection = {type, lift, user};
                res = db.query(TABLE_NAME, COLUMNS, COL_4 + " = ? and " + COL_5 + " = ? and " + COL_2 + " = ?", selection, null, null,sorting);
            }
            else if (type != null) {
                String[] selection = {type, user};
                res = db.query(TABLE_NAME, COLUMNS, COL_4 + " = ? and " + COL_2 + " = ?", selection, null, null,sorting);
            }
            else if (lift != null) {
                String[] selection = {lift, user};
                res = db.query(TABLE_NAME, COLUMNS, COL_5 + " = ? and " + COL_2 + " = ?", selection, null, null,sorting);
            }
            else {
                String[] selection = { user};
                res = db.query(TABLE_NAME, COLUMNS, COL_2 + " = ?", selection, null, null,sorting);
            }
        }
        else {
            if(type != null && lift != null) {
                String[] selection = {lift, type};
                res = db.query(TABLE_NAME, COLUMNS, COL_5 + " = ? and " + COL_4 + " = ?", selection, null, null, sorting);
            }
            else if(type != null) {
                String[] selection = {type};
                res = db.query(TABLE_NAME, COLUMNS, COL_4 + " = ?", selection, null, null,sorting);
            }
            else if(lift != null) {
                String[] selection = {lift};
                res = db.query(TABLE_NAME, COLUMNS, COL_5 + " = ?", selection, null, null,sorting);
            }
            else {
                res = db.query(TABLE_NAME, COLUMNS, null, null, null, null, sorting);
            }
        }
        return res;
    }
    /**
     *
     * @param user The user to be selected (Can be left null to return all users)
     * @param type The type of lift to be selected (Can be left null to return all types)
     * @param lift The lift to be selected (Can be left null to return all lifts)
     * @return Cursor object with all selected values
     */
    public Cursor getCursor(String user, String type, String lift) {
        return getCursor(user,type,lift,COL_2 + " ASC, " +COL_4 + " ASC, " + COL_5 + " ASC, " + COL_3 + " ASC");
    }
    public int getSize() {
        return getCursor(null,null,null).getCount();
    }
    public ArrayList<String> getAllData() {

        Cursor cursor = getCursor(null,null,null,null);
        ArrayList<String> result = new ArrayList<String>();
        while(cursor.moveToNext()) {
            result.add(cursor.getString(0) + "," + cursor.getString(1) + "," + cursor.getString(2) + "," +
                    cursor.getString(3) + "," + cursor.getString(4) + "," + cursor.getString(5) + "," + cursor.getString(6));
        }
        return result;
    }

    public boolean updateData(String id,String user,String date, String type,String lift, int weight, int reps) {
        Log.d("Debug","Replacing id: " + id + " with: " + user +" "+ date +" "+ type +" "+ lift +" "+ weight +" "+ reps + " into " + TABLE_NAME);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,user);
        contentValues.put(COL_3,date);
        contentValues.put(COL_4,type);
        contentValues.put(COL_5,lift);
        contentValues.put(COL_6,weight);
        contentValues.put(COL_7,reps);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Integer deleteData (String id) {
        Log.d("Debug","Deleting data at id: " + id);
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }
}
