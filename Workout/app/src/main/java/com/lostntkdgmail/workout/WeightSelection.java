package com.lostntkdgmail.workout;

//TODO: Add side menu
//TODO: Add ability to view/edit past entries
//TODO: Add ability to edit types of workouts
//TODO: Pull press/Pull press are switched
//TODO: Add Users
//TODO: Eventually add Landscape support

        import android.database.Cursor;
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
    private String type, lift,user;
    private int digit1 = 0;
    private int digit2 = 0;
    private int digit3 = 0;
    private int reps = 0;
    private SeekBar sBar;
    private TextView sBarText;
    private WeightDatabaseAccessor wdb;
    private LiftDatabaseAccessor ldb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Debug","Launching Activity: Weight Selection");
        setContentView(R.layout.weight_selection);
        type = getIntent().getStringExtra("TYPE");
        lift = getIntent().getStringExtra("LIFT");
        user = "Tyler";

        setUpSeekBar();
        setUpNumberPickers();

        Button submit = (Button)findViewById(R.id.button);
        wdb = new WeightDatabaseAccessor(this);
        ldb = new LiftDatabaseAccessor(this);

        for(int[] s : getPreviousWeights()) {
            Log.d("Debug",s[0]+" "+s[1]);
        }

    }
    @Override
    protected void onDestroy() {
        Log.d("Debug","onDestroy() called for WeightSelection");
        wdb.close();
        ldb.close();
        super.onDestroy();
    }
    public void submitWeight(View view) {
        int weight = digit1*100+digit2*10+digit3;
        if(reps > 0 && weight > 0) {

            boolean insertResult = wdb.insert("Tyler", type, lift, weight, reps);
            if(insertResult) {
                Toast.makeText(getApplicationContext(), "Submitted!", Toast.LENGTH_SHORT).show();
                getPreviousWeights();
            }
            else
                Toast.makeText(getApplicationContext(),"Failed to submit",Toast.LENGTH_SHORT).show();
        }
        else if(reps <= 0)
            Toast.makeText(getApplicationContext(),"Number of reps can't be zero!",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(),"The weight can't be zero!",Toast.LENGTH_SHORT).show();



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
    public ArrayList<int[]> getPreviousWeights() {
        Cursor c = wdb.getCursor(user,type,lift,wdb.getCol()[0]+" DESC","3");
        ArrayList<int[]> result = new ArrayList<int[]>(3);
        while(c.moveToNext()) {
            int[] arr = {Integer.parseInt(c.getString(5)),Integer.parseInt(c.getString(6))};
            result.add(arr);
        }
        TextView weight1 = (TextView)findViewById(R.id.Weight1);
        TextView weight2 = (TextView)findViewById(R.id.Weight2);
        TextView weight3 = (TextView)findViewById(R.id.Weight3);
        TextView rep1 = (TextView)findViewById(R.id.Rep1);
        TextView rep2 = (TextView)findViewById(R.id.Rep2);
        TextView rep3 = (TextView)findViewById(R.id.Rep3);
        if(result.size() == 3) {
            weight3.setText(result.get(0)[0]+" lbs");
            rep3.setText(result.get(0)[1]+ " reps");

            weight2.setText(result.get(1)[0]+" lbs");
            rep2.setText(result.get(1)[1]+ " reps");

            weight1.setText(result.get(2)[0]+" lbs");
            rep1.setText(result.get(2)[1]+ " reps");
        }
        else if(result.size() == 2) {
            weight2.setText(result.get(1)[0]+" lbs");
            rep2.setText(result.get(1)[1]+ " reps");

            weight3.setText(result.get(0)[0]+" lbs");
            rep3.setText(result.get(0)[1]+ " reps");

            weight1.setText("-- lbs");
            rep1.setText("-- reps");
        }
        else if(result.size() == 1) {
            weight3.setText(result.get(0)[0]+" lbs");
            rep3.setText(result.get(0)[1]+ " reps");

            weight2.setText("-- lbs");
            rep2.setText("-- reps");

            weight1.setText("-- lbs");
            rep1.setText("-- reps");
        }
        else if(result.size() ==0) {
            weight1.setText("-- lbs");
            rep1.setText("-- reps");

            weight2.setText("-- lbs");
            rep2.setText("-- reps");

            weight3.setText("-- lbs");
            rep3.setText("-- reps");
        }
        return result;
    }



}