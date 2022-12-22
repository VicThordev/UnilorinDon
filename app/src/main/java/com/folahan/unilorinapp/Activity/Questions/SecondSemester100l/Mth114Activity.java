package com.folahan.unilorinapp.Activity.Questions.SecondSemester100l;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.folahan.unilorinapp.MainActivity;
import com.folahan.unilorinapp.Model.Question;
import com.folahan.unilorinapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Mth114Activity extends AppCompatActivity {

    private List<Question> questionList;
    private Random random;
    private TextView questionText, questionNo, countDown, answerText;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=0, mTimeLeft = 600000, questionAnswered = 1;
    Button btnNext, btnPrev, btnEnd;
    private AlertDialog.Builder dialog;
    private boolean mTimerRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chm132);

        questionList = new ArrayList<>();
        questionText = findViewById(R.id.questionText);
        answerText = findViewById(R.id.txtAnswer);
        btnEnd = findViewById(R.id.buttonGoto);
        rbOption1 = findViewById(R.id.radioA);
        rbOption2 = findViewById(R.id.radioB);
        rbOption3 = findViewById(R.id.radioC);
        rbOption4 = findViewById(R.id.radioD);
        questionNo = findViewById(R.id.question1);
        countDown = findViewById(R.id.timeText);
        random = new Random();

        timer = new CountDownTimer(mTimeLeft,1000) {
            @Override
            public void onTick(long l) {
                mTimeLeft = (int) l;
                int minutes = (int) (mTimeLeft/1000) / 60;
                int secs = (int) (mTimeLeft/1000) % 60;
                String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, secs);
                countDown.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                showButton();
            }
        }.start();

        mTimerRunning = true;

        setListeners();

        getQuestionPhase(questionList);

        setDataView(pos);
        btnNext=findViewById(R.id.btnNext);
        btnPrev=findViewById(R.id.button_previous);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        btnNext.setOnClickListener(view -> {
            if (questionAnswered == 50) {
                Toast.makeText(this, "Last Question", Toast.LENGTH_SHORT).show();
            } else {
                questionAnswered++;
                pos++;
                setDataView(pos);
            }
        });

        btnPrev.setOnClickListener(view -> {
            if (questionAnswered == 1) {
                Toast.makeText(this, "First Question", Toast.LENGTH_SHORT).show();
            } else {
                questionAnswered--;
                pos--;
                setDataView(pos);
            }
        });
    }

    private void setListeners() {
        rbOption1.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption1.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
        });

        rbOption2.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption2.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
        });

        rbOption3.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption3.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
        });

        rbOption4.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption4.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
        });

        btnEnd.setOnClickListener(view -> dialogAlert());
    }

    private void dialogAlert() {
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Confirm Submission")
                .setMessage("Are you sure you want to submit? \n You answered "+questionAnswered+" out of 30 questions")
                .setPositiveButton("Yes", (dialog, which) -> {
                    showButton();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .setIcon(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.ic_cancel)).show();
    }

    protected void showButton() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet,
                findViewById(R.id.design_bottom_sheet));
        TextView scoreShow = bottomSheet.findViewById(R.id.score);
        Button goHome = bottomSheet.findViewById(R.id.btnScore);
        Button showAnswer = bottomSheet.findViewById(R.id.btnAnswer);

        scoreShow.setText("Your score is \n"+pos2+" out of 30");

        goHome.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
            dialog.dismiss();
            finish();
        });

        showAnswer.setOnClickListener(view -> {
            timer.cancel();
            answerText.setVisibility(View.VISIBLE);
            rbOption1.setVisibility(View.GONE);
            rbOption2.setVisibility(View.GONE);
            rbOption3.setVisibility(View.GONE);
            rbOption4.setVisibility(View.GONE);
            btnEnd.setText("Go Home");
            btnEnd.setOnClickListener(view1 -> startActivity(new Intent(this, MainActivity.class)));
            answerText.setText(R.string.log_out);
            answerText.setText(questionList.get(pos).getAnswer());
            rbOption1.setVisibility(View.GONE);
            dialog.cancel();
        });
        dialog.setCancelable(false);
        dialog.setContentView(bottomSheet);
        dialog.show();
    }

    private void setDataView(int position) {
        questionText.setText(questionList.get(position).getQuestion());

        rbOption1.setText(questionList.get(position).getOption1());
        rbOption2.setText(questionList.get(position).getOption2());
        rbOption3.setText(questionList.get(position).getOption3());
        rbOption4.setText(questionList.get(position).getOption4());
        answerText.setText(questionList.get(position).getAnswer());

        questionNo.setText("Question "+questionAnswered+" of 30");

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Pls finish the test", Toast.LENGTH_SHORT).show();
    }

    private void getQuestionPhase(List<Question> list) {


        questionList.add(new Question("1. Find the equation of the line through point (1,2) and parallel to 3x+5y=8",
                "(a) 2x+6x=13",
                "(b) 3x+5y = 13",
                "(c) 2x-5y=31",
                "(d) 8y+3-7x=13",
                "(b) 3x+5y = 13"));

        questionList.add(new Question("2. Find the inverse of the mapping: f(x) = 3x-5/2, x belonging to real no",
                "(a) 2+5y/4 ",
                "(b) 2y+5/3 ",
                "(c) 3/2y-4 ",
                "(d) 3y-2/5",
                "(a) 2+5y/4 "));

        questionList.add(new Question("3. Express sin(x+h) as a series of power of h and evaluate sin 30 correct to 5 decimal places",
                "(a) 0.51504 ",
                "(b) 0.6854",
                "(c) 0.5754",
                "(d) 1.0865",
                "(a) 0.51504 "));

        questionList.add(new Question("4. Find the equation of the line passing through the points (5,3) (-2,1)",
                "(a) 5y+2x=7",
                "(b) x-4y=8",
                "(c) 7x-y=9",
                "(d) 2x-7y=11",
                "(d) 2x-7y=11"));

        questionList.add(new Question("5. a*b=a+b-1=b+a-1. This represents ",
                "(a) associative",
                "(b) distributive",
                "(c) commutative",
                "(d) inverse",
                "(c) commutative"));

        questionList.add(new Question("6. Find the gradient of the line passing through points (4,2) and (-6,10)",
                "(a) -8/10",
                "(b) -4/10",
                "(c) 7/5" ,
                "(d) -4/9", "(d) -4/9"));

        questionList.add(new Question("7. A car left a point A at an angle of 25 degree and covered a distance of 9km to another point C, then changes it direction to a bearing S20E and travel a distance of 11km until it get to a point B which is due east of its starting point (i) What is the distance of the car to its starting point (ii) What is the car’s bearing to the point?",
                "(a)(i)7.2km (ii)340degree", "(b)(i)8km (ii)270 degree",
                "(c)(i)8.5km (ii)340 degree", "(d)(i)6.7km (ii)220degree",
                "(c)(i)8.5km (ii)340 degree"));

        questionList.add(new Question("8. Find x, if 9ˣ² = 3⁵ˣ-²",
                "A. ½ or 2", "B. 2",
                "C. -2 or 1",
                "D. -2 or 2",
                "A. ½ or 2"));

        questionList.add(new Question("9. Solve for x in the equation 5²ˣ - 5¹+ˣ + 6",
                "A. log 5/log 3",
                "B. 6 log 5",
                "C. log 3/log 5",
                "D. log 5x",
                "C. log 3/log 5"));

        questionList.add(new Question("10. _____ is a square matrix in which all of its diagonal elements are zero.",
                "A. Zero matrix",
                "B. Null matrix",
                "C. Diagonal matrix",
                "D. Column matrix", "C. Diagonal matrix"));

        questionList.add(new Question("11. Determine the zero of the function defined by f(x) = 2x + 5",
                "A. -2/5",
                "B. 5/2" ,
                "C. -5/2",
                "D. 2/5",
                "C. -5/2"));

        questionList.add(new Question( "12. a, h, g \n h, h, f \n g, f, c \n The matrix above represents",
                "A. Skew Symmetric matrix",
                "B. Symmetric matrix",
                "C. Diagonal matrix",
                "D. Straight Matrix",
                "B. Symmetric matrix"));

        questionList.add(new Question( "13. x+3  2y+x \n z-1  4a-6 \n = 0  -7 \n 3  2a \n Find the values of x,y and z",
                "A. x = -3, y = -2, z = 4",
                "B. x = -2, y = -3, z = 2",
                "C. x = -3, y = -2, z = 3",
                "D. A. x = -4, y = -2, z = -2",
                "A. x = -3, y = -2, z = 4"));

        questionList.add(new Question( "14. Given that f: x² + px + q is a mapping defined on the set of real numbers. " +
                "If f(1) is 2 & f(-1) = 6. Find the values of p & q",
                "A. q = 3, p = -2",
                "B. q = 2, p = -1",
                "C. q = 2, p = 2",
                "D. q = 1, p = -3",
                "A. q = 3, p = -2"));

        questionList.add(new Question( "15. Determine the domain D of the function h: x -> x² + 2, " +
                "R{3} & h is defined on D.",
                "A. √3",
                "B. +/-√2",
                "C. +/-1",
                "D. No answer",
                "C. +/-1"));
    }

    private void getQuestionPhase1(List<Question> list) {

        questionList.add(new Question("1. Convert -128°10¹ to degrees",
                "A. -0.5",
                "B. -0.31",
                "C. 0.871",
                "D. -0.7148",
                "D. -0.7148"));

        questionList.add(new Question("2. Solve 3°(4°5)",
                "A. 3",
                "B. 5",
                "C. 4/5",
                "D. -3",
                "B. 5"));

        questionList.add(new Question("3. Solve 2▽(3▽4)",
                "A. 21",
                "B. 38",
                "C. 7",
                "D. 59",
                "D. 59"));

        questionList.add(new Question("4. A Surjective mapping is also called ______",
                "A. One to One Mapping",
                "B. Onto Mapping ",
                "C. Bijective Mapping",
                "D. Rotary Mapping",
                "A. One to One Mapping"));

        questionList.add(new Question("5. If a x e = e x a, 'e' is said to be ",
                "A. Inverse element",
                "B. Identity element",
                "C. Inverse set",
                "D. Binary element",
                "B. Identity element"));

        questionList.add(new Question("6. Express sin 2A",
                "A. 2sinAcosA",
                "B. sin2Acos2A",
                "C. cosA²",
                "D. sinA²",
                "A. 2sinAcosA"));

        questionList.add(new Question("7. a --->--- a \n b --->--- b \n c --->--- c \n This mapping is a kind of ",
                "A. One to One Mapping",
                "B. Onto Mapping",
                "C. Identity Mapping",
                "D. Surjective Mapping",
                "C. Identity Mapping"));

        questionList.add(new Question("8. Express tan 60 in surd form",
                "A. √3",
                "B. √3/2",
                "C. 1/2",
                "D. -√2",
                "A. √3"));

        questionList.add(new Question("9. a*(b▽c) = (a*b)▽(a*c). This represents ",
                "A. Associativity",
                "B. Identity",
                "C. Distributivity",
                "D. Inverse",
                "C. Distributivity"));

        questionList.add(new Question("10. For angles greater than 90° but less than 180° in trigonometry are found using",
                "A. α + θ = 180",
                "B. θ = α - 180",
                "C. θ = 180 - α",
                "D. θ = 360 - α",
                "C. θ = 180 - α"));

        questionList.add(new Question("11. Express cos(A + B)",
                "A. 12.1cm",
                "B. 10.79cm",
                "C. 11.09cm",
                "D. cosAcosB - sinAsinB",
                "D. cosAcosB - sinAsinB"));

        questionList.add(new Question("12. Evaluate cos(−9π/4)",
                "A. -√2/2",
                "B. -½",
                "C. √2/2",
                "D. ½",
                "C. √2/2"));

        questionList.add(new Question("13. Simplify tan (A+B+C)",
                "A. (tan 2A + tan 2B + tan 2C - tanAtanBtanC)/ 1 - tanAtanB - tanBtanC - tanAtanC",
                "B. (tan A + tan B + tan C - tanAtanBtanC)/ 1 - tanAtanB - tanBtanC - tanAtanC",
                "C. (tan A - tan B - tan C + tanAtanBtanC)/ 1 + tanAtanB + tanBtanC - tanAtanC",
                "D. (tan² A + tan² B + tan² C - tanAtanBtanC)/ 1 - tanAtanB - tanBtanC - tanAtanC",
                "B. (tan A + tan B + tan C - tanAtanBtanC)/ 1 - tanAtanB - tanBtanC - tanAtanC"));

        questionList.add(new Question("14. Simplify sin 2A",
                "A. sin²Acos²A",
                "B. 2sin²Acos²A",
                "C. 2sinAcosA",
                "D. 2sec²A",
                "C. 2sinAcosA"));

        questionList.add(new Question("15. Simplify cos 2A",
                "A. 1 - 2sin²A",
                "B. 2 - 2sin²A",
                "C. cosec²A",
                "D. 2sec²A",
                "A. 1 - 2sin²A"));
    }


}