package com.lisowski.wojtek.wkuwaczswek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

//DO edycji słówek
//!!!

public class SelectSection extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "SelectSection";
    private ArrayList<Section> arrayList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_section);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
        section1.setSelected(true);
        section1.addWord(w3);
        section1.addWord(w4);

        arrayList = new ArrayList<>();
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


        SelectSectionAdapter sectionAdapter = new SelectSectionAdapter(SelectSection.this, R.layout.section_record_select, arrayList);
        listView = (ListView) findViewById(R.id.selectSectionListView);
        listView.setAdapter(sectionAdapter);

    }

    //  TU TRZEBA BĘDZIE SPRAWDZAĆ W PĘTLI CO JEST WYBRANE A POTEM TO WYSŁAĆ

    @Override
    public void onClick(View view) {
        Intent intent = null;

        switch (view.getId()) {
            case R.id.applyBtnWorldLW:
                Log.d(TAG, "onCreate: ty karakanie" + arrayList.get(0).isSelected());
                intent = new Intent(this, EditWord.class);
                intent.putExtra("SECTION_ID", 0); // id działu się tu wyśle
                break;
            default:
        }
        if (intent != null)
            startActivity(intent);
    }
}
