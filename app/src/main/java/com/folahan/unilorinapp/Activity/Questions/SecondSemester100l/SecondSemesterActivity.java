package com.folahan.unilorinapp.Activity.Questions.SecondSemester100l;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.folahan.unilorinapp.Activity.Questions.FirstSemester100l.Chm101Activity;
import com.folahan.unilorinapp.Activity.Questions.FirstSemester100l.Chm115Activity;
import com.folahan.unilorinapp.Activity.Questions.FirstSemester100l.Gns111Activity;
import com.folahan.unilorinapp.Activity.Questions.FirstSemester100l.Mth111Activity;
import com.folahan.unilorinapp.Activity.Questions.FirstSemester100l.Mth113Activity;
import com.folahan.unilorinapp.Activity.Questions.FirstSemester100l.Phy115Activity;
import com.folahan.unilorinapp.Activity.Questions.FirstSemester100l.Phy125Activity;
import com.folahan.unilorinapp.Activity.Questions.FirstSemester100l.Phy191Activity;
import com.folahan.unilorinapp.Activity.Questions.FirstSemester100l.Plb101Activity;
import com.folahan.unilorinapp.Activity.Questions.FirstSemester100l.Sta124Activity;
import com.folahan.unilorinapp.R;

public class SecondSemesterActivity extends AppCompatActivity {

    private Button mBtnMth112, mBtnChm112, mBtnChm116, mBtnMth114, mBtnGns112, mBtnPhy152,
            mBtnPhy142, mBtnSta124, mBtnPhy192, mBtnMth116, mBtnGns114, mBtnChm132;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_semester);

        mBtnChm112 = findViewById(R.id.btnChm112);
        mBtnChm116 = findViewById(R.id.btnChm116);
        mBtnMth112 = findViewById(R.id.btnMth112);
        mBtnMth114 = findViewById(R.id.btnMth114);
        mBtnGns112 = findViewById(R.id.btnGns112);
        mBtnPhy152 = findViewById(R.id.btnPhy152);
        mBtnPhy142 = findViewById(R.id.btnPhy142);
        mBtnSta124 = findViewById(R.id.btnSta124);
        mBtnPhy192 = findViewById(R.id.btnPhy192);
        mBtnMth116 = findViewById(R.id.btnMth116);
        mBtnGns114 = findViewById(R.id.btnGns114);
        mBtnChm132 = findViewById(R.id.btnChm132);


        mBtnChm112.setOnClickListener(view -> {
            startActivity(new Intent(this, Chm112Activity.class));
        });

        mBtnGns114.setOnClickListener(view -> {
            startActivity(new Intent(this, Gns114Activity.class));
        });

        mBtnChm116.setOnClickListener(view -> {
            startActivity(new Intent(this, Chm116Activity.class));
        });

        mBtnMth112.setOnClickListener(view -> {
            startActivity(new Intent(this, Mth112Activity.class));
        });

        mBtnMth114.setOnClickListener(view -> {
            startActivity(new Intent(this, Mth114Activity.class));
        });

        mBtnGns112.setOnClickListener(view -> {
            startActivity(new Intent(this, Gns112Activity.class));
        });

        mBtnPhy152.setOnClickListener(view -> {
            startActivity(new Intent(this, Phy152Activity.class));
        });

        mBtnPhy142.setOnClickListener(view -> {
            startActivity(new Intent(this, Phy142Activity.class));
        });

        mBtnSta124.setOnClickListener(view -> {
            startActivity(new Intent(this, Sta124Activity.class));
        });

        mBtnPhy192.setOnClickListener(view -> {
            startActivity(new Intent(this, Phy192Activity.class));
        });

        mBtnMth116.setOnClickListener(view -> {
            startActivity(new Intent(this, Mth116Activity.class));
        });
    }
}