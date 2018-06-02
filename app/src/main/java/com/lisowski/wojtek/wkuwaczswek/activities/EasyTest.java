package com.lisowski.wojtek.wkuwaczswek.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lisowski.wojtek.wkuwaczswek.R;
import com.lisowski.wojtek.wkuwaczswek.entities.Words;

import java.util.ArrayList;
import java.util.Arrays;

public class EasyTest extends AppCompatActivity implements View.OnClickListener {

    private TextView wordEasyTv;
    private Button ans1Btn;
    private Button ans2Btn;
    private Button ans3Btn;
    private Button ans4Btn;

    private Context context;

    private static final String TAG = "EasyTest";

    ArrayList<Words> arrayList = null;
    String answer = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_test);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = getApplicationContext();

        wordEasyTv = (TextView) findViewById(R.id.wordEasyTv);

        ans1Btn = (Button) findViewById(R.id.ans1Btn);
        ans1Btn.setOnClickListener(this);
        ans2Btn = (Button) findViewById(R.id.ans2Btn);
        ans2Btn.setOnClickListener(this);
        ans3Btn = (Button) findViewById(R.id.ans3Btn);
        ans3Btn.setOnClickListener(this);
        ans4Btn = (Button) findViewById(R.id.ans4Btn);
        ans4Btn.setOnClickListener(this);

        // pobieranie id sekcji!
        int[] arrayIDs = getIntent().getIntArrayExtra("IDsSECTIONs");
        Log.d(TAG, "onCreate: " + Arrays.toString(arrayIDs));

        /////////////////////////////
        arrayList = new ArrayList<Words>();
        Words w1 = new Words("Tata", "Dad");
        Words w2 = new Words("Mama", "Mom");
        Words w3 = new Words("Brat", "Brother");
        Words w4 = new Words("Siostra", "Sister");
        arrayList.add(w1);
        arrayList.add(w2);
        arrayList.add(w3);
        arrayList.add(w4);

        /////////////////////////
        wordEasyTv.setText(arrayList.get(0).getWord());
        answer = arrayList.get(0).getTranslation();
        ans1Btn.setText(arrayList.get(0).getTranslation());
        ans2Btn.setText(arrayList.get(1).getTranslation());
        ans3Btn.setText(arrayList.get(2).getTranslation());
        ans4Btn.setText(arrayList.get(3).getTranslation());
    }

    @Override
    public void onClick(View view) {
        checkAnswer(view);
    }

    private void checkAnswer(View view) {
        if (view.getId() == R.id.ans1Btn) {
            if (ans1Btn.getText().toString().equals(answer)) {
                ans1Btn.setBackgroundColor(Color.GREEN);
            } else {
                ans1Btn.setBackgroundColor(Color.RED);
                showCorrectAnswer();
            }
        }
        if (view.getId() == R.id.ans2Btn) {
            if (ans2Btn.getText().toString().equals(answer)) {
                ans2Btn.setBackgroundColor(Color.GREEN);
            } else {
                ans2Btn.setBackgroundColor(Color.RED);
                showCorrectAnswer();
            }
        }
        if (view.getId() == R.id.ans3Btn) {
            if (ans3Btn.getText().toString().equals(answer)) {
                ans3Btn.setBackgroundColor(Color.GREEN);
            } else {
                ans3Btn.setBackgroundColor(Color.RED);
                showCorrectAnswer();
            }
        }
        if (view.getId() == R.id.ans4Btn) {
            if (ans4Btn.getText().toString().equals(answer)) {
                ans4Btn.setBackgroundColor(Color.GREEN);
            } else {
                ans4Btn.setBackgroundColor(Color.RED);
                showCorrectAnswer();
            }
        }
        ans1Btn.setClickable(false);
        ans2Btn.setClickable(false);
        ans3Btn.setClickable(false);
        ans4Btn.setClickable(false);
        nextQuestion();
    }
    private void showCorrectAnswer() {
        if(ans1Btn.getText().toString().equals(answer)) {
            ans1Btn.setBackgroundColor(Color.GREEN);
        }
        if(ans2Btn.getText().toString().equals(answer)) {
            ans2Btn.setBackgroundColor(Color.GREEN);
        }
        if(ans3Btn.getText().toString().equals(answer)) {
            ans3Btn.setBackgroundColor(Color.GREEN);
        }
        if(ans4Btn.getText().toString().equals(answer)) {
            ans4Btn.setBackgroundColor(Color.GREEN);
        }
    }

    private void nextQuestion() {
        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
                //inputTranslationEt.setText("seconds remaining: " + 20);
            }

            public void onFinish() {
                ans1Btn.setClickable(true);
                ans2Btn.setClickable(true);
                ans3Btn.setClickable(true);
                ans4Btn.setClickable(true);
                loadNextQuestion();
                //TODO dodać logowanie następnego słowa
            }
        }.start();
    }
    private void loadNextQuestion() {
        // TODO sprawdzać czy są jeszcze słowa w liście
        showResult();
    }

    private void showResult() {
        ans1Btn.setClickable(false);
        ans2Btn.setClickable(false);
        ans3Btn.setClickable(false);
        ans4Btn.setClickable(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wynik");
        builder.setMessage("Twój wynik: 10/70");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(context, ChooseLevel.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
