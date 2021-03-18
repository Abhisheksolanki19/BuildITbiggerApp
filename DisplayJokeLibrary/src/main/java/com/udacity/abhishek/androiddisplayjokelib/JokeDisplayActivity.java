package com.udacity.abhishek.androiddisplayjokelib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE_CODE = "extra_joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joke_display_activity);
        String joke = getIntent().getStringExtra(EXTRA_JOKE_CODE);

        TextView jokeDisplay = findViewById(R.id.joke_display);
        jokeDisplay.setText(joke);
    }
}
