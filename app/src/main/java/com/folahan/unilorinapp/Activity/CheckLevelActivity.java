package com.folahan.unilorinapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.folahan.unilorinapp.R;


public class CheckLevelActivity extends AppCompatActivity {

    private View view1, view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_level);
        //Objects.requireNonNull(getSupportActionBar()).hide();

        view1 = findViewById(R.id.level1);
        //view2 = findViewById(R.id.level2);

        view1.setOnClickListener(view ->
                startActivity(new Intent(this, NotesActivity.class)));
    }
}