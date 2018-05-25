package com.lisowski.wojtek.wkuwaczswek;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


public class AddWords extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AddWords";
    private ArrayList arrayList = null;
    SelectSectionAdapter sectionAdapter = null;
    Button selectSectionButton;
    Button addSectionButton;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_words);
        context = this.getApplicationContext();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectSectionButton = (Button) findViewById(R.id.selectSectionButton);
        selectSectionButton.setOnClickListener(this);
        addSectionButton = (Button) findViewById(R.id.addSectionButton);
        addSectionButton.setOnClickListener(this);


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

        sectionAdapter = new SelectSectionAdapter(AddWords.this, R.layout.section_record_select, arrayList);
        ////////////////////////////////

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selectSectionButton:
                //intent = new Intent(this, SelectSectionInAddWords.class);
                showDialog(view);
                break;
            case R.id.addSectionButton:
                showAddSectionDialog();

            default:
        }
    }

    private void showDialog(View view) {
        // setup the alert builder
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
                for (Object o : arrayList) {
                    Section s = (Section) o;
                    Log.d(TAG, "OKKK " + s.toString() + " jest na " + s.isSelected());
                    if (s.isSelected() == true)
                        selectSectionButton.setText(s.toString());
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
                if(!ed.getText().toString().equals("")){
                    arrayList.add(new Section(ed.getText().toString()));
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
}
