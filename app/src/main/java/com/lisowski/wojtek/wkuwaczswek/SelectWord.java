package com.lisowski.wojtek.wkuwaczswek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class SelectWord extends AppCompatActivity {

    private static final String TAG = "SelectWord";
    private ArrayList<Words> arrayList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_word);

        int sectioid = getIntent().getIntExtra("SECTION_ID", -1);
        Words w1 = new Words("Tata", "Dad");
        Words w2 = new Words("Mama", "Mom");
        Words w3 = new Words("Brat", "Brother");
        Words w4 = new Words("Siostra", "Sister");

        arrayList = new ArrayList<>();
        arrayList.add(w1);
        arrayList.add(w2);
        arrayList.add(w3);
        arrayList.add(w4);

        WordAdapter wordAdapter = new WordAdapter(SelectWord.this, R.layout.word_record, arrayList);
        listView = (ListView) findViewById(R.id.wordSelectListView);
        listView.setAdapter(wordAdapter);

    }
}
