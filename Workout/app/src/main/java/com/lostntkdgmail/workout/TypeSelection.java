package com.lostntkdgmail.workout;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;



public class TypeSelection extends Activity {
    private LiftDatabaseAccessor ldb;
    private ListView typeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Debug","Launching Activity: TypeSelection");
        setContentView(R.layout.type_selection);
        ldb = new LiftDatabaseAccessor(this);
        setUpListView();

    }
    @Override
    protected void onDestroy() {
        Log.d("Debug","onDestroy() called for Type Selection");
        ldb.close();
        super.onDestroy();
    }
    public void setUpListView() {
        if(ldb.getSize() <1)
            ldb.fillWithData();
        String[] types = ldb.getTypes();
        typeList = (ListView)findViewById(R.id.listv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.listText,types);
        typeList.setAdapter(adapter);

        typeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String type = (String)typeList.getItemAtPosition(position);
                Log.d("Debug","Selected: "+type);
                Intent intent = new Intent(getBaseContext(),LiftSelection.class);
                intent.putExtra("TYPE",type);
                startActivity(intent);

            }
        });
    }



}