package com.folahan.unilorinapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.folahan.unilorinapp.Activity.Questions.FirstSemester100l.FirstSemesterActivity;
import com.folahan.unilorinapp.Activity.Questions.SecondSemester100l.SecondSemesterActivity;
import com.folahan.unilorinapp.MainActivity;
import com.folahan.unilorinapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class QuestionTab extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_tab);

    }

    public void open(View view) {
        showButton();
    }

    public void openQuestion(View view) {
        startActivity(new Intent(this, QuestionActivity.class));
        finish();
    }

    protected void showButton() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.question_check_nav,
                findViewById(R.id.design_bottom_sheet));
        Button scoreShow = bottomSheet.findViewById(R.id.btn100);
        Button goHome = bottomSheet.findViewById(R.id.btn200);

        scoreShow.setOnClickListener(view -> {
            startActivity(new Intent(this, FirstSemesterActivity.class));
        });

        goHome.setOnClickListener(view -> {
            startActivity(new Intent(this, SecondSemesterActivity.class));
            dialog.dismiss();
            finish();
        });
        dialog.setCancelable(true);
        dialog.setContentView(bottomSheet);
        dialog.show();
    }
}