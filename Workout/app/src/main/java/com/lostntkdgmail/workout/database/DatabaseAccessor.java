package com.lostntkdgmail.workout.database;

/* Created by Tyler Atkinson
 * Workout-App
 * https://github.com/tha7556/Workout-App
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

/**
 * A database accessor used to access the workout database
 */
public abstract class DatabaseAccessor extends SQLiteOpenHelper {
    /**
     * The name of the Database
     */
    public static final String DATABASE_NAME = "Workout.db";
    private static final String TAG = "DatabaseAccessor";
    private final String TABLE_NAME;
    private final String[] col;
    protected SQLiteDatabase readableDb, writableDb;
    protected Resources resources;



    /**
     * Creates a new DatabaseAccessor
     * @param context The current context
     * @param name The name of the table inside of the database
     * @param cols An array containing the names of each column
     */
    public DatabaseAccessor(Context context, String name,String[] cols) {
        super(context, DATABASE_NAME, null, 1);
        TABLE_NAME = name;
        col = cols;
        Log.d(TAG, DATABASE_NAME.substring(0, DATABASE_NAME.length()-3) +"."+ TABLE_NAME +" accessor created");
        readableDb = getReadableDatabase();
        writableDb = getWritableDatabase();
        resources = context.getResources();
    }

    /**
     * Specifies what should happen the first time that the database is created. Creates the Weight and Lift tables
     * @param db The SQLiteDatabase being created
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        WeightTableAccessor.createTable(db);
        LiftTableAccessor.createTable(db);
        UserTableAccessor.createTable(db);
    }
    /**
     * Specifies what should happen when the schema is upgraded
     * @param db The database being upgraded
     * @param oldVersion The old version number
     * @param newVersion The new version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //TODO: This wipes the database completely, modifying would be better
        if(oldVersion < newVersion) {
            Log.d(TAG, "Upgrading Database: " + TABLE_NAME + " from version: " + oldVersion + " to version: " + newVersion);
            //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            //onCreate(db);
            Cursor oldDB = db.query(TABLE_NAME, null, null, null, null, null, null);
            Cursor newDB = readableDb.query(TABLE_NAME, null, null, null, null, null, null);
            int cols = oldDB.getColumnCount();
            int rows = oldDB.getCount();
            if (newDB.getColumnCount() != cols && newDB.getCount() != rows) {
                while (oldDB.moveToNext()) {
                    ContentValues cv = new ContentValues();
                    cv.put(WeightTableAccessor.Columns.USER.name(), oldDB.getString(0));
                    cv.put(WeightTableAccessor.Columns.DATE.name(), oldDB.getString(1));
                    cv.put(WeightTableAccessor.Columns.TYPE.name(), oldDB.getString(2));
                    cv.put(WeightTableAccessor.Columns.LIFT.name(), oldDB.getString(3));
                    cv.put(WeightTableAccessor.Columns.WEIGHT.name(), oldDB.getString(4));
                    cv.put(WeightTableAccessor.Columns.REPS.name(), oldDB.getString(5));
                    writableDb.insert(TABLE_NAME, null, null);
                }
            }
        }
    }

    /**
     * Gets the number of Rows inside of the table
     * @return The number of rows
     */
    public int getNumberOfRows() {
        return readableDb.query(TABLE_NAME, col, null, null, null, null, null).getCount();
    }

    /**
     * Gets all of the data stored inside of the table
     * @return The data in the table in an ArrayList
     */
    public ArrayList<String> getAllData() {
        Cursor cursor = readableDb.query(TABLE_NAME, col, null, null, null, null, null);
        ArrayList<String> result = new ArrayList<>();

        while(cursor.moveToNext()) {
            StringBuilder builder = new StringBuilder();
            int i = 0;
            for(; i < col.length-1; i++) {
                builder.append(cursor.getString(i)).append(",");
            }
            builder.append(cursor.getString(i));
            result.add(builder.toString());
        }
        cursor.close();
        return result;
    }

    /**
     * Deletes an entry with the given id
     * @param id The id of the entry to delete
     * @return The number of rows affected by the delete
     */
    public int deleteData (String id) {
        Log.d(TAG,"Deleting data at id: " + id);
        return writableDb.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

    /**
     * Gets all of the column names inside of the table
     * @return An array of all the column names
     */
    public String[] getColumnNames() {
        return col;
    }

    /**
     * Overrides close to add a log statement, other than that, no differences
     */
    @Override
    public void close() {
        super.close();
        readableDb.close();
        writableDb.close();
        Log.d(TAG,"Closing: "+ DATABASE_NAME.substring(0, DATABASE_NAME.length()-3) +"."+ TABLE_NAME + " accessor");
    }
}
