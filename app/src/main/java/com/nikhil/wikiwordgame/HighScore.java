package com.nikhil.wikiwordgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Highscore screen
 * **/

public class HighScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        TextView rightAnswer = (TextView)findViewById(R.id.rightAnswer);
        rightAnswer.setText(getIntent().getStringExtra("wiki"));

        TextView score = (TextView)findViewById(R.id.score);
        score.setText("Score : " + getIntent().getIntExtra("score", 0));
    }

    public void replay(View view){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
