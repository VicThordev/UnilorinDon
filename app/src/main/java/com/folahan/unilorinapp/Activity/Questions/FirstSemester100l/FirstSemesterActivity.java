package com.folahan.unilorinapp.Activity.Questions.FirstSemester100l;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


import com.folahan.unilorinapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class FirstSemesterActivity extends AppCompatActivity {

    private Button mBtnMth111, mBtnChm101, mBtnChm115, mBtnMth113, mBtnGns111, mBtnPhy115,
    mBtnPhy125, mBtnSta124, mBtnPhy191, mBtnPlb101;
    public static int questionRequestCode;
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
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_entry,
                    findViewById(R.id.bottom_design_entry));
            Button series1 = bottomSheet.findViewById(R.id.series1);
            Button series2 = bottomSheet.findViewById(R.id.series2);
            Button series3 = bottomSheet.findViewById(R.id.series3);
            Button series4 = bottomSheet.findViewById(R.id.series4);
            Button series5 = bottomSheet.findViewById(R.id.series5);

            series1.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Chm101Activity.class);
                questionRequestCode = 1;
                startActivity(intent);
            });

            series2.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Chm101Activity.class);
                questionRequestCode = 2;
                startActivity(intent);
            });

            series3.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Chm101Activity.class);
                questionRequestCode = 3;
                startActivity(intent);
            });

            series4.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Chm101Activity.class);
                questionRequestCode = 4;
                startActivity(intent);
            });

            series5.setVisibility(View.GONE);

            dialog.setCancelable(true);
            dialog.setContentView(bottomSheet);
            dialog.show();

        });

        mBtnChm115.setOnClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_entry,
                    findViewById(R.id.bottom_design_entry));
            Button series1 = bottomSheet.findViewById(R.id.series1);
            Button series2 = bottomSheet.findViewById(R.id.series2);
            Button series3 = bottomSheet.findViewById(R.id.series3);
            Button series4 = bottomSheet.findViewById(R.id.series4);
            Button series5 = bottomSheet.findViewById(R.id.series5);

            series1.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Chm115Activity.class);
                questionRequestCode = 1;
                startActivity(intent);
            });

            series2.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Chm115Activity.class);
                questionRequestCode = 2;
                startActivity(intent);
            });

            series3.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Chm115Activity.class);
                questionRequestCode = 3;
                startActivity(intent);
            });

            series4.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Chm115Activity.class);
                questionRequestCode = 4;
                startActivity(intent);
            });

            series5.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Chm115Activity.class);
                questionRequestCode = 5;
                startActivity(intent);
            });

            dialog.setCancelable(true);
            dialog.setContentView(bottomSheet);
            dialog.show();

        });

        mBtnMth111.setOnClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_entry,
                    findViewById(R.id.bottom_design_entry));
            Button series1 = bottomSheet.findViewById(R.id.series1);
            Button series2 = bottomSheet.findViewById(R.id.series2);
            Button series3 = bottomSheet.findViewById(R.id.series3);
            Button series4 = bottomSheet.findViewById(R.id.series4);
            Button series5 = bottomSheet.findViewById(R.id.series5);

            series1.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth111Activity.class);
                questionRequestCode = 1;
                startActivity(intent);
            });

            series2.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth111Activity.class);
                questionRequestCode = 2;
                startActivity(intent);
            });

            series3.setVisibility(View.GONE);
            series4.setVisibility(View.GONE);
            series5.setVisibility(View.GONE);

            series3.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth111Activity.class);
                questionRequestCode = 3;
                startActivity(intent);
            });

            series4.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth111Activity.class);
                questionRequestCode = 4;
                startActivity(intent);
            });

            series5.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth111Activity.class);
                questionRequestCode = 5;
                startActivity(intent);
            });

            dialog.setCancelable(true);
            dialog.setContentView(bottomSheet);
            dialog.show();

        });

        mBtnMth113.setOnClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_entry,
                    findViewById(R.id.bottom_design_entry));
            Button series1 = bottomSheet.findViewById(R.id.series1);
            Button series2 = bottomSheet.findViewById(R.id.series2);
            Button series3 = bottomSheet.findViewById(R.id.series3);
            Button series4 = bottomSheet.findViewById(R.id.series4);
            Button series5 = bottomSheet.findViewById(R.id.series5);

            series1.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth113Activity.class);
                questionRequestCode = 1;
                startActivity(intent);
            });

            series2.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth113Activity.class);
                questionRequestCode = 2;
                startActivity(intent);
            });

            series3.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth113Activity.class);
                questionRequestCode = 3;
                startActivity(intent);
            });

            series4.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth113Activity.class);
                questionRequestCode = 4;
                startActivity(intent);
            });
            series5.setVisibility(View.GONE);

            series5.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth113Activity.class);
                questionRequestCode = 5;
                startActivity(intent);
            });

            dialog.setCancelable(true);
            dialog.setContentView(bottomSheet);
            dialog.show();

        });

        mBtnGns111.setOnClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_entry,
                    findViewById(R.id.bottom_design_entry));
            Button series1 = bottomSheet.findViewById(R.id.series1);
            Button series2 = bottomSheet.findViewById(R.id.series2);
            Button series3 = bottomSheet.findViewById(R.id.series3);
            Button series4 = bottomSheet.findViewById(R.id.series4);
            Button series5 = bottomSheet.findViewById(R.id.series5);

            series1.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Gns111Activity.class);
                questionRequestCode = 1;
                startActivity(intent);
            });

            series2.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Gns111Activity.class);
                questionRequestCode = 2;
                startActivity(intent);
            });

            series3.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Gns111Activity.class);
                questionRequestCode = 3;
                startActivity(intent);
            });

            series4.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Gns111Activity.class);
                questionRequestCode = 4;
                startActivity(intent);
            });

            series5.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Gns111Activity.class);
                questionRequestCode = 5;
                startActivity(intent);
            });

            dialog.setCancelable(true);
            dialog.setContentView(bottomSheet);
            dialog.show();

        });

        mBtnPhy115.setOnClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_entry,
                    findViewById(R.id.bottom_design_entry));
            Button series1 = bottomSheet.findViewById(R.id.series1);
            Button series2 = bottomSheet.findViewById(R.id.series2);
            Button series3 = bottomSheet.findViewById(R.id.series3);
            Button series4 = bottomSheet.findViewById(R.id.series4);
            Button series5 = bottomSheet.findViewById(R.id.series5);

            series1.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy115Activity.class);
                questionRequestCode = 1;
                startActivity(intent);
            });

            series2.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy115Activity.class);
                questionRequestCode = 2;
                startActivity(intent);
            });

            series3.setVisibility(View.GONE);
            series4.setVisibility(View.GONE);
            series5.setVisibility(View.GONE);

            series3.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy115Activity.class);
                questionRequestCode = 3;
                startActivity(intent);
            });

            series4.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy115Activity.class);
                questionRequestCode = 4;
                startActivity(intent);
            });

            series5.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy115Activity.class);
                questionRequestCode = 5;
                startActivity(intent);
            });

            dialog.setCancelable(true);
            dialog.setContentView(bottomSheet);
            dialog.show();

        });

        mBtnPhy125.setOnClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_entry,
                    findViewById(R.id.bottom_design_entry));
            Button series1 = bottomSheet.findViewById(R.id.series1);
            Button series2 = bottomSheet.findViewById(R.id.series2);
            Button series3 = bottomSheet.findViewById(R.id.series3);
            Button series4 = bottomSheet.findViewById(R.id.series4);
            Button series5 = bottomSheet.findViewById(R.id.series5);

            series1.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy125Activity.class);
                questionRequestCode = 1;
                startActivity(intent);
            });

            series2.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy125Activity.class);
                questionRequestCode = 2;
                startActivity(intent);
            });

            series3.setVisibility(View.GONE);
            series4.setVisibility(View.GONE);
            series5.setVisibility(View.GONE);

            series3.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy125Activity.class);
                questionRequestCode = 3;
                startActivity(intent);
            });

            series4.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy125Activity.class);
                questionRequestCode = 4;
                startActivity(intent);
            });

            series5.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy125Activity.class);
                questionRequestCode = 5;
                startActivity(intent);
            });

            dialog.setCancelable(true);
            dialog.setContentView(bottomSheet);
            dialog.show();

        });

        mBtnSta124.setOnClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_entry,
                    findViewById(R.id.bottom_design_entry));
            Button series1 = bottomSheet.findViewById(R.id.series1);
            Button series2 = bottomSheet.findViewById(R.id.series2);
            Button series3 = bottomSheet.findViewById(R.id.series3);
            Button series4 = bottomSheet.findViewById(R.id.series4);
            Button series5 = bottomSheet.findViewById(R.id.series5);

            series1.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Sta124Activity.class);
                questionRequestCode = 1;
                startActivity(intent);
            });

            series2.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Sta124Activity.class);
                questionRequestCode = 2;
                startActivity(intent);
            });

            series3.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Sta124Activity.class);
                questionRequestCode = 3;
                startActivity(intent);
            });

            series4.setVisibility(View.GONE);
            series5.setVisibility(View.GONE);

            series4.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Sta124Activity.class);
                questionRequestCode = 4;
                startActivity(intent);
            });

            series5.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Sta124Activity.class);
                questionRequestCode = 5;
                startActivity(intent);
            });

            dialog.setCancelable(true);
            dialog.setContentView(bottomSheet);
            dialog.show();

        });

        mBtnPhy191.setOnClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_entry,
                    findViewById(R.id.bottom_design_entry));
            Button series1 = bottomSheet.findViewById(R.id.series1);
            Button series2 = bottomSheet.findViewById(R.id.series2);
            Button series3 = bottomSheet.findViewById(R.id.series3);
            Button series4 = bottomSheet.findViewById(R.id.series4);
            Button series5 = bottomSheet.findViewById(R.id.series5);

            series1.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy191Activity.class);
                questionRequestCode = 1;
                startActivity(intent);
            });

            series2.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy191Activity.class);
                questionRequestCode = 2;
                startActivity(intent);
            });

            series3.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy191Activity.class);
                questionRequestCode = 3;
                startActivity(intent);
            });

            series4.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy191Activity.class);
                questionRequestCode = 4;
                startActivity(intent);
            });

            series5.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy191Activity.class);
                questionRequestCode = 5;
                startActivity(intent);
            });

            dialog.setCancelable(true);
            dialog.setContentView(bottomSheet);
            dialog.show();

        });

        mBtnPlb101.setOnClickListener(view -> {
            startActivity(new Intent(this, Plb101Activity.class));
        });
    }

}