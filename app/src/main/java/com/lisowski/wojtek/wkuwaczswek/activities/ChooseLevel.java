package com.lisowski.wojtek.wkuwaczswek.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lisowski.wojtek.wkuwaczswek.R;
import com.lisowski.wojtek.wkuwaczswek.adapters.SelectSectionAdapter;
import com.lisowski.wojtek.wkuwaczswek.database.AppDatabase;
import com.lisowski.wojtek.wkuwaczswek.entities.Section;
import com.lisowski.wojtek.wkuwaczswek.adapters.SectionAdapter;
import com.lisowski.wojtek.wkuwaczswek.entities.Words;

import java.util.ArrayList;

import static com.lisowski.wojtek.wkuwaczswek.database.AppDatabase.getInstance;

public class ChooseLevel extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup rdGrp;
    private static final String TAG = "ChooseLevel";
    private Button selSecButton;
    private Button goBtn;
    private TextView selectedSectionsTV;
    private ArrayList<Section> arrayList;
    private SectionAdapter sectionAdapter = null;
    private AppDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = getInstance(getApplicationContext());

        rdGrp = findViewById(R.id.rdGrp);
        goBtn = findViewById(R.id.goBtn);
        selSecButton = findViewById(R.id.selSecBTN);
        selSecButton.setOnClickListener(this);
        goBtn.setOnClickListener(this);

        selectedSectionsTV = (TextView) findViewById(R.id.selectedSectionsTV);
        selectedSectionsTV.setText("");
        selectedSectionsTV.setMovementMethod(new ScrollingMovementMethod());

        new DownloadData().execute();

    }

    private class DownloadData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            arrayList = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            arrayList.addAll(database.sectionDao().getAll());
            sectionAdapter = new SectionAdapter(ChooseLevel.this, R.layout.section_record, arrayList);
            return null;
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selSecBTN:
                showSectionDialog();
                break;
            case R.id.goBtn:
                checkIsSelectedSection();
                   break;
            default:
        }
    }

    private void showSectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Wybierz dział/y");
        builder.setAdapter(sectionAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedSection = "Wybrane działy: ";
                for (Section section : arrayList) {
                    if (section.isSelected()) {
                        selectedSection += section.getNameOfSection();
                        selectedSection += " \n";
                    }
                }
                selectedSectionsTV.setText(selectedSection);
            }
        });
        builder.setNegativeButton("Anuluj", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void checkIsSelectedSection() {
        boolean isSelected = false;
        for (Section section : arrayList) {
            if (section.isSelected()) {
                isSelected = true;
                break;
            }
        }
        if (isSelected) {
            Intent intent = null;
            if (rdGrp.getCheckedRadioButtonId() == R.id.easyBtn) {
                //TODO tu się wyśle wybrane działy albo i nie :D
                intent = new Intent(this, EasyTest.class);
                intent.putExtra("IDsSECTIONs", idSelectedSections());
            } else {
                intent = new Intent(this, DifficultTest.class);
                intent.putExtra("IDsSECTIONs", idSelectedSections());
            }
            startActivity(intent);
        } else {
            Toast.makeText(this, "Wybierz dział!", Toast.LENGTH_LONG).show();
        }
    }

    private int[] idSelectedSections(){
        int counter = 0;
        for (Section section : arrayList) {
            if (section.isSelected()) {
                counter++;
            }
        }
        int[] idsArray = new int[counter];
        int i = 0;
        for (Section section : arrayList) {
            if (section.isSelected()) {
                idsArray[i] = section.getSid();
                i++;
            }
        }
        return idsArray;
    }
}

