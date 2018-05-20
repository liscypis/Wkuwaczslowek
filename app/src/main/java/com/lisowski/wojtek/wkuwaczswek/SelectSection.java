package com.lisowski.wojtek.wkuwaczswek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class SelectSection extends AppCompatActivity {

    private static final String TAG = "SectionListView";
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


        SectionAdapter sectionAdapter = new SectionAdapter(SelectSection.this, R.layout.section_record, arrayList);
        listView = (ListView) findViewById(R.id.sectionListView);
        listView.setAdapter(sectionAdapter);
    }
}
