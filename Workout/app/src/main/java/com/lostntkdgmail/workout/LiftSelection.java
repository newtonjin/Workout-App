package com.lostntkdgmail.workout;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class LiftSelection extends Activity {
    private LiftDatabaseAccessor ldb;
    private ListView liftList;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Debug","Launching Activity LiftSelection");
        setContentView(R.layout.lift_selection);
        text = (TextView)findViewById(R.id.tvlift);
        text.setText(getIntent().getStringExtra("TYPE"));
        ldb = new LiftDatabaseAccessor(this);
        setUpListView();

    }
    @Override
    protected void onDestroy() {
        Log.d("Debug","onDestroy() called for LiftSelection");
        ldb.close();
        super.onDestroy();
    }
    public void setUpListView() {
        String[] lifts = ldb.getLifts(getIntent().getStringExtra("TYPE"));
        liftList = (ListView)findViewById(R.id.listvlift);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.list_item,R.id.listText,lifts);
        liftList.setAdapter(adapter);

        liftList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String lift = (String)liftList.getItemAtPosition(position);
                Log.d("Debug","Selected: "+lift);
                Intent intent = new Intent(getBaseContext(),WeightSelection.class);
                intent.putExtra("LIFT",lift);
                intent.putExtra("TYPE",getIntent().getStringExtra("TYPE"));
                startActivity(intent);

            }
        });


    }


}