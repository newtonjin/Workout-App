package com.lostntkdgmail.workout;

//TODO: Add side menu
//TODO: Add ability to view/edit past entries
//TODO: Add ability to edit types of workouts
//TODO: Add Users
//TODO: Eventually add Landscape support, not sure how it currently behaves with different screen types, tablets?
//TODO: Replace hardcoded text with resource strings
//TODO: Replace hardcoded colors with color resources, possibly add color customization to users?

import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.NumberPicker;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * The Activity for selecting a weight
 */
public class WeightSelection extends Fragment {
    private String type, lift, user;
    private int digit1 = 0;
    private int digit2 = 0;
    private int digit3 = 0;
    private int reps = 0;
    private SeekBar sBar;
    private TextView sBarText;
    private WeightTableAccessor weightTable;
    private LiftTableAccessor liftTable;

    /**
     * Creates the Activity and sets up the data
     * @param savedInstanceState
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weight_selection, container, false);
        type = getActivity().getIntent().getStringExtra("TYPE"); //Gets the type of lift from the Intent
        lift = getActivity().getIntent().getStringExtra("LIFT"); //Gets the lift name from the Intent
        user = "Tyler"; //TODO: Actually add users, don't hard code them in

        setUpSeekBar(view);
        setUpNumberPickers(view);

        Button submit = view.findViewById(R.id.button);
        weightTable = new WeightTableAccessor(this.getContext());
        liftTable = new LiftTableAccessor(this.getContext());

        for(int[] s : getPreviousWeights(view)) {
            Log.d("Debug",s[0]+" "+s[1]);
        }
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Debug","Launching Activity: Weight Selection");

    }

    /**
     * Cleans up the Activity and closes the Accessors
     */
    @Override
    public void onDestroy() {
        Log.d("Debug","onDestroy() called for WeightSelection");
        weightTable.close();
        liftTable.close();
        super.onDestroy();
    }
    /**
     * Submits the weight into the database
     * @param view The submit button (I think)
     */
    public void submitWeight(View view) { //TODO: Can we take out the param? I don't remember why it's there
        int weight = digit1*100+digit2*10+digit3;
        if(reps > 0 && weight > 0) {
            boolean insertResult = weightTable.insert(user, type, lift, weight, reps);
            if(insertResult) {
                Toast.makeText(getContext(), "Submitted!", Toast.LENGTH_SHORT).show();
                getPreviousWeights(view);
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
     */
    public void setUpNumberPickers(View view) {
        NumberPicker np1 = view.findViewById(R.id.np1);
        NumberPicker np2 = view.findViewById(R.id.np2);
        NumberPicker np3 = view.findViewById(R.id.np3);

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
     */
    public void setUpSeekBar(View view) {
        sBarText = view.findViewById(R.id.tv2);
        sBar= view.findViewById(R.id.seekBar);
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
                sBarText.setText(reps + " reps");
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
                sBarText.setText(reps + " reps");
            }
        });
        sBarText.setText(sBar.getProgress() + " reps");
    }

    /**
     * Gets the 3 previous weights to display
     * @return An ArrayList<int[]> containing the previous weights. Each int[] is an entry set up like: [weight, reps]
     */
    public ArrayList<int[]> getPreviousWeights(View view) {
        Cursor c = weightTable.getCursor(user,type,lift, weightTable.getColumnNames()[0]+" DESC","3");
        ArrayList<int[]> result = new ArrayList<>(3);
        while(c.moveToNext()) {
            int[] arr = {Integer.parseInt(c.getString(5)),Integer.parseInt(c.getString(6))};
            result.add(arr);
        }
        TextView weight1 = view.findViewById(R.id.Weight1);
        TextView weight2 = view.findViewById(R.id.Weight2);
        TextView weight3 = view.findViewById(R.id.Weight3);
        TextView rep1 = view.findViewById(R.id.Rep1);
        TextView rep2 = view.findViewById(R.id.Rep2);
        TextView rep3 = view.findViewById(R.id.Rep3);
        switch (result.size()) {
            case 3:
                weight3.setText(result.get(0)[0] + " lbs");
                rep3.setText(result.get(0)[1] + " reps");

                weight2.setText(result.get(1)[0] + " lbs");
                rep2.setText(result.get(1)[1] + " reps");

                weight1.setText(result.get(2)[0] + " lbs");
                rep1.setText(result.get(2)[1] + " reps");
                break;
            case 2:
                weight2.setText(result.get(1)[0] + " lbs");
                rep2.setText(result.get(1)[1] + " reps");

                weight3.setText(result.get(0)[0] + " lbs");
                rep3.setText(result.get(0)[1] + " reps");

                weight1.setText("-- lbs");
                rep1.setText("-- reps");
                break;
            case 1:
                weight3.setText(result.get(0)[0] + " lbs");
                rep3.setText(result.get(0)[1] + " reps");

                weight2.setText("-- lbs");
                rep2.setText("-- reps");

                weight1.setText("-- lbs");
                rep1.setText("-- reps");
                break;
            case 0:
                weight1.setText("-- lbs");
                rep1.setText("-- reps");

                weight2.setText("-- lbs");
                rep2.setText("-- reps");

                weight3.setText("-- lbs");
                rep3.setText("-- reps");
                break;
        }
        return result;
    }
}