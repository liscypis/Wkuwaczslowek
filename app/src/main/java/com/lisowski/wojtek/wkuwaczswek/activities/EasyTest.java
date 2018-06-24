package com.lisowski.wojtek.wkuwaczswek.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.lisowski.wojtek.wkuwaczswek.R;
import com.lisowski.wojtek.wkuwaczswek.database.AppDatabase;
import com.lisowski.wojtek.wkuwaczswek.entities.Words;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class EasyTest extends AppCompatActivity implements View.OnClickListener {

    private TextView wordEasyTv, counterQuestionTV;
    private Button ans1Btn;
    private Button ans2Btn;
    private Button ans3Btn;
    private Button ans4Btn;
    private Drawable defaultButtonColor = null;
    private Context context;
    private AppDatabase database = null;

    private static final String TAG = "EasyTest";

    ArrayList<Words> allWordsList = null;
    ArrayList<Words> selectedWordsList = null;
    String answer = "";
    private int testIndex, questionCounter = 0, questionQuantity, correctAnswer = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_test);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = getApplicationContext();
        database = AppDatabase.getInstance(context);

        counterQuestionTV = (TextView) findViewById(R.id.counterQuestionTV);
        wordEasyTv = (TextView) findViewById(R.id.wordEasyTv);

        ans1Btn = (Button) findViewById(R.id.ans1Btn);
        ans1Btn.setOnClickListener(this);
        ans2Btn = (Button) findViewById(R.id.ans2Btn);
        ans2Btn.setOnClickListener(this);
        ans3Btn = (Button) findViewById(R.id.ans3Btn);
        ans3Btn.setOnClickListener(this);
        ans4Btn = (Button) findViewById(R.id.ans4Btn);
        ans4Btn.setOnClickListener(this);

        defaultButtonColor = ans1Btn.getBackground();


        // pobieranie id sekcji!
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
        checkAnswer(view);
    }

    private void checkAnswer(View view) {
        if (view.getId() == R.id.ans1Btn) {
            if (ans1Btn.getText().toString().equals(answer)) {
                ans1Btn.setBackgroundColor(Color.GREEN);
                correctAnswer++;
            } else {
                ans1Btn.setBackgroundColor(Color.RED);
                showCorrectAnswer();
            }
        }
        if (view.getId() == R.id.ans2Btn) {
            if (ans2Btn.getText().toString().equals(answer)) {
                ans2Btn.setBackgroundColor(Color.GREEN);
                correctAnswer++;
            } else {
                ans2Btn.setBackgroundColor(Color.RED);
                showCorrectAnswer();
            }
        }
        if (view.getId() == R.id.ans3Btn) {
            if (ans3Btn.getText().toString().equals(answer)) {
                ans3Btn.setBackgroundColor(Color.GREEN);
                correctAnswer++;
            } else {
                ans3Btn.setBackgroundColor(Color.RED);
                showCorrectAnswer();
            }
        }
        if (view.getId() == R.id.ans4Btn) {
            if (ans4Btn.getText().toString().equals(answer)) {
                ans4Btn.setBackgroundColor(Color.GREEN);
                correctAnswer++;
            } else {
                ans4Btn.setBackgroundColor(Color.RED);
                showCorrectAnswer();
            }
        }
        ans1Btn.setClickable(false);
        ans2Btn.setClickable(false);
        ans3Btn.setClickable(false);
        ans4Btn.setClickable(false);

        selectedWordsList.remove(testIndex);
        nextQuestion();
    }

    private void showCorrectAnswer() {
        if (ans1Btn.getText().toString().equals(answer)) {
            ans1Btn.setBackgroundColor(Color.GREEN);
        }
        if (ans2Btn.getText().toString().equals(answer)) {
            ans2Btn.setBackgroundColor(Color.GREEN);
        }
        if (ans3Btn.getText().toString().equals(answer)) {
            ans3Btn.setBackgroundColor(Color.GREEN);
        }
        if (ans4Btn.getText().toString().equals(answer)) {
            ans4Btn.setBackgroundColor(Color.GREEN);
        }
    }

    private void nextQuestion() {
        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
                //inputTranslationEt.setText("seconds remaining: " + 20);
            }

            public void onFinish() {
                ans1Btn.setBackground(defaultButtonColor);
                ans2Btn.setBackground(defaultButtonColor);
                ans3Btn.setBackground(defaultButtonColor);
                ans4Btn.setBackground(defaultButtonColor);
                ans1Btn.setClickable(true);
                ans2Btn.setClickable(true);
                ans3Btn.setClickable(true);
                ans4Btn.setClickable(true);

                animation();
                loadNextQuestion();
            }
        }.start();
    }

    private void loadNextQuestion() {
        if (selectedWordsList.size() != 0) {
            questionCounter++;
            counterQuestionTV.setText("Pytanie " + questionCounter + "/" + questionQuantity);
            Random random = new Random();
            testIndex = random.nextInt(selectedWordsList.size());

            int answerIndex = random.nextInt(3) + 1;

            answer = selectedWordsList.get(testIndex).getTranslation();
            wordEasyTv.setText(selectedWordsList.get(testIndex).getWord());

            // przypisanie poprawnej odpowiedzi do buttona o indeksie answerIndex, ktory został wylosowany
            switch (answerIndex) {
                case 1:
                    ans1Btn.setText(selectedWordsList.get(testIndex).getTranslation());
                    break;
                case 2:
                    ans2Btn.setText(selectedWordsList.get(testIndex).getTranslation());
                    break;
                case 3:
                    ans3Btn.setText(selectedWordsList.get(testIndex).getTranslation());
                    break;
                case 4:
                    ans4Btn.setText(selectedWordsList.get(testIndex).getTranslation());
                    break;
            }
            // przypisanie błędnych odpowiedzi do pozostałych butonow, button o indeksie "answerIndex" jest pomijany
            for (int i = 1; i <= 4; i++) {
                if (i == answerIndex)
                    continue;
                else {
                    for (; ; ) {
                        int indexOfIncorrectAnswer = random.nextInt(allWordsList.size());
                        if (!allWordsList.get(indexOfIncorrectAnswer).getTranslation().equals(ans1Btn.getText().toString()) &&
                                !allWordsList.get(indexOfIncorrectAnswer).getTranslation().equals(ans2Btn.getText().toString()) &&
                                !allWordsList.get(indexOfIncorrectAnswer).getTranslation().equals(ans3Btn.getText().toString()) &&
                                !allWordsList.get(indexOfIncorrectAnswer).getTranslation().equals(ans4Btn.getText().toString())) {
                            switch (i) {
                                case 1:
                                    ans1Btn.setText(allWordsList.get(indexOfIncorrectAnswer).getTranslation());
                                    break;
                                case 2:
                                    ans2Btn.setText(allWordsList.get(indexOfIncorrectAnswer).getTranslation());
                                    break;
                                case 3:
                                    ans3Btn.setText(allWordsList.get(indexOfIncorrectAnswer).getTranslation());
                                    break;
                                case 4:
                                    ans4Btn.setText(allWordsList.get(indexOfIncorrectAnswer).getTranslation());
                                    break;
                            }
                            break;
                        }
                    }
                }
            }
        } else {
            showResult();
        }
    }

    private void showResult() {
        ans1Btn.setClickable(false);
        ans2Btn.setClickable(false);
        ans3Btn.setClickable(false);
        ans4Btn.setClickable(false);

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
        wordEasyTv.startAnimation(animation2);
        ans1Btn.startAnimation(animation1);
        ans2Btn.startAnimation(animation1);
        ans3Btn.startAnimation(animation1);
        ans4Btn.startAnimation(animation1);
    }
}
