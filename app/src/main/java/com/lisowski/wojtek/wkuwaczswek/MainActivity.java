package com.lisowski.wojtek.wkuwaczswek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button testBtn = findViewById(R.id.testBtn);
        Button addWordsBtn = findViewById(R.id.addWordsBtn);
        Button editWordsBtn = findViewById(R.id.editWordsBtn);

        testBtn.setOnClickListener(this);
        addWordsBtn.setOnClickListener(this);
        editWordsBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;

        switch (view.getId()) {
            case R.id.testBtn:
                intent = new Intent(this, ChooseLevel.class);
                break;
            case R.id.addWordsBtn:
                intent = new Intent(this, AddWords.class);
                break;
            case R.id.editWordsBtn:
                intent = new Intent(this, EditWord.class);
                break;
            default:
        }
        if (intent != null)
            startActivity(intent);
    }
}

