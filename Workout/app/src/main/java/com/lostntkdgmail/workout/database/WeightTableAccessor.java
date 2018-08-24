package com.lostntkdgmail.workout.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lostntkdgmail.workout.main.MainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * An Accessor used to access the Weight Table in the Workout database
 */
public class WeightTableAccessor extends DatabaseAccessor {
    public enum Columns {
        ID, USER, DATE, TYPE, LIFT, WEIGHT, REPS
    }
    private static final String TABLE_NAME = "weight";
    private static final String TAG = "WeightTableAccess";
    private static final String[] cols = {Columns.ID.name(), Columns.USER.name(),Columns.DATE.name(),Columns.TYPE.name(),Columns.LIFT.name(),Columns.WEIGHT.name(),Columns.REPS.name()};
    private static final DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

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
        db.execSQL("create table " + TABLE_NAME + " (" + Columns.ID.name() + " INTEGER PRIMARY KEY AUTOINCREMENT," + Columns.USER.name() + " LONG," + Columns.DATE.name() +
                " DATE," + Columns.TYPE.name() + " TEXT," + Columns.LIFT.name() + " TEXT," + Columns.WEIGHT.name() + " INTEGER," + Columns.REPS.name() + " INTEGER)");

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
    public boolean insert(long user,String type,String lift, int weight, int reps, int sets, Date datePicked) {
        String printDate = dateFormatter.format(datePicked);

            System.out.println("~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~");
            System.out.printf("INSERTING SET %d TIMES\n \nUSER: %d\nDATE: %s\nTYPE: %s\nLIFT: %s\nREPS: %s\nWEIGHT: %s\n", sets, user, printDate, type, lift, reps, weight);
            System.out.println("~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~");
            ContentValues contentValues = new ContentValues();
            contentValues.put(Columns.USER.name(), user);
            contentValues.put(Columns.DATE.name(), printDate);
            contentValues.put(Columns.TYPE.name(), type);
            contentValues.put(Columns.LIFT.name(), lift);
            contentValues.put(Columns.WEIGHT.name(), weight);
            contentValues.put(Columns.REPS.name(), reps);

        for(int i = 0; i < sets; i++) {
            long result = writableDb.insert(TABLE_NAME, null, contentValues);
            if (result == -1) {
                Log.d(TAG, "Failed to inserted");
                return false;
            }
        }
        Log.d(TAG, "Successfully inserted all entries");
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
    public Cursor select(long user, String type, String lift, String sorting, String limit, Date date) { //TODO: change limit to int eventually
        //Log.d(TAG, "select called");

        if(user < 0 && type == null && lift == null && date == null) {
            Log.d(TAG, "All values passed to select are null");
            return null;
        }

        StringBuilder builder = new StringBuilder();

        //"WHERE 42=42 ..." is there so there's no need to worry if you should add an AND into the string or not, you will need to in every case.
        builder.append("SELECT * FROM "+TABLE_NAME + " WHERE 42=42");

        if(user > 0)
            builder.append(" AND ").append(Columns.USER.name()).append(" = '").append(user).append("'");

        if(type != null)
            builder.append(" AND ").append(Columns.TYPE.name()).append(" = '").append(type).append("'");

        if(lift != null)
            builder.append(" AND ").append(Columns.LIFT.name()).append(" = '").append(lift).append("'");

        if(date != null)
            builder.append(" AND ").append(Columns.DATE.name()).append(" = '").append(dateFormatter.format(date)).append("'");

        if(sorting != null)
            builder.append(" ORDER BY ").append(sorting);

        if(Integer.parseInt(limit) > -1)
            builder.append(" LIMIT ").append(limit);

        String sql = builder.toString();
        System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
        System.out.println(sql);
        System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
        Log.d(TAG, "Executing query: "+ sql);
        return readableDb.rawQuery(sql, new String[0]);
    }
    /**
     * Used to select things from inside of the table. Values can be left at null to not filter the data by those columns, however user, type, and lift cannot all be null. One has to be non null.
     * Defaults to no limit on output, and sorts by User ASC, Type ASC, Lift ASC, DATE ASC, Id ASC
     * @param user The user to be selected
     * @param type The type of lift to be selected
     * @param lift The lift to be selected
     * @return Cursor object with all selected values
     */
    public Cursor select(long user, String type, String lift) {
        return select(user,type,lift, Columns.USER.name() + " ASC, " + Columns.TYPE.name() + " ASC, " + Columns.LIFT.name() + " ASC, " + Columns.DATE+ " ASC, " + Columns.ID + " ASC","0", Calendar.getInstance().getTime());
    }

    /**
     * Updates an entry inside of the table
     * @param user The current User
     * @param date The date of the lift
     * @param type The type of lift
     * @param oldLift The name of the old lift
     * @param newLift The name of the new lift
     * @return True if the update was successful
     */
    public boolean updateData(String user, String date, String type, String newLift, String oldLift) {
        Log.d(TAG,"Replacing " + oldLift + " with: " + user + " " + date + " " + type + " " + newLift);

        String sql = "UPDATE " + TABLE_NAME + " SET " + Columns.LIFT.name() + " = '" + newLift + "' WHERE " + Columns.LIFT.name() + " = '" + oldLift + "';";
        writableDb.execSQL(sql);

        Cursor cursor = readableDb.rawQuery(sql, new String[0]);
        boolean b = cursor.getCount() > 0;

        cursor.close();
        return b;
    }

    public Map<String, ArrayList<String>> getLiftsByDate(Date datePicked, String type, long user) {
        Log.d(TAG, "select called");
        String date = dateFormatter.format(datePicked);
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT * FROM " + TABLE_NAME + " WHERE");
        if(user > 0) {
            builder.append(" ").append(Columns.USER.name()).append(" = '").append(user).append("'");
            if(type != null)
                builder.append(" AND");
        }
        if(type != null) {
            builder.append(" ").append(Columns.TYPE.name()).append(" = '").append(type).append("'");
            if(date != null)
                builder.append(" AND");
        }
        if(date != null) {
            builder.append(" ").append(Columns.DATE.name()).append(" = '").append(date).append("'");
        }

        String sql = builder.toString();

        Cursor cursor = readableDb.rawQuery(sql, new String[0]);

        //type -> lift -> list of (weight + reps)
        Map<String, ArrayList<String>> returnMap = new HashMap<>();


        while(cursor.moveToNext()) {
            returnMap.put(cursor.getString(Columns.LIFT.ordinal()), getAllSets(type, cursor.getString(Columns.LIFT.ordinal())));
        }

        cursor.close();
        return returnMap;

    }

    public ArrayList<String> getAllSets(String type, String lift) {
        // get ALL SETS of this type and lift
        Cursor c = MainActivity.weightTable.select(MainActivity.USER, type, lift, null, "-1", Calendar.getInstance().getTime());
        ArrayList<String> result = new ArrayList<>();
        while(c.moveToNext()) {
            String arr = c.getString(6) + " repetitions of " + c.getString(5) + " LBS";
            result.add(arr);
        }
        return result;
    }

    public boolean deleteLiftbyName(String lift) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + Columns.LIFT.name() + " = '" + lift + "';";
        writableDb.execSQL(sql);
        return (readableDb.query(TABLE_NAME, new String[]{Columns.LIFT.name()}, Columns.LIFT.name() + " = '" + lift + "'", null, null, null, null).getCount() == 0);
    }

    public boolean hasLiftOnDate(Date date_) {
        Log.d(TAG, "select called");
        String date = dateFormatter.format(date_);
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT * FROM " + TABLE_NAME + " WHERE");

        if(MainActivity.USER > 0) {
            builder.append(" ").append(Columns.USER.name()).append(" = '").append(MainActivity.USER).append("'");
            if(date != null)
                builder.append(" AND");
        }
        if(date != null) {
            builder.append(" ").append(Columns.DATE.name()).append(" = '").append(date).append("'");
        }

        String sql = builder.toString();

        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println(sql);
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

        Cursor cursor = readableDb.rawQuery(sql, new String[0]);

        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

}
