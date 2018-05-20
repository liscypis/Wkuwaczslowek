package com.lisowski.wojtek.wkuwaczswek;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class AddWords extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_words);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onClick(View view) {

    }
}
