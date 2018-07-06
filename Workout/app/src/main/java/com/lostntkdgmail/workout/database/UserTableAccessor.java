package com.lostntkdgmail.workout.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * An Accessor used to access the User table in the workout database
 */
public class UserTableAccessor extends DatabaseAccessor {
    private static final String TABLE_NAME = "user", TAG = "UserTableAccessor";
    private enum Columns {
        ID, FIRST_NAME, LAST_NAME
    }
    private static final String[] col = {Columns.ID.name(), Columns.FIRST_NAME.name(), Columns.LAST_NAME.name()};

    /**
     * Creates an accessor for accessing the User table
     * @param context The current Context
     */
    public UserTableAccessor(Context context) {
        super(context, TABLE_NAME, col);
    }

    /**
     * Creates the User table in the Workout database
     * @param db The Workout database
     */
    public static void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + Columns.ID.name() + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Columns.FIRST_NAME.name() + " TEXT, " + Columns.LAST_NAME.name() + " TEXT)");
        Log.d(TAG, "Created Table: " + TABLE_NAME + "("+Columns.ID.name()+","+Columns.FIRST_NAME.name()+","+Columns.LAST_NAME+")");
    }

    /**
     * Inserts a user into the database
     * @param firstName The User's first name
     * @param lastName The User's last name
     * @return True if it was successful
     */
    public boolean insert(String firstName, String lastName) {
        Log.d(TAG, "Inserting: \""+ firstName + ","+lastName+"\" into "+TABLE_NAME);
        ContentValues contentValues = new ContentValues();
        contentValues.put(Columns.FIRST_NAME.name(), firstName);
        contentValues.put(Columns.LAST_NAME.name(), lastName);

        long result = writableDb.insert(TABLE_NAME, null, contentValues);
        if(result != -1) {
            Log.d(TAG, "Failed to insert");
            return false;
        }
        Log.d(TAG, "Successfully inserted");
        return true;
    }

    /**
     * Updates a User in the database
     * @param id The id of the User
     * @param firstName The new first name of the User
     * @param lastName The new last name of the User
     * @return True if it was successful
     */
    public boolean updateData(String id, String firstName, String lastName) {
        Log.d(TAG, "Replacing id: "+id+" with: "+firstName+" "+lastName);
        ContentValues contentValues = new ContentValues();
        contentValues.put(Columns.FIRST_NAME.name(), firstName);
        contentValues.put(Columns.LAST_NAME.name(), lastName);
        int num = writableDb.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});
        if(num > 0)
            return true;
        else {
            Log.d(TAG, "Update affected "+num + " rows");
            return false;
        }
    }

    /**
     * Used to select things inside of the table. Null values are acceptable for any/all parameters to reduce filtering
     * @param firstName The first name of the user
     * @param lastName The last name of the user
     * @param sorting The method for sorting the result
     * @return A Cursor object with all of the selected values
     */
    public Cursor select(String firstName, String lastName, String sorting) {
        StringBuilder sql = new StringBuilder();
        if(firstName != null && lastName != null) {
           sql.append("SELECT * FROM ").append(TABLE_NAME).append(" WHERE ").append(Columns.FIRST_NAME.name()).append(" = ").append(firstName).append(" AND ").append(Columns.LAST_NAME.name()).append(" = ").append(lastName);
        }
        else if(firstName == null && lastName == null) {
            sql.append("SELECT * FROM ").append(TABLE_NAME);
        }
        else if(firstName == null) {
            sql.append("SELECT * FROM ").append(TABLE_NAME).append(" WHERE ").append(Columns.LAST_NAME).append(" = ").append(lastName);
        }
        else {
            sql.append("SELECT * FROM ").append(TABLE_NAME).append(" WHERE ").append(Columns.FIRST_NAME).append(" = ").append(firstName);
        }
        if(sorting != null) {
            sql.append(" ORDER BY ").append(sorting);
        }
        return readableDb.rawQuery(sql.toString(), new String[0]);
    }
    /**
     * Used to select things inside of the table. Null values are acceptable for any/all parameters to reduce filtering
     * @param firstName The first name of the user
     * @param lastName The last name of the user
     * @return A Cursor object with all of the selected values
     */
    public Cursor select(String firstName, String lastName) {
        return select(firstName,lastName,null);
    }
}
