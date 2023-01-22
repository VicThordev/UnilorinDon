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

public class Phy192Activity extends AppCompatActivity {

    private List<Question> questionList;
    private TextView questionText, questionNo, countDown, answerText;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=0, mTimeLeft = 600000, questionAnswered = 1, clicked = 0;
    Button btnNext, btnPrev, btnEnd;
    private boolean mTimerRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phy192);

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
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        timer = new CountDownTimer(mTimeLeft,1000) {
            @Override
            public void onTick(long l) {
                mTimeLeft = (int) l;
                int minutes = mTimeLeft / 1000 / 60;
                int secs = (mTimeLeft/1000) % 60;
                String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, secs);
                countDown.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                showButton();
            }
        }.start();

        mTimerRunning = true;

        getQuestionPhase(questionList);

        setDataView(pos);
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
        setListeners();
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
        AlertDialog.Builder dialog1 = new AlertDialog.Builder(this);
        dialog1.setTitle("Confirm Submission")
                .setMessage("Are you sure you want to submit? \n You answered "+clicked+" out of 15 questions")
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
            rbOption1.setClickable(false);
            rbOption2.setClickable(false);
            rbOption3.setClickable(false);
            rbOption4.setClickable(false);
            btnEnd.setText(R.string.go_home);
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
        answerText.setText(String.format("Answer: %s", questionList.get(position).getAnswer()));

        questionNo.setText("Question "+questionAnswered+" of 15");

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Pls finish the test", Toast.LENGTH_SHORT).show();
    }

    private void getQuestionPhase(List<Question> list) {


        questionList.add(new Question("1. The thickness of the contral position of a thin converging lens can " +
                "be determined very accurately by using?",
                "A. vernier caliper",
                "B. micrometer screw gauge",
                "C. telescope",
                "D. microscope",
                "B. micrometer screw gauge"));

        questionList.add(new Question("2. The inner diameter of a small test tube can be measured " +
                "accurately using?",
                "A. micrometer screw gauge",
                "B. pair of dividers",
                "C. meter rule",
                "D. pair of vernier caliper",
                "D. pair of vernier caliper"));

        questionList.add(new Question("3. What is the least possible error in using a rule graduated?",
                "A. 0.1cm",
                "B. 0.5cm",
                "C. 1.0cm",
                "D. 2.0cm",
                "A. 0.1cm"));

        questionList.add(new Question("4. The error in the shape of a graph can be calculated using the " +
                "relationship.",
                "A. 4wn/R",
                "B. 4w/nR",
                "C. 4R/Wn",
                "D. 4/wnR",
                "B. 4w/nR"));

        questionList.add(new Question("5. Which of the following has a reading accuracy of 0.5mm?",
                "A. vernier caliper",
                "B. micrometer screw gauge",
                "C. meter rule",
                "D. protractor",
                "C. meter rule"));

        questionList.add(new Question("6. What is the reading of the vernier scale?",
                "A. 1.88cm",
                "B. 1.80cm",
                "C. 1.28cm" ,
                "D. 1.97cm",
                "B. 1.80cm",
                R.drawable.phy192snip));

        questionList.add(new Question("7. Given that MgT²=4Mπ² + gk², the plot of T² against L in the equation above gives " +
                "a straight line with:",
                "A. slope = 4π²m, intercept = gk²",
                "B. slope = 4π²m/g, intercept = gk²/m",
                "C. slope = 4π²/g, intercept = gk²/m",
                "D. slope = k²/g, intercept = 4π/g",
                "C. slope = 4π²/g, intercept = gk²/m"));

        questionList.add(new Question("8. An object is place 10cm in front of a concave mirror of focal length 15cm. " +
                "What is the position and nature of the image formed?",
                "A. 30cm and virtual ",
                "B. 60cm and real",
                "C. 60cm and virtual",
                "D. 30cm and real",
                "D. 30cm and real"));

        questionList.add(new Question("9. An object is placed 30cm from a concave mirror of focal length " +
                "15cm. The linear magnification of the image produced is ",
                "A. 0",
                "B. 2/3",
                "C. 1",
                "D. 2",
                "D. 2"));

        questionList.add(new Question("10. Which of the following is true for the image formed by a convex mirror \n" +
                "(i) the image is always virtual \n" +
                "(ii) the image lies between pole and focus \n" +
                "(iii) the image is never magnified \n" +
                "(iv) the focal length is negative",
                "A. I only",
                "B. I and II only",
                "C. II and III ",
                "D. I, II, III and IV", "D. I, II, III and IV"));

        questionList.add(new Question("11. If the refractive index of glass is 1.5, what is the critical angle at the air " +
                "glass interface",
                "A. arc sin(1/2)",
                "B. arc sin(2/3)" ,
                "C. arc sin(3/4)",
                "D. arc sin(8/2)",
                "B. arc sin(2/3)"));

        questionList.add(new Question( "12. The critical angle at an air-liquid interface is 45°. " +
                "Calculate the refractive index of the liquid.",
                "A. 2.4",
                "B. 1.4",
                "C. 3.5",
                "D. 4.2",
                "B. 1.4"));

        questionList.add(new Question( "13. An object is placed 5.6 * 10^-2m in front of a converging lens of " +
                "focal length 1.0 * 10^-1m. ",
                "A. real, erect and magnified",
                "B. virtual, erect and magnified",
                "C. real, inverted and magnified",
                "D. virtual, erect and diminished",
                "C. real, inverted and magnified"));

        questionList.add(new Question( "14. An object 3.0cm high is placed 60.0cm from a converging lens whose focal " +
                "length is 20.0cm. Calculate the size of the image formed.",
                "A. 0.5cm",
                "B. 1.5cm",
                "C. 2.0cm",
                "D. 6.0cm",
                "B. 1.5cm"));

        questionList.add(new Question( "15. A graph of 1/u against 1/v was plotted for a convex lens, what is the focal " +
                "length (f) deduced from the graph?",
                "A. a reciprocal of the intercept on 1/v axis",
                "B. addition of 1/u and 1/v",
                "C. area under the graph",
                "D. gradient of the graph",
                "A. a reciprocal of the intercept on 1/v axis"));

    }

    private void getQuestionPhase2(List<Question> list) {

        questionList.add(new Question( "1. An object is placed 25cm in front of a concave mirror whose focal length " +
                "is 15cm. Which of the following statement is true of the image formed?",
                "(A) inverted and 1.5 times the object size",
                "(B) erect and 2.0 times the object size",
                "(C) inverted and 3.5 times the object size",
                "(D) erect and 0.6 times the object size",
                "(A) inverted and 1.5 times the object size"));

        questionList.add(new Question( "2. A concave mirror will produce a virtual, erect and magnified " +
                "image when the object is placed _____",
                "(A) the principal focus",
                "(B) beyond the centre of curvature",
                "(C) distance less than the focus length",
                "(D) infinity",
                "(C) distance less than the focus length"));

        questionList.add(new Question( "3. The image of an object placed between placed between a concave " +
                "lens and the focal point is \n (i) virtual \n(ii) real \n" +
                "(iii) erect \n (iv) diminished",
                "(A) i, ii and iii",
                "(B) i, iii, iv",
                "(C) ii and iv",
                "(D) i and ii",
                "(C) ii and iv"));

        questionList.add(new Question( "4. If the object distance from a diverging lens is 25cm " +
                "and focal length 15cm. Determine the image distance",
                "(A) 9.35cm",
                "(B) 37.5cm",
                "(C) 20.5cm",
                "(D) 26.4cm",
                "(B) 37.5cm"));

        questionList.add(new Question( "5. The optical instrument for viewing near objects is the _____",
                "(A) telescope",
                "(B) sextant",
                "(C) microscope",
                "(D) binoculars",
                "(C) microscope"));

        questionList.add(new Question( "6. ",
                "(A) enough.",
                "(B) " +
                        "so. ",
                "(C) too.",
                "(D) quite. ",
                "(A) enough."));

        questionList.add(new Question( "22. You’re looking __________ pretty today, Temy.",
                "(A) very.",
                "(B) " +
                        "attractively. ",
                "(C) beautifully.",
                "(D) too. ",
                "(A) very."));

        questionList.add(new Question( "23. I promise to do my _________best.",
                "(A) possible.",
                "(B) " +
                        "very.",
                "(C) feasible.",
                "(D) variable. ",
                "(B) " +
                        "very."));

        questionList.add(new Question( "24. Water ________hydrogen and oxygen.",
                "(A) Varies between.",
                "(B) migrates.",
                "(C) consists of.",
                "(D) " +
                        "corresponds to",
                "(C) consists of."));

        questionList.add(new Question( "25. Agnes left the school very late, _____________________ ",
                "(A) didn’t she?",
                "(B) isn’t it?",
                "(C) " +
                        "hasn’t she? ",
                "(D) hadn’t she? ",
                "(A) didn’t she?"));

        questionList.add(new Question( "26. “Soyinka is good at play writing and Achebe is good at prose writing” Identify the sentence type.",
                "(A) Complex.",
                "(B) simple.",
                "(C) compound.",
                "(D) compound complex",
                "(C) compound."));

        questionList.add(new Question( "27. “ Mathematic _________compulsory for engineering students”.",
                "(A) is",
                "(B) as",
                "(C) are",
                "(D) " +
                        "were",
                "(A) is"));

        questionList.add(new Question( "28. The boy and the girl _______gone home.",
                "(A) has.",
                "(B) have.",
                "(C) had.",
                "(D) is. ",
                "(B) have."));

        questionList.add(new Question( "29. Which of the following is not a quality of good English?",
                "(A) coherence",
                "(B) emphasis or " +
                        "focus. ",
                "(C) unity.",
                "(D) none of the above. ",
                "(D) none of the above. "));

        questionList.add(new Question( "30. Inspite of the seemingly scarcity of marriageable men today many a girl __________ to get \n" +
                "married at the age of twenty-one.",
                "(A) Intend.",
                "(B) intended",
                "(C) plans",
                "(D) plan. ",
                "(C) plans"));
    }
}