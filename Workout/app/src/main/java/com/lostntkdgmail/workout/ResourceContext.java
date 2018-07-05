package com.lostntkdgmail.workout;

import android.app.Application;
import android.content.Context;

/**
 * A class used solely for accessing application resources from a static context
 */
public class ResourceContext extends Application {
    private static Context context;

    /**
     * Creates the context
     */
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    /**
     * Used to get a context into the application. Can be used to access non-system variables
     * @return The Resource Context
     */
    public static Context getContext() {
        return context;
    }
}
