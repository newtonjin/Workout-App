package com.lostntkdgmail.workout.data_entry;

//TODO: Add ability to view/edit/delete past entries
//TODO: Add ability to edit types of workouts
//TODO: Eventually add Landscape support, not sure how it currently behaves with different screen types, tablets?
//TODO: Possibly add color customization to users?
//TODO: Allow users to adjust max value on rep bar
//TODO: An actual app icon/logo
//TODO: Add info about lift/type/user on the weight selection page
//TODO: Add what types of lifts were performed in the calendar

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.database.UserTableAccessor;
import com.lostntkdgmail.workout.main.BaseFragment;
import com.lostntkdgmail.workout.main.MainActivity;

import java.util.ArrayList;

/**
 * The Fragment for selecting a weight
 */
public class WeightSelection extends BaseFragment {
    public static final String TITLE = "WeightSelection";
    private int digit1 = 0;
    private int digit2 = 0;
    private int digit3 = 0;
    private int reps = 0;
    private TextView sBarText;
    private NumberPicker np1, np2, np3;
    private static String lastType, lastLift;
    private static long lastUser;

    /**
     * Creates the fragment
     * @param inflater The inflater to inflate the layout
     * @param container The container to put the Fragment inside of
     * @param savedInstanceState The saved state
     * @return The view to display
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weight_selection, container, false);

        setUpSeekBar(view);
        setUpNumberPickers(view);

        Button submit = view.findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitWeight(view);
            }
        });
        if(lastType == null || lastLift == null || lastUser != MainActivity.USER || !lastType.equals(MainActivity.TYPE) || !lastLift.equals(MainActivity.LIFT)) {
            lastUser = MainActivity.USER;
            lastType = MainActivity.TYPE;
            lastLift = MainActivity.LIFT;

            for (int[] s : getPreviousWeights(view)) {
                Log.d("Debug", s[0] + " " + s[1]);
            }
        }
        return view;
    }

    /**
     * Submits the weight into the database
     * @param view The Submit button
     */
    public void submitWeight(View view) {
        int weight = digit1*100+digit2*10+digit3;
        if(reps > 0 && weight > 0) {
            boolean insertResult = MainActivity.weightTable.insert(MainActivity.USER, MainActivity.TYPE, MainActivity.LIFT, weight, reps);
            Cursor cursor = MainActivity.userTable.select(MainActivity.USER);
            cursor.moveToFirst();
            boolean userUpdateResult = MainActivity.userTable.updateData(MainActivity.USER,cursor.getString(UserTableAccessor.Columns.FIRST_NAME.ordinal()),cursor.getString(UserTableAccessor.Columns.LAST_NAME.ordinal()));
            if(insertResult && userUpdateResult) {
                Toast.makeText(getContext(), "Submitted!", Toast.LENGTH_SHORT).show();
                getPreviousWeights(view.getRootView());
            }
            else
                Toast.makeText(getContext(),"Failed to submit",Toast.LENGTH_SHORT).show();
        }
        else if(reps <= 0)
            Toast.makeText(getContext(),"Number of reps can't be zero!",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getContext(),"The weight can't be zero!",Toast.LENGTH_SHORT).show();
    }
    /**
     * Initializes the 3 number pickers
     * @param view The view that was inflated in onCreateView
     */
    public void setUpNumberPickers(View view) {
        np1 = view.findViewById(R.id.numberPicker1);
        np2 = view.findViewById(R.id.numberPicker2);
        np3 = view.findViewById(R.id.numberPicker3);

        //Setting up first Number picker
        np1.setMinValue(0);
        np1.setMaxValue(9);
        np1.setValue(0);
        np1.setWrapSelectorWheel(true);
        np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            /**
             * Sets what happens when a value is changed
             * @param picker The number picker
             * @param oldVal The last value
             * @param newVal The new value
             */
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                digit1 = newVal;
            }
        });

        //Setting up second Number picker
        np2.setMinValue(0);
        np2.setMaxValue(9);
        np2.setValue(0);
        np2.setWrapSelectorWheel(true);
        np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            /**
             * Sets what happens when a value is changed
             * @param picker The number picker
             * @param oldVal The last value
             * @param newVal The new value
             */
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                digit2 = newVal;
            }
        });

        //Setting up third Number picker
        np3.setMinValue(0);
        np3.setMaxValue(9);
        np3.setValue(0);
        np3.setWrapSelectorWheel(true);
        np3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            /**
             * Sets what happens when a value is changed
             * @param picker The number picker
             * @param oldVal The last value
             * @param newVal The new value
             */
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                digit3 = newVal;
            }
        });
    }
    /**
     * Initializes the seek bar
     * @param view The view that was inflated in onCreateView
     */
    public void setUpSeekBar(View view) {
        sBarText = view.findViewById(R.id.scrollBarText);
        SeekBar sBar = view.findViewById(R.id.seekBar);
        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * Determines what happens when the progress is changed
             * @param seekBar The seek bar
             * @param value The value of the seek bar
             * @param fromUser True if the value was changed by the user
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
                reps = value;
                sBarText.setText(getResources().getQuantityString(R.plurals.reps,reps,reps));
            }

            /**
             * Determines what happens when the user starts to touch the seek bar (currently nothing)
             * @param seekBar The seek bar
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            /**
             * Determines what happens when the user stops touching the bar
             * @param seekBar The seek bar
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sBarText.setText(getResources().getQuantityString(R.plurals.reps,reps,reps));
            }
        });
        sBarText.setText(getResources().getQuantityString(R.plurals.reps,reps,reps));
    }

    /**
     * Gets the 3 previous weights to display
     * @param view The view that was inflated in onCreateView
     * @return An ArrayList<int[]> containing the previous weights. Each int[] is an entry set up like: [weight, reps]
     */
    public ArrayList<int[]> getPreviousWeights(View view) {
        Cursor c = MainActivity.weightTable.select(MainActivity.USER,MainActivity.TYPE,MainActivity.LIFT, MainActivity.weightTable.getColumnNames()[0]+" DESC","3");
        ArrayList<int[]> result = new ArrayList<>(3);
        while(c.moveToNext()) {
            int[] arr = {Integer.parseInt(c.getString(5)),Integer.parseInt(c.getString(6))};
            result.add(arr);
        }
        TextView weight1 = view.findViewById(R.id.pastWeightText1);
        TextView weight2 = view.findViewById(R.id.pastWeightText2);
        TextView weight3 = view.findViewById(R.id.pastWeightText3);
        TextView rep1 = view.findViewById(R.id.pastRepText1);
        TextView rep2 = view.findViewById(R.id.pastRepText2);
        TextView rep3 = view.findViewById(R.id.pastRepText3);

        switch (result.size()) {
            case 0: //No previous weights
                weight1.setText(getResources().getString(R.string.null_lbs));
                rep1.setText(getResources().getString(R.string.null_reps));

                weight2.setText(getResources().getString(R.string.null_lbs));
                rep2.setText(getResources().getString(R.string.null_reps));

                weight3.setText(getResources().getString(R.string.null_lbs));
                rep3.setText(getResources().getString(R.string.null_reps));
                break;
            case 1: //Only 1 previous weight
                weight3.setText(getResources().getString(R.string.lbs,result.get(0)[0]));
                rep3.setText(getResources().getQuantityString(R.plurals.reps,result.get(0)[1],result.get(0)[1]));

                weight2.setText(getResources().getString(R.string.null_lbs));
                rep2.setText(getResources().getString(R.string.null_reps));

                weight1.setText(getResources().getString(R.string.null_lbs));
                rep1.setText(getResources().getString(R.string.null_reps));
                break;
            case 2: //Only 2 previous weights
                weight3.setText(getResources().getString(R.string.lbs,result.get(0)[0]));
                rep3.setText(getResources().getQuantityString(R.plurals.reps,result.get(0)[1],result.get(0)[1]));

                weight2.setText(getResources().getString(R.string.lbs,result.get(1)[0]));
                rep2.setText(getResources().getQuantityString(R.plurals.reps,result.get(1)[1],result.get(1)[1]));

                weight1.setText(getResources().getString(R.string.null_lbs));
                rep1.setText(getResources().getString(R.string.null_reps));
                break;
            default: //All 3 previous weights
                weight3.setText(getResources().getString(R.string.lbs,result.get(0)[0]));
                rep3.setText(getResources().getQuantityString(R.plurals.reps,result.get(0)[1],result.get(0)[1]));

                weight2.setText(getResources().getString(R.string.lbs,result.get(1)[0]));
                rep2.setText(getResources().getQuantityString(R.plurals.reps,result.get(1)[1],result.get(1)[1]));

                weight1.setText(getResources().getString(R.string.lbs,result.get(2)[0]));
                rep1.setText(getResources().getQuantityString(R.plurals.reps,result.get(2)[1],result.get(2)[1]));
                break;
        }
        return result;
    }

    /**
     * Reloads the Fragment. Specifically updates the previous weights if User/Type/Lift is changed
     */
    public void reload() {
        if(lastType == null || lastLift == null || lastUser != MainActivity.USER || !lastType.equals(MainActivity.TYPE) || !lastLift.equals(MainActivity.LIFT)) {
            lastUser = MainActivity.USER;
            lastType = MainActivity.TYPE;
            lastLift = MainActivity.LIFT;

            getPreviousWeights(getView());
            digit1 = np1.getValue();
            digit2 = np2.getValue();
            digit3 = np3.getValue();
        }
    }
    @Override
    public String getTitle() {
        return TITLE;
    }
}