package com.lostntkdgmail.workout.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

/**
 * An Accessor used to access the Weight Table in the Workout database
 */
public class WeightTableAccessor extends DatabaseAccessor {
    private enum Columns {
        ID, USER, DATE, TYPE, LIFT, WEIGHT, REPS
    }
    private static final String TABLE_NAME = "weight";
    private static final String TAG = "WeightTableAccess";
    private static final String[] cols = {Columns.ID.name(), Columns.USER.name(),Columns.DATE.name(),Columns.TYPE.name(),Columns.LIFT.name(),Columns.WEIGHT.name(),Columns.REPS.name()};

    /**
     * Creates an accessor for accessing the Weight Table
     * @param context The current context
     */
    public WeightTableAccessor(Context context) {
        super(context, TABLE_NAME, cols);
    }

    /**
     * Creates the table for the first time
     * @param db The database to add the table to
     */
    public static void createTable(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (" + Columns.ID.name() + " INTEGER PRIMARY KEY AUTOINCREMENT," + Columns.USER.name() + " TEXT," + Columns.DATE.name() +
                " TEXT," + Columns.TYPE.name() + " TEXT," + Columns.LIFT.name() + " TEXT," + Columns.WEIGHT.name() + " INTEGER," + Columns.REPS.name() + " INTEGER)");

        Log.d(TAG,"Created Weight Table");
    }

    /**
     * Inserts an entry into the table
     * @param user The current User
     * @param type The type of lift
     * @param lift The name of the lift
     * @param weight The weight being lifted
     * @param reps The number of reps
     * @return True if the insertion was successful
     */
    public boolean insert(String user,String type,String lift, int weight, int reps) {
        SQLiteDatabase db = this.getWritableDatabase();
        Date date = new Date();
        String printDate = new Date().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        printDate = printDate.substring(24)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+printDate.substring(8,10) ;
        Log.d(TAG,"Inserting: \"" + user +", "+ printDate +", "+ type +", "+ lift +", "+ weight +", "+ reps + "\" into \"" + TABLE_NAME + "\"");
        ContentValues contentValues = new ContentValues();
        contentValues.put(Columns.USER.name(),user);
        contentValues.put(Columns.DATE.name(),printDate);
        contentValues.put(Columns.TYPE.name(),type);
        contentValues.put(Columns.LIFT.name(),lift);
        contentValues.put(Columns.WEIGHT.name(),weight);
        contentValues.put(Columns.REPS.name(),reps);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1) {
            Log.d(TAG, "Failed to inserted");
            return false;
        }
        Log.d(TAG, "Successfully inserted");
        return true;
    }
    /**
     * Used to select things from inside of the table. Values can be left at null to not filter the data by those columns, however user, type, and lift cannot all be null. One has to be non null.
     * @param user The user to be selected
     * @param type The type of lift to be selected
     * @param lift The lift to be selected
     * @param sorting SQLite sorting algorithm
     * @return Cursor object with all selected values
     */
    public Cursor select(String user, String type, String lift, String sorting, String limit) { //TODO: change limit to int eventually
        Log.d(TAG, "select called");
        if(user == null && type == null && lift == null) {
            Log.d(TAG, "All values passed to select are null");
            return null;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT * FROM "+TABLE_NAME + " WHERE");
        if(user != null) {
            builder.append(" ").append(Columns.USER.name()).append(" = '").append(user).append("'");
            if(type != null || lift != null)
                builder.append(" AND");
        }
        if(type != null) {
            builder.append(" ").append(Columns.TYPE.name()).append(" = '").append(type).append("'");
            if(lift != null)
                builder.append(" AND");
        }
        if(lift != null)
            builder.append(" ").append(Columns.LIFT.name()).append(" = '").append(lift).append("'");
        if(sorting != null)
            builder.append(" ORDER BY ").append(sorting);
        if(Integer.parseInt(limit) > -1)
            builder.append(" LIMIT ").append(limit);
        String sql = builder.toString();
        Log.d(TAG, "Executing query: "+ sql);
        return db.rawQuery(sql, new String[0]);
    }
    /**
     * Used to select things from inside of the table. Values can be left at null to not filter the data by those columns, however user, type, and lift cannot all be null. One has to be non null.
     * Defaults to no limit on output, and sorts by User ASC, Type ASC, Lift ASC, DATE ASC, Id ASC
     * @param user The user to be selected
     * @param type The type of lift to be selected
     * @param lift The lift to be selected
     * @return Cursor object with all selected values
     */
    public Cursor select(String user, String type, String lift) {
        return select(user,type,lift, Columns.USER.name() + " ASC, " + Columns.TYPE.name() + " ASC, " + Columns.LIFT.name() + " ASC, " + Columns.DATE+ " ASC, " + Columns.ID + " ASC",null);
    }

    /**
     * Updates an entry inside of the table
     * @param id The id of the entry being updated
     * @param user The current User
     * @param date The date of the lift
     * @param type The type of lift
     * @param lift The name of the lift
     * @param weight The weight being lifted
     * @param reps The number of reps
     * @return True if the update was successful
     */
    public boolean updateData(String id,String user,String date, String type,String lift, int weight, int reps) {
        Log.d(TAG,"Replacing id: " + id + " with: " + user +" "+ date +" "+ type +" "+ lift +" "+ weight +" "+ reps + " into " + TABLE_NAME);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Columns.USER.name(),user);
        contentValues.put(Columns.DATE.name(),date);
        contentValues.put(Columns.TYPE.name(),type);
        contentValues.put(Columns.LIFT.name(),lift);
        contentValues.put(Columns.WEIGHT.name(),weight);
        contentValues.put(Columns.REPS.name(),reps);
        int num = db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        if(num > 0)
            return true;
        else {
            Log.d(TAG,"Update affected: " + num + " rows");
            return false;
        }
    }
}
