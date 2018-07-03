package com.lostntkdgmail.workout.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lostntkdgmail.workout.R;

import java.util.ArrayList;

/**
 * An Accessor used to access the Lift table in the workout database
 */
public class LiftTableAccessor extends DatabaseAccessor {
    private static final String TABLE_NAME = "lift";
    private static final String TAG = "LiftTableAccessor";
    private enum Columns {
        ID, TYPE, LIFT
    }
    private static final String[] col = {Columns.ID.name(),Columns.TYPE.name(),Columns.LIFT.name()};

    /**
     * Creates an accessor for accessing the Lift table
     * @param context The current Context
     */
    public LiftTableAccessor(Context context) {
        super(context, TABLE_NAME, col);
    }

    /**
     * Creates the Lift table in the Workout database
     * @param db The Workout database
     */
    public static void createTable(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (" + Columns.ID.name() + " INTEGER PRIMARY KEY AUTOINCREMENT," + Columns.TYPE.name() + " TEXT," + Columns.LIFT.name() + " TEXT)");
        Log.d(TAG,"Created Table: "+ TABLE_NAME +"("+Columns.ID.name()+","+Columns.TYPE.name()+","+Columns.LIFT.name()+")");
    }

    /**
     * Inserts an entry into the database
     * @param type The type of lift
     * @param lift The name of the lift
     * @return True if it was successful
     */
    public boolean insert(String type,String lift) {
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d(TAG,"Inserting: \"" + type +", "+ lift +"\" into \"" + TABLE_NAME + "\"");
        ContentValues contentValues = new ContentValues();
        contentValues.put(Columns.TYPE.name(),type);
        contentValues.put(Columns.LIFT.name(),lift);

        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1) {
            Log.d(TAG, "Failed to inserted");
            return false;
        }
        Log.d(TAG, "Successfully inserted");
        return true;
    }

    /**
     * Updates an entry in the table
     * @param id The id of the entry
     * @param type The type of lift
     * @param lift The name of the lift
     * @return True if it was successful
     */
    public boolean updateData(String id,String type,String lift) {
        Log.d(TAG,"Replacing id: " + id + " with: " + type +" "+ lift + " into " + TABLE_NAME);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Columns.TYPE.name(),type);
        contentValues.put(Columns.LIFT.name(),lift);
        int num = db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        if(num > 0)
            return true;
        else {
            Log.d(TAG,"Update affected: " + num + " rows");
            return false;
        }
    }
    /**
     * Used to select things inside of the table
     * @param type The type of lift
     * @param lift The name of the lift
     * @param sorting The method for sorting the result
     * @return A Cursor object with all of the selected values
     */
    public Cursor getCursor(String type, String lift, String sorting) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res;
        if(type != null && lift != null) {
            String[] selection = {type,lift};
            res = db.query(TABLE_NAME, col, Columns.TYPE.name() + " = ? and " + Columns.LIFT.name() + " = ?", selection, null, null,sorting);
        }
        else if(type != null) {
            String[] selection = {type};
            res = db.query(TABLE_NAME, col, Columns.TYPE.name() + " = ?", selection, null, null,sorting);
        }
        else if(lift != null) {
            String[] selection = {lift};
            res = db.query(TABLE_NAME, col, Columns.LIFT.name() + " = ?", selection, null, null,sorting);
        }
        else
            res = db.query(TABLE_NAME,col,null,null,null,null,sorting);
        return res;
    }

    /**
     * Used to select things inside of the table, sorted by Type ascending
     * @param type The type of lift
     * @param lift The name of the lift
     * @return A Cursor object with all of the selected values
     */
    public Cursor getCursor(String type, String lift) {
        return getCursor(type,lift,Columns.TYPE.name()+" ASC");
    }

    /**
     * Initializes the table with default values
     * @return True if it was successful
     */
    public boolean fillWithData() {
        String[] arms = {Resources.getSystem().getString(R.string.arms), "Arm Extensions", "Skull crunches", "Lean Over Curls", "Lawnmowers", "Close Grip Bench", "Dumbbell Curls", "Barbell Curls"};
        String[] back = {Resources.getSystem().getString(R.string.back), "Pull Press", "Toe Touches", "Dead lift"};
        String[] chest = {Resources.getSystem().getString(R.string.chest), "Flys", "Push Press", "Upright Rows", "Incline Bench", "Bench"};
        String[] forearms = {Resources.getSystem().getString(R.string.forearms), "Holding Weight", "Dangling Wrist Curls", "Wrist Curls"};
        String[] legs = {Resources.getSystem().getString(R.string.legs), "Dumbbell Lunges", "Barbell Lunges", "Standing Calf Raises", "Seated Calf Raises", "Leg Extensions", "Leg Press", "Leg Curls", "Front Squats", "Squats"};
        String[] shoulders = {Resources.getSystem().getString(R.string.shoulders), "Shrugs", "Shoulder Press"};
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

    /**
     * Gets all of the types inside of the table
     * @return An array containing all of the types
     */
    public String[] getTypes() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] c = {Columns.TYPE.name()};
        Cursor cursor = db.query(true, TABLE_NAME,c,null,null,null,null,Columns.TYPE.name()+" ASC",null);
        ArrayList<String> types = new ArrayList<>();
        while(cursor.moveToNext()) {
            types.add(cursor.getString(0));
        }
        cursor.close();
        return types.toArray(new String[types.size()]);
    }

    /**
     * Gets an array of all lifts of the given type
     * @param type The type of lift
     * @return An array containing all lifts for the given type
     */
    public String[] getLifts(String type) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] c = {Columns.LIFT.name()};
        String[] sel = {type};
        Cursor cursor = db.query(true, TABLE_NAME,c,Columns.TYPE.name()+" =?",sel,null,null,Columns.TYPE.name()+" ASC",null);
        ArrayList<String> types = new ArrayList<>();
        while(cursor.moveToNext()) {
            types.add(cursor.getString(0));
        }
        cursor.close();
        return types.toArray(new String[types.size()]);
    }

}
