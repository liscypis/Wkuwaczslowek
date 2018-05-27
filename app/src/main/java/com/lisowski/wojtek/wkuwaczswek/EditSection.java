package com.lisowski.wojtek.wkuwaczswek;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditSection extends AppCompatActivity implements View.OnClickListener {

    Button selSecToEditBtn;
    Button deleteSectionBtn;
    Button changeNameSecBtn;
    Button saveChangesBtn;
    TextView selectedSecTv;
    TextView newNameTv;
    EditText newNameSectionEt;
    SelectSectionAdapter sectionAdapter;


    Context context;
    private static final String TAG = "EditSection";
    private ArrayList<Section> arrayList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_section);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = getApplicationContext();

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


        ////////////////////////////////
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
        for (Section section : arrayList){
            section.setSelected(false);
        }
        sectionAdapter = new SelectSectionAdapter(EditSection.this, R.layout.section_record_select, arrayList);
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
                for (Section section : arrayList){
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
        builder.setMessage("Czy na pewno usunąć?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (Section section : arrayList) {
                    if (section.isSelected()) {
                        arrayList.remove(section);
                        break;
                    }
                }
                selectedSecTv.setText("");
                deleteSectionBtn.setEnabled(false);
                changeNameSecBtn.setEnabled(false);
                Toast toast = Toast.makeText(context, "Usunięto", Toast.LENGTH_LONG);
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
        if(newNameSectionEt.getText().toString().equals("")){
            Toast toast = Toast.makeText(context, "Podaj nową nazwę!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 200);
            toast.show();
        } else {
            for (Section section : arrayList) {
                if (section.isSelected()) {
                    section.setNameOfSection(newNameSectionEt.getText().toString());
                    break;
                }
            }
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
}
