package com.lisowski.wojtek.wkuwaczswek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ChooseLevel extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup rdGrp;
    private RadioButton radioButton;
    private static final String TAG = "ChooseLevel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level);

        rdGrp = findViewById(R.id.rdGrp);
        Button goBtn = findViewById(R.id.goBtn);
        Button selectedBtn = findViewById(R.id.selectedBtn);
        selectedBtn.setOnClickListener(this);
        goBtn.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;

        if (view.getId() == R.id.selectedBtn) {
            intent = new Intent(this, SectionListView.class);
        }
        Log.d(TAG, "onClick: click " + intent);
        if (view.getId() == R.id.goBtn) {
            switch (rdGrp.getCheckedRadioButtonId()) {
                case R.id.easyBtn:
                    intent = new Intent(this, EasyTest.class);
                    break;
                case R.id.difficultBtn:
                    intent = new Intent(this, DifficultTest.class);
                    break;
                default:
            }
        }
        if (intent != null)
            startActivity(intent);
    }
}

