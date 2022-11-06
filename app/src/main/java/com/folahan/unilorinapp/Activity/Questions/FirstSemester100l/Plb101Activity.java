package com.folahan.unilorinapp.Activity.Questions.FirstSemester100l;

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

public class Plb101Activity extends AppCompatActivity {
    private List<Question> questionList;
    private Random random;
    private TextView questionText, questionNo, countDown, answerText;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private CountDownTimer timer;
    int pos, pos2=0, mTimeLeft = 600000, questionAnswered = 1;
    Button btnNext, btnPrev, btnEnd;
    private AlertDialog dialog;
    private boolean mTimerRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gns111);

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
            questionAnswered++;
            pos = random.nextInt(questionList.size());
            setDataView(pos);
        });
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

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Pls finish the test", Toast.LENGTH_SHORT).show();
    }

    private void getQuestionPhase(List<Question> list) {


        questionList.add(new Question("1.The top class Nigerian universities are determined to _________ the\n" +
                "quality of education",
                "(A) keep up",
                "(B) keep up with",
                "(C) keep on",
                "(D) keep at",
                "(A) keep up"));

        questionList.add(new Question("Some people are failures or under-achievers because ____________.",
                "(A) they don’t like the library. ",
                "(B) they are unable to manage their time effectively.",
                "(C) they are poor readers",
                "(D) they are quiet " +
                        "speakers ",
                "(B) they are unable to manage their time effectively."));

        questionList.add(new Question("3. Anyone who wants to communicate effectively using English must___________.",
                "(A) l earn to " +
                        "verbalize. ",
                "(B) understand how others act and feel.",
                "(C) take pains to learn the rules",
                "(D) take pains to " +
                        "listen",
                "(C) take pains to learn the rules"));

        questionList.add(new Question("4. As a s a system of communication, a given language has its own sets of _____________",
                "(A) " +
                        "conversions. ",
                "(B) conventions.", "(C) corrections.",
                "(D) conversations.",
                "(B) conventions."));

        questionList.add(new Question("5. Learning involves the following activities except______________. ",
                "(A) sleeping.",
                "(B) reflecting.", "(C) thinking.",
                "(D) organizing facts.",
                "(A) sleeping."));

        questionList.add(new Question("6. ________ is a major study skill factor.",
                "(A) the mind.",
                "(B) the foreground.",
                "(C) the background." ,
                "(D) " +
                        "the context. ", "(A) the mind."));

        questionList.add(new Question("7. It is the _________of study skills that enables the learner to be in charge of his/her own \n" +
                "learning.",
                "(A) appreciation. ", "(B) memorization",
                "(C) anticipation", "(D) application.",
                "(D) application."));

        questionList.add(new Question(" 8. Personal " +
                "positive attitude helps. ",
                "(A) contents", "(B) study habits.",
                "(C) distractions.",
                "(D) interrogations",
                "(B) study habits."));

        questionList.add(new Question("9. " +
                "Study skills are habits that instill _________ in the learner once well cultivated.",
                "(A) Content learning.",
                "(B) knowledge",
                "(C) discipline",
                "(D) comprehension.",
                "(C) discipline"));

        questionList.add(new Question("10. Which of the following is not a study skill strategy for the English as a second language \n" +
                "learner___________?",
                "(A) PQRST", "(B) time management",
                "(C) Personal motivation",
                "(D) Personal ", "(D) Personal "));

        questionList.add(new Question("11. Learners frequent interactions with colleague, teachers and schooars online help to \n" +
                "ensure__________________.",
                "(A) academic objectiveness.",
                "(B) academic currency." ,
                "(C) academic harvest.",
                "(D) academic competence.",
                "(B) academic currency."));

        questionList.add(new Question( "12. Which of the following is not a level of reading \n" +
                "comprehension?________ ",
                "(A) critical.",
                "(B) inferential.",
                "(C) liberal.",
                "(D) literal.",
                "(C) liberal."));

        questionList.add(new Question( "13. Reading has " +
                "________ nature and is purpose bound, especially at the advanced level of learning. ",
                "(A) intrusive.",
                "(B) " +
                        "invalid. ",
                "(C) intricate.",
                "(D) intrinsic.",
                "(C) intricate."));

        questionList.add(new Question( "14. The minister was alarmed _______ the drop in quality of " +
                "education. ",
                "(A) with.",
                "(B) on. ",
                "(C) at.",
                "(D) for.",
                "(C) at."));

        questionList.add(new Question( "15. For understanding to take place, the basic foundation needed is __________.",
                "(A) Information.",
                "(B) " +
                        "communication.",
                "(C) concentration. ",
                "(D) reflection.",
                "(C) concentration. "));

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
}