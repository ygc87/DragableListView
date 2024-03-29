package com.example.dragablelistview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.example.dragablelistview.widget.DragableListView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DragableListView lv = (DragableListView) findViewById(R.id.list);
        lv.setAdapter(lv.new EditModeListAdapter(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
