package com.heymilkshake.topquizz.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.heymilkshake.topquizz.R;
import com.heymilkshake.topquizz.model.Question;
import com.heymilkshake.topquizz.model.QuestionBank;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String BUNDLE_EXTRA_SCORE =
            GameActivity.class.getCanonicalName().concat("BUNDLE_EXTRA_SCORE");
    
    public static final String BUNDLE_STATE_SCORE = "currentScore";
    public static final String BUNDLE_STATE_CURRENT_QUESTION_NUMBER = "currentQuestionNumber";
    public static final String BUNDLE_STATE_QUESTION_BANK = "currentQuestionBank";
    private static final String BUNDLE_STATE_CURRENT_QUESTION = "currentQuestion";
    
    private TextView mQuestionText;
    private Button mAnswer1Button;
    private Button mAnswer2Button;
    private Button mAnswer3Button;
    private Button mAnswer4Button;
    
    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;
    
    private int mNumberOfQuestions;
    private int mScore;
    private boolean mEnableTouchEvents;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println(getLocalClassName() + "::onCreate()");
        setContentView(R.layout.activity_game);
        
        findViews();
        tagButtons();
        
        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_CURRENT_QUESTION_NUMBER);
            mQuestionBank = savedInstanceState.getParcelable(BUNDLE_STATE_QUESTION_BANK);
            mCurrentQuestion = savedInstanceState.getParcelable(BUNDLE_STATE_CURRENT_QUESTION);
            displayQuestion(mCurrentQuestion);
        } else {
            mScore = 0;
            mNumberOfQuestions = 4;
            mQuestionBank = generateQuestions();
            drawQuestion();
        }
        
        mEnableTouchEvents = true;
    }
    
    private void findViews() {
        mQuestionText = findViewById(R.id.activity_game_question_text);
        mAnswer1Button = findViewById(R.id.activity_game_answer1_btn);
        mAnswer2Button = findViewById(R.id.activity_game_answer2_btn);
        mAnswer3Button = findViewById(R.id.activity_game_answer3_btn);
        mAnswer4Button = findViewById(R.id.activity_game_answer4_btn);
    }
    
    private void tagButtons() {
        int tag = 0;
        for (Button button : Arrays.asList(
                mAnswer1Button,
                mAnswer2Button,
                mAnswer3Button,
                mAnswer4Button)) {
            button.setTag(tag++);
            button.setOnClickListener(this);
        }
    }
    
    private void drawQuestion() {
        mCurrentQuestion = mQuestionBank.drawQuestion();
        displayQuestion(mCurrentQuestion);
    }
    
    private QuestionBank generateQuestions() {
        Question question1 = new Question(
                "What is the name of the current french president?",
                Arrays.asList("François Hollande",
                        "Emmanuel Macron",
                        "Jacques Chirac",
                        "François Mitterand"),
                1);
        Question question2 = new Question(
                "How many countries are there in the European Union?",
                Arrays.asList("15",
                        "24",
                        "28",
                        "32"),
                2);
        Question question3 = new Question(
                "Who is the creator of the Android operating system?",
                Arrays.asList("Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "Paul Smith"),
                0);
        Question question4 = new Question(
                "When did the first man land on the moon?",
                Arrays.asList("1958",
                        "1962",
                        "1967",
                        "1969"),
                3);
        Question question5 = new Question(
                "What is the capital of Romania?",
                Arrays.asList("Bucarest",
                        "Warsaw",
                        "Budapest",
                        "Berlin"),
                0);
        
        Question question6 = new Question(
                "Who did the Mona Lisa paint?",
                Arrays.asList("Michelangelo",
                        "Leonardo Da Vinci",
                        "Raphael",
                        "Carravagio"),
                1);
        Question question7 = new Question(
                "In which city is the composer Frédéric Chopin buried?",
                Arrays.asList("Strasbourg",
                        "Warsaw",
                        "Paris",
                        "Moscow"),
                2);
        Question question8 = new Question(
                "What is the country top-level domain of Belgium?",
                Arrays.asList(".bg",
                        ".bm",
                        ".bl",
                        ".be"),
                3);
        Question question9 = new Question(
                "What is the house number of The Simpsons?",
                Arrays.asList("42",
                        "101",
                        "666",
                        "742"),
                3);
        return new QuestionBank(Arrays.asList(
                question1,
                question2,
                question3,
                question4,
                question5,
                question6,
                question7,
                question8,
                question9));
    }
    
    private void displayQuestion(final Question question) {
        mQuestionText.setText(question.getQuestion());
        mAnswer1Button.setText(question.getChoiceList().get(0));
        mAnswer2Button.setText(question.getChoiceList().get(1));
        mAnswer3Button.setText(question.getChoiceList().get(2));
        mAnswer4Button.setText(question.getChoiceList().get(3));
    }
    
    public void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Well done!")
                .setMessage("Your score is " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }
    
    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();
        if (responseIndex == mCurrentQuestion.getAnswerIndex()) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            ++mScore;
        } else {
            Toast.makeText(this, "Wrong answer.", Toast.LENGTH_SHORT).show();
        }
        
        mEnableTouchEvents = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents = true;
                if (--mNumberOfQuestions > 0) {
                    drawQuestion();
                } else {
                    endGame();
                }
            }
        }, 2000);
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        System.out.println(getLocalClassName() + "::onStart()");
        
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        System.out.println(getLocalClassName() + "::onStop()");
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println(getLocalClassName() + "::onDestroy()");
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        System.out.println(getLocalClassName() + "::onPause()");
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println(getLocalClassName() + "::onResume()");
    }
    
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_CURRENT_QUESTION_NUMBER, mNumberOfQuestions);
        outState.putParcelable(BUNDLE_STATE_QUESTION_BANK, mQuestionBank);
        outState.putParcelable(BUNDLE_STATE_CURRENT_QUESTION, mCurrentQuestion);
        super.onSaveInstanceState(outState);
    }
}
