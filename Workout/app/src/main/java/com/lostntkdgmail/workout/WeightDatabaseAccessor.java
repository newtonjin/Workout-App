package com.lostntkdgmail.workout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class WeightDatabaseAccessor extends DatabaseAccessor {
    private static String tableName = "weight";
    private static final String[] col = {"ID","USER","DATE","TYPE","LIFT","WEIGHT","REPS"};

    public WeightDatabaseAccessor(Context context) {
        super(context,tableName, col);
    }
    public static void createTable(SQLiteDatabase db) {
        db.execSQL("create table " + tableName + " (" + col[0] + " INTEGER PRIMARY KEY AUTOINCREMENT," + col[1] + " TEXT," + col[2] + " TEXT," + col[3] + " TEXT," + col[4] + " TEXT," + col[5] + " INTEGER," + col[6] + " INTEGER)");
        Log.d("Debug","Created Table: "+tableName+"("+col[0]+","+col[1]+","+col[2]+col[3]+","+col[4]+","+col[5]+","+col[6]+")");
    }
    public boolean insert(String user,String type,String lift, int weight, int reps) {
        SQLiteDatabase db = this.getWritableDatabase();
        Date date = new Date();
        String printDate = new Date().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        printDate = printDate.substring(24)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+printDate.substring(8,10) ;
        Log.d("Debug","Inserting: \"" + user +", "+ printDate +", "+ type +", "+ lift +", "+ weight +", "+ reps + "\" into \"" + tableName + "\"");
        ContentValues contentValues = new ContentValues();
        contentValues.put(col[1],user);
        contentValues.put(col[2],printDate);
        contentValues.put(col[3],type);
        contentValues.put(col[4],lift);
        contentValues.put(col[5],weight);
        contentValues.put(col[6],reps);
        long result = db.insert(tableName,null ,contentValues);
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
    public Cursor getCursor(String user, String type, String lift, String sorting, String limit) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res;
        if(user != null) {
            if (type != null && lift != null) {
                String[] selection = {type, lift, user};
                res = db.query(tableName, col, col[3] + " = ? and " + col[4] + " = ? and " + col[1] + " = ?", selection, null, null,sorting,limit);
            }
            else if (type != null) {
                String[] selection = {type, user};
                res = db.query(tableName, col, col[3] + " = ? and " + col[1] + " = ?", selection, null, null,sorting,limit);
            }
            else if (lift != null) {
                String[] selection = {lift, user};
                res = db.query(tableName, col, col[4] + " = ? and " + col[1] + " = ?", selection, null, null,sorting,limit);
            }
            else {
                String[] selection = { user};
                res = db.query(tableName, col, col[1] + " = ?", selection, null, null,sorting,limit);
            }
        }
        else {
            if(type != null && lift != null) {
                String[] selection = {lift, type};
                res = db.query(tableName, col, col[4] + " = ? and " + col[3] + " = ?", selection, null, null, sorting,limit);
            }
            else if(type != null) {
                String[] selection = {type};
                res = db.query(tableName, col, col[3] + " = ?", selection, null, null,sorting,limit);
            }
            else if(lift != null) {
                String[] selection = {lift};
                res = db.query(tableName, col, col[4] + " = ?", selection, null, null,sorting,limit);
            }
            else {
                res = db.query(tableName, col, null, null, null, null, sorting,limit);
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
        return getCursor(user,type,lift, col[1] + " ASC, " + col[2] + " ASC, " + col[3] + " ASC, " + col[4] + " ASC, " + col[0] + " ASC",null);
    }
    public boolean updateData(String id,String user,String date, String type,String lift, int weight, int reps) {
        Log.d("Debug","Replacing id: " + id + " with: " + user +" "+ date +" "+ type +" "+ lift +" "+ weight +" "+ reps + " into " + tableName);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col[1],user);
        contentValues.put(col[2],date);
        contentValues.put(col[3],type);
        contentValues.put(col[4],lift);
        contentValues.put(col[5],weight);
        contentValues.put(col[6],reps);
        db.update(tableName, contentValues, "ID = ?",new String[] { id });
        return true;
    }
}
