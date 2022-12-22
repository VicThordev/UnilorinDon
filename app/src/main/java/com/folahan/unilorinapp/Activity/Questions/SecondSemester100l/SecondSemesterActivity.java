package com.folahan.unilorinapp.Activity.Questions.SecondSemester100l;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.folahan.unilorinapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class SecondSemesterActivity extends AppCompatActivity {

    private Button mBtnMth112, mBtnChm112, mBtnChm116, mBtnMth114, mBtnGns112, mBtnPhy152,
            mBtnPhy142, mBtnSta124, mBtnPhy192, mBtnMth116, mBtnGns114, mBtnChm132;
    public static int questionRequestCode;
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
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_entry,
                    findViewById(R.id.bottom_design_entry));
            Button series1 = bottomSheet.findViewById(R.id.series1);
            Button series2 = bottomSheet.findViewById(R.id.series2);
            Button series3 = bottomSheet.findViewById(R.id.series3);
            Button series4 = bottomSheet.findViewById(R.id.series4);
            Button series5 = bottomSheet.findViewById(R.id.series5);

            series1.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Chm112Activity.class);
                questionRequestCode = 1;
                startActivity(intent);
            });

            series2.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Chm112Activity.class);
                questionRequestCode = 2;
                startActivity(intent);
            });

            series3.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Chm112Activity.class);
                questionRequestCode = 3;
                startActivity(intent);
            });

            series4.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Chm112Activity.class);
                questionRequestCode = 4;
                startActivity(intent);
            });

            series5.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Chm112Activity.class);
                questionRequestCode = 5;
                startActivity(intent);
            });

            dialog.setCancelable(true);
            dialog.setContentView(bottomSheet);
            dialog.show();

        });

        mBtnGns114.setOnClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_entry,
                    findViewById(R.id.bottom_design_entry));
            Button series1 = bottomSheet.findViewById(R.id.series1);
            Button series2 = bottomSheet.findViewById(R.id.series2);
            Button series3 = bottomSheet.findViewById(R.id.series3);
            Button series4 = bottomSheet.findViewById(R.id.series4);
            Button series5 = bottomSheet.findViewById(R.id.series5);

            series1.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Gns114Activity.class);
                questionRequestCode = 1;
                startActivity(intent);
            });

            series2.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Gns114Activity.class);
                questionRequestCode = 2;
                startActivity(intent);
            });

            series3.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Gns114Activity.class);
                questionRequestCode = 3;
                startActivity(intent);
            });

            series4.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Gns114Activity.class);
                questionRequestCode = 4;
                startActivity(intent);
            });

            series5.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Gns114Activity.class);
                questionRequestCode = 5;
                startActivity(intent);
            });

            dialog.setCancelable(true);
            dialog.setContentView(bottomSheet);
            dialog.show();

        });

        mBtnChm116.setOnClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_entry,
                    findViewById(R.id.bottom_design_entry));
            Button series1 = bottomSheet.findViewById(R.id.series1);
            Button series2 = bottomSheet.findViewById(R.id.series2);
            Button series3 = bottomSheet.findViewById(R.id.series3);
            Button series4 = bottomSheet.findViewById(R.id.series4);
            Button series5 = bottomSheet.findViewById(R.id.series5);

            series1.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Chm116Activity.class);
                questionRequestCode = 1;
                startActivity(intent);
            });

            series2.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Chm116Activity.class);
                questionRequestCode = 2;
                startActivity(intent);
            });

            series3.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Chm116Activity.class);
                questionRequestCode = 3;
                startActivity(intent);
            });

            series4.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Chm116Activity.class);
                questionRequestCode = 4;
                startActivity(intent);
            });

            series5.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Chm116Activity.class);
                questionRequestCode = 5;
                startActivity(intent);
            });

            dialog.setCancelable(true);
            dialog.setContentView(bottomSheet);
            dialog.show();

        });

        mBtnMth112.setOnClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_entry,
                    findViewById(R.id.bottom_design_entry));
            Button series1 = bottomSheet.findViewById(R.id.series1);
            Button series2 = bottomSheet.findViewById(R.id.series2);
            Button series3 = bottomSheet.findViewById(R.id.series3);
            Button series4 = bottomSheet.findViewById(R.id.series4);
            Button series5 = bottomSheet.findViewById(R.id.series5);

            series1.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth112Activity.class);
                questionRequestCode = 1;
                startActivity(intent);
            });

            series2.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth112Activity.class);
                questionRequestCode = 2;
                startActivity(intent);
            });

            series3.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth112Activity.class);
                questionRequestCode = 3;
                startActivity(intent);
            });

            series4.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth112Activity.class);
                questionRequestCode = 4;
                startActivity(intent);
            });

            series5.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth112Activity.class);
                questionRequestCode = 5;
                startActivity(intent);
            });

            dialog.setCancelable(true);
            dialog.setContentView(bottomSheet);
            dialog.show();

        });

        mBtnMth114.setOnClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_entry,
                    findViewById(R.id.bottom_design_entry));
            Button series1 = bottomSheet.findViewById(R.id.series1);
            Button series2 = bottomSheet.findViewById(R.id.series2);
            Button series3 = bottomSheet.findViewById(R.id.series3);
            Button series4 = bottomSheet.findViewById(R.id.series4);
            Button series5 = bottomSheet.findViewById(R.id.series5);

            series1.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth114Activity.class);
                questionRequestCode = 1;
                startActivity(intent);
            });

            series2.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth114Activity.class);
                questionRequestCode = 2;
                startActivity(intent);
            });

            series3.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth114Activity.class);
                questionRequestCode = 3;
                startActivity(intent);
            });

            series4.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth114Activity.class);
                questionRequestCode = 4;
                startActivity(intent);
            });

            series5.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth114Activity.class);
                questionRequestCode = 5;
                startActivity(intent);
            });

            dialog.setCancelable(true);
            dialog.setContentView(bottomSheet);
            dialog.show();

        });

        mBtnGns112.setOnClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_entry,
                    findViewById(R.id.bottom_design_entry));
            Button series1 = bottomSheet.findViewById(R.id.series1);
            Button series2 = bottomSheet.findViewById(R.id.series2);
            Button series3 = bottomSheet.findViewById(R.id.series3);
            Button series4 = bottomSheet.findViewById(R.id.series4);
            Button series5 = bottomSheet.findViewById(R.id.series5);

            series1.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy142Activity.class);
                questionRequestCode = 1;
                startActivity(intent);
            });

            series2.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy142Activity.class);
                questionRequestCode = 2;
                startActivity(intent);
            });

            series3.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy142Activity.class);
                questionRequestCode = 3;
                startActivity(intent);
            });

            series4.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy142Activity.class);
                questionRequestCode = 4;
                startActivity(intent);
            });

            series5.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy142Activity.class);
                questionRequestCode = 5;
                startActivity(intent);
            });

            dialog.setCancelable(true);
            dialog.setContentView(bottomSheet);
            dialog.show();

        });

        mBtnPhy152.setOnClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_entry,
                    findViewById(R.id.bottom_design_entry));
            Button series1 = bottomSheet.findViewById(R.id.series1);
            Button series2 = bottomSheet.findViewById(R.id.series2);
            Button series3 = bottomSheet.findViewById(R.id.series3);
            Button series4 = bottomSheet.findViewById(R.id.series4);
            Button series5 = bottomSheet.findViewById(R.id.series5);

            series1.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy152Activity.class);
                questionRequestCode = 1;
                startActivity(intent);
            });

            series2.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy152Activity.class);
                questionRequestCode = 2;
                startActivity(intent);
            });

            series3.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy152Activity.class);
                questionRequestCode = 3;
                startActivity(intent);
            });

            series4.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy152Activity.class);
                questionRequestCode = 4;
                startActivity(intent);
            });

            series5.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy152Activity.class);
                questionRequestCode = 5;
                startActivity(intent);
            });

            dialog.setCancelable(true);
            dialog.setContentView(bottomSheet);
            dialog.show();

        });

        mBtnPhy142.setOnClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_entry,
                    findViewById(R.id.bottom_design_entry));
            Button series1 = bottomSheet.findViewById(R.id.series1);
            Button series2 = bottomSheet.findViewById(R.id.series2);
            Button series3 = bottomSheet.findViewById(R.id.series3);

            series1.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy142Activity.class);
                questionRequestCode = 1;
                startActivity(intent);
            });

            series2.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy142Activity.class);
                questionRequestCode = 2;
                startActivity(intent);
            });

            series3.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy142Activity.class);
                questionRequestCode = 3;
                startActivity(intent);
            });

            series3.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy142Activity.class);
                questionRequestCode = 4;
                startActivity(intent);
            });

            series3.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy142Activity.class);
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

        mBtnPhy192.setOnClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_entry,
                    findViewById(R.id.bottom_design_entry));
            Button series1 = bottomSheet.findViewById(R.id.series1);
            Button series2 = bottomSheet.findViewById(R.id.series2);
            Button series3 = bottomSheet.findViewById(R.id.series3);
            Button series4 = bottomSheet.findViewById(R.id.series4);
            Button series5 = bottomSheet.findViewById(R.id.series5);

            series1.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy192Activity.class);
                questionRequestCode = 1;
                startActivity(intent);
            });

            series2.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy192Activity.class);
                questionRequestCode = 2;
                startActivity(intent);
            });

            series3.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy192Activity.class);
                questionRequestCode = 3;
                startActivity(intent);
            });

            series4.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy192Activity.class);
                questionRequestCode = 4;
                startActivity(intent);
            });

            series5.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Phy192Activity.class);
                questionRequestCode = 5;
                startActivity(intent);
            });

            dialog.setCancelable(true);
            dialog.setContentView(bottomSheet);
            dialog.show();

        });

        mBtnMth116.setOnClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_entry,
                    findViewById(R.id.bottom_design_entry));
            Button series1 = bottomSheet.findViewById(R.id.series1);
            Button series2 = bottomSheet.findViewById(R.id.series2);
            Button series3 = bottomSheet.findViewById(R.id.series3);

            series1.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth116Activity.class);
                questionRequestCode = 1;
                startActivity(intent);
            });

            series2.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth116Activity.class);
                questionRequestCode = 2;
                startActivity(intent);
            });

            series3.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Mth116Activity.class);
                questionRequestCode = 3;
                startActivity(intent);
            });

            dialog.setCancelable(true);
            dialog.setContentView(bottomSheet);
            dialog.show();

        });

        mBtnChm132.setOnClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_entry,
                    findViewById(R.id.bottom_design_entry));
            Button series1 = bottomSheet.findViewById(R.id.series1);
            Button series2 = bottomSheet.findViewById(R.id.series2);
            Button series3 = bottomSheet.findViewById(R.id.series3);

            series1.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Chm132Activity.class);
                questionRequestCode = 1;
                startActivity(intent);
            });

            series2.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Chm132Activity.class);
                questionRequestCode = 2;
                startActivity(intent);
            });

            series3.setOnClickListener(view1 -> {
                Intent intent = new Intent(this, Chm132Activity.class);
                questionRequestCode = 3;
                startActivity(intent);
            });

            dialog.setCancelable(true);
            dialog.setContentView(bottomSheet);
            dialog.show();
        });
    }
}

