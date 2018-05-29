package com.lisowski.wojtek.wkuwaczswek.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lisowski.wojtek.wkuwaczswek.R;
import com.lisowski.wojtek.wkuwaczswek.Words;
import com.lisowski.wojtek.wkuwaczswek.activities.ChooseLevel;

import java.util.ArrayList;

public class DifficultTest extends AppCompatActivity implements View.OnClickListener {

    private TextView wordTextV;
    private EditText inputTranslationEt;
    private Button hintBtn;
    private Button applyDifTestBtn;
    private ArrayList<Words> arrayList = null;
    private String hint = "";
    private int hindIndex = 0;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficult_test);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = getApplicationContext();

        wordTextV = (TextView) findViewById(R.id.wordTextV);
        inputTranslationEt = (EditText) findViewById(R.id.inputTranslationEt);

        hintBtn = (Button) findViewById(R.id.hintBtn);
        hintBtn.setOnClickListener(this);
        applyDifTestBtn = (Button) findViewById(R.id.applyDifTestBtn);
        applyDifTestBtn.setOnClickListener(this);

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
        wordTextV.setText(arrayList.get(0).getWord());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.applyDifTestBtn:
                checkAnswer();
                break;
            case R.id.hintBtn:
                addOneChar();
                break;
            default:
        }

    }
    private void checkAnswer() {
        if(inputTranslationEt.getText().toString().equals(arrayList.get(0).getTranslation())) {
            inputTranslationEt.setTextColor(Color.GREEN);
            hintBtn.setEnabled(false);
            applyDifTestBtn.setEnabled(false);
            nextQuestion();
        } else {
            inputTranslationEt.setTextColor(Color.RED);
            hintBtn.setEnabled(false);
            applyDifTestBtn.setEnabled(false);
            nextQuestion();
        }
    }
    private void addOneChar() {
        String string = arrayList.get(0).getTranslation();
        hint += string.charAt(hindIndex);
        hindIndex++;
        if(hindIndex == string.length()) {
            inputTranslationEt.setText(hint);
            inputTranslationEt.setTextColor(Color.RED);
            hintBtn.setEnabled(false);
            applyDifTestBtn.setEnabled(false);
            nextQuestion();
        } else {
            inputTranslationEt.setText(hint);
        }
    }
    private void nextQuestion() {
        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
                //inputTranslationEt.setText("seconds remaining: " + 20);
            }

            public void onFinish() {
                hintBtn.setEnabled(true);
                applyDifTestBtn.setEnabled(true);
                hindIndex = 0;
                hint = "";
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
        hintBtn.setEnabled(false);
        applyDifTestBtn.setEnabled(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wynik");
        builder.setMessage("Twój wynik: 10/70");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(context, ChooseLevel.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
