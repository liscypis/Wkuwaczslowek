package com.lisowski.wojtek.wkuwaczswek.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lisowski.wojtek.wkuwaczswek.R;
import com.lisowski.wojtek.wkuwaczswek.entities.Section;
import com.lisowski.wojtek.wkuwaczswek.adapters.SelectSectionAdapter;
import com.lisowski.wojtek.wkuwaczswek.adapters.WordAdapter;
import com.lisowski.wojtek.wkuwaczswek.entities.Words;

import java.util.ArrayList;

public class EditWord extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Section> arrayList;
    private ArrayList<Words> wordsArrayList;
    SelectSectionAdapter sectionAdapter = null;
    Context context;

    private static final String TAG = "EditWord";

    EditText wordEditTx;
    EditText translationEditTx;
    Button saveEditedWordBtn;
    Button selectWordBtn;
    TextView selSecEditTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = getApplicationContext();

        Button selectSectionBtn = (Button) findViewById(R.id.selectSectionBtn);
        selectSectionBtn.setOnClickListener(this);
        selectWordBtn = (Button) findViewById(R.id.selectWordBtn);
        selectWordBtn.setOnClickListener(this);
        selectWordBtn.setEnabled(false);
        saveEditedWordBtn = (Button) findViewById(R.id.saveEditedWordBtn);
        saveEditedWordBtn.setOnClickListener(this);
        saveEditedWordBtn.setEnabled(false);

        wordEditTx = (EditText) findViewById(R.id.wordEditTx);
        wordEditTx.setEnabled(false);
        translationEditTx = (EditText) findViewById(R.id.translationEditTx);
        translationEditTx.setEnabled(false);

        selSecEditTextView = (TextView) findViewById(R.id.selSecEditTextView);
        selSecEditTextView.setVisibility(View.INVISIBLE);





        ///////////////////////////////////////////////////
        Words w1 = new Words("Tata", "Dad");
        Words w2 = new Words("Mama", "Mom");
        Words w3 = new Words("Brat", "Brother");
        Words w4 = new Words("Siostra", "Sister");

        Section section1 = new Section("Rodzina");
        Section section2 = new Section("Owoce");
        Section section3 = new Section("Warzywa");
        Section section4 = new Section("Kolory");
        Section section5 = new Section("a");
        Section section6 = new Section("b");
        Section section7 = new Section("c");
        Section section8 = new Section("d");
        Section section9 = new Section("e");
        Section section10 = new Section("f");
        Section section11 = new Section("g");
        Section section12 = new Section("h");
        Section section13 = new Section("i");
        Section section14 = new Section("j");
        section1.addWord(w1);
        section1.addWord(w2);
        section1.addWord(w3);
        section1.addWord(w4);

        arrayList = new ArrayList<Section>();
        arrayList.add(section1);
        arrayList.add(section2);
        arrayList.add(section3);
        arrayList.add(section4);
        arrayList.add(section5);
        arrayList.add(section6);
        arrayList.add(section7);
        arrayList.add(section8);
        arrayList.add(section9);
        arrayList.add(section10);
        arrayList.add(section11);
        arrayList.add(section12);
        arrayList.add(section13);
        arrayList.add(section14);
        arrayList.add(new Section("dodany"));

        sectionAdapter = new SelectSectionAdapter(EditWord.this, R.layout.section_record_select, arrayList);
        ////////////////////////////////
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selectSectionBtn:
                showSectionDialog();
                break;
            case R.id.selectWordBtn:
                showWordsDialog();
                break;
            case R.id.saveEditedWordBtn:
                //TODO zapis do bazy
                checkEditText();
                break;
            default:
        }
    }

    private void showSectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Wybierz dział");
        builder.setAdapter(sectionAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (Section section : arrayList) {
                    Section s = section;
                    if (s.isSelected()) {
                        selSecEditTextView.setVisibility(View.VISIBLE);
                        selSecEditTextView.setText("Wybrany dział: " + s.toString());
                        selectWordBtn.setEnabled(true);
                   }
               }
            }
        });
        builder.setNegativeButton("Anuluj", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void showWordsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        for (Section section: arrayList) {
            if(section.isSelected()){
                section.setSelected(false);
                wordsArrayList = section.getWordsArrayList();
                break;
            }
        }
        WordAdapter wordAdapter = new WordAdapter(EditWord.this, R.layout.word_record, wordsArrayList);

        builder.setTitle("Wybierz słowo do edycji");
        builder.setAdapter(wordAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (Words w : wordsArrayList) {
                    Words word = w;
                    if (word.isSelected()) {
                        wordEditTx.setText(word.getWord());
                        translationEditTx.setText(word.getTranslation());
                        wordEditTx.setEnabled(true);
                        translationEditTx.setEnabled(true);
                        saveEditedWordBtn.setEnabled(true);
                    }
                }
            }
        });
        builder.setNegativeButton("Anuluj", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void checkEditText() {
        if (wordEditTx.getText().toString().equals("") || translationEditTx.getText().toString().equals("")) {
            Toast toast = Toast.makeText(context, "Uzupełnij wszystkie pola", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 200);
            toast.show();
        } else {
           updateWord();
        }
    }

    private void updateWord() {
        for (Words w: wordsArrayList) {
            if(w.isSelected()){
                w.setSelected(false);
                w.setWord(wordEditTx.getText().toString());
                w.setTranslation(translationEditTx.getText().toString());
                wordEditTx.setText("");
                translationEditTx.setText("");
                Toast toast = Toast.makeText(context, "ZAPISANO", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 200);
                toast.show();
                wordEditTx.setEnabled(false);
                translationEditTx.setEnabled(false);

                break;
            }
        }
    }

}
