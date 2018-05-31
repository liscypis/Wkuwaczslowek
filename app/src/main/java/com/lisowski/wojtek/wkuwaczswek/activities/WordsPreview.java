package com.lisowski.wojtek.wkuwaczswek.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lisowski.wojtek.wkuwaczswek.R;
import com.lisowski.wojtek.wkuwaczswek.entities.Section;
import com.lisowski.wojtek.wkuwaczswek.entities.Words;
import com.lisowski.wojtek.wkuwaczswek.adapters.SelectSectionAdapter;
import com.lisowski.wojtek.wkuwaczswek.adapters.WordPreviewAdapter;

import java.util.ArrayList;

public class WordsPreview extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<Section> arrayList;
    private ArrayList<Words> wordsArrayList;
    SelectSectionAdapter sectionAdapter = null;

    Button showWordsBtn;
    TextView textViewSelSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_preview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button selSecButton = (Button) findViewById(R.id.selSecBTN);
        selSecButton.setOnClickListener(this);
        showWordsBtn = (Button) findViewById(R.id.showWordsBtn);
        showWordsBtn.setOnClickListener(this);
        showWordsBtn.setEnabled(false);

        textViewSelSec = (TextView) findViewById(R.id.textViewSelSec);
        textViewSelSec.setText("");


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

        sectionAdapter = new SelectSectionAdapter(WordsPreview.this, R.layout.section_record_select, arrayList);
        ////////////////////////////////
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selSecBTN:
                showSectionDialog();
                break;
            case R.id.showWordsBtn:
                showWordsDialog();
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
                        showWordsBtn.setEnabled(true);
                        textViewSelSec.setText("Wybrany dział: " + s.getNameOfSection());
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
        WordPreviewAdapter wordPreviewAdapter = new WordPreviewAdapter(this,  R.layout.words_preview_record, wordsArrayList);

        builder.setTitle("Lista słówek");
        builder.setAdapter(wordPreviewAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("ZAMKNIJ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
