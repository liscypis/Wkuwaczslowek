package com.lisowski.wojtek.wkuwaczswek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class EditWord extends AppCompatActivity implements View.OnClickListener{
    private ArrayList<Section> arrayList;
    private static final String TAG = "EditWord";
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onClick(View view) {

    }
}
