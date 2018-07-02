package com.lostntkdgmail.workout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;


public class LiftDatabaseAccessor extends DatabaseAccessor {
    private static String tableName = "lift";
    private static final String[] col = {"ID","Type","Lift"};

    public LiftDatabaseAccessor(Context context) {
        super(context, tableName, col);
    }
    public static void createTable(SQLiteDatabase db) {
        db.execSQL("create table " + tableName + " (" + col[0] + " INTEGER PRIMARY KEY AUTOINCREMENT," + col[1] + " TEXT," + col[2] + " TEXT)");
        Log.d("Debug","Created Table: "+tableName+"("+col[0]+","+col[1]+","+col[2]+")");
    }

    public boolean insert(String type,String lift) {
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d("Debug","Inserting: \"" + type +", "+ lift +"\" into \"" + tableName + "\"");
        ContentValues contentValues = new ContentValues();
        contentValues.put(col[1],type);
        contentValues.put(col[2],lift);

        long result = db.insert(tableName,null ,contentValues);
        if(result == -1) {
            Log.d("Debug", "Failed to inserted");
            return false;
        }
        Log.d("Debug", "Successfully inserted");
        return true;
    }
    public boolean updateData(String id,String type,String lift) {
        Log.d("Debug","Replacing id: " + id + " with: " + type +" "+ lift + " into " + tableName);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col[1],type);
        contentValues.put(col[2],lift);
        db.update(tableName, contentValues, "ID = ?",new String[] { id });
        return true;
    }
    public Cursor getCursor(String type, String lift, String sorting) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res;
        if(type != null && lift != null) {
            String[] selection = {type,lift};
            res = db.query(tableName, col, col[1] + " = ? and " + col[2] + " = ?", selection, null, null,sorting);
        }
        else if(type != null) {
            String[] selection = {type};
            res = db.query(tableName, col, col[1] + " = ?", selection, null, null,sorting);
        }
        else if(lift != null) {
            String[] selection = {lift};
            res = db.query(tableName, col, col[2] + " = ?", selection, null, null,sorting);
        }
        else
            res = db.query(tableName,col,null,null,null,null,sorting);
        return res;
    }
    public Cursor getCursor(String type, String lift) {
        return getCursor(type,lift,col[1]+" ASC");
    }
    public Boolean fillWithData() {
        String[] arms = {"Arms", "Arm Extensions", "Skull crunches", "Lean Over Curls", "Lawnmowers", "Close Grip Bench", "Dumbbell Curls", "Barbell Curls"};
        String[] back = {"Back", "Push Press", "Toe Touches", "Dead lift"};
        String[] chest = {"Chest", "Flys", "Pull Press", "Upright Rows", "Incline Bench", "Bench"};
        String[] forearms = {"Forearms", "Holding Weight", "Dangling Wrist Curls", "Wrist Curls"};
        String[] legs = {"Legs", "Dumbbell Lunges", "Barbell Lunges", "Standing Calf Raises", "Seated Calf Raises", "Leg Extensions", "Leg Press", "Leg Curls", "Front Squats", "Squats"};
        String[] shoulders = {"Shoulders", "Shrugs", "Shoulder Press"};
        String[][] lifts = {arms, back, chest, forearms, legs, shoulders};
        for (String[] arr : lifts) {
            String name = arr[0];
            arr[0] = null;
            for (String s : arr) {
                if (s != null) {
                    boolean b = insert(name, s);
                    if (!b)
                        return false;
                }
            }
        }
        return true;
    }
    public String[] getTypes() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] c = {col[1]};
        Cursor cursor = db.query(true,tableName,c,null,null,null,null,col[1]+" ASC",null);
        ArrayList<String> types = new ArrayList<>();
        while(cursor.moveToNext()) {
            types.add(cursor.getString(0));
        }
        cursor.close();
        return types.toArray(new String[types.size()]);
    }
    public String[] getLifts(String type) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] c = {col[2]};
        String[] sel = {type};
        Cursor cursor = db.query(true,tableName,c,col[1]+" =?",sel,null,null,col[1]+" ASC",null);
        ArrayList<String> types = new ArrayList<>();
        while(cursor.moveToNext()) {
            types.add(cursor.getString(0));
        }
        cursor.close();
        return types.toArray(new String[types.size()]);
    }

}
