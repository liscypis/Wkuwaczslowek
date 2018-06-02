package com.lisowski.wojtek.wkuwaczswek.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lisowski.wojtek.wkuwaczswek.R;
import com.lisowski.wojtek.wkuwaczswek.database.AppDatabase;

import static com.lisowski.wojtek.wkuwaczswek.database.AppDatabase.getInstance;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button testBtn = findViewById(R.id.testBtn);
        Button addWordsBtn = findViewById(R.id.addWordsBtn);
        Button editWordsBtn = findViewById(R.id.editWordsBtn);
        Button viewWordsButton = findViewById(R.id.viewWordsButton);
        Button editSectionButton = findViewById(R.id.editSectionButton);


        testBtn.setOnClickListener(this);
        addWordsBtn.setOnClickListener(this);
        editWordsBtn.setOnClickListener(this);
        viewWordsButton.setOnClickListener(this);
        editSectionButton.setOnClickListener(this);

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
            case R.id.viewWordsButton:
                intent = new Intent(this, WordsPreview.class);
                break;
            case R.id.editSectionButton:
                intent = new Intent(this, EditSection.class);
                break;
            default:
        }
        if (intent != null)
            startActivity(intent);
    }


}

