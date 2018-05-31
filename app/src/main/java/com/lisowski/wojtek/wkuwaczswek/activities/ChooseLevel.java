package com.lisowski.wojtek.wkuwaczswek.activities;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.lisowski.wojtek.wkuwaczswek.entities.Section;
import com.lisowski.wojtek.wkuwaczswek.adapters.SectionAdapter;
import com.lisowski.wojtek.wkuwaczswek.entities.Words;

import java.util.ArrayList;

public class ChooseLevel extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup rdGrp;
    private static final String TAG = "ChooseLevel";
    private Button selSecButton;
    private Button goBtn;
    private TextView selectedSectionsTV;
    private ArrayList<Section> arrayList;
    SectionAdapter sectionAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rdGrp = findViewById(R.id.rdGrp);
        goBtn = findViewById(R.id.goBtn);
        selSecButton = findViewById(R.id.selSecBTN);
        selSecButton.setOnClickListener(this);
        goBtn.setOnClickListener(this);

        selectedSectionsTV = (TextView) findViewById(R.id.selectedSectionsTV);
        selectedSectionsTV.setText("");
        selectedSectionsTV.setMovementMethod(new ScrollingMovementMethod());

        ///////////////////////////////////////
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

        arrayList = new ArrayList<>();
        arrayList.add(section1);
        arrayList.add(section1);
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


        sectionAdapter = new SectionAdapter(ChooseLevel.this, R.layout.section_record, arrayList);
        ///////////////////////////////

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
            } else {
                intent = new Intent(this, DifficultTest.class);
            }
            startActivity(intent);
        } else {
            Toast.makeText(this, "Wybierz dział!", Toast.LENGTH_LONG).show();
        }
    }
}

