package com.folahan.unilorinapp.Activity.Questions.FirstSemester100l;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.folahan.unilorinapp.MainActivity;
import com.folahan.unilorinapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class FirstSemesterActivity extends AppCompatActivity {

    private Button mBtnMth111, mBtnChm101, mBtnChm115, mBtnMth113, mBtnGns111, mBtnPhy115,
    mBtnPhy125, mBtnSta124, mBtnPhy191, mBtnPlb101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_semester);

        mBtnChm101 = findViewById(R.id.btnChm101);
        mBtnChm115 = findViewById(R.id.btnChm115);
        mBtnMth111 = findViewById(R.id.btnMth111);
        mBtnMth113 = findViewById(R.id.btnMth113);
        mBtnGns111 = findViewById(R.id.btnGns111);
        mBtnPhy115 = findViewById(R.id.btnPhy115);
        mBtnPhy125 = findViewById(R.id.btnPhy125);
        mBtnSta124 = findViewById(R.id.btnSta124);
        mBtnPhy191 = findViewById(R.id.btnPhy191);
        mBtnPlb101 = findViewById(R.id.btnPlb101);

        mBtnChm101.setOnClickListener(view -> {
            startActivity(new Intent(this, Chm101Activity.class));
        });

        mBtnChm115.setOnClickListener(view -> {
            startActivity(new Intent(this, Chm115Activity.class));
        });

        mBtnMth111.setOnClickListener(view -> {
            startActivity(new Intent(this, Mth111Activity.class));
        });

        mBtnMth113.setOnClickListener(view -> {
            startActivity(new Intent(this, Mth113Activity.class));
        });

        mBtnGns111.setOnClickListener(view -> {
            startActivity(new Intent(this, Gns111Activity.class));
        });

        mBtnPhy115.setOnClickListener(view -> {
            startActivity(new Intent(this, Phy115Activity.class));
        });

        mBtnPhy125.setOnClickListener(view -> {
            startActivity(new Intent(this, Phy125Activity.class));
        });

        mBtnSta124.setOnClickListener(view -> {
            startActivity(new Intent(this, Sta124Activity.class));
        });

        mBtnPhy191.setOnClickListener(view -> {
            startActivity(new Intent(this, Phy191Activity.class));
        });

        mBtnPlb101.setOnClickListener(view -> {
            startActivity(new Intent(this, Plb101Activity.class));
        });
    }

}