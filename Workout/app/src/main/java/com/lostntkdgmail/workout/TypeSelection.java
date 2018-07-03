package com.lostntkdgmail.workout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * The Activity for selecting a Type of lift
 */
public class TypeSelection extends Activity {
    private LiftTableAccessor liftTable;
    private ListView typeList;

    /**
     * Creates the Activity and sets up the data
     * @param savedInstanceState The last saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TypeSelectionDebug","Launching Activity: TypeSelection");
        setContentView(R.layout.type_selection);
        liftTable = new LiftTableAccessor(this);
        setUpListView();

    }

    /**
     * Cleans up the Activity and closes the database accessors
     */
    @Override
    protected void onDestroy() {
        Log.d("TypeSelectionDebug","onDestroy() called for Type Selection");
        liftTable.close();
        super.onDestroy();
    }

    /**
     * Sets up the ListView which holds all of the different lifts
     */
    public void setUpListView() {
        if(liftTable.getNumberOfRows() < 1)
            liftTable.fillWithData();
        String[] types = liftTable.getTypes();
        typeList = findViewById(R.id.listv);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.list_item,R.id.listText,types);
        typeList.setAdapter(adapter);

        typeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Determines what happens when one of the Items is selected
             * @param adapterView The adapter view
             * @param view The ListView
             * @param position The position of the view in the adapter
             * @param id The row id of the selected item
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String type = (String)typeList.getItemAtPosition(position);
                Log.d("TypeSelectionDebug","Selected: "+type);
                Intent intent = new Intent(getBaseContext(),LiftSelection.class);
                intent.putExtra("TYPE",type);
                startActivity(intent);

            }
        });
    }



}