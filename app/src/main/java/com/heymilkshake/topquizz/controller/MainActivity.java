package com.heymilkshake.topquizz.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.heymilkshake.topquizz.R;
import com.heymilkshake.topquizz.model.User;

public class MainActivity extends AppCompatActivity {
    public static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    public static final String PREF_KEY_NAME = "PREFERENCE_KEY_NAME";
    public static final String PREF_KEY_SCORE = "PREFERENCE_KEY_SCORE";
    
    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;

    private User mUser;
    
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println(getLocalClassName() + "::onCreate()");
        setContentView(R.layout.activity_main);

        mGreetingText = findViewById(R.id.activity_main_greeting_text);
        mNameInput = findViewById(R.id.activity_main_name_input);
        mPlayButton = findViewById(R.id.activity_main_play_btn);

        mUser = new User();
        mPreferences = getPreferences(MODE_PRIVATE);
        updateActivityBody();

        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPlayButton.setEnabled(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = mNameInput.getText().toString();
                mUser.setFirstName(firstName);
                mPreferences.edit().putString(PREF_KEY_NAME, mUser.getFirstName()).apply();
                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivity, GAME_ACTIVITY_REQUEST_CODE);
            }
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GAME_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
            mPreferences.edit().putInt(PREF_KEY_SCORE, score).apply();
            updateActivityBody();
        }
    }
    
    private void updateActivityBody() {
        String preferencesName = mPreferences.getString(PREF_KEY_NAME, null);
        if (preferencesName == null) {
            mPlayButton.setEnabled(false);
        } else {
            mUser.setFirstName(preferencesName);
            int lastScore = mPreferences.getInt(PREF_KEY_SCORE, -1);
            mGreetingText.setText("Welcome back, " + mUser.getFirstName() + "!\n" +
                    "Your last score was " + lastScore + ", wil you do better this time?");
            mNameInput.setText(mUser.getFirstName());
            mNameInput.requestFocus();
            mNameInput.setSelection(mNameInput.getText().length());
        }
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
}
