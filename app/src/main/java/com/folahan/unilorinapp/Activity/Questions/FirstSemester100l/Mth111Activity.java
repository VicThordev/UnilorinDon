package com.folahan.unilorinapp.Activity.Questions.FirstSemester100l;

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
import java.util.Objects;
import java.util.Random;

public class Mth111Activity extends AppCompatActivity {

    private List<Question> questionList;
    private Random random;
    private TextView questionText, questionNo, countDown, answerText;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=0, mTimeLeft = 600000, questionAnswered = 1, clicked = 0;
    Button btnNext, btnPrev, btnEnd;
    private boolean mTimerRunning;
    private AlertDialog.Builder dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mth111);

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

        setListeners();

        Objects.requireNonNull(getSupportActionBar()).hide();

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

        if (FirstSemesterActivity.questionRequestCode == 1) {
            getQuestionPhase(questionList);

            setDataView(pos);
        } else if (FirstSemesterActivity.questionRequestCode == 2) {
            getQuestionPhase2(questionList);

            setDataView(pos);
        }


        btnNext=findViewById(R.id.btnNext);
        btnPrev=findViewById(R.id.button_previous);

        btnNext.setOnClickListener(view -> {
            if (questionAnswered == 15) {
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
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
            answerText.setText(R.string.log_out);
            answerText.setText(questionList.get(pos).getAnswer());
            rbOption1.setVisibility(View.GONE);
            dialog.cancel();
        });
        dialog.setCancelable(false);
        dialog.setContentView(bottomSheet);
        dialog.show();
    }

    private void setListeners() {
        rbOption1.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption1.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
            clicked++;
        });

        rbOption2.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption2.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
            clicked++;
        });

        rbOption3.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption3.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
            clicked++;
        });

        rbOption4.setOnClickListener(view -> {
            if (questionList.get(pos).getAnswer().trim().toLowerCase(Locale.ROOT)
                    .equals(rbOption4.getText().toString().trim().toLowerCase(Locale.ROOT))) {
                pos2++;
            }
            clicked++;
        });

        btnEnd.setOnClickListener(view -> dialogAlert());
    }

    private void dialogAlert() {
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Confirm Submission")
                .setMessage("Are you sure you want to submit? \n You answered "+clicked+" out of 30 questions")
                .setPositiveButton("Yes", (dialog, which) -> {
                    showButton();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .setIcon(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.ic_cancel)).show();
    }

    private void setDataView(int position) {
        questionText.setText(questionList.get(position).getQuestion());

        rbOption1.setText(questionList.get(position).getOption1());
        rbOption2.setText(questionList.get(position).getOption2());
        rbOption3.setText(questionList.get(position).getOption3());
        rbOption4.setText(questionList.get(position).getOption4());
        answerText.setText(questionList.get(position).getAnswer());

        questionNo.setText("Question "+questionAnswered+" of 15");
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Pls finish the test", Toast.LENGTH_SHORT).show();
    }

    private void getQuestionPhase(List<Question> list) {


        questionList.add(new Question("1. Express the sin4x + sin2x as product of two trigonometrical ratio.",
                "(a) 2sin3xcos2x",
                "(b) 2sin3xcosx", "(c) 2sin2xcos2x",
                "(d) sin3xcosx",
                "(b) 2sin3xcosx"));

        questionList.add(new Question("2. Express cos4x – cos2x as product off two trigonometrical ratio.",
                "(a) -2sin3xsinx",
                "(b) 2sin3xsin3x",
                "(c) 2sinxsin3x",
                "(d) none of the above",
                "(a) -2sin3xsinx"));

        questionList.add(new Question("3. Express 30° in radian", "(a) π/6",
                "(b) 6/π", "(c) π",
                "(d) none of the above",
                "(a) π/6"));

        questionList.add(new Question("4. Express π/2 in degree",
                "(a) 80°",
                "(b) 90°",
                "(c) 135°", "(d) 45°",
                "(b) 90°"));

        questionList.add(new Question("5. Express 3.5rad in degree",
                "(a) 200.5°",
                "(b) 200°", "(c) 20°",
                "(d) 150°",
                "(a) 200.5°"));

        questionList.add(new Question("6. The sun subtend an angle at 35° from the center of the earth whose distant from the centre of " +
                "the earth is 382,100km. Find the diameter of the sun",
                "(a) 600km", "(b) 650km",
                "(c) 648.6km" ,
                "(d) 648km", "(c) 648.6km"));

        questionList.add(new Question("7. An arc PQ of length 20cm is marked on a circle of radius 6cm, find the area of the sector binded " +
                "by this arc and the radius ",
                "(a) 55cm²", "(b) 60cm²",
                "(c) 58.6cm²", "(d) none of the above",
                "(b) 60cm²"));

        questionList.add(new Question("8. Simplify sin 75",
                "(a) (√2 +√6)/ 4", "(b) √2 + √6 ",
                "(c) √6/ 4",
                "(d) √2", "(a) (√2 +√6)/ 4"));

        questionList.add(new Question("9. Find the value of sin(α + β) without using table if sin α = 3/5 and tan β= 5/1 and α and β are " +
                "acutes",
                "(a) 56/65", "(b) 50/65",
                "(c) 55.5/65",
                "(d) 55/6",
                "(a) 56/65"));

        questionList.add(new Question("10. Factorize 5x^2 + 9x + 4",
                "(a) x = -4/5 or x=-1",
                "(b) x=4/5 or x=1",
                "(c) x=4/5 or x = -1",
                "(d) x= -4/5 or x = 1", "(a) x = -4/5 or x=-1"));

        questionList.add(new Question("11. Find “a” if the equation has equal root (5a + 1)x² – 8ax +3a = 0",
                "(a) a = 0 or a = 3", "(b) a= 0 or a = 2",
                "(c) a=0 or a = 1",
                "(d) a= 0",
                "(a) a = 0 or a = 3"));

        questionList.add(new Question("12. Find the values of “p” for which the equation (4p + 1)x^2 + ( p + 3)x + 1 = 0",
                "(a) p = 5 + 2√5 or p = " +
                        "5 - 2√5",
                "b) p = 5 or p = 2",
                "(c) p= √5 + 5√5 or p = 2",
                "(d) none of the above",
                "(a) p = 5 + 2√5 or p = 5" +
                        "5 - 2√5"));

        questionList.add(new Question("13. Find the value of K for which d roots of the equation is x² – (4 + k)x + 9 = 0",
                "(a) K ≤ −10 or K ≥ 2", "(b) K ≤ 10 or K ≥ 2",
                "(c) K ≤ 10 or K ≥ −2",
                "(d) K ≤ −10 or K ≥ −2",
                "(a) K ≤ −10 or K ≥ 2"));

        questionList.add(new Question("14. If α and β are roots of the equation 3x² -7x – 1 = 0 find the value of (α + β)²",
                "(a) 61/9", "(b) -61/9",
                "(c) 9/61",
                "(d) -9/61",
                "(a) 61/9"));

        questionList.add(new Question("15. If α and β are roots of the equation 3x² -7x – 1 = 0 find the value of α²+ β²",
                "(a) 61/9", "(b) -61/9",
                "(c) 55/9",
                "(d) -55/9",
                "(c)55/9"));
    }

    private void getQuestionPhase2(List<Question> list) {

        questionList.add(new Question("1. Find the value of (1.02)^8 correct to 5 decimal place using the first five terms of the expansion (1 + 8)^8",
                "(a) 1.02766", "(b) 1.0277",
                "(c) 1.028",
                "(d) 1.03",
                "(a) 1.02766"));

        questionList.add(new Question("2. Find the value of x for the expression (2x + 5)/(x² –x – 6) does not exist",
                "(a) x = -3 or x = -2", "(b) x = 3 or x = -2",
                "(c) x = 3 or x = 2",
                "(d) x = 2 or x = -3",
                "(b) x = 3 or x = -2"));

        questionList.add(new Question("3. Calculate “a” if the coefficient of x^3" +
                "in ( a + 2x )^5" +
                "is 320",
                "(a) a = 2", "(b) a= 3",
                "(c) a = 4",
                "(d) a = 1",
                "(a) a = 2"));

        questionList.add(new Question("Find the coefficient of x^8 in the expansion of (2x – 5)^10",
                "(a) 288", "(b) 288000",
                "(c) 28800",
                "(d) 2880",
                "(b) 288000"));

        questionList.add(new Question("Find the 40th term of the linear sequence 66, 11, 16, 21 ……",
                "(a) 200", "(b) 201",
                "(c) 202",
                "(d) 203",
                "(b) 201"));

        questionList.add(new Question("The 4th term of an AP is 15, the 9th term is 35. Find T15",
                "(a) 56", "(b) 57",
                "(c) 58",
                "(d) 59",
                "(c) 58"));

        questionList.add(new Question("If 8, x, y, z and 20 are in AP . find x, y and z",
                "(a) x = 11 y = 14 z=17",
                "(b) x = 10 y = 13 z= 16",
                "(c) x = 14 " +
                        "y= 17 z = 11",
                "(d) x = 17 y= 14 z = 10",
                "(a) x = 11 y = 14 z=17"));

        questionList.add(new Question("Find the value of x given that x+1, 2x , 2x + 3 are consecutive terms of AP",
                "(a) 4",
                "(b) 5",
                "(c) -4",
                "(d) -5",
                "(a) 4"));

        questionList.add(new Question("The sum of nth term of a GP is for r < 1 is given as",
                "(a) a(1 - r^n)/1-r",
                "(b) a(1 - r^n)/r – 1 ",
                "(c) (1 - r^n)/1-r",
                "(d) a(1 - r^n)/r",
                "(a) a(1 - r^n)/1-r"));

        questionList.add(new Question("The third term of a G.P is 63 and the 5th term is 567. Find the sum of the 6th term of the " +
                "progression",
                "(a) 2550",
                "(b) 2548",
                "(c) 2540",
                "(d) 255",
                "(b) 2548"));

        questionList.add(new Question("Let p = { 1,2,3,4,5,6} and Q = { 2,4,6,8,10}. Find p – q",
                "(a) { 1,5,4}",
                "(b) { 1,3,5}",
                "(c) {1,3,8 }",
                "(d) 255",
                "(d) { 1, 3, 2}"));

        questionList.add(new Question("Determine the complex no Z which satisfies Z( 3 + 5i) = -1 – i ",
                "(a) -8/34, 2i/34",
                "(b) 8/34, -2i/34",
                "(c) 8i/34, 2/34",
                "(d) 2i/34, 8/33",
                "(a) -8/34, 2i/34"));

        questionList.add(new Question("Express Z = 1 + i in polar form and argand form",
                "(a) √2 (cos45b+ isin45), [ √2, 45] ",
                "(b) (cos45b+ isin45), [ √2, 45]",
                "(c)2 (cos45b+ isin45), [ √2, 45]",
                " (d) none of the above",
                "(a) √2 (cos45b+ isin45), [ √2, 45] "));
    }
}