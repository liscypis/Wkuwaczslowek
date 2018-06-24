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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lisowski.wojtek.wkuwaczswek.R;
import com.lisowski.wojtek.wkuwaczswek.database.AppDatabase;
import com.lisowski.wojtek.wkuwaczswek.entities.Words;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DifficultTest extends AppCompatActivity implements View.OnClickListener {

    private TextView wordTextV, counterQuestionTextView;
    private EditText inputTranslationEt;
    private Button hintBtn;
    private Button applyDifTestBtn;
    private Context context;

    private static final String TAG = "DifficultTest";

    private AppDatabase database = null;

    private ArrayList<Words> allWordsList = null, selectedWordsList = null;
    private String hint = "", answer = "";
    private int testIndex, questionCounter = 0, questionQuantity, correctAnswer = 0, hindIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficult_test);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = getApplicationContext();
        database = AppDatabase.getInstance(context);

        wordTextV = (TextView) findViewById(R.id.wordTextV);
        counterQuestionTextView = (TextView) findViewById(R.id.counterQuestionTextView);
        inputTranslationEt = (EditText) findViewById(R.id.inputTranslationEt);

        hintBtn = (Button) findViewById(R.id.hintBtn);
        hintBtn.setOnClickListener(this);
        applyDifTestBtn = (Button) findViewById(R.id.applyDifTestBtn);
        applyDifTestBtn.setOnClickListener(this);

        int[] arrayIDs = getIntent().getIntArrayExtra("IDsSECTIONs");
        Log.d(TAG, "onCreate: " + Arrays.toString(arrayIDs));

        new Thread() {
            @Override
            public void run() {
                allWordsList = new ArrayList<>();
                selectedWordsList = new ArrayList<>();

                allWordsList.addAll(database.wordsDao().getAll());
                selectedWordsList.addAll(database.wordsDao().loadAllBySectionIds(arrayIDs));

                questionQuantity = selectedWordsList.size();
                animation();
                loadNextQuestion();
            }
        }.start();

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
        if (inputTranslationEt.getText().toString().equals(answer)) {
            inputTranslationEt.setTextColor(Color.GREEN);
            hintBtn.setEnabled(false);
            applyDifTestBtn.setEnabled(false);
            selectedWordsList.remove(testIndex);
            correctAnswer++;
            nextQuestion();
        } else {
            inputTranslationEt.setTextColor(Color.RED);
            inputTranslationEt.setText(answer);
            hintBtn.setEnabled(false);
            applyDifTestBtn.setEnabled(false);
            selectedWordsList.remove(testIndex);
            nextQuestion();
        }
    }

    private void addOneChar() {
        String string = answer;
        hint += string.charAt(hindIndex);
        hindIndex++;
        if (hindIndex == string.length()) {
            inputTranslationEt.setText(hint);
            inputTranslationEt.setTextColor(Color.RED);
            hintBtn.setEnabled(false);
            applyDifTestBtn.setEnabled(false);
            selectedWordsList.remove(testIndex);
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
                inputTranslationEt.setTextColor(Color.parseColor("#757575"));
                inputTranslationEt.setText("");
                loadNextQuestion();
                animation();
            }
        }.start();
    }

    private void loadNextQuestion() {
        if (selectedWordsList.size() != 0) {
            questionCounter++;
            counterQuestionTextView.setText("Pytanie " + questionCounter + "/" + questionQuantity);
            Random random = new Random();
            testIndex = random.nextInt(selectedWordsList.size());
            answer = selectedWordsList.get(testIndex).getTranslation();
            wordTextV.setText(selectedWordsList.get(testIndex).getWord());
        } else {
            showResult();
        }
    }

    private void showResult() {
        hintBtn.setEnabled(false);
        applyDifTestBtn.setEnabled(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wynik");
        builder.setMessage("Twój wynik: " + correctAnswer + "/" + questionQuantity);
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

    private void animation() {
        Animation animation1 = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        Animation animation2 = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);

        animation1.setDuration(300);
        animation2.setDuration(300);
        applyDifTestBtn.startAnimation(animation1);
        hintBtn.startAnimation(animation1);
        wordTextV.startAnimation(animation2);
    }
}
