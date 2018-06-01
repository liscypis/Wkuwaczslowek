package com.lisowski.wojtek.wkuwaczswek.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lisowski.wojtek.wkuwaczswek.R;
import com.lisowski.wojtek.wkuwaczswek.adapters.SelectSectionAdapter;
import com.lisowski.wojtek.wkuwaczswek.database.AppDatabase;
import com.lisowski.wojtek.wkuwaczswek.entities.Section;
import com.lisowski.wojtek.wkuwaczswek.entities.Words;

import java.util.ArrayList;

import static com.lisowski.wojtek.wkuwaczswek.database.AppDatabase.getInstance;
//TODO pobieranie z bazy i dodawanie :D

public class AddWords extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AddWords";
    private ArrayList<Section> arrayList = null;
    private ArrayList<Words> wordsArrayList = null;
    SelectSectionAdapter sectionAdapter = null;
    Button selectSectionButton;
    Button addSectionButton;
    Button addWordButton;
    Context context;
    TextView selectedSectionTextView;
    EditText wordEditText;
    EditText translationEditText;
    AppDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_words);
        context = this.getApplicationContext();
        database = getInstance(context);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectSectionButton = (Button) findViewById(R.id.selectSectionButton);
        selectSectionButton.setOnClickListener(this);
        addSectionButton = (Button) findViewById(R.id.addSectionButton);
        addSectionButton.setOnClickListener(this);
        addWordButton = (Button) findViewById(R.id.addWordButton);
        addWordButton.setOnClickListener(this);

        selectedSectionTextView = (TextView) findViewById(R.id.selectedSecTv);
        selectedSectionTextView.setVisibility(View.INVISIBLE);

        wordEditText = (EditText) findViewById(R.id.wordEditTx);
        wordEditText.setEnabled(false);
        translationEditText = (EditText) findViewById(R.id.translationEditTx);
        translationEditText.setEnabled(false);


        arrayList = new ArrayList<Section>();
        new DownloadData().execute();

        sectionAdapter = new SelectSectionAdapter(AddWords.this, R.layout.section_record_select, arrayList);

    }

    private class DownloadData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            wordsArrayList = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            arrayList.addAll(database.sectionDao().getAll());
            wordsArrayList.addAll(database.wordsDao().getAll());
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
            case R.id.selectSectionButton:
                selectSectionDialog();
                break;
            case R.id.addSectionButton:
                showAddSectionDialog();
                break;
            case R.id.addWordButton:
                checkEditText();
                print();
                break;
            default:
        }
    }

    private void selectSectionDialog() {
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
                    if (section.isSelected()) {
                        selectedSectionTextView.setVisibility(View.VISIBLE);
                        selectedSectionTextView.setText("Wybrany dział: " + section.toString());
                        wordEditText.setEnabled(true);
                        translationEditText.setEnabled(true);
                    }
                }
            }
        });
        builder.setNegativeButton("Anuluj", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showAddSectionDialog() {
        LayoutInflater layoutInflater = this.getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.dialog_add_section, null);
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Podaj nazwę nowego działu");

        alertDialog.setCancelable(false);

        final EditText ed = (EditText) view.findViewById(R.id.addSectionEditText);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!ed.getText().toString().equals("") && !checkSection(ed.getText().toString())) {
                    new Thread() {
                        @Override
                        public void run() {
                            final int maxID = database.sectionDao().getLastID() + 1;
                            arrayList.add(new Section(maxID, ed.getText().toString()));
                            database.sectionDao().insertAll(new Section(maxID, ed.getText().toString()));
                        }
                    }.start();

                    Toast toast = Toast.makeText(context, "DODANO DZIAŁ: " + ed.getText().toString(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 200);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(context, "TAKI DZIAŁ JUŻ WYSTĘPUJE", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 200);
                    toast.show();
                }
                dialog.dismiss();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    private void checkEditText() {
        if (wordEditText.getText().toString().equals("") || translationEditText.getText().toString().equals("")) {
            Toast toast = Toast.makeText(context, "Uzupełnij wszystkie pola", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 200);
            toast.show();
            Log.d(TAG, "checkEditText: tu jest nie else :D");
        } else {
            addWord();
        }
    }

    private void addWord() {
        boolean isIn = false;
        int idSection = -1;

        for (Section section : arrayList) {
            if (section.isSelected()) {
                idSection = section.getSid();
                break;
            }
        }
        for (Words w : wordsArrayList) {
            if (w.getWord().equals(wordEditText.getText().toString())) {
                isIn = true;
            }
        }
        if (isIn) {
            Toast toast = Toast.makeText(context, "SŁOWO JUŻ WYSTĘPUJE W BAZIE ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 200);
            toast.show();
        } else {
            Toast toast = Toast.makeText(context, "Dodano " + wordEditText.getText().toString() + " - " + translationEditText.getText().toString(), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 200);
            toast.show();

            final int fIdSec = idSection;
            new Thread() {
                @Override
                public void run() {
                    final int idw = database.wordsDao().getLastID() + 1;
                    database.wordsDao().insertAll(new Words(idw, wordEditText.getText().toString(), translationEditText.getText().toString(), fIdSec));
                    wordsArrayList.addAll(database.wordsDao().getAll());
                    wordEditText.setText("");
                    translationEditText.setText("");
                }
            }.start();
        }
    }


    // zwraca true jak nazwa sekcji juz wystepuje
    private boolean checkSection(String s) {
        for (Section section : arrayList) {
            if (section.getNameOfSection().equals(s)) {
                return true;
            }
        }
        return false;
    }
    private void print() {
        ArrayList<Words> ar  = new ArrayList<>();
        new Thread() {
            @Override
            public void run() {
                ar.addAll(database.wordsDao().getAll());
                for (Words w: ar) {
                    Log.d(TAG, "print: "+ w.getWid() + " " + w.getWord() + " " + w.getTranslation() + " " + w.getSection_id());
                }
            }

        }.start();


    }

}
