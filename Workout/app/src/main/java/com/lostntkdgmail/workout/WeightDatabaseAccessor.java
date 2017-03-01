package com.lostntkdgmail.workout;

import android.content.Context;

public class WeightDatabaseAccessor extends DatabaseAccessor {
    public static final String DATABASE_NAME = "Lifts.db";
    public static final String TABLE_NAME = "lifts";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TYPE";
    public static final String COL_3 = "NAME";
    public WeightDatabaseAccessor(Context context) {
        super(context);
    }
}
