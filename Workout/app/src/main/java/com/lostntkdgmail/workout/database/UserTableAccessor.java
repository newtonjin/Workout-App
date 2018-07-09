package com.lostntkdgmail.workout.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * An Accessor used to access the User table in the workout database
 */
public class UserTableAccessor extends DatabaseAccessor {
    private static final String TABLE_NAME = "user", TAG = "UserTableAccessor";
    private enum Columns {
        ID, FIRST_NAME, LAST_NAME //TODO: Add date last accessed? Default to that one?
    }
    private static final String[] col = {Columns.ID.name(), Columns.FIRST_NAME.name(), Columns.LAST_NAME.name()};

    /**
     * Creates an accessor for accessing the User table
     * @param context The current Context
     */
    public UserTableAccessor(Context context) {
        super(context, TABLE_NAME, col);
        if(select(null, null).getCount() == 0) {
            insert("Default","User");
        }
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
     * @return The id of the user
     */
    public long insert(String firstName, String lastName) {
        Log.d(TAG, "Inserting: \""+ firstName + ","+lastName+"\" into "+TABLE_NAME);
        ContentValues contentValues = new ContentValues();
        contentValues.put(Columns.FIRST_NAME.name(), firstName);
        contentValues.put(Columns.LAST_NAME.name(), lastName);

        long result = writableDb.insert(TABLE_NAME, null, contentValues);
        return result;
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
    public Cursor select(String firstName, String lastName, String sorting, int limit) {
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
        if(limit > 0) {
            sql.append(" LIMIT ").append(limit);
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
        return select(firstName,lastName,null,-1);
    }
    public String select(long userId) {
        Cursor cursor = readableDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+Columns.ID+" = "+userId,new String[0]);
        return cursor.getString(Columns.FIRST_NAME.ordinal()) + cursor.getString(Columns.LAST_NAME.ordinal());
    }
    /**
     * Gets all of the types inside of the table
     * @return An array containing all of the types
     */
    public String[] getAllIds() {
        String[] c = {Columns.FIRST_NAME.name(), Columns.LAST_NAME.name()};
        Cursor cursor = select(null, null);
        ArrayList<String> types = new ArrayList<>();
        while(cursor.moveToNext()) {
            types.add(cursor.getString(0));
        }
        cursor.close();
        return types.toArray(new String[types.size()]);
    }
}
