package com.folahan.unilorinapp.Activity.Questions.SecondSemester100l;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
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

public class Mth112Activity extends AppCompatActivity {

    private List<Question> questionList;
    private Random random;
    private TextView questionText, questionNo, countDown;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=0, mTimeLeft = 600000, questionAnswered = 1;
    Button btnNext, btnPrev, btnEnd;
    private AlertDialog dialog;
    private boolean mTimerRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mth112);

        questionList = new ArrayList<>();
        questionText = findViewById(R.id.questionText);
        btnEnd = findViewById(R.id.buttonGoto);
        rbOption1 = findViewById(R.id.radioA);
        rbOption2 = findViewById(R.id.radioB);
        rbOption3 = findViewById(R.id.radioC);
        rbOption4 = findViewById(R.id.radioD);
        questionNo = findViewById(R.id.question1);
        countDown = findViewById(R.id.timeText);
        random = new Random();

        setListeners();

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

        getQuestionPhase(questionList);

        setDataView(pos);
        btnNext=findViewById(R.id.btnNext);
        btnPrev=findViewById(R.id.button_previous);

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

    protected void showButton() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View bottomSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet,
                (LinearLayout) findViewById(R.id.design_bottom_sheet));
        TextView scoreShow = bottomSheet.findViewById(R.id.score);
        Button goHome = bottomSheet.findViewById(R.id.btnScore);

        scoreShow.setText("Your score is \n"+pos2+" out of 30");

        goHome.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
            dialog.dismiss();
            finish();
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
        dialog = new AlertDialog.Builder(this, androidx.appcompat.R.style.ThemeOverlay_AppCompat_ActionBar)
                .setTitle("Confirm Submission")
                .setMessage("Are you sure you want to submit? \n You answered "+questionAnswered+" out of 30 questions")
                .setPositiveButton("Yes", (dialog, which) -> {
                    showButton();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .setIcon(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.ic_login)).show();
    }

    private void setDataView(int position) {
        questionText.setText(questionList.get(position).getQuestion());

        rbOption1.setText(questionList.get(position).getOption1());
        rbOption2.setText(questionList.get(position).getOption2());
        rbOption3.setText(questionList.get(position).getOption3());
        rbOption4.setText(questionList.get(position).getOption4());

        questionNo.setText("Question "+questionAnswered+" of 30");
        if (questionAnswered == 30) {
            showButton();
        }

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Pls finish the test", Toast.LENGTH_SHORT).show();
    }

    private void getQuestionPhase(List<Question> list) {


        questionList.add(new Question("If y = (x-7)^6. Find the derivative of y",
                "A. 7x(x-7)^5",
                "B. 6(x-7)^5",
                "C. 6x(x-7)^6",
                "D. 7x(x-7)^6",
                "B. 6(x-7)^5"));

        questionList.add(new Question("Given that y = (2x^2 + sin x - 3x)^5. Find dy/dx",
                "A. 5(2x^2 + sin x - 3x).(4x)",
                "B. 4(2x^2 + sin x - 3x)^-4.(4x^2 + sin x + 3)",
                "C. 5(2x^2 + sin x - 3x)^4.(4x + cos x - 3)",
                "D. 3(2x^2 + sin x - 3x)^5.(2x + cos x - 3)",
                "C. 5(2x^2 + sin x - 3x).(4x + cos x - 3)"));

        questionList.add(new Question("What is the derivative of y = tan^3(4x - 6)",
                "A. sec^2 (4x - 6)",
                "B. 4sec^2 (4x - 6)",
                "C. 4tan^2 (4x - 6)",
                "D. 4sec^6 (4x - 6)",
                "D. 4sec^6 (4x - 6)"));

        questionList.add(new Question("Find the derivative of the equation y = e^(2x-3)",
                "A. 2e^(2x-3)",
                "B. e^(2x-3)",
                "C. 2e^(2x+3)",
                "D. 2e^(2x^2-3)",
                "A. 2e^(2x-3)"));

        questionList.add(new Question("y = xe^(x^2)sin x find dy/dx",
                "A. 2x^2e^(x^2)cosx",
                "B. 2xe^(x^2)cosx",
                "C. 2x^2e^(x^2)sin x",
                "D. 2x^2e^(x^2)(-cosx)",
                "A. 2x^2e^(x^2)cosx"));

        questionList.add(new Question("6. Compute the ratio of the electric force of attraction to the gravitational force of attraction between \n" +
                "the electron and the proton in a hydrogen atom assuming that their distance of separation is 0.53pm \n" +
                "(me = 9.1 x 10^-31kg, mp = 1.7 x 10^-27kg, e = 1.6 x 10^-19C)",
                "A. 2.2 x 10^39",
                "B. 1.1 x 10^39",
                "C. 2.1 x 10^39" ,
                "D. 1.2 x 10^39", "A. 2.2 x 10^39"));

        questionList.add(new Question("7. What is the magnitude of electrostatic force of attraction between an α-particle and an electron 10^-" +
                "13m apart?",
                "A. 2.3 x 10^-2N", "B. 2.6 x 10^-2N",
                "C. 4.3 x 10^-2N", "D. 4.6 x 10^-2N",
                "D. 4.6 x 10^-2N"));

        questionList.add(new Question("8. From the Coulomb's Law, what will be the slope of the graph of Log F vs Log r? ",
                "A. ½", "B. 2",
                "C. -log2",
                "D. -2",
                "A. ½"));

        questionList.add(new Question("9. Two equal charges of equal magnitude exert an attractive force of 4.0 x 10^-4N on each other. If the " +
                "magnitude of each charge is 2.0μC, how far apart are the charges?",
                "A. 9.5m",
                "B. 9.7m",
                "C. 10.5m",
                "D. 10.7m",
                "A. 9.5m"));

        questionList.add(new Question("10. What is the magnitude of a point charge whose electric field 50cm away has magnitude 2.0N/C?",
                "A. 4.56 x 10^-11C",
                "B. 4.66 x 10^-11C",
                "C. 5.56 x 10^-11C ",
                "D. 5.66 x 10^-11C", "C. 5.56 x 10^-11C "));

        questionList.add(new Question("11. What is the magnitude of an electric field in which the force on an electron is equal in magnitude to " +
                "the weight of an electron?",
                "A. 4.58 x 10^-11N/C",
                "B. 5.58 x 10^-11N/C" ,
                "C. 6.58 x 10^-11N/C",
                "D. 7.58 x 10-^11N/C",
                "B. 5.58 x 10^-11N/C"));

        questionList.add(new Question( "12. A small object carrying a charge of 5 x 10^-9" +
                "C experiences a downward force of 20 x 10^-9N when" +
                "placed at a certain point in an electric field. What's the electric field at the point?",
                "A. 100 N/C",
                "B. 40 N/C",
                "C. 10 N/C",
                "D. 4 N/C",
                "D. 4 N/C"));

        questionList.add(new Question( "13. The distance between two positive charges 6μC & 8μC is 50cm. Calculate the electric field intensity, " +
                "due to each charges, at a point p in between the two charges and 10cm from the 6μC charge" +
                "respectively",
                "A. 5.4 x 10^6N/C, 4.5 x 10^5N/C",
                "B. 6.4 x 10^6N/C, 5.5 x 10^5N/C",
                "C. 7.4 x 10^6N/C, 6.5 x 10^5N/C",
                "D. 8.4 x 10^6N/C, 7.5 x 10^5N/C",
                "A. 5.4 x 10^6N/C, 4.5 x 10^5N/C"));

        questionList.add(new Question( "14. Find the electric field at a point 0.2m from a charge of 20μC, what force will the electric field exert " +
                "on a charge of 10μC, placed at that point?" +
                "education. ",
                "A. 3.5 x 10" +
                        "^6N/C, 35N",
                "B. 4.5 x 10^6N/C, 45N",
                "C. 5.5 x 10^6N/C, 55N",
                "D. 6.5 x 10^6N/C, 65N",
                "B. 4.5 x 10^6N/C, 45N"));

        questionList.add(new Question( "15. The magnitude of electric field is 40kN/C. If an electron is placed in the same field, what force will be \n" +
                "exerted on it?",
                "A. 3.4 x 10^-15N",
                "B. 4.4 x 10^-15N",
                "C. 5.4 x 10^-15N",
                "D. 6.4 x 10^-15N",
                "D. 6.4 x 10^-15N"));

        questionList.add(new Question( "16. Words combine to form __________.",
                "(A) " +
                        "sentences",
                "(B) clauses. ",
                "(C) phrases. ",
                "(D) morphemes",
                "(C) phrases. "));

        questionList.add(new Question( "17. An important feature of language4 is its \n" +
                "___________ ",
                "(A) structure",
                "(B) texture.",
                "(C) strength.",
                "(D) stature.",
                "(A) structure"));

        questionList.add(new Question( "18. The English as a second \n" +
                "language countries include all except ________.",
                "(A) Ghana.",
                "(B) India.",
                "(C) Nigeria. ",
                "(D) USA",
                "(D) USA"));

        questionList.add(new Question( "19. " +
                "________ to come this weekend to listen to the broadcast of the football match?",
                "(A) Do you like,",
                "(B) " +
                        "Would you like. ",
                "(C) Are you like.",
                "(D) Will you like.",
                "(B) " +
                        "Would you like. "));

        questionList.add(new Question( " 20. Factors affecting study skills include all \n" +
                "except_________.",
                "(A) hybridizing.",
                "(B) the study time.",
                "(C) the study venue",
                "(D) reading aloud to " +
                        "oneself.",
                "(A) hybridizing."));

        questionList.add(new Question( "21. Computers are now cheap_______ for nearly everyone to afford it.",
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

    private void getQuestionPhase2(List<Question> list) {

        questionList.add(new Question("If (1 - tan²67½)/(1 + tan²67½) = cos 135. Find tan 67½ in surd form.",
                "A. √2 + 1",
                "B. √2 - 1",
                "C. √2 - ½",
                "D. √2/2 + 1",
                "A. √2 + 1"));

        questionList.add(new Question("What is the relevance of sin(A+B)?",
                "A. sin A cos B - cos A cos B",
                "B. sin A cos B + cos A sin B",
                "C.  cos A cos B − sin A sin B",
                "D. sin A cos B − cos A sin B",
                "B. sin A cos B + cos A sin B"));

        questionList.add(new Question("Find the surd form of sin 75°",
                "A. (√3 + 1)/2√2",
                "B. (√4 + 2)/√2",
                "C.  (1 - √3)/√2",
                "D. (√3 - 6)/2√2",
                "A. (√3 + 1)/2√2"));

        questionList.add(new Question("Find the surd form of sin 15°",
                "A. (√3 + 1)/2√2",
                "B. (√4 + 2)/√2",
                "C.  (1 - √3)/√2",
                "D. (√3 - 1)/2√2",
                "D. (√3 - 1)/2√2"));

        questionList.add(new Question("Find the surd form of sin 105°",
                "A. (√3 + 1)/2√2",
                "B. (√4 + 2)/√2",
                "C.  (1 - √3)/2√2",
                "D. (√3 - 1)/2√2",
                "C.  (1 - √3)/2√2"));

        questionList.add(new Question("Simplify tan (A+B+C)",
                "A. (tan 2A + tan 2B + tan 2C - tanAtanBtanC)/ 1 - tanAtanB - tanBtanC - tanAtanC",
                "B. (tan A + tan B + tan C - tanAtanBtanC)/ 1 - tanAtanB - tanBtanC - tanAtanC",
                "C. (tan A - tan B - tan C + tanAtanBtanC)/ 1 + tanAtanB + tanBtanC - tanAtanC",
                "D. (tan² A + tan² B + tan² C - tanAtanBtanC)/ 1 - tanAtanB - tanBtanC - tanAtanC",
                "B. (tan A + tan B + tan C - tanAtanBtanC)/ 1 - tanAtanB - tanBtanC - tanAtanC"));

        questionList.add(new Question("Simplify sin 2A",
                "A. sin²Acos²A",
                "B. 2sin²Acos²A",
                "C. 2sinAcosA",
                "D. 2sec²A",
                "C. 2sinAcosA"));

        questionList.add(new Question("Simplify cos 2A",
                "A. 1 - 2sin²A",
                "B. 2 - 2sin²A",
                "C. cosec²A",
                "D. 2sec²A",
                "A. 1 - 2sin²A"));

        questionList.add(new Question("Express tan 45° in surd form",
                "A. 2 −√3",
                "B. 2 +4√3",
                "C. 1 +√3",
                "D. 2 +√3",
                "A. 2 −√3"));

        questionList.add(new Question("Evaluate sin(3π/2)",
                "A. 1",
                "B. -½",
                "C. √5",
                "D. ½",
                "B. -½"));

        questionList.add(new Question("Evaluate cos(−9π/4)",
                "A. -√2/2",
                "B. -½",
                "C. √2/2",
                "D. ½",
                "C. √2/2"));

        questionList.add(new Question("In a triangle ABC, Line AC = 7.2cm, Line AB = 8.9cm and angle BCA = 55°." +
                "Find the distance between line BC",
                "A. 12.1cm",
                "B. 10.79cm",
                "C. 11.09cm",
                "D. 8.2cm",
                "B. 10.79cm"));

        questionList.add(new Question("Express cos(A + B)",
                "A. 12.1cm",
                "B. 10.79cm",
                "C. 11.09cm",
                "D. cosAcosB - sinAsinB",
                "D. cosAcosB - sinAsinB"));
    }
}