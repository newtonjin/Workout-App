package com.lostntkdgmail.workout.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lostntkdgmail.workout.R;

public class SecondActivity extends AppCompatActivity{

    private static final String TAG = "SecondActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        Log.d(TAG, "onCreate: started.");
    }
}
