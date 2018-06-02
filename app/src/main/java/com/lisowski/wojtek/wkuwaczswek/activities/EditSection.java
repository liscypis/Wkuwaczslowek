package com.lisowski.wojtek.wkuwaczswek.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lisowski.wojtek.wkuwaczswek.R;
import com.lisowski.wojtek.wkuwaczswek.adapters.SelectSectionAdapter;
import com.lisowski.wojtek.wkuwaczswek.database.AppDatabase;
import com.lisowski.wojtek.wkuwaczswek.entities.Section;

import java.util.ArrayList;

import static com.lisowski.wojtek.wkuwaczswek.database.AppDatabase.getInstance;

public class EditSection extends AppCompatActivity implements View.OnClickListener {

    private Button selSecToEditBtn;
    private Button deleteSectionBtn;
    private Button changeNameSecBtn;
    private Button saveChangesBtn;
    private TextView selectedSecTv;
    private TextView newNameTv;
    private EditText newNameSectionEt;

    private SelectSectionAdapter sectionAdapter = null;
    private AppDatabase database = null;

    private Context context;
    private ArrayList<Section> arrayList = null;

    private static final String TAG = "EditSection";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_section);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = getApplicationContext();

        database = getInstance(context);

        selSecToEditBtn = (Button) findViewById(R.id.selSecToEditBtn);
        selSecToEditBtn.setOnClickListener(this);

        deleteSectionBtn = (Button) findViewById(R.id.deleteSectionBtn);
        deleteSectionBtn.setOnClickListener(this);
        deleteSectionBtn.setEnabled(false);

        changeNameSecBtn = (Button) findViewById(R.id.changeNameSecBtn);
        changeNameSecBtn.setOnClickListener(this);
        changeNameSecBtn.setEnabled(false);

        saveChangesBtn = (Button) findViewById(R.id.saveChangesBtn);
        saveChangesBtn.setOnClickListener(this);
        saveChangesBtn.setEnabled(false);

        selectedSecTv = (TextView) findViewById(R.id.selectedSecTv);
        selectedSecTv.setVisibility(View.INVISIBLE);

        newNameTv = (TextView) findViewById(R.id.newNameTv);
        newNameTv.setVisibility(View.INVISIBLE);

        newNameSectionEt = (EditText) findViewById(R.id.newNameSectionEt);
        newNameSectionEt.setVisibility(View.INVISIBLE);

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
            sectionAdapter = new SelectSectionAdapter(EditSection.this, R.layout.section_record_select, arrayList);
            return null;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selSecToEditBtn:
                showSectionDialog();
                break;
            case R.id.deleteSectionBtn:
                deleteSectionDialog();
                break;
            case R.id.changeNameSecBtn:
                changeSectionName();
                break;
            case R.id.saveChangesBtn:
                updateNameSection();
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
                Log.d(TAG, "onClick: " + which);
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (Section section : arrayList) {
                    if (section.isSelected()) {
                        selectedSecTv.setVisibility(View.VISIBLE);
                        selectedSecTv.setText("Wybrany dział: " + section.getNameOfSection());
                        deleteSectionBtn.setEnabled(true);
                        changeNameSecBtn.setEnabled(true);
                        break;
                    }
                }
            }
        });
        builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                for (Section section : arrayList) {
                    section.setSelected(false);
                }
                selectedSecTv.setVisibility(View.INVISIBLE);
                selectedSecTv.setText("");
                deleteSectionBtn.setEnabled(false);
                changeNameSecBtn.setEnabled(false);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteSectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("UWAGA!");
        builder.setMessage("Przy usuwaniu działu zostają usunięte również wszystkie słówka znajdujące się w tym dziale!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Section tmpSection = null;
                for (Section section : arrayList) {
                    if (section.isSelected()) {
                        tmpSection = section;
                        arrayList.remove(section);
                        break;
                    }
                }
                final Section fSection = tmpSection;
                new Thread() {
                    @Override
                    public void run() {
                        database.sectionDao().delete(fSection);
                    }
                }.start();

                selectedSecTv.setText("");
                deleteSectionBtn.setEnabled(false);
                changeNameSecBtn.setEnabled(false);
                Toast toast = Toast.makeText(context, "Usunięto", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 200);
                toast.show();
            }
        });
        builder.setNegativeButton("Anuluj", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void changeSectionName() {
        deleteSectionBtn.setVisibility(View.INVISIBLE);
        changeNameSecBtn.setVisibility(View.INVISIBLE);
        newNameTv.setVisibility(View.VISIBLE);
        newNameSectionEt.setVisibility(View.VISIBLE);
        saveChangesBtn.setEnabled(true);
    }

    private void updateNameSection() {
        if (checkNewSectionName()) {
            Toast toast = Toast.makeText(context, "Podaj inną nazwę", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 200);
            toast.show();
        } else {
            Section tmpSection = null;
            for (Section section : arrayList) {
                if (section.isSelected()) {
                    tmpSection = section;
                    section.setNameOfSection(newNameSectionEt.getText().toString());
                    break;
                }
            }
            final Section fSection = tmpSection;
            new Thread() {
                @Override
                public void run() {
                    database.sectionDao().updateSection(fSection);
                }
            }.start();

            Toast toast = Toast.makeText(context, "Zapisano zmiany", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 200);
            toast.show();

            selectedSecTv.setText("");
            deleteSectionBtn.setEnabled(false);
            changeNameSecBtn.setEnabled(false);
            deleteSectionBtn.setVisibility(View.VISIBLE);
            changeNameSecBtn.setVisibility(View.VISIBLE);
            newNameTv.setVisibility(View.INVISIBLE);
            newNameSectionEt.setVisibility(View.INVISIBLE);
            saveChangesBtn.setEnabled(false);
        }
    }

    // Zwraca false jak nie ma takiej nazwy
    boolean checkNewSectionName() {
        if (newNameSectionEt.getText().toString().equals(""))
            return true;
        for (Section section : arrayList) {
            if (section.getNameOfSection().equals(newNameSectionEt.getText().toString())) {
                return true;
            }
        }
        return false;
    }
}
