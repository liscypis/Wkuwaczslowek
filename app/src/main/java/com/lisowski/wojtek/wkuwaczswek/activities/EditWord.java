package com.lisowski.wojtek.wkuwaczswek.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
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
import com.lisowski.wojtek.wkuwaczswek.adapters.SelectSectionAdapter;
import com.lisowski.wojtek.wkuwaczswek.adapters.WordAdapter;
import com.lisowski.wojtek.wkuwaczswek.database.AppDatabase;
import com.lisowski.wojtek.wkuwaczswek.entities.Section;
import com.lisowski.wojtek.wkuwaczswek.entities.Words;

import java.util.ArrayList;

import static com.lisowski.wojtek.wkuwaczswek.database.AppDatabase.getInstance;

public class EditWord extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Section> arrayList;
    private ArrayList<Words> wordsArrayList;
    SelectSectionAdapter sectionAdapter = null;
    WordAdapter wordAdapter = null;
    Context context;
    AppDatabase database = null;

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

        database = getInstance(context);

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

        new DownloadData().execute();

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
            sectionAdapter = new SelectSectionAdapter(EditWord.this, R.layout.section_record_select, arrayList);
            wordAdapter = new WordAdapter(EditWord.this, R.layout.word_record, wordsArrayList);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
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
                Section sectionTmp = null;
                for (Section section : arrayList) {
                    if (section.isSelected()) {
                        selSecEditTextView.setVisibility(View.VISIBLE);
                        selSecEditTextView.setText("Wybrany dział: " + section.toString());
                        selectWordBtn.setEnabled(true);
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
                    if (w.isSelected()) {
                        wordEditTx.setText(w.getWord());
                        translationEditTx.setText(w.getTranslation());
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
        for (Words w : wordsArrayList) {
            if (w.isSelected()) {
                w.setWord(wordEditTx.getText().toString());
                w.setTranslation(translationEditTx.getText().toString());
                new Thread() {
                    @Override
                    public void run() {
                        database.wordsDao().updateWords(w);
                    }
                }.start();

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
