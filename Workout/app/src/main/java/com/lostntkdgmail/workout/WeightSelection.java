package com.lostntkdgmail.workout;


        import android.os.Bundle;
        import android.app.Activity;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.SeekBar;
        import android.widget.TextView;
        import android.widget.NumberPicker;
        import android.widget.Toast;

        import java.util.ArrayList;

public class WeightSelection extends Activity {
    private int digit1 = 0;
    private int digit2 = 0;
    private int digit3 = 0;
    private int reps = 0;
    private SeekBar sBar;
    private TextView sBarText;
    private DatabaseAccessor db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Debug","Launching Activity");
        setContentView(R.layout.weight_selection);

        setUpSeekBar();
        setUpNumberPickers();

        Button submit = (Button)findViewById(R.id.button);
        db = new DatabaseAccessor(this);






    }
    @Override
    protected void onDestroy() {
        Log.d("Debug","onDestroy() called");
        db.close();
        super.onDestroy();
    }
    public void submitWeight(View view) {
        if(reps > 0 && digit1 + digit2 + digit3 > 0)
            Toast.makeText(getApplicationContext(),"Submitted!",Toast.LENGTH_SHORT).show();
        //db.insert("Tyler","Arms","Curls",100,20);
        ArrayList<String> data = db.getAllData();
        for(String d : data) {
            //Log.d("Debug",d);
        }

    }
    public void setUpNumberPickers() {
        NumberPicker np1 = (NumberPicker) findViewById(R.id.np1);
        NumberPicker np2 = (NumberPicker) findViewById(R.id.np2);
        NumberPicker np3 = (NumberPicker) findViewById(R.id.np3);

        //Setting up first Number picker
        np1.setMinValue(0);
        np1.setMaxValue(9);
        np1.setValue(0);
        np1.setWrapSelectorWheel(true);
        np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
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
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                digit3 = newVal;
            }
        });
    }
    public void setUpSeekBar() {
        sBarText = (TextView)findViewById(R.id.tv2);
        sBar=(SeekBar)findViewById(R.id.seekBar);
        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int value, boolean b) {
                reps = value;
                sBarText.setText(reps + " reps");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sBarText.setText(reps + " reps");
            }
        });
        sBarText.setText(sBar.getProgress() + " reps");
    }



}