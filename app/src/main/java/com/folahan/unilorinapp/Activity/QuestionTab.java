package com.folahan.unilorinapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.folahan.unilorinapp.R;

public class QuestionTab extends AppCompatActivity {

    private RelativeLayout rl, relative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_tab);

        rl = findViewById(R.id.relativeNoAcctG);
        relative = findViewById(R.id.relativeNoAcct1);
    }

    public void open(View view) {
        int visible = rl.getVisibility();
        if (visible == View.VISIBLE) {
            rl.setVisibility(View.GONE);
        } else if (visible == View.GONE){
            rl.setVisibility(View.VISIBLE);
        }
    }

    public void openQuestion(View view) {
        startActivity(new Intent(this, QuestionActivity.class));
    }
}