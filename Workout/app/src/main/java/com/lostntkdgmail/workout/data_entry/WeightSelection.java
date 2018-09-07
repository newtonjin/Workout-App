package com.lostntkdgmail.workout.data_entry;

/* Created by Tom Pedraza and Tyler Atkinson
 * Workout-App
 * https://github.com/tha7556/Workout-App
 */

//TODO: Add ability to view/edit/delete past entries
//TODO: Add ability to edit types of workouts
//TODO: Eventually add Landscape support, not sure how it currently behaves with different screen types, tablets?
//TODO: Possibly add color customization to users?
//TODO: Allow users to adjust max value on rep bar
//TODO: An actual app icon/logo
//TODO: Add info about lift/type/user on the weight selection page
//TODO: Add what types of lifts were performed in the calendar

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.NumberPicker;
import android.widget.Toast;
import android.view.View.OnKeyListener;

import com.lostntkdgmail.workout.R;
import com.lostntkdgmail.workout.database.UserTableAccessor;
import com.lostntkdgmail.workout.main.BaseFragment;
import com.lostntkdgmail.workout.main.MainActivity;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * The Fragment for selecting a weight
 */
public class WeightSelection extends BaseFragment {
    public static final String TITLE = "WeightSelection";
    private int reps = 0;
    private TextView sBarText;
    private NumberPicker np1, np2, np3;
    public static String lastType, lastLift;
    private static long lastUser;
    private View view;
    private String currLift;
    EditText editDate;
    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd MMMM yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

    /**
     * Creates the fragment
     * @param inflater The inflater to inflate the layout
     * @param container The container to put the Fragment inside of
     * @param savedInstanceState The saved state
     * @return The view to display
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.weight_selection, container, false);

        TextView workoutTextView = view.findViewById(R.id.workoutTextView);
        workoutTextView.setText("Adding to " + ((MainActivity)getActivity()).TYPE + " - " + ((MainActivity)getActivity()).LIFT);

        TextInputLayout set = view.findViewById(R.id.setInput);
        TextInputLayout rep = view.findViewById(R.id.repInput);
        TextInputLayout weight = view.findViewById(R.id.weightInput);


        editDate = view.findViewById(R.id.pickDate);

        // init - set date to current date
        long currentDate = System.currentTimeMillis();
        String dateString = sdf.format(currentDate);
        editDate.setText(dateString);

        // set calendar date and update editDate
        date = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };

        // onclick - popup datepicker
        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        View.OnKeyListener enterPressEvent = new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    submitWeight();
                    return true;
                }
                return false;
            }
        };

        set.getEditText().setRawInputType(InputType.TYPE_CLASS_NUMBER);
        set.setOnKeyListener(enterPressEvent);
        set.setHintEnabled(false);
        set.getEditText().setCursorVisible(false);

        rep.getEditText().setRawInputType(InputType.TYPE_CLASS_NUMBER);
        rep.setOnKeyListener(enterPressEvent);
        rep.setHintEnabled(false);
        rep.getEditText().setCursorVisible(false);

        weight.getEditText().setRawInputType(InputType.TYPE_CLASS_NUMBER);
        weight.setOnKeyListener(enterPressEvent);
        weight.setHintEnabled(false);
        weight.getEditText().setCursorVisible(false);

        Button submit = view.findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitWeight();
            }
        });


        if(lastType == null || lastLift == null || lastUser != MainActivity.USER || !lastType.equals(MainActivity.TYPE) || !lastLift.equals(MainActivity.LIFT)) {
            lastUser = MainActivity.USER;
            lastType = MainActivity.TYPE;
            lastLift = MainActivity.LIFT;
        }

        return view;
    }

    private void updateDate() {
        editDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * Submits the weight into the database
     */
    public void submitWeight() {
        //weight entered, can be 0
        String weight_s = ((TextInputLayout)view.findViewById(R.id.weightInput)).getEditText().getText().toString();
        //sets entered, must be >= 1
        String sets_s = ((TextInputLayout)view.findViewById(R.id.setInput)).getEditText().getText().toString();

        String reps_s = ((TextInputLayout)view.findViewById(R.id.repInput)).getEditText().getText().toString();

        int sets = 1;
        reps = 0;
        int weight = 0;

        if(!weight_s.equals("")) {
            weight = Integer.parseInt(weight_s);
        }

        if(!sets_s.equals("")) {
             sets = Integer.parseInt(sets_s);
        }

        if(!reps_s.equals("")) {
            reps = Integer.parseInt(reps_s);
        }

        if(reps > 0) {
            boolean insertResult = MainActivity.weightTable.insert(MainActivity.USER, MainActivity.TYPE, MainActivity.LIFT, weight, reps, sets, myCalendar.getTime());
            Cursor cursor = MainActivity.userTable.select(MainActivity.USER);
            cursor.moveToFirst();
            boolean userUpdateResult = MainActivity.userTable.updateData(MainActivity.USER,cursor.getString(UserTableAccessor.Columns.FIRST_NAME.ordinal()),cursor.getString(UserTableAccessor.Columns.LAST_NAME.ordinal()));
            if(insertResult && userUpdateResult) {
                Toast.makeText(getContext(), "Submitted!", Toast.LENGTH_SHORT).show();
                //getPreviousWeights(view.getRootView());
            }
            else
                Toast.makeText(getContext(),"Failed to submit", Toast.LENGTH_SHORT).show();
            cursor.close();
        }
        else if(reps <= 0)
            Toast.makeText(getContext(),"Number of reps can't be zero!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getContext(),"The weight can't be zero!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Gets the 3 previous weights to display
     * @param view The view that was inflated in onCreateView
     * @return An ArrayList<int[]> containing the previous weights. Each int[] is an entry set up like: [weight, reps]
     */
    public ArrayList<int[]> getPreviousWeights(View view) {
        Cursor c = MainActivity.weightTable.select(MainActivity.USER,MainActivity.TYPE,MainActivity.LIFT, MainActivity.weightTable.getColumnNames()[0]+" DESC","3", Calendar.getInstance().getTime());
        ArrayList<int[]> result = new ArrayList<>(3);
        while(c.moveToNext()) {
            int[] arr = {Integer.parseInt(c.getString(5)),Integer.parseInt(c.getString(6))};
            result.add(arr);
        }
        c.close();
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
        }
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    public void updateLift() {
        TextView workoutTextView = view.findViewById(R.id.workoutTextView);
        workoutTextView.setText("Adding to " + ((MainActivity)getActivity()).TYPE + " - " + ((MainActivity)getActivity()).LIFT);
    }
}