package com.lisowski.wojtek.wkuwaczswek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditWord extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Section> arrayList;
    private static final String TAG = "EditWord";
    private static final String SECTION_ID = "SECTION_ID";
    private static final String WORD_ID = "WORD_ID";

    ListView listView;
    EditText wordEditText;
    EditText translationEditText;
    Button saveEditedWordBtn;
    private int id = -1;
    private int wordId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button selectSectionBtn = (Button) findViewById(R.id.selectSectionBtn);
        Button selectWordBtn = (Button) findViewById(R.id.selectWordBtn);
        saveEditedWordBtn =(Button) findViewById(R.id.saveEditedWordBtn);
        wordEditText = (EditText) findViewById(R.id.wordEditText);
        translationEditText = (EditText) findViewById(R.id.translationEditText);

        selectSectionBtn.setOnClickListener(this);
        selectWordBtn.setOnClickListener(this);
        saveEditedWordBtn.setOnClickListener(this);

        Words w1 = new Words("Tata", "Dad"); // do testow

        id = getIntent().getIntExtra(SECTION_ID, -1);
        Log.d(TAG, "onCreate: VALUE secrion id from intend " + id);

        wordId = getIntent().getIntExtra(WORD_ID, -1);
        Log.d(TAG, "onCreate: VALUE word id from INTEND " + wordId);

        if (id == -1)
            selectWordBtn.setEnabled(false);
        else
            selectWordBtn.setEnabled(true);

        if(wordId != -1){
            saveEditedWordBtn.setEnabled(true);
            wordEditText.setText(w1.getWord());
            translationEditText.setText(w1.getTranslation());
        }else
            saveEditedWordBtn.setEnabled(false);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;

        switch (view.getId()) {
            case R.id.selectSectionBtn:
                intent = new Intent(this, SelectSection.class);
                break;
            case R.id.selectWordBtn:
                intent = new Intent(this, SelectWord.class);
                intent.putExtra(SECTION_ID,id);
                break;
            case R.id.saveEditedWordBtn:
                //TODO zapis do bazy
                wordEditText.setText("");
                translationEditText.setText("");
                Toast.makeText(this, "ZAPISANO", Toast.LENGTH_SHORT).show();
                saveEditedWordBtn.setEnabled(false);
                break;
            default:
        }
        if (intent != null)
            startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SECTION_ID, id);
        outState.putInt(WORD_ID, wordId);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        id = savedInstanceState.getInt(SECTION_ID);
        wordId = savedInstanceState.getInt(WORD_ID);
    }
}
