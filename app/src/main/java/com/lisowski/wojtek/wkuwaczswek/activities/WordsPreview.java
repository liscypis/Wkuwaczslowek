package com.lisowski.wojtek.wkuwaczswek.activities;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lisowski.wojtek.wkuwaczswek.R;
import com.lisowski.wojtek.wkuwaczswek.adapters.SelectSectionAdapter;
import com.lisowski.wojtek.wkuwaczswek.adapters.WordPreviewAdapter;
import com.lisowski.wojtek.wkuwaczswek.database.AppDatabase;
import com.lisowski.wojtek.wkuwaczswek.entities.Section;
import com.lisowski.wojtek.wkuwaczswek.entities.Words;

import java.util.ArrayList;

import static com.lisowski.wojtek.wkuwaczswek.database.AppDatabase.getInstance;

public class WordsPreview extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Section> arrayList;
    private ArrayList<Words> wordsArrayList;
    private SelectSectionAdapter sectionAdapter = null;
    private WordPreviewAdapter wordPreviewAdapter = null;
    private AppDatabase database = null;

    Button showWordsBtn;
    TextView textViewSelSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_preview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = getInstance(getApplicationContext());

        Button selSecButton = (Button) findViewById(R.id.selSecBTN);
        selSecButton.setOnClickListener(this);
        showWordsBtn = (Button) findViewById(R.id.showWordsBtn);
        showWordsBtn.setOnClickListener(this);
        showWordsBtn.setEnabled(false);

        textViewSelSec = (TextView) findViewById(R.id.textViewSelSec);
        textViewSelSec.setText("");

        new DownloadData().execute();
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

    private class DownloadData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            wordsArrayList = new ArrayList<>();
            arrayList = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            arrayList.addAll(database.sectionDao().getAll());
            sectionAdapter = new SelectSectionAdapter(WordsPreview.this, R.layout.section_record_select, arrayList);
            wordPreviewAdapter = new WordPreviewAdapter(WordsPreview.this, R.layout.words_preview_record, wordsArrayList);
            return null;
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
                Section sectionTmp = null;
                for (Section section : arrayList) {
                    if (section.isSelected()) {
                        showWordsBtn.setEnabled(true);
                        textViewSelSec.setText("Wybrany dział: " + section.getNameOfSection());
                        sectionTmp = section;
                    }
                }
                final Section fSection = sectionTmp;
                new Thread() {
                    @Override
                    public void run() {
                        wordsArrayList.clear();
                        wordsArrayList.addAll(database.wordsDao().loadAllBySectionId(fSection.getSid()));
                    }
                }.start();
            }
        });
        builder.setNegativeButton("Anuluj", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showWordsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
