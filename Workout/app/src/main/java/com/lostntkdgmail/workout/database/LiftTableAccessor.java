package com.lostntkdgmail.workout.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.main.MainActivity;
import com.lostntkdgmail.workout.view.EventObjects;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * An Accessor used to access the Lift table in the workout database
 */
public class LiftTableAccessor extends DatabaseAccessor {
    private static final String TABLE_NAME = "lift";
    private static final String TAG = "LiftTableAccessor";
    public String[] biceps = new String[]{resources.getString(R.string.biceps), "Concentration Curls", "Dumbbell Curls", "Barbell Curls"};
    public String[] triceps = new String[]{resources.getString(R.string.triceps), "Overhead Extensions", "Skull crushers", "Close Grip Bench", "Pulley Pushdowns"};
    public String[] back = new String[]{resources.getString(R.string.back), "Lawnmowers", "Seated Rows", "Straight Arm Push Downs"};
    public String[] chest = new String[]{resources.getString(R.string.chest), "Flys", "Incline Flys", "Incline Bench", "Bench"};
    public String[] forearms = new String[]{resources.getString(R.string.forearms), "Dangling Wrist Curls", "Wrist Curls"};
    public String[] legs = new String[]{resources.getString(R.string.legs), "Lunges", "Deadlifts", "Calf Raises", "Leg Extensions", "Leg Press", "Leg Curls", "Front Squats", "Squats"};
    public String[] shoulders = new String[]{resources.getString(R.string.shoulders), "Shrugs", "Overhead Press", "Arnold OHP", "Lateral Raises"};
    String[][] lifts = {biceps, triceps, back, chest, forearms, legs, shoulders};

    /**
     * The columns of the table
     */
    public enum Columns {
        ID, TYPE, LIFT
    }

    private static final String[] col = {Columns.ID.name(),Columns.TYPE.name(),Columns.LIFT.name()};

    /**
     * Creates an accessor for accessing the Lift table
     * @param context The current Context
     */
    public LiftTableAccessor(Context context) {
        super(context, TABLE_NAME, col);
        if(getNumberOfRows() < 1) {
            fillWithData();
        }
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

        Log.d(TAG,"Inserting: \"" + type +", "+ lift +"\" into \"" + TABLE_NAME + "\"");
        ContentValues contentValues = new ContentValues();
        contentValues.put(Columns.TYPE.name(),type);
        contentValues.put(Columns.LIFT.name(),lift);

        long result = writableDb.insert(TABLE_NAME,null ,contentValues);
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
        ContentValues contentValues = new ContentValues();
        contentValues.put(Columns.TYPE.name(),type);
        contentValues.put(Columns.LIFT.name(),lift);
        int num = writableDb.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
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
    public Cursor select(String type, String lift, String sorting) {
        Cursor res;
        if(type != null && lift != null) {
            String[] selection = {type,lift};
            res = readableDb.query(TABLE_NAME, col, Columns.TYPE.name() + " = ? and " + Columns.LIFT.name() + " = ?", selection, null, null,sorting);
        }
        else if(type != null) {
            String[] selection = {type};
            res = readableDb.query(TABLE_NAME, col, Columns.TYPE.name() + " = ?", selection, null, null,sorting);
        }
        else if(lift != null) {
            String[] selection = {lift};
            res = readableDb.query(TABLE_NAME, col, Columns.LIFT.name() + " = ?", selection, null, null,sorting);
        }
        else
            res = readableDb.query(TABLE_NAME,col,null,null,null,null,sorting);
        return res;
    }

    public List<EventObjects> getAllLifts(){
        Date dateToday = new Date();
        List<EventObjects> events = new ArrayList<>();
        String[] c = {Columns.LIFT.name()};
        Cursor cursor = readableDb.query(true, TABLE_NAME,c,Columns.TYPE.name()+" =?",null,null,null,Columns.TYPE.name()+" ASC",null);
        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String message = cursor.getString(cursor.getColumnIndexOrThrow("message"));
                String startDate = cursor.getString(cursor.getColumnIndexOrThrow("start_date"));
                //convert start date to date object
                Date reminderDate = convertStringToDate(startDate);
                if(reminderDate.after(dateToday) || reminderDate.equals(dateToday)){
                    events.add(new EventObjects(id, message, reminderDate));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return events;
    }

    private Date convertStringToDate(String dateInString){
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dateInString);
        } catch (ParseException e) {
            System.out.println("ERROR IN LIFT TABLE ACCESSOR CONVERT STRING TO DATE");
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Used to select things inside of the table, sorted by Type ascending
     * @param type The type of lift
     * @param lift The name of the lift
     * @return A Cursor object with all of the selected values
     */
    public Cursor select(String type, String lift) {
        return select(type,lift,Columns.TYPE.name()+" ASC");
    }

    /**
     * Initializes the table with default values
     * @return True if it was successful
     */
    public boolean fillWithData() {
        String[][] lifts = {biceps, triceps, back, chest, forearms, legs, shoulders};

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

    public boolean addLift(String type, String lift) {
        for(int i = 0; i < lifts.length; i++) {
            if(lifts[i][0].equals(type)){
                String[] temp = new String[lifts[i].length + 2];
                for(int j = 0; j < lifts[i].length; j++) {
                    temp[j] = lifts[i][j];
                }
                temp[lifts[i].length + 1] = lift;
                lifts[i] = temp;
                boolean b = insert(type, lift);
                if(b) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deleteLift(String type, String lift) {
       ArrayList<String> list = new ArrayList<>(Arrays.asList(MainActivity.liftTable.getLifts(type)));
       int typeIndex = -1;
       for(int i = 0; i < MainActivity.liftTable.lifts.length; i++) {
           if(MainActivity.liftTable.lifts[i][0].equals(type)){
               typeIndex = i;
               break;
           }
       }
       if(list.contains(lift)) {
           int index = 0;
           list.remove(lift);
           if(typeIndex >= 0) {
               
           }
       }
        return false;
    }

    /**
     * Gets all of the types inside of the table
     * @return An array containing all of the types
     */
    public String[] getTypes() {
        String[] c = {Columns.TYPE.name()};
        Cursor cursor = readableDb.query(true, TABLE_NAME,c,null,null,null,null,Columns.TYPE.name()+" ASC",null);
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
         Log.d(TAG, "Getting lifts for: "+type);
         String[] c = {Columns.LIFT.name()};
         String[] sel = {type};
         Cursor cursor = readableDb.query(true, TABLE_NAME,c,Columns.TYPE.name()+" =?",sel,null,null,Columns.TYPE.name()+" ASC",null);
         ArrayList<String> types = new ArrayList<>();
         while(cursor.moveToNext()) {
             types.add(cursor.getString(0));
         }
         cursor.close();
         return types.toArray(new String[types.size()]);
     }

    public String[][] getLifts() {
        return lifts;
    }

}
